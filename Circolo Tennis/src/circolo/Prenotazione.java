package circolo;


import circolo.util.DateUtil;
import javafx.beans.property.*;

import java.time.LocalDate;
import java.time.LocalTime;

public class Prenotazione {
    /**
     * Indica il numero identificativo della prenotazione
     */
    private int numeroPrenotazione;
    /**
     * Data della prenotazione
     */
    private ObjectProperty<LocalDate> data;
    /**
     * Ora di inizio della prenotazione
     */
    ObjectProperty<LocalTime> inizio;
    /**
     * Ora di fine prenotazione
     */
    ObjectProperty<LocalTime> fine;
    /**
     * Campo che viene prenotato
     * @see Campo
     */
    private Campo campo;
    /**
     * Recapito a cui può essere contattato chi prenota
     */
    private StringProperty recapito;
    /**
     * Assume il valore 1 se la prenotazione è stata pagata, 0 altrimenti
     */
    private IntegerProperty pagato;
    /**
     * Indica il nome e cognome di chi effetua la prenotazione
     */
    private StringProperty titolare;

    /**
     * Costruttore di default
     */
    public Prenotazione(){
        campo = new Campo();
        data = new SimpleObjectProperty<>();
        inizio = new SimpleObjectProperty<>();
        fine = new SimpleObjectProperty<>();
        titolare = new SimpleStringProperty();
        recapito = new SimpleStringProperty();
        pagato = new SimpleIntegerProperty();
    }

    /**
     * Costruttore completo di Prenotazione
     *
     * @see Prenotazione#pagato
     * @param numeroPrenotazione numero della prenotazione
     * @param data data della prenotazione
     * @param Inizio ora di inizio della prenotazione
     * @param Fine ora di fine della prenotazione
     * @param campo campo che viene prenotato
     * @param titolare nome  e cognome di chi effettua la prenotazione
     * @param recapito recapito a cui può essere contattato chi effettua la prenotazione
     * @param pagato indica se la prenotazione viene pagata o no
     */
    public Prenotazione(int numeroPrenotazione,LocalDate data,LocalTime Inizio, LocalTime Fine, Campo campo, String titolare,String recapito,int pagato){
        this.numeroPrenotazione = numeroPrenotazione;
        this.data = new SimpleObjectProperty<>(data);
        this.inizio = new SimpleObjectProperty<>(Inizio);
        this.fine = new SimpleObjectProperty<>(Fine);
        this.campo = campo;
        this.titolare = new SimpleStringProperty(titolare);
        this.recapito = new SimpleStringProperty(recapito);
        this.pagato = new SimpleIntegerProperty(pagato);
    }

    /**
     * Modifica il numero della prenotazione
     * @see Prenotazione#numeroPrenotazione
     * @param numeroPrenotazione numero della prenotazione da settare
     */
    public void setNumeroPrenotazione(int numeroPrenotazione){
        this.numeroPrenotazione = numeroPrenotazione;
    }

    /**
     * @return il numero della prenotazione
     * @see Prenotazione#numeroPrenotazione
     */
    public int getNumeroPrenotazione(){
        return numeroPrenotazione;
    }

    /**
     * @return la data della prenotazione
     * @see Prenotazione#data
     */
    public LocalDate getData() {
        return data.get();
    }

    /**
     * @return la data della prenotazione come property
     * @see Prenotazione#data
     */
    public StringProperty getDataProperty() {
        return new SimpleStringProperty(DateUtil.format(this.getData()));
    }

    /**
     * Modifica la data della prenotazione
     * @see Prenotazione#data
     * @param data data della prenotazione da settare
     */
    public void setData(LocalDate data) {
        this.data = new SimpleObjectProperty<>(data);
    }

    /**
     * @return l'ora d'inizio della prenotazione
     * @see Prenotazione#inizio
     */
    public LocalTime getInizio() {
        return inizio.get();
    }

    /**
     * @return l'ora d'inizio della prenotazione come property
     * @see Prenotazione#inizio
     */
    public ObjectProperty<LocalTime> getInizioProperty() {
        return inizio;
    }

    /**
     * Modifica l'ora di inizio della prenotazione
     * @see Prenotazione#inizio
     * @param inizio ora d'inizio della partita da settare
     */
    public void setInizio(LocalTime inizio) {
        this.inizio = new SimpleObjectProperty<>(inizio);
    }
    public LocalTime getFine() {
        return fine.get();
    }

    /**
     * @return l'ora di fine della prenotazione come property
     * @see Prenotazione#fine
     */
    public ObjectProperty<LocalTime> getFineProperty() {
        return fine;
    }

    /**
     * Modifica l'ora di inizio della prenotazione
     * @see Prenotazione#fine
     * @param fine ora di fine della partita da settare
     */
    public void setFine(LocalTime fine) {
        this.fine = new SimpleObjectProperty<>(fine);
    }


    /**
     * @return il campo prenotato
     */
    public Campo getcampo() {
        return campo;
    }

    /**
     * Modifica il campo prenotato
     * @param campo campo da settare
     */
    public void setcampo(Campo campo) {
        this.campo = campo;
    }

    /**
     * @return il valore di pagato
     * @see Prenotazione#pagato
     */
    public int getPagato() {
        return pagato.get();
    }

    /**
     * @return il valore di pagato come property
     * @see Prenotazione#pagato
     */
    public IntegerProperty getPagatoProperty(){
        return pagato;
    }

    /**
     * Modifica il valore di pagato
     * @see Prenotazione#pagato
     * @param pagato il valore di pagato da settare
     */
    public void setPagato(int pagato) {
        this.pagato = new SimpleIntegerProperty(pagato);
    }

    /**
     * @return il nome e cognome di chi ha effettuato la prenotazione
     * @see Prenotazione#titolare
     */
    public String getTitolare() {
        return titolare.get();
    }

    /**
     * @return il titolare come property
     * @see Prenotazione#titolare
     */
    public  StringProperty getTitolareProperty(){
        return titolare;
    }

    /**
     * Modifica il titolare della prenotazione
     * @see Prenotazione#titolare
     * @param titolare il titolare della prenotazione da settare
     */
    public void setTitolare(String titolare) {
        this.titolare = new SimpleStringProperty(titolare);
    }

    /**
     * @return il recapito
     * @see Prenotazione#recapito
     */
    public String getRecapito() {
        return recapito.get();
    }

    /**
     * @return il recapito come property
     * @see Prenotazione#recapito
     */
    public StringProperty getRecapitoProperty(){
        return recapito;
    }

    /**
     * Modifica il recapito del titolare
     * @see Prenotazione#recapito
     * @param recapito recapito del titolare da settare
     */
    public void setRecapito(String recapito) {
        this.recapito = new SimpleStringProperty(recapito);
    }
}
