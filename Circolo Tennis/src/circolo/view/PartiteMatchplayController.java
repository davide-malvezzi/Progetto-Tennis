package circolo.view;

import circolo.Database;
import circolo.Partita;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.sql.SQLException;

public class PartiteMatchplayController {
    @FXML
    private Button gironeA,gironeB,gironeC,gironeD;
    @FXML
    private TableView<Partita> table;
    @FXML
    private TableColumn nomeGironeColumn;
    @FXML
    private TableColumn<Partita,String> Giocatore1;
    @FXML
    private TableColumn<Partita,String> Giocatore2;

    private ObservableList<ObservableList<Partita>> lista;
    private Database db;

    @FXML
    private void initialize(){
        Giocatore1.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPlayer1().getNome() + " " +  cellData.getValue().getPlayer1().getCognome()));
        Giocatore2.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPlayer2().getNome() + " " + cellData.getValue().getPlayer2().getCognome()));

        try {
            db = Database.getInstance();
            lista = db.loadPartite();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        table.setItems(lista.get(0));
    }


    @FXML
    private void handlePartiteGironeA(){
        nomeGironeColumn.setText("Girone A");
        table.setItems(lista.get(0));
    }

    @FXML
    private void handlePartiteGironeB(){
        nomeGironeColumn.setText("Girone B");
        table.setItems(lista.get(1));
    }
    @FXML
    private void handlePartiteGironeC(){
        nomeGironeColumn.setText("Girone C");
        table.setItems(lista.get(2));
    }
    @FXML
    private void handlePartiteGironeD(){
        nomeGironeColumn.setText("Girone D");
        table.setItems(lista.get(3));
    }




}
