import java.awt.Image;
import javax.swing.ImageIcon;

public class Seed {
	private Image seedImage;
	private int x;
	private int y;
	
	public Seed(int x, int y){
		this.x = x;
		this.y = y;

		ImageIcon i = new ImageIcon("res/seed.png");
		seedImage = i.getImage();
	}

	public Image getImage(){
		return seedImage;
	}
}