import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.util.List;
import java.util.Random;

public class Tank {
	private static final int XSPEED = 5;
	private static final int YSPEED = 5;
	private static final int TANK_WIDTH = 30;
	enum Belong{
		good,bad
	};
	Belong belong;
	private static final int TANK_HEIGHT = 30;
	private int x, y;
	private TankClient tankClient;

	private Boolean bL = false, bU = false, bR = false, bD = false;
	private Boolean alive = true;
	private static Random random = new Random();
	private int stepBanlance = random.nextInt(15)+3;
	private int fireBanlance = random.nextInt(5)+3;
	

	enum Direction {
		L, LU, U, RU, R, RD, D, LD, STOP
	};

	Direction gunDirection = Direction.D;

	private Direction tankDirection = Direction.STOP;

	public Tank(int x, int y, TankClient tankClient) {
		this.x = x;
		this.y = y;
		this.tankClient = tankClient;
	}


	public Tank(int x, int y,TankClient tankClient,Belong belong) {
		this(x, y, tankClient);
		this.belong = belong;
	}
	public static int getTankWidth() {
		return TANK_WIDTH;
	}

	public static int getTankHeight() {
		return TANK_HEIGHT;
	}
	public Direction getDir() {
		return tankDirection;
	}
	public Boolean getAlive() {
		return alive;
	}

	public void setAlive(Boolean alive) {
		this.alive = alive;
	}
	public void setDir(Direction dir) {
		this.tankDirection = dir;
	}

	public void draw(Graphics g) {
	
		if(!this.alive)return ;
		Color c = g.getColor();
		if (belong.equals(Tank.Belong.good))
			g.setColor(Color.RED);
		
		else {
			g.setColor(Color.BLUE);
			enemyDir();
			fire();
		}
		g.fillOval(x, y, TANK_WIDTH, TANK_HEIGHT);
		g.setColor(c);
		drawGun(g);
		move();
	}

		
	void enemyDir(){
	
		if(stepBanlance == 0){
			stepBanlance = random.nextInt(20)+3;
			int key = random.nextInt(8);
			switch (key) {
			case 0:
				tankDirection = Direction.L;
				gunDirection = Direction.L;
				
				break;
			case 1:
				tankDirection = Direction.LU;
				gunDirection = Direction.LU;
				break;
			case 2:
				tankDirection = Direction.LD;
				gunDirection = Direction.LD;
				break;
			case 3:
			tankDirection = Direction.R;
				gunDirection = Direction.R;
				break;
			case 4:
				tankDirection= Direction.RU;
				gunDirection= Direction.RU;
				break;
			case 5:
				tankDirection = Direction.RD;
				gunDirection = Direction.RD;
				break;
			case 6:
				tankDirection = Direction.D;
				gunDirection = Direction.D;
				break;
			case 7:
				tankDirection= Direction.U;
				gunDirection= Direction.U;
				break;

			default:
				break;
			}
		}
		
		stepBanlance--;
		
	}

