package circolo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnessioneDB {
    Connection con;
    String connectionUrl;

    public Connection Connessione() {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        connectionUrl = "jdbc:sqlite:circolo.db";
        try {

            con = DriverManager.getConnection(connectionUrl);

            System.out.println("Connessione al DB stabilita");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            return con;
        }

    }


}
