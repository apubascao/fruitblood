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
		this.setBackground(Color.WHITE);
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

		g2d.drawImage(background, 1000-player.getFrameX(), 1000-player.getFrameY(), null);
		g2d.drawImage(player.getImage(), player.getX(), player.getY(), null);          
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
	}
}	