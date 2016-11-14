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
import java.net.*;

public class Player extends JPanel{
	private int x, dx, y, dy, dd, frameX=1250, frameY=800;
	private Image playerImage;
	private int tempX = ((1250-3750)/2);
	private int tempY = ((800-2400)/2);
	private JLabel name;

	private int height;
	private int width;
	private int size;

	private DatagramSocket ds;

	private String address;
	private int port;
	private String username;
	private int fruitChoice;
	private int score;
 
	public Player(String address, int port, String username, int fruitChoice) {
		x = 620;
		y = 300;
		name = new JLabel("apple");
		name.setLocation(x, y - 50);
		name.setVisible(true);
		add(name);

		size = 1;
		height = 50;
		width = 50;
		score = 0;

		

		this.address = address;
		this.port = port;
		this.username = username;
		this.fruitChoice = fruitChoice;

		ImageIcon i = new ImageIcon("res/fruit" + fruitChoice + ".png");
		playerImage = i.getImage().getScaledInstance(width, height, Image.SCALE_DEFAULT);
	}
 
	public void move(){
		frameX = frameX + dx;
		frameY = frameY + dy;
	}

	public void setX(int x){
		this.x = x + dx;
	}

	public void setY(int y){
		this.y = y + dy;

	}
 
	public int getX(){
		return x;
	}
 
	public int getY(){
		return y;
	}
 
	public int getDx(){
		return dx;
	}
 
	public int getDy(){
		return dy;
	}

	public String getUsername(){
		return username;
	}

	public int getFruitSize(){
		return size;
	}
 		
	public Image getImage(){
		return playerImage;
	}
	
	public void mousePressed(MouseEvent e){

	}

	public void mouseMoved(MouseEvent e){
		dx = (e.getX() - x) / 60;
		dy = (e.getY() - y) / 60;		
	}

	public void mouseReleased(MouseEvent e){
	
	}
}