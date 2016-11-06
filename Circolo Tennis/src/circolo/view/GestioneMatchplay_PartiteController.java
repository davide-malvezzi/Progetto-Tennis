package circolo.view;

import circolo.Database;
import circolo.GiocatoreMatchplay;
import circolo.Partita;
import circolo.util.AlertUtil;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.sql.SQLException;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Classe che gestisce l'interfaccia del pannello Matchplay sezione Partite
 */
public class GestioneMatchplay_PartiteController {
    /**
     * Tabella che contiene le partite del girone A
     */
    @FXML
    private TableView<Partita> tableGironeA;
    /**
     * Tabella che contiene le partite del girone B
     */
    @FXML
    private TableView<Partita> tableGironeB;
    /**
     * Tabella che contiene le partite del girone C
     */
    @FXML
    private TableView<Partita> tableGironeC;
    /**
     * Tabella che contiene le partite del girone D
     */
    @FXML
    private TableView<Partita> tableGironeD;
    @FXML
    private TableColumn<Partita, String> Giocatore1A;
    @FXML
    private TableColumn<Partita, String> Giocatore2A;
    @FXML
    private TableColumn<Partita, String> punteggioA;
    @FXML
    private TableColumn<Partita, String> Giocatore1B;
    @FXML
    private TableColumn<Partita, String> Giocatore2B;
    @FXML
    private TableColumn<Partita, String> punteggioB;
    @FXML
    private TableColumn<Partita, String> Giocatore1C;
    @FXML
    private TableColumn<Partita, String> Giocatore2C;
    @FXML
    private TableColumn<Partita, String> punteggioC;
    @FXML
    private TableColumn<Partita, String> Giocatore1D;
    @FXML
    private TableColumn<Partita, String> Giocatore2D;
    @FXML
    private TableColumn<Partita, String> punteggioD;
    @FXML
    private ListView<String> turni;
    @FXML
    private ComboBox<String> classificaGironi;
    @FXML
    private TableView<GiocatoreMatchplay> tableClassifica;
    @FXML
    private TableColumn<GiocatoreMatchplay, String> nomeClassifica;
    @FXML
    private TableColumn<GiocatoreMatchplay, String> cognomeClassifica;
    @FXML
    private TableColumn<GiocatoreMatchplay, Integer> puntiClassifica;
    @FXML
    private TextField risultato;
    @FXML
    private Button risultatoButton;

    private Database db;
    private ObservableMap<Integer, ObservableList<ObservableList<Partita>>> turniMap = FXCollections.observableHashMap();
    private Partita partitaSelezionata;

    private ObservableList<GiocatoreMatchplay> classificaA = FXCollections.observableArrayList();
    private ObservableList<GiocatoreMatchplay> classificaB = FXCollections.observableArrayList();
    private ObservableList<GiocatoreMatchplay> classificaC = FXCollections.observableArrayList();
    private ObservableList<GiocatoreMatchplay> classificaD = FXCollections.observableArrayList();


    @FXML
    private void initialize() {
        classificaGironi.getItems().addAll("Girone A", "Girone B", "Girone C", "Girone D");
        classificaGironi.setValue("Girone A");
        try {
            db = Database.getInstance();
            aggiornaClassifica();

            int numeroTurni = db.turniMatchplay();
            for (int i = 0; i < numeroTurni; i++) turni.getItems().add(i + 1 + " Turno");
            turniMap = db.loadPartite();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            AlertUtil.displayGenericError();
        }

        Giocatore1A.setCellValueFactory(x -> new SimpleStringProperty(x.getValue().getPlayer1().getNome() + " " + x.getValue().getPlayer1().getCognome()));
        Giocatore2A.setCellValueFactory(x -> new SimpleStringProperty(x.getValue().getPlayer2().getNome() + " " + x.getValue().getPlayer2().getCognome()));
        Giocatore1B.setCellValueFactory(x -> new SimpleStringProperty(x.getValue().getPlayer1().getNome() + " " + x.getValue().getPlayer1().getCognome()));
        Giocatore2B.setCellValueFactory(x -> new SimpleStringProperty(x.getValue().getPlayer2().getNome() + " " + x.getValue().getPlayer2().getCognome()));
        Giocatore1C.setCellValueFactory(x -> new SimpleStringProperty(x.getValue().getPlayer1().getNome() + " " + x.getValue().getPlayer1().getCognome()));
        Giocatore2C.setCellValueFactory(x -> new SimpleStringProperty(x.getValue().getPlayer2().getNome() + " " + x.getValue().getPlayer2().getCognome()));
        Giocatore1D.setCellValueFactory(x -> new SimpleStringProperty(x.getValue().getPlayer1().getNome() + " " + x.getValue().getPlayer1().getCognome()));
        Giocatore2D.setCellValueFactory(x -> new SimpleStringProperty(x.getValue().getPlayer2().getNome() + " " + x.getValue().getPlayer2().getCognome()));
        punteggioA.setCellValueFactory(x -> x.getValue().risultatoProperty());
        punteggioB.setCellValueFactory(x -> x.getValue().risultatoProperty());
        punteggioC.setCellValueFactory(x -> x.getValue().risultatoProperty());
        punteggioD.setCellValueFactory(x -> x.getValue().risultatoProperty());

        nomeClassifica.setCellValueFactory(x -> x.getValue().getNomeProperty());
        cognomeClassifica.setCellValueFactory(x -> x.getValue().getCognomeProperty());
        puntiClassifica.setCellValueFactory(x -> x.getValue().getPuntiMatchplayProperty().asObject());


        puntiClassifica.setSortType(TableColumn.SortType.DESCENDING);
        tableClassifica.setItems(classificaA);


        turni.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            try {
                int n = NumberFormat.getIntegerInstance().parse(newValue).intValue();
                tableGironeA.setItems(turniMap.get(n).get(0));
                tableGironeB.setItems(turniMap.get(n).get(1));
                tableGironeC.setItems(turniMap.get(n).get(2));
                tableGironeD.setItems(turniMap.get(n).get(3));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        });

