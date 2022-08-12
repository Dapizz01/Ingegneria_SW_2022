package it.univr.lavoratoristagionali.model.Dao;

public interface LoginDao {
    public boolean verificaLogin(String user, String password);
}