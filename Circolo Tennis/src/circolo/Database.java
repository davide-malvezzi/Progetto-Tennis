package circolo;

import circolo.util.DateUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.File;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class Database {
    private Connection con;
    private Statement stm = null;
    private PreparedStatement prpst = null;

    public Database() throws SQLException, ClassNotFoundException {
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
                "Città TEXT," +
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
                "Data TEXT not null," +
                "Inizio TEXT not null," +
                "Fine TEXT not null," +
                "Num_Campo INTEGER not null references Campo(Num)," +
                "Titolare TEXT," +
                "Recapito TEXT," +
                "Importo TEXT," +
                "Pagato INTEGER " +
                ")");
        stm.execute("create table VisiteMediche (" +
                "Num_Visita INTEGER primary key autoincrement," +
                "CF TEXT not null references Giocatori(CF)," +
                "Data TEXT not null," +
                "Agonistica INTEGER not null )");
        stm.execute("create table MatchPlay (" +
                "ID_Giocatore not null references Giocatori(ID)," +
                "Fascia INTEGER," +
                "Edizione INTEGER," +
                "Nome TEXT," +
                "Cognome TEXT," +
                "Punti TEXT," +
                "Game_Fatti INTEGER," +
                "Game_Subiti INTEGER, " +
                "UNIQUE (ID_Giocatore,Edizione))");
        stm.execute("create table Admin(" +
                "Username TEXT primary key, " +
                "Password TEXT " +
                "Amministratore INTEGER not null )");
    }

    private Connection Connessione() throws SQLException, ClassNotFoundException {
        String connectionUrl;

        Class.forName("org.sqlite.JDBC");
        connectionUrl = "jdbc:sqlite:circolo.db";


        con = DriverManager.getConnection(connectionUrl);

        System.out.println("Connessione al DB stabilita");

        return con;
    }

    public boolean login(String user, String password) throws SQLException {
        prpst = null;
        boolean found = false;
        prpst = con.prepareStatement("select count(*) as num from Admin where username = ? and password = ? ");
        prpst.setString(1, user);
        prpst.setString(2, password);
        ResultSet rs = prpst.executeQuery();
        if (rs.getInt("num") > 0) {
            found = true;
        }
        return found;
    }

    public boolean InserisciGiocatore(Giocatore giocatore) throws SQLException {
        prpst = null;

        prpst = con.prepareStatement("INSERT INTO Giocatori (Nome,Cognome,Data_nascita,CF,Genere,Città,Indirizzo,Classifica_FIT,Fascia,Agonista,Socio) VALUES(?,?,?,?,?,?,?,?,?,?,?)");   //inserisce i valori al posto delle '?'
        prpst.setString(1, giocatore.getNome());
        prpst.setString(2, giocatore.getCognome());
        prpst.setString(3, DateUtil.format(giocatore.getData_nascita()));
        prpst.setString(4, giocatore.getCF());
        prpst.setString(5, giocatore.getGenere());
        prpst.setString(6,giocatore.getCitta());
        prpst.setString(7, giocatore.getIndirizzo());
        prpst.setString(8, giocatore.getClassifica_FIT());
        prpst.setInt(9, giocatore.getFascia());
        prpst.setInt(10, giocatore.getAgonista());
        prpst.setInt(11, giocatore.getSocio());


        prpst.execute();        //esegue la query nel DB
        return true;
    }

    public ObservableList<Giocatore> ricercaGiocatore(Giocatore giocatore) throws SQLException { //todo: aggiungere città
        prpst = null;
        ObservableList<Giocatore> lista = FXCollections.observableArrayList();
        ResultSet rs;
        StringBuilder query = new StringBuilder();
        query.append("select * from giocatori ");
        String whereCondition = "";
        if (giocatore.getNome() != null && giocatore.getNome().length() > 0)
            whereCondition += " nome = ? COLLATE NOCASE ";
        if (giocatore.getCognome() != null && giocatore.getCognome().length() > 0) {
            whereCondition += (whereCondition.length() > 0 ? " AND " : "");
            whereCondition += " cognome = ? COLLATE NOCASE ";
        }
        if (giocatore.getCF() != null && giocatore.getCF().length() > 0) {
            whereCondition += (whereCondition.length() > 0 ? " AND " : "");
            whereCondition += " CF = ?";
        }
        if (giocatore.getIndirizzo() != null && giocatore.getIndirizzo().length() > 0) {
            whereCondition += (whereCondition.length() > 0 ? " AND " : "");
            whereCondition += " indirizzo = ? COLLATE NOCASE ";
        }
        if (giocatore.getGenere() != null && giocatore.getGenere().length() > 0) {
            whereCondition += (whereCondition.length() > 0 ? " AND " : "");
            whereCondition += " genere = ?";
        }
        if (giocatore.getClassifica_FIT() != null && giocatore.getClassifica_FIT().length() > 0) {
            whereCondition += (whereCondition.length() > 0 ? " AND " : "");
            whereCondition += " classifica_FIT = ?";
        }
        if (giocatore.getFascia() > 0) {
            whereCondition += (whereCondition.length() > 0 ? " AND " : "");
            whereCondition += " fascia = ?";
        }
        if (giocatore.getAgonista() > 0) {
            whereCondition += (whereCondition.length() > 0 ? " AND " : "");
            whereCondition += " agonista = 1";
        }
        if (giocatore.getSocio() > 0) {
            whereCondition += (whereCondition.length() > 0 ? " AND " : "");
            whereCondition += " socio = 1";
        }
        query.append(" where ").append(whereCondition);

        prpst = con.prepareStatement(query.toString());
        int Index = 1;
        if (giocatore.getNome() != null && giocatore.getNome().length() > 0)
            prpst.setString(Index++, giocatore.getNome());
        if (giocatore.getCognome() != null && giocatore.getCognome().length() > 0) {
            prpst.setString(Index++, giocatore.getCognome());
        }
        if (giocatore.getCF() != null && giocatore.getCF().length() > 0) {
            prpst.setString(Index++, giocatore.getCF());
        }
        if (giocatore.getIndirizzo() != null && giocatore.getIndirizzo().length() > 0) {
            prpst.setString(Index++, giocatore.getIndirizzo());
        }
        if (giocatore.getGenere() != null && giocatore.getGenere().length() > 0) {
            prpst.setString(Index++, giocatore.getGenere());
        }
        if (giocatore.getClassifica_FIT() != null && giocatore.getClassifica_FIT().length() > 0) {
            prpst.setString(Index++, giocatore.getClassifica_FIT());
        }
        if (giocatore.getFascia() != 0) {
            prpst.setInt(Index++, giocatore.getFascia());
        }
        rs = prpst.executeQuery();
        while (rs.next()) {
            Giocatore tmp = new Giocatore();
            tmp.setNome(rs.getString("Nome"));
            tmp.setCognome(rs.getString("Cognome"));
            tmp.setData_nascita(DateUtil.parse(rs.getString("Data_nascita")));
            tmp.setCF(rs.getString("CF"));
            tmp.setGenere(rs.getString("Genere"));
            if (rs.getString("Indirizzo") != null) tmp.setIndirizzo(rs.getString("Indirizzo"));
            if (rs.getString("Classifica_FIT") != null) tmp.setClassifica_FIT(rs.getString("Classifica_FIT"));
            tmp.setFascia(rs.getInt("Fascia"));
            tmp.setAgonista(rs.getInt("Agonista"));
            tmp.setSocio(rs.getInt("Socio"));
            lista.add(tmp);
        }
        return lista;
    }

    public void modificaIscritto(Giocatore newGiocatore) throws SQLException {
        prpst = null;

        prpst = con.prepareStatement("update Giocatori set Indirizzo = ?, Socio = ?, Agonista = ?, " +
                "Classifica_FIT = ?, Fascia = ? " +
                "where CF = ?");

        prpst.setString(1, newGiocatore.getIndirizzo());
        prpst.setInt(2, newGiocatore.getSocio());
        prpst.setInt(3, newGiocatore.getAgonista());
        prpst.setString(4, newGiocatore.getClassifica_FIT());
        prpst.setInt(5, newGiocatore.getFascia());
        prpst.setString(6, newGiocatore.getCF());

        prpst.execute();
    }


    public void InserisciPartecipante_MatchPlay(Giocatore giocatore) throws SQLException {
        prpst = null;
        ResultSet rs;
        LocalDateTime data = LocalDateTime.now();
        prpst = con.prepareStatement("select ID,Fascia,Nome,Cognome from Giocatori where CF = ? ");
        prpst.setString(1, giocatore.getCF());
        rs = prpst.executeQuery();
        prpst = con.prepareStatement("INSERT INTO MatchPlay(id_giocatore, fascia, edizione, nome, cognome) VALUES (?,?,?,?,?)");
        prpst.setInt(1, rs.getInt("ID"));
        prpst.setInt(2, rs.getInt("Fascia"));
        prpst.setInt(3, data.getYear());
        prpst.setString(4,rs.getString("Nome"));
        prpst.setString(5,rs.getString("Cognome"));
        prpst.execute();
    }

    public ObservableList<Giocatore> ListaPartecipanti() throws SQLException {
        ObservableList<Giocatore> lista = FXCollections.observableArrayList();
        ResultSet rs = stm.executeQuery("select * from Matchplay where edizione = strftime('%Y','now')");
        while(rs.next()){
            Giocatore giocatore = new Giocatore();
            giocatore.setNome(rs.getString("nome"));
            giocatore.setCognome(rs.getString("cognome"));
            giocatore.setFascia(rs.getInt("fascia"));
            giocatore.setID(rs.getInt("ID_Giocatore"));
            lista.add(giocatore);
        }

        return lista;
    }

    public void rimuoviPartecipante(Giocatore giocatore) throws SQLException{ //rimuove un partecipante dalla lista matchplay
        prpst = null;

        prpst = con.prepareStatement("delete from Matchplay where ID_Giocatore = ?");
        prpst.setInt(1,giocatore.getID());
        prpst.execute();
    }

    public ObservableList<ObservableList<Giocatore>> generaGironiMatchPlay() throws SQLException {
        ObservableList<ObservableList<Giocatore>> urna  = FXCollections.observableArrayList();
        ObservableList<ObservableList<Giocatore>> gironi = FXCollections.observableArrayList();
        ResultSet rs;
        for(int i =0; i<5;i++) {
            urna.add(FXCollections.observableArrayList());
            gironi.add(FXCollections.observableArrayList());
        }
        rs = stm.executeQuery("select distinct m.* from matchplay m left join giocatori g on (g.id = m.id_giocatore) " +
                "where m.fascia = 1 and edizione = strftime('%Y','now')");
        while(rs.next()){
            Giocatore giocatore = new Giocatore(rs.getString("nome"),rs.getString("cognome"),rs.getInt("fascia"));
            urna.get(0).add(giocatore);
        }

        rs = stm.executeQuery("select distinct m.* from matchplay m left join giocatori g on (g.id = m.id_giocatore) " +
                "where m.fascia = 2 and edizione = strftime('%Y','now')");
        while(rs.next()){
            Giocatore giocatore = new Giocatore(rs.getString("nome"),rs.getString("cognome"),rs.getInt("fascia"));
            urna.get(1).add(giocatore);
        }

        rs = stm.executeQuery("select distinct m.* from matchplay m left join giocatori g on (g.id = m.id_giocatore) " +
                "where m.fascia = 3 and edizione = strftime('%Y','now')");
        while(rs.next()){
            Giocatore giocatore = new Giocatore(rs.getString("nome"),rs.getString("cognome"),rs.getInt("fascia"));
            urna.get(2).add(giocatore);
        }

        rs = stm.executeQuery("select distinct m.* from matchplay m left join giocatori g on (g.id = m.id_giocatore) " +
                "where m.fascia = 4 and edizione = strftime('%Y','now')");
        while(rs.next()){
            Giocatore giocatore = new Giocatore(rs.getString("nome"),rs.getString("cognome"),rs.getInt("fascia"));
            urna.get(3).add(giocatore);
        }

        rs = stm.executeQuery("select distinct m.* from matchplay m left join giocatori g on (g.id = m.id_giocatore) " +
                "where m.fascia = 5 and edizione = strftime('%Y','now')");
        while(rs.next()){
            Giocatore giocatore = new Giocatore(rs.getString("nome"),rs.getString("cognome"),rs.getInt("fascia"));
            urna.get(4).add(giocatore);
        }



        return urna;
    }

    public void InserisciPartita(Partita partita) throws SQLException {
        prpst = null;

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
    }

    public void InserisciPrenotazione(Prenotazione prenotazione) throws SQLException {
        prpst = null;

        prpst = con.prepareStatement("INSERT INTO Prenotazioni (Data,Inizio,Fine,Num_Campo,Titolare,Recapito,Importo,Pagato) VALUES(?,?,?,?,?,?,?,?)");
        prpst.setString(1, prenotazione.getData().toString());
        prpst.setString(2, prenotazione.inizio.get().toString());
        prpst.setString(3, prenotazione.fine.get().toString());
        prpst.setInt(4, prenotazione.getcampo().getNumero_campo());
        prpst.setString(5, prenotazione.getTitolare());
        prpst.setString(6, prenotazione.getRecapito());
        prpst.setString(7, prenotazione.getImporto());
        prpst.setInt(8, prenotazione.getPagato());

        prpst.execute();

    }

    public void setPrenotazionePagata(Prenotazione prenotazione) throws SQLException {
        prpst = null;

        prpst = con.prepareStatement("update Prenotazioni set pagato = 1 where num_prenotazione = ?");

        prpst.setInt(1, prenotazione.getNumeroPrenotazione());

        prpst.execute();
    }

    public ObservableList<Prenotazione> trovaPrenotazioni(int numero) throws SQLException {
        prpst = null;

        prpst = con.prepareStatement("select * from prenotazioni order by num_prenotazione desc limit ?");

        prpst.setInt(1, numero);

        ResultSet rs = prpst.executeQuery();
        ObservableList<Prenotazione> prenotazioni = FXCollections.observableArrayList();
        while (rs.next()) {
            prenotazioni.add(new Prenotazione(rs.getInt("num_prenotazione"), LocalDate.parse(rs.getString("data")), LocalTime.parse(rs.getString("inizio")),
                    LocalTime.parse(rs.getString("fine")), new Campo(rs.getInt("num_campo")), rs.getString("titolare"),
                    rs.getString("recapito"), rs.getInt("pagato"), rs.getString("importo")));
        }
        return prenotazioni;
    }

    public ObservableList<Campo> checkDisponibilità(Prenotazione prenotazione) throws SQLException {
        StringBuilder query = new StringBuilder();
        boolean superficieOK = false;
        query.append("select distinct c.* " +
                "from campi c " +
                "where c.num_campo not in(" +
                "select distinct  c1.num_campo " +
                "from Campi  c1 left join Prenotazioni p on (p.Num_Campo = c1.Num_Campo) " +
                "where (date(?) = date(data) " +
                "and time(?) between time(inizio) and time(fine)" +
                "and time(?) between time(inizio) and time(fine)))");
        if (prenotazione.getcampo().getSuperficie().length() > 0) {
            query.append(" and superficie = ?");
            superficieOK = true;
        }
        prpst = con.prepareStatement(query.toString());
        prpst.setString(1, prenotazione.getData().toString());
        prpst.setString(2, prenotazione.getInizio().toString());
        prpst.setString(3, prenotazione.getFine().toString());
        if (superficieOK) {
            prpst.setString(4, prenotazione.getcampo().getSuperficie());
        }
        ResultSet rs = prpst.executeQuery();
        ObservableList<Campo> Campi = FXCollections.observableArrayList();
        while (rs.next()) {
            Campi.add(new Campo(rs.getInt("Num_Campo"), rs.getString("Superficie"), rs.getString("Posizione")));
        }
        return Campi;
    }

    public void InserisciVisita(VisitaMedica visita) throws SQLException {
        prpst = null;

        prpst = con.prepareStatement("INSERT INTO VisiteMediche(CF,Data,Agonistica) VALUES(?,?,?)");   //inserisce i valori al posto delle '?'
        prpst.setString(1, visita.getCF_paziente());
        prpst.setString(2, visita.getData().toString());
        prpst.setInt(3, visita.getAgonistica());

        prpst.execute();        //esegue la query nel DB

    }

    public void InserisciCampo(Campo field) throws SQLException {
        prpst = null;
        prpst = con.prepareStatement("INSERT INTO Campi(Superficie,Posizione) VALUES(?,?)");
        prpst.setString(1, field.getSuperficie());
        prpst.setString(2, field.getPosizione());

        prpst.execute();

    }

    public void CloseConnection() {
        try {
            con.close();
            if (stm != null) stm.close();
            if (prpst != null) prpst.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}