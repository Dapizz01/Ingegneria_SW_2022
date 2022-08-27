package it.univr.lavoratoristagionali.controller;

import io.github.palexdev.materialfx.controls.MFXButton;
import it.univr.lavoratoristagionali.controller.enums.ControllerMode;
import it.univr.lavoratoristagionali.controller.enums.View;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;

public class MenuController extends Controller{
    @FXML
    private MFXButton modificaLavoratoreButton;
    @FXML
    private MFXButton effettuaRicercaButton;
    @FXML
    private MFXButton esciButton;
    @FXML
    private MFXButton inserisciLavoratoreButton;

    public MenuController(){}

    // TODO: aggiungere icone alla UI (https://github.com/palexdev/MaterialFX/blob/main/materialfx/src/main/java/io/github/palexdev/materialfx/font/FontResources.java)

    @FXML
    private void exitEvent(ActionEvent actionEvent) {
        System.out.println("exitEvent fired");
        switchScene(getStageFromEvent(actionEvent), View.LOGIN);
    }

    @FXML
    private void goToInserisciLavoratore(ActionEvent actionEvent){
        System.out.println("goToInserisciLavoratore fired");
        switchScene(getStageFromEvent(actionEvent), View.INSERISCI_LAVORATORE);
        // switchToForm(getStageFromEvent(actionEvent), ControllerMode.INSERT, -1);
    }

    @FXML
    private void goToMenuModificaLavoratore(ActionEvent actionEvent) {
        switchScene(getStageFromEvent(actionEvent), View.MENU_MODIFICA_LAVORATORE);
    }

    @FXML
    private void goToEffettuaRicerca(ActionEvent actionEvent) {
        // switchScene(getStageFromEvent(actionEvent), View.MENU_MODIFICA_LAVORATORE);
    }


    // TODO: aggiungere modificaLavoratore e ricercaLavoratore
}
