package circolo.view;

import circolo.Campo;
import circolo.Database;
import circolo.MainApp;
import circolo.Prenotazione;
import circolo.util.AlertUtil;
import circolo.util.DateUtil;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Classe che gestisce l'interfaccia del pannello Prenotazioni
 */
public class gestionePrenotazioniController {
    @FXML
    private DatePicker dataPrenotazione;
    @FXML
    private TextField oraInizio;
    @FXML
    private TextField oraFine;
    @FXML
    private ChoiceBox<String> superficieBox;
    @FXML
    private TableView<Prenotazione> table;
    @FXML
    private TableColumn<Prenotazione, String> titolareCol;
    @FXML
    private TableColumn<Prenotazione, String> recapitoCol;
    @FXML
    private TableColumn<Prenotazione, Integer> numCampoCol;
    @FXML
    private TableColumn<Prenotazione, String> dataCol;
    @FXML
    private TableColumn<Prenotazione, String> InizioCol;
    @FXML
    private TableColumn<Prenotazione, String> FineCol;
    @FXML
    private TableColumn<Prenotazione, String> pagatoCol;
    @FXML
    private DatePicker dataRicerca;
    @FXML
    private Button pagatoButton;
    @FXML
    private Button elimina;
    @FXML
    private Button avanti;
    @FXML
    private Button indietro;

    private Database db;
    private Prenotazione prenotazione;
    ObservableList<Prenotazione> lista;
    private int offset = 0;
    private boolean cercaClick = false;
    private int offsetCerca = 0;


    @FXML
    private void initialize() {

        titolareCol.setCellValueFactory(cellData -> cellData.getValue().getTitolareProperty());
        recapitoCol.setCellValueFactory(cellData -> cellData.getValue().getRecapitoProperty());
        numCampoCol.setCellValueFactory(cellData -> cellData.getValue().getcampo().getNumeroCampoProperty().asObject());
        dataCol.setCellValueFactory(cellData -> cellData.getValue().getDataProperty());
        InizioCol.setCellValueFactory(cellData -> cellData.getValue().getInizioProperty().asString());
        FineCol.setCellValueFactory(cellData -> cellData.getValue().getFineProperty().asString());
        pagatoCol.setCellValueFactory(cellData -> cellData.getValue().getPagato() == 1 ? new SimpleStringProperty("Sì") : new SimpleStringProperty("No"));

        table.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> Selezione(newValue));
        ButtonBar.setButtonData(avanti, ButtonBar.ButtonData.LEFT);
        ButtonBar.setButtonData(indietro, ButtonBar.ButtonData.LEFT);
        indietro.setDisable(true);
        try {
            db = Database.getInstance();
            lista = db.loadPrenotazioni(0);
            table.setItems(lista);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }


