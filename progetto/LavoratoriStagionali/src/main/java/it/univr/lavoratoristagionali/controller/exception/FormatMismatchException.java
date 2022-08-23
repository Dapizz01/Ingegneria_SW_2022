package it.univr.lavoratoristagionali.controller.exception;

import it.univr.lavoratoristagionali.controller.validated.MFXValidated;

public class FormatMismatchException extends InputException{
    public FormatMismatchException(MFXValidated item, String message){
        super(item, message);
    }
}
