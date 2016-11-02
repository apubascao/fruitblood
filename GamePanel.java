class GamePanel extends JPanel{
	Player player;
	Image background;
	MovingAdapter ma = new MovingAdapter();
 
	public GamePanel() {
		this.setBackground(Color.WHITE);
		player = new Player();
		setFocusable(true);
		ImageIcon i = new ImageIcon("images/field.png");
		background = i.getImage();          	

		addMouseMotionListener(ma);
   		addMouseListener(ma);    
   	}
    
	public void paint(Graphics g) {
		super.paint(g);
		Graphics2D g2d = (Graphics2D) g;

		g2d.drawImage(background, 590-player.frameLength, 590-player.frameLength, null);
		g2d.drawImage(player.getImage(), player.getX(), player.getY(), null);          
	}
 
	protected class MovingAdapter extends MouseAdapter {
		public void mousePressed(MouseEvent e) {
			player.mousePressed(e);
			}
 
		public void mouseDragged(MouseEvent e) {
			player.mouseDragged(e);

			player.move();
			repaint();
			}
	}
}	