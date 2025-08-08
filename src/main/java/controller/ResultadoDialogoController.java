package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class ResultadoDialogoController {
    @FXML private Label lblMensaje;
    @FXML private Button btnContinuar;
    @FXML private Button btnMenu;
    @FXML private Button btnSalir;

    private Runnable onContinuar;
    private Runnable onMenu;
    private Runnable onSalir;

    public void setMensaje(String mensaje) {
        lblMensaje.setText(mensaje);
    }

    public void setOnContinuar(Runnable onContinuar) {
        this.onContinuar = onContinuar;
    }

    public void setOnMenu(Runnable onMenu) {
        this.onMenu = onMenu;
    }

    public void setOnSalir(Runnable onSalir) {
        this.onSalir = onSalir;
    }

    @FXML
    private void initialize() {
        btnContinuar.setOnAction(e -> {
            if (onContinuar != null) onContinuar.run();
            cerrarVentana();
        });
        btnMenu.setOnAction(e -> {
            if (onMenu != null) onMenu.run();
            cerrarVentana();
        });
        btnSalir.setOnAction(e -> {
            if (onSalir != null) onSalir.run();
            cerrarVentana();
        });
    }

    private void cerrarVentana() {
        Stage stage = (Stage) lblMensaje.getScene().getWindow();
        stage.close();
    }
} 