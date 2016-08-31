package circolo.view;

import circolo.Giocatore;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class SearchResultsController {
    @FXML
    private TableView<Giocatore> table;
    @FXML
    private TableColumn<Giocatore,String> nome;
    @FXML
    private TableColumn<Giocatore,String> cognome;
    @FXML
    private TableColumn<Giocatore,String> data_nascita;
    @FXML
    private TableColumn<Giocatore,String> CF;
    @FXML
    private TableColumn<Giocatore,String> sesso;
    @FXML
    private TableColumn<Giocatore,String> indirizzo;
    @FXML
    private TableColumn<Giocatore,String> classifica_fit;
    @FXML
    private TableColumn<Giocatore,String> fascia;
    @FXML
    private TableColumn<Giocatore,Integer> agonista;
    @FXML
    private TableColumn<Giocatore,Integer> socio;

    private Giocatore giocatore;

    public SearchResultsController(){}

    @FXML
    private void initialize(){}

    public void inserisciRisultatiTabella(ObservableList<Giocatore> lista){
        nome.setCellValueFactory(cellData -> cellData.getValue().getNomeProperty());
        cognome.setCellValueFactory(cellData -> cellData.getValue().getCognomeProperty());
        data_nascita.setCellValueFactory(cellData -> cellData.getValue().getData_NascitaProperty());
        CF.setCellValueFactory(cellData -> cellData.getValue().getCFProperty());
        sesso.setCellValueFactory(cellData -> cellData.getValue().getGenereProperty());
        indirizzo.setCellValueFactory(cellData -> cellData.getValue().getIndirizzoProperty());
        classifica_fit.setCellValueFactory(cellData -> cellData.getValue().getClassifica_FITProperty());
        agonista.setCellValueFactory(new PropertyValueFactory<>("agonista"));
        fascia.setCellValueFactory(cellData -> cellData.getValue().getFasciaProperty());
        socio.setCellValueFactory(new PropertyValueFactory<>("socio"));
        table.setItems(lista);
    }
}