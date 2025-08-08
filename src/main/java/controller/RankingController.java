package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.EstadisticaJugador;
import service.EstadisticaService;
import java.text.DecimalFormat;
import java.util.List;

public class RankingController {
    
    // Elementos de la interfaz
    @FXML private Button btnCerrar;
    @FXML private Button btnActualizar;
    @FXML private Button btnVolver;
    @FXML private Label lblEstadisticasGlobales;
    
    // Tablas de ranking
    @FXML private TableView<EstadisticaJugador> tablaVictorias;
    @FXML private TableView<EstadisticaJugador> tablaPorcentaje;
    @FXML private TableView<EstadisticaJugador> tablaRachas;
    
    // Columnas tabla victorias
    @FXML private TableColumn<EstadisticaJugador, String> colPosicionVictorias;
    @FXML private TableColumn<EstadisticaJugador, String> colNombreVictorias;
    @FXML private TableColumn<EstadisticaJugador, Integer> colVictorias;
    @FXML private TableColumn<EstadisticaJugador, Integer> colPartidasVictorias;
    @FXML private TableColumn<EstadisticaJugador, String> colPorcentajeVictorias;
    @FXML private TableColumn<EstadisticaJugador, Integer> colRachaVictorias;
    @FXML private TableColumn<EstadisticaJugador, Integer> colMejorRachaVictorias;
    
    // Columnas tabla porcentaje
    @FXML private TableColumn<EstadisticaJugador, String> colPosicionPorcentaje;
    @FXML private TableColumn<EstadisticaJugador, String> colNombrePorcentaje;
    @FXML private TableColumn<EstadisticaJugador, String> colPorcentajePorcentaje;
    @FXML private TableColumn<EstadisticaJugador, Integer> colVictoriasPorcentaje;
    @FXML private TableColumn<EstadisticaJugador, Integer> colPartidasPorcentaje;
    @FXML private TableColumn<EstadisticaJugador, Integer> colDerrotas;
    @FXML private TableColumn<EstadisticaJugador, Integer> colEmpatesPorcentaje;
    
    // Columnas tabla rachas
    @FXML private TableColumn<EstadisticaJugador, String> colPosicionRachas;
    @FXML private TableColumn<EstadisticaJugador, String> colNombreRachas;
    @FXML private TableColumn<EstadisticaJugador, Integer> colMejorRachaRachas;
    @FXML private TableColumn<EstadisticaJugador, Integer> colRachaActualRachas;
    @FXML private TableColumn<EstadisticaJugador, Integer> colVictoriasRachas;
    @FXML private TableColumn<EstadisticaJugador, Integer> colPartidasRachas;
    @FXML private TableColumn<EstadisticaJugador, String> colUltimaPartida;
    
    private final EstadisticaService estadisticaService;
    private final DecimalFormat df = new DecimalFormat("#.##");
    
    public RankingController() {
        this.estadisticaService = new EstadisticaService();
    }
    
    @FXML
    private void initialize() {
        configurarTablas();
        configurarEventos();
        cargarDatos();
    }
    
