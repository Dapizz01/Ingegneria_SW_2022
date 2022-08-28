package it.univr.lavoratoristagionali.types;

public class Lingua{
    private final String nomeLingua;

    public Lingua(String nomeLingua) {
        this.nomeLingua = nomeLingua;
    }

    public String getNomeLingua(){
        return nomeLingua;
    }

    public String toString(){
        return this.nomeLingua;
    }

    @Override
    public boolean equals(Object obj) {
        return (obj instanceof Lingua) ? getNomeLingua().equals(((Lingua) obj).getNomeLingua()) : false;
    }
}
