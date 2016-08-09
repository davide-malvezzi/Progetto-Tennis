package circolo;

import java.util.LinkedList;

public class MatchPlay {
    LinkedList<String> girone_A;
    LinkedList<String> girone_B;
    LinkedList<String> girone_C;
    LinkedList<String> girone_D;

    public  MatchPlay() {
        this.girone_A = new LinkedList<String>();
        this.girone_B = new LinkedList<String>();
        this.girone_C = new LinkedList<String>();
        this.girone_D = new LinkedList<String>();
    }

    private void GeneraGironi(LinkedList<String> giocatori) {
        //questo sistema inserisce prima tutti i giocatori di fascia 5, poi fascia 4 e così via..
        do {
            if (giocatori.peek() != null)
                girone_A.add(giocatori.poll());
            if (giocatori.peek() != null)
                girone_B.add(giocatori.poll());
            if (giocatori.peek() != null)
                girone_C.add(giocatori.poll());
            if (giocatori.peek() != null)
                girone_D.add(giocatori.poll());
        } while (giocatori.peek() != null);

        System.out.println("\nGirone A:\n");
                girone_A.forEach(System.out::println);
                System.out.println("\nGirone B:\n");
                girone_B.forEach(System.out::println);
                System.out.println("\nGirone C:\n");
                girone_C.forEach(System.out::println);
                System.out.println("\nGirone D:\n");
                girone_D.forEach(System.out::println);
                System.out.println("\nPartite Girone A:\n");
                this.GeneraPartite(girone_A);
                System.out.println("\nPartite Girone B:\n");
                this.GeneraPartite(girone_B);
                System.out.println("\nPartite Girone C:\n");
                this.GeneraPartite(girone_C);
                System.out.println("\nPartite Girone D:\n");
                this.GeneraPartite(girone_D);
                }

private void GeneraPartite(LinkedList<String> giocatori) {
        int num_giocatori = giocatori.size();
        int num_giornate = num_giocatori - 1;
        int num_partite = num_giocatori / 2;

        for (int i = 1; i <= num_giornate; i++) {
        System.out.println(i + "° turno:");

        if (num_giocatori % 2 == 0)
        System.out.println(giocatori.get(num_giocatori - 2) + " - " + giocatori.get(num_giocatori - 1));
        for (int j = 0; j < num_partite - 1 + (num_giocatori % 2); j++) {
        System.out.println(giocatori.get(j) + " - " + giocatori.get(num_giocatori - 3 - j + (num_giocatori % 2)));
        }
        if (num_giocatori % 2 == 1)
        System.out.println("Riposa: " + giocatori.getLast());

        giocatori.addFirst(giocatori.getLast());
        giocatori.removeLast();
        System.out.println();
        }
        }


}