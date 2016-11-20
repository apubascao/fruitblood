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

public class GamePanel extends JPanel{
	Seed seed;
	GoldenSeed goldenSeed;
	Player player;
	Image background;
	Camera cam;


	MovingAdapter ma = new MovingAdapter();
	int tempX = ((getWidth()-3750)/2)+625;
	int tempY = ((getHeight()-2400)/2)+400;
	int prevX, prevY, z, w;
	int seedX;
	int seedY;
	int gseedX;
	int gseedY;
	JLabel score;
 
	public GamePanel() {
		this.setLayout(null);
		this.setSize(1250, 800);
		this.setBackground(Color.WHITE);
		player = new Player();
		setFocusable(true);
		ImageIcon i = new ImageIcon("images/game.png");
		background = i.getImage();   
		seed = new Seed();	
		goldenSeed = new GoldenSeed();
		cam = new Camera(0, 0);
		
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

		g2d.translate(cam.getX(), cam.getY());
		
		
		g2d.drawImage(background, tempX, tempY, null);
		//System.out.println("dx: " + player.getDx() + " dy: " + player.getDy());
		//System.out.println("tempX = " + tempX + " tempY: " + tempY);

		
			
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
		//g2d.drawImage(player.getImage(), 100, 100, null);

		

		g2d.translate(-cam.getX(), -cam.getY());

		System.out.println(player.getX() + "   " + player.getY());

		tempX = tempX-player.getDx();
		tempY = tempY-player.getDy(); 
	}
 
	protected class MovingAdapter extends MouseAdapter {
		public void mousePressed(MouseEvent e) {
			player.mousePressed(e);
		}
 
		public void mouseMoved(MouseEvent e) {
			player.mouseMoved(e);
			player.move();
			cam.tick(player);
			repaint();
		}

		public void mouseReleased(MouseEvent e) {
			player.mouseReleased(e);
		}	
	}
}	