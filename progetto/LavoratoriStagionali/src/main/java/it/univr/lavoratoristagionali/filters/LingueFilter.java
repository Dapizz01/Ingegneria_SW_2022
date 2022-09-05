package it.univr.lavoratoristagionali.filters;

import it.univr.lavoratoristagionali.types.Lingua;

import java.util.List;

public class LingueFilter {
    private final List<Lingua> lingue;
    private final Flag flag;

    public LingueFilter(List<Lingua> lingue, Flag flag) {
        this.lingue = lingue;
        this.flag = flag;
    }

    public List<Lingua> getLingue() {
        return lingue;
    }

    public Flag getFlag() {
        return flag;
    }
}
