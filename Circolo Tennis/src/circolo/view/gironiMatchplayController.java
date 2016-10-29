package circolo.view;

import circolo.Database;
import circolo.Giocatore;
import circolo.Partita;
import circolo.util.AlertUtil;
import javafx.collections.ObservableList;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;

public class gironiMatchplayController {
    @FXML
    private TableView<Giocatore> tableA;
    @FXML
    private TableView<Giocatore> tableB;
    @FXML
    private TableView<Giocatore> tableC;
    @FXML
    private TableView<Giocatore> tableD;
    @FXML
    private TableColumn<Giocatore, String> gironeA;
    @FXML
    private TableColumn<Giocatore, String> gironeB;
    @FXML
    private TableColumn<Giocatore, String> gironeC;
    @FXML
    private TableColumn<Giocatore, String> gironeD;
    @FXML
    private TableColumn<Giocatore, String> NomegironeA;
    @FXML
    private TableColumn<Giocatore, String> CognomegironeA;
    @FXML
    private TableColumn<Giocatore, String> NomegironeB;
    @FXML
    private TableColumn<Giocatore, String> CognomegironeB;
    @FXML
    private TableColumn<Giocatore, String> NomegironeC;
    @FXML
    private TableColumn<Giocatore, String> CognomegironeC;
    @FXML
    private TableColumn<Giocatore, String> NomegironeD;
    @FXML
    private TableColumn<Giocatore, String> CognomegironeD;
    @FXML
    private Button modifica;
    @FXML
    private Button creaPartite;

    private ObservableList<ObservableList<Giocatore>> lista;
    private Database db;
    private Giocatore giocatore = new Giocatore();
    private Button inserisciA;
    private Button inserisciB;
    private Button inserisciC;
    private Button inserisciD;
    private Button annulla;
    private ButtonBar SecondbuttonBar;
    private BorderPane pane;
    private HBox FirstbuttonBar;
    private HBox hBox;
    private boolean modificaClicked;
    private ProgressIndicator indicator;
    private boolean alreadyDone = false;

