package circolo;

import java.sql.SQLException;
import java.util.LinkedList;

public class MatchPlay {
    private LinkedList<Giocatore> girone_A;
    private LinkedList<Giocatore> girone_B;
    private LinkedList<Giocatore> girone_C;
    private LinkedList<Giocatore> girone_D;

    public MatchPlay() {
        this.girone_A = new LinkedList<Giocatore>();
        this.girone_B = new LinkedList<Giocatore>();
        this.girone_C = new LinkedList<Giocatore>();
        this.girone_D = new LinkedList<Giocatore>();
    }

    public void GeneraGironi() throws SQLException {


        System.out.println("\nGirone A:\n");
        for (Giocatore aGirone_A : girone_A) System.out.println(aGirone_A.getNome() + " " + aGirone_A.getCognome() + " " + aGirone_A.getFascia());
        System.out.println("\nGirone B:\n");
        for (Giocatore aGirone_B : girone_B) System.out.println(aGirone_B.getNome() + " " + aGirone_B.getCognome() + " " + aGirone_B.getFascia());
        System.out.println("\nGirone C:\n");
        for (Giocatore aGirone_C : girone_C) System.out.println(aGirone_C.getNome() + " " + aGirone_C.getCognome() + " " + aGirone_C.getFascia());
        System.out.println("\nGirone D:\n");
        for (Giocatore aGirone_D : girone_D) System.out.println(aGirone_D.getNome() + " " + aGirone_D.getCognome() + " " + aGirone_D.getFascia());
        System.out.println("\nPartite Girone A:\n");
        this.GeneraPartite(girone_A);
        System.out.println("\nPartite Girone B:\n");
        this.GeneraPartite(girone_B);
        System.out.println("\nPartite Girone C:\n");
        this.GeneraPartite(girone_C);
        System.out.println("\nPartite Girone D:\n");
        this.GeneraPartite(girone_D);
    }

    private void GeneraPartite(LinkedList<Giocatore> giocatori) {
        int num_giocatori = giocatori.size();
        int num_giornate = num_giocatori - 1;
        int num_partite = num_giocatori / 2;

        for (int i = 1; i <= num_giornate; i++) {
            System.out.println(i + "° turno:");

            if (num_giocatori % 2 == 0)
                System.out.println(giocatori.get(num_giocatori - 2).getNome() + " " + giocatori.get(num_giocatori - 2).getCognome() + " - " + giocatori.get(num_giocatori - 1).getNome() + " " + giocatori.get(num_giocatori - 1).getCognome());
            for (int j = 0; j < num_partite - 1 + (num_giocatori % 2); j++) {
                System.out.println(giocatori.get(j).getNome() + " " + giocatori.get(j).getCognome() + " - " + giocatori.get(num_giocatori - 3 - j + (num_giocatori % 2)).getNome() + " " +giocatori.get(num_giocatori - 3 - j + (num_giocatori % 2)).getCognome() );
            }
            if (num_giocatori % 2 == 1)
                System.out.println("\nRiposa: " + giocatori.getLast().getNome() + " " + giocatori.getLast().getCognome() );

            giocatori.addFirst(giocatori.getLast());
            giocatori.removeLast();
            System.out.println();
        }
    }


}