package model;

import java.time.LocalDate;

// POJO para representar las estadísticas de un jugador
public class EstadisticaJugador {
    private int id;
    private String nombreJugador;
    private int partidasJugadas;
    private int partidasGanadas;
    private int partidasPerdidas;
    private int empates;
    private double porcentajeVictorias;
    private int rachaActual;
    private int mejorRacha;
    private LocalDate ultimaPartida;

    public EstadisticaJugador() {}

    public EstadisticaJugador(String nombreJugador) {
        this.nombreJugador = nombreJugador;
        this.partidasJugadas = 0;
        this.partidasGanadas = 0;
        this.partidasPerdidas = 0;
        this.empates = 0;
        this.porcentajeVictorias = 0.0;
        this.rachaActual = 0;
        this.mejorRacha = 0;
        this.ultimaPartida = LocalDate.now();
    }

    // Método para calcular el porcentaje de victorias
    public void calcularPorcentajeVictorias() {
        if (partidasJugadas > 0) {
            this.porcentajeVictorias = (double) partidasGanadas / partidasJugadas * 100;
        } else {
            this.porcentajeVictorias = 0.0;
        }
    }

    // Método para actualizar estadísticas después de una partida
    public void actualizarEstadisticas(boolean gano, boolean empate) {
        partidasJugadas++;
        ultimaPartida = LocalDate.now();
        
        if (empate) {
            empates++;
            rachaActual = 0; // Empate rompe la racha
        } else if (gano) {
            partidasGanadas++;
            rachaActual++;
            if (rachaActual > mejorRacha) {
                mejorRacha = rachaActual;
            }
        } else {
            partidasPerdidas++;
            rachaActual = 0; // Derrota rompe la racha
        }
        
        calcularPorcentajeVictorias();
    }

    // Getters y Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombreJugador() {
        return nombreJugador;
    }

    public void setNombreJugador(String nombreJugador) {
        this.nombreJugador = nombreJugador;
    }

    public int getPartidasJugadas() {
        return partidasJugadas;
    }

    public void setPartidasJugadas(int partidasJugadas) {
        this.partidasJugadas = partidasJugadas;
    }

    public int getPartidasGanadas() {
        return partidasGanadas;
    }

    public void setPartidasGanadas(int partidasGanadas) {
        this.partidasGanadas = partidasGanadas;
    }

    public int getPartidasPerdidas() {
        return partidasPerdidas;
    }

    public void setPartidasPerdidas(int partidasPerdidas) {
        this.partidasPerdidas = partidasPerdidas;
    }

    public int getEmpates() {
        return empates;
    }

    public void setEmpates(int empates) {
        this.empates = empates;
    }

    public double getPorcentajeVictorias() {
        return porcentajeVictorias;
    }

    public void setPorcentajeVictorias(double porcentajeVictorias) {
        this.porcentajeVictorias = porcentajeVictorias;
    }

    public int getRachaActual() {
        return rachaActual;
    }

    public void setRachaActual(int rachaActual) {
        this.rachaActual = rachaActual;
    }

    public int getMejorRacha() {
        return mejorRacha;
    }

    public void setMejorRacha(int mejorRacha) {
        this.mejorRacha = mejorRacha;
    }

    public LocalDate getUltimaPartida() {
        return ultimaPartida;
    }

    public void setUltimaPartida(LocalDate ultimaPartida) {
        this.ultimaPartida = ultimaPartida;
    }
}
