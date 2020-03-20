package otros;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class ErrorV {
    public ErrorV(String errorS){
        Alert errorAlert = new Alert(AlertType.ERROR);
        errorAlert.setHeaderText("Error:");
        errorAlert.setContentText(errorS);
        errorAlert.showAndWait();
    }
}