package it.univr.lavoratoristagionali.types;

public class Esperienza {
    private final int ID_Esperienza;
    private final String nomeAzienda;
    private final int retribuzioneLordaGiornaliera;
    private final int inizioPeriodo;
    private final int finePeriodo;
    private final Comune comune;

    public Esperienza(int ID_Esperienza, String nomeAzienda, int retribuzioneLordaGiornaliera, int inizioPeriodo, int finePeriodo, Comune comune) {
        this.ID_Esperienza = ID_Esperienza;
        this.nomeAzienda = nomeAzienda;
        this.retribuzioneLordaGiornaliera = retribuzioneLordaGiornaliera;
        this.inizioPeriodo = inizioPeriodo;
        this.finePeriodo = finePeriodo;
        this.comune = comune;
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

    public Comune getComune(){
        return comune;
    }
}
