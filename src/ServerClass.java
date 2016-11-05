import javax.swing.*;
import java.io.*;
import java.awt.*;
import java.net.*;
import javax.imageio.*;
import java.awt.event.*;

public class ServerClass extends JFrame implements Runnable, ActionListener {
    private String portstr;
    private String playersstr;
    private int portint;
    private int playerint;

    private ChatServer chatServer;

    private Background portBG;            //home panel
    private Background homeBG;            //home panel
    private Background createdBG;         //exit server creation panel

    private JPanel front;               //base panel

    private JTextField portinput;
    private JTextField playerinput;

    private JButton nextButton;
    private JButton createButton;
    private JButton exitButton;

    private JLabel serverAddress;

    private CardLayout cards;

    // Component backgrounds
    private Image portImage;
    private Image homeImage;
    private Image createdImage;

    private Image nextButtonImage;
    private Image createButtonImage;
    private Image exitButtonImage;

    public ServerClass() {
        portBG = new Background();
        homeBG = new Background();
        createdBG = new Background();

        front = new JPanel();
        portinput = new JTextField();
        playerinput = new JTextField();

        cards = new CardLayout();

        //START MENU
        front.setLayout(cards);
        front.add(portBG, "portBG");
        front.add(homeBG, "homeBG");
        front.add(createdBG, "createdBG");
        front.setPreferredSize(new Dimension(1250, 800));

        readImages();
    
        portBG.setLayout(null);
        homeBG.setLayout(null);
        createdBG.setLayout(null);     

        loadButtons();

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(front);
        this.setResizable(false);
        this.setVisible(true);
        this.pack();
    }

    // Read and set image background of components
    private void readImages() {
        try {
            // Panel Backgrounds
            portImage = ImageIO.read(this.getClass().getResource("res/portImage.png"));
            homeImage = ImageIO.read(this.getClass().getResource("res/homeImage.png"));
            createdImage = ImageIO.read(this.getClass().getResource("res/createdImage.png"));

            // Button Backgrounds
            nextButtonImage = ImageIO.read(this.getClass().getResource("res/nextButtonImage.png"));
            createButtonImage = ImageIO.read(this.getClass().getResource("res/createButtonImage.png"));
            exitButtonImage = ImageIO.read(this.getClass().getResource("res/exitButtonImage.png"));

        } catch(IOException ioe) {
            System.out.println("Error while reading image file.");
        }

        portBG.setImage(new ImageIcon(portImage));
        homeBG.setImage(new ImageIcon(homeImage));
        createdBG.setImage(new ImageIcon(createdImage));
    }

    // Load and set buttons
    private void loadButtons() {
        nextButton = new JButton(new ImageIcon(nextButtonImage));
        createButton = new JButton(new ImageIcon(createButtonImage));
        exitButton = new JButton(new ImageIcon(exitButtonImage));

        nextButton.addActionListener(this);
        createButton.addActionListener(this);
        exitButton.addActionListener(this);

        nextButton.setSize(300, 150);
        createButton.setSize(300, 150);
        exitButton.setSize(300, 150);

        portinput.setSize(300, 75);
        playerinput.setSize(300, 75);

        nextButton.setLocation(480, 600);
        createButton.setLocation(480, 600);
        exitButton.setLocation(480, 600);

        portinput.setLocation(475, 380);
        playerinput.setLocation(475, 380);

        portBG.add(nextButton);
        portBG.add(portinput);
        homeBG.add(createButton);
        homeBG.add(playerinput);
        createdBG.add(exitButton);
    }

    // Handle mouse clicks
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == nextButton) {            
            portstr = portinput.getText();
            try{    //try catch for port
                portint = Integer.parseInt(portstr);

                if(portint >= 1024 && portint <= 65535){
                    cards.show(front, "homeBG");
                }
                else{
                    JOptionPane.showMessageDialog(this, "Port can be any number from 1024 to 65535");
                    portinput.setText("");
                    return;
                }

            } catch (Exception excp){
                JOptionPane.showMessageDialog(this, "Invalid input! Port must be a whole number");
                portinput.setText("");
                return;
            }
        } else if (e.getSource() == createButton) {    
            playersstr = playerinput.getText();
            try {    //try catch for number of players
                playerint = Integer.parseInt(playersstr);
                if(playerint >= 3) {
                    cards.show(front, "createdBG");
                    serverAddress = new JLabel(InetAddress.getLocalHost().getHostAddress());
                    serverAddress.setSize(800, 100);
                    serverAddress.setFont(
                        new Font(serverAddress.getFont().getName(), 
                            serverAddress.getFont().getStyle(), 100));
                    serverAddress.setLocation(350, 300);
                    createdBG.add(serverAddress);
                    chatServer = new ChatServer(portint, playerint);
                    chatServer.start();
                } else {
                    JOptionPane.showMessageDialog(this, "Invalid input! Game requires a minimum of 3 players.");
                    playerinput.setText("");
                }
            } catch (Exception excp) {
                JOptionPane.showMessageDialog(this, "Invlaid input! Input must be a whole number");
                playerinput.setText("");
            }
        } else if (e.getSource() == exitButton) {
            System.exit(0);
        }
    }

    public void run() {
        
    }
}
