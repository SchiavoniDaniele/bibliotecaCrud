package bibliotecaCrud;

public class Autore {
private int id;
private String nome;
private String cognome;

public Autore(String nome, String cognome) {
	this.nome=nome;
	this.cognome=cognome;
}
//se l'autore ha un nome d'arte, il cognome non serve
public Autore(String nome) {
	this.nome=nome;
}
public Autore(int id,String nome, String cognome) {
	this.id=id;
	this.nome=nome;
	this.cognome=cognome;
}
public Autore(int id, String nome) {
	this.id=id;
	this.nome=nome;
}
public int getId() {
	return id;
}
public void setId(int id) {
	this.id = id;
}
public String getNome() {
	return nome;
}
public void setNome(String nome) {
	this.nome = nome;
}
public String getCognome() {
	return cognome;
}
public void setCognome(String cognome) {
	this.cognome = cognome;
}
@Override
public String toString() {
	return "ID: "+String.format("%03d", id)+"\nNome: "+nome+" "+cognome;
}

}
