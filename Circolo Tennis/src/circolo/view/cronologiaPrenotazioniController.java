package circolo.view;

import circolo.Database;
import circolo.Prenotazione;
import circolo.util.AlertUtil;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.sql.SQLException;
import java.time.LocalDate;

public class cronologiaPrenotazioniController {
    @FXML
    private TableView<Prenotazione> table;
    @FXML
    private TableColumn<Prenotazione, String> titolare;
    @FXML
    private TableColumn<Prenotazione, String> recapito;
    @FXML
    private TableColumn<Prenotazione, Integer> numCampo;
    @FXML
    private TableColumn<Prenotazione, String> Inizio;
    @FXML
    private TableColumn<Prenotazione, String> Fine;
    @FXML
    private TableColumn<Prenotazione, String> importo;
    @FXML
    private TableColumn<Prenotazione, String> pagato;
    @FXML
    private DatePicker data;
    @FXML
    private Button pagatoButton;

    private Prenotazione prenotazione = new Prenotazione();

    private Database db;

    @FXML
    private void initialize() {
        titolare.setCellValueFactory(cellData -> cellData.getValue().getTitolareProperty());
        recapito.setCellValueFactory(cellData -> cellData.getValue().getRecapitoProperty());
        numCampo.setCellValueFactory(cellData -> cellData.getValue().getcampo().getNumeroCampoProperty().asObject());
        Inizio.setCellValueFactory(cellData -> cellData.getValue().getInizioProperty().asString());
        Fine.setCellValueFactory(cellData -> cellData.getValue().getFineProperty().asString());
        importo.setCellValueFactory(cellData -> cellData.getValue().getImportoProperty());
        pagato.setCellValueFactory(cellData -> cellData.getValue().getPagato() == 1 ? new SimpleStringProperty("Sì") : new SimpleStringProperty("No"));

        table.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> Selezione(newValue));
    }

    private void Selezione(Prenotazione prenotazione) {
        this.prenotazione = prenotazione;
        if (this.prenotazione != null)
            if (prenotazione.getPagato() == 0)
                pagatoButton.setDisable(false);
            else pagatoButton.setDisable(true);
        else pagatoButton.setDisable(true);
    }




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

    @FXML
    private void handleTrova() throws SQLException {
        ObservableList<Prenotazione> lista;
        if (isInputValid()) {
           lista = db.trovaPrenotazioni(LocalDate.parse(data.getValue().toString()),0);
            if(lista.size() == 0)
                AlertUtil.displayPersonalizedInfo("Nessuna prenotazione trovata per il giorno inserito", "Inserire un'altra data");
            else table.setItems(lista);
        }

    }

    private boolean isInputValid() {
        String errorMessage = "";
        if (data.getValue() == null || data.getValue().toString().length() == 0)
            errorMessage += "Inserisci la data delle prenotazioni da trovare\n";
        if (errorMessage.length() == 0) {
            return true;
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Errore");
            alert.setHeaderText("Campo non valido");
            alert.setContentText(errorMessage);

            alert.showAndWait();

            return false;
        }
    }

    public void setDatabase(Database db) {
        this.db = db;
    }
}
