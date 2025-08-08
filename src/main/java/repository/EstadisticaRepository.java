package repository;

import model.EstadisticaJugador;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class EstadisticaRepository {
    private static final String DB_URL = "jdbc:sqlite:tic_tac_toe.db";

    public EstadisticaRepository() {
        crearTablaSiNoExiste();
    }

    private void crearTablaSiNoExiste() {
        String sql = "CREATE TABLE IF NOT EXISTS estadisticas_jugador (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "nombreJugador TEXT NOT NULL UNIQUE," +
                "partidasJugadas INTEGER NOT NULL DEFAULT 0," +
                "partidasGanadas INTEGER NOT NULL DEFAULT 0," +
                "partidasPerdidas INTEGER NOT NULL DEFAULT 0," +
                "empates INTEGER NOT NULL DEFAULT 0," +
                "porcentajeVictorias REAL NOT NULL DEFAULT 0.0," +
                "rachaActual INTEGER NOT NULL DEFAULT 0," +
                "mejorRacha INTEGER NOT NULL DEFAULT 0," +
                "ultimaPartida TEXT NOT NULL" +
                ");";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            System.out.println("Tabla estadisticas_jugador creada o ya existe.");
        } catch (SQLException e) {
            System.err.println("Error al crear tabla estadisticas_jugador: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void guardarEstadistica(EstadisticaJugador estadistica) {
        String sql = "INSERT OR REPLACE INTO estadisticas_jugador " +
                "(nombreJugador, partidasJugadas, partidasGanadas, partidasPerdidas, empates, " +
                "porcentajeVictorias, rachaActual, mejorRacha, ultimaPartida) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, estadistica.getNombreJugador());
            pstmt.setInt(2, estadistica.getPartidasJugadas());
            pstmt.setInt(3, estadistica.getPartidasGanadas());
            pstmt.setInt(4, estadistica.getPartidasPerdidas());
            pstmt.setInt(5, estadistica.getEmpates());
            pstmt.setDouble(6, estadistica.getPorcentajeVictorias());
            pstmt.setInt(7, estadistica.getRachaActual());
            pstmt.setInt(8, estadistica.getMejorRacha());
            pstmt.setString(9, estadistica.getUltimaPartida().toString());
            pstmt.executeUpdate();
            System.out.println("Estadística guardada para jugador: " + estadistica.getNombreJugador());
        } catch (SQLException e) {
            System.err.println("Error al guardar estadística: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public EstadisticaJugador obtenerEstadisticaPorJugador(String nombreJugador) {
        String sql = "SELECT * FROM estadisticas_jugador WHERE nombreJugador = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, nombreJugador);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return mapearEstadistica(rs);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener estadística: " + e.getMessage());
            e.printStackTrace();
        }
        return new EstadisticaJugador(nombreJugador);
    }

    public List<EstadisticaJugador> obtenerRankingPorVictorias() {
        List<EstadisticaJugador> ranking = new ArrayList<>();
        String sql = "SELECT * FROM estadisticas_jugador ORDER BY partidasGanadas DESC, porcentajeVictorias DESC, mejorRacha DESC";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                ranking.add(mapearEstadistica(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener ranking: " + e.getMessage());
            e.printStackTrace();
        }
        return ranking;
    }

    public List<EstadisticaJugador> obtenerRankingPorPorcentaje() {
        List<EstadisticaJugador> ranking = new ArrayList<>();
        String sql = "SELECT * FROM estadisticas_jugador WHERE partidasJugadas >= 3 ORDER BY porcentajeVictorias DESC, partidasGanadas DESC";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                ranking.add(mapearEstadistica(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener ranking por porcentaje: " + e.getMessage());
            e.printStackTrace();
        }
        return ranking;
    }

    public List<EstadisticaJugador> obtenerRankingPorRacha() {
        List<EstadisticaJugador> ranking = new ArrayList<>();
        String sql = "SELECT * FROM estadisticas_jugador ORDER BY mejorRacha DESC, rachaActual DESC, partidasGanadas DESC";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                ranking.add(mapearEstadistica(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener ranking por racha: " + e.getMessage());
            e.printStackTrace();
        }
        return ranking;
    }

    private EstadisticaJugador mapearEstadistica(ResultSet rs) throws SQLException {
        EstadisticaJugador estadistica = new EstadisticaJugador();
        estadistica.setId(rs.getInt("id"));
        estadistica.setNombreJugador(rs.getString("nombreJugador"));
        estadistica.setPartidasJugadas(rs.getInt("partidasJugadas"));
        estadistica.setPartidasGanadas(rs.getInt("partidasGanadas"));
        estadistica.setPartidasPerdidas(rs.getInt("partidasPerdidas"));
        estadistica.setEmpates(rs.getInt("empates"));
        estadistica.setPorcentajeVictorias(rs.getDouble("porcentajeVictorias"));
        estadistica.setRachaActual(rs.getInt("rachaActual"));
        estadistica.setMejorRacha(rs.getInt("mejorRacha"));
        estadistica.setUltimaPartida(LocalDate.parse(rs.getString("ultimaPartida")));
        return estadistica;
    }
}
