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
	int x, dx, y, dy, dd, frameX=800, frameY=600;
	Image playerImage;
	int distance;
 
	public Player() {
		ImageIcon i = new ImageIcon("images/banana.png");
		playerImage = i.getImage();
		x = 400;
		y = 300;
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
 		
	public Image getImage(){
		return playerImage;
	}
	
	public void mousePressed(MouseEvent e){

	}

	public void mouseDragged(MouseEvent e){
		dx = (e.getX() - x)/30;
		dy = (e.getY() - y)/30;

		//System.out.println("x: " + x + " y: " + y);
		//System.out.println("dx: " + dx + " dy: " + dy);
		//System.out.println("dx: " + getDx() + " dy: " + getDy());
	}

	public void mouseReleased(MouseEvent e){
	
	}
}