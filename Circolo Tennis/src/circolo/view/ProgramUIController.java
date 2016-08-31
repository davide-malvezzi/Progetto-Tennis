package circolo.view;

import circolo.MainApp;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.BorderPane;

import java.io.IOException;

public class ProgramUIController {
    @FXML
    private Button Nuovo;
    @FXML
    private Button Ricerca;

    @FXML
    private SplitPane split;

    MainApp mainApp;

    public ProgramUIController() {
    }

    @FXML
    private void initialize() {
    }

    @FXML
    private void inserisciGiocatore() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(MainApp.class.getResource("view/newGiocatore.fxml"));
        BorderPane nuovo =  loader.load();
        clearPane();
        split.getItems().add(nuovo);
    }

    @FXML
    private void ricercaGiocatore() throws IOException{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(MainApp.class.getResource("view/ricercaGiocatore.fxml"));
        BorderPane ricerca =  loader.load();
        clearPane();
        split.getItems().add(ricerca);
        ricercaGiocatoreController controller = loader.getController();
        controller.setMainApp(mainApp);
    }

    private void clearPane(){
        if (split.getItems().size() != 1) split.getItems().remove(1);
    }

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }

}
