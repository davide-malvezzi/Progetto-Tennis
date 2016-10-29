package circolo.view;

import circolo.Database;
import circolo.Giocatore;
import circolo.VisitaMedica;
import circolo.util.AlertUtil;
import circolo.util.DateUtil;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;

import java.sql.SQLException;

public class newVisitaMedicaController {
    @FXML
    private DatePicker data;
    @FXML
    private CheckBox agonistica;

    private Database db;
    private Giocatore giocatore = new Giocatore();
    private BorderPane controlPane;
    private ButtonBar defaultPane;

    @FXML
    private void initialize() {
        try {
            db = Database.getInstance();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            AlertUtil.displayGenericError();
        }
    }

    @FXML
    private void handleOK() {
        VisitaMedica visita = new VisitaMedica();
        try {
            if (isInputValid()) {
                visita.setData(data.getValue());
                visita.setCF_paziente(giocatore.getCF());
                visita.setAgonistica(agonistica.isSelected() ? 1 : 0);
                db.InserisciVisita(visita);
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Messaggio");
                alert.setHeaderText("Nuova visita medica inserita");
                alert.setContentText("Riepilogo Dati: \n\nData: " + DateUtil.format(visita.getData()) + "\n" +
                        "Codice Fiscale: " + visita.getCF_paziente() + "\n" +
                        "Tipo: " + (visita.getAgonistica() == 1 ? "Agonistica" : "Non agonistica"));

                alert.showAndWait();
                controlPane.setBottom(defaultPane);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            AlertUtil.displayGenericError();
        }


    }

    @FXML
    private void handleAnnulla(){
        controlPane.setBottom(defaultPane);
    }

    private boolean isInputValid() {
        String errorMessage = "";
        if (data.getValue() == null)
            errorMessage += "Data non può essere vuota\n";
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
        agonistica.setSelected(false);
    }

    public void setParametri(Giocatore giocatore){
        this.giocatore = giocatore;
    }

    public void setControlli(BorderPane pane,ButtonBar defaultPane){
        this.controlPane = pane;
        this.defaultPane = defaultPane;
    }

}
