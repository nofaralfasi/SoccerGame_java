import java.awt.Color;

public class TeamPlayer extends Player {
	public TeamPlayer(Color tc, int s, int pn) {
		super(tc, s, pn);
		playerSpeed = SPEED;
	}

	public void InitialPosition(double x, double y, int w) {
		if (side == RIGHT_SIDE)
			x = x + (w / 2.3);
		setCenter(x, y);
		setRadius(w / 80);
	}

	public void decideWhatToDo(Ball b, boolean control) {
		if (!control) {
			if (b.getX() > cx + (radius * 2)) {
						if (Math.cos(alpha) <= 0)
							alpha = Math.PI - alpha;
			} else {
					if (Math.cos(alpha) >= 0)
					alpha = Math.PI - alpha;
			}
			if (b.getY() > cy + (radius * 2)) {
				if (Math.sin(alpha) <= 0 )
					alpha = -alpha;
			} else {
				if (Math.sin(alpha) >= 0 )
					alpha = -alpha;
			}
		} else { // we control the ball
			if (b.dirX() > 0)
				b.setAlpha(Math.PI - alpha);
			else
				b.setAlpha(Math.PI - alpha);
		}
	}
}
