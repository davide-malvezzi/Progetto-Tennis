package circolo;


import java.time.LocalDateTime;

public class Prenotazione {
    LocalDateTime data_inizio;
    LocalDateTime data_fine;
    Campo field;
    int pagato;
    Giocatore player1, player2;
    double importo;

    public Prenotazione(LocalDateTime dataInizio, LocalDateTime dataFine, Campo field) {
        this.data_inizio = dataInizio;
        this.data_fine = dataFine;
        this.field = field;
    }

    public LocalDateTime getData_inizio() {
        return data_inizio;
    }

    public void setData_inizio(LocalDateTime data_inizio) {
        this.data_inizio = data_inizio;
    }

    public LocalDateTime getData_fine() {
        return data_fine;
    }

    public void setData_fine(LocalDateTime data_fine) {
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
