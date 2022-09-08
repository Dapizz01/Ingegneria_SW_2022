package it.univr.lavoratoristagionali.model.Dao;

import it.univr.lavoratoristagionali.filters.*;
import it.univr.lavoratoristagionali.types.Comune;
import it.univr.lavoratoristagionali.types.Lavoratore;
import it.univr.lavoratoristagionali.types.Lingua;

import java.util.List;

public interface LavoratoriDao {
    /**
     * Aggiunge un nuovo lavoratore all'interno del database, popolando tutte le tabelle necessarie
     *
     * @param nuovoLavoratore oggetto di tipo Lavoratore contenente tutte le informazioni del nuovo lavoratore che deve essere inserito nel DB
     * @return int ID del lavoratore aggiunto al DB
     */
    int addLavoratore(Lavoratore nuovoLavoratore);

    /**
     * Si collega al DataBase per prelevare tutti i lavoratori che hanno come nome e cognome quelli specificati in ingresso nei parametri sotto forma di lista
     *
     * @param nome stringa contenente il nome del lavoratore da cercare nel DB
     * @param cognome stringa contenente il cognome del lavoratore da cercare nel DB
     * @return List<Lavoratore>: lista completa dei lavoratori presenti nel DB con quel nome, cognome specificato
     */
    List<Lavoratore> getLavoratori(String nome, String cognome);

    /**
     * Si collega al DataBase per eliminare il lavoratore(con tutte le relative tabelle associate) dal database con l'ID specificato in ingresso dal parametro
     *
     * @param idDaEliminare ID del lavoratore da eliminare dal DB
     * @return true se il lavoratore è stato correttamente rimosso dal DB, false altrimenti
     */
    boolean deleteLavoratore(int idDaEliminare);

    /**
     * Si collega al DataBase per modificare i dati di un certo lavoratore(con le relative tabelle associate)
     *
     * @param lavoratoreDaModificare oggetto Lavoratore che contiene tutti i nuovi dati associati a quel lavoratore, a eccezione dell' ID che non può essere modificato
     * @return int: ID del lavoratore che ha subito modifiche nel DB
     * @warning Il lavoratore per essere modificato, viene prima eliminato dal DB e poi reinserito al suo interno. Questo comporta una modifica del suo ID essendo autoincrementale. Nonostante ciò il valore intero di ritorno della funzione è il "vecchio" ID del lavoratore, ovvero quello prima di essere modificato
     * Es: DB: Lav 1 ID 1 - Lav 2 ID 2
     *     Modifica di Lav1
     *     DB: Lav 1 ID 3 - Lav 2 ID 2
     */
    int updateLavoratore(Lavoratore lavoratoreDaModificare);

    /**
     * Si collega al DataBase per prelevare i dati di un certo lavoratore con l'ID specificato in ingresso dal parametro
     *
     * @param idDacCercare int che specifica l'ID del lavoratore di cui prelevare i dati dal DB
     * @return Lavoratore: Oggetto Lavoratore costruito con tutte le informazione prelevate dalle varie tabelle del DB
     */
    Lavoratore getLavoratore(int idDacCercare);

    /**
     * Ricerca nel database i lavoratori specificando in ingresso il tipo ricerca da effetturare tramite i filtri e flag
     *
     * @param lingueFilter Filtro di ricerca delle lingue, contiene le lingue che i lavoratori dovranno parlare per superare la ricerca e un flag AND/OR. Se flag = AND i lavoratori che parlano tutte le lingue presenti nel filtro superano la ricerca, Se flag = OR i lavoratori devono parlare almenu una della lingue presenti nel filtro per superare la ricerca
     * @param comuniFilter Filtro di ricerca dei comuni, contiene i comuni dove i lavoratori dovranno abitare per superare la ricerca e un flag AND/OR con lo stesso funzionamento nel filtro delle lingue
     * @param patentiFilter Filtro di ricerca delle patenti, contiene le patenti che i lavoratori dovranno possedere per superare la ricerca e un flag AND/OR con lo stesso funzionamento nel filtro delle lingue
     * @param specializzazioniFilter Filtro di ricerca delle specializzazioni, contiene le specializzazioni(in base alle esperienze svolte) che i lavoratori dovranno avere per superare la ricerca e un flag AND/OR con lo stesso funzionamento nel filtro delle lingue
     * @param automunitoFilter Filtro di ricerca dei lavoratori auto muniti, se true, solo i lavoratori auto muniti sono idonei alla ricerca, se false il filtro viene ignorato
     * @param disponibilitaFilter Filtro di ricerca delle disponibilità, contiene un comune e un periodo di tempo(una data d'inizio e una di fine) in cui i lavoratori devono dare disponibilità per superare la ricerca in quel determinato comune.
     * @param dataNascitaFilter Filtro di ricerca in base alla data di nascita, contiene una data e un flag FROM/TO. Se flag = FROM i lavoratori che sono nati da quella data sono idonei alla ricerca, Se flag = TO sono idonei alla ricerca solo i lavoratori nati fino al massimo in quella data
     * @return List<Lavoratore>: lista dei lavoratori presenti nel DB che sono risultati idonei alla ricerca ovvero rispettano tutti i filtri in ingresso
     */
    List<Lavoratore> searchLavoratori(LingueFilter lingueFilter, ComuniFilter comuniFilter, PatentiFilter patentiFilter, SpecializzazioniFilter specializzazioniFilter, AutomunitoFilter automunitoFilter, DisponibilitaFilter disponibilitaFilter, DataNascitaFilter dataNascitaFilter, Flag flag);
}