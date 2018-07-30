
import javax.swing.JFrame;

public class Game {

	public static void main(String[] args) {
		JFrame f = new JFrame();
		DrawPanel dp = new DrawPanel();
		
		f.add(dp);
		f.setSize(900, 500);
		f.setLocationRelativeTo(null);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setVisible(true);
	}

}