	void drawGun(Graphics g) {
		if (bL && !bU && !bR && !bD) {
			Color color = g.getColor();
			g.setColor(Color.darkGray);
			g.drawLine(this.x + Tank.TANK_WIDTH / 2, this.y + Tank.TANK_HEIGHT
					/ 2, this.x - 20, this.y + Tank.TANK_HEIGHT / 2);

			this.gunDirection = Direction.L;
			g.setColor(color);
		} else if (bL && bU && !bR && !bD) {
			Color color = g.getColor();
			g.setColor(Color.darkGray);
			g.drawLine(this.x + Tank.TANK_WIDTH / 2, this.y + Tank.TANK_HEIGHT
					/ 2, this.x - 15, this.y - 15);
			this.gunDirection = Direction.LU;
			g.setColor(color);
		} else if (!bL && bU && !bR && !bD) {
			Color color = g.getColor();
			g.setColor(Color.darkGray);
			g.drawLine(this.x + Tank.TANK_WIDTH / 2, this.y + Tank.TANK_HEIGHT
					/ 2, this.x + Tank.TANK_WIDTH / 2, this.y - 20);
			this.gunDirection = Direction.U;
			g.setColor(color);
		} else if (!bL && bU && bR && !bD) {
			Color color = g.getColor();
			g.setColor(Color.darkGray);
			g.drawLine(this.x + Tank.TANK_WIDTH / 2, this.y + Tank.TANK_HEIGHT
					/ 2, this.x + Tank.TANK_WIDTH + 15, this.y
					- Tank.TANK_HEIGHT / 2 + 15);
			this.gunDirection = Direction.RU;
			g.setColor(color);
		} else if (!bL && !bU && bR && !bD) {
			Color color = g.getColor();
			g.setColor(Color.darkGray);
			g.drawLine(this.x + Tank.TANK_WIDTH / 2, this.y + Tank.TANK_HEIGHT
					/ 2, this.x + Tank.TANK_WIDTH + 20, this.y
					+ Tank.TANK_HEIGHT / 2);
			this.gunDirection = Direction.R;
			g.setColor(color);
		} else if (!bL && !bU && bR && bD) {
			Color color = g.getColor();
			g.setColor(Color.darkGray);
			g.drawLine(this.x + Tank.TANK_WIDTH / 2, this.y + Tank.TANK_HEIGHT
					/ 2, this.x + Tank.TANK_WIDTH + 15, this.y
					+ Tank.TANK_HEIGHT + 15);
			this.gunDirection = Direction.RD;
			g.setColor(color);
		} else if (!bL && !bU && !bR && bD) {
			Color color = g.getColor();
			g.setColor(Color.darkGray);
			g.drawLine(this.x + Tank.TANK_WIDTH / 2, this.y + Tank.TANK_HEIGHT
					/ 2, this.x + Tank.TANK_WIDTH / 2, this.y
					+ Tank.TANK_HEIGHT + 20);
			this.gunDirection = Direction.D;
			g.setColor(color);
		} else if (bL && !bU && !bR && bD) {
			Color color = g.getColor();
			g.setColor(Color.darkGray);
			g.drawLine(this.x + Tank.TANK_WIDTH / 2, this.y + Tank.TANK_HEIGHT
					/ 2, this.x - 15, this.y + Tank.TANK_HEIGHT + 15);
			this.gunDirection = Direction.LD;
			g.setColor(color);
		}

	}

	void move() {
		switch (tankDirection) {
		case L:
			x -= XSPEED;
			if ((x - TANK_WIDTH) < 0)
				x = 0; 
			break;
		case LU:
			x -= XSPEED;
			y -= YSPEED;
			if ((x - TANK_WIDTH) < 0)
				x = TANK_WIDTH;
			if ((y - TANK_HEIGHT) < 0)
				y = TANK_HEIGHT;
			break;
		case U:
			y -= YSPEED;
			if ((y - TANK_HEIGHT) < 0)
				y = TANK_HEIGHT;
			break;
		case RU:
			x += XSPEED;
			y -= YSPEED;
			if ((x + TANK_WIDTH) > TankClient.getScreeWidth())
				x = TankClient.getScreeWidth() - TANK_WIDTH;
			if ((y - TANK_HEIGHT) < 0)
				y = TANK_HEIGHT;
			break;
		case R:
			x += XSPEED;
			if ((x + TANK_WIDTH) > TankClient.getScreeWidth()) {
				x = TankClient.getScreeWidth() - TANK_WIDTH;
			}

			break;
		case RD:
			x += XSPEED;
			y += YSPEED;
			if ((x + TANK_WIDTH) > TankClient.getScreeWidth())
				x = TankClient.getScreeWidth() - TANK_WIDTH;

			if ((y + TANK_HEIGHT) > TankClient.getScreeHight())
				y = TankClient.getScreeHight() - TANK_HEIGHT;
			break;
		case D:
			y += YSPEED;
			if ((y + TANK_HEIGHT) > TankClient.getScreeHight()) {
				y = TankClient.getScreeHight() - TANK_HEIGHT;
			}
			break;
		case LD:
			x -= XSPEED;
			y += YSPEED;
			if ((x - TANK_WIDTH) < 0)
				x = TANK_WIDTH;
			if ((y + TANK_HEIGHT) > TankClient.getScreeHight())
				y = TankClient.getScreeHight() - TANK_HEIGHT;
			break;
		case STOP:
			break;
		}
	}

