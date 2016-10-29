package circolo.view;

import circolo.Database;
import circolo.MainApp;
import circolo.util.AlertUtil;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.sql.SQLException;

public class loginController {
    @FXML
    private TextField username;
    @FXML
    private PasswordField password;
    @FXML
    private Button backdoor;

    private MainApp mainApp;
    private Database db;

    @FXML
    private void initialize() {
        try {
            db = Database.getInstance();
            backdoor.setOnAction(event -> mainApp.showProgramUI());
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            AlertUtil.displayGenericError();
        }

    }

    @FXML
    private void handleLogin() {
        String usr;
        String pwd;
        int admin;
        usr = (username.getText() != null) ? username.getText() : "";
        pwd = (password.getText() != null) ? password.getText() : "";
        try {
            if (usr.length() > 0 && pwd.length() > 0) {
                admin = db.login(usr, pwd);
                if (admin == 0)
                    mainApp.showGuestUI();
                else if (admin == 1) mainApp.showProgramUI();
                else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Errore");
                    alert.setHeaderText("Accesso negato");
                    alert.setContentText("Utente non trovato");
                    alert.showAndWait();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Errore");
            alert.setHeaderText("Accesso negato");
            alert.setContentText("Si è verificato un problema interno");
            alert.showAndWait();
        }
    }

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }


}
