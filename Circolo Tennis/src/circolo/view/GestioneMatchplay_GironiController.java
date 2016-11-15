package circolo.view;

import circolo.Database;
import circolo.GiocatoreMatchplay;
import circolo.Partita;
import circolo.util.AlertUtil;
import javafx.collections.ObservableList;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.HBox;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Classe che gestisce l'interfaccia del pannello Matchplay sezione Gironi
 */
public class GestioneMatchplay_GironiController {
    /**
     * tabella che contiene il girone A
     */
    @FXML
    private TableView<GiocatoreMatchplay> tableA;
    /**
     * tabella che contiene il girone B
     */
    @FXML
    private TableView<GiocatoreMatchplay> tableB;
    /**
     * tabella che contiene il girone C
     */
    @FXML
    private TableView<GiocatoreMatchplay> tableC;
    /**
     * tabella che contiene il girone D
     */
    @FXML
    private TableView<GiocatoreMatchplay> tableD;
    /**
     * tabella che contiene il nome dei giocatori del girone A
     */
    @FXML
    private TableColumn<GiocatoreMatchplay, String> NomegironeA;
    /**
     * tabella che contiene il cognome dei giocatori del girone A
     */
    @FXML
    private TableColumn<GiocatoreMatchplay, String> CognomegironeA;
    /**
     * tabella che contiene la fascia dei giocatori del girone A
     */
    @FXML
    private TableColumn<GiocatoreMatchplay, Integer> FasciagironeA;
    /**
     * tabella che contiene il nome dei giocatori del girone B
     */
    @FXML
    private TableColumn<GiocatoreMatchplay, String> NomegironeB;
    /**
     * tabella che contiene il cognome dei giocatori del girone B
     */
    @FXML
    private TableColumn<GiocatoreMatchplay, String> CognomegironeB;
    /**
     * tabella che contiene la fascia dei giocatori del girone B
     */
    @FXML
    private TableColumn<GiocatoreMatchplay, Integer> FasciagironeB;
    /**
     * tabella che contiene il nome dei giocatori del girone C
     */
    @FXML
    private TableColumn<GiocatoreMatchplay, String> NomegironeC;
    /**
     * tabella che contiene il cognome dei giocatori del girone C
     */
    @FXML
    private TableColumn<GiocatoreMatchplay, String> CognomegironeC;
    /**
     * tabella che contiene la fascia dei giocatori del girone C
     */
    @FXML
    private TableColumn<GiocatoreMatchplay, Integer> FasciagironeC;
    /**
     * tabella che contiene il nome dei giocatori del girone D
     */
    @FXML
    private TableColumn<GiocatoreMatchplay, String> NomegironeD;
    /**
     * tabella che contiene il cognome dei giocatori del girone D
     */
    @FXML
    private TableColumn<GiocatoreMatchplay, String> CognomegironeD;
    /**
     * tabella che contiene la fascia dei giocatori del girone D
     */
    @FXML
    private TableColumn<GiocatoreMatchplay, Integer> FasciagironeD;
    /**
     * tabella che contiene il nome dei giocatori del girone A
     */
    @FXML
    private Button inserisciA;
    /**
     * tabella che contiene il nome dei giocatori del girone B
     */
    @FXML
    private Button inserisciB;
    /**
     * tabella che contiene il nome dei giocatori del girone C
     */
    @FXML
    private Button inserisciC;
    /**
     * tabella che contiene il nome dei giocatori del girone D
     */
    @FXML
    private Button inserisciD;

    @FXML
    private HBox bottom;

    private Database db;
    private ObservableList<ObservableList<GiocatoreMatchplay>> lista;
    private GiocatoreMatchplay Giocatoreselezionato;
    private boolean alreadyDone = false;


