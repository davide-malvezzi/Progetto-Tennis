package circolo.view;

import circolo.Database;
import circolo.User;
import circolo.util.AlertUtil;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;

import java.sql.SQLException;

/**
 * Classe che permette l'inserimento di un nuovo utente
 */
public class nuovoUtenteController {
    @FXML
    private TextField newNome;
    @FXML
    private TextField newPassword;
    @FXML
    private CheckBox newTipo;

    private Database db;

    private ObservableList<User> lista;


    @FXML
    private void initialize() {
        try {
            db = Database.getInstance();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }


    @FXML
    private void handleOK() {
        if (isInputvalid()) {
            User user = new User();
            user.setNome(newNome.getText());
            user.setPassword(newPassword.getText());
            user.setTipo(newTipo.isSelected() ? 1 : 0);
            newNome.setText("");
            newPassword.setText("");
            newTipo.setSelected(false);
            try {
                if (db.nuovoUser(user)) {
                    AlertUtil.displayPersonalizedInfo("Operazione effettuata", "Nuovo utente inserito");
                    lista.add(user);
                }
                else AlertUtil.displayPersonalizedInfo("Impossibile inserire nuovo utente","Username già presente");
            } catch (SQLException e) {
                e.printStackTrace();
                AlertUtil.displayGenericError();
            }
        }
    }


    private boolean isInputvalid() {
        String errorMessage = "";

        if (newNome.getText() == null || newNome.getText().length() == 0)
            errorMessage += "Nome Utente non può essere vuoto\n";

        if (newPassword.getText() == null || newPassword.getText().length() == 0)
            errorMessage += "Password non può essere vuota\n";

        if (errorMessage.length() > 0) {
            AlertUtil.displayPersonalizedError("Campi non validi", errorMessage);

            return false;
        } else return true;
    }


    public void setLista(ObservableList<User> lista){
        this.lista = lista;
    }


}
