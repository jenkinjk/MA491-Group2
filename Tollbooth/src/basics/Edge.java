package basics;

import java.awt.Color;

public class Edge extends ICar {

	Edge(int lane, int spot) {
		enterable = false;
		this.lane = lane;
		this.spot = spot;
		this.color = Color.black;
	}
}
