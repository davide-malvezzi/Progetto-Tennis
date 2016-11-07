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
    private TableView<User> usersTable;

    @FXML
    private TableColumn<User, String> nomeUsersTable;

    @FXML
    private TableColumn<User, String> passwordUsersTable;

    @FXML
    private TableColumn<User, Integer> tipoUsersTable;

    @FXML
    private Button nuovoUtente;

    @FXML
    private Button modificaUtente;

    @FXML
    private Button eliminaUtente;

    private Database db;
    private ObservableList<User> lista;

    /**
     * pannello che contiene i comandi per modificare i dati dell'utente
     */
    private AnchorPane listaPane;
    User user = new User();
    ButtonBar defaultPane;

    private boolean modificaClicked = false;

    private User selezionato = new User();
    private modificaUtenteController controllerUtente;

    @FXML
    private void handleModifica() {
        if (!modificaClicked) {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/modificaUtente.fxml"));
            try {
                listaPane = loader.load();
                controllerUtente = loader.getController();
                controllerUtente.setParametri(selezionato);
                modificaClicked = true;
                pane.setBottom(listaPane);
            } catch (IOException e) {
                e.printStackTrace();
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Errore");
                alert.setHeaderText("Si è verificato un problema interno");
                alert.showAndWait();
            }
        } else pane.setBottom(listaPane);
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
