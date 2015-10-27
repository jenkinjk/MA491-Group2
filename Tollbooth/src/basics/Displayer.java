package basics;

import java.util.HashMap;

import javax.swing.JFrame;

public class Displayer {
	
	private static TollModel model = new DiamondTollModel(2, 4);
	public static MyPanel pan = new MyPanel(model);
	static JFrame frame = new JFrame("Here there be trolls. I mean tolls.");
	public static int width, height;
	private static HashMap<Integer, TollModel> models = new HashMap<Integer, TollModel>();
	private static double masterTime;
	private static int modelCounter=0;
	
	
	public static void main(String [ ] args){
		models.put(0, new TollModel(4));
		models.put(1, new DiamondTollModel(2, 8));
		
		models.put(2, new DiamondTollModel(2, 4));
		models.put(3, new TollModel(8));
		models.put(4, new TollModel(1));
		models.put(5, new TollModel(2));
		Displayer.height = 950/(model.getNumLanes()+2);
		Displayer.width = 1800/TollModel.numSpots;
		frame.setSize(1800, 1000);
		frame.setVisible(true);
		frame.setContentPane(pan);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		double time = System.currentTimeMillis();
		Displayer.masterTime = System.currentTimeMillis();
		while(modelCounter<=5){
			if(System.currentTimeMillis()-Displayer.masterTime>30100){
				incrementTollModel();
			}
			if(System.currentTimeMillis()-time>100){
				model.increment();
				time = System.currentTimeMillis();
			}
			frame.repaint();
			frame.revalidate();
			
		}
	}


	private static void incrementTollModel() {
		model = models .get(modelCounter);
		modelCounter++;
		Displayer.masterTime = System.currentTimeMillis();
		Displayer.pan = new  MyPanel(model);
		Displayer.frame.setContentPane(pan);
	}
	

}