    @FXML
    private void initialize() {
        modificaClicked = false;
        //definizione riempimento celle
        NomegironeA.setCellValueFactory(cellData -> cellData.getValue().getNomeProperty());
        CognomegironeA.setCellValueFactory(cellData -> cellData.getValue().getCognomeProperty());
        NomegironeB.setCellValueFactory(cellData -> cellData.getValue().getNomeProperty());
        CognomegironeB.setCellValueFactory(cellData -> cellData.getValue().getCognomeProperty());
        NomegironeC.setCellValueFactory(cellData -> cellData.getValue().getNomeProperty());
        CognomegironeC.setCellValueFactory(cellData -> cellData.getValue().getCognomeProperty());
        NomegironeD.setCellValueFactory(cellData -> cellData.getValue().getNomeProperty());
        CognomegironeD.setCellValueFactory(cellData -> cellData.getValue().getCognomeProperty());

        //definizione listeners delle tabelle
        tableA.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> Selezione(newValue, 1));
        tableB.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> Selezione(newValue, 2));
        tableC.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> Selezione(newValue, 3));
        tableD.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> Selezione(newValue, 4));


        try {
            db = Database.getInstance();
            lista = db.mostraGironi();
            tableA.setItems(lista.get(0));
            tableB.setItems(lista.get(1));
            tableC.setItems(lista.get(2));
            tableD.setItems(lista.get(3));
            if (db.checkPartite()) {
                alreadyDone = true;
                modifica.setDisable(true);
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            AlertUtil.displayGenericError();
        }
    }

    @FXML
    private void Selezione(Giocatore giocatore, int table) {
        this.giocatore = giocatore;

        if (modificaClicked) {
            switch (table) {
                case 1:
                    inserisciA.setDisable(true);
                    inserisciB.setDisable(false);
                    inserisciC.setDisable(false);
                    inserisciD.setDisable(false);
                    break;
                case 2:
                    inserisciA.setDisable(false);
                    inserisciB.setDisable(true);
                    inserisciC.setDisable(false);
                    inserisciD.setDisable(false);
                    break;
                case 3:
                    inserisciA.setDisable(false);
                    inserisciB.setDisable(false);
                    inserisciC.setDisable(true);
                    inserisciD.setDisable(false);
                    break;
                case 4:
                    inserisciA.setDisable(false);
                    inserisciB.setDisable(false);
                    inserisciC.setDisable(false);
                    inserisciD.setDisable(true);
                    break;
            }
        }

    }


    @FXML
    private void handleCreaPartite() {
        indicator = new ProgressIndicator();
        if (alreadyDone)
            AlertUtil.displayPersonalizedInfo("Partite già create", null);
        else {
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
            FirstbuttonBar.getChildren().add(indicator);
            backgroundThread.setOnSucceeded(event -> {
                FirstbuttonBar.getChildren().remove(indicator);
                AlertUtil.displayPersonalizedInfo("Le partite sono state create", null);
            });

        }


    }

    private void generaPartiteGirone(ObservableList<Giocatore> list, Integer girone) {
        LinkedList<Giocatore> giocatori;
        ArrayList<Partita> partite = new ArrayList<>();
        giocatori = new LinkedList<>(list);
        int num_giocatori = giocatori.size();
        int num_giornate = num_giocatori - 1;
        int num_partite = num_giocatori / 2;

        for (int i = 1; i <= num_giornate; i++) {
            if (num_giocatori % 2 == 0) {
                System.out.println(giocatori.get(num_giocatori - 2).getNome() + " " + giocatori.get(num_giocatori - 2).getCognome() + " - " + giocatori.get(num_giocatori - 1).getNome() + " " + giocatori.get(num_giocatori - 1).getCognome());
                partite.add(new Partita(giocatori.get(num_giocatori - 2), giocatori.get(num_giocatori - 1), girone));

            }
            for (int j = 0; j < num_partite - 1 + (num_giocatori % 2); j++) {
                System.out.println(giocatori.get(j).getNome() + " " + giocatori.get(j).getCognome() + " - " + giocatori.get(num_giocatori - 3 - j + (num_giocatori % 2)).getNome() + " " + giocatori.get(num_giocatori - 3 - j + (num_giocatori % 2)).getCognome());
                partite.add(new Partita(giocatori.get(j), giocatori.get(num_giocatori - 3 - j + (num_giocatori % 2)), girone));
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

    @FXML
    private void handleModfica() {
        modificaClicked = true;
        hBox = new HBox();
        SecondbuttonBar = new ButtonBar();
        inserisciA = new Button("Girone A");
        inserisciB = new Button("Girone B");
        inserisciC = new Button("Girone C");
        inserisciD = new Button("Girone D");
        annulla = new Button("Annulla");
        inserisciA.setDisable(true);
        inserisciB.setDisable(true);
        inserisciC.setDisable(true);
        inserisciD.setDisable(true);
        ButtonBar.setButtonData(inserisciA, ButtonBar.ButtonData.LEFT);
        ButtonBar.setButtonData(inserisciB, ButtonBar.ButtonData.LEFT);
        ButtonBar.setButtonData(inserisciC, ButtonBar.ButtonData.LEFT);
        ButtonBar.setButtonData(inserisciD, ButtonBar.ButtonData.LEFT);
        ButtonBar.setButtonData(annulla, ButtonBar.ButtonData.RIGHT);
        SecondbuttonBar.getButtons().addAll(inserisciA, inserisciB, inserisciC, inserisciD, annulla);
        HBox.setMargin(SecondbuttonBar, new Insets(5, 10, 5, 0));
        HBox.setHgrow(SecondbuttonBar, Priority.ALWAYS);
        hBox.getChildren().setAll(SecondbuttonBar);
        pane.setBottom(hBox);

        //definizione azione bottoni
        inserisciA.setOnAction(event -> handleInserisciGirone(1));
        inserisciB.setOnAction(event -> handleInserisciGirone(2));
        inserisciC.setOnAction(event -> handleInserisciGirone(3));
        inserisciD.setOnAction(event -> handleInserisciGirone(4));
        annulla.setOnAction(event -> handleAnnulla());
    }

    @FXML
    private void handleAnnulla() {
        pane.setBottom(FirstbuttonBar);
    }

    @FXML
    private void handleInserisciGirone(int girone) {
        Giocatore selezionato = this.giocatore;
        try {
            db.modificaGirone(giocatore, girone);
            if (lista.get(0).contains(giocatore)) lista.get(0).remove(lista.get(0).indexOf(giocatore));
            else if (lista.get(1).contains(giocatore)) lista.get(1).remove(lista.get(1).indexOf(giocatore));
            else if (lista.get(2).contains(giocatore)) lista.get(2).remove(lista.get(2).indexOf(giocatore));
            else if (lista.get(3).contains(giocatore)) lista.get(3).remove(lista.get(3).indexOf(giocatore));
            lista.get(girone - 1).add(selezionato);
        } catch (SQLException e) {
            e.printStackTrace();
            AlertUtil.displayGenericError();
        }
    }

    public void setControlli(BorderPane pane, HBox bottom) {
        this.pane = pane;
        this.FirstbuttonBar = bottom;
    }

}