    @FXML
    private void initialize() {
        try {
            db = Database.getInstance();
            lista = db.loadGironi();
            tableA.setItems(lista.get(0));
            tableB.setItems(lista.get(1));
            tableC.setItems(lista.get(2));
            tableD.setItems(lista.get(3));
            if (db.checkPartite()) {
                alreadyDone = true;
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            AlertUtil.displayGenericError();
        }

        NomegironeA.setCellValueFactory(cellData -> cellData.getValue().getNomeProperty());
        CognomegironeA.setCellValueFactory(cellData -> cellData.getValue().getCognomeProperty());
        FasciagironeA.setCellValueFactory(cellData -> cellData.getValue().getFasciaProperty().asObject());
        NomegironeB.setCellValueFactory(cellData -> cellData.getValue().getNomeProperty());
        CognomegironeB.setCellValueFactory(cellData -> cellData.getValue().getCognomeProperty());
        FasciagironeB.setCellValueFactory(cellData -> cellData.getValue().getFasciaProperty().asObject());
        NomegironeC.setCellValueFactory(cellData -> cellData.getValue().getNomeProperty());
        CognomegironeC.setCellValueFactory(cellData -> cellData.getValue().getCognomeProperty());
        FasciagironeC.setCellValueFactory(cellData -> cellData.getValue().getFasciaProperty().asObject());
        NomegironeD.setCellValueFactory(cellData -> cellData.getValue().getNomeProperty());
        CognomegironeD.setCellValueFactory(cellData -> cellData.getValue().getCognomeProperty());
        FasciagironeD.setCellValueFactory(cellData -> cellData.getValue().getFasciaProperty().asObject());

        inserisciA.setOnAction(event -> handleInserisciGirone(1));
        inserisciB.setOnAction(event -> handleInserisciGirone(2));
        inserisciC.setOnAction(event -> handleInserisciGirone(3));
        inserisciD.setOnAction(event -> handleInserisciGirone(4));

        tableA.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            Giocatoreselezionato = newValue;
            if (!alreadyDone) {
                inserisciA.setDisable(true);
                inserisciB.setDisable(false);
                inserisciC.setDisable(false);
                inserisciD.setDisable(false);
            }
        });
        tableB.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            Giocatoreselezionato = newValue;
            if (!alreadyDone) {
                inserisciA.setDisable(false);
                inserisciB.setDisable(true);
                inserisciC.setDisable(false);
                inserisciD.setDisable(false);
            }
        });
        tableC.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            Giocatoreselezionato = newValue;
            if (!alreadyDone) {
                inserisciA.setDisable(false);
                inserisciB.setDisable(false);
                inserisciC.setDisable(true);
                inserisciD.setDisable(false);
            }
        });
        tableD.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            Giocatoreselezionato = newValue;
            if (!alreadyDone) {
                inserisciA.setDisable(false);
                inserisciB.setDisable(false);
                inserisciC.setDisable(false);
                inserisciD.setDisable(true);
            }
        });

    }

    /**
     * Metodo che sposta un giocatore da un girone all'altro
     * @param girone girone in cui inserire il giocatore
     */
    @FXML
    private void handleInserisciGirone(int girone) {
        GiocatoreMatchplay selezione = Giocatoreselezionato;
        try {
            db.modificaGirone(Giocatoreselezionato, girone);
            switch (Giocatoreselezionato.getGirone()) {
                case 1: lista.get(0).remove(Giocatoreselezionato);
                    break;
                case 2: lista.get(1).remove(Giocatoreselezionato);
                    break;
                case 3: lista.get(2).remove(Giocatoreselezionato);
                    break;
                case 4: lista.get(3).remove(Giocatoreselezionato);
                    break;
            }
           selezione.setGirone(girone);
           lista.get(girone - 1).add(selezione);
        } catch (SQLException e) {
            e.printStackTrace();
            AlertUtil.displayGenericError();
        }
    }


    /**
     * Metodo che crea le partite
     */
    public void handleCreaPartite() {
        inserisciA.setDisable(true);
        inserisciB.setDisable(true);
        inserisciC.setDisable(true);
        inserisciD.setDisable(true);
        ProgressIndicator indicator = new ProgressIndicator();
        if (alreadyDone)
            AlertUtil.displayPersonalizedInfo("Partite già create", null);
        else {
            alreadyDone = true;
            Service backgroundThread = new Service() {
                @Override
                protected Task createTask() {
                    return new Task() {
                        @Override
                        protected Object call() throws Exception {
                            generaPartiteGirone(lista.get(0), 1);
                            generaPartiteGirone(lista.get(1), 2);
                            generaPartiteGirone(lista.get(2), 3);
                            generaPartiteGirone(lista.get(3), 4);
                            return null;
                        }
                    };
                }
            };
            backgroundThread.restart();
            bottom.getChildren().add(indicator);
            backgroundThread.setOnSucceeded(event -> {
                bottom.getChildren().remove(indicator);
                AlertUtil.displayPersonalizedInfo("Le partite sono state create", null);
            });

        }

    }

    /**
     * Metodo che mostra o nasconde la colonna Fascia
     */
    public void handleMostraFasce() {
        FasciagironeA.setVisible(! FasciagironeA.isVisible());
        FasciagironeB.setVisible(! FasciagironeB.isVisible());
        FasciagironeC.setVisible(! FasciagironeC.isVisible());
        FasciagironeD.setVisible(! FasciagironeD.isVisible());
    }

    private void generaPartiteGirone(ObservableList<GiocatoreMatchplay> list, Integer girone) {
        LinkedList<GiocatoreMatchplay> giocatori;
        ArrayList<Partita> partite = new ArrayList<>();
        giocatori = new LinkedList<>(list);
        int num_giocatori = giocatori.size();
        int num_giornate = num_giocatori - 1;
        int num_partite = num_giocatori / 2;

        for (int i = 1; i <= num_giornate; i++) {

            if (num_giocatori % 2 == 0) {
                System.out.println(giocatori.get(num_giocatori - 2).getNome() + " " + giocatori.get(num_giocatori - 2).getCognome() + " - " + giocatori.get(num_giocatori - 1).getNome() + " " + giocatori.get(num_giocatori - 1).getCognome());
                partite.add(new Partita(giocatori.get(num_giocatori - 2), giocatori.get(num_giocatori - 1), girone, i));

            }
            for (int j = 0; j < num_partite - 1 + (num_giocatori % 2); j++) {
                System.out.println(giocatori.get(j).getNome() + " " + giocatori.get(j).getCognome() + " - " + giocatori.get(num_giocatori - 3 - j + (num_giocatori % 2)).getNome() + " " + giocatori.get(num_giocatori - 3 - j + (num_giocatori % 2)).getCognome());
                partite.add(new Partita(giocatori.get(j), giocatori.get(num_giocatori - 3 - j + (num_giocatori % 2)), girone, i));
            }
            if (num_giocatori % 2 == 1)
                System.out.println("\nRiposa: " + giocatori.getLast().getNome() + " " + giocatori.getLast().getCognome());

            giocatori.addFirst(giocatori.getLast());
            giocatori.removeLast();
        }

        for (Partita aPartite : partite) {
            try {
                db.InserisciPartita(aPartite);
            } catch (SQLException e) {
                e.printStackTrace();
                AlertUtil.displayGenericError();
            }
        }
    }
}
