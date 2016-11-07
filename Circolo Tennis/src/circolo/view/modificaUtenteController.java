package circolo.view;

import circolo.Database;
import circolo.Giocatore;
import circolo.User;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;

import java.sql.SQLException;

/**
 * Classe che controlla il pannello modifica della gestione Utenti
 */
public class modificaUtenteController {
    @FXML
    private CheckBox newTipo;
    @FXML
    private TextField newNome;
    @FXML
    private TextField newPassword;

    private Database db;
    private BorderPane controlPane;
    private ButtonBar defaultPane;
    private TableView<User> table;

    private User oldUser;

    /**
     * Cambia i dati dell'utente
     */
    @FXML
    private void handleConferma() {
        User newUser = oldUser;
        newUser.setNome(newNome.getText());
        newUser.setPassword(newPassword.getText());
        newUser.setTipo(newTipo.isSelected() ? 1 : 0);
        try {
            db.modificaUser(newUser);
            table.refresh();
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

    /**
     * Metodo che inizializza l'utente da modificare con i suoi dati
     * @param user giocatore che si vuole modificare
     */
    public void setParametri(User user){
        if(user != null) {
            newNome.setText(user.getNome());
            newPassword.setText(user.getPassword());
            newTipo.setSelected(user.getTipo() == 1);
            this.oldUser = user;
        }
    }
}
