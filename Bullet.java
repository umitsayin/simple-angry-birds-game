package angryBirds;

public class Bullet {
	
	private int x;
	private int y;
	private int weight;
	
	public Bullet(int x,int y,int weight) {
		this.x=x;
		this.y=y;
		this.weight=weight;
	}
	
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int d) {
		this.y = d;
	}
	public int getWeight() {
		return weight;
	}
	public void setWeight(int weight) {
		this.weight = weight;
	}
	
	
	
}
