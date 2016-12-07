import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import javax.swing.border.EmptyBorder;

public class Winner extends Background {
	private Image winnerBG;

	private JLabel winnerUsername;

	private JPanel left;
	private JPanel right;
	private JPanel top;
	private JPanel center;

	private String winner;
	private int fruitChoice;

	public Winner(String winner, int fruitChoice) {
		this.winner = winner;
		this.fruitChoice = fruitChoice;

        this.setLayout(new BorderLayout());

        winnerUsername = new JLabel(winner);
        winnerUsername.setSize(800, 20);
        winnerUsername.setFont(new Font(winnerUsername.getFont().getName(), 
            winnerUsername.getFont().getStyle(), 50));

        left = new JPanel();
        right = new JPanel();
        top = new JPanel();
        center = new JPanel();
	
		try {
            // Panel Background
            winnerBG = ImageIO.read(this.getClass().getResource("res/winner.png"));

            Image fruit = ImageIO.read(this.getClass().getResource("res/fruit" + fruitChoice + ".png"));

            this.setImage(new ImageIcon(winnerBG));

            Fruit f = new Fruit();
            f.setOpaque(false);
            f.setImage(new ImageIcon(fruit));

            top.setOpaque(false);
            left.setOpaque(false);
            right.setOpaque(false);
            center.setOpaque(false);

            top.setPreferredSize(new Dimension(800, 200));
            left.setPreferredSize(new Dimension(395, 600));
            right.setPreferredSize(new Dimension(395, 600));
            center.setPreferredSize(new Dimension(460, 600));

            center.setLayout(new BoxLayout(center, BoxLayout.Y_AXIS));
            center.setBorder(new EmptyBorder(0, 0, 100, 0));
            center.add(f);
            center.add(winnerUsername);

            this.add(center, BorderLayout.CENTER);
            this.add(top, BorderLayout.NORTH);
            this.add(left, BorderLayout.WEST);
            this.add(right, BorderLayout.EAST);
        } catch(IOException ioe) {
            System.out.println("Error while reading image file.");
        }
	}	
}