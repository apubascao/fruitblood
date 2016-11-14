import java.awt.*; 
import java.awt.event.*; 
import javax.swing.*;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.geom.Rectangle2D;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.imageio.*;
import java.io.*;

public class GamePanel extends Background {
	Seed seed;
	GoldenSeed goldenSeed;
	Player player;
	Image background;

	private Image gameBG;

	//PlayerListener mouseListener = new PlayerListener();
	//this.addMouseListener(mouseListener);

	MovingAdapter ma = new MovingAdapter();
	int tempX = ((getWidth()-3750)/2)+625;
	int tempY = ((getHeight()-2400)/2)+400;
	int prevX, prevY, z;
	int seedX;
	int seedY;
	int gseedX;
	int gseedY;
	JLabel score;
 
	public GamePanel(Player player) {
		this.setLayout(null);
		this.setSize(1250, 800);
		setFocusable(true);

		try {
            // Panel Backgrounds
            gameBG = ImageIO.read(this.getClass().getResource("res/galaxy.png"));
        } catch(IOException ioe) {
            System.out.println("Error while reading image file.");
        }
		this.setImage(new ImageIcon(gameBG));

		this.player = player;
		
		ImageIcon i = new ImageIcon("res/game.png");
		background = i.getImage();   
		seed = new Seed();	
		goldenSeed = new GoldenSeed();
		
		score = new JLabel("SCORE");
		score.setText("SCORE");
		score.setPreferredSize(new Dimension(580,11));
		score.setHorizontalAlignment(JLabel.CENTER);
		score.setBackground(Color.RED);
		add(score);

		addMouseMotionListener(ma);
   		addMouseListener(ma);    
   	}
    
	public void paint(Graphics g) {
		super.paint(g);
		Graphics2D g2d = (Graphics2D) g;
		
		g2d.drawImage(background, tempX, tempY, null);
		System.out.println("dx: " + player.getDx() + " dy: " + player.getDy());
		System.out.println("tempX = " + tempX + " tempY: " + tempY);
			
		for(z=0; z<200;z++){
			seedX = seed.getX(z);
			seedY = seed.getY(z);
			g2d.drawImage(seed.getImage(), tempX+seedX+600, tempY+seedY+500, null);    
		}

		for(z=0; z<20;z++){
			gseedX = goldenSeed.getX(z);
			gseedY = goldenSeed.getY(z);
			g2d.drawImage(goldenSeed.getImage(), tempX+gseedX+600, tempY+gseedY+500, null);    
		}

		g2d.drawImage(player.getImage(), player.getX(), player.getY(), null);

		tempX = tempX+player.getDx();
		tempY = tempY+player.getDy(); 
	}
 
	protected class MovingAdapter extends MouseAdapter {
		public void mousePressed(MouseEvent e) {
			player.mousePressed(e);
		}
 
		public void mouseMoved(MouseEvent e) {
			player.mouseMoved(e);
			player.move();
			repaint();
		}

		public void mouseReleased(MouseEvent e) {
			player.mouseReleased(e);
		}	
	}
}	