package dev.example.sistemaclinicadental.controlador;

import dev.example.sistemaclinicadental.App;
import dev.example.sistemaclinicadental.StageManager;
import dev.example.sistemaclinicadental.vista.componentes.AlertaComponent;
import java.io.IOException;
import javafx.application.Platform;
import javafx.fxml.FXML;

public class ComponentMenuPrincipalController {
    
    @FXML
    private void menuPrincipalClicked() throws IOException {
        App.setRoot("menu-principal");
    }
    
    @FXML
    private void cerrarSesionClicked() throws IOException {
        if ( AlertaComponent.solicitarConfirmacion("¿Estás seguro de que deseas cerrar sesión?") ){
            App.setRoot("login");
        }
    }
    
    @FXML
    private void cerrarAplicacionClicked() throws IOException {
        if ( AlertaComponent.solicitarConfirmacion("¿Estás seguro de que deseas cerrar la aplicación?") ){
            Platform.exit();
        }
    }
    
    ////////////////////////////////////////////////////////////////
    // Citas
    ////////////////////////////////////////////////////////////////
    @FXML
    private void nuevaCitaClicked() throws IOException {
        StageManager.getInstance().getPrimaryStage().setTitle("Nueva cita");
        App.setRoot("cita/registrar-cita");
    }
    
    @FXML
    private void consultarCitasAgendadasClicked() throws IOException {
        StageManager.getInstance().getPrimaryStage().setTitle("Consultar citas");
        App.setRoot("cita/consulta-citas");
    }
    
    ////////////////////////////////////////////////////////////////
    // Pacientes
    ////////////////////////////////////////////////////////////////
    
    @FXML
    private void consultarPacientesClicked() throws IOException {
        StageManager.getInstance().getPrimaryStage().setTitle("Consultar pacientes");
        App.setRoot("paciente/consulta-pacientes");
    }
    
    @FXML
    private void nuevoPacienteClicked() throws IOException {
        StageManager.getInstance().getPrimaryStage().setTitle("Nuevo paciente");
        App.setRoot("paciente/registrar-paciente");
    }
    
    ////////////////////////////////////////////////////////////////
    // Doctores
    ////////////////////////////////////////////////////////////////
    
    @FXML
    private void consultarDoctoresClicked() throws IOException {
        StageManager.getInstance().getPrimaryStage().setTitle("Consultar doctores");
        App.setRoot("doctor/consulta-doctores");
    }
    
    @FXML
    private void nuevoDoctorClicked() throws IOException {
        StageManager.getInstance().getPrimaryStage().setTitle("Nuevo doctor");
        App.setRoot("doctor/registrar-doctor");
    }
    
    ////////////////////////////////////////////////////////////////
    // Usuario
    ////////////////////////////////////////////////////////////////
    
    @FXML
    private void consultarUsuariosClicked() throws IOException {
        StageManager.getInstance().getPrimaryStage().setTitle("Consultar usuarios");
        App.setRoot("usuario/consulta-usuarios");
    }
    
    @FXML
    private void nuevoUsuarioClicked() throws IOException {
        StageManager.getInstance().getPrimaryStage().setTitle("Nuevo usuario");
        App.setRoot("usuario/registrar-usuario");
    }
}
