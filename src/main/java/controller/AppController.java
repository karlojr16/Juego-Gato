package controller;

import application.App;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.Jugador;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import model.Partida;

public class AppController {
    
    @FXML
    private Label welcomeText;

    @FXML
    private TextField txtJugador1;

    @FXML
    private TextField txtJugador2;

    @FXML
    private Label lblJugador2;

    @FXML
    private RadioButton rbHumano;

    @FXML
    private RadioButton rbComputadora;

    private ToggleGroup grupoModoJuego;

    @FXML
    private ImageView imgCirculo;

    @FXML
    private ImageView imgEquis;

    @FXML
    private Label lblSimboloSeleccionado;

    @FXML
    private Button btnIniciar;

    @FXML
    private Button btnRanking;

    @FXML
    private Label lblCierre;

    @FXML
    private Label lblEmpates;
    private int empates = 0;

    private String simboloSeleccionado = "O";

    public void click(MouseEvent mouseEvent) {
        Platform.exit();
        System.exit(0);
    }

    public void enter(MouseEvent mouseEvent) {
        lblCierre.setStyle("-fx-text-fill: red;");
    }

    public void exited(MouseEvent mouseEvent) {
        lblCierre.setStyle("-fx-text-fill: white;");
    }

    @FXML
    void cambiarModoJuego(ActionEvent event) {
        if (rbComputadora.isSelected()) {
            txtJugador2.setVisible(false);
            lblJugador2.setVisible(false);
            txtJugador2.setText("Computadora");
        } else {
            txtJugador2.setVisible(true);
            lblJugador2.setVisible(true);
            txtJugador2.setText("Jugador 2");
        }
    }

    @FXML
    void seleccionarCirculo(MouseEvent event) {
        simboloSeleccionado = "O";
        lblSimboloSeleccionado.setText("Símbolo: O");
        imgCirculo.setStyle("-fx-effect: dropshadow(gaussian, #ffd700, 10, 0, 0, 0);");
        imgEquis.setStyle("");
    }

    @FXML
    void seleccionarEquis(MouseEvent event) {
        simboloSeleccionado = "X";
        lblSimboloSeleccionado.setText("Símbolo: X");
        imgEquis.setStyle("-fx-effect: dropshadow(gaussian, #ffd700, 10, 0, 0, 0);");
        imgCirculo.setStyle("");
    }

    public void iniciar(ActionEvent actionEvent) {
        String nombreJugador1 = txtJugador1.getText().trim();
        String nombreJugador2 = txtJugador2.getText().trim();

        if (nombreJugador1.isEmpty() || nombreJugador2.isEmpty()) {
            mostrarAlerta("Error", "Por favor ingresa los nombres de ambos jugadores.");
            return;
        }

        Jugador jugador1 = new Jugador(nombreJugador1, 0, simboloSeleccionado, false);
        
        String simboloJugador2 = simboloSeleccionado.equals("X") ? "O" : "X";
        boolean esComputadora = rbComputadora.isSelected();
        Jugador jugador2 = new Jugador(nombreJugador2, 0, simboloJugador2, esComputadora);

        final Node node = (Node) actionEvent.getSource();
        final Stage stage = (Stage) node.getScene().getWindow();
        stage.close();

        cargarPantallaTablero(jugador1, jugador2);
    }

    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    @FXML
    public void abrirRanking() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/application/ranking.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 700, 500);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.centerOnScreen();
            stage.setResizable(false);
            stage.setTitle("🏆 Ranking y Estadísticas");
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error al abrir la pantalla de ranking: " + e.getMessage());
        }
    }

    private void cargarPantallaTablero(Jugador jugador1, Jugador jugador2) {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("tablero.fxml"));
        try {
            Scene scene = new Scene(fxmlLoader.load(), 500, 600);
            Stage stage = new Stage();
            stage.setScene(scene);

            stage.centerOnScreen();
            stage.initStyle(StageStyle.UNDECORATED);
            stage.setResizable(false);

            tabController tbc = fxmlLoader.getController();
            tbc.inicio(jugador1, jugador2);

            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setEmpates(int empates) {
        this.empates = empates;
        if (lblEmpates != null) {
            lblEmpates.setText("Empates: " + empates);
        }
    }

    @FXML
    void initialize() {
        grupoModoJuego = new ToggleGroup();
        rbHumano.setToggleGroup(grupoModoJuego);
        rbComputadora.setToggleGroup(grupoModoJuego);

        configurarCamposTexto();

        seleccionarCirculo(null);

        Platform.runLater(this::preguntarCargarPartida);
    }

    private void preguntarCargarPartida() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "¿Deseas cargar una partida almacenada?", ButtonType.YES, ButtonType.NO);
        alert.setTitle("Cargar partida");
        alert.setHeaderText(null);
        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.YES) {
                cargarPartidaDesdeDialogo();
            }
        });
    }

    private void cargarPartidaDesdeDialogo() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/cargar-partida-dialogo.fxml"));
            Scene scene = new Scene(loader.load());
            Stage dialogStage = new Stage();
            dialogStage.initModality(Modality.APPLICATION_MODAL);
            dialogStage.initOwner(lblCierre.getScene().getWindow());
            dialogStage.setScene(scene);
            dialogStage.setResizable(false);
            dialogStage.setTitle("Cargar partida");
            CargarPartidaDialogoController controller = loader.getController();
            dialogStage.showAndWait();
            Partida partida = controller.getPartidaSeleccionada();
            if (partida != null) {
                cargarPantallaTableroDesdePartida(partida);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void cargarPantallaTableroDesdePartida(Partida partida) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("tablero.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 500, 600);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.centerOnScreen();
            stage.initStyle(StageStyle.UNDECORATED);
            stage.setResizable(false);
            tabController tbc = fxmlLoader.getController();
            Jugador jugador1 = new Jugador(partida.getNombreJugadorX(), partida.getMarcadorX(), "X", false);
            Jugador jugador2 = new Jugador(partida.getNombreJugadorO(), partida.getMarcadorO(), "O", false);
            tbc.inicio(jugador1, jugador2);
            tbc.setEmpates(partida.getEmpates());
            tbc.restaurarEstadoDesdeJson(partida.getEstadoJson());
            stage.show();
            Stage mainStage = (Stage) lblCierre.getScene().getWindow();
            mainStage.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void configurarCamposTexto() {
        txtJugador1.focusedProperty().addListener((observableValue, aBoolean, t1) -> {
            if (t1 && txtJugador1.getText().equals("Jugador 1")) {
                txtJugador1.setText("");
            }
        });

        txtJugador2.focusedProperty().addListener((observableValue, aBoolean, t1) -> {
            if (t1 && txtJugador2.getText().equals("Jugador 2")) {
                txtJugador2.setText("");
            }
        });

        txtJugador1.focusedProperty().addListener((observableValue, aBoolean, t1) -> {
            if (!t1 && txtJugador1.getText().isEmpty()) {
                txtJugador1.setText("Jugador 1");
            }
        });

        txtJugador2.focusedProperty().addListener((observableValue, aBoolean, t1) -> {
            if (!t1 && txtJugador2.getText().isEmpty()) {
                txtJugador2.setText("Jugador 2");
            }
        });
    }
}