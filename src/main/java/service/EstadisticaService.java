package service;

import model.EstadisticaJugador;
import repository.EstadisticaRepository;
import java.util.List;

public class EstadisticaService {
    private final EstadisticaRepository estadisticaRepository;

    public EstadisticaService() {
        this.estadisticaRepository = new EstadisticaRepository();
    }

    public void actualizarEstadisticasPartida(String nombreJugador1, String nombreJugador2, 
                                             String ganador, boolean esEmpate) {
        EstadisticaJugador stats1 = estadisticaRepository.obtenerEstadisticaPorJugador(nombreJugador1);
        EstadisticaJugador stats2 = estadisticaRepository.obtenerEstadisticaPorJugador(nombreJugador2);

        if (esEmpate) {
            stats1.actualizarEstadisticas(false, true);
            stats2.actualizarEstadisticas(false, true);
        } else {
            boolean jugador1Gano = nombreJugador1.equals(ganador);
            stats1.actualizarEstadisticas(jugador1Gano, false);
            stats2.actualizarEstadisticas(!jugador1Gano, false);
        }

        estadisticaRepository.guardarEstadistica(stats1);
        estadisticaRepository.guardarEstadistica(stats2);

        System.out.println("Estadísticas actualizadas para " + nombreJugador1 + " y " + nombreJugador2);
    }

    public EstadisticaJugador obtenerEstadisticasJugador(String nombreJugador) {
        return estadisticaRepository.obtenerEstadisticaPorJugador(nombreJugador);
    }

    public List<EstadisticaJugador> obtenerRankingPorVictorias() {
        return estadisticaRepository.obtenerRankingPorVictorias();
    }

    public List<EstadisticaJugador> obtenerRankingPorPorcentaje() {
        return estadisticaRepository.obtenerRankingPorPorcentaje();
    }

    public List<EstadisticaJugador> obtenerRankingPorRacha() {
        return estadisticaRepository.obtenerRankingPorRacha();
    }

    public List<EstadisticaJugador> obtenerTopJugadores(int limite) {
        List<EstadisticaJugador> ranking = obtenerRankingPorVictorias();
        return ranking.size() > limite ? ranking.subList(0, limite) : ranking;
    }

    public boolean estaEnRachaGanadora(String nombreJugador) {
        EstadisticaJugador stats = obtenerEstadisticasJugador(nombreJugador);
        return stats.getRachaActual() >= 3;
    }

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

    public void resetearEstadisticasJugador(String nombreJugador) {
        EstadisticaJugador nuevasStats = new EstadisticaJugador(nombreJugador);
        estadisticaRepository.guardarEstadistica(nuevasStats);
        System.out.println("Estadísticas reseteadas para " + nombreJugador);
    }
}
