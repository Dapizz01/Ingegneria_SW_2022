package it.univr.lavoratoristagionali.controller.enums;

/**
 * Enum di possibili errori nei campi, con il corrispettivo messaggio di errore
 */
public enum Check {
    /**
     * Indica che il campo accetta solo cifre numeriche
     */
    NUMBERS_ONLY("Il campo può contenere solo numeri"),
    /**
     * Indica che il campo accetta solo lettere e spazi
     */
    LETTERS_ONLY("Il campo può contenere solo lettere"),
    /**
     * Indica che il campo deve contenere almeno un carattere
     */
    NON_EMPTY("Il campo non può essere vuoto"),
    /**
     * Indica che il campo a scelta deve avere almeno un valore selezionato
     */
    AT_LEAST_ONE_SELECTED("Selezionare almeno una opzione"),
    /**
     * Indica che il campo deve contenere un numero telefonico valido
     */
    TELEPHONE_FORMAT("Il numero telefonico non è corretto"),
    /**
     * Indica che il campo deve contenere un indirizzo email valido
     */
    EMAIL_FORMAT("La e-mail inserita non è corretta"),
    /**
     * Indica che il campo deve contenere una data passata
     */
    UP_TO_NOW("La data deve essere passata"),
    /**
     * Indica che il campo deve contenere una data passata da almeno 18 anni
     */
    MUST_BE_ADULT("Il lavoratore deve essere maggiorenne"),
    /**
     * Indica che il campo deve contenere una data passata da meno di 5 anni
     */
    FROM_FIVE_YEARS_AGO("La data deve essere passata da meno di 5 anni"),
    /**
     * Indica che il campo deve contenere una data futura
     */
    FROM_NOW("La data deve essere futura");

    private final String label;

    private Check(String label){
        this.label = label;
    }

    public String getLabel(){
        return this.label;
    }
}
