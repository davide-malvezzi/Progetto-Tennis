package circolo.view;

import circolo.Database;
import circolo.Giocatore;
import circolo.util.AlertUtil;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.sql.SQLException;

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
    private TableColumn<Giocatore,String> gironeA;
    @FXML
    private TableColumn<Giocatore,String> gironeB;
    @FXML
    private TableColumn<Giocatore,String> gironeC;
    @FXML
    private TableColumn<Giocatore,String> gironeD;
    @FXML
    private TableColumn<Giocatore,String> NomegironeA;
    @FXML
    private TableColumn<Giocatore,String> CognomegironeA;
    @FXML
    private TableColumn<Giocatore,String> NomegironeB;
    @FXML
    private TableColumn<Giocatore,String> CognomegironeB;
    @FXML
    private TableColumn<Giocatore,String> NomegironeC;
    @FXML
    private TableColumn<Giocatore,String> CognomegironeC;
    @FXML
    private TableColumn<Giocatore,String> NomegironeD;
    @FXML
    private TableColumn<Giocatore,String> CognomegironeD;
    @FXML
    private Button modifica;
    @FXML
    private Button creaPartite;

    private Database db;

    @FXML
    private void initialize(){
        NomegironeA.setCellValueFactory(cellData -> cellData.getValue().getNomeProperty());
        CognomegironeA.setCellValueFactory(cellData -> cellData.getValue().getCognomeProperty());
        NomegironeB.setCellValueFactory(cellData -> cellData.getValue().getNomeProperty());
        CognomegironeB.setCellValueFactory(cellData -> cellData.getValue().getCognomeProperty());
        NomegironeC.setCellValueFactory(cellData -> cellData.getValue().getNomeProperty());
        CognomegironeC.setCellValueFactory(cellData -> cellData.getValue().getCognomeProperty());
        NomegironeD.setCellValueFactory(cellData -> cellData.getValue().getNomeProperty());
        CognomegironeD.setCellValueFactory(cellData -> cellData.getValue().getCognomeProperty());
    }


    public void inserisci() {
        ObservableList<ObservableList<Giocatore>> lista;
        try {
            lista = db.mostraGironi();
            tableA.setItems(lista.get(0));
            tableB.setItems(lista.get(1));
            tableC.setItems(lista.get(2));
            tableD.setItems(lista.get(3));
        } catch (SQLException e) {
            e.printStackTrace();
            AlertUtil.displayGenericError();
        }
    }

    @FXML
    private void handleCreaPartite(){

    }



    public void setDatabase(Database db){
        this.db = db;
    }


}