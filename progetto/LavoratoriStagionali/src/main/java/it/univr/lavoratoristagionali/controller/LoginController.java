package it.univr.lavoratoristagionali.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginController {

    @FXML
    private TextField usernameLabel;
    @FXML
    private PasswordField passwordLabel;
    @FXML
    private Button submitButton;
    @FXML
    private Label loginTitleLabel;
    @FXML
    private Label errorLabel;


    public LoginController(){

    }

    @FXML
    protected void loginEvent(){

    }
}
