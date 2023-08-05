package dev.example.sistemaclinicadental.controlador.doctor;

import dev.example.sistemaclinicadental.App;
import dev.example.sistemaclinicadental.StageManager;
import dev.example.sistemaclinicadental.modelo.Doctor;
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

public class ConsultaDoctoresController {
    @FXML
    private TableView<Doctor> tableViewDoctores;
    @FXML
    private TableColumn<Doctor, Long> columnId;
    @FXML
    private TableColumn<Doctor, String> columnNombre;
    @FXML
    private TableColumn<Doctor, String> columnTelefono;
    @FXML
    private TableColumn<Doctor, String> columnEspecialidad;
    @FXML
    private TableColumn<Doctor, String> columnEstado;
    @FXML
    private ProgressBar progressBar;

    // Variable para la tarea de consulta en segundo plano
    private Task<List<Doctor>> consultaTask;
    
    private List<Doctor> doctores = new ArrayList<>();
    
    @FXML
    private void initialize() {
        llenarTablaDoctores();
    }
    
    @FXML
    private void nuevoDoctorClicked() throws IOException {
        StageManager.getInstance().getPrimaryStage().setTitle("Nuevo doctor");
        App.setRoot("doctor/registrar-doctor");
    }
    
    @FXML
    private void consultarDoctoresClicked() throws IOException {
        llenarTablaDoctores();
    }

    private void llenarTablaDoctores() {
        columnId.setCellValueFactory(cellData -> new SimpleLongProperty(cellData.getValue().getId()).asObject());
        columnNombre.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNombre()));
        columnTelefono.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTelefono()));
        columnEspecialidad.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEspecialidad()));
        columnEstado.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEstadoNombre()));

        try {
            tableViewDoctores.getItems().clear();
            
            tableViewDoctores.setVisible(false);
            
            // Cancelar una consulta en curso si existe
            if (consultaTask != null && consultaTask.isRunning()) {
                consultaTask.cancel();
            }
            
            progressBar.setVisible(true);
            
            // Crea una nueva tarea (Task) para la consulta en segundo plano
            consultaTask = new Task<List<Doctor>>() {
                @Override
                protected List<Doctor> call() throws Exception {
                    
                    int totalDoctores = obtenerDoctores().size();
                    for (int i = 0; i < totalDoctores; i++) {
                        Thread.sleep(100);
                        updateProgress(i + 1, totalDoctores);
                    }
                    
                    return obtenerDoctores();
                }
            };
            
            progressBar.progressProperty().bind(consultaTask.progressProperty());
            //progressBar.visibleProperty().bind(consultaTask.runningProperty());

            // Configura la acción a realizar cuando la tarea se completa
            consultaTask.setOnSucceeded(event -> {
                // Oculta el ProgressBar y muestra la tabla nuevamente
                progressBar.setVisible(false);
                tableViewDoctores.setVisible(true);

                // Obtiene el resultado de la consulta y agrega los nuevos usuarios a la tabla
                List<Doctor> nuevosDoctores = consultaTask.getValue();
                tableViewDoctores.getItems().addAll(nuevosDoctores);
                
                // Agregar menú contextual a la tabla para editar y eliminar
                ContextMenu contextMenu = new ContextMenu();
                MenuItem menuItemEditar = new MenuItem("Editar");
                MenuItem menuItemEliminar = new MenuItem("Eliminar");

                menuItemEditar.setOnAction(eventMenu -> {
                    Doctor doctor = tableViewDoctores.getSelectionModel().getSelectedItem();
                    System.out.println("Editar doctor: " + doctor.getNombre());
                });

                menuItemEliminar.setOnAction(eventMenu -> {
                    Doctor doctor = tableViewDoctores.getSelectionModel().getSelectedItem();
                    System.out.println("Eliminar doctor: " + doctor.getNombre());
                    
                    eliminarDoctor(doctor);
                });

                contextMenu.getItems().addAll(menuItemEditar, menuItemEliminar);

                tableViewDoctores.setContextMenu(contextMenu);
            });

            // Configura la acción a realizar cuando la tarea se cancela o falla
            consultaTask.setOnCancelled(event -> {
                // Oculta el ProgressBar y muestra la tabla nuevamente
                progressBar.setVisible(false);
                tableViewDoctores.setVisible(true);
            });

            // Inicia la tarea en un hilo separado
            Thread thread = new Thread(consultaTask);
            thread.start();
        } catch (Exception e) {
            e.printStackTrace();
            AlertaComponent.mostrarAlertaError(e.getLocalizedMessage());
        }
    }
    
    private List<Doctor> obtenerDoctores(){
        doctores = new ArrayList<>();

        doctores.add(new Doctor(1L, "Roberto Gomez", "12345678", "Odontologia", new Date(1992, 4, 7), 1));
        doctores.add(new Doctor(2L, "Jose Reyes", "39393939", "Clinica General", new Date(1990, 10, 12), 1));
        doctores.add(new Doctor(3L, "Andrea Lemus", "39393929", "Enfermera", new Date(1995, 8, 10), 1));
        doctores.add(new Doctor(4L, "Fernanda Torres", "29384939", "Doctora", new Date(1992, 10, 4), 1));

        return doctores;
    }

    private void eliminarDoctor(Doctor doctor) {
        if ( !AlertaComponent.solicitarConfirmacion("¿Deseas eliminar a " + doctor.getNombre() + "?") ){
            return;
        }
        
        int positionEliminar = -1;
        
        for(int i = 0; i < doctores.size(); i++){
            if ( Objects.equals(doctor.getId(), doctores.get(i).getId()) ){
                positionEliminar = i;
                break;
            }
        }
        
        if ( positionEliminar != -1 ){
            doctores.remove(positionEliminar);
            llenarTablaDoctores();
            
            AlertaComponent.mostrarAlertaInfo("Doctor eliminado correctamente.");
        }
    }
}
