package angryBirds;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

public class AngryBirds extends JPanel implements ActionListener, MouseListener, MouseMotionListener {

	Image background = Toolkit.getDefaultToolkit().createImage("D:\\java\\AngryBirds\\img\\images.jpg");
	Image bird = Toolkit.getDefaultToolkit().createImage("D:\\java\\AngryBirds\\img\\bird.png");

	LinkedList<Polygon> targets;
	LinkedList<Bullet> bullets;
	Bullet[] toWay;
	Timer time;
	Random rand;

	int wallX0;
	int wallY0;
	int wallX;
	int wallY;

	boolean snipActive;
	boolean moveActive;

	int sniperX0;
	int sniperY0;
	int sniperX;
	int sniperY;

	int speed0;
	int speedX;
	int speedY;
	int degreess;
	int t;

	int base;
	double gravity;

	public AngryBirds() {
		super();
		
		this.targets = new LinkedList<Polygon>();
		this.toWay = new Bullet[10];
		this.bullets = new LinkedList<Bullet>();

		this.wallX0 = 150;
		this.wallX = 150;
		this.wallY0 = 500;
		this.wallY = 400;

		this.sniperX0 = 150;
		this.sniperX = 100;
		this.sniperY0 = 400;
		this.sniperY = 450;

		this.snipActive = false;
		this.moveActive = false;

		this.speed0 = 115;
		this.degreess = 0;
		this.t = 0;
		this.gravity = 9.8;

		this.base = 550;

		this.rand = new Random();
		this.newGame();
		addMouseListener(this);
		addMouseMotionListener(this);
		this.time = new Timer(40, this);
		time.start();

	}

	public void newGame() {
		this.bullets.add(0, new Bullet(50, 520, 30));
		this.bullets.add(1, new Bullet(90, 520, 30));
		this.bullets.add(2, new Bullet(130, 520, 30));
		this.bullets.add(3, new Bullet(170, 520, 30));
		this.addTarget();
	}

	public Polygon newTarget() {
		Polygon target = new Polygon();
		target.xpoints = new int[4];
		target.ypoints = new int[4];
		target.npoints = 4;

		int x = rand.nextInt(400) + 500;
		int y = rand.nextInt(200) + 100;

		target.xpoints[0] = x;
		target.ypoints[0] = y;

		target.xpoints[1] = x + 50;
		target.ypoints[1] = y;

		target.xpoints[2] = x + 50;
		target.ypoints[2] = y + 50;

		target.xpoints[3] = x;
		target.ypoints[3] = y + 50;
		return target;
	}

	public void addTarget() {
		for (int i = 0; i < 10; i++) {
			this.targets.add(this.newTarget());
		}
	}

	public void slopeCalc() {
		if (this.snipActive) {
			if (this.base < this.bullets.getLast().getY()) {
				this.snipActive = false;
				this.bullets.remove(this.bullets.getLast());
				this.t = 0;

				if (this.bullets.size() == 0) {
					this.newGame();
					repaint();
					JOptionPane jb = new JOptionPane();
					jb.showMessageDialog(null," \n GAME OVER! \n THE GAME IS RESTARTING...");

				}

			} else {
				this.bullets.getLast().setX(this.bullets.getLast().getX() + this.speedX);
				this.bullets.getLast().setY((int) (this.bullets.getLast().getY() + this.speedY + this.gravity * t));
			}
		}
	}

	public void showToWay(int x, int y, int degreess) {

		int weight = 10;
		
		for (int i = 0; i < 10; i++) {
			int wayX, wayY;
			if (i == 0) {
				wayX = x;
				wayY = y;
				this.toWay[i] = new Bullet(wayX, wayY, weight);

			} else {
				wayX = (int) (this.toWay[i - 1].getX() + this.speed0 * Math.cos(Math.toRadians(-degreess)));
				wayY = (int) (this.toWay[i - 1].getY() + this.speed0 * Math.sin(Math.toRadians(-degreess))
						+ this.gravity * i);
				this.toWay[i] = new Bullet(wayX, wayY, weight);
			}
		}
	}

	public void controlShotArea() {
		if (this.bullets.size() > 0 && this.bullets.getLast().getY() < 400) {
			int index = 0;

			Area shotArea = new Area(new Rectangle(this.bullets.getLast().getX(), this.bullets.getLast().getY(),
					this.bullets.getLast().getWeight(), this.bullets.getLast().getWeight()));
			Area copyShotArea, targetArea;

			while (this.targets.size() > index) {
				copyShotArea = (Area) shotArea.clone();
				targetArea = new Area(this.targets.get(index));

				copyShotArea.intersect(targetArea);

				if (!copyShotArea.isEmpty()) {
					this.targets.remove(this.targets.get(index));
					this.controlShotArea();
					
				}
				index++;
			}
		}

	}

	public void paint(Graphics g) {
		super.paint(g);

		g.drawImage(background, 0, 0, 1000, 600, null);

		g.drawLine(this.wallX0, this.wallY0, this.wallX, this.wallY);
		g.setColor(new Color(255, 0, 0));
		g.drawLine(this.sniperX0, this.sniperY0, this.sniperX, this.sniperY);

		g.setColor(new Color(100, 25, 35));
		Iterator tar = this.targets.iterator();
		while (tar.hasNext()) {
			Polygon target = (Polygon) tar.next();
			g.fillPolygon(target);
		}

		g.setColor(new Color(255, 24, 35));
		Iterator it = this.bullets.iterator();
		while (it.hasNext()) {
			Bullet b = (Bullet) it.next();
			g.drawImage(bird, b.getX(), b.getY(), b.getWeight() + 20, b.getWeight() + 20, null);
		}

		if (this.moveActive) {
			g.setColor(new Color(255,255,255));
			for (int i = 0; i < this.toWay.length; i++) {
				Bullet circle = this.toWay[i];
				g.fillOval(circle.getX(), circle.getY(), circle.getWeight(), circle.getWeight());
			}
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (this.snipActive) {
			this.slopeCalc();
			this.controlShotArea();
			this.t++;
		}
		repaint();
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent e) {
		if (this.bullets.size() > 0 && !this.snipActive) {
			this.bullets.getLast().setX(this.sniperX - 35);
			this.bullets.getLast().setY(this.sniperY - 23);
		}
		repaint();
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if (this.bullets.size() > 0) {
			this.speedX = (int) (this.speed0 * Math.cos(Math.toRadians(this.degreess)));
			this.speedY = -(int) (this.speed0 * Math.sin(Math.toRadians(this.degreess)));
			this.snipActive = true;
		}
		this.moveActive = false;
		repaint();
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseDragged(MouseEvent e) {
		if (this.bullets.size() > 0 && this.snipActive == false) {
			this.bullets.getLast().setX(e.getX() - 35);
			this.bullets.getLast().setY(e.getY() - 23);

			this.sniperX = e.getX();
			this.sniperY = e.getY();
			this.degreess = -(int) (Math
					.toDegrees((Math.atan2(this.sniperY0 - this.sniperY, this.sniperX0 - this.sniperX))));

			this.moveActive = true;

			this.showToWay(this.wallX0, this.wallY - 10, this.degreess);
		}

		repaint();
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub

	}

}
