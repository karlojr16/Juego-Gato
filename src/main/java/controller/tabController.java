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

// Controlador de la pantalla del tablero
public class tabController {

    // Objetos del modelo
    private Tablero tablero;
    private Jugador jugador1;
    private Jugador jugador2;
    
    // Matriz de botones del tablero
    private Button[][] botonesTablero;

    // Elementos de la interfaz
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

    // Método para cerrar la aplicación
    @FXML
    void click(MouseEvent event) {
        Platform.exit();
        System.exit(0);
    }

    // Método para cambiar color cuando el mouse entra
    @FXML
    void enter(MouseEvent event) {
        lblCierre.setStyle("-fx-text-fill: red;");
    }

    // Método para cambiar color cuando el mouse sale
    @FXML
    void exited(MouseEvent event) {
        lblCierre.setStyle("-fx-text-fill: white;");
    }

    // Método para iniciar un juego nuevo
    @FXML
    void juegoNuevo(javafx.event.ActionEvent event) {
        reiniciarJuego();
    }

    // Método para volver a la pantalla principal
    @FXML
    void volver(javafx.event.ActionEvent event) {
        preguntarGuardarYSalir(() -> cerrarYVolver(event));
    }

    private void cerrarYVolver(javafx.event.ActionEvent event) {
        // Cerrar ventana actual
        final Node node = (Node) event.getSource();
        final Stage stage = (Stage) node.getScene().getWindow();
        stage.close();
        // Cargar la pantalla principal
        cargarPantallaPrincipal();
    }

    // Método para cargar la pantalla principal
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

