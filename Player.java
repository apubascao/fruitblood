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

class Player {
	int x, dx, y, dy, dd, frameX, frameY;
	Image playerImage;
	int distance;
 
	public Player() {
		ImageIcon i = new ImageIcon("images/banana.png");
		playerImage = i.getImage();
		x = 500;
		y = 280;
		frameX = 1000;
		frameY = 1000;
	}
 
	public void move(){
		y = y;
		x = x;
		frameX = frameX + dx/2;
		frameY = frameY + dy/2;

		System.out.println("frame y = " + frameY);
	}
 
	public int getX(){
		return x;
	}
 
	public int getY(){
		return y;
	}
 
	public int getFrameX(){
		return frameX;
	}
 
	public int getFrameY(){
		return frameY;
	}
 		
	public Image getImage(){
		return playerImage;
	}
	
	public void mousePressed(MouseEvent e){
		int eX = e.getX();
		int eY = e.getY();
	}

	public void mouseDragged(MouseEvent e){
		dx = e.getX() - x;
		dy = e.getY() - y;

		System.out.println(dx);
	}
	
}