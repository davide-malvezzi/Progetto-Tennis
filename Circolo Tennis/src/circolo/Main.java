package circolo;

import java.sql.SQLException;
import java.time.LocalDateTime;

public class Main {
    public static void main(String args[]) throws SQLException {
        Database db = new Database();

        /*Giocatore pippo = new Giocatore("pippo","plutarco", Date.valueOf("1995-07-12"),"ABCDEF1234","M","Via SanMareco");
        Giocatore pluto = new Giocatore("pluto","plutarco", Date.valueOf("1995-07-12"),"ABCDEF1235","M","Via SanMareco");
        db.InserisciGiocatore(pippo);
        db.InserisciGiocatore(pluto);

        Partita partita = new Partita(pippo,pluto,Date.valueOf("1995-07-12"),a);
        db.InserisciPartita(partita);
        MatchPlay mp = new MatchPlay();
        mp.GeneraGironi();*/

            Campo a = new Campo(1, "terra", "Interno");
           // db.InserisciCampo(a);

       LocalDateTime di = LocalDateTime.of(2016,6,25,16,0);
        LocalDateTime df = LocalDateTime.of(2016,6,25,18,0);
        Prenotazione pr = new Prenotazione(di,df,a);
        //db.InserisciPrenotazione(pr);
        /*Calendar cal = Calendar.getInstance();
        cal.setTime(Date.valueOf("2016-06-15"));*/

       /* String po = String.valueOf(di.getDayOfMonth());
        po = String.valueOf(di.getYear());
        po = "0".concat(String.valueOf(di.getMonthValue()));*/

    }

}