	void locateDirection() {
		if (bL && !bU && !bR && !bD)
			tankDirection = Direction.L;
		else if (bL && bU && !bR && !bD)
			tankDirection = Direction.LU;
		else if (!bL && bU && !bR && !bD)
			tankDirection = Direction.U;
		else if (!bL && bU && bR && !bD)
			tankDirection = Direction.RU;
		else if (!bL && !bU && bR && !bD)
			tankDirection = Direction.R;
		else if (!bL && !bU && bR && bD)
			tankDirection = Direction.RD;
		else if (!bL && !bU && !bR && bD)
			tankDirection = Direction.D;
		else if (bL && !bU && !bR && bD)
			tankDirection = Direction.LD;
		else if (!bL && !bU && !bR && !bD)
			tankDirection = Direction.STOP;
	}

	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		switch (key) {

		case KeyEvent.VK_LEFT:
			bL = true;
			break;
		case KeyEvent.VK_UP:
			bU = true;
			break;
		case KeyEvent.VK_RIGHT:
			bR = true;
			break;
		case KeyEvent.VK_DOWN:
			bD = true;
			break;
		}
		locateDirection();
	}

	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();
		switch (key) {
		case KeyEvent.VK_F: // æŠŠæ·»åŠ å­�å¼¹æ”¾åˆ°æ”¾èµ·é”®æ‰�å�‘å‡ºï¼Œä¸ºäº†é�¿å…�ä¸�å�œçš„å�‘å­�å¼¹ï¼Œé€ æˆ�ä¸€æ�¡çº¿
			fire();
			break;
		case KeyEvent.VK_LEFT:
			bL = false;
			break;
		case KeyEvent.VK_UP:
			bU = false;
			break;
		case KeyEvent.VK_RIGHT:
			bR = false;
			break;
		case KeyEvent.VK_DOWN:
			bD = false;
			break;
		case KeyEvent.VK_A:
			for(int i = 0;i< 3;i++){
				//System.out.println((int)Math.random());
				Tank tank =new Tank(new Random().nextInt(TankClient.getScreeWidth()), new Random().nextInt(TankClient.getScreeHight()),tankClient,Tank.Belong.bad);
				tankClient.enemytanks.add(tank);
			}
		}
		locateDirection();
	
	}

	public Missile fire() {
		if(!this.alive)return null;
		if(this.belong.equals(Belong.good)){
			int missile_x = this.x + Tank.TANK_WIDTH / 2
					- Missile.getMissileWidth() / 2;
			int missile_y = this.y + Tank.TANK_HEIGHT / 2
					- Missile.getMissileHeigth() / 2;
			
				
			Missile missile = new Missile(missile_x, missile_y, this.gunDirection,this.tankClient,belong);
			missile.setMissileAlive(true);// å¼€ç�«ä¹‹å�Žè®©å­�å¼¹ç”Ÿæ•ˆ
			tankClient.missiles.add(missile); // æŠŠå­�å¼¹åŠ å…¥å®¹å™¨
			return missile;	
		}
		
		//bad tank's fire banlance
		if(fireBanlance==0&&this.belong.equals(Belong.bad)){
			fireBanlance = random.nextInt(5)+3;
			int missile_x = this.x + Tank.TANK_WIDTH / 2
					- Missile.getMissileWidth() / 2;
			int missile_y = this.y + Tank.TANK_HEIGHT / 2
					- Missile.getMissileHeigth() / 2;
			
				
			Missile missile = new Missile(missile_x, missile_y, this.gunDirection,this.tankClient,belong);
			missile.setMissileAlive(true);// å¼€ç�«ä¹‹å�Žè®©å­�å¼¹ç”Ÿæ•ˆ
			tankClient.missiles.add(missile); // æŠŠå­�å¼¹åŠ å…¥å®¹å™¨
			return missile;	
		}

		fireBanlance--;
		return null;
		
	}
	
	public Rectangle getRect(){
		return new Rectangle(this.x,this.y,this.getTankWidth(),this.getTankHeight());
	}
}
