package it.univr.lavoratoristagionali.model.Dao;

import it.univr.lavoratoristagionali.types.Patente;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class PatentiDaoImpl implements PatentiDao {

    @Override
    public List<Patente> getPatenti() {
        List<Patente> patenti = new ArrayList<>();

        Connection c = null;
        Statement stmt = null;

        try {
            Class.forName("org.sqlite.JDBC");
            // -------------Connessione database-----------------
            c = DriverManager.getConnection("jdbc:sqlite:LavoratoriStagionali.db");
            System.out.println("Opened database successfully<PatentiDaoImpl>");
            //------------------------------------------------

            //------------------Selezione---------------
            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM Patenti;");

            while (rs.next()) {
                String nomePatente = rs.getString("NomePatente");

                Patente patente = new Patente(nomePatente);
                patenti.add(patente);
            }

            rs.close();
            //----------------------------------------------------

            stmt.close();
            c.close();

            return patenti;

        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }

        return patenti;
    }
}
