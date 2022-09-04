package it.univr.lavoratoristagionali.controller;

import io.github.palexdev.materialfx.controls.MFXButton;
import it.univr.lavoratoristagionali.controller.enums.View;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

/**
 * Classe controller del menu principale
 */
public class MenuController extends Controller{
    @FXML
    private MFXButton modificaLavoratoreButton;
    @FXML
    private MFXButton effettuaRicercaButton;
    @FXML
    private MFXButton esciButton;
    @FXML
    private MFXButton inserisciLavoratoreButton;

    public MenuController(){}

    // TODO: aggiungere icone alla UI (https://github.com/palexdev/MaterialFX/blob/main/materialfx/src/main/java/io/github/palexdev/materialfx/font/FontResources.java)

    /**
     * Evento generato da JavaFX, al click del pulsante di ritorno al login.
     * Sostituisce la scena corrente con la scena del login.
     *
     * @param actionEvent parametro evento JavaFX
     */
    @FXML
    private void exitEvent(ActionEvent actionEvent) {
        switchScene(getStageFromEvent(actionEvent), View.LOGIN);
    }

    /**
     * Evento generato da JavaFX, al click del pulsante di inserimento lavoratore.
     * Sostituisce la scena corrente con la scena di inserimento lavoratore.
     *
     * @param actionEvent parametro evento JavaFX
     */
    @FXML
    private void goToInserisciLavoratore(ActionEvent actionEvent){
        switchScene(getStageFromEvent(actionEvent), View.INSERISCI_LAVORATORE);
    }

    /**
     * Evento generato da JavaFX, al click del pulsante di modifica lavoratore.
     * Sostituisce la scena corrente con la scena di modifica lavoratore.
     *
     * @param actionEvent parametro evento JavaFX
     */
    @FXML
    private void goToMenuModificaLavoratore(ActionEvent actionEvent) {
        switchScene(getStageFromEvent(actionEvent), View.MENU_MODIFICA_LAVORATORE);
    }

    /**
     * Evento generato da JavaFX, al click del pulsante di ricerca lavoratore.
     * Sostituisce la scena corrente con la scena di ricerca lavoratore.
     *
     * @param actionEvent parametro evento JavaFX
     */
    @FXML
    private void goToEffettuaRicerca(ActionEvent actionEvent) {
        switchScene(getStageFromEvent(actionEvent), View.MENU_RICERCA_LAVORATORE);
    }
}
