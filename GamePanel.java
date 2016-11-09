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

class GamePanel extends JPanel{
	Player player;
	Image background;
	MovingAdapter ma = new MovingAdapter();
 
	public GamePanel() {
		this.setBackground(Color.CYAN);
		player = new Player();
		setFocusable(true);
		ImageIcon i = new ImageIcon("images/field.png");
		background = i.getImage();          	

		addMouseMotionListener(ma);
   		addMouseListener(ma);    
   	}
    
	public void paint(Graphics g) {
		super.paint(g);
		Graphics2D g2d = (Graphics2D) g;


		//getWidth() and getHeight() return the size of the game frame
		int tempX = ((getWidth()-4000)/2)+player.dx;
		int tempY = ((getHeight()-4000)/2)+player.dy;
		//4000 is the width and height of the background image
		g2d.drawImage(background, tempX+player.getDx(), tempY+player.getDy(), null);
		System.out.println("drawX = " + tempX + " drawY: " + tempY);
		g2d.drawImage(player.getImage(), player.getX(), player.getY(), null);          
	}
 
	protected class MovingAdapter extends MouseAdapter {
		public void mousePressed(MouseEvent e) {
			player.mousePressed(e);
			System.out.println("PRESSED!");
			System.out.println("Current x = " + player.getX() + "Current y = " + player.getY());
		}
 
		public void mouseDragged(MouseEvent e) {
			player.mouseDragged(e);

			if(player.getX()!=0 || player.getX()!=4000 || player.getY()!=0 || player.getY()!=4000){
				player.move();
				System.out.println("Current x = " + player.getX() + "Current y = " + player.getY());
				repaint();

			}else{

			}			
		}
		public void mouseReleased(MouseEvent e) {
			player.mouseReleased(e);
			System.out.println("RELEASED!");
			System.out.println("Current x = " + player.getX() + "Current y = " + player.getY());
		}	
	}
}	