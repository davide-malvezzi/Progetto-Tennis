package circolo;


import javafx.beans.property.*;

import java.time.LocalDate;

/**
 * Classe che modella le partite del Matchplay
 */
public class Partita {
	/**
	 * Data della partita
	 */
	private ObjectProperty<LocalDate> data_partita = new SimpleObjectProperty<>();
	/**
	 * Numero identificativo della partita
	 */
	private int num_partita;
	/**
	 * Girone di competenza della partita
	 */
	private IntegerProperty girone = new SimpleIntegerProperty();
	/**
	 * Turno in cui la partita avviene
	 */
	private IntegerProperty turno = new SimpleIntegerProperty();
	/**
	 * Giocatore che prende parte alla partita
	 * @see GiocatoreMatchplay
	 */
	private ObjectProperty<Giocatore> player1,player2;
	/**
	 * Risultato della partita
	 */
	private StringProperty risultato = new SimpleStringProperty();

	/**
	 * Costruttore Parziale
	 * @param giocatore1
	 * @param giocatore2
	 * @param girone
	 * @param turno
	 * @see Partita#player1
	 * @see Partita#player2
	 * @see Partita#girone
	 * @see Partita#turno
	 */
	public Partita(Giocatore giocatore1, Giocatore giocatore2,int girone,int turno){
		this.player1 = new SimpleObjectProperty<>(giocatore1);
		this.player2 = new SimpleObjectProperty<>(giocatore2);
		this.girone = new SimpleIntegerProperty(girone);
		this.turno = new SimpleIntegerProperty(turno);
	}

	/**
	 * @return la data della partita
	 * @see Partita#data_partita
	 */
	public LocalDate getData_partita() {
		return data_partita.get();
	}

	/**
	 * @return la data della partita come oggetto property
	 * @see Partita#data_partita
	 */
	public ObjectProperty<LocalDate> data_partitaProperty() {
		return data_partita;
	}

	/**
	 * Modifica la data della partita
	 * @see Partita#data_partita
	 * @param data_partita data della partita
	 */
	public void setData_partita(LocalDate data_partita) {
		this.data_partita.set(data_partita);
	}

	/**
	 * @return il girone della parita
	 * @see Partita#girone
	 */
	public int getGirone() {
		return girone.get();
	}

	/**
	 * @return il girone della partita come property
	 * @see Partita#girone
	 */
	public IntegerProperty gironeProperty() {
		return girone;
	}

	/**
	 * Modifica il girone della partita
	 * @see Partita#girone
	 * @param girone girone della partita da settare
	 */
	public void setGirone(int girone) {
		this.girone.set(girone);
	}

	/**
	 * @return  il primo giocatore della partita
	 * @see Partita#player1
	 */
	public Giocatore getPlayer1() {
		return player1.get();
	}

	/**
	 * @return  il primo giocatore della partita come property
	 * @see Partita#player1
	 */
	public ObjectProperty<Giocatore> player1Property() {
		return player1;
	}

	/**
	 * Modifica il primo giocatore della partita
	 * @see Partita#player1
	 * @param player1 Primo giocatore della partita
	 */
	public void setPlayer1(Giocatore player1) {
		this.player1.set(player1);
	}

	/**
	 * @return  il secondo giocatore della partita
	 * @see Partita#player2
	 */
	public Giocatore getPlayer2() {
		return player2.get();
	}

	/**
	 * @return il secondo giocatore della partita come property
	 * @see Partita#player2
	 */
	public ObjectProperty<Giocatore> player2Property() {
		return player2;
	}

	/**
	 * Modifica il secondo giocatore della partita
	 * @see Partita#player2
	 * @param player2 secondo giocatore della partita
	 */
	public void setPlayer2(Giocatore player2) {
		this.player2.set(player2);
	}

	/**
	 * @return il risultato della partita
	 * @see Partita#risultato
	 */
	public String getRisultato() {
		return risultato.get();
	}

	/**
	 * @return il risultato della partita come property
	 * @see Partita#risultato
	 */
	public StringProperty risultatoProperty() {
		return risultato;
	}

	/**
	 * Modifica il risultato della partita
	 * @see Partita#risultato
	 * @param risultato della partita da settare
	 */
	public void setRisultato(String risultato) {
		this.risultato.set(risultato);
	}

	/**
	 * @return il turno della partita
	 * @see Partita#turno
	 */
	public int getTurno() {
		return turno.get();
	}

	/**
	 * @return il turno della partita come property
	 * @see Partita#turno
	 */
	public IntegerProperty turnoProperty() {
		return turno;
	}

	/**
	 * Modifica il turno della partita
	 * @see Partita#turno
	 * @param turno turno della partita da settare
	 */
	public void setTurno(int turno) {
		this.turno.set(turno);
	}

	/**
	 * @return il numero della partita
	 * @see Partita#num_partita
	 */
	public int getNum_partita() {
		return num_partita;
	}


	/**
	 * Modifica il numero della partita
	 * @see Partita#num_partita
	 * @param num_partita numero della partita da settare
	 */
	public void setNum_partita(int num_partita) {
		this.num_partita = num_partita;
	}
}
