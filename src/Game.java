import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;

import javax.imageio.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;


public class Game extends Background {
    private String username;
    private int fruitChoice;
    private String message;

    private Socket server;

    private Thread messageOutgoing;
    private Thread messageIncoming;

    private Image gameBG;

    private JPanel chatbox;

    public JTextField chatField;

    public JTextArea convo;

    private JScrollPane scrollPane;

    public Game(String address, int port, String username, int fruitChoice) {
        this.username = username;
        this.fruitChoice = fruitChoice;
        message = "";

        try {
            server = new Socket(address, port);   // creating a new socket for client and binding it to a port
        } catch (IOException e) {
            System.out.println("Cannot find (or disconnected from) Server");
        }

        renderGUI();

        loadChat();
    }

    private void renderGUI() {
        this.setLayout(new BorderLayout());

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

        try {
            // Panel Backgrounds
            gameBG = ImageIO.read(this.getClass().getResource("res/game.png"));
        } catch(IOException ioe) {
            System.out.println("Error while reading image file.");
        }

        convo.setEditable(false);

        chatField.setPreferredSize(new Dimension(300, 40));

        scrollPane.setPreferredSize(new Dimension(300, 200));

        chatbox.setLayout(new BoxLayout(chatbox, BoxLayout.Y_AXIS));
        chatbox.setBorder(new EmptyBorder(600, 20, 20, 900));
        chatbox.setOpaque(false);
        chatbox.add(scrollPane);
        chatbox.add(chatField);

        this.setImage(new ImageIcon(gameBG));
        this.add(chatbox, BorderLayout.CENTER);
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

    public void start() {
        messageOutgoing.start();
        messageIncoming.start();
    }
}
