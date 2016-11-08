package circolo.view;

import circolo.Database;
import circolo.MainApp;
import circolo.User;
import circolo.util.AlertUtil;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

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
    private TableColumn<User, String> tipoCol;
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
    AnchorPane modificaPane;

    private User selezionato = new User();
    private modificaUtenteController controllerUtente;

    /**
     * Inizializza il pannello gestione utenti
     */
    @FXML
    private void initialize() {
        defaultPane = (ButtonBar) pane.getBottom();
        try {
            db = Database.getInstance();
            lista = db.loadUtenti();
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

    private void Selezione(User user) {
        this.selezionato = user;
        if(user == null){
            modifica.setDisable(true);
            elimina.setDisable(true);
        }else {
            modifica.setDisable(false);
            elimina.setDisable(false);
        }
        if (modificaClick) {
            controllerUtente.setParametri(user);
        }
    }

    @FXML
    private void handleModifica() {
        if (!modificaClick) {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/modificaUtente.fxml"));
            try {
                modificaPane = loader.load();
                controllerUtente = loader.getController();
                controllerUtente.setParametri(selezionato);
                controllerUtente.setControlli(pane,defaultPane,table);
                modificaClick = true;
                pane.setBottom(modificaPane);
            } catch (IOException e) {
                e.printStackTrace();
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Errore");
                alert.setHeaderText("Si è verificato un problema interno");
                alert.showAndWait();
            }
        } else pane.setBottom(modificaPane);
    }

    @FXML
    private void handleElimina(){
        boolean elimina = AlertUtil.displayConfirmationDialog("Elimina Utente", "Vuoi eliminare definitivamente " + selezionato.getNome() + "?");
        if(elimina){
            try {
                db.eliminaUser(selezionato);
                lista.remove(selezionato);
            } catch (SQLException e) {
                e.printStackTrace();
                AlertUtil.displayGenericError();
            }
        }

    }


    @FXML
    private void handleNuovaIscrizione(){
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(MainApp.class.getResource("view/nuovoUtente.fxml"));
        try {
            AnchorPane pane = loader.load();
            nuovoUtenteController controller = loader.getController();
            controller.setLista(lista);
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Nuovo Utente");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.setResizable(false);
            dialogStage.initOwner(MainApp.getPrimaryStage());
            Scene scene = new Scene(pane);
            dialogStage.setScene(scene);
            dialogStage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
            AlertUtil.displayGenericError();
        }
    }
}
