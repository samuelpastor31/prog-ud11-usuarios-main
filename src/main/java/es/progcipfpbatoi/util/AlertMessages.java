package es.progcipfpbatoi.util;

import javafx.scene.control.Alert;

public class AlertMessages {
    public static void mostrarAlertError(String msg) {
        mostrarAlert("Error", msg, Alert.AlertType.ERROR);
    }

    public static void mostrarAlertWarning(String msg) {
        mostrarAlert("Aviso", msg, Alert.AlertType.WARNING);
    }

    public static void mostrarAlertInformacion(String msg) {
        mostrarAlert("Información", msg, Alert.AlertType.INFORMATION);
    }

    private static void mostrarAlert(String title, String msg, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setHeaderText(null);
        alert.setTitle(title);
        alert.setContentText(msg);
        alert.showAndWait();
    }
}
