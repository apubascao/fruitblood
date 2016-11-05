public class Game {
	public static void main(String args[]){
		FruitBlood fb = new FruitBlood();
		Thread t = new Thread(fb);
		t.start();
	}
}