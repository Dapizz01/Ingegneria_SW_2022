package it.univr.lavoratoristagionali.controller;

import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

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
    }

    // TODO: aggiungere modificaLavoratore e ricercaLavoratore
}
