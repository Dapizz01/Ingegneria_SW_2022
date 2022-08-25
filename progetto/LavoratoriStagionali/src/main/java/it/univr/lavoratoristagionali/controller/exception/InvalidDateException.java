package it.univr.lavoratoristagionali.controller.exception;

import it.univr.lavoratoristagionali.controller.validated.MFXValidated;

public class InvalidDateException extends InputException{
    public InvalidDateException(MFXValidated item, String message) {
        super(item, message);
    }
}
