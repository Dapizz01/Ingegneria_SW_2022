package it.univr.lavoratoristagionali.controller.enums;

/**
 * Enum di possibili errori nei campi, con il corrispettivo messaggio di errore
 */
public enum Check {
    NUMBERS_ONLY("Il campo può contenere solo numeri"),
    LETTERS_ONLY("Il campo può contenere solo lettere"),
    NON_EMPTY("Il campo non può essere vuoto"),
    AT_LEAST_ONE_SELECTED("Selezionare almeno una opzione"),
    TELEPHONE_FORMAT("Il numero telefonico non è corretto"),
    EMAIL_FORMAT("La e-mail inserita non è corretta"),
    UP_TO_NOW("La data deve essere passata"),
    MUST_BE_ADULT("Il lavoratore deve essere maggiorenne"),
    FROM_FIVE_YEARS_AGO("La data deve essere passata da meno di 5 anni"),
    FROM_NOW("La data deve essere futura");

    private final String label;

    private Check(String label){
        this.label = label;
    }

    public String getLabel(){
        return this.label;
    }
}
