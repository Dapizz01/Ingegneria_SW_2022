package it.univr.lavoratoristagionali.model;

//import it.univr.lavoratoristagionali.model.Dao.LoginDao;
//import it.univr.lavoratoristagionali.model.Dao.LoginDaoImpl;

import java.sql.*;
import java.util.Scanner;

public class MainDemo {
    public static void main( String args[] ) {
        Connection c = null;
        Statement stmt = null;

        try {
            Class.forName("org.sqlite.JDBC");
            // -------------Connessione database-----------------
            c = DriverManager.getConnection("jdbc:sqlite:test.db");
            System.out.println("Opened database successfully");
            //------------------------------------------------

            stmt = c.createStatement();

            //---------------Creazione Tabelle----------------
            String sql = "CREATE TABLE IF NOT EXISTS Dipendenti " +
                    "(ID_Dipendente  INT PRIMARY KEY NOT NULL," +
                    " Nome           VARCHAR(20), " +
                    " Cognome        VARCHAR(20), " +
                    " Data_nascita   TEXT, " +
                    " N_telefono     CHAR(10)," +
                    " Email          VARCHAR(50), " +
                    " User           VARCHAR(20) NOT NULL, " +
                    " Password       VARCHAR(20) NOT NULL,  " +
                    " Comune_nascita VARCHAR(20)) ";
            stmt.executeUpdate(sql);

            System.out.println("Tabella dipendenti creata");
            //---------------------------------------------------

            // ------------Popolamento tabelle-------------------
            c.setAutoCommit(false);
            sql = "INSERT INTO Dipendenti (ID_Dipendente,Nome,Cognome,Data_nascita,N_telefono,Email,User,Password,Comune_nascita) " +
                    "VALUES (1, 'Mario', 'Rossi', '2000-03-15 10:20:05.123', '1234567890', 'mariorossi@gmail.com', 'aaa', 'bbb', 'Legnago');";
            stmt.executeUpdate(sql);


            sql = "INSERT INTO Dipendenti (ID_Dipendente,Nome,Cognome,Data_nascita,N_telefono,Email,User,Password,Comune_nascita) " +
                    "VALUES (2, 'Marco', 'Dp', '2019-02-12 10:20:05.123', '1234567899', 'ciao123@gmail.com', 'ccc', 'ddd', 'Verona')," +
                           "(3, 'Nico', 'Modenese', '2001-12-30 10:20:05.123', '1231231231', 'ciao12345@gmail.com', 'eee', 'fff', 'Verona');";
            stmt.executeUpdate(sql);

            System.out.println("Tabella dipendenti popolata");

            stmt.close();
            c.commit(); // Invia le query scritte fino ad adesso(solo se AutoCommit è disabilitato)
            //--------------------------------------------------
            c.close();

        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }

        Scanner keyboard = new Scanner(System.in);
        System.out.print("Inseirire user da cercare nel db: ");
        String user = keyboard.nextLine();
        System.out.print("Inseirire password da cercare nel db: ");
        String password = keyboard.nextLine();

        //LoginDao loginDao = new LoginDaoImpl();
        //boolean v = loginDao.verificaLogin(user,password);

        boolean v = verificaLogin(user,password);
        System.out.println("Risultato: " + v);

        //prova();

    }

    public static boolean verificaLogin(String userDaVerificare, String passwordDaVerificare) {
        Connection c = null;
        Statement stmt = null;

        try {
            Class.forName("org.sqlite.JDBC");
            // -------------Connessione database-----------------
            c = DriverManager.getConnection("jdbc:sqlite:test.db");
            System.out.println("Opened database successfully");
            //------------------------------------------------

            stmt = c.createStatement();

            //------------------Selezione---------------
            c.setAutoCommit(false);

            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT User, Password FROM Dipendenti;");

            boolean verifica = false;

            while (rs.next()) {
                String user = rs.getString("User");
                String password = rs.getString("Password");
                System.out.println("User = " + user);
                System.out.println("Password = " + password);
                System.out.println();

                if ( (userDaVerificare.equals(user) && passwordDaVerificare.equals(password)) && verifica == false) {
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

    public static void prova() { //per provare cosa succese se faccio una select su un User non esistente
        Connection c = null;
        Statement stmt = null;

        try {
            Class.forName("org.sqlite.JDBC");
            // -------------Connessione database-----------------
            c = DriverManager.getConnection("jdbc:sqlite:test.db");
            System.out.println("Opened database successfully");
            //------------------------------------------------

            stmt = c.createStatement();

            //------------------Selezione---------------
            c.setAutoCommit(false);

            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT User FROM Dipendenti WHERE User = 'zzz';");
            String user = rs.getString("User");
            System.out.println("User: " + user);


            rs.close();
            //----------------------------------------------------

            stmt.close();
            c.close();


        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
    }
}

