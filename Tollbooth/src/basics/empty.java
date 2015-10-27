package basics;

import java.awt.Color;


public class empty extends ICar {
	empty(int lane, int spot) {
		enterable = true;
		this.lane = lane;
		this.spot = spot;
		this.color = Color.white;
	}

}
