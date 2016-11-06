package circolo;


import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Classe che modella i campi del circolo
 */
public class Campo {
	/**
	 * Numero identificativo del campo
	 */
	private IntegerProperty numero;
	/**
	 * Superficie del terreno del campo
	 */
	private StringProperty superficie;
	/**
	 * Indica se il campo è interno o esterno
	 */
	private StringProperty posizione;

	/**
	 * Costruttore di default
	 */
	public Campo(){
		numero = new SimpleIntegerProperty();
		superficie = new SimpleStringProperty();
		posizione = new SimpleStringProperty();
	}

	/**
	 * COstruttore di campo con solo il numero
	 * @param numero
	 * @see Campo#numero
	 */
	public Campo(int numero){
		this.numero = new SimpleIntegerProperty(numero);
	}

	/**
	 * Cstruttore completo di campo
	 * @param numero
	 * @param superficie
	 * @param posizione
	 * @see Campo#numero
	 * @see Campo#superficie
	 * @see Campo#posizione
	 */
	public Campo(int numero, String superficie, String posizione){
		this.numero = new SimpleIntegerProperty(numero);
		this.superficie = new SimpleStringProperty(superficie);
		this.posizione = new SimpleStringProperty(posizione);
	}

	/**
	 * @return il numero del campo
	 * @see Campo#numero
	 */
	public int getNumero_campo() {
		return numero.get();
	}

	/**
	 * @return il numero del campo come property
	 * @see Campo#numero
	 */
	public IntegerProperty getNumeroCampoProperty(){
		return numero;
	}

	/**
	 * Modifica il numero del campo
	 * @see Campo#numero
	 * @param numero_campo Numero del campo da settare
	 */
	public void setNumero_campo(int numero_campo) {
		this.numero.set(numero_campo);
	}

	/**
	 * @return la superficie del campo
	 * @see Campo#superficie
	 */
	public String getSuperficie() {
		return superficie.get();
	}

	/**
	 * @return la superficie del campo come property
	 * @see Campo#superficie
	 */
	public StringProperty getSuperficieProperty(){
		return superficie;
	}

	/**
	 * Modifica la superficie del campo
	 * @see Campo#superficie
	 * @param superficie superficie del campo da settare
	 */
	public void setSuperficie(String superficie) {
		this.superficie.set(superficie);
	}

	/**
	 * @return la posizione del campo
	 * @see Campo#posizione
	 */
	public String getPosizione() {
		return posizione.get();
	}

	/**
	 * @return la posizione del campo come property
	 * @see Campo#posizione
	 */
	public StringProperty getPosizioneProperty(){
		return posizione;
	}

	/**
	 * Modifica la posizione del campo
	 * @see Campo#posizione
	 * @param posizione posizione del campo da settare
	 */
	public void setPosizione(String posizione) {
		this.posizione.set(posizione);
	}

}
