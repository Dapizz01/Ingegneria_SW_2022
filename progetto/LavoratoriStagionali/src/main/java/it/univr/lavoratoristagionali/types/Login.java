package it.univr.lavoratoristagionali.types;

public class Login {
    private final String user;
    private final String password;

    public Login(String user, String password) {
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
