package basics;

import java.util.ArrayList;
import java.util.Random;

public class TollModel {
	protected ICar[][] lanes;
	protected int numLanes;
	public static int numSpots = 100;
	private double time = -1;
	private int chance = 75;
	private boolean increasing = true;
	private Random rand = new Random();
	private long masterTime=-1;
	private ArrayList<Integer> chances = new ArrayList<Integer>();
	public static ArrayList<Integer> times = new ArrayList<Integer>();

	TollModel(int numberOfLanes) {
		this.numLanes = numberOfLanes;
		lanes = new ICar[numberOfLanes+2][numSpots];
		for(int i = 0; i< numSpots; i++){
			lanes[0][i] = new Edge(0,i);
			for(int j = 1; j<= numberOfLanes; j++){
				lanes[j][i] = new empty(j,i);
			}
			lanes[numberOfLanes+1][i] = new Edge(numberOfLanes+1, i);
		}
	}

	public void increment() {
		if ((int)System.currentTimeMillis() % 2 == 0) {
			for (int j = TollModel.numSpots-1; j >=0; j--) {
				for (int i = 1; i < lanes.length; i++) {
					lanes[i][j].progress(lanes);
				}
			}
		}else{
			for (int j = TollModel.numSpots-1; j >=0; j--) {
				for (int i = lanes.length-1; i >0; i--) {
					lanes[i][j].progress(lanes);
				}
			}
		}
		sendNewCars();
		checkTimeAndAverage();
	}

	private void checkTimeAndAverage() {
		if(masterTime == -1){
			masterTime = System.currentTimeMillis();
		}
		if(System.currentTimeMillis() - this.masterTime > 30000){
			System.out.println("The average for this minute of model "+this.toString()+" is "+average() + " with average chance of spawning = "+averageSpawn());
			TollModel.times = new ArrayList<Integer>();
			this.chances = new ArrayList<Integer>();
			this.masterTime = System.currentTimeMillis();
		}
		
	}

	private int averageSpawn() {
		int total = 0;
		for(int i: this.chances){
			total = total + i;
		}
		return total/this.chances.size();
	}

	private int average() {
		int total = 0;
		for(int i: TollModel.times){
			total = total + i;
		}
		return total/TollModel.times.size();
	}

	private void sendNewCars() {
		rovingTime();
		for(int j = 1; j<= numLanes; j++){
			Random rand = new Random();
			if(rand.nextInt(100)<chance)
				if(lanes[j][0].enterable)
					lanes[j][0] = new Car(j,0);
		}
		
	}

	private void rovingTime() {
		if(System.currentTimeMillis()-time > 300){
			modifyChance();
			time = System.currentTimeMillis();
		}
	}

	private void modifyChance() {
		if(this.increasing ){
			if(chance > 90){
				this.increasing=false;
				chance = chance - rand.nextInt(10);
			}else{
				chance = chance + rand.nextInt(10);
			}
		}else{
				if(chance < 10){
					this.increasing=true;
					chance = chance + rand.nextInt(10);
				}else{
					chance = chance - rand.nextInt(10);
				}
		}
		this.chances.add(chance);
	}

	public int getNumLanes() {
		return this.numLanes;
	}
	
	public ICar[][] getLanes() {
		return this.lanes;
	}
	
	@Override
	public String toString(){
		return "TollModel with "+this.numLanes+" Lanes";
		
	}

}
