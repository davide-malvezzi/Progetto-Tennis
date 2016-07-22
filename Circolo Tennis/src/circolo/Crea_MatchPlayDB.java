package circolo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Crea_MatchPlayDB {

    private Giocatore giocatore;
    private Connection con;    //connessione al DB
    ConnessioneDB database = new ConnessioneDB();

    public Crea_MatchPlayDB(Giocatore giocatore) {

        this.giocatore = giocatore;
        con = database.Connessione();
        PreparedStatement prpst = null;

        try {
            prpst = con.prepareStatement("INSERT INTO Partecipanti VALUES(?,?,?,?,?,?,?,?,?,?)");   //inserisce i valori al posto delle '?'
            prpst.setString(1, giocatore.getNome());
            prpst.setString(2, giocatore.getCognome());
            prpst.setDate(3, giocatore.getData_nascita());
            prpst.setString(4, giocatore.getCF());
            prpst.setString(5, giocatore.getGenere());
            prpst.setString(6, giocatore.getIndirizzo());
            prpst.setDouble(7, giocatore.getClassifica_FIT());
            prpst.setInt(8, giocatore.getFascia());
            prpst.setBoolean(9, giocatore.isAgonista());
            prpst.setBoolean(10, giocatore.isSocio());

            prpst.execute();        //esegue la query nel DB
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                con.close();
                if (prpst != null) prpst.close();       //chiudo le connessioni
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }

}
