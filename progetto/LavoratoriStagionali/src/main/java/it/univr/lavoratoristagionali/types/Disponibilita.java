package it.univr.lavoratoristagionali.types;

<<<<<<< HEAD
<<<<<<< HEAD
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
=======
=======
>>>>>>> InserimentoLavoratoreController
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
<<<<<<< HEAD
>>>>>>> InserimentoLavoratoreController
=======
>>>>>>> InserimentoLavoratoreController
}