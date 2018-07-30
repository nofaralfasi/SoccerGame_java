import java.awt.Color;

public class Goalkeeper extends Player {
	public  Goalkeeper( Color tc, int s,int pn) {
		super(tc,s,pn);
		playerSpeed = 1;
		setRadius(1);
	}

	@Override
	public void decideWhatToDo(Ball b, boolean control) {
		if(!control){
		if(b.getY()>cy+radius)
			alpha = Math.PI/2; //==1
		else
			alpha = -Math.PI/2;//==-1
		}
		
	}
	
	public void InitialPosition(double w, double h,int i) {
		if(side==RIGHT_SIDE)
			setCenter(w-(w/30), h/2);		
		else 
			setCenter(w/30, h/2);
		setRadius((int)(w/80));		
	}
	
	@Override
	public void move() {
		double dy;
		dy = Math.sin(alpha);
		cy = (int)( cy+(playerSpeed*dy));
	}

}
