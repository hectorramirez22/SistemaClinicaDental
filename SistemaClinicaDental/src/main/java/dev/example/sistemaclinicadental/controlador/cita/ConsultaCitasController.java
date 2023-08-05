package dev.example.sistemaclinicadental.controlador.cita;

import dev.example.sistemaclinicadental.App;
import dev.example.sistemaclinicadental.StageManager;
import dev.example.sistemaclinicadental.modelo.Cita;
import dev.example.sistemaclinicadental.vista.componentes.AlertaComponent;
import java.io.IOException;
import java.sql.Time;
import java.time.LocalDateTime;
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

public class ConsultaCitasController {
    @FXML
    private TableView<Cita> tableViewCitas;
    @FXML
    private TableColumn<Cita, Long> columnId;
    @FXML
    private TableColumn<Cita, String> columnNombrePaciente;
    @FXML
    private TableColumn<Cita, String> columnFechaAgendada;
    @FXML
    private TableColumn<Cita, String> columnFechaAtendida;
    @FXML
    private TableColumn<Cita, String> columnHoraAtendida;
    @FXML
    private TableColumn<Cita, String> columnHoraCompletada;
    @FXML
    private TableColumn<Cita, String> columnEstado;
    @FXML
    private ProgressBar progressBar;

    // Variable para la tarea de consulta en segundo plano
    private Task<List<Cita>> consultaTask;
    
    private List<Cita> citas = new ArrayList<>();
    
    @FXML
    private void initialize() {
        llenarTablaCitas();
    }
    
    @FXML
    private void progrmarNuevaCitaClicked() throws IOException {
        StageManager.getInstance().getPrimaryStage().setTitle("Nueva cita");
        App.setRoot("cita/registrar-cita");
    }
    
    @FXML
    private void consultarCitasClicked() throws IOException {
        llenarTablaCitas();
    }

    private void llenarTablaCitas() {
        columnId.setCellValueFactory(cellData -> new SimpleLongProperty(cellData.getValue().getId()).asObject());
        columnNombrePaciente.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNombrePaciente()));
        columnFechaAgendada.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getFechaProgramada().toString()));
        columnFechaAtendida.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getFechaAtendida().toString()));
        columnHoraAtendida.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getHoraAtendida().toString()));
        columnHoraCompletada.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getHoraCompletada().toString()));
        columnEstado.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEstadoNombre()));

        try {
            tableViewCitas.getItems().clear();
            
            tableViewCitas.setVisible(false);
            
            // Cancelar una consulta en curso si existe
            if (consultaTask != null && consultaTask.isRunning()) {
                consultaTask.cancel();
            }
            
            progressBar.setVisible(true);
            
            // Crea una nueva tarea (Task) para la consulta en segundo plano
            consultaTask = new Task<List<Cita>>() {
                @Override
                protected List<Cita> call() throws Exception {
                    
                    int totalCitas = obtenerCitas().size();
                    for (int i = 0; i < totalCitas; i++) {
                        Thread.sleep(100);
                        updateProgress(i + 1, totalCitas);
                    }
                    
                    return obtenerCitas();
                }
            };
            
            progressBar.progressProperty().bind(consultaTask.progressProperty());
            //progressBar.visibleProperty().bind(consultaTask.runningProperty());

            // Configura la acción a realizar cuando la tarea se completa
            consultaTask.setOnSucceeded(event -> {
                // Oculta el ProgressBar y muestra la tabla nuevamente
                progressBar.setVisible(false);
                tableViewCitas.setVisible(true);

                // Obtiene el resultado de la consulta y agrega los nuevos usuarios a la tabla
                List<Cita> nuevasCitas = consultaTask.getValue();
                tableViewCitas.getItems().addAll(nuevasCitas);
                
                // Agregar menú contextual a la tabla para editar y eliminar
                ContextMenu contextMenu = new ContextMenu();
                MenuItem menuItemEditar = new MenuItem("Editar");
                MenuItem menuItemEliminar = new MenuItem("Eliminar");

                menuItemEditar.setOnAction(eventMenu -> {
                    Cita cita = tableViewCitas.getSelectionModel().getSelectedItem();
                    System.out.println("Editar cita: " + cita.getObservacion());
                });

                menuItemEliminar.setOnAction(eventMenu -> {
                    Cita cita = tableViewCitas.getSelectionModel().getSelectedItem();
                    System.out.println("Eliminar cita: " + cita.getObservacion());
                    
                    eliminarCita(cita);
                });

                contextMenu.getItems().addAll(menuItemEditar, menuItemEliminar);

                tableViewCitas.setContextMenu(contextMenu);
            });

            // Configura la acción a realizar cuando la tarea se cancela o falla
            consultaTask.setOnCancelled(event -> {
                // Oculta el ProgressBar y muestra la tabla nuevamente
                progressBar.setVisible(false);
                tableViewCitas.setVisible(true);
            });

            // Inicia la tarea en un hilo separado
            Thread thread = new Thread(consultaTask);
            thread.start();
        } catch (Exception e) {
            e.printStackTrace();
            AlertaComponent.mostrarAlertaError(e.getLocalizedMessage());
        }
    }
    
    private List<Cita> obtenerCitas(){
        citas = new ArrayList<>();
        
        Cita cita1 = new Cita(
                1L, 
                1L, 
                "Limpieza bucal", 
                LocalDateTime.of(2023, 8, 10, 10, 0, 0),
                new Date(2023, 8, 10),
                Time.valueOf("10:00:00"),
                Time.valueOf("10:50:00"),
                1
        );
        cita1.setNombrePaciente("Jorge Ramos");
        
        Cita cita2 = new Cita(
                2L, 
                2L, 
                "Blanqueamiento de dientes", 
                LocalDateTime.of(2023, 8, 13, 10, 0, 0),
                new Date(2023, 8, 13),
                Time.valueOf("10:00:00"),
                Time.valueOf("10:40:00"),
                1
        );
        cita2.setNombrePaciente("Mario Ramirez");

        citas.add(cita1);
        citas.add(cita2);

        return citas;
    }

    private void eliminarCita(Cita cita) {
        if ( !AlertaComponent.solicitarConfirmacion("¿Deseas eliminar la cita de " + cita.getNombrePaciente() + "?") ){
            return;
        }
        
        int positionEliminar = -1;
        
        for(int i = 0; i < citas.size(); i++){
            if ( Objects.equals(cita.getId(), citas.get(i).getId()) ){
                positionEliminar = i;
                break;
            }
        }
        
        if ( positionEliminar != -1 ){
            citas.remove(positionEliminar);
            llenarTablaCitas();
            
            AlertaComponent.mostrarAlertaInfo("Cita eliminada correctamente.");
        }
    }
}
