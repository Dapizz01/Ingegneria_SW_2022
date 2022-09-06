package it.univr.lavoratoristagionali.controller;

import io.github.palexdev.materialfx.controls.MFXListView;
import io.github.palexdev.materialfx.controls.MFXTextField;
import it.univr.lavoratoristagionali.controller.enums.View;
import it.univr.lavoratoristagionali.model.Dao.*;
import it.univr.lavoratoristagionali.types.*;
import javafx.fxml.FXMLLoader;
import javafx.geometry.VerticalDirection;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.testfx.api.FxAssert;
import org.testfx.framework.junit.ApplicationTest;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class MenuModificaLavoratoreControllerTest extends ApplicationTest {

    @Override
    public void start(Stage stage) throws Exception{
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(View.MENU_MODIFICA_LAVORATORE.getLabel()));
        Scene scene = new Scene(fxmlLoader.load(), 1280, 720);
        stage.setScene(scene);
        stage.show();
    }

    @Before
    public void setUp() {
        LavoratoriDao lavoratoriDao = new LavoratoriDaoImpl();
        ComuniDao comuniDao = new ComuniDaoImpl();
        LingueDao lingueDao = new LingueDaoImpl();
        lavoratoriDao.addLavoratore(new Lavoratore(
                -1,
                "nomeTest",
                "cognomeTest",
                comuniDao.getComuni().get(0),
                comuniDao.getComuni().get(0),
                (int) LocalDate.of(2000, 1, 1).toEpochDay(),
                lingueDao.getLingue().get(0),
                "aaa@aaa.com",
                "0123456789",
                false,
                new ArrayList<Esperienza>(),
                lingueDao.getLingue(),
                List.of(new Contatto(
                        -1,
                        "test",
                        "test",
                        "0123456789",
                        "aaa@bbb.ccc"
                )),
                new ArrayList<Patente>(),
                new ArrayList<Disponibilita>()
        ));
    }

    @After
    public void tearDown() {
        LavoratoriDao lavoratoriDao = new LavoratoriDaoImpl();
    }

    @Test
    public void updateDeleteLavoratore(){
        MFXListView<Lavoratore> listaLavoratori = lookup("#listaLavoratori").query();

        clickOn("#nome").write("nomeTest");
        clickOn("#cognome").write("cognomeTest");
        clickOn("#cercaLavoratore");

        interact(() -> {
            listaLavoratori.getSelectionModel().selectIndex(0);
        });

        clickOn("#modificaLavoratore");

        FxAssert.verifyThat("#nomeLavoratore", (MFXTextField textField) -> textField.isVisible());

        clickOn("#nomeLavoratore").write("Test");
        moveTo("#ritornaMenu");
        scroll(50, VerticalDirection.DOWN);
        clickOn("#modificaLavoratore");

        FxAssert.verifyThat("#nome", (MFXTextField textField) -> textField.isVisible());

        clickOn("#nome").write("nomeTestTest");
        clickOn("#cognome").write("cognomeTest");
        clickOn("#cercaLavoratore");

        MFXListView<Lavoratore> listaLavoratoriNew = lookup("#listaLavoratori").query();

        interact(() -> {
            listaLavoratoriNew.getSelectionModel().selectIndex(0);
        });

        clickOn("#eliminaLavoratore");

        clickOn("#cercaLavoratore");

        FxAssert.verifyThat("#listaLavoratori", (MFXListView<Lavoratore> listView) -> listView.getItems().isEmpty());
    }
}