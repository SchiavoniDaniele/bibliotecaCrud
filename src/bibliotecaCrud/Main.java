package bibliotecaCrud;

import java.util.Scanner;

import Supporti.*;


public class Main {
	//dichiarazione classi statiche
	static PrintStyle print=new PrintStyle(); //stampa graduale del testo
	static metodiScanner in=new metodiScanner(); //metodi per gestire l'ingresso di date valide, interi e altre necessità
	static Scanner s=new Scanner(System.in);
	static BibliotecaManager biblioteca=new BibliotecaManager();


	public static void main(String[] args) {

		while(true) {
			System.out.println("=== Menù principale ===");
			print.slowPrint("1. Gestione libri"
					+ "\n2. Gestione autori"
					+ "\n0. Esci");
			int choice=in.getInt();
			switch(choice) {
			case 1:
				gestioneLibri();
				break;
			case 2:
				gestioneAutori();
				break;
			case 0:
				print.slowPrint("Uscita dal sistema");
				return;
			default:
				print.slowPrint("Inserisci un valore valido: ");
			}
		}

	}

	public static void gestioneLibri() {
		System.out.println("=== Gestione libri ===");
		while(true) {
			print.slowPrint("1. Aggiungi un nuovo libro"
					+ "\n2. Visualizza tutti i libri"
					+ "\n3. Visualizza libri per autore"
					+ "\n4. Visualizza libri per parola chiave"
					+ "\n5. Visualizza libri per intervallo di tempo"
					+ "\n6. Modifica un libro"
					+ "\n7. Cancella un libro"
					+ "\n8. Cerca libro per id"
					+ "\n0. Indietro");
			int choice=in.getInt();
			switch(choice) {
			case 1:
				biblioteca.printAutori();
				print.slowPrint("Inserisci l'id dell'autore o premi 0 per tornare indietro:");
				
				int autore_id=in.getInt();
				if(autore_id==0) {
					break;
				}
				Autore autore=biblioteca.getAutoreById(autore_id);
				if(autore==null) {
					print.slowPrint("Nessun autore trovato");
					break;
				}
				print.slowPrint("Inserisci il titolo del libro (max 150 caratteri):");
				String titolo=in.upperLower(s.nextLine());
				while(titolo.equals(null)||titolo.equals("")) {
					print.slowPrint("Inserire un titolo valido");
					print.slowPrint("Inserisci il titolo del libro:");
					titolo=in.upperLower(s.nextLine());
				}
				if(titolo.length()>150) {
					titolo=titolo.substring(0,149);
				}
				print.slowPrint("Inserisci anno di pubblicazione");
				int anno=in.getValidYear();
				Libro libro=new Libro(titolo, autore, anno);
				biblioteca.addLibro(libro);
				break;
			case 2:
				print.slowPrint("Libri disponibili:");
				biblioteca.printLibri();
				break;
			case 3:
				print.slowPrint("Inserisci id autore:");
				int id_autore=in.getInt();
				print.slowPrint("Libri disponibili:");
				biblioteca.printLibriPerAutoreId(id_autore);
				break;
			case 4:
				print.slowPrint("Inserisci il titolo un una parola chiave:");
				String parolaChiave=s.nextLine();
				print.slowPrint("Libri disponibili:");
				biblioteca.printLibriPerParolaChiave(parolaChiave);
				break;
			case 5:
				print.slowPrint("Inserisci l'anno di inizio intervallo:");
				int annoInizio=in.getValidYear();
				print.slowPrint("Inserisci l'anno di fine intervallo:");
				int annoFine=in.getValidYear();
				if(annoInizio>annoFine) {
					int annoTemp=annoFine;
					annoFine=annoInizio;
					annoInizio=annoTemp;
				}
				print.slowPrint("Libri disponibili:");
				biblioteca.printLibriPerIntervallo(annoInizio, annoFine);
				break;
			case 6:
				modificaLibro();
				break;
			case 7:
				print.slowPrint("Inserisci id libro:");
				int libro_id=in.getInt();
				if(biblioteca.isLibroPresente(libro_id)) {
					biblioteca.removeLibro(libro_id);
					break;
				}
				print.slowPrint("Nessun libro trovato all'id corrispondente");
				break;
			case 8:
				print.slowPrint("Inserisci id libro");
				libro_id=in.getInt();
				if(biblioteca.isLibroPresente(libro_id)) {
					biblioteca.printLibroPerId(libro_id);
				}else {
					print.slowPrint("Nessun libro nel database corrisponde all'id cercato");
				}
				break;
			case 0:
				return;
			default:
				print.slowPrint("Inserisci un valore valido: ");
			}
		}
	}

