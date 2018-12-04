package application.fxml;

import javafx.scene.control.Alert;
import javafx.stage.Window;

// help from https://www.callicoder.com/javafx-fxml-form-gui-tutorial/


public class AlertHelper {

    public static void showAlert(Alert.AlertType alertType, Window owner, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.initOwner(owner);
        alert.show();
    }
}
