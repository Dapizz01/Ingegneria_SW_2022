package it.univr.lavoratoristagionali.controller;

import it.univr.lavoratoristagionali.controller.enums.View;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;

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

    @Test
    public void tryCorrectLogin(){
        clickOn("#usernameField");
        write("aaa");
        clickOn("#passwordField");
        write("bbb");
        clickOn("#submitButton");
    }
}