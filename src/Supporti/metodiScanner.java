package Supporti;

import java.time.LocalDate;
import java.util.Scanner;

public class metodiScanner {
	Scanner s=new Scanner(System.in);
	PrintStyle print=new PrintStyle();

	//metodo per ottenere un intero valido
	public int getInt() {
		while(!s.hasNextInt()) {
			print.slowPrint("Inserisci un valore valido: ");
			s.nextLine();
		}
		int i=s.nextInt();
		s.nextLine();
		return i;
	}
	//metodo per ottenere un anno non futuro
	public int getValidYear() {
		int year=getInt();
		if(year>LocalDate.now().getYear()) {
			print.slowPrint("Non puoi inserire un valore nel futuro."
					+ "\nInserisci un valore valido: ");
			return getValidYear();
		}else {
			return year;
		}
	}
	//metodo per rendere la prima lettera di ogni parole maiuscola, e le successive minuscole
	public String upperLower(String string) {
		if(string.equals(null)||string.equals("")) {
			return string;
		}
		if(!string.contains(" ")) {
		return string.substring(0,1).toUpperCase()+string.substring(1).toLowerCase();
		}else {
			String[] arrayString=string.split(" ");
			String newString="";
			for(String tempString:arrayString) {
				newString+=(upperLower(tempString))+" ";
			}
			return newString.trim();
		}
	}
}
