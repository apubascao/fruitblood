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
	MovingAdapter ma = new MovingAdapter();
	int tempX = ((getWidth()-3750)/2)+625;
	int tempY = ((getHeight()-2400)/2)+400;
	int prevX, prevY, z, seedX, seedY, gseedX, gseedY;

 
	public GamePanel() {
		this.setSize(1250, 800);
		this.setBackground(Color.CYAN);
		player = new Player();
		setFocusable(true);
		ImageIcon i = new ImageIcon("images/game.png");
		background = i.getImage();   
		seed = new Seed();	
		goldenSeed = new GoldenSeed();

		addMouseMotionListener(ma);
   		addMouseListener(ma);    
   	}
    
	public void paint(Graphics g) {
		super.paint(g);
		Graphics2D g2d = (Graphics2D) g;
		
		g2d.drawImage(background, tempX, tempY, null);
		System.out.println("dx: " + player.getDx() + " dy: " + player.getDy());
		System.out.println("tempX = " + tempX + " tempY: " + tempY);
			
		g2d.drawImage(player.getImage(), player.getX(), player.getY(), null);
		    

		for(z=0; z<200;z++){
			seedX = seed.getX(z);
			seedY = seed.getY(z);
			g2d.drawImage(seed.getImage(), seedX, seedY, null);    
		}

		for(z=0; z<20;z++){
			gseedX = goldenSeed.getX(z);
			gseedY = goldenSeed.getY(z);
			g2d.drawImage(goldenSeed.getImage(), gseedX, gseedY, null);    
		}

		gseedX = gseedX+player.getDx();
		gseedY = gseedY+player.getDy();
		seedX = seedX+player.getDx();
		seedY = seedY+player.getDy();
		tempX = tempX+player.getDx();
		tempY = tempY+player.getDy();  

	}
 
	protected class MovingAdapter extends MouseAdapter {
		public void mousePressed(MouseEvent e) {
			player.mousePressed(e);
		}
 
		public void mouseDragged(MouseEvent e) {
			player.mouseDragged(e);
			player.move();
			repaint();
		}

		public void mouseReleased(MouseEvent e) {
			player.mouseReleased(e);
		}	
	}
}	