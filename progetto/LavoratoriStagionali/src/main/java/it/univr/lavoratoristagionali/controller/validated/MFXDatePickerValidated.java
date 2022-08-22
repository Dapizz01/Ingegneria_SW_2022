package it.univr.lavoratoristagionali.controller.validated;

import io.github.palexdev.materialfx.controls.MFXDatePicker;
import io.github.palexdev.materialfx.validation.Constraint;
import io.github.palexdev.materialfx.validation.Severity;
import it.univr.lavoratoristagionali.controller.Errore;
import javafx.beans.binding.Bindings;
import javafx.scene.control.Label;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class MFXDatePickerValidated extends MFXDatePicker {
    private final MFXDatePicker datePicker;
    private final Label errorLabel;
    private final Errore[] flags;

    public MFXDatePickerValidated(MFXDatePicker datePicker, Label errorLabel, Errore ...flags){
        this.datePicker = datePicker;
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

        for(Constraint constraint : constraints){
            datePicker.getValidator().constraint(constraint);
        }
    }

    public boolean checkValid(){
        List<Constraint> currentConstraints = datePicker.validate();
        if(!currentConstraints.isEmpty()){
            showError(currentConstraints.get(0));
            return false;
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

    private void showError(Constraint constraint){
        errorLabel.setText(constraint.getMessage());
        errorLabel.setVisible(true);
        datePicker.setStyle("-fx-border-color: -mfx-red");
    }

    private void showCorrect(){
        errorLabel.setVisible(false);
        datePicker.setStyle("-fx-border-color: -mfx-green");
    }

    public void showDefault(){
        errorLabel.setVisible(false);
        datePicker.setStyle("-fx-border-color: -common-gradient");
    }
}
