import java.io.File;
import java.util.Scanner;


public class Blinking {
	

	public static void main(String[] args) {
		offlineReading(args[0]);
		
	}
	
	/**Vola posloupnost metod pro rozbor a vizualizaci offline dat
	 * 
	 * @param inFile - název vstupního souboru
	 */
	public static void offlineReading(String inFile){
		int [] data = readInput(inFile);
		new Vizualizace(data);
	}
	
	
	/**Precte ze souboru CSV vsechna data a ulozi je do pole 
	 * 
	 * @param fileName - nazev vstupniuho souboru
	 */
	public static int [] readInput(String fileName){
		try {
			File inFile = new File(fileName);
			Scanner sc = new Scanner(inFile);
			
			String [] dataString = sc.next().split(",");
			sc.close();
			
			int [] data = new int[dataString.length];
			
			for(int i = 0; i < dataString.length; i++){
				data[i] = Integer.parseInt(dataString[i]);
			}
			
			return data;
			
		} catch (Exception e) {
			System.out.println("Couldn't open or read the file.");
			e.printStackTrace();
		}
		return null;
	}

}
