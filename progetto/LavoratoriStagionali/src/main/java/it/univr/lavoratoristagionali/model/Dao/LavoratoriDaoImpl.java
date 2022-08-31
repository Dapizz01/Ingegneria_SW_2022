package it.univr.lavoratoristagionali.model.Dao;

import it.univr.lavoratoristagionali.filters.*;
import it.univr.lavoratoristagionali.types.*;

import java.sql.*;
import java.util.*;

public class LavoratoriDaoImpl implements LavoratoriDao {
    @Override
    public boolean addLavoratore(Lavoratore nuovoLavoratore) {
        Connection c = null;
        Statement stmt = null;

        try {
            Class.forName("org.sqlite.JDBC");
            // -------------Connessione database-----------------
            c = DriverManager.getConnection("jdbc:sqlite:LavoratoriStagionali.db");
            System.out.println("Opened database successfully<LavoratoriDaoImpl/addLavoratore>");
            //---------------------------------------------------

            //------------------Inserimento nuovo lavoratore ---------------
            String sql = "INSERT INTO Lavoratori (NomeLavoratore,CognomeLavoratore,ComuneNascita,ComuneAbitazione,DataNascita,Nazionalita,Email,N_telefono,Automunito) VALUES (?,?,?,?,?,?,?,?,?)";
            PreparedStatement pstmt = c.prepareStatement(sql);
            // il parametro 1 dovrebbe essere stato l'ID che non viene toccato visto che è autoincrementale
            pstmt.setString(1, nuovoLavoratore.getNomeLavoratore());
            pstmt.setString(2, nuovoLavoratore.getCognomeLavoratore());
            pstmt.setString(3, nuovoLavoratore.getComuneNascita().getNomeComune());
            pstmt.setString(4, nuovoLavoratore.getComuneAbitazione().getNomeComune());
            pstmt.setInt(5, nuovoLavoratore.getDataNascita());
            pstmt.setString(6, nuovoLavoratore.getNazionalita().getNomeLingua());
            pstmt.setString(7, nuovoLavoratore.getEmail());
            pstmt.setString(8, nuovoLavoratore.getTelefono());
            pstmt.setBoolean(9, nuovoLavoratore.isAutomunito());
            pstmt.executeUpdate();

            //---------------- Inserimento Esperienze -------------------
            /*Prendo l'ID del lavoratore appena inserito nel DB cercando l'ultimo ID inserito visto che è autoincrmentale
             * Questo perchè quando creo l'oggetto lavoratore gli assegno come ID 0 che sta per ignorato perchè non appena
             * viene inserito nel DB prenderà l'ID con il numero più alto visto che è autoincrementale*/
            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT ID_Lavoratore FROM Lavoratori ORDER BY ID_Lavoratore DESC LIMIT 1;"); // Cerca l'ultimo ID inserito nella tabella lavoratori(ovvero quello che sto inserendo)

            int idLavoratore = rs.getInt("ID_Lavoratore");

            rs.close();
            stmt.close();

            for (Esperienza esperienza : nuovoLavoratore.getEsperienze()) { //Cicla in base alle esperienze che ha quel nuovo lavoratore da inserire
                sql = "INSERT INTO Esperienze (NomeAzienda,RetribuzioneGiornaliera,InizioPeriodo,FinePeriodo,NomeComune,NomeSpecializzazione,ID_Lavoratore) VALUES (?,?,?,?,?,?,?)";
                pstmt = c.prepareStatement(sql);
                // il parametro 1 dovrebbe essere stato l'ID_Esperienza che non viene toccato visto che è autoincrementale
                pstmt.setString(1, esperienza.getNomeAzienda());
                pstmt.setInt(2, esperienza.getRetribuzioneLordaGiornaliera());
                pstmt.setInt(3, esperienza.getInizioPeriodo());
                pstmt.setInt(4, esperienza.getFinePeriodo());
                pstmt.setString(5, esperienza.getComune().getNomeComune()); // Nome del comune in cui è stata svolta
                pstmt.setString(6,esperienza.getSpecializzazione().getNomeSpecializzazione()); // Nome della specializzazione che definisci il tipo d'esperienza
                pstmt.setInt(7,idLavoratore); // ID del lavoratore che ha fatto quella esperienza(ovvero quello che si sta inserendo)
                pstmt.executeUpdate();
                System.out.println("Esperienza Aggiunta");
            }

            //-------------- Inserimento LingueParlate -------------------
            for (Lingua linguaParlata : nuovoLavoratore.getLingue()) { // Cicla in base al numero di lingue conosciute dal nuovo lavoratore da inserire
                sql = "INSERT INTO LingueParlate (ID_Lavoratore,NomeLingua) VALUES (?,?)";
                pstmt = c.prepareStatement(sql);

                pstmt.setInt(1, idLavoratore); // ID del lavoratore da aggiungere(ultimo autoincrementato nella tabella lavoratori)
                pstmt.setString(2, linguaParlata.getNomeLingua()); // Nome della lingua che conosce
                pstmt.executeUpdate();
                System.out.println("Lingua Aggiunta");
            }

            //-------------- Inserimento Contatti -----------------
            for (Contatto nuovoContatto : nuovoLavoratore.getContatti()) {
                sql = "INSERT INTO Contatti (NomeContatto,CognomeContatto,N_telefono,Email,ID_Lavoratore) VALUES (?,?,?,?,?)";
                pstmt = c.prepareStatement(sql);
                // il parametro 1 dovrebbe essere stato l'ID_Contatto che non viene toccato visto che è autoincrementale
                pstmt.setString(1, nuovoContatto.getNomeContatto());
                pstmt.setString(2, nuovoContatto.getCognomeContatto());
                pstmt.setString(3, nuovoContatto.getTelefono());
                pstmt.setString(4, nuovoContatto.getEmail());
                pstmt.setInt(5, idLavoratore);
                pstmt.executeUpdate();
                System.out.println("Contatto aggiunto");
            }

            //------------ Inserimento PatentiPossedute-----------------------
            for (Patente patentePosseduta : nuovoLavoratore.getPatenti()) {
                sql = "INSERT INTO PatentiPossedute (ID_Lavoratore,NomePatente) VALUES (?,?)";
                pstmt = c.prepareStatement(sql);

                pstmt.setInt(1, idLavoratore); // ID del lavoratore da aggiungere(ultimo autoincrementato nella tabella lavoratori)
                pstmt.setString(2, patentePosseduta.getNomePatente());
                pstmt.executeUpdate();
                System.out.println("Patente Aggiunta");
            }

            //------------ Inserimento Disponibilità -------------------
            for (Disponibilita nuovaDisponibilita : nuovoLavoratore.getDisponibilita()) {
                sql = "INSERT INTO Disponibilita (ID_Lavoratore,NomeComune,InizioPeriodo,FinePeriodo) VALUES (?,?,?,?)";
                pstmt = c.prepareStatement(sql);

                pstmt.setInt(1, idLavoratore);
                pstmt.setString(2, nuovaDisponibilita.getComune().getNomeComune());
                pstmt.setInt(3, nuovaDisponibilita.getInizioPeriodo());
                pstmt.setInt(3, nuovaDisponibilita.getFinePeriodo());
                pstmt.executeUpdate();
                System.out.println("Disponibilità Aggiunta");
            }

            //--------------------FINE INSERIMENTO NUOVO LAVORATORE-------------------

            pstmt.close();
            c.close();

            System.out.println("Lavoratore inserito correttamente nel DB");
            return true; // Lavoratore aggiunto al DB correttamente

        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }

        return false;
    }

