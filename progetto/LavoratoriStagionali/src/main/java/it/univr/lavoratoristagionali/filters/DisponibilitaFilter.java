package it.univr.lavoratoristagionali.filters;

import it.univr.lavoratoristagionali.types.Comune;

public class DisponibilitaFilter {
    private final int inizioPeriodo;
    private final int finePeriodo;
    private  final Comune comune;

    public DisponibilitaFilter(int inizioPeriodo, int finePeriodo, Comune comune) {
        this.inizioPeriodo = inizioPeriodo;
        this.finePeriodo = finePeriodo;
        this.comune = comune;
    }

    public int getInizioPeriodo() {
        return inizioPeriodo;
    }

    public int getFinePeriodo() {
        return finePeriodo;
    }

    public Comune getComune() {
        return comune;
    }
}
