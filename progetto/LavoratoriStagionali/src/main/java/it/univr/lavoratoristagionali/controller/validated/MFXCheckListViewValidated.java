package it.univr.lavoratoristagionali.controller.validated;

import io.github.palexdev.materialfx.controls.MFXCheckListView;
import it.univr.lavoratoristagionali.controller.enums.Check;
import it.univr.lavoratoristagionali.controller.exception.InputException;
import javafx.scene.control.Label;

import java.util.List;

/**
 * Classe wrapper di MFXCheckList, comprende un MFXCheckListView e il suo label di errore.
 * Permette di ottenere il valore di MFXCheckListView in modo sicuro, lanciando un'eccezione
 * se MFXCheckListView non rispetta le condizioni (Check) specificate nel costruttore
 *
 * @param <T>
 */
public class MFXCheckListViewValidated<T> implements MFXValidated {

    private final MFXCheckListView<T> checkListView;
    private final Label errorLabel;
    private final Check[] flags;

    public MFXCheckListViewValidated(MFXCheckListView<T> checkListView, Label errorLabel, Check...flags){
        this.checkListView = checkListView;
        this.errorLabel = errorLabel;
        this.flags = flags;
    }

    /**
     * Ritorna gli elementi selezionati di MFXCheckListView. Se MFXCheckListView non rispetta i Check della classe
     * viene restituito null e viene lanciata una InputException.
     *
     * @return Lista di elementi oppure null
     * @throws InputException Lanciata se MFXCheckListView non rispetta i Check
     */
    public List<T> getSelectedItems() throws InputException{
        return checkValid() ? checkListView.getSelectionModel().getSelectedValues() : null;
    }

    /**
     * Controlla lo stato di questo oggetto, in base ai Constraint con il quale è stato costruito.
     *
     * @return true se l'oggetto rispetta i Check, altrimenti false
     * @throws InputException Lanciata se l'oggetto non rispetta i Check
     */
    @Override
    public boolean checkValid() throws InputException {
        for(Check flag : flags){
            // Ignora qualsiasi flag che non sia Errore.NON_EMPTY
            if(flag == Check.NON_EMPTY && checkListView.getSelectionModel().getSelectedValues().isEmpty()){
                throw new InputException(this, Check.NON_EMPTY.getLabel());
            }
        }
        showDefault();
        return true;
    }

    /**
     * Setta un messaggio su Label di errore e rende visibile tale label
     *
     * @param message Messaggio da mettere su Label di errore
     */
    @Override
    public void showError(String message) {
        errorLabel.setText(message);
        errorLabel.setVisible(true);
    }

    /**
     * Nasconde il Label di errore
     */
    @Override
    public void showCorrect() {
        errorLabel.setVisible(false);
    }

    /**
     * Nasconde il Label di errore
     */
    @Override
    public void showDefault() {
        errorLabel.setVisible(false);
    }

    /**
     * Effettua il reset degli elementi selezionati su MFXCheckListView
     */
    @Override
    public void reset() {
        checkListView.getSelectionModel().clearSelection();
    }

}
