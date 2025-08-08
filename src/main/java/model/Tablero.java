package model;

// Clase que maneja la lógica del juego del gato
public class Tablero {
    // Matriz que representa el tablero 3x3
    private String[][] tablero;
    
    // Contador de movimientos realizados
    private int movimientos;
    
    // Los dos jugadores
    private Jugador jugador1;
    private Jugador jugador2;
    
    // Jugador que tiene el turno actual
    private Jugador jugadorActual;

    // Constructor
    public Tablero(Jugador jugador1, Jugador jugador2) {
        this.jugador1 = jugador1;
        this.jugador2 = jugador2;
        this.jugadorActual = jugador1; // El jugador 1 siempre empieza
        this.tablero = new String[3][3];
        this.movimientos = 0;
        limpiarTablero();
    }

    // Método para limpiar el tablero
    private void limpiarTablero() {
        for (int fila = 0; fila < 3; fila++) {
            for (int columna = 0; columna < 3; columna++) {
                tablero[fila][columna] = "";
            }
        }
        movimientos = 0;
    }

    // Método para hacer un movimiento en el tablero
    public boolean hacerMovimiento(int fila, int columna) {
        // Verificar que la posición sea válida y esté vacía
        if (fila >= 0 && fila < 3 && columna >= 0 && columna < 3 && 
            tablero[fila][columna].isEmpty()) {
            tablero[fila][columna] = jugadorActual.getSimbolo();
            movimientos++;
            return true;
        }
        return false;
    }

    // Método para cambiar el turno
    public void cambiarTurno() {
        if (jugadorActual == jugador1) {
            jugadorActual = jugador2;
        } else {
            jugadorActual = jugador1;
        }
    }

    // Método para obtener el jugador actual
    public Jugador getJugadorActual() {
        return jugadorActual;
    }

    // Método para obtener el jugador 1
    public Jugador getJugador1() {
        return jugador1;
    }

    // Método para obtener el jugador 2
    public Jugador getJugador2() {
        return jugador2;
    }

    // Método para verificar si hay un ganador
    public String verificarGanador() {
        // Verificar filas
        for (int fila = 0; fila < 3; fila++) {
            if (!tablero[fila][0].isEmpty() && 
                tablero[fila][0].equals(tablero[fila][1]) && 
                tablero[fila][0].equals(tablero[fila][2])) {
                return tablero[fila][0];
            }
        }

        // Verificar columnas
        for (int columna = 0; columna < 3; columna++) {
            if (!tablero[0][columna].isEmpty() && 
                tablero[0][columna].equals(tablero[1][columna]) && 
                tablero[0][columna].equals(tablero[2][columna])) {
                return tablero[0][columna];
            }
        }

        // Verificar diagonal principal (de arriba-izquierda a abajo-derecha)
        if (!tablero[0][0].isEmpty() && 
            tablero[0][0].equals(tablero[1][1]) && 
            tablero[0][0].equals(tablero[2][2])) {
            return tablero[0][0];
        }

        // Verificar diagonal secundaria (de arriba-derecha a abajo-izquierda)
        if (!tablero[0][2].isEmpty() && 
            tablero[0][2].equals(tablero[1][1]) && 
            tablero[0][2].equals(tablero[2][0])) {
            return tablero[0][2];
        }

        return null; // No hay ganador
    }

    // Método para verificar si hay empate
    public boolean esEmpate() {
        return movimientos == 9 && verificarGanador() == null;
    }

    // Método para verificar si el juego terminó
    public boolean juegoTerminado() {
        return verificarGanador() != null || esEmpate();
    }

    // Método para reiniciar el juego
    public void reiniciarJuego() {
        limpiarTablero();
        jugadorActual = jugador1; // El jugador 1 siempre empieza
    }

    // Método para obtener el tablero
    public String[][] getTablero() {
        return tablero;
    }

    // Método para obtener el número de movimientos
    public int getMovimientos() {
        return movimientos;
    }

    // Método para obtener un jugador por su símbolo
    public Jugador obtenerJugadorPorSimbolo(String simbolo) {
        if (jugador1.getSimbolo().equals(simbolo)) {
            return jugador1;
        } else if (jugador2.getSimbolo().equals(simbolo)) {
            return jugador2;
        }
        return null;
    }
}
