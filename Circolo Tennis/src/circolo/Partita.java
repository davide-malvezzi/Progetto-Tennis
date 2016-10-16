package circolo;


import javafx.beans.property.*;

import java.time.LocalDate;

public class Partita {
	private ObjectProperty<LocalDate> data_partita;
	private IntegerProperty girone;
	private ObjectProperty<Giocatore> player1,player2;
	private ObjectProperty<Campo> field;
	private StringProperty risultato;
	private ObjectProperty<Giocatore> vincitore;

	public Partita(Giocatore giocatore1, Giocatore giocatore2,Integer girone){
		this.player1 = new SimpleObjectProperty<>(giocatore1);
		this.player2 = new SimpleObjectProperty<>(giocatore2);
		this.girone = new SimpleIntegerProperty(girone);
	}

	public LocalDate getData_partita() {
		return data_partita.get();
	}

	public ObjectProperty<LocalDate> data_partitaProperty() {
		return data_partita;
	}

	public void setData_partita(LocalDate data_partita) {
		this.data_partita.set(data_partita);
	}

	public int getGirone() {
		return girone.get();
	}

	public IntegerProperty gironeProperty() {
		return girone;
	}

	public void setGirone(int girone) {
		this.girone.set(girone);
	}

	public Giocatore getPlayer1() {
		return player1.get();
	}

	public ObjectProperty<Giocatore> player1Property() {
		return player1;
	}

	public void setPlayer1(Giocatore player1) {
		this.player1.set(player1);
	}

	public Giocatore getPlayer2() {
		return player2.get();
	}

	public ObjectProperty<Giocatore> player2Property() {
		return player2;
	}

	public void setPlayer2(Giocatore player2) {
		this.player2.set(player2);
	}

	public Campo getField() {
		return field.get();
	}

	public ObjectProperty<Campo> fieldProperty() {
		return field;
	}

	public void setField(Campo field) {
		this.field.set(field);
	}

	public String getRisultato() {
		return risultato.get();
	}

	public StringProperty risultatoProperty() {
		return risultato;
	}

	public void setRisultato(String risultato) {
		this.risultato.set(risultato);
	}

	public Giocatore getVincitore() {
		return vincitore.get();
	}

	public ObjectProperty<Giocatore> vincitoreProperty() {
		return vincitore;
	}

	public void setVincitore(Giocatore vincitore) {
		this.vincitore.set(vincitore);
	}
}
