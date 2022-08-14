package it.univr.lavoratoristagionali.controller;

import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXPasswordField;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class LoginController extends Controller{

    @FXML
    private MFXTextField usernameField;
    @FXML
    private MFXPasswordField passwordField;
    @FXML
    private MFXButton submitButton;
    @FXML
    private Label loginTitleLabel;
    @FXML
    private Label errorLabel;


    public LoginController(){

    }

    @FXML
    protected void loginEvent(ActionEvent actionEvent){
        System.out.println("loginEvent fired");
        String username = usernameField.getText();
        String password = passwordField.getText();
        if(username.equals(password)){ // TODO: sostituire controllo con controllo da Model
            displayError(false);
            switchScene(getStageFromEvent(actionEvent), View.MAIN_MENU);
        }
        else{
            displayError(true);
        }
    }

    private void displayError(boolean status){
        errorLabel.setVisible(status);
    }
}
