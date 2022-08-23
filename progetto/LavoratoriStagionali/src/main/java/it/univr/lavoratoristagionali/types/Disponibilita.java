package it.univr.lavoratoristagionali.types;

public class Disponibilita {
    private Comune comune;
    private int inizioPeriodo;
    private int finePeriodo;

    public Disponibilita(Comune comune, int inizioPeriodo, int finePeriodo) {
        this.comune = comune;
        this.inizioPeriodo = inizioPeriodo;
        this.finePeriodo = finePeriodo;
    }

    public Comune getComune() {
        return comune;
    }
    public int getInizioPeriodo() {
        return inizioPeriodo;
    }

    public int getFinePeriodo() {
        return finePeriodo;
    }
}