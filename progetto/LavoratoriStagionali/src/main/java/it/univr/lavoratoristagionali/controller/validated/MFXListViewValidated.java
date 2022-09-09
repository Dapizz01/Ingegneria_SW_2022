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
        // Se la lista si trova in uno stato valido ritorna i suoi elementi, altrimenti viene lanciata una InputException
        return checkValid() ? listView.getItems() : null;
    }

    public boolean checkValid() throws InputException{
        // Controlla ogni flag
        for(Check flag : flags){
            // Ignora qualsiasi flag che non sia Errore.NON_EMPTY
            // Se c'è la flag NON_EMPTY e la lista è vuota
            if(flag == Check.NON_EMPTY && listView.getItems().isEmpty()){
                // Lancia una nuova InputException
                throw new InputException(this, Check.NON_EMPTY.getLabel());
            }
        }
        // Lo stato è valido, reset del messaggio di errore
        showDefault();
        return true;
    }

    public void showError(String message){
        errorLabel.setText(message);
        errorLabel.setVisible(true);
    }

    public void showCorrect(){
        errorLabel.setVisible(false);
    }

    public void showDefault(){
        errorLabel.setVisible(false);
    }

    @Override
    public void reset() {
        // TODO
    }
}
