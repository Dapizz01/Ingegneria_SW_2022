package it.univr.lavoratoristagionali.model.Dao;

import it.univr.lavoratoristagionali.types.Login;

public interface LoginDao {
    /**
     * Si collega al DataBase per verificare se lo User e la Password inseriti in input dal dipendente corrispondono a una coppia User/Password presente nel DataBase
     *
     * @param login Oggetto contenente la stringhe User e Password inserite dal dipendente al momento del Login sulla piattaforma
     * @return true se la coppia User e Password all'interno di Login corrisponde a una coppia User/Password nel DB, false altrimenti
     */
    public boolean verificaLogin(Login login);
}