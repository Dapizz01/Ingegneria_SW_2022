package it.univr.lavoratoristagionali.controller;

import io.github.palexdev.materialfx.controls.*;
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
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

public class RicercaLavoratoreControllerTest extends ApplicationTest {

    private Lavoratore lavoratore1, lavoratore2, lavoratore3;

    @Override
    public void start(Stage stage) throws Exception{
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(View.RICERCA_LAVORATORE.getLabel()));
        Scene scene = new Scene(fxmlLoader.load(), 1280, 720);
        stage.setScene(scene);
        stage.show();
    }

    @Before
    public void setUp() {
        LavoratoriDao lavoratoriDao = new LavoratoriDaoImpl();
        ComuniDao comuniDao = new ComuniDaoImpl();
        LingueDao lingueDao = new LingueDaoImpl();
        PatentiDao patentiDao = new PatentiDaoImpl();
        SpecializzazioniDao specializzazioniDao = new SpecializzazioniDaoImpl();

        lavoratore1 = new Lavoratore(
                -1,
                "nomeLavoratore1",
                "cognomeLavoratore1",
                comuniDao.getComuni().get(0),
                comuniDao.getComuni().get(0),
                (int) LocalDate.of(1979, 1, 1).toEpochDay(),
                lingueDao.getLingue().get(1),
                "aaa@aaa.com",
                "0123456789",
                true,
                List.of(new Esperienza(
                        -1,
                        "aziendaTest",
                        3,
                        (int) LocalDate.of(2000, 1, 1).toEpochDay(),
                        (int) LocalDate.of(2000, 5, 1).toEpochDay(),
                        comuniDao.getComuni().get(0),
                        specializzazioniDao.getSpecializzazioni().get(1)
                )),
                List.of(lingueDao.getLingue().get(1), lingueDao.getLingue().get(2)),
                List.of(new Contatto(
                        -1,
                        "test",
                        "test",
                        "0123456789",
                        "aaa@bbb.ccc"
                )),
                List.of(patentiDao.getPatenti().get(1), patentiDao.getPatenti().get(3)),
                new ArrayList<Disponibilita>()
        );

        lavoratore2 = new Lavoratore(
                -1,
                "nomeLavoratore2",
                "cognomeLavoratore2",
                comuniDao.getComuni().get(0),
                comuniDao.getComuni().get(1),
                (int) LocalDate.of(1989, 1, 1).toEpochDay(),
                lingueDao.getLingue().get(1),
                "aaa@aaa.com",
                "0123456789",
                true,
                List.of(new Esperienza(
                        -1,
                        "aziendaTest",
                        3,
                        (int) LocalDate.of(2000, 1, 1).toEpochDay(),
                        (int) LocalDate.of(2000, 5, 1).toEpochDay(),
                        comuniDao.getComuni().get(0),
                        specializzazioniDao.getSpecializzazioni().get(1)
                )),
                List.of(lingueDao.getLingue().get(1), lingueDao.getLingue().get(2)),
                List.of(new Contatto(
                        -1,
                        "test",
                        "test",
                        "0123456789",
                        "aaa@bbb.ccc"
                )),
                List.of(patentiDao.getPatenti().get(0), patentiDao.getPatenti().get(5)),
                List.of(new Disponibilita(
                        (int) LocalDate.of(2040, 1, 1).toEpochDay(),
                        (int) LocalDate.of(2040, 12, 30).toEpochDay(),
                        comuniDao.getComuni().get(3)
                ))
        );

        lavoratore3 = new Lavoratore(
                -1,
                "nomeLavoratore3",
                "cognomeLavoratore3",
                comuniDao.getComuni().get(0),
                comuniDao.getComuni().get(1),
                (int) LocalDate.of(1995, 1, 1).toEpochDay(),
                lingueDao.getLingue().get(0),
                "aaa@aaa.com",
                "0123456789",
                false,
                new ArrayList<Esperienza>(),
                List.of(lingueDao.getLingue().get(3)),
                List.of(new Contatto(
                        -1,
                        "test",
                        "test",
                        "0123456789",
                        "aaa@bbb.ccc"
                )),
                List.of(patentiDao.getPatenti().get(3), patentiDao.getPatenti().get(5)),
                List.of(new Disponibilita(
                        (int) LocalDate.of(2040, 1, 1).toEpochDay(),
                        (int) LocalDate.of(2040, 12, 30).toEpochDay(),
                        comuniDao.getComuni().get(2)
                ))
        );

        lavoratoriDao.addLavoratore(lavoratore1);
        lavoratoriDao.addLavoratore(lavoratore2);
        lavoratoriDao.addLavoratore(lavoratore3);
    }

    @After
    public void tearDown() {
    }

    @Test
    public void tryQuery1(){
        MFXCheckListView<Lingua> lingueLavoratore = lookup("#lingueLavoratore").query();
        MFXCheckListView<Comune> comuneLavoratore = lookup("#comuneLavoratore").query();
        MFXCheckbox automunito = lookup("#automunito").query();
        MFXButton ricercaAND = lookup("#ricercaAND").query();
        MFXListView<Lavoratore> listaLavoratori = lookup("#listaLavoratori").query();

        interact(() -> {
            lingueLavoratore.getSelectionModel().selectIndex(1);
            lingueLavoratore.getSelectionModel().selectIndex(2);
            comuneLavoratore.getSelectionModel().selectIndex(0);
            automunito.setSelected(true);
        });

        clickOn("#ricercaAND");
        moveTo("#automunito");
        scroll(20, VerticalDirection.DOWN);

        FxAssert.verifyThat("#listaLavoratori", (MFXListView<Lavoratore> list) -> {
            if(list.getItems().isEmpty())
                return false;
            for(Lavoratore lavoratore : list.getItems()){
                if(!lavoratore.getNomeLavoratore().equals(lavoratore1.getNomeLavoratore()) && !lavoratore.getNomeLavoratore().equals(lavoratore2.getNomeLavoratore()))
                    return false;
            }
            return true;
        });

    }
}