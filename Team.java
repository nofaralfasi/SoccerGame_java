import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;

public class Team {
	public final int NUM_PLAYERS = 6;
	private Player[] players;
	private Color tColor;
	private int side;
	private int tPoints;
	private int w, h;

	public Team(Color c, int s) {
		setSize(1, 1);
		tColor = c;
		side = s;
		tPoints = 0;
		players = new Player[NUM_PLAYERS];
		players[0] = new Goalkeeper(tColor, side, 0);
		for (int i = 1; i < players.length; i++)
			players[i] = new TeamPlayer(tColor, side, i);
	}

	public void reArrange(int w, int h) {
		players[0].InitialPosition(w, h, 0);
		players[1].InitialPosition(w * 0.21, h * 0.2, w);
		players[2].InitialPosition(w * 0.41, h * 0.2, w);
		players[3].InitialPosition(w * 0.3, h * 0.5, w);
		players[4].InitialPosition(w * 0.22, h * 0.8, w);
		players[5].InitialPosition(w * 0.41, h * 0.8, w);
	}

	public void move(Ball b, int w, int h) {
		setLocation(w, h);
		for (int i = 0; i < players.length; i++) {
			players[i].decideWhatToDo(b, false);
			players[i].move();
		}
	}

	public void setLocation(int w, int h) {
		double r = players[0].getRadius();
		for (int i = 0; i < players.length; i++)
			players[i].setLocation(w, h, r);
	}

	public void checkBall(Ball b) {
		for (int i = 0; i < players.length; i++) {
			double dist = Math.sqrt((b.getX() - players[i].getX()) * (b.getX() - players[i].getX())
					+ (b.getY() - players[i].getY()) * (b.getY() - players[i].getY()));
			if (dist < 2 * b.getRadius()) {
				players[i].decideWhatToDo(b, true);
				players[i].setAlpha(2 * Math.PI * Math.random());
			}
		}
	}

	public Player closestPlayer(Ball b) {
		Player closest = null;
		double minDist = getW();
		for (int i = 0; i < players.length; i++) {
			double dist = Math.sqrt((b.getX() - players[i].getX()) * (b.getX() - players[i].getX())
					+ (b.getY() - players[i].getY()) * (b.getY() - players[i].getY()));
			players[i].setColor(tColor);
			if (dist < minDist) {
				minDist = dist;
				closest = players[i];
			}
		}
		closest.setColor(Color.BLACK);
		return closest;
	}

	public void getOrder(Ball b, int ke) {
		Player temp = closestPlayer(b);
		double speed=temp.getSpeed()*1.5;
		switch (ke) {
		case KeyEvent.VK_UP: // move forward
			temp.setCenter(temp.getX() + speed * Math.cos(temp.getAlpha()),
					temp.getY() + speed * Math.sin(temp.getAlpha()));
			break;
		case KeyEvent.VK_DOWN: // move backward
			temp.setCenter(temp.getX() - speed * Math.cos(temp.getAlpha()),
					temp.getY() - speed * Math.sin(temp.getAlpha()));
			break;
		case KeyEvent.VK_LEFT:
			temp.setAlpha(temp.getAlpha() - Math.PI / 10);
			break;
		case KeyEvent.VK_RIGHT:
			temp.setAlpha(temp.getAlpha() + Math.PI / 10);
			break;
		default:
			break;
		}
	}

	public void drawMe(Graphics g) {
		for (int i = 0; i < players.length; i++)
			players[i].drawMe(g);
	}

	public void checkBorders(SoccerField sf) {
		for (int i = 0; i < players.length; i++) {
			players[i].checkBorders(sf);
		}
	}

	public void addPoints() {
		tPoints++;
	}

	public int getPoints() {
		return tPoints;
	}

	public int getW() {
		return w;
	}

	public int getH() {
		return h;
	}

	public void setSize(int w, int h) {
		this.w = w;
		this.h = h;
	}
}
