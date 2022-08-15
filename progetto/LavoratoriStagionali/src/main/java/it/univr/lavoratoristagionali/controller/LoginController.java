package it.univr.lavoratoristagionali.controller;

import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXPasswordField;
import io.github.palexdev.materialfx.controls.MFXTextField;
import it.univr.lavoratoristagionali.types.Login;
import it.univr.lavoratoristagionali.model.Dao.LoginDao;
import it.univr.lavoratoristagionali.model.Dao.LoginDaoImpl;
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
        LoginDao loginDao = new LoginDaoImpl();
        if(loginDao.verificaLogin(new Login(username, password))){ // TODO: sostituire controllo con controllo da Model
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
