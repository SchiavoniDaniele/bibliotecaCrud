package bibliotecaCrud;

import java.sql.*;
import Supporti.*;

public class BibliotecaManager implements CRUD{
	//metodo per non stampare il testo tutto assieme
	PrintStyle print=new PrintStyle();
	//dati di accesso
	private final static String URL="jdbc:mysql://localhost:3306/biblioteca";
	private final static String USER="root";
	private final static String PASSWORD="";

public BibliotecaManager() {
	try {
		//alla dichiarazione del manager viene creata la tabella, se necessario
		Connection conn=DriverManager.getConnection(URL, USER, PASSWORD);
		Statement stmt=conn.createStatement();
		stmt.executeUpdate(CREATE_TABLE_AUTORI);
		stmt.executeUpdate(CREATE_TABLE_LIBRI);
		stmt.close();
		conn.close();
	}catch(SQLException e) {
		print.slowPrint("Errore di connessione al database");
		//e.printStackTrace();
	}
}

	public void addLibro(Libro libro) {
		try {
			Connection conn=DriverManager.getConnection(URL, USER, PASSWORD);
			PreparedStatement pstmt=conn.prepareStatement(ADD_LIBRO_QUERY);
			pstmt.setInt(1,libro.getAutore().getId());
			pstmt.setString(2,libro.getTitolo());
			pstmt.setInt(3, libro.getAnnoPubblicazione());
			pstmt.executeUpdate();
			print.slowPrint("Libro correttamente aggiunto al database");
			pstmt.close();
			conn.close();

		}catch(SQLException e) {
			print.slowPrint("Errore di connessione al database. Libro non aggiunto");
			//e.printStackTrace();
		}
	}

	public void removeLibro(int libro_id) {
		try {
			Connection conn=DriverManager.getConnection(URL, USER, PASSWORD);
			PreparedStatement pstmt=conn.prepareStatement(DELETE_LIBRO_QUERY);
			pstmt.setInt(1, libro_id);
			pstmt.executeUpdate();
			print.slowPrint("Libro rimosso correttamente dal database");
			pstmt.close();
			conn.close();
		}catch(SQLException e) {
			print.slowPrint("Errore di connessione al database. Libro non eliminato");
			//e.printStackTrace();
		}
	}

	public void addAutore(Autore autore) {
		try {
			Connection conn=DriverManager.getConnection(URL, USER, PASSWORD);
			PreparedStatement pstmt=conn.prepareStatement(ADD_AUTORE_QUERY);
			pstmt.setString(1, autore.getNome());
			pstmt.setString(2, autore.getCognome());
			pstmt.executeUpdate();
			print.slowPrint("Autore aggiunto correttamente al database");
			pstmt.close();
			conn.close();

		}catch(SQLException e) {
			print.slowPrint("Errore di connessione al database. Autore non aggiunto");
			//e.printStackTrace();
		}
	}

	public void removeAutore(int autore_id) {
		try {
			Connection conn=DriverManager.getConnection(URL, USER, PASSWORD);
			Statement stmtLibriCount=conn.createStatement();
			ResultSet rs=stmtLibriCount.executeQuery(SELECT_ALL_LIBRI);
			int libriDellAutore=0;
			int libriSenzaAutore=0;
			//il programma cicla la tabella e controlla quanti libri sono associati all'autore da eliminare
			while(rs.next()) {
				if(rs.getInt("autore_id")==autore_id) {
					libriDellAutore++;
				}
			}
			//se c'è almeno un libro associato, viene spostato a Autore Sconosciuto, che viene creato se non esiste
			if(libriDellAutore>0) {
				addAutoreSconosciutoIfNotExists();
				PreparedStatement pstmtModificaId=conn.prepareStatement(UPDATE_AUTOREID_LIBRI_QUERY_BY_AUTORE);
				pstmtModificaId.setInt(1, -1);
				pstmtModificaId.setInt(2,autore_id);
				libriSenzaAutore=pstmtModificaId.executeUpdate();
			}
			PreparedStatement pstmt=conn.prepareStatement(DELETE_AUTORE_QUERY);
			pstmt.setInt(1, autore_id);
			pstmt.executeUpdate();
			print.slowPrint("Autore rimosso correttamente dal database");
			print.slowPrint(libriSenzaAutore+" libri sono stati catalogati nella sezione Autore Sconosciuto");
			pstmt.close();
			conn.close();
		}catch(SQLException e) {
			print.slowPrint("Errore di connessione al database");
			//e.printStackTrace();
		}

	}
	
