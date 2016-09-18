package circolo;


import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Campo {
	IntegerProperty numero;
	StringProperty superficie;
	StringProperty posizione; // interno o esterno

	public Campo(){
		numero = new SimpleIntegerProperty();
		superficie = new SimpleStringProperty();
		posizione = new SimpleStringProperty();
	}

	public Campo(int numero){
		this.numero = new SimpleIntegerProperty(numero);
	}

	public Campo(int numero, String superficie, String posizione){
		this.numero = new SimpleIntegerProperty(numero);
		this.superficie = new SimpleStringProperty(superficie);
		this.posizione = new SimpleStringProperty(posizione);
	}

	public int getNumero_campo() {
		return numero.get();
	}

	public IntegerProperty getNumeroCampoProperty(){
		return numero;
	}

	public void setNumero_campo(int numero_campo) {
		this.numero.set(numero_campo);
	}

	public String getSuperficie() {
		return superficie.get();
	}

	public StringProperty getSuperficieProperty(){
		return superficie;
	}

	public void setSuperficie(String superficie) {
		this.superficie.set(superficie);
	}

	public String getPosizione() {
		return posizione.get();
	}

	public StringProperty getPosizioneProperty(){
		return posizione;
	}

	public void setPosizione(String posizione) {
		this.posizione.set(posizione);
	}

}
