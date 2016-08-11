package circolo;

import java.io.File;
import java.sql.*;

class Database {
    private Connection con;
    private Statement stm = null;
    private PreparedStatement prpst = null;

    public Database() throws SQLException {
        boolean exists = true;
        File f = new File("circolo.db");
        if (!f.canRead()) exists = false;
        con = Connessione();
        stm = con.createStatement();
        if (!exists) CreaDatabase();
    }

    private void CreaDatabase() throws SQLException {
        System.out.println("Creazione Database...");
        stm.execute("create table Giocatori (" +
                "ID INTEGER primary key autoincrement," +
                "Nome TEXT not null," +
                "Cognome TEXT not null," +
                "Data_nascita INTEGER not null," +
                "CF TEXT not null unique," +
                "Genere TEXT not null," +
                "Indirizzo TEXT," +
                "Classifica_FIT REAL," +
                "Fascia INTEGER," +
                "Agonista INTEGER," +
                "Socio INTEGER )");
        stm.execute("create table Campi (" +
                "Num INTEGER primary key autoincrement," +
                "Superficie TEXT not null," +
                "Posizione TEXT not null check (Posizione in ('Interno','Esterno')) )");
        stm.execute("create table Partite (" +
                "Num INTEGER primary key autoincrement," +
                "Data INTEGER," +
                "ID_Giocatore_1 INTEGER not null references Giocatori(ID)," +
                "Giocatore_1 TEXT" +
                "ID_Giocatore_2 INTEGER not null references Giocatori(ID)," +
                "Giocatore_2 TEXT," +
                "Campo INTEGER references Campi(Num)," +
                "Risultato TEXT, check(Risultato in('6-0','6-1','6-2','6-3','6-4','7-5','7-6','0-6','1-6','2-6','3-6','4-6','5-7','6-7'))," +
                "Vincitore INTEGER references Giocatori(ID)" +
                "check (ID_Giocatore_1 != ID_Giocatore_2))");
        stm.execute("create table Prenotazioni(" +
                "Num INTEGER primary key autoincrement," +
                "Data_Inizio INTEGER not null," +
                "Data_Fine INTEGER not null," +
                "Campo INTEGER not null references Campo(Num)," +
                "ID_Giocatore_1 references Giocatore(ID)," +
                "Giocatore_2 TEXT" +
                "ID_Giocatore_2 references Giocatore(ID)," +
                "Giocatore_2 TEXT" +
                "Importo REAL," +
                "Pagato INTEGER " +
                "check (ID_Giocatore_1 != ID_Giocatore_2))");
        stm.execute("create table VisiteMediche (" +
                "Num INTEGER primary key autoincrement," +
                "CF_Paziente TEXT not null references Giocatori(CF)," +
                "Data INTEGER not null," +
                "Agonistica INTEGER not null )");
        stm.execute("create table Partecipanti_MatchPlay (" +
                "ID INTEGER not null references Giocatori(ID)" +
                "Fascia INTEGER," +
                "Edizione TEXT,)");


    }

    private Connection Connessione() {
        String connectionUrl;
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
        }
        return con;
    }

    public ResultSet EseguiQuery(String s) throws SQLException {
        return stm.executeQuery(s);
    }

    public void InserisciGiocatore(Giocatore giocatore) {
        prpst = null;

        try {
            prpst = con.prepareStatement("INSERT INTO Giocatori (Nome,Cognome,Data_nascita,CF,Genere,Indirizzo,Classifica_FIT,Fascia,Agonista,Socio) VALUES(?,?,?,?,?,?,?,?,?,?)");   //inserisce i valori al posto delle '?'
            prpst.setString(1, giocatore.getNome());
            prpst.setString(2, giocatore.getCognome());
            prpst.setDate(3, giocatore.getData_nascita());
            prpst.setString(4, giocatore.getCF());
            prpst.setString(5, giocatore.getGenere());
            prpst.setString(6, giocatore.getIndirizzo());
            prpst.setDouble(7, giocatore.getClassifica_FIT());
            prpst.setInt(8, giocatore.getFascia());
            prpst.setInt(9, giocatore.getAgonista());
            prpst.setInt(10, giocatore.getSocio());


            prpst.execute();        //esegue la query nel DB
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }


    public void InserisciPartita(Partita partita) {
        prpst = null;

        try {
            prpst = con.prepareStatement("INSERT INTO Partite(Data,ID_Giocatore_1,Giocatore_1,ID_Giocatore_2,Giocatore_2,Campo) VALUES(?,?,?,?,?,?,?,?)");   //inserisce i valori al posto delle '?'
            prpst.setDate(1, partita.getData_partita());
            prpst.setInt(2,partita.getPlayer1().getID());
            prpst.setString(3,partita.getPlayer1().getNome() + " " + partita.getPlayer1().getCognome());
            prpst.setInt(4,partita.getPlayer2().getID());
            prpst.setString(5,partita.getPlayer2().getNome() + " " + partita.getPlayer2().getCognome() );
            prpst.setInt(6, partita.getField().getNumero_campo());
            prpst.setString(7, partita.getRisultato().getRisultato());
            prpst.setString(8,partita.getVincitore().getNome() + " " + partita.getVincitore().getCognome() );

            prpst.execute();        //esegue la query nel DB
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void InserisciPrenotazione(Prenotazione prenotazione) {
        prpst = null;

        try {
            prpst=con.prepareStatement("INSERT INTO Prenotazioni (Data_Inizio,Data_Fine,Campo,Giocatore_1,Giocatore_2,Importo,Pagato) VALUES(?,?,?,?,?,?,?)");
            prpst.setDate(1,prenotazione.getData_inizio());
            prpst.setDate(2,prenotazione.getData_fine());
            prpst.setInt(3,prenotazione.getField().getNumero_campo());
            prpst.setString(4,prenotazione.getPlayer1().getNome() + " " + prenotazione.getPlayer1().getCognome());
            prpst.setString(5,prenotazione.getPlayer2().getNome() + " " + prenotazione.getPlayer2().getCognome());
            prpst.setDouble(6,prenotazione.getImporto());
            prpst.setInt(7,prenotazione.getPagato());

            prpst.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void InserisciVisita(VisitaMedica visita) {
        prpst = null;

        try {
            prpst = con.prepareStatement("INSERT INTO VisiteMediche(CF_Paziente,Data,Agonistica) VALUES(?,?,?)");   //inserisce i valori al posto delle '?'
            prpst.setString(1, visita.getCF_paziente());
            prpst.setDate(2, visita.getData_visita());
            prpst.setBoolean(3, visita.isAgonistica());

            prpst.execute();        //esegue la query nel DB
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void InserisciCampo(Campo field) {
        prpst = null;

        try {
            prpst = con.prepareStatement("INSERT INTO Campi(Superficie,Posizione VALUES(?,?)");
            prpst.setString(1, field.getSuperficie());
            prpst.setString(2, field.getPosizione());

            prpst.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void CloseConnection() {
        try {
            con.close();
            if (stm != null) stm.close();
            if (prpst != null) prpst.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
