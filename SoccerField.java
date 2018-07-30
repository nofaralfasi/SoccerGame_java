import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class SoccerField  {
	private int w, h;
	private Gate leftGate, rightGate;
	
	public SoccerField(){
		leftGate = new Gate();
		rightGate = new Gate();
	}
	public void setSize(int width, int height){
		 w=width;
		 h=height;
	}
	
	public Rectangle getBorders(){		// return left-top corner and width-height
		return new Rectangle(w/45, (h/46), w-(w/22), h-(h/23));
	}
	
	public void drawMe(Graphics g){
		int delta = w/16;
		// background
		for(int i=0;i<16;i++){
			if(i%2==0)
				g.setColor(new Color(100,200,100));
			else 
				g.setColor(new Color(120,230,120));
			g.fillRect(i*delta, 0, delta, h);
		}
		g.setColor(Color.WHITE);
		////--//-------------/////
		g.drawRect(w/45, (h/40), w-(w/23), h-(h/25));
		g.drawRect((w/43), (h/46), w-(w/22), h-(h/23));
		g.drawRect((w/41), (h/42), w-(w/21), h-(h/21));
		
		g.drawLine(w/2, h/40, w/2, h - h/50);
		g.drawOval(w/2-(w/8), (h/2) - h/4, w/(9/2), h/(5/2));
		g.fillOval(w/2 - w/80, h/2 - h/50, w/45, h/25);		
				
		g.drawRect((w/45), h/2 - h/4, (w/10), h/(5/2));// goalkeeper area
		g.drawRect(w - w/8, h/2 - h/4, w/10,  h/(5/2));
		
		leftGate.setAll(w/45, (h/2)-(h/7),w/45, h/(25/7));
		rightGate.setAll(w - (w/22), (h/2)-(h/7), w/45, h/(25/7));
		
		leftGate.drawMe(g);
		rightGate.drawMe(g);
		
		
	}

}
