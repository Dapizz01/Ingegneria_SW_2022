package it.univr.lavoratoristagionali.controller.validated;

import it.univr.lavoratoristagionali.controller.exception.InputException;

public interface MFXValidated {
    boolean checkValid() throws InputException;
    void showError(String message);
    void showCorrect();
    void showDefault();
}
