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
    }
    
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;        
            
        for(int i = 0; i < 20; i++){
            int seedX = seed.getX(i);
            int seedY = seed.getY(i);
            g2d.drawImage(seed.getImage(), seedX, seedY, null);    
        }

        for(int i = 0; i < 5; i++){
            int gseedX = goldenSeed.getX(i);
            int gseedY = goldenSeed.getY(i);
            g2d.drawImage(goldenSeed.getImage(), gseedX, gseedY, null);    
        }

        g2d.drawImage(player.getImage(), player.getX(), player.getY(), null);

        //render all opponents
        System.out.println(players.keySet());
        for (Object value : players.values()) {
            String data = value + "";
            
            System.out.println("data = " + data);
            
            String substring[] = data.split(",");

            String username = substring[0].trim();      
            String fruitChoice = substring[1].trim();
            int size = Integer.parseInt(substring[2].trim());
            int x = Integer.parseInt(substring[3].trim());
            int y = Integer.parseInt(substring[4].trim());
                        
            System.out.println("fruitChoice = " + fruitChoice);
                        
            ImageIcon image = new ImageIcon(this.getClass().getResource("res/fruit" + fruitChoice + ".png"));
            g.drawImage(image.getImage(), x, y, 50, 50,this);
        } 
    }

    //String data = username + "," + fruitChoice + "," + size + "," + dx + "," + dy;
    public void paintPlayer(String data){
        String substring[] = data.split(",");
        String username = substring[0];
        players.put(username, data);

        repaint();
    }

    class MouseMotionHandler extends MouseMotionAdapter {
        public void mouseMoved(MouseEvent me) {
            player.setX(me.getX());
            player.setY(me.getY());

            player.moveOutgoing();
        }
    }
}
