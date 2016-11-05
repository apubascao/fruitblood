public class Server {
	public static void main(String args[]){
		ServerClass server = new ServerClass();
		Thread t = new Thread(server);
		t.start();
	}
}