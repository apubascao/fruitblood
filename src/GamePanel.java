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
import java.util.*;

public class GamePanel extends Background {
	Seed seed;
	GoldenSeed goldenSeed;
	Player player;
	Image background;	

	private Image gameBG;
	
	private HashMap<String, String> players;

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
		players = new HashMap<String, String>();
		
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
			
		for(z=0; z<200;z++){
			seedX = seed.getX(z);
			seedY = seed.getY(z);
			g2d.drawImage(seed.getImage(), tempX+seedX+50, tempY+seedY+150, null);    
		}

		for(z=0; z<20;z++){
			gseedX = goldenSeed.getX(z);
			gseedY = goldenSeed.getY(z);
			g2d.drawImage(goldenSeed.getImage(), tempX+gseedX+50, tempY+gseedY+150, null);    
		}

		g2d.drawImage(player.getImage(), player.getX(), player.getY(), null);

		tempX = tempX-player.getDx();
		tempY = tempY-player.getDy(); 		
		
		//render all opponents
		System.out.println(players.keySet());
		for (Object value : players.values()) {
			String data = value + "";
			
			System.out.println("data = " + data);
			
			String substring[] = data.split(",");

			String username = substring[0].trim();		
			String fruitChoice = substring[1].trim();
			int size = Integer.parseInt(substring[2].trim());
			int x = Integer.parseInt(substring[3].trim());
			int y = Integer.parseInt(substring[4].trim());
						
			System.out.println("fruitChoice = " + fruitChoice);
						
			ImageIcon i = new ImageIcon("res/fruit" + fruitChoice + ".png");
			Image playerImage = i.getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT);			
			
			System.out.println(playerImage.getWidth(null));
			
			g2d.drawImage(playerImage, x, y, null);
		}
	
	}

	//String data = username + "," + fruitChoice + "," + size + "," + dx + "," + dy;
	public void paintPlayer(String data){
		String substring[] = data.split(",");
		String username = substring[0];		
		players.put(username, data);
						
		repaint();
	}
 
	protected class MovingAdapter extends MouseAdapter {
		public void mousePressed(MouseEvent e) {
			player.mousePressed(e);
		}
 
		public void mouseMoved(MouseEvent e) {
			player.mouseMoved(e);
			player.move();
			//repaint();
		}

		public void mouseReleased(MouseEvent e) {
			player.mouseReleased(e);
		}	
	}
}	