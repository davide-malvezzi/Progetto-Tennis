package circolo.view;

import circolo.Database;
import circolo.Giocatore;
import circolo.util.AlertUtil;
import circolo.util.DateUtil;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.ObservableList;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;

import java.sql.SQLException;
import java.time.LocalDate;

/**
 * Classe che gestisce l'interfaccia del pannello Matchplay sezione Iscriizoni
 */
public class GestioneMatchplay_IscrizioniController {

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
    @FXML
    private Pane resultsPane;
    /**
     * tabella che contiene il nome dei giocatori trovati
     */
    @FXML
    private TableView<Giocatore> resultsTable;
    /**
     * colonna di {@link GestioneMatchplay_IscrizioniController#resultsTable} che contiene il nome del giocatore
     */
    @FXML
    private TableColumn<Giocatore, String> nomeResultsTable;
    /**
     * colonna di {@link GestioneMatchplay_IscrizioniController#resultsTable} che contiene il cognome del giocatore
     */
    @FXML
    private TableColumn<Giocatore, String> cognomeResultsTable;
    /**
     * colonna di {@link GestioneMatchplay_IscrizioniController#resultsTable} che contiene il codice fiscale del giocatore
     */
    @FXML
    private TableColumn<Giocatore, String> cfResultsTable;
    /**
     * colonna di {@link GestioneMatchplay_IscrizioniController#resultsTable} che contiene la fascia del giocatore
     */
    @FXML
    private TableColumn<Giocatore, Integer> fasciaResultsTable;
    @FXML
    private Button iscrivi;
    @FXML
    private TableView<Giocatore> tableIscritti;
    @FXML
    private TableColumn<Giocatore, String> nomeIscritto;
    @FXML
    private TableColumn<Giocatore, String> cognomeIscritto;
    @FXML
    private TableColumn<Giocatore, Integer> fasciaIscritto;
    @FXML
    private TableColumn<Giocatore, Integer> edizioneIscritto;
    @FXML
    private Button elimina;
    @FXML
    private Label nomeDetail;
    @FXML
    private Label cognomeDetail;
    @FXML
    private Label dataDetail;
    @FXML
    private Label cfDetail;
    @FXML
    private Label cittàDetail;
    @FXML
    private Label indirizzoDetail;
    @FXML
    private Label classificafitDetail;
    @FXML
    private Label fasciaDetail;
    @FXML
    private Label agonistaDetail;
    @FXML
    private Label socioDetail;
    private ObservableList<Giocatore> listaIscritti;
    private Database db;
    private Giocatore giocatore = new Giocatore();
    private boolean eliminaOFF = false;

    @FXML
    private void initialize() {

        try {
            db = Database.getInstance();
            if(db.checkGironi()) eliminaOFF = true;
            listaIscritti = db.ListaPartecipanti();

            nomeIscritto.setCellValueFactory(cellData -> cellData.getValue().getNomeProperty());
            cognomeIscritto.setCellValueFactory(cellData -> cellData.getValue().getCognomeProperty());
            fasciaIscritto.setCellValueFactory(cellData -> cellData.getValue().getFasciaProperty().asObject());
            edizioneIscritto.setCellValueFactory(cellData -> new SimpleIntegerProperty(LocalDate.now().getYear()).asObject());

            tableIscritti.setItems(listaIscritti);
            tableIscritti.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                this.giocatore = newValue;
                if(!eliminaOFF) elimina.setDisable(false);
                nomeDetail.setText(giocatore.getNome());
                cognomeDetail.setText(giocatore.getCognome());
                dataDetail.setText(DateUtil.format(giocatore.getData_nascita()));
                cfDetail.setText(giocatore.getCF());
                cittàDetail.setText(giocatore.getCitta());
                indirizzoDetail.setText(giocatore.getIndirizzo());
                classificafitDetail.setText(giocatore.getClassifica_FIT());
                agonistaDetail.setText(giocatore.getAgonista() == 1 ? "Sì" : "No");
                fasciaDetail.setText(String.valueOf(giocatore.getFascia()));
                socioDetail.setText(giocatore.getSocio() == 1 ? "Sì" : "No");

            });
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

        nomeResultsTable.setCellValueFactory(cellData -> cellData.getValue().getNomeProperty());
        cognomeResultsTable.setCellValueFactory(cellData -> cellData.getValue().getCognomeProperty());
        cfResultsTable.setCellValueFactory(cellData -> cellData.getValue().getCFProperty());
        fasciaResultsTable.setCellValueFactory(cellData -> cellData.getValue().getFasciaProperty().asObject());

        resultsTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
        this.giocatore = newValue;
        iscrivi.setDisable(false);
    });
}


    /**
     * metodo che cerca i giocatori nel database
     */
    @FXML
    private void handleCerca() {
        Giocatore ricerca = new Giocatore();
        ObservableList<Giocatore> lista;
        if (nome.getText().length() > 0) ricerca.setNome(nome.getText());
        else ricerca.setNome("");
        if (cognome.getText().length() > 0) ricerca.setCognome(cognome.getText());
        else ricerca.setCognome("");
        try {
            lista = db.ricercaGiocatore(ricerca, 0);
            resultsTable.setItems(lista);
            resultsPane.setVisible(true);
        } catch (SQLException e) {
            e.printStackTrace();
            AlertUtil.displayGenericError();
        }

    }

    /**
     * metodo che iscrive il giocatore selezionato al Matchplay
     */
    @FXML
    private void handleIscrivi() {
        try {
            if (db.checkPartite()) {
                AlertUtil.displayPersonalizedInfo("Impossibile iscrivere nuovo giocatore", "Le partite sono già state formate");
                resultsPane.setVisible(false);
                iscrivi.setDisable(true);
                resultsTable.getItems().clear();
                nome.setText("");
                cognome.setText("");
            } else {
                if (db.InserisciPartecipante_MatchPlay(giocatore)) {
                    AlertUtil.displayPersonalizedInfo("Operazione Eseguita", "Giocatore iscritto");
                    listaIscritti.add(giocatore);
                    resultsPane.setVisible(false);
                    iscrivi.setDisable(true);
                    resultsTable.getItems().clear();
                    nome.setText("");
                    cognome.setText("");
                } else AlertUtil.displayPersonalizedError("Giocatore già iscritto", null);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * rimuove il giocatore selezionato dal matchplay
     */
    @FXML
    private void handleElimina() {
        boolean conferma = AlertUtil.displayConfirmationDialog("Cancella Iscrizione", "Rimuovere l'iscrizione al Matchplay di " + giocatore.getNome() + " " + giocatore.getCognome() + "?");
        if (conferma) {
            try {
                db.rimuoviPartecipante(giocatore);
                listaIscritti.remove(listaIscritti.indexOf(giocatore));
            } catch (SQLException e) {
                e.printStackTrace();
                AlertUtil.displayGenericError();
            }
        }
    }

    /**
     * metodo che forma i gironi
     */
    @FXML
    private void handleFormaGironi() {
        try {
            if (db.checkGironi())
                AlertUtil.displayPersonalizedInfo("Gironi già formati", null);
            else {
                Service backgroundThread = new Service() {
                    @Override
                    protected Task createTask() {
                        return new Task() {
                            @Override
                            protected Object call() throws Exception {
                                db.generaGironiMatchPlay();
                                return null;
                            }
                        };
                    }
                };
                backgroundThread.restart();
                backgroundThread.setOnSucceeded(event -> AlertUtil.displayPersonalizedInfo("Gironi Formati", null));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            AlertUtil.displayGenericError();
        }
    }






}