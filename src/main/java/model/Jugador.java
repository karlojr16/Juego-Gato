package model;

// Clase para representar un jugador del juego
public class Jugador
{
    // Atributos del jugador
    private String nombre;        // Nombre del jugador
    private int victorias;        // Número de victorias
    private String simbolo;       // Símbolo que usa (X u O)
    private boolean esComputadora; // Si es computadora o humano

    // Constructor por defecto
    public Jugador()
    {
        this.nombre = "Jugador 1";
        this.victorias = 0;
        this.simbolo = "X";
        this.esComputadora = false;
    }

    // Constructor con nombre y victorias
    public Jugador(String nombre, int victorias)
    {
        this.nombre = nombre;
        this.victorias = victorias;
        this.simbolo = "X";
        this.esComputadora = false;
    }

    // Constructor completo
    public Jugador(String nombre, int victorias, String simbolo, boolean esComputadora)
    {
        this.nombre = nombre;
        this.victorias = victorias;
        this.simbolo = simbolo;
        this.esComputadora = esComputadora;
    }

    // Métodos para obtener y establecer valores (getters y setters)
    
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

    // Método para aumentar victorias
    public void ganarPartida() {
        this.victorias++;
    }
    
    // Método para compatibilidad con código existente
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
