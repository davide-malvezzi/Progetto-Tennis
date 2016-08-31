package circolo.view;

import circolo.Database;
import circolo.Giocatore;
import circolo.MainApp;
import circolo.util.DateUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class ricercaGiocatoreController {
    @FXML
    private TextField nome;
    @FXML
    private TextField cognome;
    @FXML
    private TextField data;
    @FXML
    private TextField indirizzo;
    @FXML
    private ComboBox sesso;
    @FXML
    private TextField cf;
    @FXML
    private ComboBox classifica_fit;
    @FXML
    private CheckBox socio;
    @FXML
    private CheckBox agonista;
    @FXML
    private ComboBox fascia;

    private Giocatore giocatore = new Giocatore();
    private MainApp mainApp;

    public ricercaGiocatoreController() {
    }

    @FXML
    private void initialize() {
        fascia.getItems().addAll("1", "2", "3", "4", "5");
        classifica_fit.getItems().addAll("4.NC", 4.5, 4.4, 4.3, 4.2, 4.1, 4.0,
                3.9, 3.8, 3.7, 3.6, 3.5, 3.4, 3.3, 3.2, 3.1, 3.0,
                2.9, 2.8, 2.7, 2.6, 2.5, 2.4, 2.3, 2.2, 2.1, 2.0,
                1.9, 1.8, 1.7, 1.6, 1.5, 1.4, 1.3, 1.2, 1.1);
        sesso.getItems().addAll("M", "F");
    }

    @FXML
    private void handleCerca() throws SQLException {
        if (isInputValid()) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-uuuu");
            giocatore.setNome(nome.getText());
            giocatore.setCognome(cognome.getText());
            if (giocatore.getData_nascita() != null)
                giocatore.setData_nascita(LocalDate.parse(data.getText(), formatter));
            giocatore.setCF(cf.getText());
            if (indirizzo.getText().length() == 0)
                giocatore.setIndirizzo(null);
            else giocatore.setIndirizzo(indirizzo.getText());
            giocatore.setAgonista(agonista.isSelected() ? 1 : 0);
            giocatore.setSocio(socio.isSelected() ? 1 : 0);
            if (fascia.getValue() != null)
                giocatore.setFascia(Integer.parseInt((String) fascia.getValue()));
            if (classifica_fit.getValue() != null)
                giocatore.setClassifica_FIT(classifica_fit.getValue().toString());
            giocatore.setGenere((String) sesso.getValue());
            ObservableList<Giocatore> lista = FXCollections.observableArrayList();
            Database db = new Database();
            lista = db.ricercaGiocatore(giocatore);
            mainApp.showSearchResults(lista);
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
        if ( classifica_fit.getValue() != null){
            parametri++;
        }
        if (parametri > 0 && !dateError) {
            return true;
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Errore");
            if (dateError) {
                alert.setContentText("Data di nascita non valida. Usa il formato gg-mm-aaaa");
            }
            if (parametri == 0) {
                alert.setContentText("Devi inserire almeno un parametro");
            }
            alert.showAndWait();

            return false;
        }
    }


    private void clearFields() {
        nome.setText("");
        cognome.setText("");
        data.setText("");
        cf.setText("");
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

}
