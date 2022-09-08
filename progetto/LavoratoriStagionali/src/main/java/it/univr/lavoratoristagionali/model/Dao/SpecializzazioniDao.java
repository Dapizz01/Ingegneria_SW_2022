package it.univr.lavoratoristagionali.model.Dao;

import it.univr.lavoratoristagionali.types.Specializzazione;

import java.util.List;

public interface SpecializzazioniDao {
    /**
     * Si collega al DataBase preleva tutti le specializzazioni al suo interno
     *
     * @return List<Specializzazione>: lista completa delle specializzazioni presenti nel DB
     */
    public List<Specializzazione> getSpecializzazioni();
}