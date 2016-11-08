package circolo.view;

import circolo.MainApp;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;

import java.io.IOException;

/**
 * Classe che controlla l'interfaccia principale
 */
public class ProgramUIController {
    @FXML
    private TabPane tabPane;
    /**
     * Tab che contiene la gestione Iscritti
     */
    @FXML
    private Tab Iscritti;
    /**
     * Tab che contiene la gestione Prenotazioni
     */
    @FXML
    private Tab Prenotazioni;
    /**
     * Tab che contiene la gestione del Matchplay
     */
    @FXML
    private Tab Matchplay;
    @FXML
    private ComboBox<String> sezioneMatchplay;

    /**
     * Tab che contiene la gestione Strumenti Amministratore
     */
    @FXML
    private Tab Strumenti;

    BorderPane pane;
    /**
     * Boolean che indica se il pannello di gestione prenotazioni è già stato caricato
     */
    private boolean prenotazioniLoaded;
    /**
     * Boolean che indica se il pannello di gestione matchplay è già stato caricato
     */
    private boolean matchplayLoaded;
    /**
     * Boolean che indica se il pannello di gestione partite del matchplay è già stato caricato
     */
    private boolean partiteLoaded;
    /**
     * Boolean che indica se il pannello di gestione gironi del matchplay è già stato caricato
     */
    private boolean gironiLoaded;
    /**
     * Boolean che indica se il pannello di gestione strumenti amministratore è già stato caricato
     */
    private boolean strumentiLoaded;

    private BorderPane partitePane;
    private BorderPane gironiPane;
    private BorderPane iscrizioniPane;
    private BorderPane contentMatchplay;
    private BorderPane strumentiPane;

    public ProgramUIController() {
    }

    @FXML
    private void initialize() {
        MainApp.getRoot().getTop().setVisible(true);
        prenotazioniLoaded = false;
        matchplayLoaded = false;
        strumentiLoaded = false;
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

    /**
     * Metodo che mostra il pannello delle prenotazioni
     */
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

    /**
     * Metodo che mostra il pannello del matchplay
     */
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

    /**
     * metodo che cambia il pannello del matchplay tra iscrizioni, gironi o partite
     */
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

    /**
     * Metodo che mostra il pannello degli strumenti amministratore
     */
    @FXML
    private void showStrumentiAmministratore() {
        if (!strumentiLoaded) {
            strumentiLoaded = true;
            FXMLLoader loaderStrumenti = new FXMLLoader();
            loaderStrumenti.setLocation(MainApp.class.getResource("view/strumentiAmministratore.fxml"));
            try {
                pane = loaderStrumenti.load();
                Strumenti.setContent(pane);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
