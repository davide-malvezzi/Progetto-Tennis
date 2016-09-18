package circolo.view;

import circolo.Database;
import circolo.VisitaMedica;
import circolo.util.DateUtil;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

import java.sql.SQLException;

public class newVisitaMedicaController {
    @FXML
    private DatePicker data;
    @FXML
    private TextField cf;
    @FXML
    private CheckBox agonistica;

    private Database db;

    @FXML
    private void initialize() {
    }

    @FXML
    private void handleOK() throws SQLException {
        VisitaMedica visita = new VisitaMedica();
        if (isInputValid()) {
            visita.setData(data.getValue());
            visita.setCF_paziente(cf.getText());
            visita.setAgonistica(agonistica.isSelected() ? 1 : 0);
            db.InserisciVisita(visita);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Messaggio");
            alert.setHeaderText("Nuova visita medica inserita\n" +
                    "Riepilogo Dati:");
            alert.setContentText("Data: " + DateUtil.format(visita.getData()) + "\n" +
                    "Codice Fiscale: " + visita.getCF_paziente() + "\n" +
                    "Tipo: " + (visita.getAgonistica() == 1 ? "Agonistica" : "Non agonistica"));

            alert.showAndWait();

            clearFields();
        }


    }

    @FXML
    private void handleAnnulla(){
        clearFields();
    }

    private boolean isInputValid() {
        String errorMessage = "";
        if (data.getValue() == null)
            errorMessage += "Data non può essere vuota\n";
        if (cf.getText() == null || cf.getText().length() == 0)
            errorMessage += "Codice Fiscale non può essere vuoto\n";
        if (errorMessage.length() == 0)
            return true;
        else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Errore");
            alert.setHeaderText("Campo non valido");
            alert.setContentText(errorMessage);

            alert.showAndWait();

            return false;
        }

    }

    private void clearFields(){
        data.setValue(null);
        cf.setText(null);
        agonistica.setSelected(false);
    }

    public void setDatabase(Database db){
        this.db = db;
    }
}
