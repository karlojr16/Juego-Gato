package controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Modality;
import model.Jugador;
import model.Tablero;
import application.App;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import service.PartidaService;
import model.Partida;
import java.time.LocalDate;
import java.time.LocalTime;
import org.json.JSONObject;
import service.EstadisticaService;

public class tabController {

    private Tablero tablero;
    private Jugador jugador1;
    private Jugador jugador2;
    
    private Button[][] botonesTablero;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private GridPane gridTablero;

    @FXML
    private Label lblCierre;

    @FXML
    private Label lblnombrejugadordos;

    @FXML
    private Label lblnombrejugadoruno;

    @FXML
    private Label lblpuntajejugadordos;

    @FXML
    private Label lblpuntajejugadoruno;

    @FXML
    private Label lblturno;

    @FXML
    private ImageView imgJugador1;

    @FXML
    private ImageView imgJugador2;

    @FXML
    private Button btnJuegoNuevo;

    @FXML
    private Button btnVolver;

    @FXML private MenuBar menuBar;
    @FXML private MenuItem menuReiniciar;
    @FXML private MenuItem menuGuardar;
    @FXML private MenuItem menuSalir;

    private final PartidaService partidaService = new PartidaService();
    private final EstadisticaService estadisticaService = new EstadisticaService();
    private boolean partidaGuardada = false;
    private int empates = 0;
    private AppController appController;

    @FXML
    void click(MouseEvent event) {
        Platform.exit();
        System.exit(0);
    }

    @FXML
    void enter(MouseEvent event) {
        lblCierre.setStyle("-fx-text-fill: red;");
    }

    @FXML
    void exited(MouseEvent event) {
        lblCierre.setStyle("-fx-text-fill: white;");
    }

    @FXML
    void juegoNuevo(javafx.event.ActionEvent event) {
        reiniciarJuego();
    }

    @FXML
    void volver(javafx.event.ActionEvent event) {
        preguntarGuardarYSalir(() -> cerrarYVolver(event));
    }

    private void cerrarYVolver(javafx.event.ActionEvent event) {
        final Node node = (Node) event.getSource();
        final Stage stage = (Stage) node.getScene().getWindow();
        stage.close();
        cargarPantallaPrincipal();
    }

