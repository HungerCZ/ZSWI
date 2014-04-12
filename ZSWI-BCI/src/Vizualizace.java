import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;


public class Vizualizace extends JFrame{
	
	int windowWidth = 1280;
	int windowHeight = 720;
	
	int [] data;
	int dataMaxValue;
	
	JFrame okno;
	
	BufferedImage platno;

	public Vizualizace(int[] data) {
		this.data = data;
		dataMaxValue = getMax(data);
		init();
		
		forceDraw();
		
		this.addComponentListener(new ComponentAdapter() {
			public void componentResized(ComponentEvent e){
				windowWidth = getWidth();
				windowHeight = getHeight();
				forceDraw();
			}
		});
	}
	
	private void forceDraw(){
		initPlatno();
		Graphics2D g2 = platno.createGraphics();
		drawAxis(g2);
		drawLine(g2);
	}
	
	private void initPlatno(){
		platno = new BufferedImage(windowWidth, windowHeight, BufferedImage.TYPE_INT_ARGB);
	}
	
	private void drawLine(Graphics2D g2) {
		int halfY = windowHeight/2;
		double scaleX = (double)windowWidth / data.length;
		double scaleY = (double)halfY / dataMaxValue;
				
		//g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON); //Antialiasing
		g2.setColor(Color.BLACK);
		
		for(int i = 1; i < data.length; i++){
			int x1 = (int)((i - 1) * scaleX);
			int y1 = (int)(halfY - data[i - 1] * scaleY);
			int x2 = (int)(i * scaleX);
			int y2 = (int)(halfY - data[i] * scaleY);
			
			g2.drawLine(x1, y1, x2, y2);
		}
		
		g2.drawLine(0, (int)(halfY - dataMaxValue*scaleY), windowWidth, (int)(halfY - dataMaxValue*scaleY));
	}

	private int getMax(int[] data2) {
		int max = Integer.MIN_VALUE;
		
		for(int i : data2){
			if(i > max) max = i;
		}

		return max;
	}

	private void drawAxis(Graphics2D g2) {	
		g2.setColor(Color.BLUE);
		g2.drawLine(0, windowHeight/2, windowWidth, windowHeight/2);
	}

	private void init(){
		okno = this;
		okno.setPreferredSize(new Dimension(windowWidth, windowHeight));
		okno.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		okno.setVisible(true);
		okno.pack();
	}
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		g.drawImage(platno, 0, 0, null);
	}
	
	
}
