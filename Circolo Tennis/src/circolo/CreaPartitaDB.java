package circolo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CreaPartitaDB {
    private Partite partita;
    ConnessioneDB database = new ConnessioneDB();
    private Connection con;    //connessione al DB
    String player1;
    String player2;
    int num_campo;
    String risultato;
    String vincitore;

    public CreaPartitaDB(Partite partita) {
        this.partita = partita;
        this.player1 = partita.getPlayer1().getNome() + " " + partita.getPlayer1().getCognome();
        this.player2 = partita.getPlayer2().getNome() + " " + partita.getPlayer2().getCognome();
        this.num_campo = partita.getField().getNumero_campo();
        this.risultato = partita.getRisultato().getRisultato();
        this.vincitore = partita.getVincitore().getNome() + " " + partita.getVincitore().getCognome();

        con = database.Connessione();
        PreparedStatement prpst = null;

        try {
            prpst = con.prepareStatement("INSERT INTO partita VALUES(?,?,?,?,?,?,?");   //inserisce i valori al posto delle '?'
            prpst.setDate(1, partita.getData_partita());
            prpst.setInt(2, partita.getNumero_partita());
            prpst.setString(3, this.player1);
            prpst.setString(4, this.player2);
            prpst.setInt(5, this.num_campo);
            prpst.setString(6, this.risultato);
            prpst.setString(7, this.vincitore);

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
