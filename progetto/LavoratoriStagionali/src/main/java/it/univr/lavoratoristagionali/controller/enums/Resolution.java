package it.univr.lavoratoristagionali.controller.enums;

public enum Resolution {
    WIDTH(1280),
    HEIGHT(720);

    private final int label;

    private Resolution(int label){
        this.label = label;
    }

    public int getLabel(){
        return this.label;
    }
}
