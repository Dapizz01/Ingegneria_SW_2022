package it.univr.lavoratoristagionali.model.Dao;

import it.univr.lavoratoristagionali.types.Login;

public interface LoginDao {
    public boolean verificaLogin(Login login);
}