package it.univr.lavoratoristagionali.controller.forms;

import it.univr.lavoratoristagionali.controller.exception.InputException;
import it.univr.lavoratoristagionali.controller.exception.InvalidPeriodException;
import it.univr.lavoratoristagionali.controller.validated.MFXDatePickerValidated;
import it.univr.lavoratoristagionali.controller.validated.MFXFilterComboBoxValidated;
import it.univr.lavoratoristagionali.controller.validated.MFXListViewValidated;
import it.univr.lavoratoristagionali.model.Dao.ComuniDao;
import it.univr.lavoratoristagionali.model.Dao.ComuniDaoImpl;
import it.univr.lavoratoristagionali.types.Comune;
import it.univr.lavoratoristagionali.types.Disponibilita;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe che rappresenta il sub-form delle disponibilità,
 * usato sia nel form di inserimento che di modifica dei lavoratori stagionali.
 */
public class DisponibilitaForm {
    private final MFXDatePickerValidated inizioPeriodo, finePeriodo;
    private final MFXFilterComboBoxValidated<Comune> comune;
    private final MFXListViewValidated<Disponibilita> listaDisponibilita;
    private final ObservableList<Disponibilita> disponibilita;

    public DisponibilitaForm(MFXDatePickerValidated inizioPeriodo,
                             MFXDatePickerValidated finePeriodo,
                             MFXFilterComboBoxValidated<Comune> comune,
                             MFXListViewValidated<Disponibilita> listaDisponibilita){

        ComuniDao comuniDao = new ComuniDaoImpl();

        this.inizioPeriodo = inizioPeriodo;
        this.finePeriodo = finePeriodo;
        this.comune = comune;
        this.listaDisponibilita = listaDisponibilita;

        this.comune.setItems(FXCollections.observableArrayList(comuniDao.getComuni()));

        disponibilita = FXCollections.observableArrayList(new ArrayList<>());
        listaDisponibilita.setItems(disponibilita);
    }

    /**
     * Imposta le disponibilità in list nella lista delle disponibilità
     *
     * @param list contiene delle disponibilità
     */
    public void setDisponibilita(List<Disponibilita> list){
        disponibilita.setAll(list);
    }

    /**
     * Raccoglie i dati contenuti nei campi e ne controlla il contenuto.
     * Se il contenuto non è valido (secondo i parametri impostati alla creazione), viene marcato il campo invalido
     * e non viene proseguita l'inserimento.
     * Se il contenuto è valido, viene inserita le disponibilità nella lista delle disponibililtà.
     */
    public void addDisponibilita(){
        try{
            // Controllo che la data di inizio disponibilità non sia successiva alla data di fine disponibilità
            if(inizioPeriodo.getEpochDays()  >= finePeriodo.getEpochDays())
                throw new InvalidPeriodException(finePeriodo, "La data di fine è antecedente alla data di inizio");

            // Controllo che il periodo indicato dall'utente sia di almeno un mese (periodo minimo di disponibilità)
            if(finePeriodo.getEpochDays() <= inizioPeriodo.getEpochDays() + 30)
                throw new InvalidPeriodException(finePeriodo, "La durata deve essere di almeno un mese");

            // Controllo dei conflitti con le disponibilità già presenti
            // Due disponibilità sono in conflitto se riguardano lo stesso comune e si sovrappongono per un certo periodo temporale

            // Per ogni disponibilità già presente
            for(Disponibilita disponibilita : listaDisponibilita.getItems()){
                // Se tale disponibilità riguarda lo stesso comune della disponibilità da inserire
                if(disponibilita.getComune() == comune.getSelectedItem()){
                    // Se hanno un periodo in comune, viene lanciata una InputException, e viene impedito di aggiungerla alla lista delle disponibilità
                    if(inizioPeriodo.getEpochDays() <= disponibilita.getFinePeriodo() && finePeriodo.getEpochDays() >= disponibilita.getInizioPeriodo())
                        throw new InvalidPeriodException(listaDisponibilita, "La disponibilità da inserire è in conflitto con quelle già inserite");
                }
            }

            // Viene creato una nuova disponibilità.
            // I vari metodi get dei decorator Validated richiamano checkValid() e
            // controllano lo stato attuale dell'elemento. Se non è valido viene lanciata
            // una InputException, e non viene terminato l'inserimento della disponibilità.
            Disponibilita nuovaDisponibilita = new Disponibilita(inizioPeriodo.getEpochDays(),
                    finePeriodo.getEpochDays(),
                    comune.getSelectedItem());

            disponibilita.add(nuovaDisponibilita);

            // Reset di tutti i campi del form delle disponibilità, per eliminare
            // eventuali errori precedenti
            inizioPeriodo.reset();
            finePeriodo.reset();
            comune.reset();
        }
        catch (InputException inputException){
            return;
        }
    }

    /**
     * Restituisce la lista delle disponibilità inseriti
     * @return disponibilità inserite
     */
    public List<Disponibilita> getDisponibilita(){
        return disponibilita;
    }

    /**
     * Se è stato selezionata una disponibilità dalla lista, essa verrà cancellata.
     */
    public void deleteSelectedDisponibilita(){
        // Per ogni elemento selezionato dall'utente su listaDisponibilita (al massimo 1 alla volta)
        for(int key : listaDisponibilita.getSelectionModel().getSelection().keySet()){
            // Rimuovi disponibilità dalla lista dei contatti (ObservableList)
            disponibilita.remove(listaDisponibilita.getSelectionModel().getSelection().get(key));
        }
        // Rimuovi la precedente selezione
        listaDisponibilita.getSelectionModel().clearSelection();
    }
}
