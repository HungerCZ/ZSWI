import java.util.Arrays;


public class RealtimeAnalyser {
	
	/** velikost bufferu namerenych hodnot */
	int bufferSize = 32;
	
	/** Self explanatory */
	int prahovaHodnotaMrkani = 400;
	
	public RealtimeAnalyser() {
		
	}
	
	/**Docasny konstruktor, prebere pole namerenych dat a simuluje realtime odesilani
	 * dat do analyzatoru.
	 * 
	 * @param data
	 */
	public RealtimeAnalyser(int data[]) {
		int zpracovano = 0;
		int pocetMrknuti = 0;
		boolean predchoziPaketMrknuto = false;
		
		for(int i = 0; i < data.length; i += bufferSize){
			int [] packet = Arrays.copyOfRange(data, i, i + bufferSize);
			zpracovano++;
			
			
			boolean mrknuto = analyse(packet, bufferSize);
			if(mrknuto && !predchoziPaketMrknuto) pocetMrknuti++;
			
			System.out.println("Paket c. " + zpracovano + " - vysledek: " + mrknuto);
		
			predchoziPaketMrknuto = mrknuto;
		}
		
		System.out.println("Detekovane mrknuti: " + pocetMrknuti);
	}
	
	
	/**Prevezme datovy paket a jeho ocekavanou velikost
	 * 
	 * V datech vyhledava hodnoty vetsi nez prahova hodnota mrknuti
	 * je-li hodnota nalezena, kouka se pred ni, zda i predchazejici
	 * hodnota byla kladne vyhodnocena. Pokud ano je nove nalezena
	 * hodnota korektni (spojita s predchozi) oznaci se jako korektni
	 * pokud predchazejici hodnota neni korektni (popr. kladne hodnocena)
	 * pak je nove nalezena hodnota oznacena jako kladna (nikoli korektni)
	 * 
	 *  na konci se urci procentuelni podil korektnich a kladnych hodnot
	 *  a pokud jsme s procenty zustali v toleranci je paket vyhodnocen 
	 *  jako obsahujici mrknuti
	 * 
	 * @param data - namerene hodnoty
	 * @param size - velikost paketu (bufferu)
	 * @return true - pokud bylo detekovano mrknuti
	 */
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
