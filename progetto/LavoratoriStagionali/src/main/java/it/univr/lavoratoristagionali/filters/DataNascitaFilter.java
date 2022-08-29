package it.univr.lavoratoristagionali.filters;

public class DataNascitaFilter {
    private final int dataNascita;
    private final Flag flag;

    public DataNascitaFilter(int dataNascita, Flag flag) {
        this.dataNascita = dataNascita;
        this.flag = flag;
    }

    public int getDataNascita() {
        return dataNascita;
    }

    public Flag getFlag() {
        return flag;
    }
}
