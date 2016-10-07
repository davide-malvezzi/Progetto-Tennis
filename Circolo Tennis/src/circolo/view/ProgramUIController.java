package circolo.view;

import circolo.Database;
import circolo.MainApp;
import circolo.util.AlertUtil;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.BorderPane;

import java.io.IOException;

public class ProgramUIController {
    @FXML
    private SplitPane splitIscritti;
    @FXML
    private SplitPane splitPrenotazioni;
    @FXML
    private SplitPane splitVisite;
    @FXML
    private SplitPane splitMatchplay;

    private MainApp mainApp;
    private Database db;

    public ProgramUIController() {
    }

    @FXML
    private void initialize() {
    }
        //PANNELLO GESTIONE ISCRITTI
        @FXML
        private void shownewGiocatore() {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/newGiocatore.fxml"));
            BorderPane iscritto = null;
            try {
                iscritto = loader.load();
                newGiocatoreController controller = loader.getController();
                controller.setDatabase(db);
                clearPane("iscritti");
                splitIscritti.getItems().add(iscritto);
            } catch (IOException e) {
                e.printStackTrace();
                AlertUtil.displayGenericError();
            }

        }

        @FXML
        private void showricercaGiocatore() {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(MainApp.class.getResource("view/ricercaGiocatore.fxml"));
            BorderPane ricerca = null;
            try {
                ricerca = loader.load();
                ricercaGiocatoreController controller = loader.getController();
                controller.setMainApp(mainApp);
                controller.setDatabase(db);
                clearPane("iscritti");
                splitIscritti.getItems().add(ricerca);
            } catch (IOException e) {
                e.printStackTrace();
            }


    }

    //PANNELLO GESTIONE PRENOTAZIONI

    @FXML
    private void showprenotazioneCampo() {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(MainApp.class.getResource("view/prenotazioneCampo.fxml"));
        BorderPane prenotazione = null;
        try {
            prenotazione = loader.load();
            clearPane("prenotazioni");
            splitPrenotazioni.getItems().add(prenotazione);
            prenotazioneCampoController controller = loader.getController();
            controller.setMainapp(mainApp);
            controller.setDatabase(db);
        } catch (IOException e) {
            e.printStackTrace();
            AlertUtil.displayGenericError();
        }

    }

    @FXML
    private void showUltimePrenotazioni() {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(MainApp.class.getResource("view/cronologiaPrenotazioni.fxml"));
        BorderPane prenotazione;
        try {
            prenotazione = loader.load();
            clearPane("prenotazioni");
            splitPrenotazioni.getItems().add(prenotazione);
            cronologiaPrenotazioniController controller = loader.getController();
            controller.setDatabase(db);
        } catch (IOException e) {
            e.printStackTrace();
            AlertUtil.displayGenericError();
        }

    }

    //PANNELLO GESTIONE VISITE MEDICHE

    @FXML
    private void showNewVisitaMedica() {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(MainApp.class.getResource("view/nuovaVisitaMedica.fxml"));
        BorderPane visita = null;
        try {
            visita = loader.load();
            clearPane("visite");
            splitVisite.getItems().add(visita);
            newVisitaMedicaController controller = loader.getController();
            controller.setDatabase(db);
        } catch (IOException e) {
            e.printStackTrace();
            AlertUtil.displayGenericError();
        }

    }

    //PANNELLO MATCHPLAY

    @FXML
    private void showNuovoPartecipante() {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(MainApp.class.getResource("view/nuovoPartecipanteMatchplay.fxml"));
        BorderPane partecipante = null;
        try {
            partecipante = loader.load();
            clearPane("matchplay");
            splitMatchplay.getItems().add(partecipante);
            nuovoPartecipanteMatchplayController controller = loader.getController();
            controller.setDatabase(db);
        } catch (IOException e) {
            e.printStackTrace();
            AlertUtil.displayGenericError();
        }

    }

    @FXML
    private void showListaPartecipanti(){
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(MainApp.class.getResource("view/ListaPartecipantiMatchplay.fxml"));
        BorderPane lista;
        try {
            lista = loader.load();
            clearPane("matchplay");
            splitMatchplay.getItems().add(lista);
            ListaPartecipantiMatchplayController controller = loader.getController();
            controller.setDatabase(db);
            controller.inserisciLista();
        } catch (IOException e) {
            e.printStackTrace();
            AlertUtil.displayGenericError();
        }
    }

    @FXML
    private void showGironi(){
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(MainApp.class.getResource("view/gironiMatchplay.fxml"));
        BorderPane gironi;
        try{
            gironi = loader.load();
            clearPane("matchplay");
            splitMatchplay.getItems().add(gironi);
            gironiMatchplayController controller = loader.getController();

        }catch (IOException e){
            e.printStackTrace();
            AlertUtil.displayGenericError();
        }
    }

    private void clearPane(String s) {
        switch (s){
            case "iscritti": if (splitIscritti.getItems().size() != 1) splitIscritti.getItems().remove(1);
                break;
            case "prenotazioni": if (splitPrenotazioni.getItems().size() != 1) splitPrenotazioni.getItems().remove(1);
                break;
            case "visite": if (splitVisite.getItems().size() != 1) splitVisite.getItems().remove(1);
                break;
            case "matchplay": if (splitMatchplay.getItems().size() != 1) splitMatchplay.getItems().remove(1);
        }
    }

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }

    public void setDatabase(Database db){
        this.db = db;
    }

}
