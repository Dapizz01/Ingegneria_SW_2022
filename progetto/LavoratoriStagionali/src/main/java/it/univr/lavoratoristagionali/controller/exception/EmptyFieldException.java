package it.univr.lavoratoristagionali.controller.exception;

import it.univr.lavoratoristagionali.controller.validated.MFXValidated;

public class EmptyFieldException extends InputException{
    public EmptyFieldException(MFXValidated item, String message){
        super(item, message);
    }
}
