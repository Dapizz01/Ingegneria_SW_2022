package it.univr.lavoratoristagionali.controller;

import it.univr.lavoratoristagionali.controller.enums.View;
import it.univr.lavoratoristagionali.types.Lavoratore;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Classe estesa da ogni controller, contiene metodi per facilitare lo scambio di scene
 */
public class Controller {

    /**
     * Cambia la scena attualmente mostrata
     *
     * @param stage stage sul quale viene sostituita la scena
     * @param target indica la vista che si vuole mettere come scena
     */
    public void switchScene(Stage stage, View target){
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(target.getLabel()));
            Scene scene = new Scene(fxmlLoader.load(), 1280, 720);
            stage.setScene(scene);
        }
        catch(IOException ioe){
            ioe.printStackTrace();
            System.out.println("Failed to view resource \"" + target.getLabel() + "\" in the project");
        }
    }

    /**
     * Cambia la scena attualmente mostrata, e passa alla nuova scena un oggetto di tipo Lavoratore
     *
     * @warning
     *
     * @param stage stage sul quale viene sostituita la scena
     * @param target indica la vista che si vuole mettere come scena
     * @param lavoratore è un oggetto di tipo Lavoratore passato alla vista attraverso il metodo setLavoratoreBase()
     */
    public void switchScene(Stage stage, View target, Lavoratore lavoratore){
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(target.getLabel()));
            Scene scene = new Scene(fxmlLoader.load(), 1280, 720);
            if(fxmlLoader.getController() instanceof ModificaLavoratoreController){
                ModificaLavoratoreController view = fxmlLoader.getController();
                view.setLavoratoreBase(lavoratore);
            }
            else{
                throw new IllegalArgumentException("The target view \"" + target.getLabel() + "\" should not be called by this method, use the other switchScene with only 2 parameters.");
            }
            stage.setScene(scene);
        }
        catch(IOException ioe){
            ioe.printStackTrace();
            System.out.println("Failed to locate view \"" + target.getLabel() + "\" in the project");
        }
    }

    /**
     * Ritorna lo stage dato un evento ActionEvent di JavaFX
     *
     * @param actionEvent oggetto ritornato da un evento JavaFX
     * @return Stage corrente
     */
    public Stage getStageFromEvent(ActionEvent actionEvent){
        return (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
    }
}
