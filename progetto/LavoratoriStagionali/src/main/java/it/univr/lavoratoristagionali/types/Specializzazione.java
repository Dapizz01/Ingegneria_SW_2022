package it.univr.lavoratoristagionali.types;

public class Specializzazione {
    private final String nomeSpecializzazione;

    public Specializzazione(String nomeSpecializzazione) {
        this.nomeSpecializzazione = nomeSpecializzazione;
    }

    public String getNomeSpecializzazione(){
        return nomeSpecializzazione;
    }

    public String toString(){
        return nomeSpecializzazione;
    }

    @Override
    public boolean equals(Object object){
        return (object instanceof Specializzazione) ? nomeSpecializzazione.equals(((Specializzazione) object).nomeSpecializzazione) : false;
    }
}
