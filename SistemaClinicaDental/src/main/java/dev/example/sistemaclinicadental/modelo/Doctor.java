package dev.example.sistemaclinicadental.modelo;

import java.util.Date;

public class Doctor {
    private Long id;
    private String nombre;
    private String telefono;
    private String especialidad;
    private Date fechaNacimiento;
    private int estado;

    public Doctor(Long id, String nombre, String telefono, String especialidad, Date fechaNacimiento, int estado) {
        this.id = id;
        this.nombre = nombre;
        this.telefono = telefono;
        this.especialidad = especialidad;
        this.fechaNacimiento = fechaNacimiento;
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

    public String getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }

    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
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
