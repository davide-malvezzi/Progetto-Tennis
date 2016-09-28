package circolo;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.SQLException;

public class Main {
    public static void main(String args[]) throws SQLException {
        ObservableList<ObservableList<Giocatore>> partecipanti = FXCollections.observableArrayList();
        for(int i =0; i<5;i++)
            partecipanti.add(FXCollections.observableArrayList());
        try {
            Database db = new Database();
            partecipanti = db.generaGironiMatchPlay();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }


    }

}
