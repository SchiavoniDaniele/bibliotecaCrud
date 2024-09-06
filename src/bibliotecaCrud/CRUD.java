package bibliotecaCrud;

public interface CRUD {
	//creazione delle tabelle
final static String CREATE_TABLE_AUTORI=
"CREATE TABLE IF NOT EXISTS autori ("
+ "autore_id INT AUTO_INCREMENT PRIMARY KEY,"
+ "nome VARCHAR(50) NOT NULL,"
+ "cognome VARCHAR(50)"
+ ")";

final static String CREATE_TABLE_LIBRI=
"CREATE TABLE IF NOT EXISTS libri("
+ "libro_id INT AUTO_INCREMENT PRIMARY KEY,"
+ "autore_id INT NOT NULL,"
+ "titolo VARCHAR(150) NOT NULL,"
+ "anno_pubblicazione INT NOT NULL,"
+ "FOREIGN KEY (autore_id) REFERENCES autori(autore_id)"
+ ")";
	
	//Query per i select
final static String SELECT_LIBRO_FROM_ID="SELECT * FROM libri WHERE libro_id=?";
final static String SELECT_AUTORE_FROM_ID="SELECT * FROM autori WHERE autore_id=?";
final static String SELECT_FROM_AUTORE="SELECT * FROM libri WHERE autore_id=?";
final static String SELECT_ALL_AUTORI="SELECT * FROM autori";
final static String SELECT_ALL_LIBRI="SELECT * FROM libri";
final static String SELECT_AUTORE_PER_NOME="SELECT * FROM autori WHERE nome=?";
final static String SELECT_AUTORE_PER_COGNOME="SELECT * FROM autori WHERE cognome=?";
//select libro per parola chiave
final static String SELECT_LIBRO_PER_CHIAVE="SELECT * FROM libri WHERE titolo LIKE ?";
//select libro per intervallo di anni
final static String SELECT_LIBRI_PER_INTERVALLO="SELECT * FROM libri WHERE anno_pubblicazione>=? AND anno_pubblicazione<=?";


	//Query per aggiungere nel database
final static String ADD_LIBRO_QUERY="INSERT INTO libri (autore_id, titolo,anno_pubblicazione) VALUES (?,?,?)";
final static String ADD_AUTORE_QUERY="INSERT INTO autori (nome,cognome) VALUES(?,?)";

	//Query per eliminare dal database
final static String DELETE_AUTORE_QUERY="DELETE FROM autori WHERE autore_id=?";
final static String DELETE_LIBRO_QUERY="DELETE FROM libri WHERE libro_id=? ";

	//Query per modificare un campo
final static String UPDATE_NOME_AUTORI_QUERY="UPDATE autori SET nome=? WHERE autore_id=?";//modifica nome autore
final static String UPDATE_COGNOME_AUTORI_QUERY="UPDATE autori SET cognome=? WHERE autore_id=?";//modifica cognome
final static String UPDATE_ID_AUTORI_QUERY="UPDATE autori SET autore_id=? WHERE autore_id=?";//modifica id autore, sconsigliato usare
final static String UPDATE_TITOLO_LIBRI_QUERY="UPDATE libri SET titolo=? WHERE libro_id=?";//modifica titolo
final static String UPDATE_AUTOREID_LIBRI_QUERY_BY_AUTORE="UPDATE libri SET autore_id=? WHERE autore_id=?";//modifica id autore nel libro
final static String UPDATE_AUTOREID_LIBRI_QUERY_BY_LIBRO="UPDATE libri SET autore_id=? WHERE libro_id=?";//modifica id autore nel libro
final static String UPDATE_ANNO_LIBRI_QUERY="UPDATE libri SET anno_pubblicazione=? WHERE libro_id=?";//modifica anno pubblicazione




}
