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
        private void shownewGiocatore() throws IOException {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/newGiocatore.fxml"));
            BorderPane iscritto = loader.load();
            newGiocatoreController controller = loader.getController();
            controller.setDatabase(db);
            clearPane("iscritti");
            splitIscritti.getItems().add(iscritto);
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

    //PANNELLO GESTIONE PRENOTAZIONI

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

    //PANNELLO GESTIONE VISITE MEDICHE

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

    //PANNELLO MATCHPLAY

    @FXML
    private void showNuovoPartecipante() throws IOException {
       /* Button Iscritto = new Button("Iscritto");
        Button nonIscritto = new Button("Non Iscritto");
        clearPane("matchplay");
        BorderPane choicePane = new BorderPane();
        HBox hBox = new HBox();
        choicePane.setCenter(hBox);
        hBox.getChildren().addAll(Iscritto,nonIscritto);
        HBox.setMargin(Iscritto, new Insets(150, 0, 0, 90));
        HBox.setMargin(nonIscritto, new Insets(150,0,0,10));
        splitMatchplay.getItems().add(choicePane);

        nonIscritto.setOnAction(event -> {
            try {
                shownewGiocatore();
            } catch (IOException e) {
                e.printStackTrace();
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Errore");
                alert.setHeaderText("Si è verificato un errore interno");
                alert.setContentText("Riprova");
            }
        });*/
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(MainApp.class.getResource("view/nuovoPartecipanteMatchplay.fxml"));
        BorderPane partecipante = loader.load();
        clearPane("matchplay");
        splitMatchplay.getItems().add(partecipante);
        nuovoPartecipanteMatchplayController controller = loader.getController();
        controller.setDatabase(db);

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
