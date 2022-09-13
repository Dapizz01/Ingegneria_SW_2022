package it.univr.lavoratoristagionali.controller.forms;

import io.github.palexdev.materialfx.controls.MFXListView;
import it.univr.lavoratoristagionali.controller.exception.InputException;
import it.univr.lavoratoristagionali.controller.validated.MFXListViewValidated;
import it.univr.lavoratoristagionali.controller.validated.MFXTextFieldValidated;
import it.univr.lavoratoristagionali.types.Contatto;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe che rappresenta il sub-form dei contatti urgenti,
 * usato sia nel form di inserimento che di modifica dei lavoratori stagionali.
 */
public class ContattiUrgentiForm{
    private final MFXTextFieldValidated nomeContatto, cognomeContatto, telefonoContatto, emailContatto;
    private final MFXListViewValidated<Contatto> listaContatti;
    private final ObservableList<Contatto> contatti;

    public ContattiUrgentiForm(MFXTextFieldValidated nomeContatto,
                               MFXTextFieldValidated cognomeContatto,
                               MFXTextFieldValidated telefonoContatto,
                               MFXTextFieldValidated emailContatto,
                               MFXListViewValidated<Contatto> listaContatti){

        this.nomeContatto = nomeContatto;
        this.cognomeContatto = cognomeContatto;
        this.telefonoContatto = telefonoContatto;
        this.emailContatto = emailContatto;
        this.listaContatti = listaContatti;

        contatti = FXCollections.observableArrayList(new ArrayList<>());
        listaContatti.setItems(contatti);
    }

    /**
     * Imposta i contatti contenuti in list nella lista dei contatti urgenti
     *
     * @param list contiene dei contatti urgenti
     */
    public void setContatti(List<Contatto> list){
        contatti.setAll(list);
    }

    /**
     * Raccoglie i dati contenuti nei campi e ne controlla il contenuto.
     * Se il contenuto non è valido (secondo i parametri impostati alla creazione), viene marcato il campo invalido
     * e non viene proseguita l'inserimento.
     * Se il contenuto è valido, viene inserito il contatto nella lista dei contatti urgenti.
     */
    public void addContatto(){
        Contatto contatto;
        try {
            // Viene creato un nuovo contatto.
            // I vari metodi get dei decorator Validated richiamano checkValid() e
            // controllano lo stato attuale dell'elemento. Se non è valido viene lanciata
            // una InputException, e non viene terminato l'inserimento del contatto.
            contatto = new Contatto(-1,
                    nomeContatto.getText(),
                    cognomeContatto.getText(),
                    telefonoContatto.getText(),
                    emailContatto.getText());

            // Ora il contatto è stato validato, e viene aggiunto alla lista dei contatti
            contatti.add(contatto);

            // Reset di tutti i campi del form dei contatti, per eliminare
            // eventuali errori precedenti
            nomeContatto.reset();
            cognomeContatto.reset();
            telefonoContatto.reset();
            emailContatto.reset();
        }
        catch (InputException inputException) {
            return;
        }
    }

    /**
     * Restituisce la lista dei contatti urgenti inseriti
     * @return contatti urgenti inseriti
     */
    public List<Contatto> getContatti(){
        return contatti;
    }

    /**
     * Se è stato selezionato un contatto dalla lista, esso verrà cancellato.
     */
    public void deleteSelectedContatto(){
        // Per ogni elemento selezionato dall'utente su listaContattoUrgente (al massimo 1 alla volta)
        for(int key : listaContatti.getSelectionModel().getSelection().keySet()){
            // Rimuovi contatto urgente dalla lista dei contatti (ObservableList)
            contatti.remove(listaContatti.getSelectionModel().getSelection().get(key));
        }
        // Rimuovi la precedente selezione
        listaContatti.getSelectionModel().clearSelection();
    }
}
