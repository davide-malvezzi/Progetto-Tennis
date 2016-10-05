package circolo.util;

import javafx.scene.control.Alert;

public class AlertUtil {


    public static void displayGenericError(){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Errore");
        alert.setHeaderText("Si è verificato un errore interno");
        alert.setContentText("Riprova");
        alert.showAndWait();
    }

    public static void displayPersonalizedError(String header, String content){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Errore");
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    public static void displayPersonalizedInfo(String header, String content){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Operazione Eseguita");
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

}
