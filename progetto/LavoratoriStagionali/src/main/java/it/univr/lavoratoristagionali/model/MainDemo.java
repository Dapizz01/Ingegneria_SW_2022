package it.univr.lavoratoristagionali.model;

import it.univr.lavoratoristagionali.model.Dao.*;
import it.univr.lavoratoristagionali.types.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

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
                    " User           VARCHAR(20) NOT NULL, " +
                    " Password       VARCHAR(20) NOT NULL,  " +
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
                    "('Marco', 'Dp', '1000', '1234567899', 'ciao123@gmail.com', 'ccc', 'ddd', 'Verona'), " +
                    "('Nico', 'Modenese', '12000', '1231231231', 'ciao12345@gmail.com', 'eee', 'fff', 'Verona');";
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
                    "VALUES ('Bagnino')," + "('Barman')," + "('Istruttore di nuoto')," + "('Viticultore')," + "('Floricultore');";
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


        ComuniDao comuniDao = new ComuniDaoImpl();
        List<Comune> comuniNelDb = comuniDao.getComuni(); // Ritorna la lista dei comuni nel DB da 0=Bonavigo a 5=Casaleone
        LingueDao lingueDao = new LingueDaoImpl();
        List<Lingua> lingueNelDb = lingueDao.getLingue();
        PatentiDao patentiDao = new PatentiDaoImpl();
        List<Patente> patentiNelDb = patentiDao.getPatenti();
        SpecializzazioniDao specializzazioniDao = new SpecializzazioniDaoImpl();
        List<Specializzazione> specializzazioniNelDb = specializzazioniDao.getSpecializzazioni();

        List<Esperienza> esperienze = new ArrayList<Esperienza>();
        esperienze.add(new Esperienza(0, "NST", 50, 1000, 2000, comuniNelDb.get(0), specializzazioniNelDb.get(0)));
        List<Esperienza> esperienze2 = new ArrayList<Esperienza>();
        esperienze2.add(new Esperienza(0, "AIA", 50, 1000, 2000, comuniNelDb.get(3), specializzazioniNelDb.get(0)));
        esperienze2.add(new Esperienza(0, "DBD", 80, 2000, 3000, comuniNelDb.get(3), specializzazioniNelDb.get(1)));

        List<Lingua> lingueParlate = new ArrayList<Lingua>();
        lingueParlate.add(lingueNelDb.get(1));
        List<Lingua> lingueParlate2 = new ArrayList<Lingua>();
        lingueParlate2.add(lingueNelDb.get(0));
        lingueParlate2.add(lingueNelDb.get(1));

        List<Contatto> contatti = new ArrayList<Contatto>();
        contatti.add(new Contatto(0, "Maurizio", "Merluzzo", "1231234567", "merlu@gmail.com"));
        List<Contatto> contatti2 = new ArrayList<Contatto>();
        contatti2.add(new Contatto(0, "Contattino", "Merlu", "1231234567", "merlu@gmail.com"));
        contatti2.add(new Contatto(0, "Contattone", "Merlo", "1231234567", "merlu@gmail.com"));

        List<Patente> patentiPossedute = new ArrayList<Patente>();
        patentiPossedute.add(patentiNelDb.get(0));
        List<Patente> patentiPossedute2 = new ArrayList<Patente>();
        patentiPossedute2.add(patentiNelDb.get(0));
        patentiPossedute2.add(patentiNelDb.get(1));

        List<Disponibilita> disponibilitaLista = new ArrayList<Disponibilita>();
        disponibilitaLista.add(new Disponibilita(8000, 10000,comuniNelDb.get(0)));
        List<Disponibilita> disponibilitaLista2 = new ArrayList<Disponibilita>();
        disponibilitaLista2.add(new Disponibilita(5000, 6000, comuniNelDb.get(0)));
        disponibilitaLista2.add(new Disponibilita(7000, 9000, comuniNelDb.get(1)));


        LavoratoriDao lavoratoriDao = new LavoratoriDaoImpl();
        Lavoratore lavoratore = new Lavoratore(0,
                "Mirko",
                "De Marchi",
                comuniNelDb.get(5),
                comuniNelDb.get(2),
                5000,
                lingueNelDb.get(0),
                "prova123@virgilio.it",
                "1231231231",
                true,
                esperienze,
                lingueParlate,
                contatti,
                patentiPossedute,
                disponibilitaLista);
        System.out.println("Primo lavoratore inserito?: " + lavoratoriDao.addLavoratore(lavoratore));

        Lavoratore lavoratore2 = new Lavoratore(0,
                "Mirko",
                "De Marchi",
                comuniNelDb.get(2),
                comuniNelDb.get(1),
                5000,
                lingueNelDb.get(1),
                "mirko_demarchi@libero.it",
                "1834567823",
                true,
                esperienze2,
                lingueParlate2,
                contatti2,
                patentiPossedute2,
                disponibilitaLista2);
        System.out.println("Secondo lavoratore inserito?: " + lavoratoriDao.addLavoratore(lavoratore2));


        //prova();

            /*
        Scanner keyboard = new Scanner(System.in);
        System.out.print("Inserire NomeLavoratore da cercare nel db: ");
        String nomeLavoratore = keyboard.nextLine();
        System.out.print("Inserire e CognomeLavoratore da cercare nel db: ");
        String cognomeLavoratore = keyboard.nextLine();

        List<Lavoratore> lavoratoriCercati;
        lavoratoriCercati = lavoratoriDao.getLavoratori(nomeLavoratore, cognomeLavoratore);

        if (lavoratoriCercati.isEmpty())
            System.out.println("Nessun lavoratore trovato con quel nome e cognome!");

        for (Lavoratore lavoratore1 : lavoratoriCercati) {
            System.out.println("ID_Lavoratore: " + lavoratore1.getID());
            System.out.println("NomeLavoratore: " + lavoratore1.getNomeLavoratore());
            System.out.println("ComuneNascita: " + lavoratore1.getComuneNascita().getNomeComune());
            System.out.println();

            System.out.println("Esperienze che ha fatto: ");
            for (Esperienza esperienza : lavoratore1.getEsperienze()) {
                System.out.println("ID_Esperienza: " + esperienza.getID());
                System.out.println("NomeAzienda: " + esperienza.getNomeAzienda());
                System.out.println("NomeComune: " + esperienza.getComune().getNomeComune());
                System.out.println("Specializzazione: " + esperienza.getSpecializzazione().getNomeSpecializzazione());
            }
            System.out.println();

            System.out.println("Contatti che possiede: ");
            for (Contatto contatto : lavoratore1.getContatti()) {
                System.out.println("ID_Contatto: " + contatto.getID());
                System.out.println("NomeContatto: " + contatto.getNomeContatto());
            }
            System.out.println();

            System.out.println("Lingue conosciute: ");
            for (Lingua lingua : lavoratore1.getLingue()) {
                System.out.println("Nome Lingua: " + lingua.getNomeLingua());
            }
            System.out.println();

            System.out.println("Patenti possedute: ");
            for (Patente patente : lavoratore1.getPatenti()) {
                System.out.println("ID_Contatto: " + patente.getNomePatente());
            }
            System.out.println();

            System.out.println("Le sue disponibilità: ");
            for (Disponibilita disponibilita : lavoratore1.getDisponibilita()) {
                System.out.println("ID_Comune: " + disponibilita.getComune().getNomeComune());
                System.out.println("InizioPeriodo: " + disponibilita.getInizioPeriodo());
            }
            System.out.println();
        }

        */

        /*
        Scanner keyboard = new Scanner(System.iùn);
        System.out.print("Inserire ID del lavoratore che si vuole eliminare dal db: ");
        int id = keyboard.nextInt();
        System.out.println("Lavoratore eliminato?: " + lavoratoriDao.deleteLavoratore(id));
        */


        //prova();

        /*
        Lavoratore lavoratore3 = new Lavoratore(1,
                "Manuel",
                "De Marchi",
                comuniNelDb.get(3),
                comuniNelDb.get(2),
                5000,
                lingueNelDb.get(1),
                "mirko_demarchi@libero.it",
                "1834567823",
                true,
                esperienze2,
                lingueParlate2,
                contatti2,
                patentiPossedute2,
                disponibilitaLista2);
        System.out.println("Lavoratore modificato?: " + lavoratoriDao.updateLavoratore(lavoratore3));

        // prova();

         */

    }

    public static void prova() { // Per vedere cosa contengono tutte le tabelle del DB
        Connection c = null;
        Statement stmt = null;

        try {
            Class.forName("org.sqlite.JDBC");
            // -------------Connessione database-----------------
            c = DriverManager.getConnection("jdbc:sqlite:LavoratoriStagionali.db");
            System.out.println("Opened database successfully<Prova>");
            //------------------------------------------------

            //------------------Selezione---------------
            c.setAutoCommit(false);

            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT ID_Lavoratore,NomeLavoratore,Nazionalita,ComuneNascita,ComuneAbitazione FROM Lavoratori;");

            while (rs.next()) {
                int idLavoratore = rs.getInt("ID_Lavoratore");
                String nomeLavoratore = rs.getString("NomeLavoratore");
                String nazionalita = rs.getString("Nazionalita");
                String comuneNascita = rs.getString("ComuneNascita");
                String comuneAbitazione = rs.getString("ComuneAbitazione");


                System.out.println("ID_Lavoratore: " + idLavoratore);
                System.out.println("NomeLavoratore: " + nomeLavoratore);
                System.out.println("Nazionalità: " + nazionalita);
                System.out.println("ComuneNascita: " + comuneNascita);
                System.out.println("ComuneAbitazione: " + comuneAbitazione);
            }
            rs.close();


            rs = stmt.executeQuery("SELECT * FROM Esperienze;");
            while (rs.next()) {
                int idLavoratore = rs.getInt("ID_Lavoratore");
                int idEsperienza = rs.getInt("ID_Esperienza");
                String nomeAzienda = rs.getString("NomeAzienda");
                String nomeComune = rs.getString("NomeComune");
                String nomeSpecializzazione = rs.getString("NomeSpecializzazione");

                System.out.println("ID_Lavoratore: " + idLavoratore);
                System.out.println("ID_Esperienza: " + idEsperienza);
                System.out.println("NomeAzienda: " + nomeAzienda);
                System.out.println("Svolta in: " + nomeComune);
                System.out.println("Specializzato in: " + nomeSpecializzazione);
            }
            rs.close();

            rs = stmt.executeQuery("SELECT * FROM LingueParlate;");
            while (rs.next()) {
                int idLavoratore = rs.getInt("ID_Lavoratore");
                String nomeLingua = rs.getString("NomeLingua");

                System.out.println("ID_Lavoratore: " + idLavoratore);
                System.out.println("Lingua Parlata: " + nomeLingua);
            }
            rs.close();

            rs = stmt.executeQuery("SELECT * FROM Contatti;");
            while (rs.next()) {
                int idLavoratore = rs.getInt("ID_Lavoratore");
                int idContatto = rs.getInt("ID_Contatto");
                String nomeContatto = rs.getString("NomeContatto");

                System.out.println("ID_Lavoratore: " + idLavoratore);
                System.out.println("ID_Contatto: " + idContatto);
                System.out.println("nomeContatto: " + nomeContatto);
            }
            rs.close();

            rs = stmt.executeQuery("SELECT * FROM PatentiPossedute;");
            while (rs.next()) {
                int idLavoratore = rs.getInt("ID_Lavoratore");
                String nomePatente = rs.getString("NomePatente");

                System.out.println("ID_Lavoratore: " + idLavoratore);
                System.out.println("Patente Posseduta: " + nomePatente);
            }
            rs.close();

            rs = stmt.executeQuery("SELECT * FROM Disponibilita;");
            while (rs.next()) {
                int idLavoratore = rs.getInt("ID_Lavoratore");
                String nomeComune = rs.getString("NomeComune");

                System.out.println("ID_Lavoratore " + idLavoratore);
                System.out.println("Da disponibilià nel comune: " + nomeComune);
            }
            rs.close();
            //-------------------------------------------------------

            stmt.close();
            c.close();


        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
    }
}

