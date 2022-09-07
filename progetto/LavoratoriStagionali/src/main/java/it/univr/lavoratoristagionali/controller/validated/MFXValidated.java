package it.univr.lavoratoristagionali.controller.validated;

import it.univr.lavoratoristagionali.controller.exception.InputException;

public interface MFXValidated {

    /**
     * Controlla se il campo è in uno stato valido, lancia una InputException se non lo è.
     *
     * @return true se l'oggetto rispetta i Constraint, altrimenti false
     * @throws InputException Lanciata se l'oggetto non rispetta i Constraint
     */
    boolean checkValid() throws InputException;

    /**
     * Setta lo stile dell'elemento come errore, inoltre setta un messaggio su Label di errore e rende visibile tale label
     *
     * @param message Messaggio da stampare su Label di errore
     */
    void showError(String message);

    /**
     * Setta lo stile dell'elemento come corretto, e nasconde Label di errore
     */
    void showCorrect();

    /**
     * Setta lo stile dell'elemento come default, e nasconde Label di errore
     */
    void showDefault();

    /**
     * Resetta sia il contenuto mostrato che il valore effettivo contenuto nel campo, questo perchè
     * .clear() elimina solo il teso mostrato
     */
    void reset();
}
