package circolo.view;

import circolo.Campo;
import circolo.Database;
import circolo.Prenotazione;
import circolo.util.DateUtil;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.sql.SQLException;

/**
 * Classe che gestisce l'interfaccia della scelta di un campo disponibile per prenotazione
 */
public class risultatiPrenotazioniController {
    @FXML
    private TableView<Campo> table;
    @FXML
    private TableColumn<Campo,Integer> numCol;
    @FXML
    private TableColumn<Campo,String> superCol;
    @FXML
    private TableColumn<Campo,String> posCol;
    @FXML
    private Button prenota;
    @FXML
    private TextField nome;
    @FXML
    private TextField cognome;
    @FXML
    private TextField recapito;
    @FXML
    private CheckBox pagato;

    private Prenotazione pren = new Prenotazione();

    private Stage resultStage;
    private Database db;

    public risultatiPrenotazioniController(){}

    @FXML
    private void initialize(){
        numCol.setCellValueFactory(cellData -> cellData.getValue().getNumeroCampoProperty().asObject());
        superCol.setCellValueFactory(cellData -> cellData.getValue().getSuperficieProperty());
        posCol.setCellValueFactory(cellData -> cellData.getValue().getPosizioneProperty());

        table.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> Selezione(newValue));

        try {
            db = Database.getInstance();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void inserisciRisultatiTabella(ObservableList<Campo> lista){
        table.setItems(lista);
    }


    private void Selezione(Campo campo){
        this.pren.setcampo(campo);
        prenota.setDisable(false);
    }

    /**
     * Inserisce la prenotazione nel database
     * @throws SQLException
     */
    @FXML
    private void handlePrenota() throws SQLException{
        if(isInputValid()) {
            pren.setTitolare(nome.getText() + " " + cognome.getText());
            pren.setRecapito(recapito.getText());
            pren.setPagato(pagato.isSelected() ? 1 : 0);
            db.InserisciPrenotazione(pren);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Prenotazione effettuata");
            alert.setHeaderText("Riepilogo dati");
            String dati = "Numero Campo: " + String.valueOf(pren.getcampo().getNumero_campo()) + "\n" +
                    "Superficie: " + pren.getcampo().getSuperficie() + "\n" +
                    "Posizione: " + pren.getcampo().getPosizione() + "\n" +
                    "Giorno: " + DateUtil.format(pren.getData()) + "\n" +
                    "Ora inizio: " + pren.getInizio().toString() + "\n" +
                    "Ora fine: " + pren.getFine().toString() + "\n" +
                    "Titolare: " + pren.getTitolare() + "\n" +
                    "Recapito: " + pren.getRecapito();
            alert.setContentText(dati);
            alert.showAndWait();
            resultStage.close();
        }
    }

    /**
     * @param prenotazione prenotazione che contiene il giorno e l'ora
     */
    public void setPrenotazione(Prenotazione prenotazione){
       this.pren = prenotazione;
    }

    /**
     * setta il controllo della finestra
     * @param resultStage
     */
    public void setResultStage(Stage resultStage){
        this.resultStage = resultStage;
    }

    private boolean isInputValid(){
        String errorMessage = "";

        if (nome.getText() == null || nome.getText().length() == 0) {
            errorMessage += "Nome non può essere vuoto\n";
        }
        if (cognome.getText() == null || cognome.getText().length() == 0) {
            errorMessage += "Cognome non può essere vuoto\n";
        }
        if (recapito.getText() == null || recapito.getText().length() == 0) {
            errorMessage += "Recapito non può essere vuoto\n";
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



}
