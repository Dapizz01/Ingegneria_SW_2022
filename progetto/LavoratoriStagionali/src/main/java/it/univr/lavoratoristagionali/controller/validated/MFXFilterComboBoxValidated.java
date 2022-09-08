package it.univr.lavoratoristagionali.controller.validated;

import io.github.palexdev.materialfx.controls.MFXFilterComboBox;
import io.github.palexdev.materialfx.validation.Severity;
import it.univr.lavoratoristagionali.controller.enums.Check;
import it.univr.lavoratoristagionali.controller.exception.InputException;
import javafx.beans.binding.Bindings;
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
        List<io.github.palexdev.materialfx.validation.Constraint> constraints = new ArrayList<io.github.palexdev.materialfx.validation.Constraint>();
        for(Check flag : flags){
            constraints.add(io.github.palexdev.materialfx.validation.Constraint.Builder.build()
                    .setSeverity(Severity.ERROR)
                    .setMessage(flag.getLabel())
                    .setCondition(Bindings.createBooleanBinding(() -> flag == Check.NON_EMPTY ? filterComboBox.getValue() != null : true, filterComboBox.textProperty())
                    ).get());
        }

        for(io.github.palexdev.materialfx.validation.Constraint constraint : constraints){
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
        if(checkValid())
            return filterComboBox.getSelectedItem();
        return null;
    }

    public boolean checkValid() throws InputException{
        List<io.github.palexdev.materialfx.validation.Constraint> currentConstraints = filterComboBox.validate();
        if(!currentConstraints.isEmpty()){
            throw new InputException(this, currentConstraints.get(0));
        }
        else{
            showDefault();
            return true;
        }
    }

    public void showError(String message){
        errorLabel.setText(message);
        errorLabel.setVisible(true);
        filterComboBox.setStyle("-fx-border-color: -mfx-red");
    }

    private void showError(io.github.palexdev.materialfx.validation.Constraint constraint){
        errorLabel.setText(constraint.getMessage());
        errorLabel.setVisible(true);
        // filterComboBox.setStyle("-fx-border-color: -mfx-red");
    }

    public void showCorrect(){
        errorLabel.setVisible(false);
        // filterComboBox.setStyle("-fx-border-color: -mfx-green");
    }

    public void showDefault(){
        errorLabel.setVisible(false);
        // filterComboBox.setStyle("-fx-border-color: -common-gradient");
    }

    public void reset(){
        filterComboBox.clearSelection();
        filterComboBox.clear();
    }
}
