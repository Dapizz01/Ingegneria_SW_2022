package it.univr.lavoratoristagionali.controller;

import it.univr.lavoratoristagionali.controller.enums.Resolution;
import it.univr.lavoratoristagionali.controller.enums.View;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class TestMain extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(View.LOGIN.getLabel()));
        Scene scene = new Scene(fxmlLoader.load(), Resolution.WIDTH.getLabel(), Resolution.HEIGHT.getLabel());
        stage.setTitle("Gestione Lavoratori");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
