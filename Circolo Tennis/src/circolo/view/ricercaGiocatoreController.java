package circolo.view;

import circolo.Database;
import circolo.Giocatore;
import circolo.MainApp;
import circolo.util.AlertUtil;
import circolo.util.DateUtil;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.sql.SQLException;

public class ricercaGiocatoreController {
    @FXML
    private TextField nome;
    @FXML
    private TextField cognome;
    @FXML
    private TextField data;
    @FXML
    private TextField città;
    @FXML
    private TextField indirizzo;
    @FXML
    private ComboBox<String> sesso;
    @FXML
    private TextField cf;
    @FXML
    private ComboBox<String> classifica_fit;
    @FXML
    private CheckBox socio;
    @FXML
    private CheckBox agonista;
    @FXML
    private ComboBox<String> fascia;

    private MainApp mainApp;

    private Database db;

    public ricercaGiocatoreController() {
    }


    @FXML
    private void initialize() {
        fascia.getItems().addAll("1", "2", "3", "4", "5");
        classifica_fit.getItems().addAll("4.NC", "4.5", "4.4", "4.3", "4.2", "4.1", "4.0",
                "3.9", "3.8", "3.7", "3.6", "3.5", "3.4", "3.3", "3.2", "3.1", "3.0",
                "2.9", "2.8", "2.7", "2.6", "2.5", "2.4", "2.3", "2.2", "2.1", "2.0",
                "1.9", "1.8", "1.7", "1.6", "1.5", "1.4", "1.3", "1.2", "1.1");
        sesso.getItems().addAll("M", "F");
    }

    @FXML
    private void handleCerca() {
        if (isInputValid()) {
            Giocatore giocatore = new Giocatore();
            giocatore.setNome(nome.getText());
            giocatore.setCognome(cognome.getText());
            giocatore.setData_nascita(DateUtil.parse(data.getText()));
            giocatore.setCF(cf.getText());
            giocatore.setCitta(città.getText());
            giocatore.setIndirizzo(indirizzo.getText());
            giocatore.setAgonista(agonista.isSelected() ? 1 : 0);
            giocatore.setSocio(socio.isSelected() ? 1 : 0);
            if(fascia.getValue() != null)
                giocatore.setFascia(Integer.parseInt(fascia.getValue()));
            else giocatore.setFascia(0);
            giocatore.setClassifica_FIT(classifica_fit.getValue());
            giocatore.setGenere(sesso.getValue());
            ObservableList<Giocatore> lista;
            try {
                lista = db.ricercaGiocatore(giocatore);
                mainApp.showrisultatiIscritti(lista);
            } catch (SQLException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Errore");
                alert.setHeaderText("Problema nella ricerca del giocatore nel Database");
                alert.showAndWait();
            }

        }
    }

    @FXML
    private void handleAnnulla() {
        clearFields();
    }

    private boolean isInputValid() {
        int parametri = 0;
        boolean dateError = false;
        if (nome.getText() != null && nome.getText().length() > 0) {
            parametri++;
        }
        if (cognome.getText() != null && cognome.getText().length() > 0) {
            parametri++;
        }

        if (cf.getText() != null && cf.getText().length() > 0) {
            parametri++;
        }
        if (sesso.getValue() != null) {
            parametri++;
        }
        if (città.getText() != null && città.getText().length() > 0) {
            parametri++;
        }
        if (indirizzo.getText() != null && indirizzo.getText().length() > 0) {
            parametri++;
        }
        if (agonista.isSelected()) {
            parametri++;
        }
        if (socio.isSelected()) {
            parametri++;
        }
        if (fascia.getValue() != null) {
            parametri++;
        }
        if (data.getText() != null && data.getText().length() > 0) {
            parametri++;
            if (!DateUtil.validDate(data.getText())) {
                dateError = true;
            }
        }
        if (classifica_fit.getValue() != null) {
            parametri++;
        }
        if (parametri > 0 && !dateError) {
            return true;
        } else {
            String errorMessage="";
            if (dateError) {
                errorMessage = "Data di nascita non valida. Usa il formato gg-mm-aaaa";
            }
            if (parametri == 0) {
                errorMessage = "Devi inserire almeno un parametro";
            }
            AlertUtil.displayPersonalizedError("Campi non validi",errorMessage);
            return false;
        }
    }


    private void clearFields() {
        nome.setText("");
        cognome.setText("");
        data.setText("");
        cf.setText("");
        città.setText("");
        indirizzo.setText("");
        agonista.setSelected(false);
        socio.setSelected(false);
        sesso.setValue(null);
        fascia.setValue(null);
        classifica_fit.setValue(null);
    }

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }

    public void setDatabase(Database db) {
        this.db = db;
    }

}
