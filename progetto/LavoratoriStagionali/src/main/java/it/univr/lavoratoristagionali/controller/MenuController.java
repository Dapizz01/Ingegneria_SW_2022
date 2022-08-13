package it.univr.lavoratoristagionali.controller;

import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

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

    @FXML
    private void exitEvent(ActionEvent actionEvent) {
        System.out.println("exitEvent fired");
        switchScene(getStageFromEvent(actionEvent), View.LOGIN);
    }
}