    private void configurarTablas() {
        // Configurar tabla de victorias
        colPosicionVictorias.setCellValueFactory(param -> 
            new javafx.beans.property.SimpleStringProperty(String.valueOf(tablaVictorias.getItems().indexOf(param.getValue()) + 1)));
        colNombreVictorias.setCellValueFactory(new PropertyValueFactory<>("nombreJugador"));
        colVictorias.setCellValueFactory(new PropertyValueFactory<>("partidasGanadas"));
        colPartidasVictorias.setCellValueFactory(new PropertyValueFactory<>("partidasJugadas"));
        colPorcentajeVictorias.setCellValueFactory(param -> 
            new javafx.beans.property.SimpleStringProperty(df.format(param.getValue().getPorcentajeVictorias()) + "%"));
        colRachaVictorias.setCellValueFactory(new PropertyValueFactory<>("rachaActual"));
        colMejorRachaVictorias.setCellValueFactory(new PropertyValueFactory<>("mejorRacha"));
        
        // Configurar tabla de porcentaje
        colPosicionPorcentaje.setCellValueFactory(param -> 
            new javafx.beans.property.SimpleStringProperty(String.valueOf(tablaPorcentaje.getItems().indexOf(param.getValue()) + 1)));
        colNombrePorcentaje.setCellValueFactory(new PropertyValueFactory<>("nombreJugador"));
        colPorcentajePorcentaje.setCellValueFactory(param -> 
            new javafx.beans.property.SimpleStringProperty(df.format(param.getValue().getPorcentajeVictorias()) + "%"));
        colVictoriasPorcentaje.setCellValueFactory(new PropertyValueFactory<>("partidasGanadas"));
        colPartidasPorcentaje.setCellValueFactory(new PropertyValueFactory<>("partidasJugadas"));
        colDerrotas.setCellValueFactory(new PropertyValueFactory<>("partidasPerdidas"));
        colEmpatesPorcentaje.setCellValueFactory(new PropertyValueFactory<>("empates"));
        
        // Configurar tabla de rachas
        colPosicionRachas.setCellValueFactory(param -> 
            new javafx.beans.property.SimpleStringProperty(String.valueOf(tablaRachas.getItems().indexOf(param.getValue()) + 1)));
        colNombreRachas.setCellValueFactory(new PropertyValueFactory<>("nombreJugador"));
        colMejorRachaRachas.setCellValueFactory(new PropertyValueFactory<>("mejorRacha"));
        colRachaActualRachas.setCellValueFactory(new PropertyValueFactory<>("rachaActual"));
        colVictoriasRachas.setCellValueFactory(new PropertyValueFactory<>("partidasGanadas"));
        colPartidasRachas.setCellValueFactory(new PropertyValueFactory<>("partidasJugadas"));
        colUltimaPartida.setCellValueFactory(param -> 
            new javafx.beans.property.SimpleStringProperty(param.getValue().getUltimaPartida().toString()));
    }
    
    private void configurarEventos() {
        btnCerrar.setOnAction(e -> cerrarVentana());
        btnVolver.setOnAction(e -> cerrarVentana());
        btnActualizar.setOnAction(e -> cargarDatos());
    }
    
    private void cargarDatos() {
        try {
            // Cargar estadísticas globales
            String estadisticasGlobales = estadisticaService.obtenerEstadisticasGlobales();
            lblEstadisticasGlobales.setText(estadisticasGlobales);
            
            // Cargar ranking por victorias
            List<EstadisticaJugador> rankingVictorias = estadisticaService.obtenerRankingPorVictorias();
            ObservableList<EstadisticaJugador> datosVictorias = FXCollections.observableArrayList(rankingVictorias);
            tablaVictorias.setItems(datosVictorias);
            
            // Cargar ranking por porcentaje
            List<EstadisticaJugador> rankingPorcentaje = estadisticaService.obtenerRankingPorPorcentaje();
            ObservableList<EstadisticaJugador> datosPorcentaje = FXCollections.observableArrayList(rankingPorcentaje);
            tablaPorcentaje.setItems(datosPorcentaje);
            
            // Cargar ranking por rachas
            List<EstadisticaJugador> rankingRachas = estadisticaService.obtenerRankingPorRacha();
            ObservableList<EstadisticaJugador> datosRachas = FXCollections.observableArrayList(rankingRachas);
            tablaRachas.setItems(datosRachas);
            
            System.out.println("Datos de ranking cargados correctamente.");
            
        } catch (Exception e) {
            System.err.println("Error al cargar datos de ranking: " + e.getMessage());
            e.printStackTrace();
            
            // Mostrar mensaje de error al usuario
            lblEstadisticasGlobales.setText("Error al cargar estadísticas. Intenta actualizar.");
        }
    }
    
    private void cerrarVentana() {
        Stage stage = (Stage) btnCerrar.getScene().getWindow();
        stage.close();
    }
}
