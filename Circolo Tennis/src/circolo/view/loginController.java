package circolo.view;

import circolo.Database;
import circolo.MainApp;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

import java.sql.SQLException;

public class loginController {
    @FXML
    private Button admin;
    @FXML
    private Button ospite;

    private MainApp mainApp;
    private Database db;
    private BorderPane pane;
    private VBox vBox = new VBox();
    private Label username = new Label("Username");
    private Label password = new Label("Password");
    private TextField user = new TextField();
    private PasswordField pwd = new PasswordField();
    private Button login = new Button("Login");

    public loginController(){}


    @FXML
    private void initialize(){
        vBox.getChildren().addAll(username,user,password,pwd,login);
        VBox.setMargin(login,new Insets(5,0,0,100));
    }


    @FXML
    private void handleAdmin(){
        pane.setRight(vBox);
        login.setOnAction(event -> handleLogin());
    }

    @FXML
    private void handleLogin(){
        String username = (user.getText() != null) ? user.getText() : "";
        String password = (pwd.getText() != null) ? pwd.getText() : "";
        System.out.println(username + "\n" + password);
        try {
            if(username.length() > 0 && password.length() >0) {
                if (db.loginAdmin(username, password))
                    mainApp.showProgramUI();
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
        }
    }

    public void setMainApp(MainApp mainApp){
        this.mainApp = mainApp;
    }

    public void setDatabase(Database db){
        this.db = db;
    }

    public void setPane(BorderPane pane){
        this.pane = pane;
    }


}
