package it.univr.lavoratoristagionali.controller;

import io.github.palexdev.materialfx.controls.MFXButton;
import it.univr.lavoratoristagionali.controller.enums.View;
import javafx.fxml.FXMLLoader;
import javafx.geometry.VerticalDirection;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.testfx.api.FxAssert;
import org.testfx.framework.junit.ApplicationTest;

import static org.junit.Assert.*;

public class InserisciLavoratoriControllerTest extends ApplicationTest {
    @Override
    public void start(Stage stage) throws Exception{
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(View.INSERISCI_LAVORATORE.getLabel()));
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
    public void emptyFields(){
        point(10, 10);
        scroll(50, VerticalDirection.DOWN);
        clickOn("#inviaLavoratore");
        // scroll(100, VerticalDirection.UP);
        FxAssert.verifyThat("#nomeLavoratoreError", (Label label) -> label.isVisible());
    }

    @Test
    public void backToMenu(){
        clickOn("#ritornaMenu");
        FxAssert.verifyThat("#inserisciLavoratoreButton", (MFXButton button) -> button.isVisible());
    }
}