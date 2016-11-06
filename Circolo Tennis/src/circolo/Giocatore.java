package circolo;


import circolo.util.DateUtil;
import javafx.beans.property.*;

import java.time.LocalDate;

/**
 * Classe che modella i giocatori iscritti nel sistema
 */
public class Giocatore {

    /**
     * Primary key del giocatore nel database
     */
    private int ID;


    /**
     * Nome del giocatore
     *
     */
    private StringProperty nome = new SimpleStringProperty();


    /**
     * Cognome del giocatore
     *
     */
    private StringProperty cognome = new SimpleStringProperty();

    /**
     * Data di nascita del giocatore
     */
    private ObjectProperty<LocalDate> data_nascita = new SimpleObjectProperty<>();
    ;
    /**
     * Codice fiscale del giocatore
     */
    private StringProperty CF = new SimpleStringProperty();
    ;
    /**
     * Sesso del giocatore
     */
    private StringProperty genere = new SimpleStringProperty();
    ;
    /**
     * Città di residenza del giocatore
     */
    private StringProperty citta = new SimpleStringProperty();
    ;
    /**
     * Indirizzo di residenza del giocatore
     */
    private StringProperty indirizzo = new SimpleStringProperty();
    ;
    /**
     * Grado del giocatore nell classifica della Federazione Italiana Tennis
     */
    private StringProperty classifica_FIT = new SimpleStringProperty();
    ;
    /**
     * {@link Integer} che assume il valore 1 se il giocatore è un agonista, 0 altrimenti
     *
     */
    private IntegerProperty agonista = new SimpleIntegerProperty();
    /**
     * {@link Integer} che assume il valore 1 se il giocatore è socio del circolo, 0 altrimenti
     *
     */
    private IntegerProperty socio = new SimpleIntegerProperty();
    /**
     * {@link Integer} che assume valori da 1 a 5 in base al grado di forza di un giocatore interno al circolo
     */
    private IntegerProperty fascia = new SimpleIntegerProperty();
    ;



    /**
     * Costruttore di default
     */
    public Giocatore() {
    }

    //getter and setters

    /**
     * Modifica il nome del giocatore
     * @see Giocatore#nome
     * @param nome Nome del giocatore da settare
     */
    public void setNome(String nome) {
        this.nome = new SimpleStringProperty(nome);
    }

    /**
     * @see Giocatore#nome
     * @return Il nome del giocatore
     */
    public String getNome() {
        return nome.get();
    }

    /**
     * @see Giocatore#nome
     * @return Il nome come oggetto Property
     */
    public StringProperty getNomeProperty() {
        return nome;
    }

    /**
     * Modifica il cognome del giocatore
     * @see Giocatore#cognome
     * @param cognome Cognome del giocatore da settare
     */
    public void setCognome(String cognome) {
        this.cognome = new SimpleStringProperty(cognome);
    }


    /**
     * @see Giocatore#cognome
     * @return Il cognome del giocatore
     */
    public String getCognome() {
        return cognome.get();
    }

    /**
     * @return Il cognome del giocatore come oggetto Property
     * @see Giocatore#cognome
     */
    public StringProperty getCognomeProperty() {
        return cognome;
    }


    /**
     * Modifica la data di nascita del giocatore
     * @see Giocatore#data_nascita
     * @param data_nascita data di nascita del giocatore da settare
     */
    public void setData_nascita(LocalDate data_nascita) {
        this.data_nascita = new SimpleObjectProperty<>(data_nascita);
    }

    /**
     * @return Data di nascita del giocatore
     * @see Giocatore#data_nascita
     */
    public LocalDate getData_nascita() {
        return data_nascita.get();
    }

    /**
     * @return Data di nascita come oggetto Property
     * @see Giocatore#data_nascita
     */
    public StringProperty getData_NascitaProperty() {
        return new SimpleStringProperty(DateUtil.format(this.getData_nascita()));
    }

    /**
     * Modifica il codice fiscale del giocatore
     * @param CF codice fiscale del giocatore da settare
     * @see Giocatore#CF
     */
    public void setCF(String CF) {
        this.CF = new SimpleStringProperty(CF);
    }

    /**
     * @return Il codice fiscale del giocatore
     * @see Giocatore#CF
     */
    public String getCF() {
        return CF.get();
    }

    /**
     * @return il codice fiscale come oggetto property
     * @see Giocatore#CF
     */
    public StringProperty getCFProperty() {
        return CF;
    }

