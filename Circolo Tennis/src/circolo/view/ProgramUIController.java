package circolo.view;

import circolo.MainApp;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;

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
    private ComboBox<String> sezioneMatchplay;


    BorderPane pane;
    private boolean prenotazioniLoaded;
    private boolean matchplayLoaded;
    private boolean partiteLoaded;
    private boolean gironiLoaded;
    private BorderPane partitePane;
    private BorderPane gironiPane;
    private BorderPane iscrizioniPane;
    private BorderPane contentMatchplay;

    public ProgramUIController() {
    }

    @FXML
    private void initialize() {
        MainApp.getRoot().getTop().setVisible(true);
        prenotazioniLoaded = false;
        matchplayLoaded = false;
        sezioneMatchplay.getItems().addAll("Iscrizioni","Gironi","Partite");
        sezioneMatchplay.setValue("Iscrizioni");
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
    private void showGestionePrenotazioni() {
        if (!prenotazioniLoaded) {
            prenotazioniLoaded = true;
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

    @FXML
    private void showGestioneMatchplay() {
        if (!matchplayLoaded) {
            matchplayLoaded = true;
            FXMLLoader loaderMatchplay = new FXMLLoader();
            loaderMatchplay.setLocation(MainApp.class.getResource("view/GestioneMatchplay_Iscrizioni.fxml"));
            try {
                iscrizioniPane = loaderMatchplay.load();
                contentMatchplay = (BorderPane) Matchplay.getContent();
                contentMatchplay.setCenter(iscrizioniPane);
                GestioneMatchplay_IscrizioniController controller = loaderMatchplay.getController();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    private void handleCambioSezione() {
        switch (sezioneMatchplay.getValue()) {
            case "Partite":
                if (!partiteLoaded) {
                    partiteLoaded = true;
                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(MainApp.class.getResource("view/GestioneMatchplay_Partite.fxml"));
                    try {
                        partitePane = loader.load();
                        contentMatchplay.setCenter(partitePane);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else contentMatchplay.setCenter(partitePane);
                break;

            case "Gironi":
                if (!gironiLoaded) {
                    gironiLoaded = true;
                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(MainApp.class.getResource("view/GestioneMatchplay_Gironi.fxml"));
                    try {
                        gironiPane = loader.load();
                        contentMatchplay.setCenter(gironiPane);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else contentMatchplay.setCenter(gironiPane);
                break;

            default:
                contentMatchplay.setCenter(iscrizioniPane);
        }

    }

}
