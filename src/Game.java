import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;

import javax.imageio.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;


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
			System.out.println("DATA = " + data);
			
        } catch (SocketException se) {
            System.out.println("The socket could not be opened, or the socket could not bind to the specified local port.");
        } catch (IOException ioe) {
            System.out.println("Input/Ouput error occurs!");      
		}	
		
        gamePanel = new GamePanel(player);
		
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
							player.ateSeed();
						}
						
						/*
						if ate a player
						*/
                        
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
