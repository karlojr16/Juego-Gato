package repository;

import model.Partida;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class PartidaRepository {
    private static final String DB_URL = "jdbc:sqlite:tic_tac_toe.db";

    public PartidaRepository() {
        System.out.println("Ruta de la base de datos: " + DB_URL);
        crearTablaSiNoExiste();
    }

    private void crearTablaSiNoExiste() {
        String sql = "CREATE TABLE IF NOT EXISTS partida (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "fecha TEXT NOT NULL," +
                "hora TEXT NOT NULL," +
                "nombreJugadorX TEXT NOT NULL," +
                "nombreJugadorO TEXT NOT NULL," +
                "marcadorX INTEGER NOT NULL," +
                "marcadorO INTEGER NOT NULL," +
                "empates INTEGER NOT NULL," +
                "estadoJson TEXT NOT NULL" +
                ");";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void guardarPartida(Partida partida) {
        System.out.println("Intentando guardar partida: " + partida.getNombreJugadorX() + " vs " + partida.getNombreJugadorO());
        String sql = "INSERT INTO partida (fecha, hora, nombreJugadorX, nombreJugadorO, marcadorX, marcadorO, empates, estadoJson) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, partida.getFecha().toString());
            pstmt.setString(2, partida.getHora().toString());
            pstmt.setString(3, partida.getNombreJugadorX());
            pstmt.setString(4, partida.getNombreJugadorO());
            pstmt.setInt(5, partida.getMarcadorX());
            pstmt.setInt(6, partida.getMarcadorO());
            pstmt.setInt(7, partida.getEmpates());
            pstmt.setString(8, partida.getEstadoJson());
            pstmt.executeUpdate();
            System.out.println("Partida guardada correctamente.");
        } catch (SQLException e) {
            System.err.println("Error al guardar en la base de datos: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public List<Partida> listarPartidas() {
        List<Partida> partidas = new ArrayList<>();
        String sql = "SELECT * FROM partida ORDER BY id DESC";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Partida partida = new Partida();
                partida.setId(rs.getInt("id"));
                partida.setFecha(LocalDate.parse(rs.getString("fecha")));
                partida.setHora(LocalTime.parse(rs.getString("hora")));
                partida.setNombreJugadorX(rs.getString("nombreJugadorX"));
                partida.setNombreJugadorO(rs.getString("nombreJugadorO"));
                partida.setMarcadorX(rs.getInt("marcadorX"));
                partida.setMarcadorO(rs.getInt("marcadorO"));
                partida.setEmpates(rs.getInt("empates"));
                partida.setEstadoJson(rs.getString("estadoJson"));
                partidas.add(partida);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return partidas;
    }

    public Partida cargarPartida(int id) {
        String sql = "SELECT * FROM partida WHERE id = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    Partida partida = new Partida();
                    partida.setId(rs.getInt("id"));
                    partida.setFecha(LocalDate.parse(rs.getString("fecha")));
                    partida.setHora(LocalTime.parse(rs.getString("hora")));
                    partida.setNombreJugadorX(rs.getString("nombreJugadorX"));
                    partida.setNombreJugadorO(rs.getString("nombreJugadorO"));
                    partida.setMarcadorX(rs.getInt("marcadorX"));
                    partida.setMarcadorO(rs.getInt("marcadorO"));
                    partida.setEmpates(rs.getInt("empates"));
                    partida.setEstadoJson(rs.getString("estadoJson"));
                    return partida;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
} 