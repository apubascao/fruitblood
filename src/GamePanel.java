import java.awt.*; 
import java.awt.event.*; 
import java.io.*;
import java.net.*;
import java.util.*;

import javax.imageio.*;
import javax.swing.*;

public class GamePanel extends Background {
    private Seed seed;
    private GoldenSeed goldenSeed;
    private Player player;

    private Image gameBG;

    private JFrame frame;
    
    private JLabel score;

    private HashMap<String, String> players;
 
	private int[][] seedCoordinates;
 
    public GamePanel(Player player) {
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
        
        seed = new Seed();  
        goldenSeed = new GoldenSeed();
        
        score = new JLabel("SCORE");
        score.setText("SCORE");
        score.setPreferredSize(new Dimension(580,11));
        score.setHorizontalAlignment(JLabel.CENTER);
        score.setBackground(Color.RED);
        add(score);

        addMouseMotionListener(new MouseMotionHandler());
		
		seedCoordinates = new int[1250][800];
    }
    
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;     
		
		for(int i = 0; i < 1250; i++){
			for(int j = 0; j < 800; j++){
				if(seedCoordinates[i][j] == 1){
					g2d.drawImage(seed.getImage(), i, j, 25, 25, null); 
				}
			}
		}

        // render golden seed
		/*
        for(int i = 0; i < 5; i++){
            int gseedX = goldenSeed.getX(i);
            int gseedY = goldenSeed.getY(i);
            g2d.drawImage(goldenSeed.getImage(), gseedX, gseedY, null);    
        }
		*/

        // render player's score
        g.drawString(player.getScore() + "", 50, 50);

        //render all players
        System.out.println(players.keySet());
        for (Object value : players.values()) {
            String data = value + "";
            
            System.out.println("data = " + data);
            
            String substring[] = data.split(",");

            String username = substring[1].trim();      
            String fruitChoice = substring[2].trim();
            int size = Integer.parseInt(substring[3].trim());
            int x = Integer.parseInt(substring[4].trim());
            int y = Integer.parseInt(substring[5].trim());
			int score = Integer.parseInt(substring[6].trim());
                        
            System.out.println("fruitChoice = " + fruitChoice);
                        
            ImageIcon image = new ImageIcon(this.getClass().getResource("res/fruit" + fruitChoice + ".png"));
            g2d.drawImage(image.getImage(), x, y, size, size,this);
            g.drawString(username, x - 20, y + 30); 
            g.drawString(size + "", x + 10, y - 10);
			
        } 
    }

    //String data = username + "," + fruitChoice + "," + size + "," + dx + "," + dy;
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
		
		seedCoordinates[x][y] = 1;

        repaint();
	}

    class MouseMotionHandler extends MouseMotionAdapter {
        public void mouseMoved(MouseEvent me) {
            player.setX(me.getX());
            player.setY(me.getY());

            player.moveOutgoing();
        }
    }
	
	public void initialSeeds(String data){
		String[] dataArray = data.split(",");
		
		System.out.println("DA = " + dataArray.length);
		
		for(int i = 0; i < dataArray.length; i = i + 2){
			int x = Integer.parseInt(dataArray[i].trim());
			int y = Integer.parseInt(dataArray[i+1].trim());			
			
			System.out.println("x y = " +  x + " " + y);
			
			seedCoordinates[x][y] = 1;
		}
	}
}
