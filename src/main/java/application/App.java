package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class App extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("app.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 500, 400);
        //stage.setTitle("Hello!");
        stage.setScene(scene);

        //centrado en mi pantalla
        stage.centerOnScreen();
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setResizable(false);

        stage.show();
    }

    public static void main(String[] args) {

        launch();
    }
}