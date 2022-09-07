package it.univr.lavoratoristagionali.controller.exception;

import io.github.palexdev.materialfx.validation.Constraint;
import it.univr.lavoratoristagionali.controller.validated.MFXValidated;
import javafx.scene.control.Label;

/**
 * Exception che indica un errore dell'utente da interfaccia grafica
 * (es: campi non compilati, inserimento errato, ...)
 */
public class InputException extends Exception{
    /**
     * Stampa a console e nel Label di errore di MFXValidated il messaggio di errore passato come parametro
     *
     * @param item Oggetto MFXValidated, contiene un Node JavaFX e il corrispettivo Label di errore
     * @param message Messaggio da stampare
     */
    public InputException(MFXValidated item, String message){
        super(message);
        item.showError(message);
    }

    /**
     * Stampa a console e nel Label di errore di MFXValidated il Constraint passato come parametro
     *
     * @param item Oggetto MFXValidated, contiene un Node JavaFX e il corrispettivo Label di errore
     * @param constraint Constraint non rispettato dall'utente
     */
    public InputException(MFXValidated item, Constraint constraint){
        super(constraint.getMessage());
        item.showError(constraint.getMessage());
    }

    /**
     * Stampa a console e in label il messaggio passato come parametro
     *
     * @param label label in cui stampare il messaggio
     * @param message Messaggio da stampare
     */
    public InputException(Label label, String message){
        super(message);
        label.setVisible(true);
        label.setText(message);
    }
}
