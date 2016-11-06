package circolo.view;

import circolo.Campo;
import circolo.Database;
import circolo.MainApp;
import circolo.Prenotazione;
import circolo.util.DateUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Classe che gestisce la prenotazione di un campo per l'utente ospite
 */
public class prenotazioneCampoController {
    @FXML
    private DatePicker giorno;
    @FXML
    private TextField oraInizio;
    @FXML
    private TextField oraFine;
    @FXML
    private ChoiceBox<String> superficieBox;

    private Database db;


    @FXML
    private void initialize() {

        superficieBox.getItems().addAll("","Cemento", "Erba", "Terra");

        try{
            db = Database.getInstance();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * @see gestionePrenotazioniController#handleVerificaDisponibilità()
     * @throws SQLException
     */
    @FXML
    private void handleVerificaDisponibilità() throws SQLException {
       /* Prenotazione prenotazione = new Prenotazione();
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
        }*/

        Prenotazione prenotazione = new Prenotazione();
        ObservableList<Campo> listaCampi = FXCollections.observableArrayList();
        if (isInputValid()) {
            prenotazione.setData(LocalDate.parse(giorno.getValue().toString()));
            prenotazione.setInizio(LocalTime.parse(oraInizio.getText()));
            prenotazione.setFine(LocalTime.parse(oraFine.getText()));
            if (superficieBox.getValue() != null) prenotazione.getcampo().setSuperficie(superficieBox.getValue());
            else prenotazione.getcampo().setSuperficie("");
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
     * @see gestionePrenotazioniController#handleAnnulla()
     */
    @FXML
    private void handleAnnulla(){
        giorno.setValue(null);
        oraInizio.setText("");
        oraFine.setText("");
        superficieBox.setValue(null);
    }


    /**
     * @see gestionePrenotazioniController#isInputValid()
     */
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


    /**
     * @see gestionePrenotazioniController#
     */


}