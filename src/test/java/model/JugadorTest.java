package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Pruebas unitarias para la clase Jugador
 */
class JugadorTest {

    private Jugador jugador;

    @BeforeEach
    void setUp() {
        jugador = new Jugador();
    }

    @Test
    void testConstructorPorDefecto() {
        assertEquals("Jugador 1", jugador.getNombre());
        assertEquals(0, jugador.getVictorias());
        assertEquals("X", jugador.getSimbolo());
        assertFalse(jugador.isEsComputadora());
    }

    @Test
    void testConstructorConNombreYVictorias() {
        Jugador jugador2 = new Jugador("Ana", 5);
        assertEquals("Ana", jugador2.getNombre());
        assertEquals(5, jugador2.getVictorias());
        assertEquals("X", jugador2.getSimbolo());
        assertFalse(jugador2.isEsComputadora());
    }

    @Test
    void testConstructorCompleto() {
        Jugador jugador3 = new Jugador("Carlos", 3, "O", true);
        assertEquals("Carlos", jugador3.getNombre());
        assertEquals(3, jugador3.getVictorias());
        assertEquals("O", jugador3.getSimbolo());
        assertTrue(jugador3.isEsComputadora());
    }

    @Test
    void testSettersYGetters() {
        jugador.setNombre("María");
        jugador.setVictorias(10);
        jugador.setSimbolo("O");
        jugador.setEsComputadora(true);

        assertEquals("María", jugador.getNombre());
        assertEquals(10, jugador.getVictorias());
        assertEquals("O", jugador.getSimbolo());
        assertTrue(jugador.isEsComputadora());
    }

    @Test
    void testGanarPartida() {
        int victoriasIniciales = jugador.getVictorias();
        jugador.ganarPartida();
        assertEquals(victoriasIniciales + 1, jugador.getVictorias());
    }

    @Test
    void testIncrementarVictoria() {
        int victoriasIniciales = jugador.getVictorias();
        jugador.incrementarVictoria();
        assertEquals(victoriasIniciales + 1, jugador.getVictorias());
    }

    @Test
    void testMetodosCompatibilidad() {
        jugador.setWins(15);
        assertEquals(15, jugador.getWins());
        assertEquals(15, jugador.getVictorias());
    }

    @Test
    void testMultiplesVictorias() {
        for (int i = 0; i < 5; i++) {
            jugador.ganarPartida();
        }
        assertEquals(5, jugador.getVictorias());
    }

    @Test
    void testVictoriasNegativas() {
        jugador.setVictorias(-1);
        assertEquals(-1, jugador.getVictorias());
        // Nota: En una implementación más robusta, se podría validar que no sean negativas
    }

    @Test
    void testSimboloValido() {
        jugador.setSimbolo("X");
        assertEquals("X", jugador.getSimbolo());
        
        jugador.setSimbolo("O");
        assertEquals("O", jugador.getSimbolo());
    }

    @Test
    void testNombreVacio() {
        jugador.setNombre("");
        assertEquals("", jugador.getNombre());
    }

    @Test
    void testNombreNull() {
        jugador.setNombre(null);
        assertNull(jugador.getNombre());
    }
}
