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
                pstmt.setString(1, nuovoContatto.getNome());
                pstmt.setString(2, nuovoContatto.getCognome());
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
}
