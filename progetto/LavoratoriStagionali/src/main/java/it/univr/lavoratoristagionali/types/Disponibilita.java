package it.univr.lavoratoristagionali.types;

import java.time.LocalDate;

public class Disponibilita{
    private final int inizioPeriodo;
    private final int finePeriodo;
    private final Comune comune;

    public Disponibilita(int inizioPeriodo, int finePeriodo, Comune comune){
        this.inizioPeriodo = inizioPeriodo;
        this.finePeriodo = finePeriodo;
        this.comune = comune;
    }

    public int getInizioPeriodo(){
        return inizioPeriodo;
    }

    public int getFinePeriodo(){
        return finePeriodo;
    }

    public Comune getComune(){
        return comune;
    }

    public String toString(){
        return "Dal " + LocalDate.ofEpochDay(inizioPeriodo) + " al " + LocalDate.ofEpochDay(finePeriodo) + " nel comune di " + comune;
    }
}