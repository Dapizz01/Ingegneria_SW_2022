package it.univr.lavoratoristagionali.controller;

import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXPasswordField;
import io.github.palexdev.materialfx.controls.MFXTextField;
import it.univr.lavoratoristagionali.controller.enums.View;
import it.univr.lavoratoristagionali.types.Login;
import it.univr.lavoratoristagionali.model.Dao.LoginDao;
import it.univr.lavoratoristagionali.model.Dao.LoginDaoImpl;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginController extends Controller implements Initializable {

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

    LoginDao loginDao;


    public LoginController(){

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loginDao = new LoginDaoImpl();
    }

    @FXML
    protected void loginEvent(ActionEvent actionEvent){
        System.out.println("loginEvent fired");
        String username = usernameField.getText();
        String password = passwordField.getText();
        if(loginDao.verificaLogin(new Login(username, password))){
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
