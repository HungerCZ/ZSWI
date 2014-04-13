import java.util.Arrays;


public class RealtimeAnalyser {
	
	/** velikost bufferu namerenych hodnot */
	int bufferSize = 64;
	
	int prahovaHodnotaMrkani = 400;
	
	public RealtimeAnalyser() {
		
	}
	
	public RealtimeAnalyser(int data[]) {
		int zpracovano = 0;
		for(int i = 0; i < data.length; i += bufferSize){
			int [] packet = Arrays.copyOfRange(data, i, i + bufferSize);
			zpracovano++;
			System.out.println("Paket c. " + zpracovano + " - vysledek: " + analyse(packet, bufferSize));
		}
	}
	
	public boolean analyse(int[] data, int size){
		int ano = 0;
		int korektni = 0;
		int tolerance = 20; //tolerance v procentech
		
		for(int i = 1; i < size; i++){
			if(data[i] > prahovaHodnotaMrkani){
				ano++;
				if(data[i-1] > prahovaHodnotaMrkani){
					korektni++;
				}
			}
		}
		
		if(ano == 0 && korektni == 0) return false;
		
		System.out.println("\nAno: " + ano + ", Korektnich: " + korektni);
		int procentSpravne = (korektni * 100)/ano;
		System.out.println("Procent spravne: " + procentSpravne);
		
		return procentSpravne > (100 - tolerance);
	}
}