        turni.getSelectionModel().select(0);

        tableGironeA.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            partitaSelezionata = newValue;

            if (newValue != null) {
                if (newValue.getRisultato() == null || newValue.getRisultato().length() == 0 ){
                    risultatoButton.setDisable(false);
                    risultato.setDisable(false);
                }else{
                    risultatoButton.setDisable(true);
                    risultato.setDisable(true);
                }
            }
        });
        tableGironeB.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            partitaSelezionata = newValue;
            if (newValue != null) {
                if (newValue.getRisultato() == null || newValue.getRisultato().length() == 0 ){
                    risultatoButton.setDisable(false);
                    risultato.setDisable(false);
                }else{
                    risultatoButton.setDisable(true);
                    risultato.setDisable(true);
                }
            }
        });
        tableGironeC.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            partitaSelezionata = newValue;

            if (newValue != null) {
                if (newValue.getRisultato() == null || newValue.getRisultato().length() == 0 ){
                    risultatoButton.setDisable(false);
                    risultato.setDisable(false);
                }else{
                    risultatoButton.setDisable(true);
                    risultato.setDisable(true);
                }
            }
        });
        tableGironeD.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            partitaSelezionata = newValue;

            if (newValue != null) {
                if (newValue.getRisultato() == null || newValue.getRisultato().length() == 0 ){
                    risultatoButton.setDisable(false);
                    risultato.setDisable(false);
                }else{
                    risultatoButton.setDisable(true);
                    risultato.setDisable(true);
                }

            }
        });
    }

    /**
     * Metodo che mostra le classifiche dei vari gironi
     */
    @FXML
    private void handleCambioClassifica() {
        switch (classificaGironi.getValue()) {
            case "Girone B":
                tableClassifica.setItems(classificaB);
                break;
            case "Girone C":
                tableClassifica.setItems(classificaC);
                break;
            case "Girone D":
                tableClassifica.setItems(classificaD);
                break;
            default:
                tableClassifica.setItems(classificaA);
        }
    }


    /**
     * Metodo che aggiorna il risultato della partita
     */
    @FXML
    private void handleAggiornaRisultato() {
        if (risultato.getText() != null) {
            int puntiGiocatore1, puntiGiocatore2;
            Scanner scanner = new Scanner(risultato.getText()).useDelimiter("\\D");
            try {
                puntiGiocatore1 = scanner.nextInt();
                puntiGiocatore2 = scanner.nextInt();

                partitaSelezionata.setRisultato(risultato.getText());
                try {
                    db.aggiornaRisultatoPartita(partitaSelezionata);
                    if (puntiGiocatore1 > puntiGiocatore2)
                        db.aggiornaPunteggioGiocatore(partitaSelezionata.getPlayer1(), 3);
                    else if (puntiGiocatore1 < puntiGiocatore2)
                        db.aggiornaPunteggioGiocatore(partitaSelezionata.getPlayer2(), 3);
                    else {
                        db.aggiornaPunteggioGiocatore(partitaSelezionata.getPlayer1(), 1);
                        db.aggiornaPunteggioGiocatore(partitaSelezionata.getPlayer2(), 1);
                    }

                    aggiornaClassifica();

                    handleCambioClassifica();

                } catch (SQLException e) {
                    e.printStackTrace();
                }
            } catch (InputMismatchException e) {
                e.printStackTrace();
                AlertUtil.displayPersonalizedError("Dato errato", "Inserire un punteggio ammissibile");
            }
        }
    }

    private void aggiornaClassifica() {
        try {
            classificaA = db.loadClassifica(1);
            classificaB = db.loadClassifica(2);
            classificaC = db.loadClassifica(3);
            classificaD = db.loadClassifica(4);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
