package it.univr.lavoratoristagionali.controller.validated;

import io.github.palexdev.materialfx.controls.MFXListView;
import it.univr.lavoratoristagionali.controller.enums.Errore;
import it.univr.lavoratoristagionali.controller.exception.InputException;
import javafx.scene.control.Label;

import java.util.List;

public class MFXListViewValidated<T> implements MFXValidated{
    private final MFXListView<T> listView;
    private final Label errorLabel;
    private final Errore[] flags;

    public MFXListViewValidated(MFXListView<T> listView, Label errorLabel, Errore ...flags){
        this.listView = listView;
        this.errorLabel = errorLabel;
        this.flags = flags;
    }

    public List<T> getSelectedItems() throws InputException{
        if(checkValid())
            return listView.getItems();
        return null;
    }

    // Non ci sono constraint e validate sulle MFXListView
    public boolean checkValid() throws InputException{
        for(Errore flag : flags){
            // Ignora qualsiasi flag che non sia Errore.NON_EMPTY
            if(flag == Errore.NON_EMPTY && listView.getItems().isEmpty()){
                throw new InputException(this, Errore.NON_EMPTY.getLabel());
            }
        }
        showDefault();
        return true;
    }

    public void showError(String message){
        errorLabel.setText(message);
        errorLabel.setVisible(true);
        // listView.setStyle("-fx-border-color: -mfx-red"); // TODO: non funzionano, generano warnings
    }

    public void showCorrect(){
        errorLabel.setVisible(false);
        // listView.setStyle("-fx-border-color: -mfx-green");
    }

    public void showDefault(){
        errorLabel.setVisible(false);
        // listView.setStyle("-fx-border-color: -common-gradient");
    }

    @Override
    public void reset() {
        // TODO
    }
}
