package it.univr.lavoratoristagionali.model.Dao;

import it.univr.lavoratoristagionali.types.Specializzazione;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class SpecializzazioniDaoImpl implements SpecializzazioniDao {

    List<Specializzazione> specializzazioni = new ArrayList<Specializzazione>();

    @Override
    public List<Specializzazione> getSpecializzazioni() {
        Connection c = null;
        Statement stmt = null;

        try {
            Class.forName("org.sqlite.JDBC");
            // -------------Connessione database-----------------
            c = DriverManager.getConnection("jdbc:sqlite:LavoratoriStagionali.db");
            System.out.println("Opened database successfully<SpecializzazioniDaoImpl>");
            //------------------------------------------------

            //------------------Selezione---------------
            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM Specializzazioni;");

            while (rs.next()) {
                String nomeSpecializzazione = rs.getString("NomeSpecializzazione");

                Specializzazione specializzazione = new Specializzazione(nomeSpecializzazione);
                specializzazioni.add(specializzazione);
            }

            rs.close();
            //----------------------------------------------------

            stmt.close();
            c.close();

            return specializzazioni;

        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }

        return specializzazioni;
    }
}