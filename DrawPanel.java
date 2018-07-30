import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Label;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class DrawPanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private SoccerField sf;
	private Team t1;
	private Team t2;
	private boolean hasStarted;
	private Ball b;
	private Timer t;
	private int w, h;
	private Label points;
	Object selectedValue;
	Object[] selectionValues;
	int s;

	public DrawPanel() {

		points = new Label();
		points.setFont(new Font("Ariel", Font.BOLD, 20));
		points.setBackground(Color.green);
		add(points);
		s = 0;
		setWH(0, 0);
		sf = new SoccerField();
		t1 = new Team(Color.YELLOW, 1);
		t2 = new Team(Color.RED, 2);
		hasStarted = false;
		b = new Ball();
		t = new Timer();

		while (s == 0)
			s = bla();

		t.schedule(new MyTimerTask(), 1000, 30);
		addKeyListener(new MyKeyboardAdapter());
		setFocusable(true);
		requestFocusInWindow();
		addMouseListener(new MouseHelper());

	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		h = getHeight();
		w = getWidth();
		setWH(w, h);
		
		t1.setSize(w, h);
		t2.setSize(w, h);

		sf.setSize(w, h);		
		sf.drawMe(g);

		if (!hasStarted) {
			t1.reArrange(w, h);
			t2.reArrange(w, h);
			b.InitialPosition(w, h, 0);
		}

		points.setText("YELLOW: " + t1.getPoints() + " RED: " + t2.getPoints());
		points.paint(g);

		t1.drawMe(g);
		t2.drawMe(g);
		b.drawMe(g);

	}

	private void setWH(int width, int height) {
		w = width;
		h = height;
	}

	public int getW() {
		return w;
	}

	public int getH() {
		return h;
	}

	public int bla() {
		selectionValues = new Object[3];
		selectionValues[0] = "Select";
		selectionValues[1] = "Real player";
		selectionValues[2] = "Alt";

		selectedValue = JOptionPane.showInputDialog(null, "Select who is playing", "Soccer Game",
				JOptionPane.QUESTION_MESSAGE, null, selectionValues, selectionValues[0]);

		if (selectedValue.equals(selectionValues[1]))
			return 1;
		else if (selectedValue == selectionValues[2])
			return 2;
		else
			return 0;
	}

	private class MyTimerTask extends TimerTask {
		public void run() {
			hasStarted = true;

			b.setLocation(getW(), getH(), b.getRadius());
			b.checkAngle(sf);
			b.move();

			t1.checkBall(b);
			t2.checkBall(b);

			t1.checkBorders(sf);
			t2.checkBorders(sf);

			t1.move(b, getW(), getH());

			if (s == 2)
				t2.move(b, getW(), getH());
			else {
				t2.closestPlayer(b);
				t2.setLocation(getW(), getH());
			}
			int score = b.checkGates(w, h);
			if (score == 0) {
				t1.addPoints();
				hasStarted = !hasStarted;
			} else if (score == 1) {
				t2.addPoints();
				hasStarted = !hasStarted;
			}
			repaint();
		}
	}

	private class MyKeyboardAdapter extends KeyAdapter {
		public void keyPressed(KeyEvent e) {
			int ke = e.getKeyCode();
			if (ke == KeyEvent.VK_DOWN || ke == KeyEvent.VK_UP || ke == KeyEvent.VK_LEFT || ke == KeyEvent.VK_RIGHT){
				if (s == 1)
					t2.getOrder(b, ke);
			}
			else if(ke==KeyEvent.VK_SPACE)
				t2.checkBall(b);
		}
		
	}

	private class MouseHelper extends MouseAdapter {
		public void mouseClicked(MouseEvent e) {
			int x, y;
			x = e.getX();
			y = e.getY();
			b.setCenter(x, y);

		}
	}

}
