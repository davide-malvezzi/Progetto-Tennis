package circolo.view;

import circolo.Database;
import circolo.MainApp;
import circolo.util.AlertUtil;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.SplitPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

import java.io.IOException;

public class ProgramUIController {
    @FXML
    private TabPane tabPane;
    @FXML
    private Tab Iscritti;
    @FXML
    private Tab Prenotazioni;
    @FXML
    private Tab Matchplay;
    @FXML
    private SplitPane splitMatchplay;
    @FXML
    private Tab AdminTools;

    private MainApp mainApp;
    private Database db;
    BorderPane pane;
    private boolean prenotazioniLoaded;

    public ProgramUIController() {
    }

    @FXML
    private void initialize() {
        MainApp.getRoot().getTop().setVisible(true);
        prenotazioniLoaded = false;
        FXMLLoader loaderIscritti = new FXMLLoader();
        try {
            loaderIscritti.setLocation(MainApp.class.getResource("view/gestioneIscritti.fxml"));
            pane = loaderIscritti.load();
            Iscritti.setContent(pane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void showGestionePrenotazioni(){
        if(!prenotazioniLoaded) {
            prenotazioniLoaded= true;
            FXMLLoader loaderPrenotazioni = new FXMLLoader();
            loaderPrenotazioni.setLocation(MainApp.class.getResource("view/gestionePrenotazioni.fxml"));
            try {
                pane = loaderPrenotazioni.load();
                Prenotazioni.setContent(pane);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

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
                clearPane("iscritti");
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
            newVisitaMedicaController controller = loader.getController();
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
        HBox bottom;
        try{
            gironi = loader.load();
            clearPane("matchplay");
            splitMatchplay.getItems().add(gironi);
            gironiMatchplayController controller = loader.getController();
            bottom =  (HBox) gironi.getBottom();
            controller.setControlli(gironi,bottom);
        }catch (IOException e){
            e.printStackTrace();
            AlertUtil.displayGenericError();
        }
    }

    public void showPartite(){
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(MainApp.class.getResource("view/PartiteMatchplay.fxml"));
        BorderPane partite;

        try{
            partite= loader.load();
            clearPane("matchplay");
            splitMatchplay.getItems().add(partite);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void clearPane(String s) {
        switch (s){
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