	private void addAutoreSconosciutoIfNotExists() {
	    if (isAutorePresente(-1)) {
	        return;
	    }
	    try {
	    	Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
	        Statement stmt = conn.createStatement(); 
	         
	        // Inserisce l'autore sconosciuto
	        stmt.execute("INSERT INTO autori (nome, cognome) VALUES('Autore', 'Sconosciuto')");
	        
	        // Ottiene l'ID dell'ultimo autore inserito
	        ResultSet rs = stmt.executeQuery("SELECT autore_id FROM autori ORDER BY autore_id DESC LIMIT 1");
	        int autore_id = -1;
	        while(rs.next()) {
	            autore_id = rs.getInt("autore_id");
	        }

	        // Aggiorna l'ID dell'autore appena inserito a -1
	        try (PreparedStatement ptsmt = conn.prepareStatement("UPDATE autori SET autore_id = -1 WHERE autore_id = ?")) {
	            ptsmt.setInt(1, autore_id);
	            ptsmt.executeUpdate();
	        }
	        
	    } catch (SQLException e) {
	        print.slowPrint("Errore di connessione al database");
	        //e.printStackTrace();
	    }
	}


	
	//overload della funzione updateLibro(con string titolo o int anno)
	public void updateLibro(int libro_id, String titolo) {
		try {
			Connection conn=DriverManager.getConnection(URL, USER, PASSWORD);
			PreparedStatement pstmt=conn.prepareStatement(UPDATE_TITOLO_LIBRI_QUERY);
			pstmt.setString(1, titolo);
			pstmt.setInt(2,libro_id);
			pstmt.executeUpdate();
			print.slowPrint("Titolo del libro modificato correttamente");
			pstmt.close();
			conn.close();
		}catch(SQLException e) {
			print.slowPrint("Errore di connessione al database. Titolo libro non modificato");
			//e.printStackTrace();
		}
	}
	public void updateLibro(int libro_id, int anno_pubblicazione) {
		try {
			Connection conn=DriverManager.getConnection(URL, USER, PASSWORD);
			PreparedStatement pstmt=conn.prepareStatement(UPDATE_ANNO_LIBRI_QUERY);
			pstmt.setInt(1, anno_pubblicazione);
			pstmt.setInt(2,libro_id);
			pstmt.executeUpdate();
			print.slowPrint("Anno di pubblicazione del libro modificato correttamente");
			pstmt.close();
			conn.close();
		}catch(SQLException e) {
			print.slowPrint("Errore di connessione al database. Anno di pubblicazione del libro non modificato");
			//e.printStackTrace();
		}
	}
	//funzione per modificare l'id dell'autore del libro
	public void updateAutoreLibro(int libro_id, int autore_id) {
		try {
			Connection conn=DriverManager.getConnection(URL, USER, PASSWORD);
			PreparedStatement pstmt=conn.prepareStatement(UPDATE_AUTOREID_LIBRI_QUERY_BY_LIBRO);
			pstmt.setInt(1, autore_id);
			pstmt.setInt(2,libro_id);
			pstmt.executeUpdate();
			print.slowPrint("Autore del libro modificato correttamente");
			pstmt.close();
			conn.close();
		}catch(SQLException e) {
			print.slowPrint("Errore di connessione al database. Anno di pubblicazione del libro non modificato");
			//e.printStackTrace();
		}
	}
	

	public void updateAutore(int autore_id, String campo, String nuovoValore) {
		//Siccome l'autore ha due campi, e entrambi come stringa, con una sola funzione si può modificare il campo necessario
		try {
			Connection conn=DriverManager.getConnection(URL, USER, PASSWORD);
			PreparedStatement pstmt=conn.prepareStatement(campo.equals("nome")?UPDATE_NOME_AUTORI_QUERY:UPDATE_COGNOME_AUTORI_QUERY);
			pstmt.setString(1, nuovoValore);
			pstmt.setInt(2, autore_id);
			pstmt.executeUpdate();
			print.slowPrint(campo+" autore modificato correttamente");
			pstmt.close();
			conn.close();
		}catch(SQLException e) {
			print.slowPrint("Errore di connessione al database. "+campo+" autore non modificato");
			//e.printStackTrace();
		}
	}

