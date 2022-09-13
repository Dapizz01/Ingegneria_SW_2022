package it.univr.lavoratoristagionali.model;

import java.sql.*;

public class MainDemo {
    public static void main( String args[] ) {
        Connection c = null;
        Statement stmt = null;

        try {
            Class.forName("org.sqlite.JDBC");
            // -------------Connessione database-----------------
            c = DriverManager.getConnection("jdbc:sqlite:LavoratoriStagionali.db");
            System.out.println("Opened database successfully<MainDemo>");
            //------------------------------------------------

            stmt = c.createStatement();

            //---------------Creazione Tabelle----------------
            String sql = "CREATE TABLE IF NOT EXISTS Dipendenti " +
                    "(ID_Dipendente  INTEGER PRIMARY KEY AUTOINCREMENT," +
                    " Nome           VARCHAR(20), " +
                    " Cognome        VARCHAR(20), " +
                    " DataNascita    INTEGER, " + //Numero di Giorni da 1970-01-01
                    " N_telefono     CHAR(10)," +
                    " Email          VARCHAR(50), " +
                    " User           VARCHAR(20), " +
                    " Password       VARCHAR(20),  " +
                    " ComuneNascita  VARCHAR(50), " +
                    " FOREIGN KEY (ComuneNascita) REFERENCES Comuni (NomeComune)) ";
            stmt.executeUpdate(sql);

            sql = "CREATE TABLE IF NOT EXISTS Patenti " +
                    "(NomePatente VARCHAR(3) PRIMARY KEY) ";
            stmt.executeUpdate(sql);

            sql = "CREATE TABLE IF NOT EXISTS Lingue " +
                    "(NomeLingua VARCHAR(20) PRIMARY KEY) ";
            stmt.executeUpdate(sql);

            sql = "CREATE TABLE IF NOT EXISTS Comuni " +
                    "(NomeComune VARCHAR(50) PRIMARY KEY) ";
            stmt.executeUpdate(sql);

            sql = "CREATE TABLE IF NOT EXISTS Specializzazioni " +
                    "(NomeSpecializzazione VARCHAR(20) PRIMARY KEY) ";
            stmt.executeUpdate(sql);

            sql = "CREATE TABLE IF NOT EXISTS Lavoratori " +
                    "(ID_Lavoratore INTEGER PRIMARY KEY AUTOINCREMENT," +
                    " NomeLavoratore VARCHAR(50)," +
                    " CognomeLavoratore VARCHAR(50), " +
                    " ComuneNascita VARCHAR(50), " +    //Tabella Comuni
                    " ComuneAbitazione VARCHAR(50), " + //Tabella Comuni
                    " DataNascita INTEGER, " +          //Numero di Giorni da 1970-01-01
                    " Nazionalita VARCHAR(20), " +      //Tabella Lingue
                    " Email VARCHAR(50), " +
                    " N_telefono CHAR(10), " +
                    " Automunito BOOLEAN, " +
                    " FOREIGN KEY (ComuneNascita) REFERENCES Comuni (NomeComune), " +
                    " FOREIGN KEY (ComuneAbitazione) REFERENCES Comuni (NomeComune), " +
                    " FOREIGN KEY (Nazionalita) REFERENCES Lingue (NomeLingua)) ";
            stmt.executeUpdate(sql);

            sql = "CREATE TABLE IF NOT EXISTS Esperienze " +
                    "(ID_Esperienza INTEGER PRIMARY KEY AUTOINCREMENT," +
                    " NomeAzienda VARCHAR(50)," +
                    " RetribuzioneGiornaliera INT, " +
                    " InizioPeriodo INTEGER, " +        //Numero di Giorni da 1970-01-01
                    " FinePeriodo INTEGER, " +          //Numero di Giorni da 1970-01-01
                    " NomeComune VARCHAR(50), " +
                    " NomeSpecializzazione VARCHAR(20), " +
                    " ID_Lavoratore INTEGER, " +
                    " FOREIGN KEY (NomeSpecializzazione) REFERENCES Specializzazioni(NomeSpecializzazione), " +
                    " FOREIGN KEY (NomeComune) REFERENCES Comuni(NomeComune), " +
                    " FOREIGN KEY (ID_Lavoratore) REFERENCES Lavoratori(ID_Lavoratore)) ";
            stmt.executeUpdate(sql);

            sql = "CREATE TABLE IF NOT EXISTS Contatti " +
                    "(ID_Contatto INTEGER PRIMARY KEY AUTOINCREMENT," +
                    " NomeContatto VARCHAR(20)," +
                    " CognomeContatto VARCHAR(20), " +
                    " N_telefono CHAR(10), " +
                    " Email VARCHAR(50), " +
                    " ID_Lavoratore INTEGER, " +
                    " FOREIGN KEY(ID_Lavoratore) REFERENCES Lavoratori(ID_Lavoratore)) ";
            stmt.executeUpdate(sql);

            sql = "CREATE TABLE IF NOT EXISTS Disponibilita " +
                    " (ID_Lavoratore INTEGER," +
                    " NomeComune VARCHAR(50)," +
                    " InizioPeriodo INTEGER, " +        //Numero di Giorni da 1970-01-01
                    " FinePeriodo INTEGER, " +          //Numero di Giorni da 1970-01-01
                    " PRIMARY KEY(ID_Lavoratore,NomeComune)," +
                    " FOREIGN KEY(ID_Lavoratore) REFERENCES Lavoratore(ID_Lavoratore)," +
                    " FOREIGN KEY(NomeComune) REFERENCES Comuni(NomeComune)) ";
            stmt.executeUpdate(sql);

            sql = "CREATE TABLE IF NOT EXISTS LingueParlate " +
                    "(ID_Lavoratore INTEGER," +
                    " NomeLingua VARCHAR(20)," +
                    " PRIMARY KEY(ID_Lavoratore,NomeLingua)," +
                    " FOREIGN KEY(ID_Lavoratore) REFERENCES Lavoratori(ID_Lavoratore)," +
                    " FOREIGN KEY(NomeLingua) REFERENCES Lingue(NomeLingua)) ";
            stmt.executeUpdate(sql);

            sql = "CREATE TABLE IF NOT EXISTS PatentiPossedute " +
                    "(ID_Lavoratore INTEGER NOT NULL," +
                    " NomePatente INTEGER NOT NULL," +
                    " PRIMARY KEY(ID_Lavoratore,NomePatente)," +
                    " FOREIGN KEY(ID_Lavoratore) REFERENCES Lavoratori(ID_Lavoratore)," +
                    " FOREIGN KEY(NomePatente) REFERENCES Patenti(NomePatente)) ";
            stmt.executeUpdate(sql);

            System.out.println("Tabelle DB create");
            //---------------------------------------------------

            // ------------Popolamento tabelle-------------------
            c.setAutoCommit(false);
            // Gli autoincrement non vengono inseriti
            sql = "INSERT INTO Dipendenti (Nome,Cognome,DataNascita,N_telefono,Email,User,Password,ComuneNascita) " +
                    "VALUES ('Mario', 'Rossi', '2999', '1234567890', 'mariorossi@gmail.com', 'aaa', 'bbb', 'Legnago'), " +
                    "('Mattia', 'Gialli', '1000', '1234567899', 'ciao123@gmail.com', 'username', 'password', 'Verona'), " +
                    "('Piero', 'Neri', '12000', '1231231231', 'ciao12345@gmail.com', 'eee', 'fff', 'Verona');";
            stmt.executeUpdate(sql);

            sql = "INSERT INTO Patenti (NomePatente)" +
                    "VALUES ('AM')," + "('A1')," + "('A2')," + "('A')," + "('B1')," + "('B')," + "('C1')," +
                    "('C')," + "('D1')," + "('D')," + "('BE')," + "('C1E')," + "('CE')," + "('DE')," + "('D1E');";
            stmt.executeUpdate(sql);

            sql = "INSERT INTO Lingue (NomeLingua)" +
                    "VALUES ('Italiana')," + "('Inglese')," + "('Tedesca')," + "('Francese')," + "('Spagnola')," + "('Russa');";
            stmt.executeUpdate(sql);

            sql = "INSERT INTO Comuni (NomeComune)" +
                    "VALUES ('Bonavigo')," + "('Minerbe')," + "('Legnago')," + "('Roverchiara')," + "('Isola Rizza')," + "('Casaleone');";
            stmt.executeUpdate(sql);

            sql = "INSERT INTO Specializzazioni (NomeSpecializzazione)" +
                    "VALUES ('Bagnino')," + "('Barman')," + "('Istruttore di nuoto')," + "('Viticoltore')," + "('Floricoltore');";
            stmt.executeUpdate(sql);

            System.out.println("Tabelle DB popolate");

            stmt.close();
            c.commit(); // Invia le query scritte fino ad adesso(solo se AutoCommit è disabilitato)
            //--------------------------------------------------
            c.close();

        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
    }
}

