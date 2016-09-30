package circolo.view;

import circolo.Database;
import circolo.Giocatore;
import circolo.util.AlertUtil;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.sql.SQLException;
import java.time.LocalDate;

public class ListaPartecipantiMatchplayController {
    @FXML
    private TableView<Giocatore> table;
    @FXML
    private TableColumn<Giocatore,String> nome;
    @FXML
    private TableColumn<Giocatore,String> cognome;
    @FXML
    private TableColumn<Giocatore,Integer> fascia;
    @FXML
    private TableColumn<Giocatore,Integer> edizione;
    @FXML
    private Button rimuovi;

    private Database db;
    private ObservableList<Giocatore> lista = FXCollections.observableArrayList();


    @FXML
    private void initialize(){
            nome.setCellValueFactory(cellData -> cellData.getValue().getNomeProperty());
            cognome.setCellValueFactory(cellData -> cellData.getValue().getCognomeProperty());
            fascia.setCellValueFactory(cellData -> cellData.getValue().getFasciaProperty().asObject());
            edizione.setCellValueFactory(cellData -> new SimpleIntegerProperty(LocalDate.now().getYear()).asObject());
    }

    public void inserisciLista(){
        try {
            lista = db.ListaPartecipanti();
            table.getItems().addAll(lista);
        } catch (SQLException e) {
            e.printStackTrace();
            AlertUtil.displayGenericError();
        }
    }


    public void setDatabase(Database db){
        this.db = db;
    }


}
