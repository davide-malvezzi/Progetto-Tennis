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
            partecipanti = db.mostraGironi();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        System.out.println("\nGirone A:\n");
        for (int i =0;i<partecipanti.get(0).size();i++) System.out.println(partecipanti.get(0).get(i).getNome() + " " + partecipanti.get(0).get(i).getCognome() + " " + partecipanti.get(0).get(i).getFascia());
        System.out.println("\nGirone B:\n");
        for (int i =0;i<partecipanti.get(1).size();i++) System.out.println(partecipanti.get(1).get(i).getNome() + " " + partecipanti.get(1).get(i).getCognome() + " " + partecipanti.get(1).get(i).getFascia());
        System.out.println("\nGirone C:\n");
        for (int i =0;i<partecipanti.get(2).size();i++) System.out.println(partecipanti.get(2).get(i).getNome() + " " + partecipanti.get(2).get(i).getCognome() + " " + partecipanti.get(2).get(i).getFascia());
        System.out.println("\nGirone D:\n");
        for (int i =0;i<partecipanti.get(3).size();i++) System.out.println(partecipanti.get(3).get(i).getNome() + " " + partecipanti.get(3).get(i).getCognome() + " " + partecipanti.get(3).get(i).getFascia());
    }

}
