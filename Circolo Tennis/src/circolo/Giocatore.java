package circolo;


import java.sql.Date;

class Giocatore {
    private int ID;
    private String nome, cognome;
    private Date data_nascita;
    private String CF;
    private String genere;
    private String indirizzo;
    private double classifica_FIT;
    private int fascia;
    private int agonista, socio;

    Giocatore() {
    }

    Giocatore(String nome, String cognome, int fascia) {
        this.nome = nome;
        this.cognome = cognome;
        this.fascia = fascia;
    }


    public Giocatore(String nome, String cognome, Date data, String cf, String genere, String indirizzo) {
        this.nome = nome;
        this.cognome = cognome;
        this.data_nascita = data;
        this.CF = cf;
        this.genere = genere;
        this.indirizzo = indirizzo;
    }

    //getter and setters

    public void setNome(String nome) {
        this.nome = nome;
    }

    String getNome() {
        return nome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }


    String getCognome() {
        return cognome;
    }

    public void setData_nascita(Date data_nascita) {
        this.data_nascita = data_nascita;
    }

    Date getData_nascita() {
        return data_nascita;
    }

    public void setCF(String CF) {
        this.CF = CF;
    }

    String getCF() {
        return CF;
    }

    double getClassifica_FIT() {
        return classifica_FIT;
    }

    public void setClassifica_FIT(double classifica_FIT) {
        this.classifica_FIT = classifica_FIT;
    }

    int getFascia() {
        return fascia;
    }

    public void setFascia(int fascia) {
        this.fascia = fascia;
    }

    public void setGenere(String genere) {
        this.genere = genere;
    }

    String getGenere() {
        return genere;
    }

    String getIndirizzo() {
        return indirizzo;
    }

    public void setIndirizzo(String indirizzo) {
        this.indirizzo = indirizzo;
    }

    public int getAgonista() {
        return agonista;
    }

    public void setAgonista(int agonista) {
        this.agonista = agonista;
    }

    public int getSocio() {
        return socio;
    }

    public void setSocio(int socio) {
        this.socio = socio;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }
}
