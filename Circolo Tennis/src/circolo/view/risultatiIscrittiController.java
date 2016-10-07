package circolo.view;

import circolo.Database;
import circolo.Giocatore;
import circolo.MainApp;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

import java.io.IOException;

public class risultatiIscrittiController {
    @FXML
    private TableView<Giocatore> table;
    @FXML
    private TableColumn<Giocatore, String> nome;
    @FXML
    private TableColumn<Giocatore, String> cognome;
    @FXML
    private TableColumn<Giocatore, String> data_nascita;
    @FXML
    private TableColumn<Giocatore, String> CF;
    @FXML
    private TableColumn<Giocatore, String> sesso;
    @FXML
    private TableColumn<Giocatore,String> città;
    @FXML
    private TableColumn<Giocatore, String> indirizzo;
    @FXML
    private TableColumn<Giocatore, String> classifica_fit;
    @FXML
    private TableColumn<Giocatore, Integer> fascia;
    @FXML
    private TableColumn<Giocatore, String> agonista;
    @FXML
    private TableColumn<Giocatore, String> socio;

    @FXML
    private Button modifica;

    @FXML
    private BorderPane pane;

    private AnchorPane modificaPane;

    private boolean modificaClicked = false;

    private Giocatore oldGiocatore = new Giocatore();
    private Database db;
    private modificaIscrittoController controller;

    public risultatiIscrittiController() {
    }

    @FXML
    private void initialize() {
        nome.setCellValueFactory(cellData -> cellData.getValue().getNomeProperty());
        cognome.setCellValueFactory(cellData -> cellData.getValue().getCognomeProperty());
        data_nascita.setCellValueFactory(cellData -> cellData.getValue().getData_NascitaProperty());
        CF.setCellValueFactory(cellData -> cellData.getValue().getCFProperty());
        sesso.setCellValueFactory(cellData -> cellData.getValue().getGenereProperty());
        città.setCellValueFactory(cellData -> cellData.getValue().getCittaProperty());
        indirizzo.setCellValueFactory(cellData -> cellData.getValue().getIndirizzoProperty());
        classifica_fit.setCellValueFactory(cellData -> cellData.getValue().getClassifica_FITProperty());
        agonista.setCellValueFactory(cellData -> cellData.getValue().getAgonista() == 1 ? new SimpleStringProperty("Sì") : new SimpleStringProperty("No"));
        fascia.setCellValueFactory(cellData -> cellData.getValue().getFasciaProperty().asObject());
        socio.setCellValueFactory(cellData -> cellData.getValue().getSocio() == 1 ? new SimpleStringProperty("Sì") : new SimpleStringProperty("No"));

        table.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> Selezione(newValue));
    }

    public void inserisciRisultatiTabella(ObservableList<Giocatore> lista) {
        table.setItems(lista);
    }

    private void Selezione(Giocatore giocatore) {
        this.oldGiocatore = giocatore;
        modifica.setDisable(false);
        if (modificaClicked) {
            controller.setParametri(giocatore);
        }
    }

    @FXML
    private void handleModifica() {
        if (!modificaClicked) {
            loadModificaPane();
        } else pane.setBottom(modificaPane);
    }

    private void loadModificaPane() {
        ButtonBar defaultPane = (ButtonBar) pane.getBottom();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(MainApp.class.getResource("view/modificaIscritto.fxml"));
        try {
            modificaPane = loader.load();
            controller = loader.getController();
            controller.setParametri(oldGiocatore);
            controller.setControlli(db, pane, defaultPane, table);
            modificaClicked = true;
            pane.setBottom(modificaPane);
        } catch (IOException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Errore");
            alert.setHeaderText("Si è verificato un problema interno");
            alert.showAndWait();
        }
    }

    public void setBorderPane(BorderPane pane) {
        this.pane = pane;
    }

    public void setDatabase(Database db) {
        this.db = db;
    }
}