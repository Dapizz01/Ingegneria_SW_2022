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

    @Override
    public boolean equals(Object obj) {
        return (obj instanceof Patente) ? getNomePatente().equals(((Patente) obj).getNomePatente()) : false;
    }
}
