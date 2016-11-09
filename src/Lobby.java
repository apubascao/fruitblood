import javax.swing.*;
import java.io.*;
import java.awt.*;
import javax.imageio.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.border.EmptyBorder;

public class Lobby extends Background implements ActionListener {
    private Image lobbyBG;
    private Image prevBG;
    private Image nextBG;

    private JPanel left;
    private JPanel right;
    private JPanel usernamePanel;
    private JPanel fruitChooser;

    private JButton prev;
    private JButton next;

    private JTextField usernameField;

    private JLabel countdown;

    private CardLayout card;

    private ArrayList<Image> fruitBG = new ArrayList<Image>();
    private ArrayList<Fruit> fruits = new ArrayList<Fruit>();

    private int choice;

    public Lobby() {
        choice = 1;
        
        this.setLayout(new BorderLayout());

        left = new JPanel();
        right = new JPanel();
        usernamePanel = new JPanel();
        fruitChooser = new JPanel();

        usernameField = new JTextField();

        countdown = new JLabel("Countdown: 5");

        card = new CardLayout();

        loadImages();

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

        usernameField.setPreferredSize(new Dimension(300, 40));

        usernamePanel.setPreferredSize(new Dimension(625, 400));
        usernamePanel.setBorder(new EmptyBorder(35, 120, 40, 0));
        usernamePanel.setOpaque(false);
        usernamePanel.add(usernameField);

        countdown.setSize(625, 90);
        countdown.setFont(
            new Font(countdown.getFont().getName(), 
                countdown.getFont().getStyle(), 24));

        right.setPreferredSize(new Dimension(625, 550));
        right.setLayout(new BoxLayout(right, BoxLayout.Y_AXIS));
        right.setBorder(new EmptyBorder(250, 0, 90, 0));
        right.setOpaque(false);
        right.add(usernamePanel);
        right.add(countdown);

        this.add(left, BorderLayout.CENTER);
        this.add(right, BorderLayout.EAST);
    }

    // Load and set buttons
    private void loadImages() {
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

        this.setImage(new ImageIcon(lobbyBG));

        for (int i = 0; i < 10; i++) {
            Fruit f = new Fruit();
            f.setOpaque(false);
            f.setImage(new ImageIcon(fruitBG.get(i)));
            fruits.add(f);
        }

        prev = new JButton(new ImageIcon(prevBG));
        next = new JButton(new ImageIcon(nextBG));
    }

    // Handle mouse clicks
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == prev) {
            choice--;
            next.setEnabled(true);
            if (choice == 1) prev.setEnabled(false);
            else prev.setEnabled(true);
            card.show(fruitChooser, "fruit " + choice);
        } else if (e.getSource() == next) {
            choice++;
            prev.setEnabled(true);
            if (choice == 10) next.setEnabled(false);
            else next.setEnabled(true);
            card.show(fruitChooser, "fruit " + choice);
        }
        System.out.println(choice);
    }
}
