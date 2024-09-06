package bibliotecaCrud;

public class Libro {
private int id;
private String titolo;
private Autore autore;
private int annoPubblicazione;

public Libro(String titolo, Autore autore, int annoPubblicazione) {
	this.titolo=titolo;
	this.autore=autore;
	this.annoPubblicazione=annoPubblicazione;
}
public Libro(int id, String titolo, Autore autore, int annoPubblicazione) {
	this.id=id;
	this.titolo=titolo;
	this.autore=autore;
	this.annoPubblicazione=annoPubblicazione;
}
public int getId() {
	return id;
}
public void setId(int id) {
	this.id = id;
}
public String getTitolo() {
	return titolo;
}
public void setTitolo(String titolo) {
	this.titolo = titolo;
}
public Autore getAutore() {
	return autore;
}
public void setAutore(Autore autore) {
	this.autore = autore;
}
public int getAnnoPubblicazione() {
	return annoPubblicazione;
}
public void setAnnoPubblicazione(int annoPubblicazione) {
	this.annoPubblicazione = annoPubblicazione;
}
@Override
public String toString() {
	return "ID: "+String.format("%03d", id)+"\nTitolo: "+titolo+"\nAutore: "
+autore.getNome()+" "+autore.getCognome()+"\nAnno pubblicazione: "+annoPubblicazione;
}


}
