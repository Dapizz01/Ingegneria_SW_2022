package it.univr.lavoratoristagionali.model;

import it.univr.lavoratoristagionali.filters.*;
import it.univr.lavoratoristagionali.model.Dao.*;
import it.univr.lavoratoristagionali.types.*;

import java.awt.*;
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
        List<Lingua> lingueParlate = new ArrayList<Lingua>();
        lingueParlate.add(lingueNelDb.get(0));
        List<Contatto> contatti = new ArrayList<Contatto>();
        contatti.add(new Contatto(0, "Maurizio", "Merluzzo", "1231234567", "merlu@gmail.com"));
        List<Patente> patentiPossedute = new ArrayList<Patente>();
        patentiPossedute.add(patentiNelDb.get(0));
        List<Disponibilita> disponibilitaLista = new ArrayList<Disponibilita>();
        disponibilitaLista.add(new Disponibilita(1000, 2000,comuniNelDb.get(0)));

        LavoratoriDao lavoratoriDao = new LavoratoriDaoImpl();
        Lavoratore lavoratore = new Lavoratore(0,
                "Matteo",
                "Cognome1",
                comuniNelDb.get(5),
                comuniNelDb.get(0),  // ComuneAbitazione: Bonavigo
                200,
                lingueNelDb.get(0),
                "prova123@virgilio.it",
                "1231231231",
                true,
                esperienze,         // Specializzazione: Bagnino
                lingueParlate,      // Italiana
                contatti,
                patentiPossedute,    // AM
                disponibilitaLista); //1000-2000
        System.out.println("Primo lavoratore inserito?: " + lavoratoriDao.addLavoratore(lavoratore));

        List<Contatto> contatti2 = new ArrayList<Contatto>();
        contatti2.add(new Contatto(0, "Contattino", "Merlu", "1231234567", "merlu@gmail.com"));
        contatti2.add(new Contatto(0, "Contattone", "Merlo", "1231234567", "merlu@gmail.com"));
        List<Lingua> lingueParlate2 = new ArrayList<Lingua>();
        lingueParlate2.add(lingueNelDb.get(0));
        lingueParlate2.add(lingueNelDb.get(1));
        List<Patente> patentiPossedute2 = new ArrayList<Patente>();
        patentiPossedute2.add(patentiNelDb.get(0));
        patentiPossedute2.add(patentiNelDb.get(1));
        List<Disponibilita> disponibilitaLista2 = new ArrayList<Disponibilita>();
        disponibilitaLista2.add(new Disponibilita(1500, 4000, comuniNelDb.get(0)));
        disponibilitaLista2.add(new Disponibilita(7000, 9000, comuniNelDb.get(1)));
        List<Esperienza> esperienze2 = new ArrayList<Esperienza>();
        esperienze2.add(new Esperienza(0, "AIA", 50, 1000, 2000, comuniNelDb.get(3), specializzazioniNelDb.get(0)));
        esperienze2.add(new Esperienza(0, "DBD", 80, 2000, 3000, comuniNelDb.get(3), specializzazioniNelDb.get(1)));

        Lavoratore lavoratore2 = new Lavoratore(0,
                "Mirko",
                "Cognome2",
                comuniNelDb.get(2),
                comuniNelDb.get(1),  //Minerbe
                1000,
                lingueNelDb.get(1),
                "mirko_demarchi@libero.it",
                "1834567823",
                false,
                esperienze2,
                lingueParlate2,
                contatti2,
                patentiPossedute2,
                disponibilitaLista2);
        System.out.println("Secondo lavoratore inserito?: " + lavoratoriDao.addLavoratore(lavoratore2));


        prova();

        // ------------------------------------Ignored
        List<Lingua> lingueVuota = new ArrayList<>();
        List<Comune> comuniVuota = new ArrayList<>();
        List<Patente> patentiVuota = new ArrayList<>();
        List<Specializzazione> specializzazioniVuota = new ArrayList<>();

        LingueFilter lingueFilterVuota = new LingueFilter(lingueVuota, Flag.OR);
        ComuniFilter comuniFilterVuota = new ComuniFilter(comuniVuota, Flag.AND);
        PatentiFilter patentiFilterVuota = new PatentiFilter(patentiVuota, Flag.OR);
        SpecializzazioniFilter specializzazioniFilterVuota = new SpecializzazioniFilter(specializzazioniVuota, Flag.OR);
        AutomunitoFilter automunitoFilterFalse = new AutomunitoFilter(false); // sia automuniti che non
        DisponibilitaFilter disponibilitaFilterVuota = new DisponibilitaFilter(-1,-1, comuniNelDb.get(0));
        DataNascitaFilter dataNascitaFilterVuota = new DataNascitaFilter(-1,Flag.OR);
        // ------------------------------------------


        List<Lingua> lingueDacercare = new ArrayList<>();
        lingueDacercare.add(lingueNelDb.get(0)); // Italiana
        lingueDacercare.add(lingueNelDb.get(1)); // Inglese
        List<Comune> comuniDacercare = new ArrayList<>();
        comuniDacercare.add(comuniNelDb.get(0)); // Bonavigo
        comuniDacercare.add(comuniNelDb.get(1)); // Minerbe
        List<Specializzazione> specializzazioniDacercare = new ArrayList<>();
        specializzazioniDacercare.add(specializzazioniNelDb.get(0));
        specializzazioniDacercare.add(specializzazioniNelDb.get(1));

        LingueFilter lingueFilterAND = new LingueFilter(lingueDacercare, Flag.AND);
        LingueFilter lingueFilterOR = new LingueFilter(lingueDacercare, Flag.OR);
        ComuniFilter comuniFilterAND = new ComuniFilter(comuniDacercare, Flag.AND);
        ComuniFilter comuniFilterOR = new ComuniFilter(comuniDacercare, Flag.OR);

        SpecializzazioniFilter specializzazioniFilterOR = new SpecializzazioniFilter(specializzazioniDacercare, Flag.OR);
        DisponibilitaFilter disponibilitaFilterBonavigo = new DisponibilitaFilter(1600,2000,comuniNelDb.get(0));
        AutomunitoFilter automunitoFilterTrue = new AutomunitoFilter(true);



        List<Lavoratore> lavoratoriCercati;
        /*
        lavoratoriCercati = lavoratoriDao.searchLavoratori(lingueFilterAND,comuniFilterOR,patentiFilterVuota,specializzazioniFilterVuota,automunitoFilterFalse, disponibilitaFilterVuota, dataNascitaFilterVuota, Flag.AND);
        System.out.println("Prima ricerca: ");
        stampaRicerca(lavoratoriCercati);

        lavoratoriCercati = lavoratoriDao.searchLavoratori(lingueFilterOR,comuniFilterOR,patentiFilterVuota,specializzazioniFilterVuota,automunitoFilterFalse, disponibilitaFilterVuota, dataNascitaFilterVuota, Flag.AND);
        System.out.println("Seconda ricerca: ");
        stampaRicerca(lavoratoriCercati);

        lavoratoriCercati = lavoratoriDao.searchLavoratori(lingueFilterOR,comuniFilterAND,patentiFilterVuota,specializzazioniFilterVuota,automunitoFilterFalse, disponibilitaFilterVuota, dataNascitaFilterVuota, Flag.AND);
        System.out.println("Terza ricerca: ");
        stampaRicerca(lavoratoriCercati);

        lavoratoriCercati = lavoratoriDao.searchLavoratori(lingueFilterOR,comuniFilterOR,patentiFilterVuota,specializzazioniFilterVuota,automunitoFilterFalse, disponibilitaFilterVuota, dataNascitaFilterVuota,Flag.OR);
        System.out.println("Quarta ricerca: ");
        stampaRicerca(lavoratoriCercati);

        lavoratoriCercati = lavoratoriDao.searchLavoratori(lingueFilterAND,comuniFilterOR,patentiFilterVuota,specializzazioniFilterVuota,automunitoFilterFalse, disponibilitaFilterVuota, dataNascitaFilterVuota,Flag.OR);
        System.out.println("Quinta ricerca: ");
        stampaRicerca(lavoratoriCercati);

        lavoratoriCercati = lavoratoriDao.searchLavoratori(lingueFilterOR,comuniFilterAND,patentiFilterVuota,specializzazioniFilterVuota,automunitoFilterFalse, disponibilitaFilterVuota, dataNascitaFilterVuota,Flag.OR);
        System.out.println("Sesta ricerca: ");
        stampaRicerca(lavoratoriCercati);

        lavoratoriCercati = lavoratoriDao.searchLavoratori(lingueFilterAND,comuniFilterAND,patentiFilterVuota,specializzazioniFilterVuota,automunitoFilterFalse, disponibilitaFilterVuota, dataNascitaFilterVuota, Flag.OR);
        System.out.println("Settima ricerca: ");
        stampaRicerca(lavoratoriCercati);

        lavoratoriCercati = lavoratoriDao.searchLavoratori(lingueFilterAND,comuniFilterAND,patentiFilterVuota,specializzazioniFilterVuota,automunitoFilterFalse, disponibilitaFilterVuota, dataNascitaFilterVuota, Flag.AND);
        System.out.println("Ottava ricerca: ");
        stampaRicerca(lavoratoriCercati);

        lavoratoriCercati = lavoratoriDao.searchLavoratori(lingueFilterVuota,comuniFilterVuota,patentiFilterVuota,specializzazioniFilterOR,automunitoFilterFalse, disponibilitaFilterVuota, dataNascitaFilterVuota, Flag.OR);
        System.out.println("Ricerca per solo chi ha almeno bagnino: ");
        stampaRicerca(lavoratoriCercati);
         */

        lavoratoriCercati = lavoratoriDao.searchLavoratori(lingueFilterVuota,comuniFilterVuota,patentiFilterVuota,specializzazioniFilterVuota,automunitoFilterFalse, disponibilitaFilterBonavigo, dataNascitaFilterVuota, Flag.OR);
        System.out.println("Ricerca da 1600 a 2000 per bonavigo ");
        stampaRicerca(lavoratoriCercati);
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
            ResultSet rs = stmt.executeQuery("SELECT * FROM Lavoratori;");

            while (rs.next()) {
                int idLavoratore = rs.getInt("ID_Lavoratore");
                String nomeLavoratore = rs.getString("NomeLavoratore");
                String nazionalita = rs.getString("Nazionalita");
                String comuneNascita = rs.getString("ComuneNascita");
                String comuneAbitazione = rs.getString("ComuneAbitazione");
                boolean automunito = rs.getBoolean("Automunito");


                System.out.println("ID_Lavoratore: " + idLavoratore);
                System.out.println("NomeLavoratore: " + nomeLavoratore);
                System.out.println("Nazionalità: " + nazionalita);
                System.out.println("ComuneNascita: " + comuneNascita);
                System.out.println("ComuneAbitazione: " + comuneAbitazione);
                System.out.println("Automunito: " + automunito);
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
                int inizioPeriodo = rs.getInt("InizioPeriodo");
                int finePeriodo = rs.getInt("FinePeriodo");

                System.out.println("ID_Lavoratore " + idLavoratore);
                System.out.println("Da disponibilià nel comune: " + nomeComune);
                System.out.println("Da: " + inizioPeriodo);
                System.out.println("A: " + finePeriodo);

            }
            rs.close();

            System.out.println("Gli automuniti sono: ");
            stmt = c.createStatement();
            rs = stmt.executeQuery("SELECT ID_Lavoratore FROM Lavoratori WHERE Automunito = TRUE;");
            while (rs.next()) {
                int idLavoratore = rs.getInt("ID_Lavoratore");

                System.out.println("ID_Lavoratore: " + idLavoratore);
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

    public static void stampaRicerca(List<Lavoratore> lavoratoriCercati) {
        if (lavoratoriCercati.isEmpty())
            System.out.println("Nessun lavoratore trovato!");

        for (Lavoratore lavoratore1 : lavoratoriCercati) {
            System.out.println("ID_Lavoratore: " + lavoratore1.getID());
            System.out.println("NomeLavoratore: " + lavoratore1.getNomeLavoratore());
            System.out.println("ComuneNascita: " + lavoratore1.getComuneNascita().getNomeComune());
            System.out.println();

            /*
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

             */
        }
    }
}

