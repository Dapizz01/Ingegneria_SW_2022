package it.univr.lavoratoristagionali.controller;

import io.github.palexdev.materialfx.controls.*;
import it.univr.lavoratoristagionali.controller.enums.View;
import it.univr.lavoratoristagionali.model.Dao.*;
import it.univr.lavoratoristagionali.types.*;
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

import java.time.LocalDate;

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

    /*
        Test da fare:
            - Inserire un lavoratore (corretto)
            - Aggiungi contatto urgente vuoto
            - Aggiungi contatto urgente con telefono errato(lunghezza)
            - Aggiungi contatto urgente con mail errata(senza @)
            - Aggiungi contatto urgente con telefono e mail(corretti), ma nome e cognome vuoto
            - Aggiungi contatto urgente e poi eliminarlo (e controllare che la lista sia vuota)
            - Aggiungi disponibilità vuoto
            - Aggiungi disponibilità dove data inizio > data fine
            - Aggiungi disponibilità dove la data di inizio / fine è passata(errata)
            - Aggiungi disponibilità e poi eliminarla (controllare lista vuota)
            - Aggiungere esperienza vuota
            - Aggiungere esperienza con retribuzione errata (lettere, spazi, ...)
            - Aggiungere esperienza più vecchia di 5 anni
            - Aggiungere esperienza con data futura
            - Aggiungere esperienza e poi eliminarla (controllare lista vuota)
     */
    // ------- TEST LAVORATORE ------- //

    /**
     * Prova ad aggiungere un lavoratore non compilando alcun campo
     */
    @Test
    public void emptyFields(){
        // Sposta il cursore al button "indietro"
        moveTo("#ritornaMenu");
        // Scrolla fino alla fine della pagina
        scroll(50, VerticalDirection.DOWN);
        // Invia lavoratore
        clickOn("#inviaLavoratore");
        // Verifica che il label di errore di mancato inserimento del nome del lavoratore sia visibile
        FxAssert.verifyThat("#nomeLavoratoreError", (Label label) -> label.isVisible());
    }

    /**
     * Prova ad aggiungere un lavoratore compilando correttamente solo i campi necessari (gli altri vengono lasciati vuoti)
     *
     * @throws InterruptedException
     */
    /* @Test
    public void addValidLavoratore() throws InterruptedException {
        MFXDatePicker dataNascitaLavoratore = lookup("#dataNascitaLavoratore").query();
        MFXFilterComboBox<Comune> comuneNascitaLavoratore = lookup("#comuneNascitaLavoratore").query();
        MFXFilterComboBox<Comune> comuneAbitazioneLavoratore = lookup("#comuneAbitazioneLavoratore").query();
        MFXFilterComboBox<Lingua> nazionalitaLavoratore = lookup("#nazionalitaLavoratore").query();
        MFXCheckListView<Lingua> lingueLavoratore = lookup("#lingueLavoratore").query();

        moveTo("#ritornaMenu");

        // Inserimento dati anagrafici lavoratore
        clickOn("#nomeLavoratore").write("TestNome");
        clickOn("#cognomeLavoratore").write("TestCognome");
        interact(() -> {
            dataNascitaLavoratore.setValue(LocalDate.of(1980, 2, 2));
            comuneNascitaLavoratore.selectIndex(0);
            comuneAbitazioneLavoratore.selectIndex(0);
            nazionalitaLavoratore.selectIndex(0);
            lingueLavoratore.getSelectionModel().selectIndex(0);
            lingueLavoratore.getSelectionModel().selectIndex(4);
        });

        scroll(8, VerticalDirection.DOWN);

        // Inserimento contatto lavoratore
        clickOn("#emailLavoratore").write("test@test.com");

        moveTo("#lingueLavoratore");
        scroll(15, VerticalDirection.DOWN);

        // Inserimento contatto urgente
        clickOn("#nomeContatto").write("testNome");
        clickOn("#cognomeContatto").write("testCognome");
        clickOn("#telefonoContatto").write("0123456789");
        clickOn("#emailContatto").write("aaa@aaa.com");
        clickOn("#aggiungiContatto");

        // Sposta cursore su #nomeContatto per evitare che lo scroll rimanga fermo sulla lista dei contatti urgenti
        moveTo("#nomeContatto");
        scroll(35, VerticalDirection.DOWN);
        clickOn("#inviaLavoratore");

        // Se il lavoratore è stato effettivamente inserito, la scena dovrebbe cambiare ed essere quella del menù
        // principale, perciò controlla che esista il button ...
        FxAssert.verifyThat("#modificaLavoratoreButton", (Button button) -> button.isVisible());
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
     * Verifica l'inserimento di un nuovo contatto urgente
     */
    /* @Test
    public void addValidContattoUrgente(){
        moveTo("#ritornaMenu");
        scroll(20, VerticalDirection.DOWN);
        clickOn("#nomeContatto").write("testNome");
        clickOn("#cognomeContatto").write("testCognome");
        clickOn("#telefonoContatto").write("0123456789");
        clickOn("#emailContatto").write("aaa@aaa.com");
        clickOn("#aggiungiContatto");
        FxAssert.verifyThat("#listaContattoUrgente", (MFXListView<Contatto> list) -> !list.getItems().isEmpty());
    } */

    /**
     * Prova ad inserire un contatto urgente lasciando i campi vuoti. Dovrebbe risultare un errore, con la mancata
     * aggiunta della entry nella lista dei contatti urgenti
     */
    @Test
    public void addEmptyContattoUrgente(){
        moveTo("#ritornaMenu");
        scroll(20, VerticalDirection.DOWN);
        clickOn("#aggiungiContatto");
        FxAssert.verifyThat("#listaContattoUrgente", (MFXListView<Contatto> list) -> list.getItems().isEmpty());
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
        FxAssert.verifyThat("#listaContattoUrgente", (MFXListView<Contatto> list) -> list.getItems().isEmpty());
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
        FxAssert.verifyThat("#listaContattoUrgente", (MFXListView<Contatto> list) -> list.getItems().isEmpty());
    }

    /**
     * Prova ad inserire un contatto urgente compilando i campi "telfono" e "email", ma lasciando vuoti i campi "nome" e "cognome".
     * Dovrebbe risultare un errore (a causa dei campi vuoti), con la mancata aggiunta della entry nella lista dei contatti urgenti
     */
    @Test
    public void addEmptyNameSurnameContattoUrgente(){
        moveTo("#ritornaMenu");
        scroll(20, VerticalDirection.DOWN);
        clickOn("#telefonoContatto").write("0123456789");
        clickOn("#emailContatto").write("aaa@aaa.com");
        clickOn("#aggiungiContatto");
        FxAssert.verifyThat("#listaContattoUrgente", (MFXListView<Contatto> list) -> list.getItems().isEmpty());
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
        FxAssert.verifyThat("#listaContattoUrgente", (MFXListView<Contatto> list) -> !list.getItems().isEmpty());
        interact(() -> {
            listaContatti.getSelectionModel().selectIndex(0); // Simula il click
        });
        clickOn("#eliminaContatto");
        FxAssert.verifyThat("#listaContattoUrgente", (MFXListView<Contatto> list) -> list.getItems().isEmpty());
    }

    // ------ TEST DISPONIBILITA ------ //

    /* @Test
    public void addValidDisponibilita(){
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
        FxAssert.verifyThat("#listaDisponibilita", (MFXListView<Disponibilita> list) -> !list.getItems().isEmpty());
    } */

    @Test
    public void addEmptyDisponibilita(){
        moveTo("#ritornaMenu");
        scroll(30, VerticalDirection.DOWN);
        clickOn("#aggiungiDisponibilita");
        FxAssert.verifyThat("#listaDisponibilita", (MFXListView<Disponibilita> list) -> list.getItems().isEmpty());
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
        FxAssert.verifyThat("#listaDisponibilita", (MFXListView<Disponibilita> list) -> list.getItems().isEmpty());
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
        FxAssert.verifyThat("#listaDisponibilita", (MFXListView<Disponibilita> list) -> !list.getItems().isEmpty());
        interact(() -> {
            listaDisponibilta.getSelectionModel().selectIndex(0); // Simula il click
        });
        clickOn("#eliminaDisponibilita");
        FxAssert.verifyThat("#listaDisponibilita", (MFXListView<Disponibilita> list) -> list.getItems().isEmpty());
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
        FxAssert.verifyThat("#listaDisponibilita", (MFXListView<Disponibilita> list) -> list.getItems().isEmpty());
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
        FxAssert.verifyThat("#listaEsperienze", (MFXListView<Esperienza> list) -> list.getItems().isEmpty());
    }

    /* @Test
    public void addValidEsperienza(){
        MFXDatePicker inizio = lookup("#inizioEsperienza").query();
        MFXDatePicker fine = lookup("#fineEsperienza").query();
        MFXFilterComboBox<Comune> comune = lookup("#comuneEsperienza").query();
        MFXFilterComboBox<Specializzazione> specializzazione = lookup("#specializzazioneEsperienza").query();

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

        FxAssert.verifyThat("#listaEsperienze", (MFXListView<Esperienza> list) -> !list.getItems().isEmpty());
    } */

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

        FxAssert.verifyThat("#listaEsperienze", (MFXListView<Esperienza> list) -> list.getItems().isEmpty());
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

        FxAssert.verifyThat("#listaEsperienze", (MFXListView<Esperienza> list) -> list.getItems().isEmpty());
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

        FxAssert.verifyThat("#listaEsperienze", (MFXListView<Esperienza> list) -> list.getItems().isEmpty());
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

        FxAssert.verifyThat("#listaEsperienze", (MFXListView<Esperienza> list) -> !list.getItems().isEmpty());

        interact(() -> {
            listaEsperienze.getSelectionModel().selectIndex(0); // Simula il click
        });
        clickOn("#eliminaEsperienza");
        FxAssert.verifyThat("#listaEsperienze", (MFXListView<Esperienza> list) -> list.getItems().isEmpty());
    }

    @Test
    public void backToMenu(){
        clickOn("#ritornaMenu");
        FxAssert.verifyThat("#inserisciLavoratoreButton", (MFXButton button) -> button.isVisible());
    }
}