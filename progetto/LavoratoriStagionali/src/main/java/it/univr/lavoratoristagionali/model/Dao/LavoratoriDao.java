package it.univr.lavoratoristagionali.model.Dao;

import it.univr.lavoratoristagionali.types.Comune;
import it.univr.lavoratoristagionali.types.Lavoratore;
import it.univr.lavoratoristagionali.types.Lingua;

import java.util.List;

public interface LavoratoriDao {
    boolean addLavoratore(Lavoratore nuovoLavoratore);
    List<Lavoratore> getLavoratori(String nome, String cognome);
    boolean deleteLavoratore(int idDaEliminare);
    boolean updateLavoratore(Lavoratore lavoratoreDaModificare);
    Lavoratore getLavoratore(int idDacCercare);
    List<Lavoratore> searchLavoratori(List<Lingua> lingue, String flagl, List<Comune> comuni,String flagc, String flag);
}