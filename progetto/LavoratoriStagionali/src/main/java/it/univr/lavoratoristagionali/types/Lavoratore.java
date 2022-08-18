package it.univr.lavoratoristagionali.types;

import java.util.List;

public class Lavoratore {
    private final int ID_Lavoratore;
    private final String nomeLavoratore;
    private final String cognomeLavoratore;
    private final Comune comuneNascita;
    private final Comune comuneAbitazione;
    private final int dataNascita;
    private final Lingua nazionalita;
    private final String email;
    private final String telefono;
    private final boolean automunito;
    private final List<Esperienza> esperienze;
    private final List<Lingua> lingue;
    private final List<Contatto> contatti;
    private final List<Patente> patenti;
    private final List<Disponibilita> disponibilita;


    public Lavoratore(int id_lavoratore, String nomeLavoratore, String cognomeLavoratore, Comune comuneNascita, Comune comuneAbitazione, int dataNascita, Lingua nazionalita, String email, String telefono, boolean automunito, List<Esperienza> esperienze, List<Lingua> lingue, List<Contatto> contatti, List<Patente> patenti, List<Disponibilita> disponibilita) {
        ID_Lavoratore = id_lavoratore;
        this.nomeLavoratore = nomeLavoratore;
        this.cognomeLavoratore = cognomeLavoratore;
        this.comuneNascita = comuneNascita;
        this.comuneAbitazione = comuneAbitazione;
        this.dataNascita = dataNascita;
        this.nazionalita = nazionalita;
        this.email = email;
        this.telefono = telefono;
        this.automunito = automunito;
        this.esperienze = esperienze;
        this.lingue = lingue;
        this.contatti = contatti;
        this.patenti = patenti;
        this.disponibilita = disponibilita;
    }

    public int getID() {
        return ID_Lavoratore;
    }

    public String getNomeLavoratore() {
        return nomeLavoratore;
    }

    public String getCognomeLavoratore() {
        return cognomeLavoratore;
    }

    public Comune getComuneNascita() {
        return comuneNascita;
    }

    public Comune getComuneAbitazione() {
        return comuneAbitazione;
    }

    public int getDataNascita() {
        return dataNascita;
    }

    public Lingua getNazionalita() {
        return nazionalita;
    }

    public String getEmail() {
        return email;
    }

    public String getTelefono() {
        return telefono;
    }

    public boolean isAutomunito() {
        return automunito;
    }

    public List<Esperienza> getEsperienze() {
        return esperienze;
    }

    public List<Lingua> getLingue() {
        return lingue;
    }

    public List<Contatto> getContatti() {
        return contatti;
    }

    public List<Patente> getPatenti() {
        return patenti;
    }

    public List<Disponibilita> getDisponibilita() {
        return disponibilita;
    }
}
