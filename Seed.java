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
import java.util.Random;

public class Seed {
	Image seedImage;
	int z;
	int[] x = new int[200];
	int[] y = new int[200];
	
	public Seed(){
		Random randX = new Random();
		Random randY = new Random();
		ImageIcon i = new ImageIcon("images/seed.png");
		seedImage = i.getImage();
		
		for(z=0; z<200; z++){
			x[z] = randX.nextInt(11 - -2434)+11;
			y[z] = randY.nextInt(-87 - -1637)+ -87;
		}
	}

	public int getX(int z){
		return x[z];
	}
 
	public int getY(int z){
		return y[z];
	}

	public Image getImage(){
		return seedImage;
	}

}