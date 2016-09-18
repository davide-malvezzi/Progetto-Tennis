package circolo.view;

import circolo.Database;
import circolo.MainApp;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.BorderPane;

import java.io.IOException;

public class ProgramUIController {
    @FXML
    private Button Nuovo;
    @FXML
    private Button Ricerca;

    @FXML
    private SplitPane splitIscritti;

    @FXML
    private SplitPane splitPrenotazioni;

    @FXML
    private SplitPane splitVisite;

    private MainApp mainApp;
    private Database db;

    public ProgramUIController() {
    }

    @FXML
    private void initialize() {
    }

        @FXML
        private void shownewGiocatore() throws IOException {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/newGiocatore.fxml"));
            BorderPane nuovo = loader.load();
            newGiocatoreController controller = loader.getController();
            controller.setDatabase(db);
            clearPane("iscritti");
            splitIscritti.getItems().add(nuovo);
        }

        @FXML
        private void showricercaGiocatore() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(MainApp.class.getResource("view/ricercaGiocatore.fxml"));
        BorderPane ricerca = loader.load();
        clearPane("iscritti");
        splitIscritti.getItems().add(ricerca);
        ricercaGiocatoreController controller = loader.getController();
        controller.setMainApp(mainApp);
        controller.setDatabase(db);
    }

    @FXML
    private void showprenotazioneCampo() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(MainApp.class.getResource("view/prenotazioneCampo.fxml"));
        BorderPane prenotazione = loader.load();
        clearPane("prenotazioni");
        splitPrenotazioni.getItems().add(prenotazione);
        prenotazioneCampoController controller = loader.getController();
        controller.setMainapp(mainApp);
        controller.setDatabase(db);
    }

    @FXML
    private void showUltimePrenotazioni() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(MainApp.class.getResource("view/cronologiaPrenotazioni.fxml"));
        BorderPane prenotazione = loader.load();
        clearPane("prenotazioni");
        splitPrenotazioni.getItems().add(prenotazione);
        cronologiaPrenotazioniController controller = loader.getController();
        controller.setDatabase(db);
    }

    @FXML
    private void showNewVisitaMedica() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(MainApp.class.getResource("view/nuovaVisitaMedica.fxml"));
        BorderPane visita = loader.load();
        clearPane("visite");
        splitVisite.getItems().add(visita);
        newVisitaMedicaController controller = loader.getController();
        controller.setDatabase(db);
    }


    private void clearPane(String s) {
        switch (s){
            case "iscritti": if (splitIscritti.getItems().size() != 1) splitIscritti.getItems().remove(1);
                break;
            case "prenotazioni": if (splitPrenotazioni.getItems().size() != 1) splitPrenotazioni.getItems().remove(1);
                break;
            case "visite": if (splitVisite.getItems().size() != 1) splitVisite.getItems().remove(1);
        }
    }

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }

    public void setDatabase(Database db){
        this.db = db;
    }

}
