package it.univr.lavoratoristagionali.model.Dao;

import it.univr.lavoratoristagionali.types.Patente;

import java.util.List;

public interface PatentiDao {
    /**
     * Si collega al DataBase preleva tutti le patenti al suo interno
     *
     * @return List<Patente>: lista completa delle patenti presenti nel DB
     */
    public List<Patente> getPatenti();
}