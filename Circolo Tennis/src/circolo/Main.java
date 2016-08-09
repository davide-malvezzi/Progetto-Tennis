package circolo;

import java.sql.Date;
import java.sql.SQLException;

public class Main {
    public static void main(String args[]) throws SQLException {
        Database db = new Database();
        Giocatore pippo = new Giocatore("pippo","plutarco", Date.valueOf("1995-07-12"),"ABCDEF1234","M","Via SanMareco");
        db.InserisciGiocatore(pippo);
    }

}
