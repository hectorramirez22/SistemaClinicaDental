package dev.example.sistemaclinicadental.vista.componentes;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;

public class AlertaComponent {
    
    public static void mostrarAlertaInfo( String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Informacion");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);

        alert.showAndWait();
    }
    
    public static void mostrarAlertaError( String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);

        alert.showAndWait();
    }
    
    public static boolean solicitarConfirmacion(String mensaje) {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Confirmación");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);

        // Personalizar los botones del diálogo de confirmación
        ButtonType buttonSi = new ButtonType("Sí");
        ButtonType buttonNo = new ButtonType("No");

        alert.getButtonTypes().setAll(buttonSi, buttonNo);

        // Mostrar el diálogo y esperar a que el usuario elija una opción
        alert.showAndWait();

        // Devolver true si el usuario elige "Sí", y false si elige "No"
        return alert.getResult() == buttonSi;
    }
}
