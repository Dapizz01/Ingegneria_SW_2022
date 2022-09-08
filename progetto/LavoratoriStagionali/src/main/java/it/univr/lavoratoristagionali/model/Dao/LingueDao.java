package it.univr.lavoratoristagionali.model.Dao;

import it.univr.lavoratoristagionali.types.Lingua;

import java.util.List;

public interface LingueDao {
    /**
     * Si collega al DataBase preleva tutti le lingue al suo interno
     *
     * @return List<Lingua>: lista completa delle lingue presenti nel DB
     */
    public List<Lingua> getLingue();
}