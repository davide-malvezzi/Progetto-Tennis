package circolo;


import java.sql.Date;

public class Prenotazione {
	Date data_inizio;
	Date data_fine;
	Campo field;
	int pagato;
	Giocatore player1,player2;
	double importo;

	public Prenotazione(Date data1, Date data2, Campo field){
		this.data_inizio=data1;
		this.data_fine=data2;
		this.field=field;
	}

	public Date getData_inizio() {
		return data_inizio;
	}

	public void setData_inizio(Date data_inizio) {
		this.data_inizio = data_inizio;
	}

	public Date getData_fine() {
		return data_fine;
	}

	public void setData_fine(Date data_fine) {
		this.data_fine = data_fine;
	}

	public Campo getField() {
		return field;
	}

	public void setField(Campo field) {
		this.field = field;
	}

	public int getPagato() {
		return pagato;
	}

	public void setPagato(int pagato) {
		this.pagato = pagato;
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

	public double getImporto() {
		return importo;
	}

	public void setImporto(double importo) {
		this.importo = importo;
	}
	
	
}
