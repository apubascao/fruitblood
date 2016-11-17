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
import java.io.*;
import java.util.*;

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
	private InetAddress ia;

	private String address;
	private int port;
	private String username;
	private int fruitChoice;
	private int score;
	
	private int playerSocket;
 
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
		
		
		try{
			ia = InetAddress.getByName(address);
			ds = new DatagramSocket();
		} catch (SocketException se) {
			System.out.println("player1" + se);
		} catch (UnknownHostException ue) {
			System.out.println();
		}		

		ImageIcon i = new ImageIcon("res/fruit" + fruitChoice + ".png");
		playerImage = i.getImage().getScaledInstance(width, height, Image.SCALE_DEFAULT);
		
		Random rn = new Random();
		playerSocket = rn.nextInt(65535 - 1024 + 1) + 1024;
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
	
	public int getFruitChoice(){
		return fruitChoice;
	}
 		
	public Image getImage(){
		return playerImage;
	}
	
	public void mousePressed(MouseEvent e){

	}

	public void mouseMoved(MouseEvent e){
		dx = (e.getX() - x) / 60;
		dy = (e.getY() - y) / 60;		
		moveOutgoing();
	}

	public void mouseReleased(MouseEvent e){
	
	}
	
	
	private void moveOutgoing(){
		try {			
			byte buffer[] = new byte[256];
			
			String dx = this.getDx() + "";
			String dy = this.getDy() + "";
			String username = this.getUsername();
			String size = this.getFruitSize() + "";
			String fruitChoice = this.getFruitChoice() + "";
						
			String data = username + "," + fruitChoice + "," + size + "," + dx + "," + dy;
			
			buffer = data.getBytes();
					
			DatagramPacket packet = new DatagramPacket(buffer, buffer.length, ia, port);
			ds.send(packet);			
		} catch (SocketException se) {
			System.out.println(se);
		} catch (UnknownHostException ue) {
			System.out.println();
		} catch (IOException ioe) {

		}
	}
	
	public int getPlayerSocket(){
		return playerSocket;
	}
	
	public void sendPort(){
		//INITIAL SEND TO SERVER TO GET THE PORT
		try {
			byte buffer[] = new byte[256];				
			String data = username + "," + playerSocket;
			buffer = data.getBytes();		
			DatagramPacket packet = new DatagramPacket(buffer, buffer.length, ia, port);
			ds.send(packet);
		} catch (SocketException se) {
			System.out.println(se);
		} catch (UnknownHostException ue) {
			System.out.println();
		} catch (IOException ioe) {
		
		}
	}
	
}