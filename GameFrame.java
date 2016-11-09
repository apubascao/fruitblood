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

=======
>>>>>>> 6a84c43b1945102695ad007e75774b30dfcdab9f
class GameFrame extends JFrame{ 
	GamePanel panel;
	
	public GameFrame(){ 
		super("FruitBlood");
		Container c = getContentPane(); 
		panel = new GamePanel();
		c.add(panel);
<<<<<<< HEAD
		this.setSize(800,600);
=======
		this.setSize(1000,1000);
>>>>>>> 6a84c43b1945102695ad007e75774b30dfcdab9f
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}