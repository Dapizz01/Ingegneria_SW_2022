package it.univr.lavoratoristagionali.types;

public class Comune {
    private final String nomeComune;

    public Comune(String nomeComune) {
        this.nomeComune = nomeComune;
    }

    public String getNomeComune(){
        return nomeComune;
    }

    public String toString(){
        return nomeComune;
    }

    @Override
    public boolean equals(Object obj) {
        return (obj instanceof Comune) ? getNomeComune().equals(((Comune) obj).getNomeComune()) : false;
    }
}
