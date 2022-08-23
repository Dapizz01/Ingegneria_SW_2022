package it.univr.lavoratoristagionali.controller.validated;

import io.github.palexdev.materialfx.controls.MFXFilterComboBox;
import io.github.palexdev.materialfx.controls.MFXTextField;
import io.github.palexdev.materialfx.validation.Constraint;
import io.github.palexdev.materialfx.validation.Severity;
import it.univr.lavoratoristagionali.controller.Errore;
import it.univr.lavoratoristagionali.controller.exception.EmptyFieldException;
import it.univr.lavoratoristagionali.controller.exception.InputException;
import javafx.beans.binding.Bindings;
import javafx.scene.control.Label;

import java.util.ArrayList;
import java.util.List;

public class MFXFilterComboBoxValidated<T> implements MFXValidated{
    private final MFXFilterComboBox<T> filterComboBox;
    private final Label errorLabel;
    private final Errore[] flags;

    public MFXFilterComboBoxValidated(MFXFilterComboBox<T> filterComboBox, Label errorLabel, Errore ...flags){
        this.filterComboBox = filterComboBox;
        this.errorLabel = errorLabel;
        this.flags = flags;

        buildValidator();
    }

    private void buildValidator(){
        List<Constraint> constraints = new ArrayList<Constraint>();
        for(Errore flag : flags){
            constraints.add(Constraint.Builder.build()
                    .setSeverity(Severity.ERROR)
                    .setMessage(flag.getLabel())
                    .setCondition(Bindings.createBooleanBinding(() -> switch(flag){
                                case NON_EMPTY:
                                    yield filterComboBox.getValue() != null;
                                default:
                                    yield true;
                            }, filterComboBox.textProperty())
                    ).get());
        }

        for(Constraint constraint : constraints){
            filterComboBox.getValidator().constraint(constraint);
        }
    }

    public T getSelectedItem() throws InputException {
        if(checkValid())
            return filterComboBox.getSelectedItem();
        return null;
    }

    public boolean checkValid() throws InputException{
        List<Constraint> currentConstraints = filterComboBox.validate();
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

    private void showError(Constraint constraint){
        errorLabel.setText(constraint.getMessage());
        errorLabel.setVisible(true);
        filterComboBox.setStyle("-fx-border-color: -mfx-red");
    }

    public void showCorrect(){
        errorLabel.setVisible(false);
        filterComboBox.setStyle("-fx-border-color: -mfx-green");
    }

    public void showDefault(){
        errorLabel.setVisible(false);
        filterComboBox.setStyle("-fx-border-color: -common-gradient");
    }
}
