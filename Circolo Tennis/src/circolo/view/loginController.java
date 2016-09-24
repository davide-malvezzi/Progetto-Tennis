package circolo.view;

import circolo.Database;
import circolo.MainApp;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

import java.sql.SQLException;

public class loginController {
    @FXML
    private Button admin;
    @FXML
    private Button ospite;
    @FXML
    private VBox vBoxLogin;

    private MainApp mainApp;
    private Database db;
    private BorderPane pane;
    private VBox vBoxAdmin;
    private VBox vBoxOspite;
    private TextField user;
    private PasswordField pwd;
    private PasswordField pwdCircolo;
    private Button indietro;

    private boolean guest;

    public loginController() {
    }


    @FXML
    private void initialize() {
        vBoxAdmin = new VBox();
        vBoxOspite = new VBox();
        user = new TextField();
        pwd = new PasswordField();
        Label username = new Label("Username");
        Label password = new Label("Password");
        Label passwordCircolo = new Label("Password Circolo");
        pwdCircolo = new PasswordField();
        Button login1 = new Button("Login");
        Button login2 = new Button("Login");
        indietro = new Button();
        Image backArrow = new Image(getClass().getResourceAsStream("Arrow_Back.png"));
        indietro.setGraphic(new ImageView(backArrow));
        VBox.setMargin(login1, new Insets(5, 0, 0, 100));
        VBox.setMargin(login2, new Insets(5, 0, 0, 100));
        vBoxOspite.getChildren().addAll(passwordCircolo,pwdCircolo,login1);
        vBoxAdmin.getChildren().addAll(username, user, password, pwd, login2);

        guest = false;
        login1.setOnAction(event -> handleLogin());
        login2.setOnAction(event -> handleLogin());
        indietro.setOnAction(event -> handleIndietro());
    }


    @FXML
    private void handleAdmin() {
        guest = false;
        pane.setRight(vBoxAdmin);
        pane.setTop(indietro);
    }

    @FXML
    private void handleLogin() {
        String username;
        String password;
        if(!guest){
            username = (user.getText() != null) ? user.getText() : "";
            password = (pwd.getText() != null) ? pwd.getText() : "";
        }
        else {
            username = "guest";
            password = (pwdCircolo.getText() != null) ? pwdCircolo.getText() : "";
        }
        try {
            if (username.length() > 0 && password.length() > 0) {
                if (db.login(username, password))
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

    @FXML
    private void handleOspite(){
        guest = true;
        pane.setRight(vBoxOspite);
        pane.setTop(indietro);
    }

    @FXML
    private void handleIndietro() {
        pane.setRight(vBoxLogin);
        pane.setTop(null);
        user.setText("");
        pwd.setText("");
    }

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }

    public void setDatabase(Database db) {
        this.db = db;
    }

    public void setPane(BorderPane pane) {
        this.pane = pane;
    }


}
