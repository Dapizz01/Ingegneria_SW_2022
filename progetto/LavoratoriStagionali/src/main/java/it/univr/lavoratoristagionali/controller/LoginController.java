package it.univr.lavoratoristagionali.controller;

import io.github.palexdev.materialfx.controls.MFXPasswordField;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

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
        if(username.equals(password)){
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
