package it.univr.lavoratoristagionali.filters;

public class DisponibilitaFilter {
    private final int inizioPeriodo;
    private final int finePeriodo;

    public DisponibilitaFilter(int inizioPeriodo, int finePeriodo) {
        this.inizioPeriodo = inizioPeriodo;
        this.finePeriodo = finePeriodo;
    }

    public int getInizioPeriodo() {
        return inizioPeriodo;
    }

    public int getFinePeriodo() {
        return finePeriodo;
    }
}
