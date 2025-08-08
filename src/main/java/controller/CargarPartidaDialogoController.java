package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import model.Partida;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import repository.PartidaRepository;

public class CargarPartidaDialogoController {
    @FXML private TableView<Partida> tablaPartidas;
    @FXML private TableColumn<Partida, String> colFecha;
    @FXML private TableColumn<Partida, String> colHora;
    @FXML private TableColumn<Partida, String> colJugadorX;
    @FXML private TableColumn<Partida, String> colJugadorO;
    @FXML private Button btnCargar;
    @FXML private Button btnCancelar;

    private Partida partidaSeleccionada;

    @FXML
    private void initialize() {
        colFecha.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getFecha().toString()));
        colHora.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getHora().toString()));
        colJugadorX.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getNombreJugadorX()));
        colJugadorO.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getNombreJugadorO()));

        PartidaRepository repo = new PartidaRepository();
        ObservableList<Partida> partidas = FXCollections.observableArrayList(repo.listarPartidas());
        tablaPartidas.setItems(partidas);

        btnCargar.setOnAction(e -> {
            partidaSeleccionada = tablaPartidas.getSelectionModel().getSelectedItem();
            cerrarVentana();
        });
        btnCancelar.setOnAction(e -> {
            partidaSeleccionada = null;
            cerrarVentana();
        });
    }

    public Partida getPartidaSeleccionada() {
        return partidaSeleccionada;
    }

    private void cerrarVentana() {
        Stage stage = (Stage) tablaPartidas.getScene().getWindow();
        stage.close();
    }
} 