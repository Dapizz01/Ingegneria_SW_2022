package it.univr.lavoratoristagionali.model.Dao;

import it.univr.lavoratoristagionali.types.Login;

import java.sql.*;

public class LoginDaoImpl implements LoginDao {

    @Override
    public boolean verificaLogin(Login login) {
        Connection c = null;
        Statement stmt = null;

        try {
            Class.forName("org.sqlite.JDBC");
            // -------------Connessione database-----------------
            c = DriverManager.getConnection("jdbc:sqlite:LavoratoriStagionali.db");
            System.out.println("Opened database successfully");
            //------------------------------------------------

            //------------------Selezione---------------
            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT User, Password FROM Dipendenti;");

            boolean verifica = false;

            while (rs.next()) {
                String user = rs.getString("User");
                String password = rs.getString("Password");

                if ( (login.getUser().equals(user) && login.getPassword().equals(password)) && verifica == false) {
                    verifica = true;
                }
            }

            rs.close();
            //----------------------------------------------------

            stmt.close();
            c.close();

            return verifica;

        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }

        return false;
    }
}
