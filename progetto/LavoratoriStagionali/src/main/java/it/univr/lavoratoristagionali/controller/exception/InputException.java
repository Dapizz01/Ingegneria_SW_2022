package it.univr.lavoratoristagionali.controller.exception;

import io.github.palexdev.materialfx.validation.Constraint;
import it.univr.lavoratoristagionali.controller.validated.MFXValidated;

public class InputException extends Exception{
    public InputException(MFXValidated item, String message){
        super(message);
        item.showError(message);
    }

    public InputException(MFXValidated item, Constraint constraint){
        super(constraint.getMessage());
        item.showError(constraint.getMessage());
    }
}
