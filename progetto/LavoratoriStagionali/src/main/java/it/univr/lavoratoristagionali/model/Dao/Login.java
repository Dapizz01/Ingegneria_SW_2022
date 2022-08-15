package it.univr.lavoratoristagionali.model.Dao;

public class Login {
    private String user;
    private String password;

    Login(String user, String password) {
        this.user = user;
        this.password = password;
    }

    public String getUser() {
        return user;
    }

    public String getPassword() {
        return password;
    }
}
