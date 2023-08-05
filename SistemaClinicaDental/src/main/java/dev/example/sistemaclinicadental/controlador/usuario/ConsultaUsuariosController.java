package dev.example.sistemaclinicadental.controlador.usuario;

import dev.example.sistemaclinicadental.App;
import dev.example.sistemaclinicadental.StageManager;
import dev.example.sistemaclinicadental.modelo.Usuario;
import dev.example.sistemaclinicadental.vista.componentes.AlertaComponent;
import java.io.IOException;
import java.util.ArrayList;
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

public class ConsultaUsuariosController {
    @FXML
    private TableView<Usuario> tableViewUsuarios;
    @FXML
    private TableColumn<Usuario, Long> columnId;
    @FXML
    private TableColumn<Usuario, String> columnNombre;
    @FXML
    private TableColumn<Usuario, String> columnTelefono;
    @FXML
    private TableColumn<Usuario, String> columnGenero;
    @FXML
    private TableColumn<Usuario, String> columnEstado;
    @FXML
    private ProgressBar progressBar;

    // Variable para la tarea de consulta en segundo plano
    private Task<List<Usuario>> consultaTask;
    
    private List<Usuario> usuarios = new ArrayList<>();
    
    @FXML
    private void initialize() {
        llenarTablaUsuarios();
    }
    
    @FXML
    private void nuevoUsuarioClicked() throws IOException {
        StageManager.getInstance().getPrimaryStage().setTitle("Nuevo usuario");
        App.setRoot("usuario/registrar-usuario");
    }
    
    @FXML
    private void consultarUsariosClicked() throws IOException {
        llenarTablaUsuarios();
    }

    private void llenarTablaUsuarios() {
        columnId.setCellValueFactory(cellData -> new SimpleLongProperty(cellData.getValue().getId()).asObject());
        columnNombre.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNombre()));
        columnTelefono.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTelefono()));
        columnGenero.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getGenero()));
        columnEstado.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEstadoNombre()));

        try {
            tableViewUsuarios.getItems().clear();
            
            tableViewUsuarios.setVisible(false);
            
            // Cancelar una consulta en curso si existe
            if (consultaTask != null && consultaTask.isRunning()) {
                consultaTask.cancel();
            }
            
            progressBar.setVisible(true);
            
            // Crea una nueva tarea (Task) para la consulta en segundo plano
            consultaTask = new Task<List<Usuario>>() {
                @Override
                protected List<Usuario> call() throws Exception {
                    
                    int totalUsuarios = obtenerUsuarios().size();
                    for (int i = 0; i < totalUsuarios; i++) {
                        Thread.sleep(100);
                        updateProgress(i + 1, totalUsuarios);
                    }
                    
                    return obtenerUsuarios();
                }
            };
            
            progressBar.progressProperty().bind(consultaTask.progressProperty());
            //progressBar.visibleProperty().bind(consultaTask.runningProperty());

            // Configura la acción a realizar cuando la tarea se completa
            consultaTask.setOnSucceeded(event -> {
                // Oculta el ProgressBar y muestra la tabla nuevamente
                progressBar.setVisible(false);
                tableViewUsuarios.setVisible(true);

                // Obtiene el resultado de la consulta y agrega los nuevos usuarios a la tabla
                List<Usuario> nuevosUsuarios = consultaTask.getValue();
                tableViewUsuarios.getItems().addAll(nuevosUsuarios);
                
                // Agregar menú contextual a la tabla para editar y eliminar
                ContextMenu contextMenu = new ContextMenu();
                MenuItem menuItemEditar = new MenuItem("Editar");
                MenuItem menuItemEliminar = new MenuItem("Eliminar");

                menuItemEditar.setOnAction(eventMenu -> {
                    Usuario usuario = tableViewUsuarios.getSelectionModel().getSelectedItem();
                    System.out.println("Editar usuario: " + usuario.getNombre());
                });

                menuItemEliminar.setOnAction(eventMenu -> {
                    Usuario usuario = tableViewUsuarios.getSelectionModel().getSelectedItem();
                    System.out.println("Eliminar usuario: " + usuario.getNombre());
                    
                    eliminarUsuario(usuario);
                });

                contextMenu.getItems().addAll(menuItemEditar, menuItemEliminar);

                tableViewUsuarios.setContextMenu(contextMenu);
            });

            // Configura la acción a realizar cuando la tarea se cancela o falla
            consultaTask.setOnCancelled(event -> {
                // Oculta el ProgressBar y muestra la tabla nuevamente
                progressBar.setVisible(false);
                tableViewUsuarios.setVisible(true);
            });

            // Inicia la tarea en un hilo separado
            Thread thread = new Thread(consultaTask);
            thread.start();
        } catch (Exception e) {
            e.printStackTrace();
            AlertaComponent.mostrarAlertaError(e.getLocalizedMessage());
        }
    }
    
    private List<Usuario> obtenerUsuarios(){
        usuarios = new ArrayList<>();

        usuarios.add(new Usuario(1L, "Jose Perez", "12345678", "Masculino", 1));
        usuarios.add(new Usuario(2L, "Mario Gomez", "39393939", "Masculino", 1));
        usuarios.add(new Usuario(3L, "Rosa Elena", "39393929", "Femenino", 1));

        return usuarios;
    }

    private void eliminarUsuario(Usuario usuario) {
        if ( !AlertaComponent.solicitarConfirmacion("¿Deseas eliminar a " + usuario.getNombre() + "?") ){
            return;
        }
        
        int positionEliminar = -1;
        
        for(int i = 0; i < usuarios.size(); i++){
            if ( Objects.equals(usuario.getId(), usuarios.get(i).getId()) ){
                positionEliminar = i;
                break;
            }
        }
        
        if ( positionEliminar != -1 ){
            usuarios.remove(positionEliminar);
            llenarTablaUsuarios();
            
            AlertaComponent.mostrarAlertaInfo("Usuario eliminado correctamente.");
        }
    }
}
