package it.univr.lavoratoristagionali.controller.validated;

import io.github.palexdev.materialfx.controls.MFXDatePicker;
import io.github.palexdev.materialfx.validation.Constraint;
import io.github.palexdev.materialfx.validation.Severity;
import it.univr.lavoratoristagionali.controller.enums.Check;
import it.univr.lavoratoristagionali.controller.exception.InputException;
import javafx.beans.binding.Bindings;
import javafx.scene.control.Label;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe wrapper di MFXDatePicker, comprende un MFXDatePicker e il suo label di errore.
 * Permette di ottenere il valore di MFXDatePicker in modo sicuro, lanciando un'eccezione
 * se MFXDatePicker non rispetta le condizioni (Check) specificate nel costruttore
 */
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

    /**
     * Costruisce e applica i Constraint indicati dal costruttore al campo MFXDatePicker
     */
    private void buildValidator(){
        List<Constraint> constraints = new ArrayList<Constraint>();
        // Per ogni flag indicata
        for(Check flag : flags){
            // Costruisci un nuovo Constraint
            constraints.add(Constraint.Builder.build()
                    .setSeverity(Severity.ERROR)
                    .setMessage(flag.getLabel())
                    // Vengono definite le condizioni del constraint creato. Se la funzione ritorna true il constraint viene considerato rispettato
                    // altrimenti no, e viene considerato uno stato invalido del datePicker
                    .setCondition(Bindings.createBooleanBinding(() -> switch(flag){
                        case NON_EMPTY: // Il datePicker deve avere una data contenuta
                            yield datePicker.getValue() != null;
                        case UP_TO_NOW: // Il datePicker deve avere come data massima la data odierna
                            yield datePicker.getValue() != null ? datePicker.getValue().toEpochDay() < LocalDate.now().toEpochDay() : true;
                        case FROM_NOW: // Il datePicker deve avere come data minima la data odierna
                            yield datePicker.getValue() != null ? datePicker.getValue().toEpochDay() >= LocalDate.now().toEpochDay() : true;
                        case MUST_BE_ADULT: // Il datePicker indica una data di nascita, e tale deve indicare una data di nascita di una persona maggiorenne rispetto alla data odierna
                            yield datePicker.getValue() != null ? datePicker.getValue().toEpochDay() <= (LocalDate.now().toEpochDay() - (18 * 365)) : true;
                        case FROM_FIVE_YEARS_AGO: // Il datePicker deve indicare una data vecchia di massimo 5 anni
                            yield datePicker.getValue() != null ? datePicker.getValue().toEpochDay() >= (LocalDate.now().toEpochDay() - (5 * 365)) : true;
                        default: // Qualsiasi altra flag viene ignorata
                            yield true;
                            }, datePicker.textProperty())
                    ).get());
        }

        // Associa i constraint appena creati al datePicker
        for(Constraint constraint : constraints){
            datePicker.getValidator().constraint(constraint);
        }
    }

    /**
     * Restituisce il valore testuale di datePicker di questo oggetto. Se datePicker non rispetta i Constraint indicati durante la costruzione dell'oggetto
     * viene lanciata una InputException (con valore di ritorno null)
     *
     * @return Valore di datePicker se valido, altrimenti null
     * @throws InputException Lanciata quando il valore di datePicker non è valido
     */
    public int getEpochDays() throws InputException{
        // Se datePicker si trova in uno stato valido ritorna il numero di giorni passati dalla Unix Epoch (01/01/1970) rispetto alla data indicata
        // altrimenti viene lanciata una InputException
        if(checkValid()){
            if(datePicker.getValue() != null)
                return (int) datePicker.getValue().toEpochDay();
        }
        return 0;
    }

    public boolean checkValid() throws InputException{
        // Ottenimento dei constraint non validi
        // (.validate() ritorna i constraint al momento non rispettati da datePicker)
        List<Constraint> currentConstraints = datePicker.validate();
        // Se ci sono constraint non rispettati
        if(!currentConstraints.isEmpty()){
            // Viene lanciata una InputException con tale constraint
            throw new InputException(this, currentConstraints.get(0));
        }
        // datePicker si trova in uno stato valido, e viene resettato il suo aspetto
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

    public void reset(){
        datePicker.setValue(null);
        datePicker.clear();
    }
}
