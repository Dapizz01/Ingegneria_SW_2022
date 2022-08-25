package it.univr.lavoratoristagionali.model.Dao;

import it.univr.lavoratoristagionali.types.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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

                    Disponibilita disponibilitaSingola = new Disponibilita(comune,inizioPeriodo,finePeriodo);
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
}
