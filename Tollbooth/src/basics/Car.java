package basics;

import java.awt.Color;
import java.util.Random;

public class Car extends ICar {
	private int waited;
	private int time = 0;
	private Random rand;

	Car(int lane, int spot) {
		enterable = false;
		this.lane = lane;
		this.spot = spot;
		this.color = Color.RED;
		this.rand = new Random();
		this.speed = rand.nextInt(6) + 10;
		this.waited = rand.nextInt(4) + 2;
	}

	@Override
	public void progress(ICar[][] lanes) {
		time = time + 1;
		int toCheck = spot + 1;
		adjustSpeed(lanes);
		int moved = 0;
		while (moved < this.speed) { // Drive on.
			if (spot == TollModel.numSpots / 2 && this.waited != 0) { // You hit
																		// a
																		// toll
																		// booth.
																		// Pay
				// up.
				speed = 0;
			} else if (toCheck < TollModel.numSpots) {
				if (lanes[lane][toCheck].enterable) { // Drive on.
						ResetSpaceEmpty(lanes);
						progressCar(lanes);
						this.spot = toCheck;
						toCheck++;
						moved++;
				} else {
					boolean avoided = attemptAvoid(lanes);
					if (avoided) {
						toCheck++;
						moved++;
					} else { // Guess you can't.
						this.speed = lanes[lane][toCheck].getSpeed(); // Break
																		// to
																		// the
																		// speed
																		// of
																		// the
																		// guy
																		// in
																		// front
																		// of
																		// you.
						break; // No more progression for now.

					}
				}
			} else { // Dodge around. If you can.
				ResetSpaceEmpty(lanes); // You reached the end! Be free.
				TollModel.times.add(this.time);
				break;
			}
		}
	}

	private void ResetSpaceEmpty(ICar[][] lanes) {
		ICar me = lanes[lane][spot];
		me = null;
		ICar spotReplacement = new empty(lane, spot);
		lanes[lane][spot] = spotReplacement;
	}

	private void progressCar(ICar[][] lanes) {
		if (spot + 1 < TollModel.numSpots){
			lanes[lane][spot+1]=null;
			lanes[lane][spot + 1] = this;
		}
	}

	private boolean attemptAvoid(ICar[][] lanes) {
		boolean upFirst = rand.nextBoolean();
		int toCheck = spot + 1;
		if (upFirst) {
			if (lanes[lane - 1][toCheck].enterable) {
				ResetSpaceEmpty(lanes);
				this.lane = lane - 1;
				progressCar(lanes);
				this.spot = toCheck;
				return true;
			} else {
				if (lanes[lane + 1][toCheck].enterable) {
					ResetSpaceEmpty(lanes);
					this.lane = lane + 1;
					progressCar(lanes);
					this.spot = toCheck;
					return true;
				}
			}
		} else {
			if (lanes[lane + 1][toCheck].enterable) {
				ResetSpaceEmpty(lanes);
				this.lane = lane + 1;
				progressCar(lanes);
				this.spot = toCheck;
				return true;
			} else {
				if (lanes[lane - 1][toCheck].enterable) {
					ResetSpaceEmpty(lanes);
					this.lane = lane - 1;
					progressCar(lanes);
					this.spot = toCheck;
					return true;
				}
			}
		}
		return false;

	}

	private void adjustSpeed(ICar[][] lanes) {

		if (spot < TollModel.numSpots / 2) {
			brake(lanes);
		} else if (spot > TollModel.numSpots / 2) {
			accelerate();
		} else {
			payToll();
		}
		if (speed == 0) {
			if (lanes[lane][spot + 1].enterable)
				speed = 1;
		}
	}

	private void payToll() {
		this.waited--;
		if (waited == 0) {
			this.speed = 1;
		}

	}

	private void accelerate() {
		if (speed < 10)
			this.speed += rand.nextInt(5) + 1;

	}

	private void brake(ICar[][] lanes) {
		while (checkSpeedSpotsAhead(lanes)) {
			this.speed -= rand.nextInt(5) + 1;
			if (this.speed <= 0) {
				this.speed = 1;
				break;
			}
		}
	}

	private boolean checkSpeedSpotsAhead(ICar[][] lanes) {
		for (int i = 1; i <= speed; i++) {
			if (!lanes[lane][spot + i].enterable) {
				return true;
			}
			if (spot + i == TollModel.numSpots / 2)
				return true;
		}
		return false;
	}

}
