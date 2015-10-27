package basics;

public class DiamondTollModel extends TollModel {

	private int entryLanes;

	DiamondTollModel(int numberOfLanes, int numberOfTolls) {
		super(0);
		this.numLanes = numberOfTolls;
		lanes = new ICar[numberOfTolls+2][numSpots];
		this.entryLanes = numberOfLanes;
		for(int i = 0; i< numSpots; i++){
			lanes[0][i] = new Edge(0,i);
			for(int j = 1; j<= numberOfTolls; j++){
				int numTrans = calcNumberTransitions(numberOfLanes, numberOfTolls);
				int lenTrans = calcLenTrans(numTrans);
				if(i < (numTrans - (j-1)) * lenTrans || i-1 > numSpots/2+ (j)*lenTrans || i < (numTrans - ((numberOfTolls - (j-1))-1)) * lenTrans || i-1 > numSpots/2+ (numberOfTolls - (j-1))*lenTrans)
					lanes[j][i] = new Edge(j,i);
				else
					lanes[j][i] = new empty(j,i);
			}
			lanes[numberOfTolls+1][i] = new Edge(numberOfTolls+1, i);
		}
		
	}

	private int calcLenTrans(int numTrans) {
		return numSpots/(2*(numTrans+1));
	}

	private int calcNumberTransitions(int numberOfLanes, int numberOfTolls) {
		return (numberOfTolls-numberOfLanes)/2;
	}
	
	@Override
	public String toString(){
		return "DiamondTollModel with "+this.numLanes+" Tollbooths and "+entryLanes+" lanes";
		
	}

}