	public void printLibri() {
		try {
			String libri="";
			Connection conn=DriverManager.getConnection(URL, USER, PASSWORD);
			Statement stmt=conn.createStatement();
			ResultSet rs=stmt.executeQuery(SELECT_ALL_LIBRI);
			while(rs.next()) {
				int id=rs.getInt("libro_id");
				int autore_id=rs.getInt("autore_id");
				String titolo=rs.getString("titolo");
				int anno=rs.getInt("anno_pubblicazione");
				Autore autore=getAutoreById(autore_id);
				libri+="ID: "+String.format("%03d", id)+"\nID autore: "+autore_id+"("+autore.getNome()+" "
				+autore.getCognome()+")\nTitolo: "+titolo+"\nAnno di pubblicazione: "+(anno<0?Math.abs(anno)+"a.C.":anno)
				+"\n-----------\n";

			}
			if(libri.equals("")) {
				print.slowPrint("Nessun libro nel database");
			}else {
				print.slowPrint(libri);
			}
			stmt.close();
			conn.close();

		}catch(SQLException e) {
			print.slowPrint("Errore di connessione al database");
			//e.printStackTrace();
		}
	}

	public void printAutori() {
		try {
			String autori="";
			Connection conn=DriverManager.getConnection(URL, USER, PASSWORD);
			Statement stmt=conn.createStatement();
			ResultSet rs=stmt.executeQuery(SELECT_ALL_AUTORI);
			while(rs.next()) {
				Autore autore=new Autore(rs.getInt("autore_id"),rs.getString("nome"),rs.getString("cognome"));
				autori+=autore.toString()+"\n-----------\n";
			}
			if(autori.equals("")) {
				print.slowPrint("Nessun autore nel database");
			}else {
				print.slowPrint(autori);
			}
			stmt.close();
			conn.close();
		}catch(SQLException e) {
			print.slowPrint("Errore di connessione al database");
			//e.printStackTrace();
		}
	}
	//ricerca di autori per nome
	public void printAutoriPerNome(String valore) {
		try {
			String autori="";
			Connection conn=DriverManager.getConnection(URL,USER,PASSWORD);
			PreparedStatement pstmt=conn.prepareStatement(SELECT_AUTORE_PER_NOME);
			pstmt.setString(1, valore);
			ResultSet rs=pstmt.executeQuery();
			while(rs.next()) {
				int id=rs.getInt("autore_id");
				String nome=rs.getString("nome");
				String cognome=rs.getString("cognome");
				if(nome.equals(valore)) {
					Autore autore=new Autore(id,nome,cognome);
					autori+=autore+"\n-----------\n";
				}

			}
			if(autori.equals("")) {
				print.slowPrint("Nessun autore nel database");
			}else {
				print.slowPrint(autori);
			}
			pstmt.close();
			conn.close();
		}catch(SQLException e) {
			print.slowPrint("Errore di connessione al database");
			//e.printStackTrace();
		}
	}
	//ricerca di autori per cognome
	public void printAutoriPerCognome(String valore) {
		try {
			String autori="";
			Connection conn=DriverManager.getConnection(URL,USER,PASSWORD);
			PreparedStatement pstmt=conn.prepareStatement(SELECT_AUTORE_PER_COGNOME);
			pstmt.setString(1, valore);
			ResultSet rs=pstmt.executeQuery();
			while(rs.next()) {
				int id=rs.getInt("autore_id");
				String nome=rs.getString("nome");
				String cognome=rs.getString("cognome");
				if(cognome.equals(valore)) {
					Autore autore=new Autore(id,nome,cognome);
					autori+=autore+"\n-----------\n";
				}

			}
			if(autori.equals("")) {
				print.slowPrint("Nessun autore nel database");
			}else {
				print.slowPrint(autori);
			}
			pstmt.close();
			conn.close();
		}catch(SQLException e) {
			print.slowPrint("Errore di connessione al database");
			//e.printStackTrace();
		}
	}
	//ricerca libro per id
	public void printLibroPerId(int id) {
		try {
			String libro="";
			Connection conn=DriverManager.getConnection(URL, USER, PASSWORD);
			PreparedStatement pstmt=conn.prepareStatement(SELECT_LIBRO_FROM_ID);
			pstmt.setInt(1, id);
			ResultSet rs=pstmt.executeQuery();
			while(rs.next()) {
			int autore_id=rs.getInt("autore_id");
			String titolo=rs.getString("titolo");
			int anno=rs.getInt("anno_pubblicazione");
			Autore autore=getAutoreById(autore_id);
			libro+="ID: "+String.format("%03d", id)+"\nID autore: "+autore_id+"("+autore.getNome()+" "
			+autore.getCognome()+")\nTitolo: "+titolo+"\nAnno di pubblicazione: "+(anno<0?Math.abs(anno)+"a.C.":anno)
			+"\n-----------\n";			}
			if(libro.equals("")) {
				print.slowPrint("Nessun libro nel database corrisponde all'id cercato");
			}else {
				print.slowPrint(libro);
			}
			pstmt.close();
			conn.close();
		}catch(SQLException e) {
			print.slowPrint("Errore di connessione al database");
			//e.printStackTrace();
		}
	}
	//ricerca autore per id
	public void printAutorePerId(int id) {
		try {
			String autore="";
			Connection conn=DriverManager.getConnection(URL, USER, PASSWORD);
			PreparedStatement pstmt=conn.prepareStatement(SELECT_AUTORE_FROM_ID);
			pstmt.setInt(1, id);
			ResultSet rs=pstmt.executeQuery();
			while(rs.next()) {
			String nome=rs.getString("nome");
			String cognome=rs.getString("cognome");
			autore+="ID: "+String.format("%03d", id)+"\nNome: "+nome+" "+cognome;
			}
			if(autore.equals("")) {
				print.slowPrint("Nessun autore nel database corrisponde all'id cercato");
			}else {
				print.slowPrint(autore);
			}
			pstmt.close();
			conn.close();
		}catch(SQLException e) {
			print.slowPrint("Errore di connessione al database");
			//e.printStackTrace();
		}
	}
	//ricerca libri per id autore
	public void printLibriPerAutoreId(int id) {
		try {
			String libri="";
			Connection conn=DriverManager.getConnection(URL, USER, PASSWORD);
			PreparedStatement pstmt=conn.prepareStatement(SELECT_FROM_AUTORE);
			pstmt.setInt(1, id);
			ResultSet rs=pstmt.executeQuery();
			while(rs.next()) {
				int libro_id=rs.getInt("libro_id");
				String titolo=rs.getString("titolo");
				int anno=rs.getInt("anno_pubblicazione");
				Autore autore=getAutoreById(id);
				libri+="ID: "+String.format("%03d", libro_id)+"\nID autore: "+String.format("%03d",id)+"("+autore.getNome()+" "
				+autore.getCognome()+")\nTitolo: "+titolo+"\nAnno di pubblicazione: "+(anno<0?Math.abs(anno)+"a.C.":anno)
				+"\n-----------\n";
			}
			if(libri.equals("")) {
				print.slowPrint("Nessun libro nel database");
			}else {
				print.slowPrint(libri);
			}
			pstmt.close();
			conn.close();
			}
		catch(SQLException e) {
			print.slowPrint("Errore di connessione al database");
			//e.printStackTrace();
		}
	}
	//ricerca libri per intervallo di tempo
	public void printLibriPerIntervallo(int annoInizio, int annoFine) {
		try {
			String libri="";
			Connection conn=DriverManager.getConnection(URL, USER, PASSWORD);
			PreparedStatement pstmt=conn.prepareStatement(SELECT_LIBRI_PER_INTERVALLO);
			pstmt.setInt(1, annoInizio);
			pstmt.setInt(2, annoFine);
			ResultSet rs=pstmt.executeQuery();
			while(rs.next()){
				int id=rs.getInt("libro_id");
				int autore_id=rs.getInt("autore_id");
				String titolo=rs.getString("titolo");
				int anno=rs.getInt("anno_pubblicazione");
				Autore autore=getAutoreById(autore_id);
				libri+="ID: "+String.format("%03d", id)+"\nID autore: "+String.format("%03d",autore_id)+"("+autore.getNome()+" "
				+autore.getCognome()+")\nTitolo: "+titolo+"\nAnno di pubblicazione: "+(anno<0?Math.abs(anno)+"a.C.":anno)
				+"\n-----------\n";			}
			if(libri.equals("")) {
				print.slowPrint("Nessun libro nel database corrispondente all'intervallo cercato");
			}else {
				print.slowPrint(libri);
			}
			pstmt.close();
			conn.close();
		}catch(SQLException e) {
			print.slowPrint("Errore di connessione al database");
			//e.printStackTrace();
		}
	}
	//ricerca di libri per una o più parole chiave
	public void printLibriPerParolaChiave(String parolaChiave) {
		try {
			String libri="";
			Connection conn=DriverManager.getConnection(URL, USER, PASSWORD);
			PreparedStatement pstmt=conn.prepareStatement(SELECT_LIBRO_PER_CHIAVE);
			pstmt.setString(1, '%'+parolaChiave+'%');
			ResultSet rs=pstmt.executeQuery();
			while(rs.next()){
				int id=rs.getInt("libro_id");
				int autore_id=rs.getInt("autore_id");
				String titolo=rs.getString("titolo");
				int anno=rs.getInt("anno_pubblicazione");
				Autore autore=getAutoreById(autore_id);
				libri+="ID: "+String.format("%03d", id)+"\nID autore: "+String.format("%03d",autore_id)+"("+autore.getNome()+" "
				+autore.getCognome()+")\nTitolo: "+titolo+"\nAnno di pubblicazione: "+(anno<0?Math.abs(anno)+"a.C.":anno)
				+"\n-----------\n";			}
			if(libri.equals("")) {
				print.slowPrint("Nessun libro nel database corrispondente all'intervallo cercato");
			}else {
				print.slowPrint(libri);
			}
			pstmt.close();
			conn.close();
		}catch(SQLException e) {
			print.slowPrint("Errore di connessione al database");
			//e.printStackTrace();
		}
	}
	
