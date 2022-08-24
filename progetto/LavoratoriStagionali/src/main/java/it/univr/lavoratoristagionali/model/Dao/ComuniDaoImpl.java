package it.univr.lavoratoristagionali.model.Dao;

import it.univr.lavoratoristagionali.types.Comune;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ComuniDaoImpl implements ComuniDao {

    @Override
    public List<Comune> getComuni() {
        List<Comune> comuni = new ArrayList<>();

        Connection c = null;
        Statement stmt = null;

        try {
            Class.forName("org.sqlite.JDBC");
            // -------------Connessione database-----------------
            c = DriverManager.getConnection("jdbc:sqlite:LavoratoriStagionali.db");
            System.out.println("Opened database successfully<ComuniDaoImpl>");
            //------------------------------------------------

            //------------------Selezione---------------
            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM Comuni;");

            while (rs.next()) {
                String nomeComune = rs.getString("NomeComune");

                Comune comune = new Comune(nomeComune);
                comuni.add(comune);
            }

            rs.close();
            //----------------------------------------------------

            stmt.close();
            c.close();

            return comuni;

        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }

        return comuni;
    }
}