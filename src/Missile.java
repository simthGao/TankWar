import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.List;

import org.omg.CORBA.TCKind;

public class Missile {
	private static final int XSPEED = 15;
	private static final int YSPEED = 15;
	private static final int MISSILE_WIDTH = 10;
	private static final int MISSILE_HEIGTH = 10;
	private int x, y;
	TankClient tankClient;
	private Boolean missileAlive = false;
	Tank.Belong belong;
	

	public Missile(int x, int y, Tank.Direction direction) {
		this.x = x;
		this.y = y;
		this.missileDirection = direction;
	}

	public Missile(int x, int y, Tank.Direction direction, TankClient tankClient) {
		this(x, y, direction);
		this.tankClient = tankClient;
	}
	
	public Missile(int x,int y ,Tank.Direction direction,TankClient tankClient,Tank.Belong belong){
		this(x, y, direction, tankClient);
		this.belong = belong;
	}



	public Boolean getMissileAlive() {
		if (this.x < 0 || this.y < 0 || this.x > TankClient.getScreeWidth()
				|| this.y > TankClient.getScreeHight()) {
			this.die();
			// tankClient.missiles.remove(this); 
		}
		return missileAlive;
	}

	public void setMissileAlive(Boolean missileAlive) {
		this.missileAlive = missileAlive;
	}

	public static int getMissileWidth() {
		return MISSILE_WIDTH;
	}

	public static int getMissileHeigth() {

		return MISSILE_HEIGTH;
	}

	Tank.Direction missileDirection;

	public void draw(Graphics g) {
		if(!missileAlive)return;
		
		Color color = g.getColor();
		g.setColor(Color.BLACK);
		g.fillOval(x, y, MISSILE_WIDTH, MISSILE_HEIGTH);
		g.setColor(color);

		move();
	}

	void die() {
		this.setMissileAlive(false);
	}

	private void move() {
		switch (missileDirection) {
		case L:
			x -= XSPEED;
			break;
		case LU:
			x -= XSPEED;
			y -= YSPEED;
			break;
		case U:
			y -= YSPEED;
			break;
		case RU:
			x += XSPEED;
			y -= YSPEED;
			break;
		case R:
			x += XSPEED;
			break;
		case RD:
			x += XSPEED;
			y += YSPEED;
			break;
		case D:
			y += YSPEED;
			break;
		case LD:
			x -= XSPEED;
			y += YSPEED;
			break;

		}
	}

	public Rectangle getRect(){
		return new Rectangle(this.x,this.y,MISSILE_WIDTH,MISSILE_HEIGTH);
	}
	
	public Boolean hitTank(Tank tank){
		//identify that missile is intersected with enemyTank and enemyTank is alive, if enemyTank is not alive , missile can still alive!
		if(tank.belong.equals(this.belong))return false;
//		if(this.getRect().intersects(tankClient.myTank.getRect())&&tankClient.myTank.getAlive()){
//			tankClient.myTank.setAlive(false);
//			Explode explode = new Explode(x, y, tankClient);
//			tankClient.explodes.add(explode);
//			missileAlive = false;
//			return true;
//		}//how to kill the main tank!
		if(this.missileAlive&&this.getRect().intersects(tank.getRect())&&tank.getAlive())
		{
		
			tank.setAlive(false);
			Explode explode = new Explode(x, y, tankClient);
			tankClient.explodes.add(explode);
			missileAlive = false;
			return true;
			}
		
	
		return false;
	}
	public Boolean hitTanks(List<Tank> tanks){
		for(int i = 0;i<tanks.size();i++){
			if(hitTank(tanks.get(i))){
				return true;
			}
		}
		return false;
	}
}
