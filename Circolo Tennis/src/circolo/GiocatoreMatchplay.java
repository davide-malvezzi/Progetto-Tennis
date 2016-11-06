package circolo;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;


/**
 * Classe che modella i giocatori iscritti al Matchplay
 */
public class GiocatoreMatchplay extends Giocatore {
    /**
     * Indica il punteggio raggiunto dal giocatore al Matchplay
     */
    private IntegerProperty puntiMatchplay;
    ;

    /**
     * Edizione del Matchplay a cui è iscritto il giocatore
     */
    private IntegerProperty edizione;

    /**
     * Girone in cui il giocatore è stato inserito
     */
    private IntegerProperty girone;


    /**
     * Costruttore di default
     */
    public GiocatoreMatchplay(){
        puntiMatchplay = new SimpleIntegerProperty();
        edizione = new SimpleIntegerProperty();
        girone = new SimpleIntegerProperty();
    }

    /**
     * @return i punti del giocatore
     * @see GiocatoreMatchplay#puntiMatchplay
     */
    public int getPuntiMatchplay() {
        return puntiMatchplay.get();
    }

    /**
     * @return i punti del giocatore come property
     * @see GiocatoreMatchplay#puntiMatchplay
     */
    public IntegerProperty getPuntiMatchplayProperty() {
        return puntiMatchplay;
    }

    /**
     * Modifica il punteggio del giocatore
     * @see GiocatoreMatchplay#puntiMatchplay
     * @param puntiMatchplay punti del giocatore da settare
     */
    public void setPuntiMatchplay(int puntiMatchplay) {
        this.puntiMatchplay.set(puntiMatchplay);
    }

    /**
     * @return l'edizione del matchplay a cui è iscritto il giocatore
     * @see GiocatoreMatchplay#edizione
     */
    public int getEdizione() {
        return edizione.get();
    }

    /**
     * @return l'edizione del matchplay a cui è iscritto il giocatore come property
     * @see GiocatoreMatchplay#edizione
     */
    public IntegerProperty edizioneProperty() {
        return edizione;
    }

    /**
     * Modifica l'edizione del matchplay
     * @see GiocatoreMatchplay#edizione
     * @param edizione edizione del matchplay da settare
     */
    public void setEdizione(int edizione) {
        this.edizione.set(edizione);
    }

    /**
     * @return il girone del giocatore
     * @see GiocatoreMatchplay#girone
     */
    public int getGirone() {
        return girone.get();
    }

    /**
     * @returnil girone del giocatore come property
     * @see GiocatoreMatchplay#girone
     */
    public IntegerProperty gironeProperty() {
        return girone;
    }

    /**
     * Modifica il girone del giocatore
     * @see GiocatoreMatchplay#girone
     * @param girone girone del matchplay da settare
     */
    public void setGirone(int girone) {
        this.girone.set(girone);
    }
}
