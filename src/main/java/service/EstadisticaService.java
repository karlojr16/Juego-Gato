package service;

import model.EstadisticaJugador;
import repository.EstadisticaRepository;
import java.util.List;

// Servicio para gestionar estadísticas y ranking de jugadores
public class EstadisticaService {
    private final EstadisticaRepository estadisticaRepository;

    public EstadisticaService() {
        this.estadisticaRepository = new EstadisticaRepository();
    }

    // Actualizar estadísticas después de una partida
    public void actualizarEstadisticasPartida(String nombreJugador1, String nombreJugador2, 
                                             String ganador, boolean esEmpate) {
        // Obtener estadísticas actuales de ambos jugadores
        EstadisticaJugador stats1 = estadisticaRepository.obtenerEstadisticaPorJugador(nombreJugador1);
        EstadisticaJugador stats2 = estadisticaRepository.obtenerEstadisticaPorJugador(nombreJugador2);

        if (esEmpate) {
            // Ambos jugadores registran empate
            stats1.actualizarEstadisticas(false, true);
            stats2.actualizarEstadisticas(false, true);
        } else {
            // Determinar ganador y perdedor
            boolean jugador1Gano = nombreJugador1.equals(ganador);
            stats1.actualizarEstadisticas(jugador1Gano, false);
            stats2.actualizarEstadisticas(!jugador1Gano, false);
        }

        // Guardar estadísticas actualizadas
        estadisticaRepository.guardarEstadistica(stats1);
        estadisticaRepository.guardarEstadistica(stats2);

        System.out.println("Estadísticas actualizadas para " + nombreJugador1 + " y " + nombreJugador2);
    }

    // Obtener estadísticas de un jugador específico
    public EstadisticaJugador obtenerEstadisticasJugador(String nombreJugador) {
        return estadisticaRepository.obtenerEstadisticaPorJugador(nombreJugador);
    }

    // Obtener ranking por número de victorias
    public List<EstadisticaJugador> obtenerRankingPorVictorias() {
        return estadisticaRepository.obtenerRankingPorVictorias();
    }

    // Obtener ranking por porcentaje de victorias (mínimo 3 partidas)
    public List<EstadisticaJugador> obtenerRankingPorPorcentaje() {
        return estadisticaRepository.obtenerRankingPorPorcentaje();
    }

    // Obtener ranking por mejor racha
    public List<EstadisticaJugador> obtenerRankingPorRacha() {
        return estadisticaRepository.obtenerRankingPorRacha();
    }

    // Obtener el top N jugadores por victorias
    public List<EstadisticaJugador> obtenerTopJugadores(int limite) {
        List<EstadisticaJugador> ranking = obtenerRankingPorVictorias();
        return ranking.size() > limite ? ranking.subList(0, limite) : ranking;
    }

    // Verificar si un jugador está en racha ganadora (3+ victorias consecutivas)
    public boolean estaEnRachaGanadora(String nombreJugador) {
        EstadisticaJugador stats = obtenerEstadisticasJugador(nombreJugador);
        return stats.getRachaActual() >= 3;
    }

    // Obtener estadísticas globales del juego
    public String obtenerEstadisticasGlobales() {
        List<EstadisticaJugador> todasLasEstadisticas = estadisticaRepository.obtenerRankingPorVictorias();
        
        if (todasLasEstadisticas.isEmpty()) {
            return "No hay estadísticas disponibles.";
        }
        
        int totalPartidas = 0;
        int totalJugadores = todasLasEstadisticas.size();
        EstadisticaJugador mejorJugador = todasLasEstadisticas.get(0);
        
        for (EstadisticaJugador stats : todasLasEstadisticas) {
            totalPartidas += stats.getPartidasJugadas();
        }
        
        // Evitar contar partidas doble (cada partida involucra 2 jugadores)
        totalPartidas = totalPartidas / 2;
        
        StringBuilder sb = new StringBuilder();
        sb.append("=== ESTADÍSTICAS GLOBALES ===\n");
        sb.append("Total de jugadores: ").append(totalJugadores).append("\n");
        sb.append("Total de partidas: ").append(totalPartidas).append("\n");
        sb.append("Mejor jugador: ").append(mejorJugador.getNombreJugador())
          .append(" (").append(mejorJugador.getPartidasGanadas()).append(" victorias)\n");
        sb.append("Mejor racha: ").append(mejorJugador.getMejorRacha()).append(" victorias consecutivas");
        
        return sb.toString();
    }

    // Resetear estadísticas de un jugador
    public void resetearEstadisticasJugador(String nombreJugador) {
        EstadisticaJugador nuevasStats = new EstadisticaJugador(nombreJugador);
        estadisticaRepository.guardarEstadistica(nuevasStats);
        System.out.println("Estadísticas reseteadas para " + nombreJugador);
    }
}
