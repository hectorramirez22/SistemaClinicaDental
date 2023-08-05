package dev.example.sistemaclinicadental.controlador;

import dev.example.sistemaclinicadental.App;
import dev.example.sistemaclinicadental.StageManager;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.util.Duration;

public class MenuPrincipalController {
    
    @FXML
    private Button consultarCitasButton;
    
    @FXML
    private Button consultarDoctoresButton;
    
    @FXML
    private Button consultarPacientesButton;
    
    @FXML
    private Button consultarUuariosButton;
    
    @FXML
    private Label relojLabel;
    
    @FXML
    private void initialize() {
        Tooltip tooltip = new Tooltip("Click para ir a consultar citas.");
        consultarCitasButton.setTooltip(tooltip);
        
        tooltip = new Tooltip("Click para ir a consultar doctores.");
        consultarDoctoresButton.setTooltip(tooltip);
        
        tooltip = new Tooltip("Click para ir a consultar pacientes.");
        consultarPacientesButton.setTooltip(tooltip);
        
        tooltip = new Tooltip("Click para ir a consultar usuarios.");
        consultarUuariosButton.setTooltip(tooltip);
        
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            actualizarReloj();
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    @FXML
    private void consultaCitasClicked() throws IOException {
        StageManager.getInstance().getPrimaryStage().setTitle("Consultar citas");
        App.setRoot("cita/consulta-citas");
    }

    @FXML
    private void consultaPacientesClicked() throws IOException {
        StageManager.getInstance().getPrimaryStage().setTitle("Consultar pacientes");
        App.setRoot("paciente/consulta-pacientes");
    }

    @FXML
    private void consultaDoctoresClicked() throws IOException {
        StageManager.getInstance().getPrimaryStage().setTitle("Consultar doctores");
        App.setRoot("doctor/consulta-doctores");
    }
    
    @FXML
    private void consultaUsuariosClicked() throws IOException {
        StageManager.getInstance().getPrimaryStage().setTitle("Consultar usuarios");
        App.setRoot("usuario/consulta-usuarios");
    }

    private void actualizarReloj() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
        String horaActual = dateFormat.format(new Date());
        relojLabel.setText("Reloj: " + horaActual);
    }
}