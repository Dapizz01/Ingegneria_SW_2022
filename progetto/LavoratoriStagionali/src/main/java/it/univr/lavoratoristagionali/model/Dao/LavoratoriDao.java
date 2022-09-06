package it.univr.lavoratoristagionali.model.Dao;

import it.univr.lavoratoristagionali.filters.*;
import it.univr.lavoratoristagionali.types.Comune;
import it.univr.lavoratoristagionali.types.Lavoratore;
import it.univr.lavoratoristagionali.types.Lingua;

import java.util.List;

public interface LavoratoriDao {
    int addLavoratore(Lavoratore nuovoLavoratore);
    List<Lavoratore> getLavoratori(String nome, String cognome);
    boolean deleteLavoratore(int idDaEliminare);
    int updateLavoratore(Lavoratore lavoratoreDaModificare);
    Lavoratore getLavoratore(int idDacCercare);
    List<Lavoratore> searchLavoratori(LingueFilter lingueFilter, ComuniFilter comuniFilter, PatentiFilter patentiFilter, SpecializzazioniFilter specializzazioniFilter, AutomunitoFilter automunitoFilter, DisponibilitaFilter disponibilitaFilter, DataNascitaFilter dataNascitaFilter, Flag flag);
}