package it.univr.lavoratoristagionali.filters;

import it.univr.lavoratoristagionali.types.Comune;

import java.util.List;

public class ComuniFilter {
    private final List<Comune> comuni;
    private final Flag flag;

    public ComuniFilter(List<Comune> comuni, Flag flag) {
        this.comuni = comuni;
        this.flag = flag;
    }

    public List<Comune> getComuni() {
        return comuni;
    }

    public Flag getFlag() {
        return flag;
    }
}
