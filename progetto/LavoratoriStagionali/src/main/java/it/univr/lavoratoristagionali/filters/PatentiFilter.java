package it.univr.lavoratoristagionali.filters;

import it.univr.lavoratoristagionali.types.Patente;

import java.util.List;

public class PatentiFilter {
    private final List<Patente> patenti;
    private final Flag flag;

    public PatentiFilter(List<Patente> patenti, Flag flag) {
        this.patenti = patenti;
        this.flag = flag;
    }

    public List<Patente> getPatenti() {
        return patenti;
    }

    public Flag getFlag() {
        return flag;
    }
}
