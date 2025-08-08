package model;

import java.time.LocalDate;
import java.time.LocalTime;

public class Partida {
    private int id;
    private LocalDate fecha;
    private LocalTime hora;
    private String nombreJugadorX;
    private String nombreJugadorO;
    private int marcadorX;
    private int marcadorO;
    private int empates;
    private String estadoJson;

    public Partida() {}

    public Partida(LocalDate fecha, LocalTime hora, String nombreJugadorX, String nombreJugadorO, int marcadorX, int marcadorO, int empates, String estadoJson) {
        this.fecha = fecha;
        this.hora = hora;
        this.nombreJugadorX = nombreJugadorX;
        this.nombreJugadorO = nombreJugadorO;
        this.marcadorX = marcadorX;
        this.marcadorO = marcadorO;
        this.empates = empates;
        this.estadoJson = estadoJson;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public LocalTime getHora() {
        return hora;
    }

    public void setHora(LocalTime hora) {
        this.hora = hora;
    }

    public String getNombreJugadorX() {
        return nombreJugadorX;
    }

    public void setNombreJugadorX(String nombreJugadorX) {
        this.nombreJugadorX = nombreJugadorX;
    }

    public String getNombreJugadorO() {
        return nombreJugadorO;
    }

    public void setNombreJugadorO(String nombreJugadorO) {
        this.nombreJugadorO = nombreJugadorO;
    }

    public int getMarcadorX() {
        return marcadorX;
    }

    public void setMarcadorX(int marcadorX) {
        this.marcadorX = marcadorX;
    }

    public int getMarcadorO() {
        return marcadorO;
    }

    public void setMarcadorO(int marcadorO) {
        this.marcadorO = marcadorO;
    }

    public int getEmpates() {
        return empates;
    }

    public void setEmpates(int empates) {
        this.empates = empates;
    }

    public String getEstadoJson() {
        return estadoJson;
    }

    public void setEstadoJson(String estadoJson) {
        this.estadoJson = estadoJson;
    }
} 