    @Override
    public List<Lavoratore> getLavoratori(String nome, String cognome) {
        List<Lavoratore> lavoratori = new ArrayList<>();

        Connection c = null;
        Statement stmt = null;

        try {
            Class.forName("org.sqlite.JDBC");
            // -------------Connessione database-----------------
            c = DriverManager.getConnection("jdbc:sqlite:LavoratoriStagionali.db");
            System.out.println("Opened database successfully<LavoratoriDaoImpl/getLavoratori>");
            //------------------------------------------------

            //---------------- Ricavo l'ID dei lavoratori cercati(per nome e cognome) -----------
            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM Lavoratori WHERE NomeLavoratore = '" + nome + "' AND CognomeLavoratore = '" + cognome + "';");

            while (rs.next()) { // Cicla per ogni lavoratore trovato con quel nome e cognome
                int idLavoratore = rs.getInt("ID_Lavoratore"); // serve per costruire le liste di Esperiene/Contatti/LingueParlate/PatentiPossedute/Disponibilità dei lavoratori con quel nome e cognome

                //---------------- Costruisco le 5 liste di Lavoratore -----------------
                List<Esperienza> esperienze = new ArrayList<>();
                List<Contatto> contatti = new ArrayList<>();
                List<Lingua> lingue = new ArrayList<>();
                List<Patente> patenti = new ArrayList<>();
                List<Disponibilita> disponibilitaLista = new ArrayList<>();

                stmt = c.createStatement();
                ResultSet rs2 = stmt.executeQuery("SELECT * FROM Esperienze WHERE ID_Lavoratore = '" + idLavoratore + "';"); // in base all'ID preso prima

                while (rs2.next()) { // cicla in base al numero di esperienze del lavoratore con l'ID ricavato all'inizio
                    // l'ID del lavoratore non mi serve nell'esperienza
                    int idEsperienza = rs2.getInt("ID_Esperienza");
                    String nomeAzienda = rs2.getString("NomeAzienda");
                    int retribuzione = rs2.getInt("RetribuzioneGiornaliera");
                    int inizioPeriodo = rs2.getInt("InizioPeriodo");
                    int finePeriodo = rs2.getInt("FinePeriodo");
                    String nomeComune = rs2.getString("NomeComune");
                    String nomeSpecializzazione = rs2.getString("NomeSpecializzazione");

                    Comune comune = new Comune(nomeComune);
                    Specializzazione specializzazione = new Specializzazione(nomeSpecializzazione);

                    Esperienza esperienza = new Esperienza(idEsperienza,nomeAzienda,retribuzione,inizioPeriodo,finePeriodo,comune,specializzazione);
                    esperienze.add(esperienza);
                }
                rs2.close();

                stmt = c.createStatement();
                rs2 = stmt.executeQuery("SELECT * FROM Contatti WHERE ID_Lavoratore = '" + idLavoratore + "';");

                while (rs2.next()) { // cicla in base al numero di esperienze del lavoratore con l'ID ricavato all'inizio
                    int idContatto = rs2.getInt("ID_Contatto");
                    String nomeContatto = rs2.getString("NomeContatto");
                    String cognomeContatto = rs2.getString("CognomeContatto");
                    String nTelefono = rs2.getString("N_telefono");
                    String email = rs2.getString("Email");

                    Contatto contatto = new Contatto(idContatto,nomeContatto,cognomeContatto,nTelefono,email);
                    contatti.add(contatto);
                }
                rs2.close();

                stmt = c.createStatement();
                rs2 = stmt.executeQuery("SELECT * FROM LingueParlate WHERE ID_Lavoratore = '" + idLavoratore + "';");

                while (rs2.next()) { // cicla in base al numero di lingue parlate dal lavoratore con l'ID ricavato all'inizio
                    String nomeLingua = rs2.getString("NomeLingua");

                    Lingua lingua = new Lingua(nomeLingua);
                    lingue.add(lingua);
                }
                rs2.close();

                stmt = c.createStatement();
                rs2 = stmt.executeQuery("SELECT * FROM PatentiPossedute WHERE ID_Lavoratore = '" + idLavoratore + "';");

                while (rs2.next()) { // cicla in base al numero di patenti possedute dal lavoratore con l'ID ricavato all'inizio
                    String nomePatente = rs2.getString("NomePatente");

                    Patente patente = new Patente(nomePatente);
                    patenti.add(patente);
                }
                rs2.close();

                stmt = c.createStatement();
                rs2 = stmt.executeQuery("SELECT * FROM Disponibilita WHERE ID_Lavoratore = '" + idLavoratore + "';");

                while (rs2.next()) { // cicla in base al numero di patenti possedute dal lavoratore con l'ID ricavato all'inizio
                    String nomeComune = rs2.getString("NomeComune");
                    int inizioPeriodo = rs2.getInt("InizioPeriodo");
                    int finePeriodo = rs2.getInt("FinePeriodo");

                    Comune comune = new Comune(nomeComune);

                    Disponibilita disponibilitaSingola = new Disponibilita(inizioPeriodo, finePeriodo, comune);
                    disponibilitaLista.add(disponibilitaSingola);
                }
                rs2.close();

                // Ora che ho tutto posso creare la lista di lavoratori da ritornare con quel nome e cognome cercati
                // Recupero le cose che mi mancavano del lavoratore
                String nomeLavoratore = rs.getString("NomeLavoratore");
                String cognomeLavoratore = rs.getString("CognomeLavoratore");
                String comune1 = rs.getString("ComuneNascita");
                String comune2 = rs.getString("ComuneAbitazione");
                int dataNascita = rs.getInt("DataNascita");
                String nazionalita1 = rs.getString("Nazionalita");
                String email = rs.getString("Email");
                String nTelefono = rs.getString("N_telefono");
                boolean automunito = rs.getBoolean("Automunito");

                Comune comuneNascita = new Comune(comune1);
                Comune comuneAbitazione = new Comune(comune2);
                Lingua nazionalita = new Lingua(nazionalita1);

                Lavoratore lavoratore = new Lavoratore(idLavoratore,nomeLavoratore,cognomeLavoratore,comuneNascita,comuneAbitazione,dataNascita,nazionalita,email,nTelefono,automunito,esperienze,lingue,contatti,patenti,disponibilitaLista);
                lavoratori.add(lavoratore);
            }
            //-------------------------------------------------------------

            rs.close();
            stmt.close();
            c.close();

            return lavoratori; // anche vuota va bene

        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }

        return lavoratori;
    }

