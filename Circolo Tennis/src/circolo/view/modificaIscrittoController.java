package circolo.view;

import circolo.Database;
import circolo.Giocatore;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;

import java.sql.SQLException;

/**
 * Classe che controlla il pannello modifica della gestione Iscritti
 * @see gestioneIscrittiController#modificaPane
 */
public class modificaIscrittoController {
    @FXML
    private TextField newIndirizzo;
    @FXML
    private TextField newCittà;
    @FXML
    private CheckBox newAgonista;
    @FXML
    private CheckBox newSocio;
    @FXML
    private ChoiceBox<String> newClassificaFIT;
    @FXML
    private ChoiceBox<Integer> newFascia;

    private Database db;
    private BorderPane controlPane;
    private ButtonBar defaultPane;
    private TableView<Giocatore> table;
    /**
     * Giocatore selezionato di cui si vuole modificare i dati
     */
    private Giocatore oldGiocatore;


    @FXML
    private void initialize(){
        newFascia.getItems().addAll(1, 2, 3, 4, 5);
        newClassificaFIT.getItems().addAll("4.NC", "4.5", "4.4", "4.3", "4.2", "4.1", "4.0",
                "3.9", "3.8", "3.7", "3.6", "3.5", "3.4", "3.3", "3.2", "3.1", "3.0",
                "2.9", "2.8", "2.7", "2.6", "2.5", "2.4", "2.3", "2.2", "2.1", "2.0",
                "1.9", "1.8", "1.7", "1.6", "1.5", "1.4", "1.3", "1.2", "1.1");
        try {
            db = Database.getInstance();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Cambia i dati del giocatore
     */
    @FXML
    private void handleConferma() {
        Giocatore newGiocatore = oldGiocatore;
        newGiocatore.setCitta(newCittà.getText());
        newGiocatore.setIndirizzo(newIndirizzo.getText());
        newGiocatore.setSocio(newSocio.isSelected() ? 1 : 0);
        newGiocatore.setAgonista(newAgonista.isSelected() ? 1 : 0);
        newGiocatore.setFascia(newFascia.getValue() == null ? 0 : newFascia.getValue());
        newGiocatore.setClassifica_FIT(newClassificaFIT.getValue());
        newGiocatore.setCF(oldGiocatore.getCF());
        try {
            db.modificaIscritto(newGiocatore);
            table.refresh();
        } catch (SQLException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Errore");
            alert.setHeaderText("Si è verificato un problema interno");
            alert.showAndWait();
        }finally {
            controlPane.setBottom(defaultPane);
        }

    }

    /**
     * Metodo che inizializza il giocatore da modificare con i suoi dati
     * @param giocatore giocatore che si vuole modificare
     */
    public void setParametri(Giocatore giocatore){
        if(giocatore != null) {
            newIndirizzo.setText(giocatore.getIndirizzo());
            newCittà.setText(giocatore.getCitta());
            newSocio.setSelected(giocatore.getSocio() == 1);
            newAgonista.setSelected(giocatore.getAgonista() == 1);
            newFascia.setValue(giocatore.getFascia() == 0 ? null : giocatore.getFascia());
            newClassificaFIT.setValue(giocatore.getClassifica_FIT());
            this.oldGiocatore = giocatore;
        }
    }

    /**
     * Metodo che prende i controlli dell'interfaccia di gestione Iscritti
     * @param pane pannello della gestione iscritti
     * @param defaultPane button bar di default
     * @param table tabella giocatori
     */
    public void setControlli(BorderPane pane,ButtonBar defaultPane,TableView<Giocatore> table){
        this.controlPane = pane;
        this.defaultPane = defaultPane;
        this.table = table;
    }
}