import java.awt.Color;
import java.awt.Graphics;


public class Explode {
	private int x ,y ;
	private Boolean alive = true;
	private TankClient tankClient;
	
	private int[] dimension = {6,12,18,24,32,40,30,15,8};
	private int step=0;
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	public Boolean getAlive() {
		return alive;
	}
	public void setAlive(Boolean alive) {
		this.alive = alive;
	}
	
	public Explode(int x,int y,TankClient tankClient){
		this.x = x ;
		this.y = y ;
		this.tankClient = tankClient;
	}
	
	void draw(Graphics g){
		if(!this.alive){
			return;
		}
		if(step==dimension.length){
			this.alive = false;
			step =0;
			return;
		}
		Color color = g.getColor();
		g.setColor(Color.red);
		g.fillOval(x, y, dimension[step], dimension[step]);
		g.setColor(color);
		step++;
	}
}
