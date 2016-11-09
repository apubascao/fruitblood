<<<<<<< HEAD
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
=======
class Player {
	int x, dx, y, dy, dd, frameLength;
>>>>>>> 6a84c43b1945102695ad007e75774b30dfcdab9f
	Image playerImage;
	int distance;
 
	public Player() {
		ImageIcon i = new ImageIcon("images/banana.png");
		playerImage = i.getImage();
<<<<<<< HEAD
		x = 400;
		y = 300;
		/*frameX = 800;
		frameY = 600;*/
	}
 
	public void move(){
		//y = this.getY();
		//x = this.getX();
		frameX = frameX + dx;
		frameY = frameY + dy;

		//System.out.println("Current x = " + frameX + "Current y = " + frameY);
	}

	//set new values of x and y
	public void setX(int x){
		this.x = x + dx;
	}

	public void setY(int y){
		this.y = y + dy;
=======
		x = 300;
		y = 300;
		frameLength=590;
	}
 
	public void move(){
		y = y + dy;
		x = x + dx;
		frameLength = frameLength + dy;
>>>>>>> 6a84c43b1945102695ad007e75774b30dfcdab9f
	}
 
	public int getX(){
		return x;
	}
 
	public int getY(){
		return y;
	}
 
<<<<<<< HEAD
	public int getDx(){
		return dx;
	}
 
	public int getDy(){
		return dy;
	}
=======
 	public void setDistance(int distance){
 		this.distance = distance;
 	}
 	
 	public int getDistance(){
 		return this.distance;
 	}
>>>>>>> 6a84c43b1945102695ad007e75774b30dfcdab9f
 		
	public Image getImage(){
		return playerImage;
	}
	
	public void mousePressed(MouseEvent e){
<<<<<<< HEAD
		//int eX = e.getX();
		//int eY = e.getY();
		//System.out.println("Current ex: " + eX + "Current ey: " + eY);
=======
		int eX = e.getX();
		int eY = e.getY();
>>>>>>> 6a84c43b1945102695ad007e75774b30dfcdab9f
	}

	public void mouseDragged(MouseEvent e){
		dx = e.getX() - x;
		dy = e.getY() - y;
<<<<<<< HEAD
		//setX(dx+x);
		//setY(dy+y);

		System.out.println("x: " + x + " y: " + y);
		System.out.println("dx: " + dx + " dy: " + dy);
		System.out.println("dx: " + getDx() + " dy: " + getDy());
	}

	public void mouseReleased(MouseEvent e){
		//x = x + dx;
		//y = y + dy;

		
	}
	

=======

		System.out.println(dx);
	}
	
>>>>>>> 6a84c43b1945102695ad007e75774b30dfcdab9f
}