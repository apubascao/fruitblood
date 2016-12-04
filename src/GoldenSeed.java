import java.awt.Image;
import javax.swing.ImageIcon;

public class GoldenSeed {
    private Image goldImage;
    private int x;
    private int y;
    
    public GoldenSeed(int x, int y){
        this.x = x;
        this.y = y;

        ImageIcon i = new ImageIcon("res/gold.png");
        goldImage = i.getImage();
    }

    public Image getImage(){
        return goldImage;
    }
}