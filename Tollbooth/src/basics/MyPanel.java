package basics;

import java.awt.Graphics;

import javax.swing.JPanel;

public class MyPanel extends JPanel {

	private TollModel lanes;

	public MyPanel(TollModel model) {
		this.lanes = model;
	}
	
	@Override
	public void paint(Graphics g){
		for(ICar[] cars: lanes.getLanes()){
			for(ICar c: cars){
				try{
				c.draw(g);
				}catch(NullPointerException e){
					if(c==null){
						System.out.println("c is null!");
					}
					if(g == null){
						System.out.println("g is null!");
					}
				}
			}
		}
	}

	private static final long serialVersionUID = 1L;

}
