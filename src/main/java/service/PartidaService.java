package service;

import model.Partida;
import repository.PartidaRepository;
import java.util.List;

// Servicio para la gestión de partidas (lógica de negocio)
public class PartidaService {
    private final PartidaRepository partidaRepository;

    public PartidaService() {
        this.partidaRepository = new PartidaRepository();
    }

    public void guardarPartida(Partida partida) {
        partidaRepository.guardarPartida(partida);
    }

    public List<Partida> listarPartidas() {
        return partidaRepository.listarPartidas();
    }

    public Partida cargarPartida(int id) {
        return partidaRepository.cargarPartida(id);
    }
} 