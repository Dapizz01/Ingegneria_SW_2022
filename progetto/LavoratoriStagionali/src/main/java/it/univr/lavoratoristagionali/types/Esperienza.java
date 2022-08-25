package it.univr.lavoratoristagionali.types;

import java.time.LocalDate;

public class Esperienza {
    private final int ID_Esperienza;
    private final String nomeAzienda;
    private final int retribuzioneLordaGiornaliera;
    private final int inizioPeriodo;
    private final int finePeriodo;
    private final Comune comune;
    private final Specializzazione specializzazione;

    public Esperienza(int ID_Esperienza, String nomeAzienda, int retribuzioneLordaGiornaliera, int inizioPeriodo, int finePeriodo, Comune comune, Specializzazione specializzazione) {
        this.ID_Esperienza = ID_Esperienza;
        this.nomeAzienda = nomeAzienda;
        this.retribuzioneLordaGiornaliera = retribuzioneLordaGiornaliera;
        this.inizioPeriodo = inizioPeriodo;
        this.finePeriodo = finePeriodo;
        this.comune = comune;
        this.specializzazione = specializzazione;
    }

    public int getID(){
        return ID_Esperienza;
    }

    public String getNomeAzienda() {
        return nomeAzienda;
    }

    public int getRetribuzioneLordaGiornaliera() {
        return retribuzioneLordaGiornaliera;
    }

    public int getInizioPeriodo(){
        return inizioPeriodo;
    }

    public int getFinePeriodo(){
        return finePeriodo;
    }

<<<<<<< HEAD
    public Comune getComune() { return comune; }

    public Specializzazione getSpecializzazione() {return specializzazione; }
=======
    public Comune getComune(){
        return comune;
    }

    public Specializzazione getSpecializzazione(){
        return specializzazione;
    }

    public String toString(){
        return "Svolta presso " + getNomeAzienda() +
                "con specializzazione " + getSpecializzazione() +
                ", a " + getComune() +
                ", dal " + LocalDate.ofEpochDay(getInizioPeriodo()) +
                " al " + LocalDate.ofEpochDay(getFinePeriodo()) +
                ", retribuzione di €" + getRetribuzioneLordaGiornaliera();
    }
>>>>>>> InserimentoLavoratoreController
}
