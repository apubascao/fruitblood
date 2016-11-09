import javax.swing.*;
import java.io.*;
import java.awt.*;
import javax.imageio.*;
import java.awt.event.*;

public class FruitBlood extends JFrame implements ActionListener {
    private boolean end;

    private Background home; //home panel
    private Background gameMechanics;   //game mechanics panel
    private Background gameInstructions; // game instructions panel

    private JPanel front; //base panel

    private JButton instructions;
    private JButton start;
    private JButton mechanics;
    private JButton back1;
    private JButton back2;

    private CardLayout cards;

    // Component backgrounds
    private Image homeBG;
    private Image gameMechanicsBG;
    private Image gameInstructionsBG;
    private Image instructionsBG;
    private Image startBG;
    private Image mechanicsBG;
    private Image backBG;

    private Lobby lobby;

    public FruitBlood() {
        this.end = false;

        home = new Background();
        gameMechanics = new Background();
        gameInstructions = new Background();

        lobby = new Lobby();

        front = new JPanel();

        cards = new CardLayout();

        //START MENU
        front.setLayout(cards);
        front.add(home, "home");
        front.add(gameMechanics, "mechanics");
        front.add(gameInstructions, "instructions");
        front.add(lobby, "lobby");
        front.setPreferredSize(new Dimension(1250, 800));

        loadImages();
    
        home.setLayout(null);
        gameMechanics.setLayout(null);
        gameInstructions.setLayout(null);     

        loadButtons();

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(front);
        this.setResizable(false);
        this.setVisible(true);
        this.pack();
    }

    // Read and set image background of components
    private void loadImages() {
        try {
            // Panel Backgrounds
            homeBG = ImageIO.read(this.getClass().getResource("res/home.png"));
            gameMechanicsBG = ImageIO.read(this.getClass().getResource("res/gameMechanics.png"));
            gameInstructionsBG = ImageIO.read(this.getClass().getResource("res/gameInstructions.png"));

            // Button Backgrounds
            startBG = ImageIO.read(this.getClass().getResource("res/start.png"));
            mechanicsBG = ImageIO.read(this.getClass().getResource("res/mechanics.png"));
            instructionsBG = ImageIO.read(this.getClass().getResource("res/instructions.png"));
            backBG = ImageIO.read(this.getClass().getResource("res/back.png"));
        } catch(IOException ioe) {
            System.out.println("Error while reading image file.");
        }

        home.setImage(new ImageIcon(homeBG));
        gameMechanics.setImage(new ImageIcon(gameMechanicsBG));
        gameInstructions.setImage(new ImageIcon(gameInstructionsBG));
    }

    // Load and set buttons
    private void loadButtons() {
        instructions = new JButton(new ImageIcon(instructionsBG));
        start = new JButton(new ImageIcon(startBG));
        mechanics = new JButton(new ImageIcon(mechanicsBG));
        back1 = new JButton(new ImageIcon(backBG));
        back2 = new JButton(new ImageIcon(backBG));

        instructions.addActionListener(this);
        start.addActionListener(this);
        mechanics.addActionListener(this);
        back1.addActionListener(this);
        back2.addActionListener(this);

        instructions.setSize(300, 150);
        start.setSize(300, 150);
        mechanics.setSize(300, 150);
        back1.setSize(100, 100);
        back2.setSize(100, 100);

        instructions.setLocation(80, 600);
        start.setLocation(480, 600);
        mechanics.setLocation(880, 600);
        back1.setLocation(50, 50);
        back2.setLocation(50, 50);

        home.add(instructions);
        home.add(start);
        home.add(mechanics);
        gameMechanics.add(back1);
        gameInstructions.add(back2);
    }

    // Handle mouse clicks
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == start) {
            cards.show(front, "lobby");
        } else if (e.getSource() == mechanics) {
            cards.show(front, "mechanics");
        } else if (e.getSource() == instructions) {
            cards.show(front, "instructions");
        } else if (e.getSource() == back1 || e.getSource() == back2) {
            cards.show(front, "home");
        }
    }

    public static void main(String args[]){
        new FruitBlood();
    }
}
