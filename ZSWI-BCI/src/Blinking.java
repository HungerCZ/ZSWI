import java.io.File;
import java.util.Scanner;


public class Blinking {
	
	
	static int data [];
	
	public static void main(String[] args) {
		readInput(args[0]);
		new Vizualizace(data);
	}
	
	
	/**Precte ze souboru CSV vsechna data a ulozi je do pole 
	 * 
	 * @param fileName - nazev vstupniuho souboru
	 */
	public static void readInput(String fileName){
		try {
			File inFile = new File(fileName);
			Scanner sc = new Scanner(inFile);
			
			String [] dataString = sc.next().split(",");
			sc.close();
			
			data = new int[dataString.length];
			
			for(int i = 0; i < dataString.length; i++){
				data[i] = Integer.parseInt(dataString[i]);
			}
			
		} catch (Exception e) {
			System.out.println("Couldn't open or read the file.");
			e.printStackTrace();
		}
	}

}
