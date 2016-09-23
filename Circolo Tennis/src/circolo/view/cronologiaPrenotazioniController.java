package circolo.view;

import circolo.Database;
import circolo.Prenotazione;
import circolo.util.DateUtil;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.sql.SQLException;

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
    private TableColumn<Prenotazione, String> data;
    @FXML
    private TableColumn<Prenotazione, String> Inizio;
    @FXML
    private TableColumn<Prenotazione, String> Fine;
    @FXML
    private TableColumn<Prenotazione, String> importo;
    @FXML
    private TableColumn<Prenotazione, String> pagato;
    @FXML
    private TextField numero;
    @FXML
    private Button pagatoButton;

    private Prenotazione prenotazione = new Prenotazione();

    private int indice;

    private Database db;

    @FXML
    private void initialize() {
        titolare.setCellValueFactory(cellData -> cellData.getValue().getTitolareProperty());
        recapito.setCellValueFactory(cellData -> cellData.getValue().getRecapitoProperty());
        numCampo.setCellValueFactory(cellData -> cellData.getValue().getcampo().getNumeroCampoProperty().asObject());
        data.setCellValueFactory(cellData -> new SimpleStringProperty(DateUtil.format(cellData.getValue().getDataProperty().get())));
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
        int indice = table.getSelectionModel().getSelectedIndex();
        prenotazione.setPagato(1);
        try {
            db.setPrenotazionePagata(prenotazione);
            table.getItems().set(indice, prenotazione);
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
            lista = db.trovaPrenotazioni(Integer.parseInt(numero.getText()));
            table.setItems(lista);
        }

    }

    private boolean isInputValid() {
        String errorMessage = "";
        if (numero.getText() == null || numero.getText().length() == 0)
            errorMessage += "Inserisci il numero delle ultime prenotazioni da trovare\n";
        else {
            try {
                Integer.parseInt(numero.getText());
            } catch (NumberFormatException e) {
                errorMessage += "Il valore inserito non è un numero";
            }
        }
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
