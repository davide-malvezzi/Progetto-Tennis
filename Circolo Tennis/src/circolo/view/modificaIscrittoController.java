package circolo.view;

import circolo.Database;
import circolo.Giocatore;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;

import java.sql.SQLException;

public class modificaIscrittoController {
    @FXML
    private TextField newIndirizzo;
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
    private Giocatore oldGiocatore;


    @FXML
    private void initialize(){
        newFascia.getItems().addAll(1, 2, 3, 4, 5);
        newClassificaFIT.getItems().addAll("4.NC", "4.5", "4.4", "4.3", "4.2", "4.1", "4.0",
                "3.9", "3.8", "3.7", "3.6", "3.5", "3.4", "3.3", "3.2", "3.1", "3.0",
                "2.9", "2.8", "2.7", "2.6", "2.5", "2.4", "2.3", "2.2", "2.1", "2.0",
                "1.9", "1.8", "1.7", "1.6", "1.5", "1.4", "1.3", "1.2", "1.1");
    }

    @FXML
    private void handleConferma() {
        int indice = table.getSelectionModel().getSelectedIndex();
        Giocatore newGiocatore = oldGiocatore;
        newGiocatore.setIndirizzo(newIndirizzo.getText());
        newGiocatore.setSocio(newSocio.isSelected() ? 1 : 0);
        newGiocatore.setAgonista(newAgonista.isSelected() ? 1 : 0);
        newGiocatore.setFascia(newFascia.getValue());
        newGiocatore.setClassifica_FIT(newClassificaFIT.getValue());
        newGiocatore.setCF(oldGiocatore.getCF());
        try {
            db.modificaIscritto(newGiocatore);
            table.getItems().set(indice, newGiocatore);
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

    public void setParametri(Giocatore giocatore){
        if(giocatore != null) {
            newIndirizzo.setText(giocatore.getIndirizzo());
            newSocio.setSelected(giocatore.getSocio() == 1);
            newAgonista.setSelected(giocatore.getAgonista() == 1);
            newFascia.setValue(giocatore.getFascia());
            newClassificaFIT.setValue(giocatore.getClassifica_FIT());
            this.oldGiocatore = giocatore;
        }
    }

    public void setControlli(Database db,BorderPane pane,ButtonBar defaultPane,TableView<Giocatore> table){
        this.db = db;
        this.controlPane = pane;
        this.defaultPane = defaultPane;
        this.table = table;
    }
}