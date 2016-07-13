package circolo;


import java.sql.Date;

public class Partite {
	Date data_partita;
	int numero_partita;
	Giocatore player1,player2;
	Campo field;
	Risultato risultato;
	Giocatore vincitore;
	
	public Partite(Giocatore player1,Giocatore player2,Date data,Campo field){
		this.player1=player1;
		this.player2=player2;
		this.data_partita=data;
		this.field=field;
		
	}
	
	
	public Date getData_partita() {
		return data_partita;
	}


	public void setData_partita(Date data_partita) {
		this.data_partita = data_partita;
	}


	public int getNumero_partita() {
		return numero_partita;
	}
	public void setNumero_partita(int numero_partita) {
		this.numero_partita = numero_partita;
	}

	public Giocatore getPlayer1() {
		return player1;
	}


	public void setPlayer1(Giocatore player1) {
		this.player1 = player1;
	}


	public Giocatore getPlayer2() {
		return player2;
	}


	public void setPlayer2(Giocatore player2) {
		this.player2 = player2;
	}


	public Campo getField() {
		return field;
	}
	public void setField(Campo field) {
		this.field = field;
	}
	public Risultato getRisultato() {
		return risultato;
	}
	public void setRisultato(Risultato risultato) {
		this.risultato = risultato;
	}
	public Giocatore getVincitore() {
		return this.vincitore;
	}
	public void setVincitore(Giocatore vincitore) {
		this.vincitore = vincitore;
	}
	
	
}
