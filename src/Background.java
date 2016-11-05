import javax.swing.*;
import javax.imageio.*;
import java.awt.*;

public class Background extends JPanel {
	private Image bg;

	public void setImage(ImageIcon icon) {
		bg = icon.getImage().getScaledInstance(1250, 800, Image.SCALE_DEFAULT);
		repaint();
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(bg, 0, 0, getWidth(), getHeight(), null);
	}
	
}