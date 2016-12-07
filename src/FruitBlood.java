import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;

import javax.swing.*;
import javax.imageio.*;
import javax.swing.border.EmptyBorder;


public class FruitBlood extends JFrame implements ActionListener {
    private Background home; //home panel
    private Background gameMechanics;   //game mechanics panel
    private Background gameInstructions; // game instructions panel
    private Background lobby;

    private JPanel front; //base panel

    private JButton instructions;
    private JButton start;
    private JButton mechanics;
    private JButton back1;
    private JButton back2;
    private JButton ready;

    private CardLayout cards;

    // Component backgrounds
    private Image homeBG;
    private Image gameMechanicsBG;
    private Image gameInstructionsBG;
    private Image instructionsBG;
    private Image startBG;
    private Image mechanicsBG;
    private Image backBG;

    private String address;
    private int port;
    private String username;
    private int fruitChoice;

    private Image lobbyBG;
    private Image prevBG;
    private Image nextBG;

    private JPanel left;
    private JPanel right;
    private JPanel portPanel;
    private JPanel addressPanel;
    private JPanel usernamePanel;
    private JPanel fruitChooser;

    private JButton prev;
    private JButton next;

    private JTextField portField;
    private JTextField addressField;
    private JTextField usernameField;
    
    private CardLayout card;

    private ArrayList<Image> fruitBG = new ArrayList<Image>();
    private ArrayList<Fruit> fruits = new ArrayList<Fruit>();

    private ArrayList<Game> players = new ArrayList<Game>();

    public FruitBlood() {
        home = new Background();
        gameMechanics = new Background();
        gameInstructions = new Background();
        lobby = new Background();

        ready = new JButton("READY");

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

        loadLobby();

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

        try {
            lobbyBG = ImageIO.read(this.getClass().getResource("res/lobby.png"));
            prevBG = ImageIO.read(this.getClass().getResource("res/prev.png"));
            nextBG = ImageIO.read(this.getClass().getResource("res/next.png"));

            // fruit images
            for (int i = 1; i <= 10; i++) {
                Image fruit = ImageIO.read(this.getClass().getResource("res/fruit" + i + ".png"));
                fruitBG.add(fruit);
            }

        } catch(IOException ioe) {
            System.out.println("Error while reading image file.");
        }

        lobby.setImage(new ImageIcon(lobbyBG));

        for (int i = 0; i < 10; i++) {
            Fruit f = new Fruit();
            f.setOpaque(false);
            f.setImage(new ImageIcon(fruitBG.get(i)));
            fruits.add(f);
        }

        prev = new JButton(new ImageIcon(prevBG));
        next = new JButton(new ImageIcon(nextBG));
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

    private void loadLobby() {
        fruitChoice = 1;
        
        lobby.setLayout(new BorderLayout());

        left = new JPanel();
        right = new JPanel();
        portPanel = new JPanel();
        addressPanel = new JPanel();
        usernamePanel = new JPanel();
        fruitChooser = new JPanel();

        portField = new JTextField();
        addressField = new JTextField();
        usernameField = new JTextField();

        card = new CardLayout();

        loadImages();

        ready.addActionListener(this);

        fruitChooser.setPreferredSize(new Dimension(525, 460));
        fruitChooser.setOpaque(false);
        fruitChooser.setLayout(card);
        for (int i = 0; i < 10; i++)
            fruitChooser.add(fruits.get(i), "fruit " + (i + 1));

        prev.setContentAreaFilled(false);
        prev.setBorderPainted(false);
        prev.setFocusPainted(false);
        prev.setEnabled(false);
        prev.addActionListener(this);

        next.setContentAreaFilled(false);
        next.setBorderPainted(false);
        next.setFocusPainted(false);
        next.addActionListener(this);

        left.setPreferredSize(new Dimension(625, 550));
        left.setLayout(new BoxLayout(left, BoxLayout.X_AXIS));
        left.setBorder(new EmptyBorder(250, 0, 90, 0));
        left.setOpaque(false);
        left.add(prev);
        left.add(fruitChooser);
        left.add(next);

        portField.setPreferredSize(new Dimension(300, 40));
        addressField.setPreferredSize(new Dimension(300, 40));
        usernameField.setPreferredSize(new Dimension(300, 40));

        portPanel.setPreferredSize(new Dimension(625, 400));
        portPanel.setBorder(new EmptyBorder(35, 120, 40, 0));
        portPanel.setOpaque(false);
        portPanel.add(portField);

        addressPanel.setPreferredSize(new Dimension(625, 400));
        addressPanel.setBorder(new EmptyBorder(35, 120, 40, 0));
        addressPanel.setOpaque(false);
        addressPanel.add(addressField);

        usernamePanel.setPreferredSize(new Dimension(625, 400));
        usernamePanel.setBorder(new EmptyBorder(35, 120, 40, 0));
        usernamePanel.setOpaque(false);
        usernamePanel.add(usernameField);

        right.setPreferredSize(new Dimension(625, 550));
        right.setLayout(new BoxLayout(right, BoxLayout.Y_AXIS));
        right.setBorder(new EmptyBorder(250, 0, 90, 0));
        right.setOpaque(false);
        right.add(addressPanel);
        right.add(portPanel);
        right.add(usernamePanel);
        right.add(ready);

        lobby.add(left, BorderLayout.CENTER);
        lobby.add(right, BorderLayout.EAST);
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
        } else if (e.getSource() == prev) {
            fruitChoice--;
            next.setEnabled(true);
            if (fruitChoice == 1) prev.setEnabled(false);
            else prev.setEnabled(true);
            card.show(fruitChooser, "fruit " + fruitChoice);
        } else if (e.getSource() == next) {
            fruitChoice++;
            prev.setEnabled(true);
            if (fruitChoice == 10) next.setEnabled(false);
            else next.setEnabled(true);
            card.show(fruitChooser, "fruit " + fruitChoice);
        } else if (e.getSource() == ready) {
            port = Integer.parseInt(portField.getText());
            address = addressField.getText();
            username = usernameField.getText();

            Game game = new Game(address, port, username, fruitChoice);
            
            players.add(game);			
			
            front.add(game, "game");
            cards.show(front, "game");
            
            game.start();
        }
    }

    public static void main(String args[]){
        new FruitBlood();
    }
}
