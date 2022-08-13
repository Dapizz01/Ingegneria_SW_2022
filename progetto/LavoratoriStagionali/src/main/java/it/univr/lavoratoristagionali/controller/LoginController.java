package it.univr.lavoratoristagionali.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController extends Controller{

    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Button submitButton;
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

    /*private void goToMenu(Stage currentStage) {
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("fxml/menuView.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 320, 240);
            currentStage.setScene(scene);
        }
        catch(IOException ioe){
            System.out.println("Failed to locate resource \"fxml/menuView.fxml\" in the project");
        }
    }*/

    private void displayError(boolean status){
        errorLabel.setVisible(status);
    }
}