	private static void modificaLibro() {
		biblioteca.printLibri();
		print.slowPrint("Inserisci id libro o premi 0 per tornare indietro");
		int libro_id=in.getInt();
		if(libro_id==0) {
			return;
		}
		while(!biblioteca.isLibroPresente(libro_id)) {
			print.slowPrint("Nessun libro corrispondente all'id ricercato");
			print.slowPrint("Inserisci id libro o premi 0 per tornare indietro");
			libro_id=in.getInt();
			if(libro_id==0) {
				return;
			}
		}
		while(true) {
			print.slowPrint("Cosa vuoi modificare?"
					+ "\n1. Titolo"
					+ "\n2. Autore"
					+ "\n3. Anno pubblicazione"
					+ "\n0. Indietro");

			int choice=in.getInt();
			switch(choice) {
			case 1:
				String campo="titolo";
				print.slowPrint("Inserisci il nuovo titolo:");
				String titolo=in.upperLower(s.nextLine());
				biblioteca.updateLibro(libro_id, titolo);
				return;
			case 2:
				biblioteca.printAutori();
				campo="autore";
				print.slowPrint("Inserisci il nuovo id autore:");
				int autore_id=in.getInt();
				if(!biblioteca.isAutorePresente(autore_id)) {
					print.slowPrint("Nessun autore trovato");
					return;
				}
				biblioteca.updateAutoreLibro(libro_id, autore_id);
				return;
			case 3:
				campo="anno_pubblicazione";
				print.slowPrint("Inserisci il nuovo anno di pubblicazione:");
				int anno=in.getValidYear();
				biblioteca.updateLibro(libro_id, anno);
				return;
			case 0:
				return;
			default:
				print.slowPrint("Inserisci un valore valido");

			}
		}
	}
	
	public static void gestioneAutori() {
		System.out.println("=== Gestione autori ===");
		while(true) {
			print.slowPrint("1. Aggiungi un nuovo autore"
					+ "\n2. Visualizza tutti gli autori"
					+ "\n3. Visualizza un autore per id"
					+ "\n4. Visualizza autori per nome"
					+ "\n5. Visualizza autori per cognome"
					+ "\n6. Modifica un autore"
					+ "\n7. Cancella un autore"
					+ "\n8. Cerca autore per id"
					+ "\n0. Indietro");
			int choice=in.getInt();
			switch(choice) {
			case 1:
				print.slowPrint("Inserisci nome autore (max 50 caratteri):");
				String nome=in.upperLower(s.nextLine());
				if(nome.length()>50) {
					nome=nome.substring(0,49);
				}
				print.slowPrint("Inserisci cognome autore o premi 0 per saltare (max 50 caratteri):");
				String cognome=in.upperLower(s.nextLine());
				if(cognome.length()>50) {
					cognome=cognome.substring(0,49);
				}
				cognome=cognome.equals("0")?"":cognome;
				biblioteca.addAutore(new Autore(nome,cognome));
				break;
			case 2:
				print.slowPrint("Autori trovati:");
				biblioteca.printAutori();
				break;
			case 3:
				print.slowPrint("Inserisci id autore:");
				int autore_id=in.getInt();
				print.slowPrint("Autore trovato:");
				biblioteca.printAutorePerId(autore_id);
				break;
			case 4:
				print.slowPrint("Inserisci nome autore:");
				nome=in.upperLower(s.nextLine());
				print.slowPrint("Autori trovati: ");
				biblioteca.printAutoriPerNome(nome);
				break;
			case 5:
				print.slowPrint("Inserisci cognome autore:");
				cognome=in.upperLower(s.nextLine());
				print.slowPrint("Autori trovati: ");
				biblioteca.printAutoriPerCognome(cognome);
				break;
			case 6:
				modificaAutore();
				break;
			case 7:
				biblioteca.printAutori();
				print.slowPrint("Inserisci il numero id dell'autore da eliminare o premi 0 per tornare indietro:");
				autore_id=in.getInt();
				if(autore_id==0) {
					break;
				}
				if(autore_id==-1) {
					print.slowPrint("Impossibile eliminare Autore Sconosciuto");
					break;
				}
				if(!biblioteca.isAutorePresente(autore_id)) {
					print.slowPrint("Nessun autore corrispondente alla ricerca");
					break;
				}
				biblioteca.removeAutore(autore_id);
				break;
			case 8:
				print.slowPrint("Inserisci id autore");
				autore_id=in.getInt();
				if(biblioteca.isAutorePresente(autore_id)) {
					biblioteca.printAutorePerId(autore_id);
				}else {
					print.slowPrint("Nessun autore nel database corrisponde all'id cercato");
				}
				break;
			case 0:
				return;
				default:
					print.slowPrint("Inserisci un valore valido");
				
			}
		}
	}
	private static void modificaAutore() {
		biblioteca.printAutori();
		print.slowPrint("Inserisci id autore:");
		int autore_id=in.getInt();
		if(!biblioteca.isAutorePresente(autore_id)) {
			print.slowPrint("Nessun autore trovato");
			return;
		}
		print.slowPrint("Cosa vuoi modificare?"
				+ "\n1. Nome"
				+ "\n2. Cognome"
				+ "\nPremi qualsiasi altro tasto per tornare indietro");
		String scelta=s.nextLine();
		String campo="";
		if(scelta.equals("1")) {
			campo="nome";
		}else if(scelta.equals("2")) {
			campo="cognome";
		}else {
			return;
		}
		print.slowPrint("Inserisci il nuovo "+campo+":");
		String nuovoValore=in.upperLower(s.nextLine());
		biblioteca.updateAutore(autore_id, campo, nuovoValore);
	}

}
