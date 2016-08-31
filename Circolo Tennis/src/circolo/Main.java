package circolo;

import java.sql.SQLException;
import java.util.ArrayList;

public class Main {
    public static void main(String args[]) throws SQLException {
        Database db = new Database();
        ArrayList<Giocatore> list = new ArrayList<Giocatore>();
        Giocatore pippo = new Giocatore("ciccio","graziani");
        //list = db.ricercaGiocatore(pippo);
    }

}
