package it.univr.lavoratoristagionali.controller.enums;

/**
 * Enum dei valori di default delle risoluzioni della finestra di JavaFX
 */
public enum Resolution {
    /**
     * Larghezza della finestra in pixel
     */
    WIDTH(1280),
    /**
     * Altezza della finestra in pixel
     */
    HEIGHT(720);

    private final int label;

    Resolution(int label){
        this.label = label;
    }

    public int getLabel(){
        return this.label;
    }
}
