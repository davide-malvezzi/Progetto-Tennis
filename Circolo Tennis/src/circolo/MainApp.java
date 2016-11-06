package circolo;

import circolo.view.*;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class MainApp extends Application {
    private static Stage primaryStage;
    private static BorderPane rootLayout;
    private Stage resultsStage = new Stage();
    private Database db;


    public static void main(String[] args) {
        launch(args);
    }

    public static BorderPane getRoot(){
        return rootLayout;
    }

    public static Stage getPrimaryStage(){
        return primaryStage;
    }

    @Override
    public void start(Stage primaryStage) {
        MainApp.primaryStage = primaryStage;
        MainApp.primaryStage.setTitle("Tennis Club San Felice");
        resultsStage.initOwner(primaryStage);
        resultsStage.initModality(Modality.WINDOW_MODAL);
        initRootLayout();
        try {
            db = Database.getInstance();
        } catch (SQLException  | ClassNotFoundException e) {
            e.printStackTrace();
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

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/rootLayout.fxml"));
            rootLayout = loader.load();
            rootLayoutController controller = loader.getController();
            controller.setMainApp(this);

            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showLogin(){
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(MainApp.class.getResource("view/login.fxml"));
        try {
            BorderPane login = loader.load();
            rootLayout.setCenter(login);
            loginController controller = loader.getController();
            controller.setMainApp(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showProgramUI() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/ProgramUI.fxml"));
            TabPane programUI = loader.load();
            rootLayout.setCenter(programUI);
            ProgramUIController controller = loader.getController();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void showGuestUI(){
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(MainApp.class.getResource("view/prenotazioneCampo.fxml"));
        try {
            AnchorPane prenotazione = loader.load();
            rootLayout.setCenter(prenotazione);
            rootLayout.getTop().setVisible(true);
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
            controller.setResultStage(resultsStage);
            controller.setPrenotazione(prenotazione);
            controller.inserisciRisultatiTabella(lista);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}