    @Override
    public boolean deleteLavoratore(int idDaEliminare) {
        Connection c = null;
        Statement stmt = null;

        try {
            Class.forName("org.sqlite.JDBC");
            // -------------Connessione database-----------------
            c = DriverManager.getConnection("jdbc:sqlite:LavoratoriStagionali.db");
            System.out.println("Opened database successfully<LavoratoriDaoImpl/deleteLavoratore>");
            //------------------------------------------------

            c.setAutoCommit(false);

            //--------------------- Eliminazione -------------------------
            stmt = c.createStatement();
            String sql = "DELETE FROM Lavoratori WHERE ID_Lavoratore = '" + idDaEliminare + "';";
            stmt.executeUpdate(sql);
            c.commit();

            stmt = c.createStatement();
            sql = "DELETE FROM Esperienze WHERE ID_Lavoratore = '" + idDaEliminare + "';";
            stmt.executeUpdate(sql);
            c.commit();

            stmt = c.createStatement();
            sql = "DELETE FROM Contatti WHERE ID_Lavoratore = '" + idDaEliminare + "';";
            stmt.executeUpdate(sql);
            c.commit();

            stmt = c.createStatement();
            sql = "DELETE FROM Disponibilita WHERE ID_Lavoratore = '" + idDaEliminare + "';";
            stmt.executeUpdate(sql);
            c.commit();

            stmt = c.createStatement();
            sql = "DELETE FROM LingueParlate WHERE ID_Lavoratore = '" + idDaEliminare + "';";
            stmt.executeUpdate(sql);
            c.commit();

            stmt = c.createStatement();
            sql = "DELETE FROM PatentiPossedute WHERE ID_Lavoratore = '" + idDaEliminare + "';";
            stmt.executeUpdate(sql);
            c.commit();
            //----------------------------------------------------

            stmt.close();
            c.close();

            System.out.println("Lavoratore eliminato correttamente");
            return true; // Lavoratore eliminato correttamente dal db

        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }

        return false;
    }

