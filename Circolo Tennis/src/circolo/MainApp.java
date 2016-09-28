package circolo;

import circolo.view.ProgramUIController;
import circolo.view.loginController;
import circolo.view.risultatiIscrittiController;
import circolo.view.risultatiPrenotazioniController;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class MainApp extends Application {
    private Stage primaryStage;
    private BorderPane rootLayout;
    private TabPane ProgramUI;
    private Stage resultsStage = new Stage();
    private Database db;


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Tennis Club San Felice");
        resultsStage.initOwner(primaryStage);
        resultsStage.initModality(Modality.WINDOW_MODAL);
        initRootLayout();
        try {
            db = new Database();
        } catch (SQLException | ClassNotFoundException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Errore");
            alert.setHeaderText("Impossibile connettersi al database");
            alert.setContentText("Il programma verrà chiuso");
            alert.showAndWait();
            primaryStage.close();
        }
        showLogin();
    }



    private void initRootLayout() {
        try {
            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/rootLayout.fxml"));
            rootLayout = loader.load();

            // Show the scene containing the root layout.
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showLogin(){
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(MainApp.class.getResource("view/login.fxml"));
        try {
            BorderPane login = loader.load();
            rootLayout.setCenter(login);
            loginController controller = loader.getController();
            controller.setMainApp(this);
            controller.setPane(login);
            controller.setDatabase(db);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showProgramUI() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/ProgramUI.fxml"));
            this.ProgramUI = loader.load();
            rootLayout.setCenter(ProgramUI);
            ProgramUIController controller = loader.getController();
            controller.setMainApp(this);
            controller.setDatabase(db);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void showrisultatiIscritti(ObservableList<Giocatore> lista) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/risultatiIscritti.fxml"));
            BorderPane pane = loader.load();
            resultsStage.setTitle("Risultati di ricerca");
            Scene scene = new Scene(pane);
            resultsStage.setScene(scene);
            resultsStage.show();
            risultatiIscrittiController controller = loader.getController();
            controller.inserisciRisultatiTabella(lista);
            controller.setBorderPane(pane);
            controller.setDatabase(db);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showrisultatiPrenotazioni(ObservableList<Campo> lista, Prenotazione prenotazione) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/risultatiPrenotazioni.fxml"));
            BorderPane pane = loader.load();
            resultsStage.setTitle("Risultati di ricerca");
            Scene scene = new Scene(pane);
            resultsStage.setScene(scene);
            resultsStage.setResizable(false);
            resultsStage.show();
            risultatiPrenotazioniController controller = loader.getController();
            controller.setPrenotazione(prenotazione);
            controller.inserisciRisultatiTabella(lista);
            controller.setResultStage(resultsStage);
            controller.setDatabase(db);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }
}