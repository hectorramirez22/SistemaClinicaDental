package dev.example.sistemaclinicadental.controlador.cita;

import dev.example.sistemaclinicadental.App;
import dev.example.sistemaclinicadental.StageManager;
import dev.example.sistemaclinicadental.modelo.Paciente;
import dev.example.sistemaclinicadental.vista.componentes.AlertaComponent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.util.StringConverter;

public class RegistrarCitaController {
    
    @FXML
    private ChoiceBox<Paciente> pacienteChoiceBox;
    
    @FXML
    private ChoiceBox<Integer> horaAtentidoChoiceBox;

    @FXML
    private ChoiceBox<Integer> minutoAtentidoChoiceBox;
    
    @FXML
    private ChoiceBox<Integer> horaCompletadaChoiceBox;

    @FXML
    private ChoiceBox<Integer> minutoCompletadaChoiceBox;
    
    @FXML
    public void initialize() {
        for (int i = 0; i <= 23; i++) {
            horaAtentidoChoiceBox.getItems().add(i);
            horaCompletadaChoiceBox.getItems().add(i);
        }

        for (int i = 0; i <= 59; i++) {
            minutoAtentidoChoiceBox.getItems().add(i);
            minutoCompletadaChoiceBox.getItems().add(i);
        }
        
        llenarChoiceBoxPacientes();
    }
    
    @FXML
    private void cancelarRegistro() throws IOException {
        if ( AlertaComponent.solicitarConfirmacion("¿Deseas cancelar el registro?") ){
            StageManager.getInstance().getPrimaryStage().setTitle("Consultar cotas");
            App.setRoot("cita/consulta-citas");
        }
    }
    
    private void llenarChoiceBoxPacientes(){
        List<Paciente> pacientes = new ArrayList<>();

        pacientes.add(new Paciente(1L, "Luis Ramos", "12345678", new Date(92, 4, 7), "Masculino", 6, 1));
        pacientes.add(new Paciente(2L, "Francisco Perez", "39393939", new Date(90, 10, 12), "Masculino", 7, 1));
        pacientes.add(new Paciente(3L, "Antonela Gomez", "39393929", new Date(95, 8, 10), "Femenino", 9, 1));

        pacienteChoiceBox.getItems().addAll(pacientes);

        // Configurar StringConverter para mostrar el nombre y manejar la conversión
        pacienteChoiceBox.setConverter(new StringConverter<Paciente>() {
            @Override
            public String toString(Paciente paciente) {
                return paciente.getNombre();
            }

            @Override
            public Paciente fromString(String s) {
                return null; // No se necesita para esta implementación
            }
        });
    }
}
