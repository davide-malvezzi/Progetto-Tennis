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

/**
 * Classe che controlla l'interfaccia del tab Gestione Iscritti
 */
public class gestioneIscrittiController {
    /**
     * Pannello principale
     */
    @FXML
    private BorderPane pane;
    /**
     * bottone che fa scorrere la lista degli iscritti in avanti
     */
    @FXML
    private Button avanti;
    /**
     * bottone che fa scorrere la lista degli iscritti all'indietro
     */
    @FXML
    private Button indietro;
    /**
     * Tabella che contiene gli iscritti
     */
    @FXML
    private TableView<Giocatore> table;
    /**
     * colonna di {@link gestioneIscrittiController#table} che contiene il nome del giocatore
     */
    @FXML
    private TableColumn<Giocatore, String> nomeCol;
    /**
     * colonna di {@link gestioneIscrittiController#table} che contiene il cognome del giocatore
     */
    @FXML
    private TableColumn<Giocatore, String> cognomeCol;
    /**
     * colonna di {@link gestioneIscrittiController#table} che contiene la data di nascita del giocatore
     */
    @FXML
    private TableColumn<Giocatore, String> data_nascitaCol;
    /**
     * colonna di {@link gestioneIscrittiController#table} che contiene il codice fiscale del giocatore
     */
    @FXML
    private TableColumn<Giocatore, String> CFCol;
    /**
     * colonna di {@link gestioneIscrittiController#table} che contiene il sesso del giocatore
     */
    @FXML
    private TableColumn<Giocatore, String> sessoCol;
    /**
     * colonna di {@link gestioneIscrittiController#table} che contiene la città del giocatore
     */
    @FXML
    private TableColumn<Giocatore, String> cittàCol;
    /**
     * colonna di {@link gestioneIscrittiController#table} che contiene l'indirizzo del giocatore
     */
    @FXML
    private TableColumn<Giocatore, String> indirizzoCol;
    /**
     * colonna di {@link gestioneIscrittiController#table} che contiene la classifica FIT del giocatore
     */
    @FXML
    private TableColumn<Giocatore, String> classifica_fitCol;
    /**
     * colonna di {@link gestioneIscrittiController#table} che contiene la fascia del giocatore
     */
    @FXML
    private TableColumn<Giocatore, Integer> fasciaCol;
    /**
     * colonna di {@link gestioneIscrittiController#table} che indica se il giocatore è agonista o no
     */
    @FXML
    private TableColumn<Giocatore, String> agonistaCol;
    /**
     * colonna di {@link gestioneIscrittiController#table} che indica se il giocatore è socio o no
     */
    @FXML
    private TableColumn<Giocatore, String> socioCol;
    /**
     * pulsante che fa apparire il pannello modifica giocatore
     * @see gestioneIscrittiController#modificaPane
     */
    @FXML
    private Button modifica;
    /**
     * pulsante che elimina un giocatore
     */
    @FXML
    private Button elimina;
    /**
     * casella di testo dove inserire il nome del giocatore da cercare
     */
    @FXML
    private TextField nome;
    /**
     * casella di testo dove inserire il cognome del giocatore da cercare
     */
    @FXML
    private TextField cognome;
    /**
     * casella di testo dove inserire la città del giocatore da cercare
     */
    @FXML
    private TextField città;
    /**
     * casella di testo dove inserire il sesso del giocatore da cercare
     */
    @FXML
    private ComboBox<String> sesso;
    /**
     * casella di testo dove inserire il codice fiscale del giocatore da cercare
     */
    @FXML
    private TextField cf;
    /**
     * casella dove scegliere la classifica FIT del giocatore da cercare
     */
    @FXML
    private ComboBox<String> classifica_fit;
    /**
     * casella dove indicare se il giocatore è socio o no
     */
    @FXML
    private CheckBox socio;
    /**
     * casella dove indicare se il giocatore è agonista o no
     */
    @FXML
    private CheckBox agonista;
    /**
     * casella dove scegliere la fascia del giocatore da cercare
     */
    @FXML
    private ComboBox<String> fascia;

    private Database db;
    private ObservableList<Giocatore> lista;
    /**
     * pannello che contiene i comandi per modificare i dati del giocatore
     */
    private AnchorPane modificaPane;
    private int offset = 0;
    private boolean cercaClick = false;
    private int offsetCerca = 0;
    Giocatore giocatore = new Giocatore();
    ButtonBar defaultPane;

    private boolean modificaClicked = false;

    private Giocatore selezionato = new Giocatore();
    private modificaIscrittoController controllerModifica;

    /**
     * Inizializza il pannello gestione iscritti
     */
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
        }else {
            modifica.setDisable(false);
            elimina.setDisable(false);
        }
        if (modificaClicked) {
            controllerModifica.setParametri(giocatore);
        }
    }

    /**
     * metodo che fa avanzare la lista
     */
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

    /**
     * metodo che scorre la lista all'indietro
     */
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

    /**
     * metodo che richiama il pannello modifica giocatore
     */
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


    /**
     * metodo che cerca giocatori
     */
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

    /**
     * metodo che annulla la ricerca
     */
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

    /**
     * metodo che richiama la finestra per registrare un nuovo giocatore
     */
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

    /**
     * metodo che elimina un giocatore
     */
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

    /**
     * Metodo che verifica che i parametri inseriti nelle caselle di ricerca siano accettabili
     * @return <code>true</code> se almeno un parametro è inserito e la data di nascita è nel fomrato corretto, <code>false</code> altrimenti
     */
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


    /**
     * metodo che azzera i campi di ricerca
     */
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