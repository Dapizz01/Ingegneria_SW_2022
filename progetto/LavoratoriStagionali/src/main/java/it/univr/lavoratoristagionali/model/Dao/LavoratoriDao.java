package it.univr.lavoratoristagionali.model.Dao;

import it.univr.lavoratoristagionali.types.Lavoratore;

import java.util.List;

public interface LavoratoriDao {
    boolean addLavoratore(Lavoratore nuovoLavoratore);
    List<Lavoratore> getLavoratori(String nome, String cognome);
    boolean deleteLavoratore(int idDaEliminare);
    boolean updateLavoratore(Lavoratore lavoratoreDaModificare);
}