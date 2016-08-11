package circolo;

import java.sql.SQLException;

public class Main {
    public static void main(String args[]) throws SQLException {
        Database db = new Database();
        /*Giocatore pippo = new Giocatore("pippo","plutarco", Date.valueOf("1995-07-12"),"ABCDEF1234","M","Via SanMareco");
        Giocatore pluto = new Giocatore("pluto","plutarco", Date.valueOf("1995-07-12"),"ABCDEF1235","M","Via SanMareco");
        db.InserisciGiocatore(pippo);
        db.InserisciGiocatore(pluto);
        Campo a = new Campo(1,null,null);
        Partita partita = new Partita(pippo,pluto,Date.valueOf("1995-07-12"),a);
        db.InserisciPartita(partita);*/
        MatchPlay mp = new MatchPlay();
        mp.GeneraGironi();
    }

}
