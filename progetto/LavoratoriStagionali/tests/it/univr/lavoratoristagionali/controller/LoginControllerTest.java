package it.univr.lavoratoristagionali.controller;

import io.github.palexdev.materialfx.controls.MFXButton;
import it.univr.lavoratoristagionali.controller.enums.View;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputControl;
import javafx.stage.Stage;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.testfx.api.FxAssert;
import org.testfx.api.FxAssertContext;
import org.testfx.framework.junit.ApplicationTest;
import org.testfx.matcher.base.WindowMatchers;

import static org.junit.Assert.*;

public class LoginControllerTest extends ApplicationTest {
    @Override
    public void start(Stage stage) throws Exception{
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(View.LOGIN.getLabel()));
        Scene scene = new Scene(fxmlLoader.load(), 1280, 720);
        stage.setScene(scene);
        stage.show();
    }

    @Before
    public void setup() throws Exception{

    }

    @After
    public void tearDown() throws Exception{

    }

    /**
     * Inserimento di credenziali corrette
     *
     * Comportamento aspettato: cambio di scena al menù principale
     */
    @Test
    public void tryCorrectLogin(){
        clickOn("#usernameField").write("aaa");
        clickOn("#passwordField").write("bbb");
        clickOn("#submitButton");
        FxAssert.verifyThat("#modificaLavoratoreButton", (Button button) -> button.isVisible());
    }

    /**
     * Inserimento di credenziali errate
     *
     * Comportamento aspettato: nessun cambio di scena, comparsa label errore
     */
    @Test
    public void tryWrongLogin(){
        clickOn("#usernameField");
        write("q1097hewur");
        clickOn("#passwordField");
        write("q1097hewur");
        clickOn("#submitButton");
        FxAssert.verifyThat("#errorLabel", (Label label) -> label.isVisible());
    }

    /**
     * Compilazione del campo password, ma non del campo username
     *
     * Comportamento aspettato: nessun cambio di scena
     */
    @Test
    public void tryMissingUsername(){
        clickOn("#passwordField");
        write("bbb");
        clickOn("#submitButton");
        FxAssert.verifyThat("#errorLabel", (Label label) -> label.isVisible());
    }

    /**
     * Compilazione del campo username, ma non del campo password
     *
     * Comportamento aspettato: nessun cambio di scena
     */
    @Test
    public void tryMissingPassword(){
        clickOn("#usernameField");
        write("aaa");
        clickOn("#submitButton");
        FxAssert.verifyThat("#errorLabel", (Label label) -> label.isVisible());
    }

    /**
     * Nessun campo compilato
     *
     * Comportamento aspettato: nessun cambio di scena
     */
    @Test
    public void tryEmptyFields(){
        clickOn("#submitButton");
        FxAssert.verifyThat("#errorLabel", (Label label) -> label.isVisible());
    }
}