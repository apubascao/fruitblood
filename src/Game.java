import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import java.util.Random;

import javax.imageio.*;
import javax.swing.*;


public class Game extends JLayeredPane {
    private String username;
    private int fruitChoice;
    private String message;

    private Socket server;

    private Thread messageOutgoing;
    private Thread messageIncoming;
    private Thread moveOutgoing;
    private Thread moveIncoming;

    private JPanel chatbox;

    public JTextField chatField;

    public JTextArea convo;

    private JScrollPane scrollPane;

    private Player player;

    private GamePanel gamePanel;
    
    private String address;
    private int port;    
    private DatagramSocket socket;

    public Game(String address, int port, String username, int fruitChoice) {
        this.username = username;
        this.fruitChoice = fruitChoice;
        message = "";

        try {
            server = new Socket(address, port);   // creating a new socket for client and binding it to a port
            player = new Player(address, port, username, fruitChoice);
        } catch (IOException e) {
            System.out.println("Cannot find (or disconnected from) Server");
        }
        
        loadChat();
        player.sendPort();

        this.address = address;
        this.port = port;
        
        loadGame();
        
        try {           
            socket = new DatagramSocket(player.getPlayerSocket());
            byte buffer[] = new byte[256];
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
        
            //for starting the game
            socket.receive(packet);
        } catch (SocketException se) {
            System.out.println("The socket could not be opened, or the socket could not bind to the specified local port.");
        } catch (IOException ioe) {
            System.out.println("Input/Ouput error occurs!");      
        }
                
        renderGUI();
    }

    private void renderGUI() {
        String data = null; 
        try {
            byte buffer[] = new byte[256];
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
            
            //for initial coordinates of seeds
            socket.receive(packet);
            data = new String(packet.getData());
            
        } catch (SocketException se) {
            System.out.println("The socket could not be opened, or the socket could not bind to the specified local port.");
        } catch (IOException ioe) {
            System.out.println("Input/Ouput error occurs!");      
        }   
        
        gamePanel = new GamePanel(player, address, port);
        
        gamePanel.initialSeeds(data);

        chatbox = new JPanel();

        convo = new JTextArea();

        scrollPane = new JScrollPane(convo);

        chatField = new JTextField();

        chatField.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                message = chatField.getText();
                chatField.setText("");
            }
        });

        convo.setEditable(false);

        chatField.setPreferredSize(new Dimension(300, 40));

        scrollPane.setPreferredSize(new Dimension(300, 200));

        chatbox.setLayout(new BoxLayout(chatbox, BoxLayout.Y_AXIS));
        chatbox.setBounds(20, 540, 300, 240);
        chatbox.add(scrollPane);
        chatbox.add(chatField);
        chatbox.setOpaque(false);

        this.add(chatbox, new Integer(2));
        this.add(gamePanel, new Integer(1));
    }

    private void loadChat() {

        //for getting the message from the user
        messageOutgoing = new Thread() {
            public void run() {
                while (true) {
                    try {
                        OutputStream outToServer = server.getOutputStream();
                        DataOutputStream out = new DataOutputStream(outToServer);
                        if (message.length() > 0) out.writeUTF("\n" + username + ": " + message); // send message data
                        message = "";
                    } catch(IOException ioe) {
                        System.out.println("Input/Output error occurs!");
                        System.exit(1);
                    }
                }
            }
        };

        //for getting messages from other users
        messageIncoming = new Thread() {
            public void run() {
                while (true) {
                    try{
                        DataInputStream in = new DataInputStream(server.getInputStream());
                        convo.append(in.readUTF()); // get message data
                        message = "";
                    } catch (IOException e) {
                        System.out.println("Stream has been closed or Input/Ouput error occurs!");
                        System.exit(1);
                    }            
                }
            }
        };
    }

    private void loadGame(){
        moveIncoming = new Thread() {
            public void run(){
                try {
                    while (true) {
                        byte buffer[] = new byte[256];
                        DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                        
                        socket.receive(packet);

                        String data = new String(packet.getData());             
                        
                        if(data.startsWith("playermove")){
                            gamePanel.paintPlayer(data);
                        }
                        
                        if(data.startsWith("seedupdate")){
                            gamePanel.paintSeed(data);
                        }

                        if(data.startsWith("ateseed")){
                            String substring[] = data.split(",");
                            int type = Integer.parseInt(substring[1].trim());

                            if (type == 1) player.ateSeed();
                            else {
                                player.setMultiplier(type);

                                // 10 seconds countdown
                                Thread t = new Thread() {
                                    public void run() {
                                        for (int i = 10; i >= 0; i--) {
                                            try {
                                                // thread to sleep for 1000 milliseconds
                                                Thread.sleep(1000);
                                            }catch(InterruptedException ie) {}
                                        }
                                        player.setMultiplier(1);
                                    }
                                };

                                t.start();
                            }
						}
						
						if(data.startsWith("ateopponent")){
							player.ateOpponent();
						}
						
						if(data.startsWith("eaten")){
							player.decreaseLife();

							// Respawn player when eaten
							if(player.getLife() > 0){
								while(true){
									Random rand = new Random();									
									int x = rand.nextInt(1250);
									int y = rand.nextInt(800);
									
									if(!gamePanel.respawnOverlap(x, y, player.getFruitSize())){
										player.setX(x);
										player.setY(y);
										player.sendNewXY();										
										break;
									}
								}
								
							}
						}
						
						if(data.startsWith("newXY")){
							gamePanel.paintPlayer(data);
						}
						
						if(data.startsWith("removelistener")){
							gamePanel.removeListener();
						}
						
						if(data.startsWith("dead")){
							gamePanel.dead(data);
						}
                        
                        if(data.startsWith("win")){
                            gamePanel.removeListener();
                            System.out.println(data);
                            //create a jpanel that will display the winner, username and fruitChoice
                        }
                    }
                } catch (SocketException se) {
                    System.out.println("The socket could not be opened, or the socket could not bind to the specified local port.");
                } catch (IOException ioe) {
                    System.out.println("Input/Ouput error occurs!");
                }
            }
        };      
    }

    public void start() {
        messageOutgoing.start();
        messageIncoming.start();
        moveIncoming.start();
    }
}
