package it.univr.lavoratoristagionali.types;

/**
 * Rappresenta un comune, identificato dal suo nome
 */
public class Comune {
    private final String nomeComune;

    /**
     * Costruttore di Comune
     *
     * @param nomeComune nome del comune
     */
    public Comune(String nomeComune) {
        this.nomeComune = nomeComune;
    }

    /**
     * Metodo getter del nome di un comune
     *
     * @return nome del comune
     */
    public String getNomeComune(){
        return nomeComune;
    }

    /**
     * Metodo toString() di Comune, ritorna il nome del comune
     *
     * @return nome del comune
     */
    public String toString(){
        return nomeComune;
    }

    /**
     * Compara questo oggetto con un altro oggetto passato come parametro. Se l'oggetto passato come parametro è di classe
     * Comune ed ha lo stesso nome di comune di questo oggetto, allora i due oggetti sono uguali.
     *
     * @param obj Oggetto passato come parametro
     * @return true se obj è uguale 
     */
    @Override
    public boolean equals(Object obj) {
        return (obj instanceof Comune) ? getNomeComune().equals(((Comune) obj).getNomeComune()) : false;
    }
}
