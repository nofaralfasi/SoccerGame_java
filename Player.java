import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public abstract class Player implements Moveable {
	public final int LEFT_SIDE = 1;
	public final int RIGHT_SIDE = 2;
	public final double SPEED = 1.3; // basic speed
	protected double radius;
	protected double cx, cy;
	protected double alpha;
	protected double playerSpeed;
	protected int pn;
	protected Color teamColor;
	protected int side;


	public Player(Color c, int s, int i) {
		setCenter(1, 1);
		setRadius(1);
		teamColor = c;
		side = s;
		pn = i;
		setAlpha(Math.PI / 4);
		if(side==RIGHT_SIDE)
			setAlpha(Math.PI-alpha);
	}

	public void setLocation(int w, int h, double r) {
		double deltaR = (w / 80) / (r);
		if (deltaR != 1) {
			cx = deltaR * cx;
			cy = deltaR * cy;
			setRadius(w / 80);
			setPlayerSpeed(getSpeed() * deltaR);
		}
	}

	public void move() {
		double dx, dy;
		dx = Math.cos(alpha);
		dy = Math.sin(alpha);
		cx = cx + (playerSpeed * dx);
		cy =  cy + (playerSpeed * dy);	
	}

	public void checkBorders(SoccerField sf) {
		double dx, dy;
		Rectangle r = sf.getBorders();
		dx = Math.cos(alpha);
		dy = Math.sin(alpha);
		while (dx == 0)
			alpha -= Math.PI / 4;
		if (dx > 0) { // check right border
			if (cx + radius + dx * getSpeed() > r.getMaxX())
				alpha = Math.PI - alpha;
		} else { // check left border
			if (cx - radius + dx * getSpeed() < r.getMinX())
				alpha = Math.PI - alpha;
		}
		while (dy == 0)
			alpha += Math.PI / 4;
		if (dy > 0) { // check bottom border
			if (cy + radius + dy * getSpeed() > r.getMaxY())
				alpha = -alpha;
		} else { // check top border
			if (cy - radius + dy * getSpeed() < r.getMinY())
				alpha = -alpha;
		}
	}

	public void setCenter(double x, double y) {
		cx = x;
		cy = y;
	}

	public void setAlpha(double a) {
		alpha = a;
	}
	public void setColor(Color c) {
		teamColor = c;
	}
	public double getX() {
		return cx;
	}

	public double getY() {
		return cy;
	}

	public void drawMe(Graphics g) {
		g.setColor(teamColor);
		g.fillOval((int) (cx - radius), (int) (cy - radius), (int) (2 * radius), (int) (2 * radius));
		g.setColor(Color.BLACK);
		g.drawOval((int) (cx - radius), (int) (cy - radius), (int) (2 * radius), (int) (2 * radius));
		g.drawLine((int) cx, (int) cy, (int) (cx + radius * Math.cos(alpha)), (int) (cy + radius * Math.sin(alpha)));
	}

	public double getSpeed() {
		return playerSpeed;
	}

	public double getAlpha() {
		return alpha;
	}

	public void setRadius(double r) {
		radius = r;
	}

	public void setPlayerSpeed(double speed) {
		playerSpeed = speed;
	}

	public double getRadius() {
		return radius;
	}

	public abstract void InitialPosition(double d, double e, int w);

	public abstract void decideWhatToDo(Ball b, boolean c);

}