    @Override
    public boolean updateLavoratore(Lavoratore lavoratoreDaModificare) {
        System.out.println("<LavoratoriDaoImpl/updateLavoratore>");

        if(deleteLavoratore(lavoratoreDaModificare.getID()))
            if(addLavoratore(lavoratoreDaModificare))
                return true; // Lavoratore modificato correttamente

        return false;
    }

    @Override
    public Lavoratore getLavoratore(int idDaCercare) { // Se l'id non esiste ritornerà un Lavoratore a null
        Lavoratore lavoratore = null;

        Connection c = null;
        Statement stmt = null;

        try {
            Class.forName("org.sqlite.JDBC");
            // -------------Connessione database-----------------
            c = DriverManager.getConnection("jdbc:sqlite:LavoratoriStagionali.db");
            System.out.println("Opened database successfully<LavoratoriDaoImpl/getLavoratore>");
            //------------------------------------------------

            //---------------- Costruisco le 5 liste di Lavoratore -----------------
            List<Esperienza> esperienze = new ArrayList<>();
            List<Contatto> contatti = new ArrayList<>();
            List<Lingua> lingue = new ArrayList<>();
            List<Patente> patenti = new ArrayList<>();
            List<Disponibilita> disponibilitaLista = new ArrayList<>();

            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM Esperienze WHERE ID_Lavoratore = '" + idDaCercare + "';");

            while (rs.next()) {
                // l'ID del lavoratore non mi serve nell'esperienza
                int idEsperienza = rs.getInt("ID_Esperienza");
                String nomeAzienda = rs.getString("NomeAzienda");
                int retribuzione = rs.getInt("RetribuzioneGiornaliera");
                int inizioPeriodo = rs.getInt("InizioPeriodo");
                int finePeriodo = rs.getInt("FinePeriodo");
                String nomeComune = rs.getString("NomeComune");
                String nomeSpecializzazione = rs.getString("NomeSpecializzazione");

                Comune comune = new Comune(nomeComune);
                Specializzazione specializzazione = new Specializzazione(nomeSpecializzazione);

                Esperienza esperienza = new Esperienza(idEsperienza,nomeAzienda,retribuzione,inizioPeriodo,finePeriodo,comune,specializzazione);
                esperienze.add(esperienza);
            }
            rs.close();

            stmt = c.createStatement();
            rs = stmt.executeQuery("SELECT * FROM Contatti WHERE ID_Lavoratore = '" + idDaCercare + "';");

            while (rs.next()) {
                int idContatto = rs.getInt("ID_Contatto");
                String nomeContatto = rs.getString("NomeContatto");
                String cognomeContatto = rs.getString("CognomeContatto");
                String nTelefono = rs.getString("N_telefono");
                String email = rs.getString("Email");

                Contatto contatto = new Contatto(idContatto,nomeContatto,cognomeContatto,nTelefono,email);
                contatti.add(contatto);
            }
            rs.close();

            stmt = c.createStatement();
            rs = stmt.executeQuery("SELECT * FROM LingueParlate WHERE ID_Lavoratore = '" + idDaCercare + "';");

            while (rs.next()) { // cicla in base al numero di lingue parlate dal lavoratore con l'ID ricavato all'inizio
                String nomeLingua = rs.getString("NomeLingua");

                Lingua lingua = new Lingua(nomeLingua);
                lingue.add(lingua);
            }
            rs.close();

            stmt = c.createStatement();
            rs = stmt.executeQuery("SELECT * FROM PatentiPossedute WHERE ID_Lavoratore = '" + idDaCercare + "';");

            while (rs.next()) { // cicla in base al numero di patenti possedute dal lavoratore con l'ID ricavato all'inizio
                String nomePatente = rs.getString("NomePatente");

                Patente patente = new Patente(nomePatente);
                patenti.add(patente);
            }
            rs.close();

            stmt = c.createStatement();
            rs = stmt.executeQuery("SELECT * FROM Disponibilita WHERE ID_Lavoratore = '" + idDaCercare + "';");

            while (rs.next()) {
                String nomeComune = rs.getString("NomeComune");
                int inizioPeriodo = rs.getInt("InizioPeriodo");
                int finePeriodo = rs.getInt("FinePeriodo");

                Comune comune = new Comune(nomeComune);

                Disponibilita disponibilitaSingola = new Disponibilita(inizioPeriodo, finePeriodo, comune);
                disponibilitaLista.add(disponibilitaSingola);
            }
            rs.close();

            stmt = c.createStatement();
            rs = stmt.executeQuery("SELECT * FROM Lavoratori WHERE ID_Lavoratore = '" + idDaCercare + "';");
            while (rs.next()) {
                String nomeLavoratore = rs.getString("NomeLavoratore");
                String cognomeLavoratore = rs.getString("CognomeLavoratore");
                String comune1 = rs.getString("ComuneNascita");
                String comune2 = rs.getString("ComuneAbitazione");
                int dataNascita = rs.getInt("DataNascita");
                String nazionalita1 = rs.getString("Nazionalita");
                String email = rs.getString("Email");
                String nTelefono = rs.getString("N_telefono");
                boolean automunito = rs.getBoolean("Automunito");

                Comune comuneNascita = new Comune(comune1);
                Comune comuneAbitazione = new Comune(comune2);
                Lingua nazionalita = new Lingua(nazionalita1);

                lavoratore = new Lavoratore(idDaCercare, nomeLavoratore, cognomeLavoratore, comuneNascita, comuneAbitazione, dataNascita, nazionalita, email, nTelefono, automunito, esperienze, lingue, contatti, patenti, disponibilitaLista);
            }
            rs.close();

            stmt.close();
            c.close();

            return lavoratore;

        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }

        return lavoratore;
    }


