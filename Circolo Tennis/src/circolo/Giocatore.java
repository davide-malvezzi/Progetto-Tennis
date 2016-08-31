package circolo;


import circolo.util.DateUtil;
import javafx.beans.property.*;

import java.time.LocalDate;

public class Giocatore {
    private String nome, cognome;
    LocalDate data_nascita;
    private String CF;
    private String genere;
    private String indirizzo;
    private String classifica_FIT;
    private int agonista, socio,fascia;

    public Giocatore() {
    }

    Giocatore(String nome, String cognome) {
        this.nome = nome;
        this.cognome = cognome;
    }

    Giocatore(String nome, String cognome, int fascia) {
        this.nome = nome;
        this.cognome = cognome;
        this.fascia = fascia;
    }


    public Giocatore(String nome, String cognome, LocalDate data, String cf, String genere, String indirizzo) {
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

    public StringProperty getNomeProperty(){
        return new SimpleStringProperty(nome);
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }


    String getCognome() {
        return cognome;
    }

    public StringProperty getCognomeProperty(){
        return new SimpleStringProperty(cognome);
    }

    public void setData_nascita(LocalDate data_nascita) {
        this.data_nascita = data_nascita;
    }

    public LocalDate getData_nascita() {
        return data_nascita;
    }

    public StringProperty getData_NascitaProperty(){
        return new SimpleStringProperty(DateUtil.format(this.getData_nascita()));
    }

    public void setCF(String CF) {
        this.CF = CF;
    }

    String getCF() {
        return CF;
    }

    public StringProperty getCFProperty(){
        return new SimpleStringProperty(CF);
    }

    String getClassifica_FIT() {
        return classifica_FIT;
    }

    public StringProperty getClassifica_FITProperty(){
        return new SimpleStringProperty(classifica_FIT);
    }

    public void setClassifica_FIT(String classifica_FIT) {
        this.classifica_FIT = classifica_FIT;
    }

    int getFascia() {
        return fascia;
    }

    public StringProperty getFasciaProperty(){
        return new SimpleStringProperty(String.valueOf(fascia));
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

    public StringProperty getGenereProperty(){
        return new SimpleStringProperty(genere);
    }

    String getIndirizzo() {
        return indirizzo;
    }

    public StringProperty getIndirizzoProperty(){
        return new SimpleStringProperty(indirizzo);
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
}
