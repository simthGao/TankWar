import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;
import java.util.List;

public class TankClient extends Frame {

	private static final int SCREE_WIDTH = 1024;

	private static final int SCREE_HIGHT = 720;
	Tank myTank = new Tank(50, 50, this,Tank.Belong.good);

	
	Image offScreenImage = null;
	List<Explode> explodes = new ArrayList<Explode>();
	List<Missile> missiles = new ArrayList<Missile>();
	List<Tank> enemytanks = new ArrayList<Tank>();
	public static int getScreeWidth() {
		return SCREE_WIDTH;
	}

	public static int getScreeHight() {
		return SCREE_HIGHT;
	}

	public void paint(Graphics g) {
		Color color = g.getColor();
		g.setColor(Color.ORANGE);
		g.drawString("missile numbers:" + missiles.size(), 60, 60);
		g.drawString("tank number:"+enemytanks.size(), 60, 50);
		g.drawString("explode numbers:"+explodes.size(), 60, 40);
		g.setColor(color);
		for (int i = 0; i < missiles.size(); i++) {
			Missile m = missiles.get(i);
			
			if (m != null && !m.getMissileAlive())// 判断炮弹是否活着
				missiles.remove(i);
			if (m != null && m.getMissileAlive()) // 活着则画出炮弹
				m.hitTanks(enemytanks);
				m.hitTank(myTank);
				m.draw(g);
		}
		for(int i = 0;i<explodes.size();i++){
			Explode explode = explodes.get(i);
			if(explode!=null&&!explode.getAlive()){
				explodes.remove(explode);
			}
			if(explode!=null&&explode.getAlive()){
				explode.draw(g);
			}
		}
		for(int i=0;i<enemytanks.size();i++){
			Tank tank = enemytanks.get(i);
			if(!tank.getAlive()&&tank!=null){
				enemytanks.remove(i);
			}
			if(tank.getAlive()&&tank!=null){
				tank.draw(g);
			
			
			}
		}
		myTank.draw(g);
		
		
	}

	public void update(Graphics g) { // 解决重影问题
		if (offScreenImage == null) {
			offScreenImage = this.createImage(SCREE_WIDTH, SCREE_HIGHT);
		}
		Graphics gOffScreen = offScreenImage.getGraphics();
		Color color = g.getColor();
		gOffScreen.setColor(Color.GREEN);
		gOffScreen.fillRect(0, 0, SCREE_WIDTH, SCREE_HIGHT);
		paint(gOffScreen);
		g.drawImage(offScreenImage, 0, 0, null);
	}

	private void launchFrame() {
		this.setLocation(200, 200);
		this.setSize(SCREE_WIDTH, SCREE_HIGHT);
		this.setTitle("TankWar");
		this.addWindowListener(new WindowListener() {

			@Override
			public void windowOpened(WindowEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void windowIconified(WindowEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void windowDeiconified(WindowEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void windowDeactivated(WindowEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void windowClosing(WindowEvent e) {
				// TODO Auto-generated method stub
				System.exit(0);
			}

			@Override
			public void windowClosed(WindowEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void windowActivated(WindowEvent e) {
				// TODO Auto-generated method stub

			}
		});
		this.setResizable(false);
		this.setBackground(Color.green);

		this.addKeyListener(new KeyMonitor());
		this.setVisible(true);
		new Thread(new PaintThread()).start();
	}

	private class PaintThread implements Runnable {
		public void run() {
			while (true) {
				repaint(); // 重画框架
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

			}
		}
	}

	private class KeyMonitor extends KeyAdapter {

		@Override
		public void keyReleased(KeyEvent e) {
			myTank.keyReleased(e);
		}

		public void keyPressed(KeyEvent e) {
			myTank.keyPressed(e);
		}

	}

	public static void main(String[] args) {
		TankClient tankClient = new TankClient();
		tankClient.launchFrame();

	}
}