    @Override
    public List<Lavoratore> searchLavoratori(LingueFilter lingueFilter, ComuniFilter comuniFilter, PatentiFilter patentiFilter, SpecializzazioniFilter specializzazioniFilter, AutomunitoFilter automunitoFilter, DisponibilitaFilter disponibilitaFilter, DataNascitaFilter dataNascitaFilter, Flag flag) {
        List<Lavoratore> lavoratoriCercati = new ArrayList<>();
        Set<Integer> idLavoratori = new TreeSet<>(); // tiene traccia degli id dei lavoratori idonei alla ricerca
        Set<Integer> tracciaIdLingue = new TreeSet<>();
        Set<Integer> tracciaIdComuni = new TreeSet<>();
        Set<Integer> tracciaIdPatenti = new TreeSet<>();
        Set<Integer> tracciaIdSpecializzazioni = new TreeSet<>();
        Set<Integer> tracciaIdAutomunito = new TreeSet<>();
        Set<Integer> tracciaIdDisponibilita = new TreeSet<>();
        Set<Integer> tracciaIdDataNascita = new TreeSet<>();
        List<Integer> and1 = new ArrayList<>();


        Connection c = null;
        Statement stmt = null;

        try {
            Class.forName("org.sqlite.JDBC");
            // -------------Connessione database-----------------
            c = DriverManager.getConnection("jdbc:sqlite:LavoratoriStagionali.db");
            System.out.println("Opened database successfully<LavoratoriDaoImpl/searchLavoratori>");
            //---------------------------------------------------

            //------------------ Ricerca -----------------

            // Con lista lingue vuota: ignored
            // Con Flag.AND ritorno tutti i lavoratori
            // Con Flag.OR ritorno nessuno
            // Con lista lingue piena:
            // Con lingueFilter.getFlag() == Flag.AND ritorno tutti i lavoratori che parlano tutte le lingue della lista
            // Con lingueFilter.getFlag() == Flag.OR ritorno tutti quelli che parlano almeno una delle lingue della lista
            // *idem per le altre liste*

            // Con automunito a FALSE: ignored
            // Con Flag.AND ritorno tutti i lavoratori
            // Con Flag.OR ritorno nessuno
            // Con autounito a TRUE:
            // Ritorno tutti gli automuniti
            // *idem per disponibilita e data di nascita*
            //--------------------------------------------

            //---------------------------- Lingue --------------------------------
            if(lingueFilter.getLingue().isEmpty()) {
                if(flag == Flag.AND) { // Il campo è ignorato nella ricerca(vanno bene tutti i lavoratori per l'AND)
                    stmt = c.createStatement();
                    ResultSet rs = stmt.executeQuery("SELECT ID_Lavoratore FROM Lavoratori");
                    while (rs.next()) {
                        int idLavoratore = rs.getInt("ID_Lavoratore");

                        tracciaIdLingue.add(idLavoratore);
                    }
                    rs.close();
                }
                else { // Il campo è ignorato nella ricerca(non voglio nessuno per l'OR)
                    // tracciaIdLingue rimane vuota
                }
            }
            else {
                if(lingueFilter.getFlag() == Flag.AND) { //Prendo solo gli id dei lavoratori che parlano tutte le lingue nella lista
                    for(Lingua lingua : lingueFilter.getLingue()) {
                        stmt = c.createStatement();
                        ResultSet rs = stmt.executeQuery("SELECT ID_Lavoratore FROM LingueParlate WHERE NomeLingua ='" + lingua.getNomeLingua() + "';");
                        while (rs.next()) {
                            int idLavoratore = rs.getInt("ID_Lavoratore");

                            and1.add(idLavoratore); // quel lavoratore parla quella lingua
                        }
                        rs.close();
                    }

                    // Devo tenere gli id di and1 che compaiono esattamente n volte pari alla lunghezza di lingue, ovvero parlano tutte le lingue nella lista
                    Map<Integer, Integer> hm = new HashMap<Integer, Integer>();

                    for (Integer i : and1) {
                        Integer j = hm.get(i);
                        hm.put(i, (j == null) ? 1 : j + 1);
                    }
                    for (Map.Entry<Integer, Integer> val : hm.entrySet()) {
                        if(val.getValue() == lingueFilter.getLingue().size())
                            tracciaIdLingue.add(val.getValue());
                    }

                    hm.clear();
                    and1.clear();
                }
                else { // lingueFilter.getFlag() == Flag.OR // Prendo gli id dei lavoratori che parlano almeno una delle lingue nella lista
                    for(Lingua lingua : lingueFilter.getLingue()) {
                        stmt = c.createStatement();
                        ResultSet rs = stmt.executeQuery("SELECT ID_Lavoratore FROM LingueParlate WHERE NomeLingua ='" + lingua.getNomeLingua() + "';");
                        while (rs.next()) {
                            int idLavoratore = rs.getInt("ID_Lavoratore");

                            tracciaIdLingue.add(idLavoratore);  // set senza ripetizioni
                        }
                        rs.close();
                    }
                }
            }
            // -----------------------------// Lingue // --------------------------------

            //-------------------------------- Comuni ------------------------------------
            if(comuniFilter.getComuni().isEmpty()) { // Il campo è ignorato nella ricerca(vanno bene tutti i lavoratori)
                stmt = c.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT ID_Lavoratore FROM Lavoratori");
                while (rs.next()) {
                    int idLavoratore = rs.getInt("ID_Lavoratore");

                    tracciaIdComuni.add(idLavoratore);
                }
                rs.close();
            }
            else {
                if(comuniFilter.getFlag() == Flag.OR) {
                    for(Comune comune : comuniFilter.getComuni()) {
                        stmt = c.createStatement();
                        ResultSet rs = stmt.executeQuery("SELECT ID_Lavoratore FROM Lavoratori WHERE ComuneAbitazione ='" + comune.getNomeComune() + "';");
                        while (rs.next()) {
                            int idLavoratore = rs.getInt("ID_Lavoratore");

                            tracciaIdComuni.add(idLavoratore);  // set senza ripetizioni
                        }
                        rs.close();
                    }
                }
                else { // comuniFilter.getFlag() != Flag.OR
                    // tracciaIdComuni rimane vuota
                }
            }
            //---------------------------------// Comuni //-------------------------------

            //-------------------------------- Patenti -----------------------------------
            if(patentiFilter.getPatenti().isEmpty()) { // Il campo è ignorato nella ricerca(vanno bene tutti i lavoratori per l'AND)
                if(flag == Flag.AND) {
                    stmt = c.createStatement();
                    ResultSet rs = stmt.executeQuery("SELECT ID_Lavoratore FROM Lavoratori");
                    while (rs.next()) {
                        int idLavoratore = rs.getInt("ID_Lavoratore");

                        tracciaIdPatenti.add(idLavoratore);
                    }
                    rs.close();
                }
                else { // Il campo è ignorato nella ricerca(non voglio nessun lavoratore per l'OR)
                    // tracciaIdPatenti rimane vuota
                }

            }
            else {
                if(patentiFilter.getFlag() == Flag.AND) {
                    for(Patente patente : patentiFilter.getPatenti()) {
                        stmt = c.createStatement();
                        ResultSet rs = stmt.executeQuery("SELECT ID_Lavoratore FROM PatentiPossedute WHERE NomePatente ='" + patente.getNomePatente() + "';");
                        while (rs.next()) {
                            int idLavoratore = rs.getInt("ID_Lavoratore");

                            and1.add(idLavoratore);
                        }
                        rs.close();
                    }

                    Map<Integer, Integer> hm = new HashMap<Integer, Integer>();

                    for (Integer i : and1) {
                        Integer j = hm.get(i);
                        hm.put(i, (j == null) ? 1 : j + 1);
                    }
                    for (Map.Entry<Integer, Integer> val : hm.entrySet()) {
                        if(val.getValue() == patentiFilter.getPatenti().size())
                            tracciaIdPatenti.add(val.getValue());
                    }

                    hm.clear();
                    and1.clear();

                }
                else {
                    for(Patente patente : patentiFilter.getPatenti()) {
                        stmt = c.createStatement();
                        ResultSet rs = stmt.executeQuery("SELECT ID_Lavoratore FROM PatentiPossedute WHERE NomePatente ='" + patente.getNomePatente() + "';");
                        while (rs.next()) {
                            int idLavoratore = rs.getInt("ID_Lavoratore");

                            tracciaIdPatenti.add(idLavoratore);  // set senza ripetizioni
                        }
                        rs.close();
                    }
                }
            }
            //-------------------------------// Patenti //---------------------------------

            //-------------------------- Specializzazioni ---------------------------------
            if(specializzazioniFilter.getSpecializzazioni().isEmpty()) { // Il campo è ignorato nella ricerca(vanno bene tutti i lavoratori)
                if(flag == Flag.AND) {
                    stmt = c.createStatement();
                    ResultSet rs = stmt.executeQuery("SELECT ID_Lavoratore FROM Lavoratori");
                    while (rs.next()) {
                        int idLavoratore = rs.getInt("ID_Lavoratore");

                        tracciaIdSpecializzazioni.add(idLavoratore);
                    }
                    rs.close();
                }
                else {
                    // tracciaIdSpecializzazioni rimane vuota
                }

            }
            else {
                if(specializzazioniFilter.getFlag() == Flag.AND) {
                    for(Specializzazione specializzazione : specializzazioniFilter.getSpecializzazioni()) {
                        stmt = c.createStatement();
                        ResultSet rs = stmt.executeQuery("SELECT ID_Lavoratore FROM Esperienze WHERE NomeSpecializzazione ='" + specializzazione.getNomeSpecializzazione() + "';");
                        while (rs.next()) {
                            int idLavoratore = rs.getInt("ID_Lavoratore");

                            and1.add(idLavoratore);
                        }
                        rs.close();
                    }

                    Map<Integer, Integer> hm = new HashMap<Integer, Integer>();

                    for (Integer i : and1) {
                        Integer j = hm.get(i);
                        hm.put(i, (j == null) ? 1 : j + 1);
                    }
                    for (Map.Entry<Integer, Integer> val : hm.entrySet()) {
                        if(val.getValue() == specializzazioniFilter.getSpecializzazioni().size())
                            tracciaIdSpecializzazioni.add(val.getValue());
                    }

                    hm.clear();
                    and1.clear();

                }
                else {
                    for(Specializzazione specializzazione : specializzazioniFilter.getSpecializzazioni()) {
                        stmt = c.createStatement();
                        ResultSet rs = stmt.executeQuery("SELECT ID_Lavoratore FROM Esperienze WHERE NomeSpecializzazione ='" + specializzazione.getNomeSpecializzazione() + "';");
                        while (rs.next()) {
                            int idLavoratore = rs.getInt("ID_Lavoratore");

                            tracciaIdSpecializzazioni.add(idLavoratore);  // set senza ripetizioni
                        }
                        rs.close();
                    }
                }
            }
            //------------------------// Specializzazioni //-------------------------------

            //-------------------------- Automunito -----------------------------
            // Se TRUE -> predo solo tutti gli automuniti
            if(automunitoFilter.isAutomunito()) {
                stmt = c.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT ID_Lavoratore FROM Lavoratori WHERE Automunito = TRUE;");
                while (rs.next()) {
                    int idLavoratore = rs.getInt("ID_Lavoratore");

                    tracciaIdAutomunito.add(idLavoratore);
                }
                rs.close();
            }
            else { // Se false -> vanno bene sia automuniti che non automuniti(ovvero tutti i lavoratori per l'AND)
                if(flag == Flag.AND) {
                    stmt = c.createStatement();
                    ResultSet rs = stmt.executeQuery("SELECT ID_Lavoratore FROM Lavoratori");
                    while (rs.next()) {
                        int idLavoratore = rs.getInt("ID_Lavoratore");

                        tracciaIdAutomunito.add(idLavoratore);
                    }
                    rs.close();
                }
                else { // Se false -> non voglio nessun lavoratore (per l'OR)
                    // tracciaIdAutomunito rimane vuota
                }

            }
            //-------------------------// Automunito //--------------------------

            //---------------------------- Disponibilità -------------------------------
            // Se != -1 è un periodo di cui tener traccia(seleziono solo i lavoratori che hanno dispobinilità in quel periodo)
            if(disponibilitaFilter.getInizioPeriodo() != -1 && disponibilitaFilter.getFinePeriodo() != -1) {
                stmt = c.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT ID_Lavoratore FROM Disponibilita WHERE InizioPeriodo <='" + disponibilitaFilter.getInizioPeriodo() + "' AND FinePeriodo >= '" + disponibilitaFilter.getFinePeriodo() + "';");
                while (rs.next()) {
                    int idLavoratore = rs.getInt("ID_Lavoratore");

                    tracciaIdDisponibilita.add(idLavoratore);
                }
                rs.close();
            }
            else { // Se == -1 il periodo di disponibilità è ignorato(vanno bene tutti i lavoratori per l'AND)
                if(flag == Flag.AND) {
                    stmt = c.createStatement();
                    ResultSet rs = stmt.executeQuery("SELECT ID_Lavoratore FROM Lavoratori");
                    while (rs.next()) {
                        int idLavoratore = rs.getInt("ID_Lavoratore");

                        tracciaIdDisponibilita.add(idLavoratore);
                    }
                    rs.close();
                }
                else { // Se == -1 il periodo di disponibilità è ignorato(non voglio nessuno per l'OR)
                    // tracciaIdDisponibilità rimane vuota
                }
            }
            //--------------------------// Disponibilità //-----------------------------

            //---------------------------- DataNascita -----------------------------
            // Se != -1 è una data di cui tener traccia
            if(dataNascitaFilter.getDataNascita() != -1) {
                if(dataNascitaFilter.getFlag() == Flag.TO) { // I lavoratori nati fino al massimo in quella data
                    stmt = c.createStatement();
                    ResultSet rs = stmt.executeQuery("SELECT ID_Lavoratore FROM Lavoratori WHERE DataNascita <='" + dataNascitaFilter.getDataNascita() + "';");
                    while (rs.next()) {
                        int idLavoratore = rs.getInt("ID_Lavoratore");

                        tracciaIdDataNascita.add(idLavoratore);
                    }
                    rs.close();
                }
                else if(dataNascitaFilter.getFlag() == Flag.FROM) { // I lavoratori nati al minimo in quella data
                    stmt = c.createStatement();
                    ResultSet rs = stmt.executeQuery("SELECT ID_Lavoratore FROM Lavoratori WHERE DataNascita >='" + dataNascitaFilter.getDataNascita() + "';");
                    while (rs.next()) {
                        int idLavoratore = rs.getInt("ID_Lavoratore");

                        tracciaIdDataNascita.add(idLavoratore);
                    }
                    rs.close();
                }
                else {
                    System.out.println("Flag dataNascitaFilter non valido!");
                }

            }
            else { // Se == -1 la data di nascita è ignorata(vanno bene tutti i lavoratori per l'AND)
                if(flag == Flag.AND) {
                    stmt = c.createStatement();
                    ResultSet rs = stmt.executeQuery("SELECT ID_Lavoratore FROM Lavoratori");
                    while (rs.next()) {
                        int idLavoratore = rs.getInt("ID_Lavoratore");

                        tracciaIdDataNascita.add(idLavoratore);
                    }
                    rs.close();
                }
                else { // Se == -1 la data di nascita è ignorata(non voglio nessuno per l'OR)
                    // tracciaIdDataNascita rimane vuota
                }
            }
            //---------------------------// DataNascita //--------------------------


            if(flag == Flag.AND) {
                //Tengo solo gli id che fanno parte di tutte le liste che tracciano gli ID
                for(Integer i : tracciaIdLingue) {
                    if(tracciaIdComuni.contains(i) && tracciaIdPatenti.contains(i) && tracciaIdSpecializzazioni.contains(i) && tracciaIdAutomunito.contains(i) && tracciaIdDisponibilita.contains(i) && tracciaIdDataNascita.contains(i))
                        idLavoratori.add(i); // Trovato id idoneo alla ricerca
                }
            }
            else if(flag == Flag.OR) {
                //Tengo tutti gli id che compaiono almeno in una traccia(cioè tutti gli id di tutte le tracce)
                for(Integer i : tracciaIdLingue) {
                    idLavoratori.add(i); // set senza ripetizioni
                }
                for(Integer i : tracciaIdComuni) {
                    idLavoratori.add(i);
                }
                for(Integer i : tracciaIdPatenti) {
                    idLavoratori.add(i);
                }
                for(Integer i : tracciaIdSpecializzazioni) {
                    idLavoratori.add(i);
                }
                for(Integer i : tracciaIdAutomunito) {
                    idLavoratori.add(i);
                }
                for(Integer i : tracciaIdDisponibilita) {
                    idLavoratori.add(i);
                }
                for(Integer i : tracciaIdDataNascita) {
                    idLavoratori.add(i);
                }
            }
            else {
                System.out.println("Flag non valido!");
            }
            //----------------------------// Fine ricerca // ------------------------------

            for(Integer idTrovato : idLavoratori) {               // Ogni id trovato corrisponde ad un lavoratore idoneo ai parametri di ricerca
                Lavoratore lavoratore = getLavoratore(idTrovato); // Restituisce un lavoratore cercato
                lavoratoriCercati.add(lavoratore);                // Lista dei lavoratori cercati completa
            }

            stmt.close();
            c.close();

            return lavoratoriCercati;

        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }

        return null;
    }
}