    private void cargarPantallaPrincipal() {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("app.fxml"));
        try {
            Scene scene = new Scene(fxmlLoader.load(), 500, 400);
            Stage stage = new Stage();
            stage.setScene(scene);

            stage.centerOnScreen();
            stage.initStyle(StageStyle.UNDECORATED);
            stage.setResizable(false);

            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void initialize() {
        botonesTablero = new Button[3][3];
        
        gridTablero.setHgap(10);
        gridTablero.setVgap(10);
        gridTablero.setAlignment(Pos.CENTER);

        crearBotonesTablero();

        if (menuReiniciar != null) {
            menuReiniciar.setOnAction(e -> mostrarConfirmacionReiniciar());
        }
        if (menuGuardar != null) {
            menuGuardar.setOnAction(e -> guardarPartida());
        }
        if (menuSalir != null) {
            menuSalir.setOnAction(e -> preguntarGuardarYSalir(this::salirAplicacion));
        }
    }

    private void crearBotonesTablero() {
        for (int fila = 0; fila < 3; fila++) {
            for (int columna = 0; columna < 3; columna++) {
                Button boton = new Button();
                boton.setFont(Font.font("Arial", FontWeight.BOLD, 40));
                boton.setPrefSize(80, 80);
                boton.setStyle("-fx-background-color: rgba(255,255,255,0.15); -fx-text-fill: white; -fx-border-color: rgba(255,255,255,0.8); -fx-border-width: 2; -fx-border-radius: 8; -fx-background-radius: 8; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.2), 3, 0, 0, 1);");
                
                final int f = fila;
                final int c = columna;
                
                boton.setOnAction(actionEvent -> {
                    if (boton.getText().isEmpty() && !tablero.juegoTerminado()) {
                        hacerMovimiento(f, c, boton);
                    }
                });
                
                boton.setOnMouseEntered(e -> {
                    if (boton.getText().isEmpty()) {
                        boton.setStyle("-fx-background-color: rgba(255,255,255,0.3); -fx-text-fill: white; -fx-border-color: rgba(255,255,255,1); -fx-border-width: 2; -fx-border-radius: 8; -fx-background-radius: 8; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.3), 5, 0, 0, 2);");
                    }
                });
                
                boton.setOnMouseExited(e -> {
                    if (boton.getText().isEmpty()) {
                        boton.setStyle("-fx-background-color: rgba(255,255,255,0.15); -fx-text-fill: white; -fx-border-color: rgba(255,255,255,0.8); -fx-border-width: 2; -fx-border-radius: 8; -fx-background-radius: 8; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.2), 3, 0, 0, 1);");
                    }
                });
                
                botonesTablero[fila][columna] = boton;
                gridTablero.add(boton, columna, fila);
            }
        }
    }

    private void hacerMovimiento(int fila, int columna, Button boton) {
        if (tablero.hacerMovimiento(fila, columna)) {
            boton.setText(tablero.getJugadorActual().getSimbolo());
            
            String simbolo = tablero.getJugadorActual().getSimbolo();
            if (simbolo.equals("X")) {
                boton.setStyle("-fx-background-color: rgba(255,107,107,0.3); -fx-text-fill: #ff6b6b; -fx-border-color: #ff6b6b; -fx-border-width: 2; -fx-border-radius: 8; -fx-background-radius: 8; -fx-effect: dropshadow(gaussian, rgba(255,107,107,0.4), 5, 0, 0, 2); -fx-font-weight: bold;");
            } else {
                boton.setStyle("-fx-background-color: rgba(102,126,234,0.3); -fx-text-fill: #667eea; -fx-border-color: #667eea; -fx-border-width: 2; -fx-border-radius: 8; -fx-background-radius: 8; -fx-effect: dropshadow(gaussian, rgba(102,126,234,0.4), 5, 0, 0, 2); -fx-font-weight: bold;");
            }
            
            String ganador = tablero.verificarGanador();
            if (ganador != null) {
                manejarVictoria(ganador);
                return;
            }
            
            if (tablero.esEmpate()) {
                manejarEmpate();
                return;
            }
            
            tablero.cambiarTurno();
            actualizarTurno();
            
            if (tablero.getJugadorActual().isEsComputadora()) {
                hacerMovimientoComputadora();
            }
        }
    }

    private void hacerMovimientoComputadora() {
        for (int fila = 0; fila < 3; fila++) {
            for (int columna = 0; columna < 3; columna++) {
                if (botonesTablero[fila][columna].getText().isEmpty()) {
                    hacerMovimiento(fila, columna, botonesTablero[fila][columna]);
                    return;
                }
            }
        }
    }

    private void manejarVictoria(String simboloGanador) {
        Jugador ganador = tablero.obtenerJugadorPorSimbolo(simboloGanador);
        ganador.incrementarVictoria();
        actualizarPuntajes();
        
        estadisticaService.actualizarEstadisticasPartida(
            jugador1.getNombre(), 
            jugador2.getNombre(), 
            ganador.getNombre(), 
            false
        );
        
        deshabilitarTablero();
        mostrarDialogoResultado("¡" + ganador.getNombre() + " ha ganado!", true);
    }

    private void manejarEmpate() {
        empates++;
        if (appController != null) {
            appController.setEmpates(empates);
        }
        
        estadisticaService.actualizarEstadisticasPartida(
            jugador1.getNombre(), 
            jugador2.getNombre(), 
            "", 
            true
        );
        
        deshabilitarTablero();
        mostrarDialogoResultado("El juego ha terminado en empate.", false);
    }

    private void mostrarDialogoResultado(String mensaje, boolean hayGanador) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/resultado-dialogo.fxml"));
            Scene scene = new Scene(loader.load());
            ResultadoDialogoController controller = loader.getController();
            controller.setMensaje(mensaje);
            controller.setOnContinuar(() -> preguntarGuardarYReiniciar());
            controller.setOnMenu(() -> preguntarGuardarYSalir(() -> {
                Stage stage = (Stage) gridTablero.getScene().getWindow();
                stage.close();
                cargarPantallaPrincipal();
            }));
            controller.setOnSalir(() -> preguntarGuardarYSalir(this::salirAplicacion));
            Stage dialogStage = new Stage();
            dialogStage.initModality(Modality.APPLICATION_MODAL);
            dialogStage.initOwner(gridTablero.getScene().getWindow());
            dialogStage.setScene(scene);
            dialogStage.setResizable(false);
            dialogStage.setTitle(hayGanador ? "¡Victoria!" : "¡Empate!");
            dialogStage.showAndWait();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void preguntarGuardarYReiniciar() {
        if (!partidaGuardada) {
            mostrarDialogoConfirmacion("¿Deseas guardar la partida antes de continuar?", () -> {
                guardarPartida();
                reiniciarJuego();
            }, this::reiniciarJuego);
        } else {
            reiniciarJuego();
        }
    }

    private void deshabilitarTablero() {
        for (int fila = 0; fila < 3; fila++) {
            for (int columna = 0; columna < 3; columna++) {
                botonesTablero[fila][columna].setDisable(true);
            }
        }
    }

    private void habilitarTablero() {
        for (int fila = 0; fila < 3; fila++) {
            for (int columna = 0; columna < 3; columna++) {
                botonesTablero[fila][columna].setDisable(false);
            }
        }
    }

    private void actualizarTurno() {
        Jugador jugadorActual = tablero.getJugadorActual();
        lblturno.setText("Turno: " + jugadorActual.getNombre());
        
        try {
            if (jugadorActual == jugador1) {
                imgJugador1.setImage(new Image(getClass().getResourceAsStream("/images/JugadorAuxillar.png")));
                imgJugador2.setImage(new Image(getClass().getResourceAsStream("/images/JugadorCirculo.png")));
            } else {
                imgJugador1.setImage(new Image(getClass().getResourceAsStream("/images/JugadorEquis.png")));
                imgJugador2.setImage(new Image(getClass().getResourceAsStream("/images/JugadorAuxillar.png")));
            }
        } catch (Exception e) {
            System.out.println("Error al cargar imágenes: " + e.getMessage());
        }
    }

    private void actualizarPuntajes() {
        lblpuntajejugadoruno.setText(String.valueOf(jugador1.getWins()));
        lblpuntajejugadordos.setText(String.valueOf(jugador2.getWins()));
    }

    private void reiniciarJuego() {
        for (int fila = 0; fila < 3; fila++) {
            for (int columna = 0; columna < 3; columna++) {
                botonesTablero[fila][columna].setText("");
                botonesTablero[fila][columna].setStyle("-fx-background-color: rgba(255,255,255,0.15); -fx-text-fill: white; -fx-border-color: rgba(255,255,255,0.8); -fx-border-width: 2; -fx-border-radius: 8; -fx-background-radius: 8; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.2), 3, 0, 0, 1);");
            }
        }
        
        tablero.reiniciarJuego();
        
        habilitarTablero();
        
        actualizarTurno();
    }

    void inicio(Jugador jugador1, Jugador jugador2) {
        this.jugador1 = jugador1;
        this.jugador2 = jugador2;
        this.tablero = new Tablero(jugador1, jugador2);
        this.empates = 0;
        
        lblnombrejugadoruno.setText(jugador1.getNombre());
        lblnombrejugadordos.setText(jugador2.getNombre());
        
        actualizarPuntajes();
        
        configurarImagenesIniciales();
        
        actualizarTurno();
    }

    private void configurarImagenesIniciales() {
        try {
            if (jugador1.getSimbolo().equals("X")) {
                imgJugador1.setImage(new Image(getClass().getResourceAsStream("/images/JugadorEquis.png")));
                imgJugador2.setImage(new Image(getClass().getResourceAsStream("/images/JugadorCirculo.png")));
            } else {
                imgJugador1.setImage(new Image(getClass().getResourceAsStream("/images/JugadorCirculo.png")));
                imgJugador2.setImage(new Image(getClass().getResourceAsStream("/images/JugadorEquis.png")));
            }
        } catch (Exception e) {
            System.out.println("Error al cargar imágenes iniciales: " + e.getMessage());
        }
    }

    public void restaurarEstadoDesdeJson(String estadoJson) {
        try {
            JSONObject obj = new JSONObject(estadoJson);
            String[][] t = tablero.getTablero();
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    t[i][j] = obj.getJSONArray("tablero").getJSONArray(i).getString(j);
                    botonesTablero[i][j].setText(t[i][j]);
                }
            }
            String turno = obj.getString("turno");
            if (!tablero.getJugadorActual().getSimbolo().equals(turno)) {
                tablero.cambiarTurno();
            }
            if (obj.has("empates")) {
                empates = obj.getInt("empates");
            }
            actualizarTurno();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void mostrarConfirmacionReiniciar() {
        mostrarDialogoConfirmacion("¿Deseas reiniciar la partida?", this::reiniciarJuego);
    }

    private void preguntarGuardarYSalir(Runnable onSalir) {
        if (!partidaGuardada) {
            mostrarDialogoConfirmacion("¿Deseas guardar la partida antes de salir del tablero?", () -> {
                guardarPartida();
                onSalir.run();
            }, onSalir);
        } else {
            onSalir.run();
        }
    }

    private void mostrarConfirmacionSalir() {
        preguntarGuardarYSalir(this::salirAplicacion);
    }

    private void mostrarDialogoConfirmacion(String mensaje, Runnable onAceptar) {
        mostrarDialogoConfirmacion(mensaje, onAceptar, null);
    }

    private void mostrarDialogoConfirmacion(String mensaje, Runnable onAceptar, Runnable onCancelar) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/resultado-dialogo.fxml"));
            Scene scene = new Scene(loader.load());
            ResultadoDialogoController controller = loader.getController();
            controller.setMensaje(mensaje);
            controller.setOnContinuar(() -> {
                if (onAceptar != null) onAceptar.run();
            });
            controller.setOnMenu(() -> {
                if (onCancelar != null) onCancelar.run();
            });
            controller.setOnSalir(() -> {});
            Stage dialogStage = new Stage();
            dialogStage.initModality(Modality.APPLICATION_MODAL);
            dialogStage.initOwner(gridTablero.getScene().getWindow());
            dialogStage.setScene(scene);
            dialogStage.setResizable(false);
            dialogStage.setTitle("Confirmación");
            dialogStage.showAndWait();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void guardarPartida() {
        try {
            String estadoJson = serializarEstadoPartida();
            Partida partida = new Partida(
                LocalDate.now(),
                LocalTime.now(),
                jugador1.getNombre(),
                jugador2.getNombre(),
                jugador1.getVictorias(),
                jugador2.getVictorias(),
                empates,
                estadoJson
            );
            partidaService.guardarPartida(partida);
            partidaGuardada = true;
            mostrarDialogoResultado("¡Partida guardada exitosamente!", false);
        } catch (Exception e) {
            mostrarDialogoResultado("Error al guardar la partida.", false);
        }
    }

    private String serializarEstadoPartida() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        sb.append("\"tablero\":[");
        String[][] t = tablero.getTablero();
        for (int i = 0; i < 3; i++) {
            sb.append("[");
            for (int j = 0; j < 3; j++) {
                sb.append("\"").append(t[i][j]).append("\"");
                if (j < 2) sb.append(",");
            }
            sb.append("]");
            if (i < 2) sb.append(",");
        }
        sb.append("],");
        sb.append("\"turno\":\"").append(tablero.getJugadorActual().getSimbolo()).append("\",");
        sb.append("\"empates\":").append(empates);
        sb.append("}");
        return sb.toString();
    }

    private void salirAplicacion() {
        Platform.exit();
        System.exit(0);
    }

    public void setEmpates(int empates) {
        this.empates = empates;
    }
}