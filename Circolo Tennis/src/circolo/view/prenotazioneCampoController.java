package circolo.view;

import circolo.Campo;
import circolo.Database;
import circolo.MainApp;
import circolo.Prenotazione;
import circolo.util.DateUtil;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;

public class prenotazioneCampoController {
    @FXML
    private DatePicker giorno;
    @FXML
    private TextField oraInizio;
    @FXML
    private TextField oraFine;
    @FXML
    private ChoiceBox<String> superficieBox;

    private MainApp mainApp;
    private Database db;

    public prenotazioneCampoController() throws SQLException {
    }

    @FXML
    private void initialize() {
        superficieBox.getItems().addAll("","Cemento", "Erba", "Terra");
    }

    @FXML
    private void handleVerificaDisponibilità() throws SQLException {
        Prenotazione prenotazione = new Prenotazione();
        if (isInputValid()) {
            prenotazione.setData(LocalDate.parse(giorno.getValue().toString()));
            prenotazione.setInizio(LocalTime.parse(oraInizio.getText()));
            prenotazione.setFine(LocalTime.parse(oraFine.getText()));
            if (superficieBox.getValue() != null) prenotazione.getcampo().setSuperficie(superficieBox.getValue());
            else prenotazione.getcampo().setSuperficie("");
            ObservableList<Campo> lista;
            lista = db.checkDisponibilità(prenotazione);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            if (lista.size() > 0) {
                mainApp.showrisultatiPrenotazioni(lista,prenotazione);
            }else{
                alert.setHeaderText("Non sono stati trovati campi disponibili");
                alert.showAndWait();
            }
        }
    }
    @FXML
    private void handleAnnulla(){
        giorno.setValue(null);
        oraInizio.setText("");
        oraFine.setText("");
        superficieBox.setValue(null);
    }


    private boolean isInputValid() {
        String errorMessage = "";
        boolean orainizioOK = false;
        if (giorno.getValue() == null) {
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



    public void setMainapp(MainApp mainapp) {
        this.mainApp = mainapp;
    }

    public void setDatabase(Database db){
        this.db = db;
    }

}