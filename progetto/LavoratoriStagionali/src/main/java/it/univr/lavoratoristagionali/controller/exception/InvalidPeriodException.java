package it.univr.lavoratoristagionali.controller.exception;

import it.univr.lavoratoristagionali.controller.validated.MFXValidated;

public class InvalidPeriodException extends InputException{
    public InvalidPeriodException(MFXValidated item, String message){
        super(item, message);
    }
}
