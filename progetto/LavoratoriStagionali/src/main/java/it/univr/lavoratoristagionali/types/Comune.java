package it.univr.lavoratoristagionali.types;

public class Comune {
    private final int ID_Comune;
    private final String nomeComune;

    public Comune(int ID_Comune, String nomeComune) {
        this.ID_Comune = ID_Comune;
        this.nomeComune = nomeComune;
    }

    public int getID() {
        return ID_Comune;
    }

    public String getNomeComune(){
        return nomeComune;
    }

    public String toString(){
        return nomeComune;
    }
}
