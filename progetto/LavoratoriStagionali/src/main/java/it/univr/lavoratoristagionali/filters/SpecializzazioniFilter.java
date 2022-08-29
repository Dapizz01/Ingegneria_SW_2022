package it.univr.lavoratoristagionali.filters;

import it.univr.lavoratoristagionali.types.Specializzazione;

import java.util.List;

public class SpecializzazioniFilter {
    private final List<Specializzazione> specializzazioni;
    private final Flag flag;


    public SpecializzazioniFilter(List<Specializzazione> specializzazioni, Flag flag) {
        this.specializzazioni = specializzazioni;
        this.flag = flag;
    }

    public List<Specializzazione> getSpecializzazioni() {
        return specializzazioni;
    }

    public Flag getFlag() {
        return flag;
    }
}
