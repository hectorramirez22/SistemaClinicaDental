package dev.example.sistemaclinicadental.modelo;

import java.time.LocalDate;
import java.time.Period;
import java.util.Date;

public class Paciente {
    private Long id;
    private String nombre;
    private String telefono;
    private Date fechaNacimiento;
    private String genero;
    private int compromisoCliente;
    private int estado;

    public Paciente() {
    }

    public Paciente(Long id, String nombre, String telefono, Date fechaNacimiento, String genero, int compromisoCliente, int estado) {
        this.id = id;
        this.nombre = nombre;
        this.telefono = telefono;
        this.fechaNacimiento = fechaNacimiento;
        this.genero = genero;
        this.compromisoCliente = compromisoCliente;
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

    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public int getCompromisoCliente() {
        return compromisoCliente;
    }

    public void setCompromisoCliente(int compromisoCliente) {
        this.compromisoCliente = compromisoCliente;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }
    
    public String calcularEdad(){
        try{
            // Convertir la fecha de nacimiento a LocalDate
            LocalDate fechaNacimientoLocalDate = fechaNacimiento.toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate();

            // Obtener la fecha actual
            LocalDate fechaActual = LocalDate.now();

            // Calcular la diferencia entre las fechas
            Period periodo = Period.between(fechaNacimientoLocalDate, fechaActual);

            int edad = periodo.getYears();

            return edad + " años";
        }catch(Exception ex){
            ex.printStackTrace();
            return "";
        }
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

    @Override
    public String toString() {
        return this.getNombre();
    }
    
}
