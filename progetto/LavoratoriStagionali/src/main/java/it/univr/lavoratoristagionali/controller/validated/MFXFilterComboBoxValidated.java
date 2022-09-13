package it.univr.lavoratoristagionali.controller.validated;

import io.github.palexdev.materialfx.controls.MFXFilterComboBox;
import io.github.palexdev.materialfx.validation.Constraint;
import io.github.palexdev.materialfx.validation.Severity;
import it.univr.lavoratoristagionali.controller.enums.Check;
import it.univr.lavoratoristagionali.controller.exception.InputException;
import javafx.beans.binding.Bindings;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;

import java.util.ArrayList;
import java.util.List;

public class MFXFilterComboBoxValidated<T> implements MFXValidated{
    private final MFXFilterComboBox<T> filterComboBox;
    private final Label errorLabel;
    private final Check[] flags;

    public MFXFilterComboBoxValidated(MFXFilterComboBox<T> filterComboBox, Label errorLabel, Check...flags){
        this.filterComboBox = filterComboBox;
        this.errorLabel = errorLabel;
        this.flags = flags;

        buildValidator();
    }

    /**
     * Costruisce e applica i Constraint indicati dal costruttore al campo filterComboBox
     */
    private void buildValidator(){
        List<Constraint> constraints = new ArrayList<io.github.palexdev.materialfx.validation.Constraint>();
        for(Check flag : flags){
            constraints.add(io.github.palexdev.materialfx.validation.Constraint.Builder.build()
                    .setSeverity(Severity.ERROR)
                    .setMessage(flag.getLabel())
                    // Vengono definite le condizioni del constraint creato. Se la funzione ritorna true il constraint viene considerato rispettato
                    // altrimenti no, e viene considerato uno stato invalido del filterComboBox
                    .setCondition(Bindings.createBooleanBinding(() -> flag == Check.NON_EMPTY ? filterComboBox.getValue() != null : true, filterComboBox.textProperty())
                    ).get());
        }

        // Associa i constraint appena creati al filterComboBox
        for(Constraint constraint : constraints){
            filterComboBox.getValidator().constraint(constraint);
        }
    }

    /**
     * Restituisce gli elementi selezionati dall'utente di checkListView. Se checkListView non rispetta i Constraint,
     * viene lanciata una InputException (con valore di ritorno null)
     *
     * @return Elementi di checkListView flaggati se list si trova in uno stato valido, altrimenti null
     * @throws InputException Lanciato quando checkListView si trova in uno stato invalido
     */
    public T getSelectedItem() throws InputException {
        // Se filterComboBox si trova in uno stato valido ritorna gli elementi selezionati dall'utente
        // altrimenti viene lanciata una InputException
        return checkValid() ? filterComboBox.getSelectedItem() : null;
    }

    public boolean checkValid() throws InputException{
        // Ottenimento dei constraint non validi
        // (.validate() ritorna i constraint al momento non rispettati da filterComboBox)
        List<Constraint> currentConstraints = filterComboBox.validate();
        // Se ci sono constraint non rispettati
        if(!currentConstraints.isEmpty()){
            // Viene lanciata una InputException con tale constraint
            throw new InputException(this, currentConstraints.get(0));
        }
        // filterComboBox si trova in uno stato valido, e viene resettato il suo aspetto
        // (nel caso prima fosse mostrato come errato, dato che ora non lo è più)
        else{
            showDefault();
            return true;
        }
    }

    public void setItems(ObservableList<T> list){
        filterComboBox.setItems(list);
    }

    public void showError(String message){
        errorLabel.setText(message);
        errorLabel.setVisible(true);
    }

    private void showError(Constraint constraint){
        errorLabel.setText(constraint.getMessage());
        errorLabel.setVisible(true);
    }

    public void showCorrect(){
        errorLabel.setVisible(false);
    }

    public void showDefault(){
        errorLabel.setVisible(false);
    }

    public void reset(){
        filterComboBox.clearSelection();
        filterComboBox.clear();
    }
}
