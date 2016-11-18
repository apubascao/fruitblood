import java.awt.*; 
import java.awt.event.*; 
import javax.swing.*;
import java.util.Random;

public class GoldenSeed {
	private Image goldenImage;
	private int z;
	private int[] x = new int[200];
	private int[] y = new int[200];
	
	public GoldenSeed(){
		Random randX = new Random();
		Random randY = new Random();
		ImageIcon i = new ImageIcon("res/gold.png");		
		goldenImage = i.getImage();

		for(z=0; z<200; z++){
			x[z] = randX.nextInt(1250);
			y[z] = randY.nextInt(800);
		}
	}

	public int getX(int z){
		return x[z];
	}
 
	public int getY(int z){
		return y[z];
	}

	public Image getImage(){
		return goldenImage;
	}
}