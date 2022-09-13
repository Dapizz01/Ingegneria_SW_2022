package it.univr.lavoratoristagionali.controller.forms;

import io.github.palexdev.materialfx.controls.MFXListView;
import io.github.palexdev.materialfx.controls.MFXTextField;
import it.univr.lavoratoristagionali.controller.exception.InputException;
import it.univr.lavoratoristagionali.controller.exception.InvalidPeriodException;
import it.univr.lavoratoristagionali.controller.validated.MFXDatePickerValidated;
import it.univr.lavoratoristagionali.controller.validated.MFXFilterComboBoxValidated;
import it.univr.lavoratoristagionali.controller.validated.MFXListViewValidated;
import it.univr.lavoratoristagionali.controller.validated.MFXTextFieldValidated;
import it.univr.lavoratoristagionali.model.Dao.ComuniDao;
import it.univr.lavoratoristagionali.model.Dao.ComuniDaoImpl;
import it.univr.lavoratoristagionali.model.Dao.SpecializzazioniDao;
import it.univr.lavoratoristagionali.model.Dao.SpecializzazioniDaoImpl;
import it.univr.lavoratoristagionali.types.Comune;
import it.univr.lavoratoristagionali.types.Esperienza;
import it.univr.lavoratoristagionali.types.Specializzazione;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe che rappresenta il sub-form delle esperienze lavorative passate,
 * usato sia nel form di inserimento che di modifica dei lavoratori stagionali.
 */
public class EsperienzeForm {
    private final MFXTextFieldValidated azienda, retribuzione;
    private final MFXDatePickerValidated inizioPeriodo, finePeriodo;
    private final MFXFilterComboBoxValidated<Comune> comune;
    private final MFXFilterComboBoxValidated<Specializzazione> specializzazione;
    private final MFXListViewValidated<Esperienza> listaEsperienze;
    private final ObservableList<Esperienza> esperienze;

    public EsperienzeForm(MFXTextFieldValidated azienda,
                          MFXTextFieldValidated retribuzione,
                          MFXDatePickerValidated inizioPeriodo,
                          MFXDatePickerValidated finePeriodo,
                          MFXFilterComboBoxValidated<Comune> comune,
                          MFXFilterComboBoxValidated<Specializzazione> specializzazione,
                          MFXListViewValidated<Esperienza> listaEsperienze){

        ComuniDao comuniDao = new ComuniDaoImpl();
        SpecializzazioniDao specializzazioniDao = new SpecializzazioniDaoImpl();

        this.azienda = azienda;
        this.retribuzione = retribuzione;
        this.inizioPeriodo = inizioPeriodo;
        this.finePeriodo = finePeriodo;
        this.comune = comune;
        this.specializzazione = specializzazione;
        this.listaEsperienze = listaEsperienze;

        this.comune.setItems(FXCollections.observableArrayList(comuniDao.getComuni()));
        this.specializzazione.setItems(FXCollections.observableArrayList(specializzazioniDao.getSpecializzazioni()));

        esperienze = FXCollections.observableArrayList(new ArrayList<>());
        listaEsperienze.setItems(esperienze);
    }

    /**
     * Imposta le esperienze in list nella lista delle esperienze
     *
     * @param list contiene delle esperienze
     */
    public void setEsperienze(List<Esperienza> list){
        esperienze.setAll(list);
    }

    /**
     * Raccoglie i dati contenuti nei campi e ne controlla il contenuto.
     * Se il contenuto non è valido (secondo i parametri impostati alla creazione), viene marcato il campo invalido
     * e non viene proseguita l'inserimento.
     * Se il contenuto è valido, viene inserita l'esperienza nella lista delle esperienze.
     */
    public void addEsperienza(){
        try {
            // Controllo che la data di inizio esperienza non sia successiva alla data di fine esperienza
            if(finePeriodo.getEpochDays() <= inizioPeriodo.getEpochDays())
                throw new InvalidPeriodException(finePeriodo, "La data di fine è antecedente alla data di inizio");

            // Controllo che il periodo indicato dall'utente sia di almeno un mese (periodo minimo di esperienza)
            if(finePeriodo.getEpochDays() <= inizioPeriodo.getEpochDays() + 30)
                throw new InvalidPeriodException(finePeriodo, "La durata deve essere di almeno un mese");

            // Controllo che l'esperienza abbia durata inferiore a 2 anni (limite massimo per un lavoro stagionale)
            if(finePeriodo.getEpochDays() >= inizioPeriodo.getEpochDays() + 2 * 365)
                throw new InvalidPeriodException(finePeriodo, "La durata deve essere di massimo 2 anni");

            // Viene creato una nuova esperienza.
            // I vari metodi get dei decorator Validated richiamano checkValid() e
            // controllano lo stato attuale dell'elemento. Se non è valido viene lanciata
            // una InputException, e non viene terminato l'inserimento dell'esperienza.
            Esperienza nuovaEsperienza = new Esperienza(-1,
                    azienda.getText(),
                    Integer.parseInt(retribuzione.getText()),
                    inizioPeriodo.getEpochDays(),
                    finePeriodo.getEpochDays(),
                    comune.getSelectedItem(),
                    specializzazione.getSelectedItem());

            esperienze.add(nuovaEsperienza);

            // Reset di tutti i campi del form dell'esperienza, per eliminare
            // eventuali errori precedenti
            azienda.reset();
            retribuzione.reset();
            inizioPeriodo.reset();
            finePeriodo.reset();
            comune.reset();
        }
        catch(InputException inputException) {
            return;
        }
    }

    /**
     * Se è stato selezionata un'esperienza dalla lista, essa verrà cancellata.
     */
     public void deleteSelectedEsperienza(){
         // Per ogni elemento selezionato dall'utente su listaDisponibilita (al massimo 1 alla volta)
         for(int key : listaEsperienze.getSelectionModel().getSelection().keySet()){
             // Rimuovi disponibilità dalla lista dei contatti (ObservableList)
             esperienze.remove(listaEsperienze.getSelectionModel().getSelection().get(key));
         }
         // Rimuovi la precedente selezione
         listaEsperienze.getSelectionModel().clearSelection();
     }

    /**
     * Restituisce la lista delle esperienze inseriti
     * @return esperienze inserite
     */
     public List<Esperienza> getEsperienze(){
        return esperienze;
     }


}
