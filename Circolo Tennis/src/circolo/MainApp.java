package circolo;

import circolo.view.ProgramUIController;
import circolo.view.SearchResultsController;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;

public class MainApp extends Application {
    private Stage primaryStage;
    private BorderPane rootLayout;
    private TabPane ProgramUI;


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Tennis Club San Felice");

        initRootLayout();

        showProgramUI();

    }

    public void initRootLayout() {
        try {
            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/rootLayout.fxml"));
            rootLayout = (BorderPane) loader.load();

            // Show the scene containing the root layout.
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showProgramUI() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/ProgramUI.fxml"));
            this.ProgramUI = (TabPane) loader.load();
            rootLayout.setCenter(ProgramUI);
            ProgramUIController controller = loader.getController();
            controller.setMainApp(this);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void showSearchResults(ObservableList<Giocatore> lista) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/SearchResults.fxml"));
            AnchorPane pane = loader.load();
            Stage resultsStage = new Stage();
            resultsStage.setTitle("Risultati di ricerca");
            resultsStage.initOwner(primaryStage);
            Scene scene = new Scene(pane);
            resultsStage.setScene(scene);
            resultsStage.show();
            SearchResultsController controller = loader.getController();
            controller.inserisciRisultatiTabella(lista);
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }
}
