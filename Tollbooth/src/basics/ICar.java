package basics;

import java.awt.Color;
import java.awt.Graphics;

abstract public class ICar {
	protected int lane;
	protected int spot;
	private boolean easyPass = false;
	protected boolean enterable;
	protected Color color;
	protected int speed = 0;
	public void progress(ICar[][] lanes){
		//The default is to do nothing.
	}
	public void draw(Graphics g){
		//Draw a colored box
		g.setColor(color);
		g.fillRect(spot*Displayer.width, lane*Displayer.height, Displayer.width, Displayer.height);
		if(spot == TollModel.numSpots/2){
			g.setColor(Color.orange);
			g.fillRect(spot*Displayer.width, lane*Displayer.height+(Displayer.height/2), Displayer.width/2, Displayer.height/2);
		}
	}
	public int getSpeed() {
		return this.speed ;
	}
}
