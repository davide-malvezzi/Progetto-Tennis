package circolo.view;

import circolo.MainApp;
import javafx.fxml.FXML;
import javafx.scene.control.ToolBar;

/**
 * Classe che controlla il pannello radice dell'interfaccia.
 * <p>In particolare questa classe si occupa di gestire il pulsante di logout</p>
 */
public class rootLayoutController {
    private MainApp mainApp;
    @FXML
    private ToolBar logout;

    @FXML
    private void initialize(){
        logout.setVisible(false);
    }

    /**
     * Fa il logout e mostra la schermata di login
     */
    @FXML
    private void handleLogout(){
        mainApp.showLogin();
        logout.setVisible(false);
    }

    public void setMainApp(MainApp mainApp){
        this.mainApp = mainApp;
    }

}
