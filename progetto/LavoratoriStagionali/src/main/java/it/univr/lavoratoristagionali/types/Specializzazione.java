package it.univr.lavoratoristagionali.types;

public class Specializzazione {
    private final int ID_Specializzazione;
    private final String nomeSpecializzazione;

    public Specializzazione(int ID_Specializzazione, String nomeSpecializzazione) {
        this.ID_Specializzazione = ID_Specializzazione;
        this.nomeSpecializzazione = nomeSpecializzazione;
    }

    public int getID() {
        return ID_Specializzazione;
    }

    public String getNomeSpecializzazione(){
        return nomeSpecializzazione;
    }

    public String toString(){
        return nomeSpecializzazione;
    }
}
