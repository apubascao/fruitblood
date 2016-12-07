import java.awt.*; 
import java.awt.event.*; 
import java.io.*;
import java.net.*;
import java.util.*;

import javax.imageio.*;
import javax.swing.*;

public class GamePanel extends Background {
    private Player player;

    private Image gameBG;

    private JFrame frame;
    
    private JLabel score;

    private HashMap<String, String> players;
 
	private int[][] seedCoordinates;
	
	private String address;
    private int port;    
    private DatagramSocket ds;
	private InetAddress ia;
	
	private MouseMotionHandler mmh;
 
    public GamePanel(Player player, String address, int port) {
        players = new HashMap<String, String>();
        
        this.setLayout(null);
        this.setSize(1250, 800);
        setFocusable(true);

        try {
            // Panel Backgrounds
            gameBG = ImageIO.read(this.getClass().getResource("res/game.png"));
        } catch(IOException ioe) {
            System.out.println("Error while reading image file.");
        }

        this.setImage(new ImageIcon(gameBG));

        this.player = player;
        
        score = new JLabel("SCORE");
        score.setText("SCORE");
        score.setPreferredSize(new Dimension(580,11));
        score.setHorizontalAlignment(JLabel.CENTER);
        score.setBackground(Color.RED);
        add(score);

		
		mmh = new MouseMotionHandler();
		
        addMouseMotionListener(mmh);
		
		seedCoordinates = new int[1250][800];
		
		this.address = address;
		this.port = port;
		
		try{
            ia = InetAddress.getByName(address);
            ds = new DatagramSocket();
        } catch (SocketException se) {
            System.out.println(se);
        } catch (IOException ie) {
            System.out.println(ie);
        }
    }
    
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;     
		
		for(int i = 0; i < 1250; i++){
			for(int j = 0; j < 800; j++){
				if(seedCoordinates[i][j] == 1){
                    Seed seed = new Seed(i, j);
					g2d.drawImage(seed.getImage(), i, j, 25, 25, null);
				} else if (seedCoordinates[i][j] == 2){
					GoldenSeed goldenSeed = new GoldenSeed(i, j);
					g2d.drawImage(goldenSeed.getImage(), i, j, 25, 25, null);
				}
			}
		}

        // render player's score
        g.drawString(player.getScore() + "", 50, 50);

