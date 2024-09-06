package Supporti;

import java.util.concurrent.TimeUnit;

public class PrintStyle {
	//metodo per non far apparire tutto assieme il testo sullo schermo
public void slowPrint(String string) {
	if(string.equals(null)||string.equals("")) {
		return;
	}
	for(int i=0;i<string.length();i++) {
		System.out.print(string.substring(i,i+1));
			try {
				TimeUnit.MILLISECONDS.sleep(10);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	System.out.println();
	}
}

