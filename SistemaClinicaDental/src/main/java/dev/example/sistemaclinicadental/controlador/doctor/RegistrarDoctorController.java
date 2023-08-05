package dev.example.sistemaclinicadental.controlador.doctor;

import dev.example.sistemaclinicadental.App;
import dev.example.sistemaclinicadental.StageManager;
import dev.example.sistemaclinicadental.vista.componentes.AlertaComponent;
import dev.example.sistemaclinicadental.vista.componentes.AutoCompleteTextField;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

public class RegistrarDoctorController {
    @FXML
    private TextField nombreField;

    @FXML
    private TextField telefonoField;

    @FXML
    private AutoCompleteTextField especialidadCombo;
    
    @FXML
    private DatePicker fechaNacimientoDatePicker;

    @FXML
    private void initialize() {
        especialidadCombo.getEntries().add("Odontologia");
        especialidadCombo.getEntries().add("Cirugia estetica");
    }
    
    @FXML
    private void registrarUsuario() {
        String nombre = nombreField.getText();
        String telefono = telefonoField.getText();
        String especialidad = especialidadCombo.getText();
    }

    @FXML
    private void cancelarRegistro() throws IOException {
        if ( AlertaComponent.solicitarConfirmacion("¿Deseas cancelar el registro?") ){
            StageManager.getInstance().getPrimaryStage().setTitle("Consultar doctores");
            App.setRoot("doctor/consulta-doctores");
        }
    }
}
