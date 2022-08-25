package it.univr.lavoratoristagionali.types;

public class Patente {
    private final String nomePatente;

    public Patente(String nomePatente) {
        this.nomePatente = nomePatente;
    }

    public String getNomePatente(){
        return nomePatente;
    }

    public String toString(){
        return this.nomePatente;
    }
}
