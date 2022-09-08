package it.univr.lavoratoristagionali.model.Dao;

import it.univr.lavoratoristagionali.types.Comune;

import java.util.List;

public interface ComuniDao {
    /**
     * Si collega al DataBase preleva tutti i comuni al suo interno
     *
     * @return List<Comune>: lista completa dei comuni presenti nel DB
     */
    public List<Comune> getComuni();
}