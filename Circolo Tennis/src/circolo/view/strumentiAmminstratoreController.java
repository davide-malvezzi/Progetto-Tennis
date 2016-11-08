package circolo.view;

import circolo.*;
import circolo.util.AlertUtil;
import circolo.util.DateUtil;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import javax.jws.soap.SOAPBinding;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Classe che gestisce l'interfaccia del pannello Strumenti Amministratore
 */
public class strumentiAmminstratoreController {
    @FXML
    private BorderPane pane;
    @FXML
    private AnchorPane topPane;
    @FXML
    private TableView<User> table;
    @FXML
    private TableColumn<User, String> nomeCol;
    @FXML
    private TableColumn<User, String> passwordCol;
    @FXML
    private TableColumn<User, Integer> tipoCol;
    @FXML
    private Button modifica;
    @FXML
    private Button elimina;

    private Database db;
    private User user;
    private ObservableList<User> lista;

    private int offset = 0;

    private boolean modificaClick = false;
    private int offsetModifica = 0;

    ButtonBar defaultPane;

    private User selezionato = new User();
    private modificaUtenteController controllerUtente;

    /**
     * Inizializza il pannello gestione iscritti
     */
    @FXML
    private void initialize() {
        defaultPane = (ButtonBar) pane.getBottom();
        try {
            db = Database.getInstance();
            lista = db.loadUtenti(offsetModifica);
            nomeCol.setCellValueFactory(cellData -> cellData.getValue().getNomeProperty());
            passwordCol.setCellValueFactory(cellData -> cellData.getValue().getPasswordProperty());
            tipoCol.setCellValueFactory(cellData -> cellData.getValue().getTipo() == 1 ? new SimpleStringProperty("Amministratore") : new SimpleStringProperty("Ospite"));

            table.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> Selezione(newValue));
            table.setItems(lista);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            AlertUtil.displayGenericError();
        }
    }

    @FXML
    private void handleModifica() {
        if (!modificaClick) {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/modificaUtente.fxml"));
            try {
                pane = loader.load();
                controllerUtente = loader.getController();
                controllerUtente.setParametri(selezionato);
                modificaClick = true;
                pane.setBottom(pane);
            } catch (IOException e) {
                e.printStackTrace();
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Errore");
                alert.setHeaderText("Si è verificato un problema interno");
                alert.showAndWait();
            }
        } else pane.setBottom(pane);
    }

    @FXML
    private void handleElimina(){
        boolean elimina = AlertUtil.displayConfirmationDialog("Elimina Utente", "Vuoi eliminare definitivamente " + selezionato.getNome() + "?");
        if(elimina){
            try {
                db.eliminaUser(selezionato);
                lista.remove(lista.indexOf(selezionato));
            } catch (SQLException e) {
                e.printStackTrace();
                AlertUtil.displayGenericError();
            }
        }

    }
}
