package it.univr.lavoratoristagionali.controller.validated;

import it.univr.lavoratoristagionali.controller.exception.InputException;

public interface MFXValidated {

    // Controlla se il campo è in uno stato valido, lancia una InputException se non lo è
    boolean checkValid() throws InputException;

    // Setta lo stile dell'elemento come errore
    void showError(String message);

    // Setta lo stile dell'elemento come corretto
    void showCorrect();

    // Setta lo stile dell'elemento come default
    void showDefault();

    // Resetta sia il contenuto mostrato che il valore effettivo contenuto nel campo, questo perchè
    // (.clear()) elimina solo il teso mostrato
    void reset();
}
