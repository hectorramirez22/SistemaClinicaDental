package dev.example.sistemaclinicadental.modelo;

public class Usuario {
    private Long id;
    private String nombre;
    private String telefono;
    private String genero;
    private int estado;

    public Usuario() {
    }

    public Usuario(Long id, String nombre, String telefono, String genero, int estado) {
        this.id = id;
        this.nombre = nombre;
        this.telefono = telefono;
        this.genero = genero;
        this.estado = estado;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }
    
    
    public String getEstadoNombre(){
        String value = "";
        
        if ( this.getEstado() == 0 ){
            value = "Eliminado";
        }
        
        if ( this.getEstado() == 1 ){
            value = "Activo";
        }
        
        return value;
    }
    
}
