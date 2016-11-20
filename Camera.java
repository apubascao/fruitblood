import java.awt.*; 
import java.awt.event.*; 
import java.io.*;
import java.net.*;
import java.util.*;

import javax.swing.*;

public class Camera {

	private int x, y;

	public Camera(int x, int y){
		this.x = x;
		this.y = y;
	}

	public void setX(int x){
		this.x = x;
	}

	public void setY(int y){
		this.y = y;
	}

	public int getX(){
		return x;
	}

	public int getY(){
		return y;
	}

	public void tick(Player player){
		
		y = player.getDy();
		x = player.getDx();
		


	}

}