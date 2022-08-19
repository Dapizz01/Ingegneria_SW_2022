package it.univr.lavoratoristagionali.controller;

public enum Errore {
    NUMBERS_ONLY("Il campo può contenere solo numeri"),
    LETTERS_ONLY("Il campo può contenere solo lettere"),
    NON_EMPTY("Il campo non può essere vuoto");

    private final String label;

    private Errore(String label){
        this.label = label;
    }

    public String getLabel(){
        return this.label;
    }
}
