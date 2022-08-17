package it.univr.lavoratoristagionali.types;

public class Lingua {
    private final int ID_Lingua;
    private final String nomeLingua;

    public Lingua(int ID_Lingua, String nomeLingua) {
        this.ID_Lingua = ID_Lingua;
        this.nomeLingua = nomeLingua;
    }

    public int getID() {
        return ID_Lingua;
    }

    public String getNomeLingua(){
        return nomeLingua;
    }

    public String toString(){
        return this.nomeLingua;
    }
}