        //render all players
        for (Object value : players.values()) {
            String data = value + "";
            
            String substring[] = data.split(",");

            String username = substring[1].trim();      
            String fruitChoice = substring[2].trim();
            int size = Integer.parseInt(substring[3].trim());
            int x = Integer.parseInt(substring[4].trim());
            int y = Integer.parseInt(substring[5].trim());
			int score = Integer.parseInt(substring[6].trim());
                        
            ImageIcon image = new ImageIcon(this.getClass().getResource("res/fruit" + fruitChoice + ".png"));
            g2d.drawImage(image.getImage(), x, y, size, size, this);
            g.drawString(username, x - 20, y + 30); 
            g.drawString(size + "", x + 10, y - 10);			
        } 
    }

    //String data = username + "," + fruitChoice + "," + size + "," + x + "," + y;
    public void paintPlayer(String data){
        String substring[] = data.split(",");
        String username = substring[1];
        players.put(username, data);

        repaint();
    }
	
	public void paintSeed(String data){
		String substring[] = data.split(",");
		
		int x = Integer.parseInt(substring[1].trim());
		int y = Integer.parseInt(substring[2].trim());

		seedCoordinates[x][y] = -1;
		
		x = Integer.parseInt(substring[3].trim());
		y = Integer.parseInt(substring[4].trim());
		int type = Integer.parseInt(substring[5].trim());
		
		seedCoordinates[x][y] = type;

        repaint();
	}

    class MouseMotionHandler extends MouseMotionAdapter {
        public void mouseMoved(MouseEvent me) {
            player.setX(me.getX());
            player.setY(me.getY());
			
			// eating an opponent
            Object[] pks = players.keySet().toArray();
            for(int i = 0; i < pks.length; i++){
				String data = players.get(pks[i] + "");
				
				String substring[] = data.split(",");

				String username = substring[1].trim();      
				String fruitChoice = substring[2].trim();
				int size = Integer.parseInt(substring[3].trim());
				int x = Integer.parseInt(substring[4].trim());
				int y = Integer.parseInt(substring[5].trim());
				int score = Integer.parseInt(substring[6].trim());
				int life = Integer.parseInt(substring[7].trim());

                if (player.getUsername().equals(username)) continue;
			
				//if they are on the same location
				
				byte buffer[];
				String toSend;						
				DatagramPacket packet;

                Rectangle player1 = new Rectangle(player.getX(), player.getY(), player.getFruitSize(), player.getFruitSize());       
                Rectangle player2 = new Rectangle(x, y, size, size);		
				
				try {
					if (overlap(player1, player2)) { // Check if the two players overlap		
						
						if (player.getFruitSize() > size) {   // Compare if player is bigger than opponent
							buffer = new byte[256];
							toSend = "ateopponent," + player.getUsername(); 
							buffer = toSend.getBytes();
							packet = new DatagramPacket(buffer, buffer.length, ia, port);
							ds.send(packet);
							
							buffer = new byte[256];
							toSend = "eaten," + username;
							buffer = toSend.getBytes();
							packet = new DatagramPacket(buffer, buffer.length, ia, port);
							ds.send(packet);

							players.remove(username);
						} else if (player.getFruitSize() < size) {   // Compare if player is smaller than opponent		
							buffer = new byte[256];
							toSend = "ateopponent," + username; 
							buffer = toSend.getBytes();
							packet = new DatagramPacket(buffer, buffer.length, ia, port);
							ds.send(packet);
							
							buffer = new byte[256];
							toSend = "eaten," + player.getUsername(); 
							buffer = toSend.getBytes();
							packet = new DatagramPacket(buffer, buffer.length, ia, port);
							ds.send(packet);
						} 
					}
				} catch (SocketException se) {
					System.out.println(se);
				} catch (IOException ie) {
					System.out.println(ie);
				}								
			}			

            player.moveOutgoing();
        }
    }

    // Check if the two players overlap
    private boolean overlap(Rectangle playerBounds, Rectangle opponentBounds) {
        return opponentBounds.x < playerBounds.x + playerBounds.width && 
            opponentBounds.x + opponentBounds.width > playerBounds.x && 
            opponentBounds.y < playerBounds.y + playerBounds.height && 
            opponentBounds.y + opponentBounds.height > playerBounds.y;
    }
	
	public boolean respawnOverlap(int check_x, int check_y, int check_size){		

		for (Object value : players.values()) {
			String data = value + "";
			
			String substring[] = data.split(",");

			int size = Integer.parseInt(substring[3].trim());
			int x = Integer.parseInt(substring[4].trim());
			int y = Integer.parseInt(substring[5].trim());			

			Rectangle player1 = new Rectangle(check_x, check_y, check_size, check_size);       
			Rectangle player2 = new Rectangle(x, y, size, size);		
			
			if(overlap(player1, player2)){
				return true;
			}
		}

		return false;
	}
	
	public void initialSeeds(String data){
		String[] dataArray = data.split(",");
				
		for(int i = 0; i < dataArray.length; i = i + 3){
			int x = Integer.parseInt(dataArray[i].trim());
			int y = Integer.parseInt(dataArray[i+1].trim());
			int type = Integer.parseInt(dataArray[i+2].trim());			
						
			seedCoordinates[x][y] = type;
		}
	}
	
	public void removeListener(){
		this.removeMouseMotionListener(mmh);
	}
	
	public void dead(String data){
		String[] dataArray = data.split(",");
		String username = dataArray[1].trim();		
		players.remove(username);		
		
		repaint();
		
		//player wins by eating all opponents
		if(players.size() == 1){
			Object value = players.values();
			
			data = value + "";				
			String substring[] = data.split(",");
			username = substring[1].trim();      
			String fruitChoice = substring[2].trim();			
			
			//to avoid duplicate packets
			try{
				if(username.equals(player.getUsername())){
					byte buffer[] = new byte[256];              
					String toSend = "win," + username + "," + fruitChoice; 
					buffer = toSend.getBytes();       
					DatagramPacket packet = new DatagramPacket(buffer, buffer.length, ia, port);
					ds.send(packet);
				}				
			} catch (SocketException se) {
				System.out.println(se);
			} catch (IOException ie) {
				System.out.println(ie);
			}						
		}
	}
}
