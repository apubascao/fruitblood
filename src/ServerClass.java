import javax.swing.*;
import java.io.*;
import java.awt.*;
import javax.imageio.*;
import java.awt.event.*;

public class ServerClass extends JFrame implements Runnable, ActionListener {
    private Background homeBG;            //home panel
    private Background createdBG;         //exit server creation panel

    private JPanel front;               //base panel
    private JTextField input;

    private JButton createButton;
    private JButton exitButton;
    private JButton exitButton2;

    private CardLayout cards;

    // Component backgrounds
    private Image homeImage;
    private Image createdImage;
    private Image createButtonImage;
    private Image exitButtonImage;

    public ServerClass() {
        homeBG = new Background();
        createdBG = new Background();

        front = new JPanel();
        input = new JTextField();

        cards = new CardLayout();

        //START MENU
        front.setLayout(cards);
        front.add(homeBG, "homeBG");
        front.add(createdBG, "createdBG");
        front.setPreferredSize(new Dimension(1250, 800));

        readImages();
    
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
            homeImage = ImageIO.read(this.getClass().getResource("res/homeImage.png"));
            createdImage = ImageIO.read(this.getClass().getResource("res/createdImage.png"));

            // Button Backgrounds
            createButtonImage = ImageIO.read(this.getClass().getResource("res/createButtonImage.png"));
            exitButtonImage = ImageIO.read(this.getClass().getResource("res/exitButtonImage.png"));
        } catch(IOException ioe) {
            System.out.println("Error while reading image file.");
        }

        homeBG.setImage(new ImageIcon(homeImage));
        createdBG.setImage(new ImageIcon(createdImage));
    }

    // Load and set buttons
    private void loadButtons() {
        createButton = new JButton(new ImageIcon(createButtonImage));
        exitButton = new JButton(new ImageIcon(exitButtonImage));
        exitButton2 = new JButton(new ImageIcon(exitButtonImage));

        createButton.addActionListener(this);
        exitButton.addActionListener(this);
        exitButton2.addActionListener(this);

        createButton.setSize(300, 150);
        exitButton.setSize(300, 150);
        exitButton2.setSize(300, 150);
        input.setSize(300, 75);

        createButton.setLocation(480, 600);
        //exitButton.setLocation(625, 600);
        exitButton2.setLocation(480, 600);
        input.setLocation(475, 380);

        homeBG.add(createButton);
        //homeBG.add(exitButton);
        homeBG.add(input);
        createdBG.add(exitButton2);
    }

    // Handle mouse clicks
    public void actionPerformed(ActionEvent e){
        if (e.getSource() == createButton) {
            String playersstr = input.getText();
            
            try{
                int playerint = Integer.parseInt(playersstr);
                if(playerint > 0){
                    cards.show(front, "createdBG");
                    
                }
                else{
                    JOptionPane.showMessageDialog(this, "Input must be greater than 0");
                    input.setText("");
                }
            } catch (Exception excp){
                JOptionPane.showMessageDialog(this, "Input must be a whole number");
                input.setText("");
            }
            
        } else if (e.getSource() == exitButton || e.getSource() == exitButton2) {
            System.exit(0);
        }
    }

    public void run() {
        
    }
}
