package model;

public class Jugador
{
    private String nombre;
    private int victorias;
    private String simbolo;
    private boolean esComputadora;

    public Jugador()
    {
        this.nombre = "Jugador 1";
        this.victorias = 0;
        this.simbolo = "X";
        this.esComputadora = false;
    }

    public Jugador(String nombre, int victorias)
    {
        this.nombre = nombre;
        this.victorias = victorias;
        this.simbolo = "X";
        this.esComputadora = false;
    }

    public Jugador(String nombre, int victorias, String simbolo, boolean esComputadora)
    {
        this.nombre = nombre;
        this.victorias = victorias;
        this.simbolo = simbolo;
        this.esComputadora = esComputadora;
    }
    
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getVictorias() {
        return victorias;
    }

    public void setVictorias(int victorias) {
        this.victorias = victorias;
    }

    public String getSimbolo() {
        return simbolo;
    }

    public void setSimbolo(String simbolo) {
        this.simbolo = simbolo;
    }

    public boolean isEsComputadora() {
        return esComputadora;
    }

    public void setEsComputadora(boolean esComputadora) {
        this.esComputadora = esComputadora;
    }

    public void ganarPartida() {
        this.victorias++;
    }
    
    public int getWins() {
        return victorias;
    }

    public void setWins(int wins) {
        this.victorias = wins;
    }

    public void incrementarVictoria() {
        this.victorias++;
    }
}
