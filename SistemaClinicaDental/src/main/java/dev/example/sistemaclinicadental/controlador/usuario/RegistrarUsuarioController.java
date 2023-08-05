package dev.example.sistemaclinicadental.controlador.usuario;

import dev.example.sistemaclinicadental.App;
import dev.example.sistemaclinicadental.StageManager;
import dev.example.sistemaclinicadental.vista.componentes.AlertaComponent;
import java.io.IOException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

public class RegistrarUsuarioController {
    @FXML
    private TextField nombreField;

    @FXML
    private TextField telefonoField;

    @FXML
    private ChoiceBox<String> generoChoiceBox;
    
    private ObservableList<String> generoOptions = FXCollections.observableArrayList("Masculino", "Femenino");

    @FXML
    private void initialize() {
        // Inicializar las opciones del ChoiceBox
        generoChoiceBox.setItems(generoOptions);
    }
    
    @FXML
    private void registrarUsuario() {
        String nombre = nombreField.getText();
        String telefono = telefonoField.getText();
        String genero = generoChoiceBox.getValue();
    }

    @FXML
    private void cancelarRegistro() throws IOException {
        if ( AlertaComponent.solicitarConfirmacion("¿Deseas cancelar el registro?") ){
            StageManager.getInstance().getPrimaryStage().setTitle("Consultar usuarios");
            App.setRoot("usuario/consulta-usuarios");
        }
    }
}