    /**
     * @return la classifica FIT del giocatore
     * @see Giocatore#classifica_FIT
     */
    public String getClassifica_FIT() {
        return classifica_FIT.get();
    }

    /**
     * @return la classifica FIT come oggetto Property
     * @see Giocatore#classifica_FIT
     */
    public StringProperty getClassifica_FITProperty() {
        return classifica_FIT;
    }

    /**
     * Modifica il grado del giocatore nella classifica FIT
     * @see Giocatore#classifica_FIT
     * @param classifica_FIT grado della classifica FIT da settare
     */
    public void setClassifica_FIT(String classifica_FIT) {
        this.classifica_FIT = new SimpleStringProperty(classifica_FIT);
    }

    /**
     * @return La fascia del giocatore
     * @see Giocatore#fascia
     */
    public int getFascia() {
        return fascia.get();
    }

    /**
     * @return la fascia del giocatore come oggetto property
     * @see Giocatore#fascia
     */
    public IntegerProperty getFasciaProperty() {
        return fascia;
    }

    /**
     * Modifica la fascia del giocatore
     * @see Giocatore#fascia
     * @param fascia fascia del giocatore da settare
     */
    public void setFascia(int fascia) {
        this.fascia = new SimpleIntegerProperty(fascia);
    }

    /**
     * Modifica il sesso del giocatore
     * @see Giocatore#genere
     * @param genere sesso del giocatore da settare
     */
    public void setGenere(String genere) {
        this.genere = new SimpleStringProperty(genere);
    }

    /**
     * @return il sesso del giocatore
     * @see Giocatore#genere
     */
    public String getGenere() {
        return genere.get();
    }

    /**
     * @return il sesso del giocatore come Property
     * @see Giocatore#genere
     */
    public StringProperty getGenereProperty() {
        return genere;
    }

    /**
     * @return l'indirizzo del giocatore
     * @see Giocatore#indirizzo
     */
    public String getIndirizzo() {
        return indirizzo.get();
    }

    /**
     * @return l'indirizzo del giocatore come Property
     * @see Giocatore#indirizzo
     */
    public StringProperty getIndirizzoProperty() {
        return indirizzo;
    }

    /**
     * Modifica l'indirizzo del giocatore
     * @see Giocatore#indirizzo
     * @param indirizzo indirizzo del giocatore da settare
     */
    public void setIndirizzo(String indirizzo) {
        this.indirizzo = new SimpleStringProperty(indirizzo);
    }

    /**
     * @return la città del giocatore
     * @see Giocatore#citta
     */
    public String getCitta() {
        return citta.get();
    }

    /**
     * @return la citta del giocatore come property
     * @see Giocatore#citta
     */
    public StringProperty getCittaProperty() {
        return citta;
    }

    /**
     * Modifica la città del giocatore
     * @see Giocatore#citta
     * @param citta città del giocatore da settare
     */
    public void setCitta(String citta) {
        this.citta = new SimpleStringProperty(citta);
    }

    /**
     * @return il valore di agonista del giocatore
     * @see Giocatore#agonista
     */
    public int getAgonista() {
        return agonista.get();
    }

    /**
     * @return il valore di agonista come property
     * @see Giocatore#agonista
     */
    public IntegerProperty getAgonistaProperty() {
        return agonista;
    }

    /**
     * Modifica il valore di agonista del giocatore
     * @see Giocatore#agonista
     * @param agonista valore di agonista del giocatore da settare
     */
    public void setAgonista(int agonista) {
        this.agonista = new SimpleIntegerProperty(agonista);
    }

    /**
     * @return il valore di socio del giocatore
     * @see Giocatore#socio
     */
    public int getSocio() {
        return socio.get();
    }
    /**
     * @return il valore di socio del giocatore come property
     * @see Giocatore#socio
     */
    public IntegerProperty getSocioProperty() {
        return socio;
    }

    /**
     * Modifica il valore di socio del giocatore
     * @see Giocatore#socio
     * @param socio valore di socio del giocatore da settare
     */
    public void setSocio(int socio) {
        this.socio = new SimpleIntegerProperty(socio);
    }

    /**
     * @return ID del giocatore
     * @see Giocatore#ID
     */
    public int getID() {
        return ID;
    }

    /**
     * Modifica l'ID del giocatore
     * @see Giocatore#ID
     * @param ID ID del gicatore da settare
     */
    public void setID(int ID) {
        this.ID = ID;
    }
}
