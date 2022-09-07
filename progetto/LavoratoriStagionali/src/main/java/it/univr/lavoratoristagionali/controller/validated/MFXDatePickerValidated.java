package it.univr.lavoratoristagionali.controller.validated;

import io.github.palexdev.materialfx.controls.MFXDatePicker;
import io.github.palexdev.materialfx.validation.Severity;
import it.univr.lavoratoristagionali.controller.enums.Check;
import it.univr.lavoratoristagionali.controller.exception.InputException;
import javafx.beans.binding.Bindings;
import javafx.scene.control.Label;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class MFXDatePickerValidated implements MFXValidated{
    private final MFXDatePicker datePicker;
    private final Label errorLabel;
    private final Check[] flags;

    public MFXDatePickerValidated(MFXDatePicker datePicker, Label errorLabel, Check...flags){
        this.datePicker = datePicker;
        this.errorLabel = errorLabel;
        this.flags = flags;

        buildValidator();
    }

    private void buildValidator(){
        List<io.github.palexdev.materialfx.validation.Constraint> constraints = new ArrayList<io.github.palexdev.materialfx.validation.Constraint>();
        for(Check flag : flags){
            constraints.add(io.github.palexdev.materialfx.validation.Constraint.Builder.build()
                    .setSeverity(Severity.ERROR)
                    .setMessage(flag.getLabel())
                    .setCondition(Bindings.createBooleanBinding(() -> switch(flag){
                        case NON_EMPTY:
                            yield datePicker.getValue() != null;
                        case UP_TO_NOW:
                            if(datePicker.getValue() != null)
                                yield datePicker.getValue().toEpochDay() < LocalDate.now().toEpochDay();
                            else
                                yield true;
                        case FROM_NOW:
                            if(datePicker.getValue() != null)
                                yield datePicker.getValue().toEpochDay() >= LocalDate.now().toEpochDay();
                            else
                                yield true;
                        case MUST_BE_ADULT:
                            if(datePicker.getValue() != null)
                                yield datePicker.getValue().toEpochDay() <= (LocalDate.now().toEpochDay() - (18 * 365));
                            else
                                yield true;
                            default:
                                    yield true;
                            }, datePicker.textProperty())
                    ).get());
        }

        for(io.github.palexdev.materialfx.validation.Constraint constraint : constraints){
            datePicker.getValidator().constraint(constraint);
        }
    }

    public int getEpochDays() throws InputException{
        if(checkValid()){
            if(datePicker.getValue() != null)
                return (int) datePicker.getValue().toEpochDay();
        }
        return 0;
    }

    public boolean checkValid() throws InputException{
        List<io.github.palexdev.materialfx.validation.Constraint> currentConstraints = datePicker.validate();
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
        datePicker.setStyle("-fx-border-color: -mfx-red");
    }

    private void showError(io.github.palexdev.materialfx.validation.Constraint constraint){
        errorLabel.setText(constraint.getMessage());
        errorLabel.setVisible(true);
        // datePicker.setStyle("-fx-border-color: -mfx-red");
    }

    public void showCorrect(){
        errorLabel.setVisible(false);
        // datePicker.setStyle("-fx-border-color: -mfx-green");
    }

    public void showDefault(){
        errorLabel.setVisible(false);
        // datePicker.setStyle("-fx-border-color: -common-gradient");
    }

    public void reset(){
        datePicker.setValue(null);
        datePicker.clear();
    }
}
