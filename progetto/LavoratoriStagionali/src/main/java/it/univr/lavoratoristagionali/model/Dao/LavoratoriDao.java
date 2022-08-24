package it.univr.lavoratoristagionali.model.Dao;

import it.univr.lavoratoristagionali.types.Lavoratore;

import java.util.List;

public interface LavoratoriDao {
    boolean addLavoratore(Lavoratore nuovoLavoratore);

    //int getLavoratori(String nome, String cognome);
}