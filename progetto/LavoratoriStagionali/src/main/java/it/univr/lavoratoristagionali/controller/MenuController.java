package it.univr.lavoratoristagionali.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class MenuController extends Controller{
    @FXML
    private Button modificaLavoratoreButton;
    @FXML
    private Button effettuaRicercaButton;
    @FXML
    private Button esciButton;
    @FXML
    private Button inserisciLavoratoreButton;

    public MenuController(){}

    @FXML
    private void exitEvent(ActionEvent actionEvent) {
        System.out.println("exitEvent fired");
        switchScene(getStageFromEvent(actionEvent), View.LOGIN);
    }
}
