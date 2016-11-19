import java.awt.*; 
import java.awt.event.*; 
import java.io.*;
import java.net.*;
import java.util.*;

import javax.swing.*;

public class Player extends JPanel{
	private Image playerImage;
	private JLabel name;

	private DatagramSocket ds;
	private InetAddress ia;

	private int x, y;
	private int size;
	private int port;
	private int fruitChoice;
	private int score;
	private int life;
	private int playerSocket;

	private String address;
	private String username;
	
 
	public Player(String address, int port, String username, int fruitChoice) {	
		this.address = address;
		this.port = port;
		this.username = username;
		this.fruitChoice = fruitChoice;

		size = 50;
		score = 0;
		life = 3;

		try{
            ia = InetAddress.getByName(address);
            ds = new DatagramSocket();
        } catch (SocketException se) {
            System.out.println("player1" + se);
        } catch (UnknownHostException ue) {
            System.out.println();
        }   

		ImageIcon i = new ImageIcon("res/fruit" + fruitChoice + ".png");
		playerImage = i.getImage().getScaledInstance(size, size, Image.SCALE_DEFAULT);

		Random rn = new Random();
        playerSocket = rn.nextInt(65535 - 1024 + 1) + 1024;
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

	public int getScore() {
		return score;
	}

	public int getLife() {
		return life;
	}

	public String getAddress() {
		return address;
	}

	public int getPort() {
        return port;
    }

	public int getPlayerSocket() {
        return playerSocket;
    }

    public void moveOutgoing() {
		try {           
            byte buffer[] = new byte[256];
            String data = "playermove," + username + "," + fruitChoice + "," + 
                            size + "," + x + "," + y;
            buffer = data.getBytes();
                    
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length, ia, port);
            ds.send(packet);            
        } catch (SocketException se) {
            System.out.println(se);
        } catch (UnknownHostException ue) {
            System.out.println(ue);
        } catch (IOException ioe) {
            System.out.println(ioe);
        }
    }

    public void sendPort(){
        //INITIAL SEND TO SERVER TO GET THE PORT
        try {
            byte buffer[] = new byte[256];              
            String data = "sendport," + username + "," + playerSocket;
            buffer = data.getBytes();       
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length, ia,port);
            ds.send(packet);
        } catch (SocketException se) {
            System.out.println(se);
        } catch (UnknownHostException ue) {
            System.out.println();
        } catch (IOException ioe) {
        
        }
    }
	
	public void ateSeed(){
		System.out.println("wooo ate a seed");
	}
}
