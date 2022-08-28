package it.univr.lavoratoristagionali.controller;

import it.univr.lavoratoristagionali.controller.enums.ControllerMode;
import it.univr.lavoratoristagionali.controller.enums.View;
import it.univr.lavoratoristagionali.types.Lavoratore;
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
            System.out.println(ioe.getMessage());
            System.out.println("Failed to locate resource \"" + target.getLabel() + "\" in the project");
        }
    }

    public void switchScene(Stage stage, View target, Lavoratore lavoratore){
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(target.getLabel()));
            Scene scene = new Scene(fxmlLoader.load(), 1280, 720);
            // if(fxmlLoader.getController() instanceof ModificaLavoratoreController){
                ModificaLavoratoreController view = fxmlLoader.getController();
                view.setLavoratoreBase(lavoratore);
            // }
            stage.setScene(scene);
        }
        catch(IOException ioe){
            ioe.printStackTrace();
            System.out.println(ioe.getMessage());
            System.out.println("Failed to locate resource \"" + target.getLabel() + "\" in the project");
        }
    }

    public Stage getStageFromEvent(ActionEvent actionEvent){
        return (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
    }
}
