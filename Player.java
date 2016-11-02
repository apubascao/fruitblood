class Player {
	int x, dx, y, dy, dd, frameLength;
	Image playerImage;
	int distance;
 
	public Player() {
		ImageIcon i = new ImageIcon("images/banana.png");
		playerImage = i.getImage();
		x = 300;
		y = 300;
		frameLength=590;
	}
 
	public void move(){
		y = y + dy;
		x = x + dx;
		//frameLength = frameLength + dy;
	}
 
	public int getX(){
		return x;
	}
 
	public int getY(){
		return y;
	}
 
 	public void setDistance(int distance){
 		this.distance = distance;
 	}
 	
 	public int getDistance(){
 		return this.distance;
 	}
 		
	public Image getImage(){
		return playerImage;
	}
	
	public void mousePressed(MouseEvent e){
		int eX = e.getX();
		int eY = e.getY();

		System.out.println(eX);
		System.out.println(eY);
	}

	public void mouseDragged(MouseEvent e){
		dx = e.getX() - x;
		dy = e.getY() - y;

		System.out.println(dx);
	}
	
}