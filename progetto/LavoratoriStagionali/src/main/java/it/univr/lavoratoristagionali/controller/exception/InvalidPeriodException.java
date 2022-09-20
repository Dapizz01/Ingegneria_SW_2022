package it.univr.lavoratoristagionali.controller.exception;

import it.univr.lavoratoristagionali.controller.validated.MFXValidated;

/**
 * Exception che indica un errore riguardante due date
 * (es: campi non compilati, sovrapposizione di date, ...)
 */
public class InvalidPeriodException extends InputException{
    public InvalidPeriodException(MFXValidated item, String message){
        super(item, message);
    }
}
