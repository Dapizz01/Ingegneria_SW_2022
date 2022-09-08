package it.univr.lavoratoristagionali.controller.validated;

import io.github.palexdev.materialfx.controls.MFXListView;
import it.univr.lavoratoristagionali.controller.enums.Check;
import it.univr.lavoratoristagionali.controller.exception.InputException;
import javafx.scene.control.Label;

import java.util.List;

public class MFXListViewValidated<T> implements MFXValidated{
    private final MFXListView<T> listView;
    private final Label errorLabel;
    private final Check[] flags;

    public MFXListViewValidated(MFXListView<T> listView, Label errorLabel, Check...flags){
        this.listView = listView;
        this.errorLabel = errorLabel;
        this.flags = flags;
    }

    /**
     * Restituisce gli elementi selezionati dall'utente di list. Se list non rispetta i Constraint,
     * viene lanciata una InputException (con valore di ritorno null)
     *
     * @return Elementi di list selezionati se list si trova in uno stato valido, altrimenti null
     * @throws InputException Lanciato quando list si trova in uno stato invalido
     */
    public List<T> getSelectedItems() throws InputException{
        if(checkValid())
            return listView.getItems();
        return null;
    }

    public boolean checkValid() throws InputException{
        for(Check flag : flags){
            // Ignora qualsiasi flag che non sia Errore.NON_EMPTY
            if(flag == Check.NON_EMPTY && listView.getItems().isEmpty()){
                throw new InputException(this, Check.NON_EMPTY.getLabel());
            }
        }
        showDefault();
        return true;
    }

    public void showError(String message){
        errorLabel.setText(message);
        errorLabel.setVisible(true);
        // listView.setStyle("-fx-border-color: -mfx-red");
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
