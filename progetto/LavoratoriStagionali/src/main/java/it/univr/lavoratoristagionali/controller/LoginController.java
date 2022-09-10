package it.univr.lavoratoristagionali.controller;

import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXPasswordField;
import io.github.palexdev.materialfx.controls.MFXTextField;
import it.univr.lavoratoristagionali.controller.enums.View;
import it.univr.lavoratoristagionali.model.Dao.LoginDao;
import it.univr.lavoratoristagionali.model.Dao.LoginDaoImpl;
import it.univr.lavoratoristagionali.types.Login;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Classe controller del login
 */
public class LoginController extends Controller implements Initializable {

    // Elementi FXML
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

    private LoginDao loginDao;

    public LoginController(){

    }

    /**
     * Metodo chiamato dopo la creazione della scena (e di tutti i suoi elementi) da parte di JavaFX.
     * Istanzia il DAO login.
     *
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loginDao = new LoginDaoImpl();
    }

    /**
     * Mateodo callback richiamato da JavaFX al click del pulsante di login.
     * Estrae il contenuto di username e password, e li manda al DAO login.
     * Se le credenziali sono valide, si passa alla vista del menu principale, altrimenti si avvisa l'utente delle credenziali errate.
     *
     * @param actionEvent parametro evento JavaFX
     */
    @FXML
    private void loginEvent(ActionEvent actionEvent){
        String username = usernameField.getText();
        String password = passwordField.getText();
        // Se le credenziali sono valide
        if(loginDao.verificaLogin(new Login(username, password))){
            displayError(false);
            switchScene(getStageFromEvent(actionEvent), View.MAIN_MENU);
        }
        else{
            displayError(true);
        }
    }

    /**
     * Mostra (o nasconde) il label di errore del login.
     *
     * @param status valore booleano, indica se mostrare il label di errore (true) oppure no (false)
     */
    private void displayError(boolean status){
        errorLabel.setVisible(status);
    }
}
