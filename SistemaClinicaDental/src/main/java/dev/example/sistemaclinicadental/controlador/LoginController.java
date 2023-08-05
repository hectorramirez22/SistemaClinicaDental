package dev.example.sistemaclinicadental.controlador;

import dev.example.sistemaclinicadental.App;
import dev.example.sistemaclinicadental.StageManager;
import dev.example.sistemaclinicadental.vista.componentes.AlertaComponent;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginController {
    
    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private void loginClicked() throws IOException {
        try{
            if ( usernameField.getText().isEmpty() ){
                AlertaComponent.mostrarAlertaInfo("Ïndique nombre de usuario");
                return;
            }

            if ( passwordField.getText().isEmpty() ){
                AlertaComponent.mostrarAlertaInfo("Ïndique la contraseña");
                return;
            }

            StageManager.getInstance().getPrimaryStage().setTitle("Inicio");
            App.setRoot("menu-principal");
        }catch(Exception ex){
            ex.printStackTrace();
            AlertaComponent.mostrarAlertaError(ex.getLocalizedMessage());
        }
    }
}
