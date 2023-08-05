package dev.example.sistemaclinicadental.modelo;

import java.sql.Time;
import java.time.LocalDateTime;
import java.util.Date;

public class Cita {
    private Long id;
    private Long idPaciente;
    private String observacion;
    private LocalDateTime fechaProgramada;
    private Date fechaAtendida;
    private Time horaAtendida;
    private Time horaCompletada;
    private int estado;
    
    private String nombrePaciente;

    public Cita() {
    }

    public Cita(Long id, Long idPaciente, String observacion, LocalDateTime fechaProgramada, Date fechaAtendida, Time HoraAtendida, Time HoraCompletada, int estado) {
        this.id = id;
        this.idPaciente = idPaciente;
        this.observacion = observacion;
        this.fechaProgramada = fechaProgramada;
        this.fechaAtendida = fechaAtendida;
        this.horaAtendida = HoraAtendida;
        this.horaCompletada = HoraCompletada;
        this.estado = estado;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdPaciente() {
        return idPaciente;
    }

    public void setIdPaciente(Long idPaciente) {
        this.idPaciente = idPaciente;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public LocalDateTime getFechaProgramada() {
        return fechaProgramada;
    }

    public void setFechaProgramada(LocalDateTime fechaProgramada) {
        this.fechaProgramada = fechaProgramada;
    }

    public Date getFechaAtendida() {
        return fechaAtendida;
    }

    public void setFechaAtendida(Date fechaAtendida) {
        this.fechaAtendida = fechaAtendida;
    }

    public Time getHoraAtendida() {
        return horaAtendida;
    }

    public void setHoraAtendida(Time HoraAtendida) {
        this.horaAtendida = HoraAtendida;
    }

    public Time getHoraCompletada() {
        return horaCompletada;
    }

    public void setHoraCompletada(Time HoraCompletada) {
        this.horaCompletada = HoraCompletada;
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
            value = "Agendado";
        }
        
        if ( this.getEstado() == 2){
            value = "Cancelado";
        }
        
        if ( this.getEstado() == 3) {
            value = "Atendido";
        }
        
        return value;
    }

    public String getNombrePaciente() {
        return nombrePaciente;
    }

    public void setNombrePaciente(String nombrePaciente) {
        this.nombrePaciente = nombrePaciente;
    }
    
}