    // Método que se ejecuta al inicializar la pantalla
    @FXML
    void initialize() {
        // Crear la matriz de botones
        botonesTablero = new Button[3][3];
        
        // Configurar el grid del tablero
        gridTablero.setHgap(10);
        gridTablero.setVgap(10);
        gridTablero.setAlignment(Pos.CENTER);

        // Crear los botones del tablero
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

    // Método para crear los botones del tablero
    private void crearBotonesTablero() {
        for (int fila = 0; fila < 3; fila++) {
            for (int columna = 0; columna < 3; columna++) {
                Button boton = new Button();
                boton.setFont(Font.font("Arial", FontWeight.BOLD, 40));
                boton.setPrefSize(80, 80);
                boton.setStyle("-fx-background-color: rgba(255,255,255,0.15); -fx-text-fill: white; -fx-border-color: rgba(255,255,255,0.8); -fx-border-width: 2; -fx-border-radius: 8; -fx-background-radius: 8; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.2), 3, 0, 0, 1);");
                
                final int f = fila;
                final int c = columna;
                
                // Configurar el evento del botón
                boton.setOnAction(actionEvent -> {
                    if (boton.getText().isEmpty() && !tablero.juegoTerminado()) {
                        hacerMovimiento(f, c, boton);
                    }
                });
                
                // Efecto hover para los botones
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

    // Método para hacer un movimiento
    private void hacerMovimiento(int fila, int columna, Button boton) {
        if (tablero.hacerMovimiento(fila, columna)) {
            // Poner el símbolo en el botón
            boton.setText(tablero.getJugadorActual().getSimbolo());
            
            // Cambiar el estilo del botón cuando se hace un movimiento
            String simbolo = tablero.getJugadorActual().getSimbolo();
            if (simbolo.equals("X")) {
                boton.setStyle("-fx-background-color: rgba(255,107,107,0.3); -fx-text-fill: #ff6b6b; -fx-border-color: #ff6b6b; -fx-border-width: 2; -fx-border-radius: 8; -fx-background-radius: 8; -fx-effect: dropshadow(gaussian, rgba(255,107,107,0.4), 5, 0, 0, 2); -fx-font-weight: bold;");
            } else {
                boton.setStyle("-fx-background-color: rgba(102,126,234,0.3); -fx-text-fill: #667eea; -fx-border-color: #667eea; -fx-border-width: 2; -fx-border-radius: 8; -fx-background-radius: 8; -fx-effect: dropshadow(gaussian, rgba(102,126,234,0.4), 5, 0, 0, 2); -fx-font-weight: bold;");
            }
            
            // Verificar si hay ganador
            String ganador = tablero.verificarGanador();
            if (ganador != null) {
                manejarVictoria(ganador);
                return;
            }
            
            // Verificar empate
            if (tablero.esEmpate()) {
                manejarEmpate();
                return;
            }
            
            // Cambiar turno
            tablero.cambiarTurno();
            actualizarTurno();
            
            // Si el siguiente jugador es computadora, hacer su movimiento
            if (tablero.getJugadorActual().isEsComputadora()) {
                hacerMovimientoComputadora();
            }
        }
    }

    // Método para que la computadora haga su movimiento
    private void hacerMovimientoComputadora() {
        // Lógica simple: buscar la primera casilla vacía
        for (int fila = 0; fila < 3; fila++) {
            for (int columna = 0; columna < 3; columna++) {
                if (botonesTablero[fila][columna].getText().isEmpty()) {
                    hacerMovimiento(fila, columna, botonesTablero[fila][columna]);
                    return;
                }
            }
        }
    }

    // Método para manejar cuando hay un ganador
    private void manejarVictoria(String simboloGanador) {
        Jugador ganador = tablero.obtenerJugadorPorSimbolo(simboloGanador);
        ganador.incrementarVictoria();
        actualizarPuntajes();
        
        // Actualizar estadísticas en la base de datos
        estadisticaService.actualizarEstadisticasPartida(
            jugador1.getNombre(), 
            jugador2.getNombre(), 
            ganador.getNombre(), 
            false
        );
        
        deshabilitarTablero();
        mostrarDialogoResultado("¡" + ganador.getNombre() + " ha ganado!", true);
    }

    // Método para manejar cuando hay empate
    private void manejarEmpate() {
        empates++;
        if (appController != null) {
            appController.setEmpates(empates);
        }
        
        // Actualizar estadísticas en la base de datos (empate)
        estadisticaService.actualizarEstadisticasPartida(
            jugador1.getNombre(), 
            jugador2.getNombre(), 
            "", 
            true
        );
        
        deshabilitarTablero();
        mostrarDialogoResultado("El juego ha terminado en empate.", false);
    }

    // Método para mostrar el diálogo personalizado de resultado
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

    // Preguntar si se desea guardar la partida antes de reiniciar
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

    // Método para deshabilitar el tablero
    private void deshabilitarTablero() {
        for (int fila = 0; fila < 3; fila++) {
            for (int columna = 0; columna < 3; columna++) {
                botonesTablero[fila][columna].setDisable(true);
            }
        }
    }

    // Método para habilitar el tablero
    private void habilitarTablero() {
        for (int fila = 0; fila < 3; fila++) {
            for (int columna = 0; columna < 3; columna++) {
                botonesTablero[fila][columna].setDisable(false);
            }
        }
    }

    // Método para actualizar el turno
    private void actualizarTurno() {
        Jugador jugadorActual = tablero.getJugadorActual();
        lblturno.setText("Turno: " + jugadorActual.getNombre());
        
        // Cambiar imagen del jugador en turno
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

    // Método para actualizar los puntajes
    private void actualizarPuntajes() {
        lblpuntajejugadoruno.setText(String.valueOf(jugador1.getWins()));
        lblpuntajejugadordos.setText(String.valueOf(jugador2.getWins()));
    }

    // Método para reiniciar el juego
    private void reiniciarJuego() {
        // Limpiar el tablero y restaurar estilos
        for (int fila = 0; fila < 3; fila++) {
            for (int columna = 0; columna < 3; columna++) {
                botonesTablero[fila][columna].setText("");
                // Restaurar el estilo original de los botones
                botonesTablero[fila][columna].setStyle("-fx-background-color: rgba(255,255,255,0.15); -fx-text-fill: white; -fx-border-color: rgba(255,255,255,0.8); -fx-border-width: 2; -fx-border-radius: 8; -fx-background-radius: 8; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.2), 3, 0, 0, 1);");
            }
        }
        
        // Reiniciar la lógica del tablero
        tablero.reiniciarJuego();
        
        // Habilitar el tablero
        habilitarTablero();
        
        // Actualizar el turno
        actualizarTurno();
    }

    // Método para inicializar el juego con los jugadores
    void inicio(Jugador jugador1, Jugador jugador2) {
        this.jugador1 = jugador1;
        this.jugador2 = jugador2;
        this.tablero = new Tablero(jugador1, jugador2);
        this.empates = 0;
        
        // Configurar los nombres
        lblnombrejugadoruno.setText(jugador1.getNombre());
        lblnombrejugadordos.setText(jugador2.getNombre());
        
        // Configurar los puntajes
        actualizarPuntajes();
        
        // Configurar las imágenes iniciales
        configurarImagenesIniciales();
        
        // Actualizar el turno inicial
        actualizarTurno();
    }

    // Método para configurar las imágenes iniciales
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

    // Método para restaurar el estado del tablero y turno desde un JSON
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
            controller.setOnSalir(() -> {}); // No hace nada
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
        // Serialización simple a JSON (puedes usar una librería como Gson para mejor formato)
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