package circolo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CreaVisitaDB {
    private VisitaMedica visita;
    private Connection con;    //connessione al DB
    ConnessioneDB database = new ConnessioneDB();

    public CreaVisitaDB(VisitaMedica visita){
        this.visita= visita;
        con = database.Connessione();
        PreparedStatement prpst = null;

        try {
            prpst = con.prepareStatement("INSERT INTO visita VALUES(?,?,?)");   //inserisce i valori al posto delle '?'
            prpst.setString(1, visita.getCF_paziente());
            prpst.setDate(2, visita.getData_visita());
            prpst.setBoolean(3,visita.isAgonistica());

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
