package it.univr.lavoratoristagionali.controller.validated;

import io.github.palexdev.materialfx.controls.MFXTextField;
import io.github.palexdev.materialfx.validation.Constraint;
import io.github.palexdev.materialfx.validation.Severity;
import it.univr.lavoratoristagionali.controller.enums.Errore;
import it.univr.lavoratoristagionali.controller.exception.InputException;
import javafx.beans.binding.Bindings;
import javafx.scene.control.Label;

import java.util.ArrayList;
import java.util.List;

public class MFXTextFieldValidated implements MFXValidated {

    private final MFXTextField textField;
    private final Label errorLabel;
    private final Errore[] flags;

    public MFXTextFieldValidated(MFXTextField textField, Label errorLabel, Errore ...flags){
        this.textField = textField;
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
                            yield !this.textField.getText().equals("");
                            case NUMBERS_ONLY:
                                yield this.textField.getText().chars().allMatch(Character::isDigit);
                                case LETTERS_ONLY:
                                    yield this.textField.getText().chars().allMatch(Character::isAlphabetic);
                                    case TELEPHONE_FORMAT:
                                        yield this.textField.getText().chars().allMatch(Character::isDigit) && textField.getLength() == 10;
                        case EMAIL_FORMAT:
                            yield this.textField.getText().contains("@");
                            default:
                                yield true;
                                }, this.textField.textProperty())
                    ).get());
        }

        for(Constraint constraint : constraints){
            textField.getValidator().constraint(constraint);
        }
    }

    public String getText() throws InputException {
        if(checkValid())
            return textField.getText();
        return null;
    }

    public boolean checkValid() throws InputException{
        List<Constraint> currentConstraints = textField.validate();
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
        textField.setStyle("-fx-border-color: -mfx-red");
    }

    private void showError(Constraint constraint){
        errorLabel.setText(constraint.getMessage());
        errorLabel.setVisible(true);
        // textField.setStyle("-fx-border-color: -mfx-red");
    }

    public void showCorrect(){
        errorLabel.setVisible(false);
        // textField.setStyle("-fx-border-color: -mfx-green");
    }

    public void showDefault(){
        errorLabel.setVisible(false);
        // textField.setStyle("-fx-border-color: -common-gradient");
    }

    public void reset(){
        textField.cut();
        textField.clear();
    }
}
