package circolo.view;

import circolo.Database;
import circolo.Giocatore;
import circolo.util.AlertUtil;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;

import java.sql.SQLException;

public class nuovoPartecipanteMatchplayController {
    @FXML
    private TextField nome;
    @FXML
    private TextField cognome;
    @FXML
    private BorderPane pane;
    @FXML
    private Button iscriviButton;
    private Database db;
    private Giocatore giocatore = new Giocatore();

    public nuovoPartecipanteMatchplayController(){}

    @FXML
    private void initialize(){
    }


    @FXML
    private void handleCerca(){
        if (isInputValid()){
            Giocatore giocatore = new Giocatore();
            ObservableList<Giocatore> lista;
            giocatore.setNome(nome.getText());
            giocatore.setCognome(cognome.getText());
            try {
                lista = db.ricercaGiocatore(giocatore);
                IscrizionePartecipante(lista);
            } catch (SQLException e) {
                e.printStackTrace();
                AlertUtil.displayGenericError();
            }
        }

    }


    private  void IscrizionePartecipante(ObservableList<Giocatore> lista){
        TableView<Giocatore> table = new TableView<Giocatore>();
        TableColumn<Giocatore,String> nome = new TableColumn<>();
        TableColumn<Giocatore,String> cognome = new TableColumn<>();
        TableColumn<Giocatore,String> cf = new TableColumn<>();
        TableColumn<Giocatore,String> citta = new TableColumn<>();
        TableColumn<Giocatore,String> indirizzo = new TableColumn<>();

        nome.setText("Nome");
        cognome.setText("Cognome");
        cf.setText("Codice Fiscale");
        citta.setText("Città");
        indirizzo.setText("Indirizzo");

        table.getColumns().addAll(nome,cognome,cf,citta,indirizzo);

        nome.setCellValueFactory(cellData -> cellData.getValue().getNomeProperty());
        cognome.setCellValueFactory(cellData -> cellData.getValue().getCognomeProperty());
        cf.setCellValueFactory(cellData -> cellData.getValue().getCFProperty());
        citta.setCellValueFactory(cellData -> cellData.getValue().getCittaProperty());
        indirizzo.setCellValueFactory(cellData -> cellData.getValue().getIndirizzoProperty());

        table.setItems(lista);
        table.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> Selezione(newValue));
        pane.setCenter(table);


    }

    @FXML
    private void handleIscrivi(){
        try {
            db.InserisciPartecipante_MatchPlay(giocatore);
            AlertUtil.displayPersonalizedInfo("Operazione Eseguita","Giocatore iscritto");
        } catch (SQLException e) {
            e.printStackTrace();
            AlertUtil.displayPersonalizedError("Giocatore già iscritto", null);
        }
    }

    private void Selezione(Giocatore giocatore){
        iscriviButton.setDisable(false);
        this.giocatore = giocatore;
    }

    private boolean isInputValid(){
        return nome.getText().length() > 0 || cognome.getText().length() > 0;
    }


    public void setDatabase(Database db){
        this.db = db;
    }


}