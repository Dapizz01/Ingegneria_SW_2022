package it.univr.lavoratoristagionali.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Controller {
    public void switchScene(Stage stage, View target){
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(target.getLabel()));
            Scene scene = new Scene(fxmlLoader.load(), 1280, 720);
            stage.setScene(scene);
        }
        catch(IOException ioe){
            ioe.printStackTrace();
            System.out.println("Failed to locate resource \"" + target.getLabel() + "\" in the project");
        }
    }

    public Stage getStageFromEvent(ActionEvent actionEvent){
        return (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
    }
}