    /**
     * Metdodo che fa avanzare la lista delle prenotazioni
     */
    @FXML
    private void handleAvanti() {
        indietro.setDisable(false);
        Selezione(null);
        try {
            if (cercaClick) {
                offsetCerca += 10;
                lista = db.trovaPrenotazioni(LocalDate.parse(dataRicerca.getValue().toString()), offsetCerca);
                table.setItems(lista);
                if (lista.size() < 10) avanti.setDisable(true);
            } else {
                offset += 10;
                lista = db.loadPrenotazioni(offset);
                table.setItems(lista);
                if (lista.size() < 10) avanti.setDisable(true);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Metodo che scorre la lista delle prenotazioni all'indietro
     */
    @FXML
    private void handleIndietro() {
        Selezione(null);
        try {
            if (cercaClick) {
                offsetCerca -= 10;
                if (offsetCerca == 0) indietro.setDisable(true);
                lista = db.trovaPrenotazioni(LocalDate.parse(dataRicerca.getValue().toString()), offsetCerca);
                table.setItems(lista);
                avanti.setDisable(false);
            } else {
                offset -= 10;
                if (offset == 0) indietro.setDisable(true);
                lista = db.loadPrenotazioni(offset);
                table.setItems(lista);
                avanti.setDisable(false);
                if (offset == 0) indietro.setDisable(true);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Metodo che verifica la disponibilità di campi per la prenotazione
     * @throws SQLException
     */
    @FXML
    private void handleVerificaDisponibilità() throws SQLException {
        Prenotazione prenotazione = new Prenotazione();
        ObservableList<Campo> listaCampi = FXCollections.observableArrayList();
        if (isInputValid()) {
            prenotazione.setData(LocalDate.parse(dataPrenotazione.getValue().toString()));
            prenotazione.setInizio(LocalTime.parse(oraInizio.getText()));
            prenotazione.setFine(LocalTime.parse(oraFine.getText()));
            listaCampi = db.checkDisponibilità(prenotazione);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            if (listaCampi.size() > 0) {
                try {
                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(MainApp.class.getResource("view/risultatiPrenotazioni.fxml"));
                    BorderPane pane = loader.load();
                    risultatiPrenotazioniController controller = loader.getController();
                    controller.inserisciRisultatiTabella(listaCampi);
                    controller.setPrenotazione(prenotazione);
                    Stage dialogStage = new Stage();
                    dialogStage.setTitle("Campi Disponibili");
                    dialogStage.initModality(Modality.WINDOW_MODAL);
                    dialogStage.setResizable(false);
                    dialogStage.initOwner(MainApp.getPrimaryStage());
                    Scene scene = new Scene(pane);
                    dialogStage.setScene(scene);
                    controller.setResultStage(dialogStage);
                    dialogStage.showAndWait();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                alert.setHeaderText("Non sono stati trovati campi disponibili");
                alert.showAndWait();
            }
        }
    }


    /**
     * Metodo che azzera i campi di ricerca
     */
    @FXML
    private void handleAnnulla() {
        dataPrenotazione.setValue(null);
        oraInizio.setText("");
        oraFine.setText("");
        superficieBox.setValue(null);
    }


    /**
     * Metodo che verifica che i parametri inseriti nelle caselle di ricerca siano accettabili
     * @return <code>true</code> se almeno un parametro è inserito e la date siano nel formato corretto, <code>false</code> altrimenti
     */
    private boolean isInputValid() {
        String errorMessage = "";
        boolean orainizioOK = false;
        if (dataPrenotazione.getValue() == null) {
            errorMessage += "Giorno non può essere vuoto\n";
        }
        if (oraInizio.getText() == null || oraInizio.getText().length() == 0) {
            errorMessage += "Ora inizio non può essere vuota\n";
        } else {
            if (DateUtil.validTime(oraInizio.getText())) {
                orainizioOK = true;
            } else errorMessage += "Ora inizio non valida. Usa il formato hh:mm\n";
        }

        if (oraFine.getText() == null || oraFine.getText().length() == 0) {
            errorMessage += "Ora fine non può essere vuota\n";
        } else {
            if (DateUtil.validTime(oraFine.getText())) {
                if (orainizioOK) {
                    if (LocalTime.parse(oraFine.getText()).isBefore(LocalTime.parse(oraInizio.getText())))
                        errorMessage += "Ora fine non può essere minore di ora inizio\n";
                }
            } else errorMessage += "Ora fine non valida. Usa il formato hh:mm\n";
        }

        if (errorMessage.length() == 0) {
            return true;
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Errore");
            alert.setHeaderText("Campi non validi");
            alert.setContentText(errorMessage);

            alert.showAndWait();

            return false;
        }
    }

    private void Selezione(Prenotazione prenotazione) {
        this.prenotazione = prenotazione;
        if (this.prenotazione != null) {
            elimina.setDisable(false);
            if (prenotazione.getPagato() == 0)
                pagatoButton.setDisable(false);
            else pagatoButton.setDisable(true);
        }
        else pagatoButton.setDisable(true);
    }


    /**
     * setta la prenotazione selezionata come pagata
     */
    @FXML
    private void setPagato() {
        prenotazione.setPagato(1);
        try {
            db.setPrenotazionePagata(prenotazione);
            table.refresh();
        } catch (SQLException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Errore");
            alert.setHeaderText("Problema connessione al database");
            alert.setContentText("Riprova più tardi");
            alert.showAndWait();
        }
    }

    /**
     * elimina una prenotazione
     */
    @FXML
    private void handleElimina(){

        try {
            db.eliminaPrenotazione(prenotazione);
            lista.remove(lista.indexOf(prenotazione));
            AlertUtil.displayPersonalizedInfo("Operazione effettuata","Prenotazione Cancellata");
        } catch (SQLException e) {
            e.printStackTrace();
            AlertUtil.displayGenericError();
        }

    }

    /**
     * trova prenotazioni in base alla data inserita
     */
    @FXML
    private void handleTrova() {
        offsetCerca = 0;
        cercaClick = true;
        if (dataRicerca.getValue() != null) {
            try {
                lista = db.trovaPrenotazioni(LocalDate.parse(dataRicerca.getValue().toString()), offsetCerca);

                if (lista.size() == 0) {
                    AlertUtil.displayPersonalizedInfo("Nessuna prenotazione trovata per il giorno inserito", "Inserire un'altra data");
                    cercaClick = false;
                    handleReset();
                } else {
                    table.setItems(lista);
                    if (lista.size() < 10)
                        avanti.setDisable(true);
                    indietro.setDisable(true);
                }
            } catch (SQLException e) {
                e.printStackTrace();
                AlertUtil.displayGenericError();
            }
        }
    }

    /**
     * annulla la ricerca e ritorna allo stato precedente
     */
    @FXML
    private void handleReset() {
        cercaClick = false;
        dataRicerca.setValue(null);
        offsetCerca = 0;
        try {
            lista = db.loadPrenotazioni(offset);
            if (offset == 0) {
                indietro.setDisable(true);
                avanti.setDisable(false);
            } else {
                indietro.setDisable(false);
                if (lista.size() < 10)
                    avanti.setDisable(true);
            }
            table.setItems(lista);
        } catch (SQLException e) {
            e.printStackTrace();
            AlertUtil.displayGenericError();
        }
    }

}
