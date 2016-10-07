package circolo.view;

import circolo.Giocatore;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class gironiMatchplayController {
    @FXML
    private TableView<String> table;
    @FXML
    private TableColumn<Giocatore,String> gironeA;
    @FXML
    private TableColumn<Giocatore,String> gironeB;
    @FXML
    private TableColumn<Giocatore,String> gironeC;
    @FXML
    private TableColumn<Giocatore,String> gironeD;
    @FXML
    private Button modifica;
    @FXML
    private Button creaGironi;

    @FXML
    private void initialize(){



    }



}
