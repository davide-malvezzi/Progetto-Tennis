package circolo;

import circolo.util.DateUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.File;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Database {
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
                "Data_nascita TEXT not null," +
                "CF TEXT not null unique," +
                "Genere TEXT not null," +
                "Indirizzo TEXT," +
                "Classifica_FIT TEXT," +
                "Fascia INTEGER," +
                "Agonista INTEGER," +
                "Socio INTEGER )");
        stm.execute("create table Campi (" +
                "Num_Campo INTEGER primary key autoincrement," +
                "Superficie TEXT," +
                "Posizione TEXT check (Posizione in ('Interno','Esterno')) )");
        stm.execute("create table Partite (" +
                "Num_Partita INTEGER primary key autoincrement," +
                "Data TEXT," +
                "CF_Giocatore1 TEXT not null references Giocatori(CF)," +
                "Giocatore1 TEXT," +
                "CF_Giocatore2 TEXT not null references Giocatori(CF)," +
                "Giocatore2 TEXT," +
                "Num_Campo INTEGER references Campi(Num)," +
                "Risultato TEXT check(Risultato in('6-0','6-1','6-2','6-3','6-4','7-5','7-6','0-6','1-6','2-6','3-6','4-6','5-7','6-7'))," +
                "Vincitore INTEGER references Giocatori(ID) " +
                "check (CF_Giocatore1 != CF_Giocatore2))");
        stm.execute("create table Prenotazioni(" +
                "Num_Prenotazione INTEGER primary key autoincrement," +
                "Data_Inizio TEXT not null," +
                "Data_Fine TEXT not null," +
                "Num_Campo INTEGER not null references Campo(Num)," +
                "Importo REAL," +
                "Pagato INTEGER )");
        stm.execute("create table VisiteMediche (" +
                "Num_Visita INTEGER primary key autoincrement," +
                "CF TEXT not null references Giocatori(CF)," +
                "Data TEXT not null," +
                "Agonistica INTEGER not null )");
        stm.execute("create table Partecipanti_MatchPlay (" +
                "ID INTEGER not null references Giocatori(ID)," +
                "Fascia INTEGER," +
                "Edizione INTEGER )");


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

    public boolean InserisciGiocatore(Giocatore giocatore) {
        prpst = null;

        try {
            prpst = con.prepareStatement("INSERT INTO Giocatori (Nome,Cognome,Data_nascita,CF,Genere,Indirizzo,Classifica_FIT,Fascia,Agonista,Socio) VALUES(?,?,?,?,?,?,?,?,?,?)");   //inserisce i valori al posto delle '?'
            prpst.setString(1, giocatore.getNome());
            prpst.setString(2, giocatore.getCognome());
            prpst.setString(3, DateUtil.format(giocatore.getData_nascita()));
            prpst.setString(4, giocatore.getCF());
            prpst.setString(5, giocatore.getGenere());
            prpst.setString(6, giocatore.getIndirizzo());
            prpst.setString(7, giocatore.getClassifica_FIT());
            prpst.setInt(8, giocatore.getFascia());
            prpst.setInt(9, giocatore.getAgonista());
            prpst.setInt(10, giocatore.getSocio());


            prpst.execute();        //esegue la query nel DB
        } catch (SQLException e) {
            e.printStackTrace();
        }
        CloseConnection();
        return true;
    }

    public ObservableList<Giocatore> ricercaGiocatore(Giocatore giocatore) {
        prpst = null;
        ObservableList<Giocatore> lista = FXCollections.observableArrayList();
        ResultSet rs;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-uuuu");
        StringBuilder query = new StringBuilder();
        query.append("select * from giocatori ");
        String whereCondition = "";
        if (giocatore.getNome() != null && giocatore.getNome().length() > 0)
            whereCondition += " nome = ?";
        if (giocatore.getCognome() != null && giocatore.getCognome().length() > 0) {
            whereCondition += (whereCondition.length() > 0 ? " AND " : "");
            whereCondition += " cognome = ?";
        }
        if (giocatore.getCF() != null && giocatore.getCF().length() > 0) {
            whereCondition += (whereCondition.length() > 0 ? " AND " : "");
            whereCondition += " CF = ?";
        }
        if (giocatore.getIndirizzo() != null && giocatore.getIndirizzo().length() > 0) {
            whereCondition += (whereCondition.length() > 0 ? " AND " : "");
            whereCondition += " indirizzo = ?";
        }
        if (giocatore.getGenere() != null && giocatore.getGenere().length() > 0) {
            whereCondition += (whereCondition.length() > 0 ? " AND " : "");
            whereCondition += " genere = ?";
        }
        if (giocatore.getClassifica_FIT() != null && giocatore.getClassifica_FIT().length() > 0) {
            whereCondition += (whereCondition.length() > 0 ? " AND " : "");
            whereCondition += " classifica_FIT = ?";
        }
        if(giocatore.getFascia() > 0) {
            whereCondition += (whereCondition.length() > 0 ? " AND " : "");
            whereCondition += " fascia = ?";
        }
        if(giocatore.getAgonista() > 0 ) {
            whereCondition += (whereCondition.length() > 0 ? " AND " : "");
            whereCondition += " agonista = 1";
        }
        if (giocatore.getSocio() > 0) {
            whereCondition += (whereCondition.length() > 0 ? " AND " : "");
            whereCondition += " socio = 1";
        }
        query.append(" where ").append(whereCondition);

            try {
                prpst = con.prepareStatement(query.toString());
                int Index = 1;
                if (giocatore.getNome() != null && giocatore.getNome().length() > 0)
                    prpst.setString( Index++, giocatore.getNome());
                if (giocatore.getCognome() != null && giocatore.getCognome().length() > 0) {
                    prpst.setString( Index++, giocatore.getCognome());
                }
                if (giocatore.getCF() != null && giocatore.getCF().length() > 0) {
                    prpst.setString( Index++, giocatore.getCF());
                }
                if (giocatore.getIndirizzo() != null && giocatore.getIndirizzo().length() > 0) {
                    prpst.setString( Index++, giocatore.getIndirizzo());
                }
                if (giocatore.getGenere() != null && giocatore.getGenere().length() > 0) {
                    prpst.setString( Index++, giocatore.getGenere());
                }
                if (giocatore.getClassifica_FIT() != null && giocatore.getClassifica_FIT().length() > 0) {
                    prpst.setString( Index++, giocatore.getClassifica_FIT());
                }
                if(giocatore.getFascia() != 0) {
                    prpst.setInt( Index++, giocatore.getFascia());
                }
                rs = prpst.executeQuery();
                while (rs.next()) {
                    Giocatore tmp = new Giocatore();
                    tmp.setNome(rs.getString("Nome"));
                    tmp.setCognome(rs.getString("Cognome"));
                    tmp.setData_nascita(LocalDate.parse(rs.getString("Data_nascita"), formatter));
                    tmp.setCF(rs.getString("CF"));
                    tmp.setGenere(rs.getString("Genere"));
                    if (rs.getString("Indirizzo") != null) tmp.setIndirizzo(rs.getString("Indirizzo"));
                    if (rs.getString("Classifica_FIT") != null) tmp.setClassifica_FIT(rs.getString("Classifica_FIT"));
                    tmp.setFascia(rs.getInt("Fascia"));
                    tmp.setAgonista(rs.getInt("Agonista"));
                    tmp.setSocio(rs.getInt("Socio"));
                    lista.add(tmp);
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
        CloseConnection();
        return lista;
    }

    public void InserisciPartecipante_MatchPlay(Giocatore giocatore) {
        prpst = null;
        ResultSet rs;
        LocalDateTime data = LocalDateTime.now();
        try {
            prpst = con.prepareStatement("select ID,Fascia from Giocatori where CF = ? ");
            prpst.setString(1, giocatore.getCF());
            rs = prpst.executeQuery();
            prpst = con.prepareStatement("INSERT INTO Partecipanti_MatchPlay VALUES (?,?,?)");
            prpst.setInt(1, rs.getInt("ID"));
            prpst.setInt(2, rs.getInt("Fascia"));
            prpst.setInt(3, data.getYear());
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void InserisciPartita(Partita partita) {
        prpst = null;

        try {
            prpst = con.prepareStatement("INSERT INTO Partite(Data,CF_Giocatore1,Giocatore1,CF_Giocatore2,Giocatore2,Num_Campo) VALUES(?,?,?,?,?,?,?,?)");   //inserisce i valori al posto delle '?'
            prpst.setString(1, partita.data_partita.toString());
            prpst.setString(2, partita.getPlayer1().getCF());
            prpst.setString(3, partita.getPlayer1().getNome() + " " + partita.getPlayer1().getCognome());
            prpst.setString(4, partita.getPlayer2().getCF());
            prpst.setString(5, partita.getPlayer2().getNome() + " " + partita.getPlayer2().getCognome());
            prpst.setInt(6, partita.getField().getNumero_campo());
            prpst.setString(7, partita.getRisultato().getRisultato());
            prpst.setString(8, partita.getVincitore().getNome() + " " + partita.getVincitore().getCognome());

            prpst.execute();        //esegue la query nel DB
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void InserisciPrenotazione(Prenotazione prenotazione) {
        prpst = null;

        try {
            prpst = con.prepareStatement("INSERT INTO Prenotazioni (Data_Inizio,Data_Fine,Num_Campo,Importo,Pagato) VALUES(?,?,?,?,?)");
            prpst.setString(1, prenotazione.data_inizio.toString());
            prpst.setString(2, prenotazione.data_fine.toString());
            prpst.setInt(3, prenotazione.getField().getNumero_campo());
            prpst.setDouble(4, prenotazione.getImporto());
            prpst.setInt(5, prenotazione.getPagato());

            prpst.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public ArrayList<Integer> checkDisponibilità(LocalDateTime dataInizio, LocalDateTime dataFine) throws SQLException {
        prpst = con.prepareStatement("select distinct  c.Num_Campo " +
                "from Campi  c left join Prenotazioni p on (p.Num_Campo = c.Num_Campo) " +
                "where (datetime(?) not between datetime(data_inizio) and datetime(data_fine)" +
                "and datetime(?) not between datetime(data_inizio) and datetime(data_fine) )" +
                "or num_prenotazione is null");

        prpst.setString(1, dataInizio.toString());
        prpst.setString(2, dataFine.toString());
        ResultSet rs = prpst.executeQuery();
        ArrayList<Integer> num_Campi = new ArrayList<Integer>();
        while (rs.next()) {
            num_Campi.add(rs.getInt("Num_Campo"));
        }
        return num_Campi;
    }

    public void InserisciVisita(VisitaMedica visita) {
        prpst = null;

        try {
            prpst = con.prepareStatement("INSERT INTO VisiteMediche(CF,Data,Agonistica) VALUES(?,?,?)");   //inserisce i valori al posto delle '?'
            prpst.setString(1, visita.getCF_paziente());
            prpst.setString(2, visita.data_visita.toString());
            prpst.setInt(3, visita.isAgonistica());

            prpst.execute();        //esegue la query nel DB
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void InserisciCampo(Campo field) {
        prpst = null;

        try {
            prpst = con.prepareStatement("INSERT INTO Campi(Superficie,Posizione) VALUES(?,?)");
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