package dev.example.sistemaclinicadental.controlador.paciente;

import dev.example.sistemaclinicadental.App;
import dev.example.sistemaclinicadental.StageManager;
import dev.example.sistemaclinicadental.modelo.Paciente;
import dev.example.sistemaclinicadental.vista.componentes.AlertaComponent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class ConsultaPacientesController {
    @FXML
    private TableView<Paciente> tableViewPacientes;
    @FXML
    private TableColumn<Paciente, Long> columnId;
    @FXML
    private TableColumn<Paciente, String> columnNombre;
    @FXML
    private TableColumn<Paciente, String> columnTelefono;
    @FXML
    private TableColumn<Paciente, String> columnGenero;
    @FXML
    private TableColumn<Paciente, String> columnEdad;
    @FXML
    private TableColumn<Paciente, String> columnNivelCompromiso;
    @FXML
    private TableColumn<Paciente, String> columnEstado;
    @FXML
    private ProgressBar progressBar;

    // Variable para la tarea de consulta en segundo plano
    private Task<List<Paciente>> consultaTask;
    
    private List<Paciente> pacientes = new ArrayList<>();
    
    @FXML
    private void initialize() {
        llenarTablaPacientes();
    }
    
    @FXML
    private void nuevoPacienteClicked() throws IOException {
        StageManager.getInstance().getPrimaryStage().setTitle("Nuevo paciente");
        App.setRoot("paciente/registrar-paciente");
    }
    
    @FXML
    private void consultarPacientesClicked() throws IOException {
        llenarTablaPacientes();
    }

    private void llenarTablaPacientes() {
        columnId.setCellValueFactory(cellData -> new SimpleLongProperty(cellData.getValue().getId()).asObject());
        columnNombre.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNombre()));
        columnTelefono.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTelefono()));
        columnGenero.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getGenero()));
        columnEdad.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().calcularEdad()));
        columnNivelCompromiso.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getCompromisoCliente())));
        columnEstado.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEstadoNombre()));

        try {
            tableViewPacientes.getItems().clear();
            
            tableViewPacientes.setVisible(false);
            
            // Cancelar una consulta en curso si existe
            if (consultaTask != null && consultaTask.isRunning()) {
                consultaTask.cancel();
            }
            
            progressBar.setVisible(true);
            
            // Crea una nueva tarea (Task) para la consulta en segundo plano
            consultaTask = new Task<List<Paciente>>() {
                @Override
                protected List<Paciente> call() throws Exception {
                    
                    int totalPacientes = obtenerPacientes().size();
                    for (int i = 0; i < totalPacientes; i++) {
                        Thread.sleep(100);
                        updateProgress(i + 1, totalPacientes);
                    }
                    
                    return obtenerPacientes();
                }
            };
            
            progressBar.progressProperty().bind(consultaTask.progressProperty());
            //progressBar.visibleProperty().bind(consultaTask.runningProperty());

            // Configura la acción a realizar cuando la tarea se completa
            consultaTask.setOnSucceeded(event -> {
                // Oculta el ProgressBar y muestra la tabla nuevamente
                progressBar.setVisible(false);
                tableViewPacientes.setVisible(true);

                // Obtiene el resultado de la consulta y agrega los nuevos usuarios a la tabla
                List<Paciente> nuevosPacientes = consultaTask.getValue();
                tableViewPacientes.getItems().addAll(nuevosPacientes);
                
                // Agregar menú contextual a la tabla para editar y eliminar
                ContextMenu contextMenu = new ContextMenu();
                MenuItem menuItemEditar = new MenuItem("Editar");
                MenuItem menuItemEliminar = new MenuItem("Eliminar");

                menuItemEditar.setOnAction(eventMenu -> {
                    Paciente paciente = tableViewPacientes.getSelectionModel().getSelectedItem();
                    System.out.println("Editar paciente: " + paciente.getNombre());
                });

                menuItemEliminar.setOnAction(eventMenu -> {
                    Paciente paciente = tableViewPacientes.getSelectionModel().getSelectedItem();
                    System.out.println("Eliminar paciente: " + paciente.getNombre());
                    
                    eliminarPaciente(paciente);
                });

                contextMenu.getItems().addAll(menuItemEditar, menuItemEliminar);

                tableViewPacientes.setContextMenu(contextMenu);
            });

            // Configura la acción a realizar cuando la tarea se cancela o falla
            consultaTask.setOnCancelled(event -> {
                // Oculta el ProgressBar y muestra la tabla nuevamente
                progressBar.setVisible(false);
                tableViewPacientes.setVisible(true);
            });

            // Inicia la tarea en un hilo separado
            Thread thread = new Thread(consultaTask);
            thread.start();
        } catch (Exception e) {
            e.printStackTrace();
            AlertaComponent.mostrarAlertaError(e.getLocalizedMessage());
        }
    }
    
    private List<Paciente> obtenerPacientes(){
        pacientes = new ArrayList<>();

        pacientes.add(new Paciente(1L, "Luis Ramos", "12345678", new Date(92, 4, 7), "Masculino", 6, 1));
        pacientes.add(new Paciente(2L, "Francisco Perez", "39393939", new Date(90, 10, 12), "Masculino", 7, 1));
        pacientes.add(new Paciente(3L, "Antonela Gomez", "39393929", new Date(95, 8, 10), "Femenino", 9, 1));

        return pacientes;
    }

    private void eliminarPaciente(Paciente paciente) {
        if ( !AlertaComponent.solicitarConfirmacion("¿Deseas eliminar a " + paciente.getNombre() + "?") ){
            return;
        }
        
        int positionEliminar = -1;
        
        for(int i = 0; i < pacientes.size(); i++){
            if ( Objects.equals(paciente.getId(), pacientes.get(i).getId()) ){
                positionEliminar = i;
                break;
            }
        }
        
        if ( positionEliminar != -1 ){
            pacientes.remove(positionEliminar);
            llenarTablaPacientes();
            
            AlertaComponent.mostrarAlertaInfo("Paciente eliminado correctamente.");
        }
    }
}
