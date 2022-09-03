package it.univr.lavoratoristagionali.controller.validated;

import io.github.palexdev.materialfx.controls.MFXCheckListView;
import it.univr.lavoratoristagionali.controller.enums.Errore;
import it.univr.lavoratoristagionali.controller.exception.InputException;
import javafx.scene.control.Label;

import java.util.List;

public class MFXCheckListViewValidated<T> implements MFXValidated {

    private final MFXCheckListView<T> checkListView;
    private final Label errorLabel;
    private final Errore[] flags;

    public MFXCheckListViewValidated(MFXCheckListView<T> checkListView, Label errorLabel, Errore ...flags){
        this.checkListView = checkListView;
        this.errorLabel = errorLabel;
        this.flags = flags;
    }

    public List<T> getSelectedItems() throws InputException{
        return checkValid() ? checkListView.getSelectionModel().getSelectedValues() : null;
    }

    @Override
    public boolean checkValid() throws InputException {
        for(Errore flag : flags){
            // Ignora qualsiasi flag che non sia Errore.NON_EMPTY
            if(flag == Errore.NON_EMPTY && checkListView.getSelectionModel().getSelectedValues().isEmpty()){
                throw new InputException(this, Errore.NON_EMPTY.getLabel());
            }
        }
        showDefault();
        return true;
    }

    @Override
    public void showError(String message) {
        errorLabel.setText(message);
        errorLabel.setVisible(true);
    }

    @Override
    public void showCorrect() {
        errorLabel.setVisible(false);
    }

    @Override
    public void showDefault() {
        errorLabel.setVisible(false);
    }

    @Override
    public void reset() {
        checkListView.getSelectionModel().clearSelection();
    }

}
