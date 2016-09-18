package circolo;


import javafx.beans.property.*;

import java.time.LocalDate;
import java.time.LocalTime;

public class Prenotazione {
    int numeroPrenotazione;
    ObjectProperty<LocalDate> data;
    ObjectProperty<LocalTime> inizio;
    ObjectProperty<LocalTime> fine;
    Campo campo;
    StringProperty recapito;
    IntegerProperty pagato;
    StringProperty titolare;
    StringProperty importo;

    public Prenotazione(){
        campo = new Campo();
        data = new SimpleObjectProperty<>();
        inizio = new SimpleObjectProperty<>();
        fine = new SimpleObjectProperty<>();
        titolare = new SimpleStringProperty();
        recapito = new SimpleStringProperty();
        importo = new SimpleStringProperty();
        pagato = new SimpleIntegerProperty();
    }

    public Prenotazione(int numeroPrenotazione,LocalDate data,LocalTime Inizio, LocalTime Fine, Campo campo, String titolare,String recapito,int pagato,String importo){
        this.numeroPrenotazione = numeroPrenotazione;
        this.data = new SimpleObjectProperty<>(data);
        this.inizio = new SimpleObjectProperty<>(Inizio);
        this.fine = new SimpleObjectProperty<>(Fine);
        this.campo = campo;
        this.titolare = new SimpleStringProperty(titolare);
        this.recapito = new SimpleStringProperty(recapito);
        this.pagato = new SimpleIntegerProperty(pagato);
        this.importo = new SimpleStringProperty(importo);
    }

    public Prenotazione(LocalDate data,LocalTime Inizio, LocalTime Fine, Campo campo) {
        this.data = new SimpleObjectProperty<>(data);
        this.inizio = new SimpleObjectProperty<>(Inizio);
        this.fine = new SimpleObjectProperty<>(Fine);
        this.campo = campo;
    }

    public void setNumeroPrenotazione(int numeroPrenotazione){
        this.numeroPrenotazione = numeroPrenotazione;
    }

    public int getNumeroPrenotazione(){
        return numeroPrenotazione;
    }

    public LocalDate getData() {
        return data.get();
    }

    public ObjectProperty<LocalDate> getDataProperty() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = new SimpleObjectProperty<>(data);
    }

    public LocalTime getInizio() {
        return inizio.get();
    }

    public ObjectProperty<LocalTime> getInizioProperty() {
        return inizio;
    }

    public void setInizio(LocalTime inizio) {
        this.inizio = new SimpleObjectProperty<>(inizio);
    }
    public LocalTime getFine() {
        return fine.get();
    }

    public ObjectProperty<LocalTime> getFineProperty() {
        return fine;
    }

    public void setFine(LocalTime fine) {
        this.fine = new SimpleObjectProperty<>(fine);
    }


    public Campo getcampo() {
        return campo;
    }

    public void setcampo(Campo campo) {
        this.campo = campo;
    }

    public int getPagato() {
        return pagato.get();
    }

    public IntegerProperty getPagatoProperty(){
        return pagato;
    }

    public void setPagato(int pagato) {
        this.pagato = new SimpleIntegerProperty(pagato);
    }

    public String getTitolare() {
        return titolare.get();
    }

    public  StringProperty getTitolareProperty(){
        return titolare;
    }

    public void setTitolare(String titolare) {
        this.titolare = new SimpleStringProperty(titolare);
    }

    public String getImporto() {
        return importo.get();
    }

    public StringProperty getImportoProperty(){
        return importo;
    }

    public void setImporto(String importo) {
        this.importo = new SimpleStringProperty(importo);
    }

    public String getRecapito() {
        return recapito.get();
    }

    public StringProperty getRecapitoProperty(){
        return recapito;
    }

    public void setRecapito(String recapito) {
        this.recapito = new SimpleStringProperty(recapito);
    }
}
