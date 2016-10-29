package circolo.view;

import circolo.Database;
import circolo.Giocatore;
import circolo.MainApp;
import circolo.util.AlertUtil;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class gestioneIscrittiController {
    @FXML
    private BorderPane pane;
    @FXML
    private Button avanti;
    @FXML
    private Button indietro;
    @FXML
    private TableView<Giocatore> table;
    @FXML
    private TableColumn<Giocatore, String> nomeCol;
    @FXML
    private TableColumn<Giocatore, String> cognomeCol;
    @FXML
    private TableColumn<Giocatore, String> data_nascitaCol;
    @FXML
    private TableColumn<Giocatore, String> CFCol;
    @FXML
    private TableColumn<Giocatore, String> sessoCol;
    @FXML
    private TableColumn<Giocatore, String> cittàCol;
    @FXML
    private TableColumn<Giocatore, String> indirizzoCol;
    @FXML
    private TableColumn<Giocatore, String> classifica_fitCol;
    @FXML
    private TableColumn<Giocatore, Integer> fasciaCol;
    @FXML
    private TableColumn<Giocatore, String> agonistaCol;
    @FXML
    private TableColumn<Giocatore, String> socioCol;
    @FXML
    private Button modifica;
    @FXML
    private Button elimina;
    @FXML
    private Button visitaMedica;
    @FXML
    private TextField nome;
    @FXML
    private TextField cognome;
    @FXML
    private TextField città;
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
    @FXML
    private Button nuovoButton;

    private Database db;
    private ObservableList<Giocatore> lista;
    private AnchorPane modificaPane;
    private AnchorPane visitaPane;
    private int offset = 0;
    private boolean cercaClick = false;
    private int offsetCerca = 0;
    Giocatore giocatore = new Giocatore();
    ButtonBar defaultPane;

    private boolean modificaClicked = false;
    private boolean visitaClicked = false;

    private Giocatore selezionato = new Giocatore();
    private modificaIscrittoController controllerModifica;
    private newVisitaMedicaController controllerVisita;

    @FXML
    private void initialize() {
        fascia.getItems().addAll("1", "2", "3", "4", "5");
        classifica_fit.getItems().addAll("4.NC", "4.5", "4.4", "4.3", "4.2", "4.1", "4.0",
                "3.9", "3.8", "3.7", "3.6", "3.5", "3.4", "3.3", "3.2", "3.1", "3.0",
                "2.9", "2.8", "2.7", "2.6", "2.5", "2.4", "2.3", "2.2", "2.1", "2.0",
                "1.9", "1.8", "1.7", "1.6", "1.5", "1.4", "1.3", "1.2", "1.1");
        sesso.getItems().addAll("M", "F");
        defaultPane = (ButtonBar) pane.getBottom();
        try {
            db = Database.getInstance();
            lista = db.loadIscritti(offset);
            indietro.setDisable(true);
            ButtonBar.setButtonData(avanti, ButtonBar.ButtonData.LEFT);
            ButtonBar.setButtonData(indietro, ButtonBar.ButtonData.LEFT);
            nomeCol.setCellValueFactory(cellData -> cellData.getValue().getNomeProperty());
            cognomeCol.setCellValueFactory(cellData -> cellData.getValue().getCognomeProperty());
            data_nascitaCol.setCellValueFactory(cellData -> cellData.getValue().getData_NascitaProperty());
            CFCol.setCellValueFactory(cellData -> cellData.getValue().getCFProperty());
            sessoCol.setCellValueFactory(cellData -> cellData.getValue().getGenereProperty());
            cittàCol.setCellValueFactory(cellData -> cellData.getValue().getCittaProperty());
            indirizzoCol.setCellValueFactory(cellData -> cellData.getValue().getIndirizzoProperty());
            classifica_fitCol.setCellValueFactory(cellData -> cellData.getValue().getClassifica_FITProperty());
            agonistaCol.setCellValueFactory(cellData -> cellData.getValue().getAgonista() == 1 ? new SimpleStringProperty("Sì") : new SimpleStringProperty("No"));
            fasciaCol.setCellValueFactory(cellData -> cellData.getValue().getFasciaProperty().asObject());
            socioCol.setCellValueFactory(cellData -> cellData.getValue().getSocio() == 1 ? new SimpleStringProperty("Sì") : new SimpleStringProperty("No"));

            table.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> Selezione(newValue));
            table.setItems(lista);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            AlertUtil.displayGenericError();
        }


    }

    private void Selezione(Giocatore giocatore) {
        this.selezionato = giocatore;
        if(giocatore == null){
            modifica.setDisable(true);
            elimina.setDisable(true);
            visitaMedica.setDisable(true);
        }else {
            modifica.setDisable(false);
            elimina.setDisable(false);
            visitaMedica.setDisable(false);
        }
        if (modificaClicked) {
            controllerModifica.setParametri(giocatore);
        }
        if(visitaClicked){
            controllerVisita.setParametri(giocatore);
        }
    }

    @FXML
    private void handleAvanti() {
        Selezione(null);
        indietro.setDisable(false);
        try {
            if (cercaClick){
                offsetCerca += 10;
                lista = db.ricercaGiocatore(giocatore,offsetCerca);
                table.setItems(lista);
                if (lista.size() < 10) avanti.setDisable(true);
            }
            else{
                offset += 10;
                lista = db.loadIscritti(offset);
                table.setItems(lista);
                if (lista.size() < 10) avanti.setDisable(true);
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    @FXML
    private void handleIndietro() {
        Selezione(null);
        try {
            if (cercaClick){
                offsetCerca -= 10;
                if(offsetCerca == 0) indietro.setDisable(true);
                lista = db.ricercaGiocatore(giocatore,offsetCerca);
                table.setItems(lista);
                avanti.setDisable(false);
            }
            else {
                offset -= 10;
                if (offset == 0) indietro.setDisable(true);
                lista = db.loadIscritti(offset);
                table.setItems(lista);
                avanti.setDisable(false);
                if (offset == 0) indietro.setDisable(true);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleModifica() {
        if (!modificaClicked) {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/modificaIscritto.fxml"));
            try {
                modificaPane = loader.load();
                controllerModifica = loader.getController();
                controllerModifica.setParametri(selezionato);
                controllerModifica.setControlli(pane, defaultPane, table);
                modificaClicked = true;
                pane.setBottom(modificaPane);
            } catch (IOException e) {
                e.printStackTrace();
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Errore");
                alert.setHeaderText("Si è verificato un problema interno");
                alert.showAndWait();
            }
        } else pane.setBottom(modificaPane);
    }

    @FXML
    private void handleVisita(){
        if (!visitaClicked) {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/nuovaVisitaMedica.fxml"));
            try {
                visitaPane = loader.load();
                controllerVisita = loader.getController();
                controllerVisita.setParametri(selezionato);
                controllerVisita.setControlli(pane, defaultPane);
                visitaClicked = true;
                pane.setBottom(visitaPane);
            } catch (IOException e) {
                e.printStackTrace();
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Errore");
                alert.setHeaderText("Si è verificato un problema interno");
                alert.showAndWait();
            }
        } else pane.setBottom(visitaPane);
    }


    @FXML
    private void handleCerca() {
        if (isInputValid()) {
            cercaClick = true;
            giocatore.setNome(nome.getText());
            giocatore.setCognome(cognome.getText());
            giocatore.setCF(cf.getText());
            giocatore.setCitta(città.getText());
            giocatore.setAgonista(agonista.isSelected() ? 1 : 0);
            giocatore.setSocio(socio.isSelected() ? 1 : 0);
            if (fascia.getValue() != null)
                giocatore.setFascia(Integer.parseInt(fascia.getValue()));
            else giocatore.setFascia(0);
            giocatore.setClassifica_FIT(classifica_fit.getValue());
            giocatore.setGenere(sesso.getValue());
            try {
                lista = db.ricercaGiocatore(giocatore,offsetCerca);
                table.setItems(lista);
                offsetCerca = 0;
                indietro.setDisable(true);
                if(lista.size() < 10) avanti.setDisable(true);
                else avanti.setDisable(false);
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
        offsetCerca = 0;
        cercaClick = false;
        clearFields();
        if(offset == 0) {
            indietro.setDisable(true);
            avanti.setDisable(false);
        }else{
            indietro.setDisable(false);
            if (lista.size() < 10)
                avanti.setDisable(true);
        }

        try {
            lista = db.loadIscritti(offset);
            table.setItems(lista);
        } catch (SQLException e) {
            e.printStackTrace();
            AlertUtil.displayGenericError();
        }
    }

    @FXML
    private void handleNuovaIscrizione(){
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(MainApp.class.getResource("view/newGiocatore.fxml"));
        try {
            BorderPane pane = loader.load();
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Nuova Iscrizione");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.setResizable(false);
            dialogStage.initOwner(MainApp.getPrimaryStage());
            Scene scene = new Scene(pane);
            dialogStage.setScene(scene);
            dialogStage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
            AlertUtil.displayGenericError();
        }

    }

    @FXML
    private void handleElimina(){
        boolean elimina = AlertUtil.displayConfirmationDialog("Elimina Giocatore", "Vuoi eliminare definitivamente " + selezionato.getNome() + " " + selezionato.getCognome() + "?");
        if(elimina){
            try {
                db.eliminaGiocatore(selezionato);
                lista.remove(lista.indexOf(selezionato));
            } catch (SQLException e) {
                e.printStackTrace();
                AlertUtil.displayGenericError();
            }
        }

    }

    private boolean isInputValid() {
        int parametri = 0;
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
        if (agonista.isSelected()) {
            parametri++;
        }
        if (socio.isSelected()) {
            parametri++;
        }
        if (fascia.getValue() != null) {
            parametri++;
        }
        if (classifica_fit.getValue() != null) {
            parametri++;
        }
        if (parametri > 0) {
            return true;
        } else {
            String errorMessage = "";
            if (parametri == 0) {
                errorMessage = "Devi inserire almeno un parametro";
            }
            AlertUtil.displayPersonalizedError("Campi non validi", errorMessage);
            return false;
        }
    }


    private void clearFields() {
        nome.setText("");
        cognome.setText("");
        cf.setText("");
        città.setText("");
        agonista.setSelected(false);
        socio.setSelected(false);
        sesso.setValue(null);
        fascia.setValue(null);
        classifica_fit.setValue(null);
    }
}