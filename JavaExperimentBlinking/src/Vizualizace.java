import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
//import java.awt.RenderingHints;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;


public class Vizualizace extends JFrame{

	private static final long serialVersionUID = 1L;
	
	/** Rozmery JFramu */
	int windowWidth = 1280;
	int windowHeight = 720;
	
	/** Nactena data */
	int [] data;
	
	/** Nalezene maximum v datech - pro ucely uprav meritka vykresleni */
	int dataMaxValue;
	
	/** Prahova hodnota pro mrkani */
	int blinkMinValue = 400;
	
	/** Okno vizualizace */
	JFrame okno;
	
	/** Obrazek na ktery vykreslujeme nactena data, prah mrkani a nalezene mrkani */
	BufferedImage platno;

	/**Zobrazi okno s vykreslenou krivkou namerenych dat (cerna), nulovou osou (modra) 
	 * prahovou hodnotou mrkani (cervena) a v horni casti cerne obdelnicky v mistech
	 * positivniho nalezu mrknuti
	 * 
	 * @param data - pole namerenych dat
	 */
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
	
	/**
	 * Vola posloupnost metod pro vykresleni a inicializuje platno podle rozmeru aplikace
	 */
	private void forceDraw(){
		platno = new BufferedImage(windowWidth, windowHeight, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2 = platno.createGraphics();
		drawAxis(g2);
		drawLine(g2);
	}
	
	/**Vykresluje krivky specifikovane v dokumentaci konstruktoru
	 * 
	 * @param g2 - graficka kontext platna
	 */
	private void drawLine(Graphics2D g2) {
		
		int halfY = windowHeight/2;
		double scaleX = (double)windowWidth / data.length;
		double scaleY = (double)halfY / (dataMaxValue * 2);
				
		//g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON); //Antialiasing
		g2.setColor(Color.BLACK);
		
		for(int i = 1; i < data.length; i++){
			int x1 = (int)((i - 1) * scaleX);
			int y1 = (int)(halfY - data[i - 1] * scaleY);
			int x2 = (int)(i * scaleX);
			int y2 = (int)(halfY - data[i] * scaleY);
			
			g2.drawLine(x1, y1, x2, y2);
			
			if(data[i] > blinkMinValue){
				g2.drawRect((int)(i * scaleX), 80, 1, 20);
			}
		}
		
		g2.setColor(Color.RED);
		g2.drawLine(0, (int)(halfY - blinkMinValue * scaleY), windowWidth, (int)(halfY - blinkMinValue * scaleY));
	}

	/**Nalezne v poli dat maximalni hodnotu
	 * 
	 * @param data2 - pole dat
	 * @return int - maximalni hodnota
	 */
	private int getMax(int[] data2) {
		int max = Integer.MIN_VALUE;
		
		for(int i : data2){
			if(i > max) max = i;
		}

		return max;
	}

	
	/**Vykresli nulovou osu uprostred platna
	 * 
	 * @param g2 - graficky kontext platna
	 */
	private void drawAxis(Graphics2D g2) {	
		g2.setColor(Color.BLUE);
		g2.drawLine(0, windowHeight/2, windowWidth, windowHeight/2);
	}

	/**
	 * Inicializace okna.
	 */
	private void init(){
		okno = this;
		okno.setTitle("Vizualizace namerenych dat - Offline verze");
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