	public boolean isAutorePresente(int autore_id) {
		try {
			Connection conn=DriverManager.getConnection(URL, USER, PASSWORD);
			PreparedStatement pstmt=conn.prepareStatement(SELECT_AUTORE_FROM_ID);
			pstmt.setInt(1, autore_id);
			ResultSet rs=pstmt.executeQuery();
			while(rs.next()) {
				if(rs.getInt("autore_id")==autore_id);
				return true;
			}
			return false;
		}catch(SQLException e) {
			System.out.println("Errore di connessione al database");
			//e.printStackTrace();
			return false;
		}
	}
	
	public boolean isLibroPresente(int libro_id) {
		try {
			Connection conn=DriverManager.getConnection(URL, USER, PASSWORD);
			PreparedStatement pstmt=conn.prepareStatement(SELECT_LIBRO_FROM_ID);
			pstmt.setInt(1, libro_id);
			ResultSet rs=pstmt.executeQuery();
			while(rs.next()) {
				if(rs.getInt("libro_id")==libro_id) {
					return true;
				}
			}
			return false;
		}catch(SQLException e) {
			System.out.println("Errore di connessione al database");
			//e.printStackTrace();
			return false;
		}
	}
	
	public Autore getAutoreById(int autore_id) {
		try {
			Connection conn=DriverManager.getConnection(URL, USER, PASSWORD);
			PreparedStatement pstmt=conn.prepareStatement(SELECT_AUTORE_FROM_ID);
			pstmt.setInt(1, autore_id);
			ResultSet rs=pstmt.executeQuery();
			while(rs.next()) {
				if(rs.getInt("autore_id")==autore_id) {
				String nome=rs.getString("nome");
				String cognome=rs.getString("cognome");
				return new Autore(autore_id,nome,cognome);
				}
			}
			return null;
				
				
		}catch(SQLException e) {
			System.out.println("Errore di connessione al database");
			//e.printStackTrace();
			return null;
		}
	}
	
	
}
