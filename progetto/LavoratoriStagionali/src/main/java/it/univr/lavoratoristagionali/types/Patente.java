package it.univr.lavoratoristagionali.types;

public class Patente {
    private final int ID_Patente;
    private final String nomePatente;

    public Patente(int ID_Patente, String nomePatente) {
        this.ID_Patente = ID_Patente;
        this.nomePatente = nomePatente;
    }

    public int getID() {
        return ID_Patente;
    }

    public String getNomePatente(){
        return nomePatente;
    }

    public String toString(){
        return this.nomePatente;
    }
}
