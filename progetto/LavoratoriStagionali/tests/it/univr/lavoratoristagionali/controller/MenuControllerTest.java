package it.univr.lavoratoristagionali.controller;

import io.github.palexdev.materialfx.controls.MFXTextField;
import it.univr.lavoratoristagionali.controller.enums.View;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.testfx.api.FxAssert;
import org.testfx.framework.junit.ApplicationTest;

import static org.junit.Assert.*;

public class MenuControllerTest extends ApplicationTest {
    @Override
    public void start(Stage stage) throws Exception{
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(View.MAIN_MENU.getLabel()));
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

    @Test
    public void backToLogin() throws Exception{
        clickOn("#esciButton");
        FxAssert.verifyThat("#usernameField", (MFXTextField textField) -> textField.isVisible());
    }

    @Test
    public void goToInserisciLavoratore() throws Exception{
        clickOn("#inserisciLavoratoreButton");
        FxAssert.verifyThat("#nomeLavoratore", (MFXTextField textField) -> textField.isVisible());
    }

    @Test
    public void goToModificaLavoratore() throws Exception{
        clickOn("#modificaLavoratoreButton");
        FxAssert.verifyThat("#nome", (MFXTextField textField) -> textField.isVisible());
    }

    @Test
    public void goToRicercaLavoratore() throws Exception{
        clickOn("#effettuaRicercaButton");
        FxAssert.verifyThat("#nomeRisultato", (MFXTextField textField) -> textField.isVisible());
    }
}