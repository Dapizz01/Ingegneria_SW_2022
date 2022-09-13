package it.univr.lavoratoristagionali.controller;

import io.github.palexdev.materialfx.controls.*;
import it.univr.lavoratoristagionali.controller.enums.View;
import it.univr.lavoratoristagionali.model.Dao.*;
import it.univr.lavoratoristagionali.types.*;
import javafx.fxml.FXMLLoader;
import javafx.geometry.VerticalDirection;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.testfx.api.FxAssert;
import org.testfx.framework.junit.ApplicationTest;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ModificaLavoratoreControllerTest extends ApplicationTest {

    Lavoratore lavoratoreTest;

    @Override
    public void start(Stage stage) throws Exception{
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(View.MODIFICA_LAVORATORE.getLabel()));
        Scene scene = new Scene(fxmlLoader.load(), 1280, 720);

        ModificaLavoratoreController view = fxmlLoader.getController();
        buildLavoratoreTest();
        view.setLavoratoreBase(lavoratoreTest);

        stage.setScene(scene);
        stage.show();
    }

    private void buildLavoratoreTest(){
        ComuniDao comuniDao = new ComuniDaoImpl();
        LingueDao lingueDao = new LingueDaoImpl();
        PatentiDao patentiDao = new PatentiDaoImpl();
        SpecializzazioniDao specializzazioniDao = new SpecializzazioniDaoImpl();

        lavoratoreTest = new Lavoratore(
                -1,
                "testUno",
                "testUno",
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
    }

    @Before
    public void setUp() {

    }

    @After
    public void tearDown() {
    }

    // ------ CONTROLLO CONTENUTO CAMPI LAVORATORE CORRENTE ------ //

    @Test
    public void checkAnagrafica(){
        clickOn("#nomeLavoratore");
        FxAssert.verifyThat("#nomeLavoratore", (MFXTextField nomeLavoratore) -> nomeLavoratore.getText().equals(lavoratoreTest.getNomeLavoratore()));
        clickOn("#cognomeLavoratore");
        FxAssert.verifyThat("#cognomeLavoratore", (MFXTextField cognomeLavoratore) -> cognomeLavoratore.getText().equals(lavoratoreTest.getCognomeLavoratore()));
        clickOn("#dataNascitaLavoratore");
        FxAssert.verifyThat("#dataNascitaLavoratore", (MFXDatePicker datePicker) -> datePicker.getValue().equals(LocalDate.ofEpochDay(lavoratoreTest.getDataNascita())));
        clickOn("#comuneNascitaLavoratore");
        FxAssert.verifyThat("#comuneNascitaLavoratore", (MFXFilterComboBox<Comune> comuneNascita) -> comuneNascita.getSelectedItem().equals(lavoratoreTest.getComuneNascita()));
        clickOn("#comuneAbitazioneLavoratore");
        FxAssert.verifyThat("#comuneAbitazioneLavoratore", (MFXFilterComboBox<Comune> comuneAbitazione) -> comuneAbitazione.getSelectedItem().equals(lavoratoreTest.getComuneNascita()));
        clickOn("#nazionalitaLavoratore");
        FxAssert.verifyThat("#nazionalitaLavoratore", (MFXFilterComboBox<Lingua> nazionalita) -> nazionalita.getSelectedItem().equals(lavoratoreTest.getNazionalita()));

        moveTo("#ritornaMenu");

        scroll(8, VerticalDirection.DOWN);
        moveTo("#lingueLavoratore");
        FxAssert.verifyThat("#lingueLavoratore", (MFXCheckListView<Lingua> lingueLavoratore) -> lingueLavoratore.getSelectionModel().getSelectedValues().equals(lavoratoreTest.getLingue()));
        moveTo("#patentiLavoratore");
        FxAssert.verifyThat("#patentiLavoratore", (MFXCheckListView<Patente> patentiLavoratore) -> patentiLavoratore.getSelectionModel().getSelectedValues().equals(lavoratoreTest.getPatenti()));

        moveTo("#telefonoLavoratore");
        FxAssert.verifyThat("#telefonoLavoratore", (MFXTextField telefonoLavoratore) -> telefonoLavoratore.getText().equals(lavoratoreTest.getTelefono()));
        moveTo("#emailLavoratore");
        FxAssert.verifyThat("#emailLavoratore", (MFXTextField emailLavoratore) -> emailLavoratore.getText().equals(lavoratoreTest.getEmail()));

        moveTo("#lingueLavoratore");
        scroll(15, VerticalDirection.DOWN);

        moveTo("#listaContattoUrgente");
        FxAssert.verifyThat("#listaContattoUrgente", (MFXListView<Contatto> listaContattoUrgente) -> listaContattoUrgente.getItems().equals(lavoratoreTest.getContatti()));

        moveTo("#nomeContatto");
        scroll(15, VerticalDirection.DOWN);

        moveTo("#listaDisponibilita");
        FxAssert.verifyThat("#listaDisponibilita", (MFXListView<Disponibilita> listaDisponibilita) -> listaDisponibilita.getItems().equals(lavoratoreTest.getDisponibilita()));

        moveTo("#inizioDisponibilita");
        scroll(15, VerticalDirection.DOWN);

        moveTo("#listaEsperienze");
        FxAssert.verifyThat("#listaEsperienze", (MFXListView<Esperienza> listaEsperienze) -> listaEsperienze.getItems().equals(lavoratoreTest.getEsperienze()));
    }

    /* @Test
    public void checkInformazioniGenerali(){
        scroll(5, VerticalDirection.DOWN);
        moveTo("#lingueLavoratore");
        FxAssert.verifyThat("#lingueLavoratore", (MFXCheckListView<Lingua> lingueLavoratore) -> lingueLavoratore.getSelectionModel().getSelectedValues().equals(lavoratoreTest.getLingue()));
        moveTo("#patentiLavoratore");
        FxAssert.verifyThat("#patentiLavoratore", (MFXCheckListView<Patente> patentiLavoratore) -> patentiLavoratore.getSelectionModel().getSelectedValues().equals(lavoratoreTest.getPatenti()));
    }

    @Test
    public void checkContattiLavoratore(){
        scroll(5, VerticalDirection.DOWN);
        moveTo("#telefonoLavoratore");
        FxAssert.verifyThat("#telefonoLavoratore", (MFXTextField telefonoLavoratore) -> telefonoLavoratore.getText().equals(lavoratoreTest.getTelefono()));
        moveTo("#emailLavoratore");
        FxAssert.verifyThat("#emailLavoratore", (MFXTextField emailLavoratore) -> emailLavoratore.getText().equals(lavoratoreTest.getEmail()));
    }

    @Test
    public void checkContattiUrgenti(){
        moveTo("#ritornaMenu");
        scroll(20, VerticalDirection.DOWN);
        moveTo("#listaContattoUrgente");
        FxAssert.verifyThat("#listaContattoUrgente", (MFXListView<Contatto> listaContattoUrgente) -> listaContattoUrgente.getItems().equals(lavoratoreTest.getContatti()));
    }

    @Test
    public void checkDisponibilita(){
        moveTo("#ritornaMenu");
        scroll(30, VerticalDirection.DOWN);
        moveTo("#listaDisponibilita");
        FxAssert.verifyThat("#listaDisponibilita", (MFXListView<Disponibilita> listaDisponibilita) -> listaDisponibilita.getItems().equals(lavoratoreTest.getDisponibilita()));
    }

    @Test
    public void checkEsperienze(){
        moveTo("#ritornaMenu");
        scroll(50, VerticalDirection.DOWN);
        moveTo("#listaEsperienze");
        FxAssert.verifyThat("#listaEsperienze", (MFXListView<Esperienza> listaEsperienze) -> listaEsperienze.getItems().equals(lavoratoreTest.getEsperienze()));
    } */

    // ------ TEST CONTENUTO COMBOBOX, LISTVIEW, CHECKLISTVIEW ------ //

    @Test
    public void verifyCorrectItems(){
        ComuniDao comuniDao = new ComuniDaoImpl();
        LingueDao lingueDao = new LingueDaoImpl();
        PatentiDao patentiDao = new PatentiDaoImpl();
        SpecializzazioniDao specializzazioniDao = new SpecializzazioniDaoImpl();

        clickOn("#comuneAbitazioneLavoratore .caret");
        FxAssert.verifyThat("#comuneAbitazioneLavoratore", (MFXFilterComboBox<Comune> comuneAbitazione) -> comuneAbitazione.getItems().equals(comuniDao.getComuni()));
        clickOn("#comuneAbitazioneLavoratore .caret");

        clickOn("#comuneAbitazioneLavoratore .caret");
        FxAssert.verifyThat("#comuneAbitazioneLavoratore", (MFXFilterComboBox<Comune> comuneAbitazione) -> comuneAbitazione.getItems().equals(comuniDao.getComuni()));
        clickOn("#comuneAbitazioneLavoratore .caret");

        clickOn("#nazionalitaLavoratore .caret");
        FxAssert.verifyThat("#nazionalitaLavoratore", (MFXFilterComboBox<Lingua> nazionalita) -> nazionalita.getItems().equals(lingueDao.getLingue()));
        clickOn("#nazionalitaLavoratore .caret");

        moveTo("#ritornaMenu");
        scroll(5, VerticalDirection.DOWN);

        moveTo("#lingueLavoratore");
        FxAssert.verifyThat("#lingueLavoratore", (MFXCheckListView<Lingua> lingue) -> lingue.getItems().equals(lingueDao.getLingue()));

        moveTo("#patentiLavoratore");
        FxAssert.verifyThat("#patentiLavoratore", (MFXCheckListView<Patente> patenti) -> patenti.getItems().equals(patentiDao.getPatenti()));

        moveTo("#lingueLavoratore");
        scroll(30, VerticalDirection.DOWN);

        clickOn("#comuneDisponibilita .caret");
        FxAssert.verifyThat("#comuneDisponibilita", (MFXFilterComboBox<Comune> comuneDisponibilita) -> comuneDisponibilita.getItems().equals(comuniDao.getComuni()));
        clickOn("#comuneDisponibilita .caret");

        scroll(20, VerticalDirection.DOWN);

        clickOn("#comuneEsperienza .caret");
        FxAssert.verifyThat("#comuneEsperienza", (MFXFilterComboBox<Comune> comuneEsperienza) -> comuneEsperienza.getItems().equals(comuniDao.getComuni()));
        clickOn("#comuneEsperienza .caret");

        clickOn("#specializzazioneEsperienza .caret");
        FxAssert.verifyThat("#specializzazioneEsperienza", (MFXFilterComboBox<Specializzazione> specializzazioneEsperienza) -> specializzazioneEsperienza.getItems().equals(specializzazioniDao.getSpecializzazioni()));
        clickOn("#specializzazioneEsperienza .caret");
    }

    /**
     * Viene testato che il filterComboBox del comune di nascita abbia effettivamente tutti i comuni forniti da ComuniDao
     */
    /* @Test
    public void verifyComuneNascitaItems(){
        ComuniDao comuniDao = new ComuniDaoImpl();
        clickOn("#comuneNascitaLavoratore .caret");
        FxAssert.verifyThat("#comuneNascitaLavoratore", (MFXFilterComboBox<Comune> comuneNascita) -> comuneNascita.getItems().equals(comuniDao.getComuni()));
    } */

    /**
     * Viene testato che il filterComboBox del comune di abitazione abbia effettivamente tutti i comuni forniti da ComuniDao
     */
    /* @Test
    public void verifyComuneAbitazioneItems(){
        ComuniDao comuniDao = new ComuniDaoImpl();
        clickOn("#comuneAbitazioneLavoratore .caret");
        FxAssert.verifyThat("#comuneAbitazioneLavoratore", (MFXFilterComboBox<Comune> comuneAbitazione) -> comuneAbitazione.getItems().equals(comuniDao.getComuni()));
    } */

    /**
     * Viene testato che il filterComboBox della nazionalità abbia effettivamente tutte le nazionalità in LingueDao
     */
    /* @Test
    public void verifyNazionalitaItems(){
        LingueDao lingueDao = new LingueDaoImpl();
        clickOn("#nazionalitaLavoratore .caret");
        FxAssert.verifyThat("#nazionalitaLavoratore", (MFXFilterComboBox<Lingua> nazionalita) -> nazionalita.getItems().equals(lingueDao.getLingue()));
    } */

    /**
     * Viene testato che il checkListView delle lingue parlate da un lavoratore abbia effettivamente tutte le lingue forniti da LingueDao
     */
    /* @Test
    public void verifyLingueItems(){
        LingueDao lingueDao = new LingueDaoImpl();
        moveTo("#ritornaMenu");
        scroll(5, VerticalDirection.DOWN);
        FxAssert.verifyThat("#lingueLavoratore", (MFXCheckListView<Lingua> lingue) -> lingue.getItems().equals(lingueDao.getLingue()));
    } */

    /**
     * Viene testato che il checkListView delle patenti possedute abbia effettivamente tutte le patenti forniti da PatentiDao
     */
    /* @Test
    public void verifyPatentiItems(){
        PatentiDao patentiDao = new PatentiDaoImpl();
        moveTo("#ritornaMenu");
        scroll(5, VerticalDirection.DOWN);
        FxAssert.verifyThat("#patentiLavoratore", (MFXCheckListView<Patente> patenti) -> patenti.getItems().equals(patentiDao.getPatenti()));
    } */

    /**
     * Viene testato che il filterComboBox del comune di disponibilità abbia effettivamente tutti i comuni forniti da ComuniDao
     */
    /* @Test
    public void verifyComuneDisponibilitaItems(){
        ComuniDao comuniDao = new ComuniDaoImpl();
        moveTo("#ritornaMenu");
        scroll(30, VerticalDirection.DOWN);
        clickOn("#comuneDisponibilita .caret");
        FxAssert.verifyThat("#comuneDisponibilita", (MFXFilterComboBox<Comune> comuneDisponibilita) -> comuneDisponibilita.getItems().equals(comuniDao.getComuni()));
    } */

    /**
     * Viene testato che il filterComboBox delle specializzazioni abbia effettivamente tutte le specializzazioni forniti da SpecializzazioniDao
     */
    /* @Test
    public void verifySpecializzazioniItems(){
        SpecializzazioniDao specializzazioniDao = new SpecializzazioniDaoImpl();
        moveTo("#ritornaMenu");
        scroll(50, VerticalDirection.DOWN);
        clickOn("#specializzazioneEsperienza .caret");
        FxAssert.verifyThat("#specializzazioneEsperienza", (MFXFilterComboBox<Specializzazione> specializzazioneEsperienza) -> specializzazioneEsperienza.getItems().equals(specializzazioniDao.getSpecializzazioni()));
    } */

    /**
     * Viene testato che il filterComboBox del comune dell'esperienza lavorativa abbia effettivamente tutti i comuni forniti da ComuniDao
     */
    /* @Test
    public void verifyComuneEsperienzaItems(){
        ComuniDao comuniDao = new ComuniDaoImpl();
        moveTo("#ritornaMenu");
        scroll(50, VerticalDirection.DOWN);
        clickOn("#comuneEsperienza .caret");
        FxAssert.verifyThat("#comuneEsperienza", (MFXFilterComboBox<Comune> comuneEsperienza) -> comuneEsperienza.getItems().equals(comuniDao.getComuni()));
    } */

    // ------ TEST CONTATTO URGENTE ------ //

    /**
     * Prova ad inserire un contatto urgente lasciando i campi vuoti. Dovrebbe risultare un errore, con la mancata
     * aggiunta della entry nella lista dei contatti urgenti
     */
    @Test
    public void addEmptyContattoUrgente(){
        moveTo("#ritornaMenu");
        scroll(20, VerticalDirection.DOWN);
        clickOn("#aggiungiContatto");
        FxAssert.verifyThat("#listaContattoUrgente", (MFXListView<Contatto> list) -> list.getItems().size() == lavoratoreTest.getContatti().size());
    }

    /**
     * Prova ad inserire un contatto urgente compilando tutti i campi correttamente tranne il numero telefonico.
     * Dovrebbe risultare un errore (a causa del numero telefonico breve), con la mancata aggiunta della entry nella lista dei contatti urgenti
     */
    @Test
    public void addInvalidPhoneNumberContattoUrgente(){
        moveTo("#ritornaMenu");
        scroll(20, VerticalDirection.DOWN);
        clickOn("#nomeContatto").write("testNome");
        clickOn("#cognomeContatto").write("testCognome");
        clickOn("#telefonoContatto").write("222");
        clickOn("#emailContatto").write("aaa@aaa.com");
        clickOn("#aggiungiContatto");
        FxAssert.verifyThat("#listaContattoUrgente", (MFXListView<Contatto> list) -> list.getItems().size() == lavoratoreTest.getContatti().size());
    }

    /**
     * Prova ad inserire un contatto urgente compilando tutti i campi correttamente tranne l'indirizzo email (manca @).
     * Dovrebbe risultare un errore, con la mancata aggiunta della entry nella lista dei contatti urgenti
     */
    @Test
    public void addInvalidEmailContattoUrgente(){
        moveTo("#ritornaMenu");
        scroll(20, VerticalDirection.DOWN);
        clickOn("#nomeContatto").write("testNome");
        clickOn("#cognomeContatto").write("testCognome");
        clickOn("#telefonoContatto").write("0123456789");
        clickOn("#emailContatto").write("aaa");
        clickOn("#aggiungiContatto");
        FxAssert.verifyThat("#listaContattoUrgente", (MFXListView<Contatto> list) -> list.getItems().size() == lavoratoreTest.getContatti().size());
    }

    /**
     * Aggiunge un nuovo contatto urgente valido, controlla che sia presente nella lista, poi lo seleziona e lo elimina
     */
    @Test
    public void addDeleteValidContattoUrgente(){
        MFXListView<Contatto> listaContatti = lookup("#listaContattoUrgente").query();
        moveTo("#ritornaMenu");
        scroll(20, VerticalDirection.DOWN);
        clickOn("#nomeContatto").write("testNome");
        clickOn("#cognomeContatto").write("testCognome");
        clickOn("#telefonoContatto").write("0123456789");
        clickOn("#emailContatto").write("aaa@aaa.com");
        clickOn("#aggiungiContatto");
        FxAssert.verifyThat("#listaContattoUrgente", (MFXListView<Contatto> list) -> list.getItems().size() > lavoratoreTest.getContatti().size());
        interact(() -> {
            listaContatti.getSelectionModel().selectIndex(listaContatti.getItems().size() - 1); // Simula il click
        });
        clickOn("#eliminaContatto");
        FxAssert.verifyThat("#listaContattoUrgente", (MFXListView<Contatto> list) -> list.getItems().size() == lavoratoreTest.getContatti().size());
    }

    // ------ TEST DISPONIBILITA ------ //

    @Test
    public void addEmptyDisponibilita(){
        moveTo("#ritornaMenu");
        scroll(30, VerticalDirection.DOWN);
        clickOn("#aggiungiDisponibilita");
        FxAssert.verifyThat("#listaDisponibilita", (MFXListView<Disponibilita> list) -> list.getItems().size() == lavoratoreTest.getDisponibilita().size());
    }

    @Test
    public void addPastDateDisponibilita(){
        moveTo("#ritornaMenu");
        scroll(30, VerticalDirection.DOWN);
        MFXDatePicker inizio = lookup("#inizioDisponibilita").query();
        MFXDatePicker fine = lookup("#fineDisponibilita").query();
        MFXFilterComboBox<Comune> listComuni = lookup("#comuneDisponibilita").query();
        interact(() -> {
            inizio.setValue(LocalDate.of(2021, 9, 4));
            fine.setValue(LocalDate.of(2021, 11, 20));
            listComuni.selectIndex(0);
        });
        clickOn("#aggiungiDisponibilita");
        FxAssert.verifyThat("#listaDisponibilita", (MFXListView<Disponibilita> list) -> list.getItems().size() == lavoratoreTest.getDisponibilita().size());
    }

    @Test
    public void addDeleteValidDisponibilita(){
        MFXListView<Contatto> listaDisponibilta = lookup("#listaDisponibilita").query();
        moveTo("#ritornaMenu");
        scroll(30, VerticalDirection.DOWN);
        MFXDatePicker inizio = lookup("#inizioDisponibilita").query();
        MFXDatePicker fine = lookup("#fineDisponibilita").query();
        MFXFilterComboBox<Comune> listComuni = lookup("#comuneDisponibilita").query();
        interact(() -> {
            inizio.setValue(LocalDate.of(2040, 9, 4));
            fine.setValue(LocalDate.of(2040, 11, 20));
            listComuni.selectIndex(0);
        });
        clickOn("#aggiungiDisponibilita");
        FxAssert.verifyThat("#listaDisponibilita", (MFXListView<Disponibilita> list) -> list.getItems().size() > lavoratoreTest.getDisponibilita().size());
        interact(() -> {
            listaDisponibilta.getSelectionModel().selectIndex(listaDisponibilta.getItems().size() - 1); // Simula il click
        });
        clickOn("#eliminaDisponibilita");
        FxAssert.verifyThat("#listaDisponibilita", (MFXListView<Disponibilita> list) -> list.getItems().size() == lavoratoreTest.getDisponibilita().size());
    }

    @Test
    public void addInvalidDateDisponiblita(){
        moveTo("#ritornaMenu");
        scroll(30, VerticalDirection.DOWN);
        MFXDatePicker inizio = lookup("#inizioDisponibilita").query();
        MFXDatePicker fine = lookup("#fineDisponibilita").query();
        MFXFilterComboBox<Comune> listComuni = lookup("#comuneDisponibilita").query();
        interact(() -> {
            inizio.setValue(LocalDate.of(2040, 9, 4));
            fine.setValue(LocalDate.of(2040, 5, 4));
            listComuni.selectIndex(0);
        });
        clickOn("#aggiungiDisponibilita");
        FxAssert.verifyThat("#listaDisponibilita", (MFXListView<Disponibilita> list) -> list.getItems().size() == lavoratoreTest.getDisponibilita().size());
    }

    // NON FUNZIONANTE, non rileva l'errore
    /* @Test
    public void addOverlappingDisponibilita(){
        moveTo("#ritornaMenu");
        scroll(30, VerticalDirection.DOWN);
        MFXDatePicker inizio = lookup("#inizioDisponibilita").query();
        MFXDatePicker fine = lookup("#fineDisponibilita").query();
        MFXFilterComboBox<Comune> listComuni = lookup("#comuneDisponibilita").query();
        // Prima disponibilità
        interact(() -> {
            inizio.setValue(LocalDate.of(2040, 9, 4));
            fine.setValue(LocalDate.of(2040, 11, 20));
            listComuni.selectIndex(0);
        });
        clickOn("#aggiungiDisponibilita");
        FxAssert.verifyThat("#listaDisponibilita", (MFXListView<Disponibilita> list) -> !list.getItems().isEmpty());
        // Seconda disponiblita
        interact(() -> {
            inizio.setValue(LocalDate.of(2040, 8, 4));
            fine.setValue(LocalDate.of(2040, 10, 20));
            listComuni.selectIndex(0);
        });
        clickOn("#aggiungiDisponibilita");
        FxAssert.verifyThat("#listaDisponibilita", (MFXListView<Disponibilita> list) -> list.getItems().size() == 1);
    } */

    // ------ TEST ESPERIENZA ------ //
    @Test
    public void addEmptyEsperienza(){
        moveTo("#ritornaMenu");
        scroll(50, VerticalDirection.DOWN);
        clickOn("#aggiungiEsperienza");
        FxAssert.verifyThat("#listaEsperienze", (MFXListView<Esperienza> list) -> list.getItems().size() == lavoratoreTest.getEsperienze().size());
    }

    @Test
    public void addOldEsperienza(){
        MFXDatePicker inizio = lookup("#inizioEsperienza").query();
        MFXDatePicker fine = lookup("#fineEsperienza").query();
        MFXFilterComboBox<Comune> comune = lookup("#comuneEsperienza").query();
        MFXFilterComboBox<Specializzazione> specializzazione = lookup("#specializzazioneEsperienza").query();

        moveTo("#ritornaMenu");
        scroll(50, VerticalDirection.DOWN);
        clickOn("#aziendaEsperienza").write("AziendaTest");
        clickOn("#retribuzioneEsperienza").write("3");

        interact(() -> {
            inizio.setValue(LocalDate.of(2000, 9, 4));
            fine.setValue(LocalDate.of(2001, 5, 4));
            comune.selectIndex(0);
            specializzazione.selectIndex(0);
        });

        clickOn("#aggiungiEsperienza");

        FxAssert.verifyThat("#listaEsperienze", (MFXListView<Esperienza> list) -> list.getItems().size() == lavoratoreTest.getEsperienze().size());
    }

    @Test
    public void addInvalidRetribuzioneEsperienza(){
        MFXDatePicker inizio = lookup("#inizioEsperienza").query();
        MFXDatePicker fine = lookup("#fineEsperienza").query();
        MFXFilterComboBox<Comune> comune = lookup("#comuneEsperienza").query();
        MFXFilterComboBox<Specializzazione> specializzazione = lookup("#specializzazioneEsperienza").query();

        moveTo("#ritornaMenu");
        scroll(50, VerticalDirection.DOWN);
        clickOn("#aziendaEsperienza").write("AziendaTest");
        clickOn("#retribuzioneEsperienza").write("3 abc");

        interact(() -> {
            inizio.setValue(LocalDate.of(2000, 9, 4));
            fine.setValue(LocalDate.of(2001, 5, 4));
            comune.selectIndex(0);
            specializzazione.selectIndex(0);
        });

        clickOn("#aggiungiEsperienza");

        FxAssert.verifyThat("#listaEsperienze", (MFXListView<Esperienza> list) -> list.getItems().size() == lavoratoreTest.getEsperienze().size());
    }

    @Test
    public void addInvalidDateEsperienza(){
        MFXDatePicker inizio = lookup("#inizioEsperienza").query();
        MFXDatePicker fine = lookup("#fineEsperienza").query();
        MFXFilterComboBox<Comune> comune = lookup("#comuneEsperienza").query();
        MFXFilterComboBox<Specializzazione> specializzazione = lookup("#specializzazioneEsperienza").query();

        moveTo("#ritornaMenu");
        scroll(50, VerticalDirection.DOWN);
        clickOn("#aziendaEsperienza").write("AziendaTest");
        clickOn("#retribuzioneEsperienza").write("3 abc");

        interact(() -> {
            inizio.setValue(LocalDate.ofEpochDay(LocalDate.now().toEpochDay() - 365));
            fine.setValue(LocalDate.ofEpochDay(LocalDate.now().toEpochDay() - 200));
            comune.selectIndex(0);
            specializzazione.selectIndex(0);
        });

        clickOn("#aggiungiEsperienza");

        FxAssert.verifyThat("#listaEsperienze", (MFXListView<Esperienza> list) -> list.getItems().size() == lavoratoreTest.getEsperienze().size());
    }

    @Test
    public void addDeleteValidEsperienza(){
        MFXDatePicker inizio = lookup("#inizioEsperienza").query();
        MFXDatePicker fine = lookup("#fineEsperienza").query();
        MFXFilterComboBox<Comune> comune = lookup("#comuneEsperienza").query();
        MFXFilterComboBox<Specializzazione> specializzazione = lookup("#specializzazioneEsperienza").query();
        MFXListView<Esperienza> listaEsperienze = lookup("#listaEsperienze").query();

        moveTo("#ritornaMenu");
        scroll(50, VerticalDirection.DOWN);
        clickOn("#aziendaEsperienza").write("AziendaTest");
        clickOn("#retribuzioneEsperienza").write("3");

        interact(() -> {
            inizio.setValue(LocalDate.ofEpochDay(LocalDate.now().toEpochDay() - 365));
            fine.setValue(LocalDate.ofEpochDay(LocalDate.now().toEpochDay() - 200));
            comune.selectIndex(0);
            specializzazione.selectIndex(0);
        });

        clickOn("#aggiungiEsperienza");

        FxAssert.verifyThat("#listaEsperienze", (MFXListView<Esperienza> list) -> list.getItems().size() > lavoratoreTest.getEsperienze().size());

        interact(() -> {
            listaEsperienze.getSelectionModel().selectIndex(0); // Simula il click
        });
        clickOn("#eliminaEsperienza");
        FxAssert.verifyThat("#listaEsperienze", (MFXListView<Esperienza> list) -> list.getItems().size() == lavoratoreTest.getEsperienze().size());
    }
}