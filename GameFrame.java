class GameFrame extends JFrame{ 
	GamePanel panel;
	
	public GameFrame(){ 
		super("FruitBlood");
		Container c = getContentPane(); 
		panel = new GamePanel();
		c.add(panel);
		this.setSize(1000,1000);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}