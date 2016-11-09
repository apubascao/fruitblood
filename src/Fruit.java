import javax.swing.*;
import javax.imageio.*;
import java.awt.*;

public class Fruit extends JPanel {
	private Image bg;

	public void setImage(ImageIcon icon) {
		bg = icon.getImage().getScaledInstance(500, 500, Image.SCALE_DEFAULT);
		repaint();
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(bg, 0, 0, getWidth(), getHeight(), null);
	}
	
}