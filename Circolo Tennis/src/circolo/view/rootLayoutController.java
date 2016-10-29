package circolo.view;

import circolo.MainApp;
import javafx.fxml.FXML;
import javafx.scene.control.ToolBar;

public class rootLayoutController {
    private MainApp mainApp;
    @FXML
    private ToolBar logout;

    @FXML
    private void initialize(){
        logout.setVisible(false);
    }

    @FXML
    private void handleLogout(){
        mainApp.showLogin();
        logout.setVisible(false);
    }

    public void setMainApp(MainApp mainApp){
        this.mainApp = mainApp;
    }

}
