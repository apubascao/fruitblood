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

public class FruitBlood{ 
	public static void main(String[] args){
		GameFrame frame = new GameFrame(); 
		frame.setVisible(true);
		frame.setLocationRelativeTo(null);
	}
} //end of class ClimbingCar
//--------------------------------------------------------------------------
class GameFrame extends JFrame{ 
	GamePanel panel;
	
	public GameFrame(){ 
		super("FruitBlood");
		Container c = getContentPane(); 
		panel = new GamePanel();
		c.add(panel);
		this.setSize(1000,1000);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
} //end of class GameFrame
//--------------------------------------------------------------------------
class GamePanel extends JPanel{
	Player player;
	Image background;
	//Timer time;  
	MovingAdapter ma = new MovingAdapter();
 
	public GamePanel() {
		
		this.setBackground(Color.CYAN);
		player = new Player();
		setFocusable(true);
		ImageIcon i = new ImageIcon("images/field.png");
		background = i.getImage();
		//time = new Timer(10, this);
		//time.start();              	

		addMouseMotionListener(ma);
   		addMouseListener(ma);
    	//addMouseWheelListener(new ScaleHandler());
    }
    
	public void paint(Graphics g) {
		super.paint(g);
		Graphics2D g2d = (Graphics2D) g;
 
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    	g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
        RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

		g2d.drawImage(background, 590-player.frameLength, 590-player.frameLength, null);
		g2d.drawImage(player.getImage(), player.getX(), player.getY(), null);          
	}

	/*public void actionPerformed(ActionEvent e) {
		player.move();
		
		repaint();
	}*/
 
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
//--------------------------------------------------------------------------
class Player {
	int x, dx, y, dy, dd, frameLength;
	Image playerImage;
	int distance;
 
	public Player() {
		ImageIcon i = new ImageIcon("images/banana.png");
		playerImage = i.getImage();
		x = 500;
		y = 500;
		frameLength=590;
	}
 
	public void move(){
		y = y + dy;
		x = x + dx;
		//frameLength = frameLength + dy;
	}
 
	public int getX(){
		return x;
	}
 
	public int getY(){
		return y;
	}
 
 	public void setDistance(int distance){
 		this.distance = distance;
 	}
 	
 	public int getDistance(){
 		return this.distance;
 	}
 		
	public Image getImage(){
		return playerImage;
	}
	
	public void mousePressed(MouseEvent e){
		int eX = e.getX();
		int eY = e.getY();

		System.out.println(eX);
	}

	public void mouseDragged(MouseEvent e){
		dx = e.getX() - x;
		dy = e.getY() - y;

		System.out.println(dx);
	}
	
}//end of class Car
//--------------------------------------------------------------------------