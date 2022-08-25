package it.univr.lavoratoristagionali.types;

public class Contatto {
    private final int ID_Contatto;
    private final String nome;
    private final String cognome;
    private final String telefono;
    private final String email;

    public Contatto(int ID_Contatto, String nome, String cognome, String telefono, String email) {
        this.ID_Contatto = ID_Contatto;
        this.nome = nome;
        this.cognome = cognome;
        this.telefono = telefono;
        this.email = email;
    }

    public int getID() {
        return ID_Contatto;
    }

    public String getNome() {
        return nome;
    }

    public String getCognome() {
        return cognome;
    }

    public String getTelefono() {
        return telefono;
    }

    public String getEmail() {
        return email;
    }

    public String toString() {
        return "Nominativo: " + nome + " " + cognome +
                " - Telefono: " + telefono +
                " - E-mail: " + email;
    }
}
