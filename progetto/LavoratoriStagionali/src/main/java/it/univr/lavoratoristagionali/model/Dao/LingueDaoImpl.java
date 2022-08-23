package it.univr.lavoratoristagionali.model.Dao;

import it.univr.lavoratoristagionali.types.Lingua;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class LingueDaoImpl implements LingueDao {

    List<Lingua> lingue = new ArrayList<Lingua>();

    @Override
    public List<Lingua> getLingue() {
        Connection c = null;
        Statement stmt = null;

        try {
            Class.forName("org.sqlite.JDBC");
            // -------------Connessione database-----------------
            c = DriverManager.getConnection("jdbc:sqlite:LavoratoriStagionali.db");
            System.out.println("Opened database successfully<LingueDaoImpl>");
            //------------------------------------------------

            //------------------Selezione---------------
            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM Lingue;");

            while (rs.next()) {
                String nomeLingua = rs.getString("NomeLingua");

                Lingua lingua = new Lingua(nomeLingua);
                lingue.add(lingua);
            }

            rs.close();
            //----------------------------------------------------

            stmt.close();
            c.close();

            return lingue;

        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }

        return lingue;
    }
}
