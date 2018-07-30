import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.TexturePaint;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Ball implements Moveable {
	private int radius;
	private double bx, by;
	private double alpha;
	private double ball_speed;
	
	public Ball() {
		ball_speed = 3;
		alpha = 2*Math.PI*Math.random();
		setRadius(10);
	}
	public void InitialPosition(double w, double h, int i) {
		bx = w / 2;
		by = h / 2;
		setRadius((int)(w/80));
	}
	public void setLocation(int w, int h,double r) {
		double deltaR = (w/80)/(r);
		if (deltaR != 1){
		bx = deltaR*bx;
		by = deltaR*by;
		setRadius(w/80);
		setPlayerSpeed(getSpeed()*deltaR);
		}			
	}
	public void move() {
		double dx, dy;
		dx = Math.cos(alpha);
		dy = Math.sin(alpha);
		bx = (bx + (ball_speed * dx));
		by = (by + (ball_speed * dy));
	}
	public void drawMe(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		BufferedImage bi = null;
		try {
			bi = ImageIO.read(new File("ball.jpg"));
		} catch (IOException ex) {
			System.out.println("File ball.jpg was not found\n");
		}
		TexturePaint tp = new TexturePaint(bi, new Rectangle((int) bx + 10, (int) by, 50, 50));
		g2d.setPaint(tp);
		g2d.fillOval((int) bx - radius, (int) by - radius, 2 * radius, 2 * radius);
		g2d.setColor(Color.BLACK);
		g2d.drawOval((int) bx - radius, (int) by - radius, 2 * radius, 2 * radius);
		g2d.dispose();
	}
	public double getX() {
		return bx;
	}
	public double getY() {
		return by;
	}
	public void setCenter(double x, double y) {
		bx = x;
		by = y;
	}
	public double dirX() {
		return Math.cos(alpha);
	}
	public void checkAngle(SoccerField sf) {
		double dx, dy;
		Rectangle r = sf.getBorders();
		dx = Math.cos(alpha);
		dy = Math.sin(alpha);
		if (dx > 0) { // check right border
			if (bx + radius + dx * ball_speed > r.getMaxX())
				alpha = Math.PI - alpha;
		} else { // check left border
			if (bx - radius + dx * ball_speed < r.getMinX())
				alpha = Math.PI - alpha;
		}
		if (dy > 0) { // check bottom border
			if (by + radius + dy * ball_speed > r.getMaxY())
				alpha = -alpha;
		} else { // check top border
			if (by - radius + dy * ball_speed < r.getMinY())
				alpha = -alpha;
		}
	}
	public void setAlpha(double angle) {
		alpha = angle;
	}
	public double getAlpha() {
		return alpha;
	}
	
	public int checkGates(int w, int h) {
		if (by > (h/2)-(h/7) && by < (h/2)+(h/7)) {
			if (bx < (w/22))
				return 0;
			else if (bx > w - (w/22))
				return 1;
		} 
			return -1;
	}
	private void setPlayerSpeed(double bs) {
		ball_speed=bs;		
	}
	private double getSpeed() {
		return ball_speed;
	}
	private void setRadius(int r) {
		radius=r;		
	}
	public int getRadius() {
		return radius;		
	}
	
}
