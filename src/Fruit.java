import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JPanel;
import javax.swing.ImageIcon;

public class Fruit extends JPanel {
    private Image bg;

    public void setImage(ImageIcon icon) {
        bg = icon.getImage().getScaledInstance(460, 460, Image.SCALE_DEFAULT);
        repaint();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(bg, 0, 0, getWidth(), getHeight(), null);
    }
    
}