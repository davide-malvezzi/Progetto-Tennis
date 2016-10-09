package circolo;


import circolo.util.DateUtil;
import javafx.beans.property.*;

import java.time.LocalDate;

public class Giocatore {
    private IntegerProperty ID;
    private StringProperty nome, cognome;
    private ObjectProperty<LocalDate> data_nascita;
    private StringProperty CF;
    private StringProperty genere;
    private StringProperty citta;
    private StringProperty indirizzo;
    private StringProperty classifica_FIT;
    private IntegerProperty agonista, socio,fascia;

    public Giocatore() {
        ID = new SimpleIntegerProperty();
        nome = new SimpleStringProperty();
        cognome = new SimpleStringProperty();
        data_nascita = new SimpleObjectProperty<>();
        CF = new SimpleStringProperty();
        genere = new SimpleStringProperty();
        citta = new SimpleStringProperty();
        indirizzo = new SimpleStringProperty();
        classifica_FIT = new SimpleStringProperty();
        agonista = new SimpleIntegerProperty();
        socio = new SimpleIntegerProperty();
        fascia = new SimpleIntegerProperty();
    }

    public Giocatore(String nome, String cognome) {
        this.nome = new SimpleStringProperty(nome);
        this.cognome = new SimpleStringProperty(cognome);
    }


    public Giocatore(String nome, String cognome, int fascia) {
        this.nome = new SimpleStringProperty(nome);
        this.cognome = new SimpleStringProperty(cognome);
        this.fascia = new SimpleIntegerProperty(fascia);
    }

    public Giocatore(String indirizzo,int socio,int agonista,int fascia,String classificaFIT,String cf){
        this.indirizzo = new SimpleStringProperty(indirizzo);
        this.socio = new SimpleIntegerProperty(socio);
        this.agonista = new SimpleIntegerProperty(agonista);
        this.fascia = new SimpleIntegerProperty(fascia);
        this.classifica_FIT = new SimpleStringProperty(classificaFIT);
        this.CF = new SimpleStringProperty(cf);
    }


    public Giocatore(String nome, String cognome, LocalDate data, String cf, String genere, String indirizzo) {
        this.nome = new SimpleStringProperty(nome);
        this.cognome = new SimpleStringProperty(cognome);
        this.data_nascita = new SimpleObjectProperty<>(data);
        this.CF = new SimpleStringProperty(cf);
        this.genere = new SimpleStringProperty(genere);
        this.indirizzo = new SimpleStringProperty(indirizzo);
    }

    //getter and setters

    public void setNome(String nome) {
       this.nome = new SimpleStringProperty(nome);
    }

    public String getNome() {
        return nome.get();
    }

    public StringProperty getNomeProperty(){
        return nome;
    }

    public void setCognome(String cognome) {
        this.cognome = new SimpleStringProperty(cognome);
    }


    public String getCognome() {
        return cognome.get();
    }

    public StringProperty getCognomeProperty(){
        return cognome;
    }

    public void setData_nascita(LocalDate data_nascita) {
        this.data_nascita = new SimpleObjectProperty<>(data_nascita);
    }

    public LocalDate getData_nascita() {
        return data_nascita.get();
    }

    public StringProperty getData_NascitaProperty(){
        return new SimpleStringProperty(DateUtil.format(this.getData_nascita()));
    }

    public void setCF(String CF) {
        this.CF = new SimpleStringProperty(CF);
    }

    public String getCF() {
        return CF.get();
    }

    public StringProperty getCFProperty(){
        return CF;
    }

    public String getClassifica_FIT() {
        return classifica_FIT.get();
    }

    public StringProperty getClassifica_FITProperty(){
        return classifica_FIT;
    }

    public void setClassifica_FIT(String classifica_FIT) {
        this.classifica_FIT = new SimpleStringProperty(classifica_FIT);
    }

    public int getFascia() {
        return fascia.get();
    }

    public IntegerProperty getFasciaProperty(){
        return fascia;
    }

    public void setFascia(int fascia) {
        this.fascia = new SimpleIntegerProperty(fascia);
    }

    public void setGenere(String genere) {
        this.genere = new SimpleStringProperty(genere);
    }

    public String getGenere() {
        return genere.get();
    }

    public StringProperty getGenereProperty(){
        return genere;
    }

    public String getIndirizzo() {
        return indirizzo.get();
    }

    public StringProperty getIndirizzoProperty(){
        return indirizzo;
    }

    public void setIndirizzo(String indirizzo) {
        this.indirizzo = new SimpleStringProperty(indirizzo);
    }

    public String getCitta(){
        return citta.get();
    }

    public StringProperty getCittaProperty(){
        return citta;
    }

    public void setCitta(String citta){
        this.citta = new SimpleStringProperty(citta);
    }

    public int getAgonista() {
        return agonista.get();
    }

    public IntegerProperty getAgonistaProperty(){
        return agonista;
    }

    public void setAgonista(int agonista) {
        this.agonista = new SimpleIntegerProperty(agonista);
    }

    public int getSocio() {
        return socio.get();
    }

    public IntegerProperty getSocioProperty(){
        return socio;
    }

    public void setSocio(int socio) {
        this.socio = new SimpleIntegerProperty(socio);
    }

    public int getID() {
        return ID.get();
    }

    public IntegerProperty IDProperty() {
        return ID;
    }

    public void setID(int ID) {
        this.ID.set(ID);
    }
}
