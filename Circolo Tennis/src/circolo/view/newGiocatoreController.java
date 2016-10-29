package circolo.view;

import circolo.Database;
import circolo.Giocatore;
import circolo.util.AlertUtil;
import circolo.util.DateUtil;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.sql.SQLException;

public class newGiocatoreController {
    @FXML
    private TextField Nome;
    @FXML
    private TextField Cognome;
    @FXML
    private TextField Data_nascita;
    @FXML
    private TextField CF;
    @FXML
    private TextField Città;
    @FXML
    private TextField Indirizzo;
    @FXML
    private CheckBox agonista;
    @FXML
    private CheckBox socio;
    @FXML
    private ComboBox<String> classifica_fit;
    @FXML
    private ComboBox<Integer> fascia;
    @FXML
    private ComboBox<String> sesso;

    private Giocatore giocatore = new Giocatore();

    private Database db;


    @FXML
    private void initialize() {
        fascia.getItems().addAll(1, 2, 3, 4, 5);
        classifica_fit.getItems().addAll("4.NC", "4.5", "4.4", "4.3", "4.2", "4.1", "4.0",
                "3.9", "3.8", "3.7", "3.6", "3.5", "3.4", "3.3", "3.2", "3.1", "3.0",
                "2.9", "2.8", "2.7", "2.6", "2.5", "2.4", "2.3", "2.2", "2.1", "2.0",
                "1.9", "1.8", "1.7", "1.6", "1.5", "1.4", "1.3", "1.2", "1.1");
        sesso.getItems().addAll("M", "F");
        try{
            db = Database.getInstance();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            AlertUtil.displayGenericError();
        }
    }

    @FXML
    private void handleOK() throws SQLException {
        if (isInputValid()) {
            giocatore.setNome(Nome.getText());
            giocatore.setCognome(Cognome.getText());
            giocatore.setData_nascita(DateUtil.parse(Data_nascita.getText()));
            giocatore.setCF(CF.getText());
            giocatore.setCitta(Città.getText());
            giocatore.setIndirizzo(Indirizzo.getText());
            giocatore.setAgonista(agonista.isSelected() ? 1 : 0);
            giocatore.setSocio(socio.isSelected() ? 1 : 0);
            if(fascia.getValue() != null) giocatore.setFascia(fascia.getValue());
            if(classifica_fit.getValue() != null) giocatore.setClassifica_FIT(classifica_fit.getValue());
            giocatore.setGenere( sesso.getValue());
            if (db.InserisciGiocatore(giocatore)) {
                AlertUtil.displayPersonalizedInfo("Operazione Effettuata","Nuovo giocatore salvato");
                clearFields();
            }else AlertUtil.displayPersonalizedError("Giocatore già iscritto","E' già presente un giocatore con codice fiscale " + giocatore.getCF());
        }
    }

    @FXML
    private void handlePulisciCampi() {
        clearFields();
    }

    private boolean isInputValid() {
        String errorMessage = "";

        if (Nome.getText() == null || Nome.getText().length() == 0) {
            errorMessage += "Nome non può essere vuoto\n";
        }
        if (Cognome.getText() == null || Cognome.getText().length() == 0) {
            errorMessage += "Cognome non può essere vuoto\n";
        }

        if (CF.getText() == null || CF.getText().length() == 0) {
            errorMessage += "Codice fiscale non può essere vuoto\n";
        }
        if (sesso.getValue() == null) {
            errorMessage += "Sesso non può essere vuoto\n";
        }

        if (Data_nascita.getText() == null || Data_nascita.getText().length() == 0) {
            errorMessage += "Data di nascita non può essere vuota\n";
        } else {
            if (!DateUtil.validDate(Data_nascita.getText())) {
                errorMessage += "Data di nascita non valida. Usa il formato gg-mm-aaaa\n";
            }
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

    private void clearFields() {
        Nome.setText("");
        Cognome.setText("");
        Data_nascita.setText("");
        CF.setText("");
        Città.setText("");
        Indirizzo.setText("");
        agonista.setSelected(false);
        socio.setSelected(false);
        sesso.setValue(null);
        fascia.setValue(null);
        classifica_fit.setValue(null);
    }
}
