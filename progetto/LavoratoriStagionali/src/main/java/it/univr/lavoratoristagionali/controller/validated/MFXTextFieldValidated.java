package it.univr.lavoratoristagionali.controller.validated;

import io.github.palexdev.materialfx.controls.MFXTextField;
import io.github.palexdev.materialfx.validation.Constraint;
import io.github.palexdev.materialfx.validation.Severity;
import it.univr.lavoratoristagionali.controller.enums.Check;
import it.univr.lavoratoristagionali.controller.exception.InputException;
import javafx.beans.binding.Bindings;
import javafx.scene.control.Label;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe wrapper di MFXTextField, comprende un MFXTextField e il suo label di errore.
 * Permette di ottenere il valore selezionato di MFXTextField in modo sicuro, lanciando un'eccezione
 * se MFXTextField non rispetta le condizioni (Check) specificate nel costruttore
 */
public class MFXTextFieldValidated implements MFXValidated {

    private final MFXTextField textField;
    private final Label errorLabel;
    private final Check[] flags;

    public MFXTextFieldValidated(MFXTextField textField, Label errorLabel, Check...flags){
        this.textField = textField;
        this.errorLabel = errorLabel;
        this.flags = flags;

        buildValidator();
    }

    /**
     * Costruisce e applica i Constraint indicati dal costruttore al campo textField
     */
    private void buildValidator(){
        List<Constraint> constraints = new ArrayList<Constraint>();
        for(Check flag : flags){
            constraints.add(Constraint.Builder.build()
                    .setSeverity(Severity.ERROR)
                    .setMessage(flag.getLabel())
                    // Vengono definite le condizioni del constraint creato. Se la funzione ritorna true il constraint viene considerato rispettato
                    // altrimenti no, e viene considerato uno stato invalido del textField
                    .setCondition(Bindings.createBooleanBinding(() -> switch(flag){
                        case NON_EMPTY:
                            yield !this.textField.getText().equals("") || this.textField.getLength() != 0;
                        case NUMBERS_ONLY:
                            yield this.textField.getText().chars().allMatch(Character::isDigit);
                        case LETTERS_ONLY:
                            yield this.textField.getLength() != 0 ? this.textField.getText().chars().allMatch(c -> Character.isWhitespace(c) || Character.isLetter(c)) : true;
                        case TELEPHONE_FORMAT:
                            yield this.textField.getLength() != 0 ? this.textField.getText().chars().allMatch(Character::isDigit) && textField.getLength() == 10 : true;
                        case EMAIL_FORMAT:
                            yield this.textField.getText().contains("@");
                        default:
                            yield true;
                            }, this.textField.textProperty())
                    ).get());
        }

        // Associa i constraint appena creati al filterComboBox
        for(Constraint constraint : constraints){
            textField.getValidator().constraint(constraint);
        }
    }

    /**
     * Restituisce il valore testuale di textField di questo oggetto. Se textField non rispetta i Constraint indicati durante la costruzione dell'oggetto
     * viene lanciata una InputException (con valore di ritorno null)
     *
     * @return Valore di textField se valido, altrimenti null
     * @throws InputException Lanciata quando il valore di textField non è valido
     */
    public String getText() throws InputException {
        return checkValid() ? textField.getText() : null;
    }

    public boolean checkValid() throws InputException{
        // Ottenimento dei constraint non rispettati
        // (.validate() ritorna i constraint al momento non rispettati da textField sotto forma di lista)
        List<Constraint> currentConstraints = textField.validate();
        // Se ci sono constraint non rispettati
        if(!currentConstraints.isEmpty()){
            // Viene lanciata una InputException con tale constraint
            throw new InputException(this, currentConstraints.get(0));
        }
        // textField si trova in uno stato valido, e viene resettato il suo aspetto
        // (nel caso prima fosse mostrato come errato, dato che ora non lo è più)
        else{
            showDefault();
            return true;
        }
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

    /**
     * Resetta il valore del campo MFXTextField
     */
    public void reset(){
        textField.cut();
        textField.clear();
    }
}
