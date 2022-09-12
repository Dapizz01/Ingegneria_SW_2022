package it.univr.lavoratoristagionali.controller;

import io.github.palexdev.materialfx.controls.*;
import it.univr.lavoratoristagionali.controller.enums.Check;
import it.univr.lavoratoristagionali.controller.enums.View;
import it.univr.lavoratoristagionali.controller.exception.InputException;
import it.univr.lavoratoristagionali.controller.exception.InvalidPeriodException;
import it.univr.lavoratoristagionali.controller.validated.*;
import it.univr.lavoratoristagionali.model.Dao.*;
import it.univr.lavoratoristagionali.types.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Classe controller dell'inserimento dei lavoratori
 */
public class InserisciLavoratoriController extends Controller implements Initializable {
    // ------ ANAGRAFICA ------ //
    @FXML
    private MFXTextField nomeLavoratore, cognomeLavoratore;
    private MFXTextFieldValidated nomeLavoratoreValidated, cognomeLavoratoreValidated;
    @FXML
    private MFXDatePicker dataNascitaLavoratore;
    private MFXDatePickerValidated dataNascitaLavoratoreValidated;
    @FXML
    private MFXFilterComboBox<Comune> comuneNascitaLavoratore, comuneAbitazioneLavoratore;
    private MFXFilterComboBoxValidated<Comune> comuneNascitaLavoratoreValidated, comuneAbitazioneLavoratoreValidated;
    @FXML
    private MFXFilterComboBox<Lingua> nazionalitaLavoratore;
    private MFXFilterComboBoxValidated<Lingua> nazionalitaLavoratoreValidated;
    @FXML
    private Label nomeLavoratoreError, cognomeLavoratoreError, dataNascitaLavoratoreError, comuneNascitaLavoratoreError, comuneAbitazioneLavoratoreError;

    // ------ CONTATTO LAVORATORE ------ //
    @FXML
    private MFXTextField telefonoLavoratore, emailLavoratore;
    private MFXTextFieldValidated telefonoLavoratoreValidated, emailLavoratoreValidated;
    @FXML
    private Label indirizzoLavoratoreError, telefonoLavoratoreError, emailLavoratoreError;

    // ------ INFORMAZIONI GENERALI ------ //
    @FXML
    private MFXCheckListView<Lingua> lingueLavoratore;
    private MFXCheckListViewValidated<Lingua> lingueLavoratoreValidated;
    @FXML
    private MFXCheckListView<Patente> patentiLavoratore;
    private MFXCheckListViewValidated<Patente> patentiLavoratoreValidated;
    @FXML
    private MFXCheckbox automunito;
    @FXML
    private Label nazionalitaLavoratoreError, patentiLavoratoreError, lingueLavoratoreError;

    // ------ CONTATTI URGENTI ------ //
    @FXML
    private MFXTextField nomeContatto, cognomeContatto, telefonoContatto, emailContatto;
    private MFXTextFieldValidated nomeContattoValidated, cognomeContattoValidated, telefonoContattoValidated, emailContattoValidated;
    @FXML
    private MFXButton aggiungiContatto, eliminaContatto;
    @FXML
    private MFXListView<Contatto> listaContattoUrgente;
    private MFXListViewValidated<Contatto> listaContattoUrgenteValidated;
    @FXML
    private Label nomeContattoError, cognomeContattoError, telefonoContattoError, emailContattoError, listaContattoUrgenteError;

    // ------ DISPONIBILITA ------ //
    @FXML
    private MFXDatePicker inizioDisponibilita, fineDisponibilita;
    private MFXDatePickerValidated inizioDisponibilitaValidated, fineDisponibilitaValidated;
    @FXML
    private MFXFilterComboBox<Comune> comuneDisponibilita;
    private MFXFilterComboBoxValidated<Comune> comuneDisponibilitaValidated;
    @FXML
    private MFXButton aggiungiDisponibilita, eliminaDisponibilita;
    @FXML
    private MFXListView<Disponibilita> listaDisponibilita;
    private MFXListViewValidated<Disponibilita> listaDisponibilitaValidated;
    @FXML
    private Label inizioDisponibilitaError, fineDisponibilitaError, comuneDisponibilitaError, listaDisponibilitaError;

    // ------ ESPERIENZE ------ //
    @FXML
    private MFXTextField aziendaEsperienza, retribuzioneEsperienza;
    private MFXTextFieldValidated aziendaEsperienzaValidated, retribuzioneEsperienzaValidated;
    @FXML
    private MFXDatePicker inizioEsperienza, fineEsperienza;
    private MFXDatePickerValidated inizioEsperienzaValidated, fineEsperienzaValidated;
    @FXML
    private MFXFilterComboBox<Comune> comuneEsperienza;
    private MFXFilterComboBoxValidated<Comune> comuneEsperienzaValidated;
    @FXML
    private MFXFilterComboBox<Specializzazione> specializzazioneEsperienza;
    private MFXFilterComboBoxValidated<Specializzazione> specializzazioneEsperienzaValidated;
    @FXML
    private MFXButton aggiungiEsperienza, eliminaEsperienza;
    @FXML
    private MFXListView<Esperienza> listaEsperienze;
    private MFXListViewValidated<Esperienza> listaEsperienzeValidated;
    @FXML
    private Label aziendaEsperienzaError, retribuzioneEsperienzaError, inizioEsperienzaError, fineEsperienzaError, comuneEsperienzaError, specializzazioneEsperienzaError, listaEsperienzeError;

    // ... il resto
    @FXML
    private MFXButton ritornaMenu, inviaLavoratore;
    @FXML
    private MFXScrollPane scrollPane;

    // Variabili del controller non legate ad FXML
    private ObservableList<Contatto> contatti;

    private ObservableList<Esperienza> esperienze;

    private ObservableList<Disponibilita> disponibilita;

    // Costanti
    private static final int DAYS_IN_MONTH = 30;
    private static final int DAYS_IN_YEAR = 365;

    public InserisciLavoratoriController(){

    }

    /**
     * Metodo chiamato dopo la creazione della scena (e di tutti i suoi elementi) da parte di JavaFX.
     * Crea gli oggetti Dao e i decorator Validated. Inoltre popola i campi a scelta multipla con i dati ottenuti dai Dao.
     *
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Creazione oggetti DAO
        ComuniDao comuniDao = new ComuniDaoImpl();
        LingueDao lingueDao = new LingueDaoImpl();
        PatentiDao patentiDao = new PatentiDaoImpl();
        SpecializzazioniDao specializzazioniDao = new SpecializzazioniDaoImpl();

        // Salvataggio di tutti i dati dei DAO in delle liste
        List<Comune> comuni = comuniDao.getComuni(); // Ritorna la lista dei comuni nel DB da 0=Bonavigo a 5=Casaleone
        List<Lingua> lingue = lingueDao.getLingue();
        List<Patente> patenti = patentiDao.getPatenti();
        List<Specializzazione> specializzazioni = specializzazioniDao.getSpecializzazioni();

        // Creazione dei decorator Validated
        nomeLavoratoreValidated = new MFXTextFieldValidated(nomeLavoratore, nomeLavoratoreError, Check.LETTERS_ONLY, Check.NON_EMPTY);
        cognomeLavoratoreValidated = new MFXTextFieldValidated(cognomeLavoratore, cognomeLavoratoreError, Check.LETTERS_ONLY, Check.NON_EMPTY);
        dataNascitaLavoratoreValidated = new MFXDatePickerValidated(dataNascitaLavoratore, dataNascitaLavoratoreError, Check.MUST_BE_ADULT, Check.NON_EMPTY);
        comuneNascitaLavoratoreValidated = new MFXFilterComboBoxValidated<Comune>(comuneNascitaLavoratore, comuneNascitaLavoratoreError, Check.NON_EMPTY);
        comuneAbitazioneLavoratoreValidated = new MFXFilterComboBoxValidated<Comune>(comuneAbitazioneLavoratore, comuneAbitazioneLavoratoreError, Check.NON_EMPTY);
        nazionalitaLavoratoreValidated = new MFXFilterComboBoxValidated<Lingua>(nazionalitaLavoratore, nazionalitaLavoratoreError, Check.NON_EMPTY);
        telefonoLavoratoreValidated = new MFXTextFieldValidated(telefonoLavoratore, telefonoLavoratoreError, Check.TELEPHONE_FORMAT);
        emailLavoratoreValidated = new MFXTextFieldValidated(emailLavoratore, emailLavoratoreError, Check.EMAIL_FORMAT, Check.NON_EMPTY);

        lingueLavoratoreValidated = new MFXCheckListViewValidated<Lingua>(lingueLavoratore, lingueLavoratoreError, Check.NON_EMPTY);
        patentiLavoratoreValidated = new MFXCheckListViewValidated<Patente>(patentiLavoratore, patentiLavoratoreError, (Check) null);

        nomeContattoValidated = new MFXTextFieldValidated(nomeContatto, nomeContattoError, Check.LETTERS_ONLY, Check.NON_EMPTY);
        cognomeContattoValidated = new MFXTextFieldValidated(cognomeContatto, cognomeContattoError, Check.LETTERS_ONLY, Check.NON_EMPTY);
        emailContattoValidated = new MFXTextFieldValidated(emailContatto, emailContattoError, Check.EMAIL_FORMAT, Check.NON_EMPTY);
        telefonoContattoValidated = new MFXTextFieldValidated(telefonoContatto, telefonoContattoError, Check.TELEPHONE_FORMAT, Check.NON_EMPTY);
        listaContattoUrgenteValidated = new MFXListViewValidated<Contatto>(listaContattoUrgente, listaContattoUrgenteError, Check.NON_EMPTY);

        inizioDisponibilitaValidated = new MFXDatePickerValidated(inizioDisponibilita, inizioDisponibilitaError, Check.FROM_NOW, Check.NON_EMPTY);
        fineDisponibilitaValidated = new MFXDatePickerValidated(fineDisponibilita, fineDisponibilitaError, Check.FROM_NOW, Check.NON_EMPTY);
        comuneDisponibilitaValidated = new MFXFilterComboBoxValidated<Comune>(comuneDisponibilita, comuneDisponibilitaError, Check.NON_EMPTY);
        listaDisponibilitaValidated = new MFXListViewValidated<Disponibilita>(listaDisponibilita, listaDisponibilitaError, (Check) null);

        aziendaEsperienzaValidated = new MFXTextFieldValidated(aziendaEsperienza, aziendaEsperienzaError, Check.LETTERS_ONLY, Check.NON_EMPTY);
        retribuzioneEsperienzaValidated = new MFXTextFieldValidated(retribuzioneEsperienza, retribuzioneEsperienzaError, Check.NUMBERS_ONLY, Check.NON_EMPTY);
        inizioEsperienzaValidated = new MFXDatePickerValidated(inizioEsperienza, inizioEsperienzaError, Check.UP_TO_NOW, Check.NON_EMPTY, Check.FROM_FIVE_YEARS_AGO);
        fineEsperienzaValidated = new MFXDatePickerValidated(fineEsperienza, fineEsperienzaError, Check.UP_TO_NOW, Check.NON_EMPTY, Check.FROM_FIVE_YEARS_AGO);
        comuneEsperienzaValidated = new MFXFilterComboBoxValidated<Comune>(comuneEsperienza, comuneEsperienzaError, Check.NON_EMPTY);
        specializzazioneEsperienzaValidated = new MFXFilterComboBoxValidated<Specializzazione>(specializzazioneEsperienza, specializzazioneEsperienzaError, Check.NON_EMPTY);
        listaEsperienzeValidated = new MFXListViewValidated<Esperienza>(listaEsperienze, listaEsperienzeError, (Check) null);

        // Popolazione dei campi a scelta multipla (o delle liste) con i dati salvati
        comuneNascitaLavoratore.setItems(FXCollections.observableArrayList(comuni));
        comuneAbitazioneLavoratore.setItems(FXCollections.observableArrayList(comuni));
        lingueLavoratore.setItems(FXCollections.observableArrayList(lingue));
        nazionalitaLavoratore.setItems(FXCollections.observableArrayList(lingue));
        patentiLavoratore.setItems(FXCollections.observableArrayList(patenti));
        comuneEsperienza.setItems(FXCollections.observableArrayList(comuni));
        comuneDisponibilita.setItems(FXCollections.observableArrayList(comuni));
        specializzazioneEsperienza.setItems(FXCollections.observableArrayList(specializzazioni));

        // I dati inseriti in listaContattoUrgente, listaEsperienze, listaDisponibilita vengono automaticamente
        // messi anche in contatti, esperienze e disponiblità, perchè condividono fra loro una ObservableList
        contatti = FXCollections.observableArrayList(new ArrayList<>());
        listaContattoUrgente.setItems(contatti);

        esperienze = FXCollections.observableArrayList(new ArrayList<>());
        listaEsperienze.setItems(esperienze);

        disponibilita = FXCollections.observableArrayList(new ArrayList<>());
        listaDisponibilita.setItems(disponibilita);

        // Settaggi che implementano lo smooth scrolling (solo con trackpad)
        listaDisponibilita.features().enableSmoothScrolling(1.2);
        listaEsperienze.features().enableSmoothScrolling(1.2);
        listaContattoUrgente.features().enableSmoothScrolling(1.2);
    }

    /**
     * Evento generato da JavaFX, al click del pulsante di ritorno al menu.
     * Sostituisce la scena corrente con la scena del menu principale.
     *
     * @param actionEvent parametro evento JavaFX
     */
    @FXML
    private void onClickRitornaMenu(ActionEvent actionEvent){
        switchScene(getStageFromEvent(actionEvent), View.MAIN_MENU);
    }

    /**
     * Evento generato da JavaFX, al click del pulsante di invio lavoratore al DAO.
     * Raccoglie i dati contenuti nei campi e ne controlla il contenuto.
     * Se il contenuto non è valido (secondo i parametri impostati alla creazione), viene marcato il campo invalido
     * e non viene proseguita l'invio.
     * Se il contenuto è valido, viene inviato il lavoratore al DAO appropriato.
     *
     * @param actionEvent parametro evento JavaFX
     */
    @FXML
    private void onClickInviaLavoratore(ActionEvent actionEvent){
        Lavoratore lavoratore;

        try{
            // Viene creato un nuovo lavoratore.
            // I vari metodi get dei decorator Validated richiamano checkValid() e
            // controllano lo stato attuale dell'elemento. Se non è valido viene lanciata
            // una InputException, e non viene terminato l'inserimento del lavoratore.
            lavoratore = new Lavoratore(-1,
                    nomeLavoratoreValidated.getText(),
                    cognomeLavoratoreValidated.getText(),
                    comuneNascitaLavoratoreValidated.getSelectedItem(),
                    comuneAbitazioneLavoratoreValidated.getSelectedItem(),
                    dataNascitaLavoratoreValidated.getEpochDays(),
                    nazionalitaLavoratoreValidated.getSelectedItem(),
                    emailLavoratoreValidated.getText(),
                    telefonoLavoratoreValidated.getText(),
                    automunito.isSelected(),
                    listaEsperienzeValidated.getSelectedItems(),
                    lingueLavoratoreValidated.getSelectedItems(),
                    listaContattoUrgenteValidated.getSelectedItems(),
                    patentiLavoratoreValidated.getSelectedItems(),
                    listaDisponibilitaValidated.getSelectedItems());
            LavoratoriDao lavoratoriDao = new LavoratoriDaoImpl();
            // Inserimento lavoratore attraverso Dao
            lavoratoriDao.addLavoratore(lavoratore);
            // L'inserimento è avvenuto con successo, cambio di scena al menù principale
            switchScene(getStageFromEvent(actionEvent), View.MAIN_MENU);
        }
        catch (InputException inputException){
            return;
        }
    }

    /**
     * Mateodo callback richiamato da JavaFX al click del pulsante di inserimento contatto urgente.
     * Raccoglie i dati contenuti nei campi e ne controlla il contenuto.
     * Se il contenuto non è valido (secondo i parametri impostati alla creazione), viene marcato il campo invalido
     * e non viene proseguita l'inserimento.
     * Se il contenuto è valido, viene inserito il contatto nella lista dei contatti urgenti.
     *
     * @param actionEvent parametro evento JavaFX
     */
    @FXML
    private void onClickAggiungiContatto(ActionEvent actionEvent) {
        Contatto contatto;
        try {
            // Viene creato un nuovo contatto.
            // I vari metodi get dei decorator Validated richiamano checkValid() e
            // controllano lo stato attuale dell'elemento. Se non è valido viene lanciata
            // una InputException, e non viene terminato l'inserimento del contatto.
            contatto = new Contatto(-1,
                    nomeContattoValidated.getText(),
                    cognomeContattoValidated.getText(),
                    telefonoContattoValidated.getText(),
                    emailContattoValidated.getText());

            // Ora il contatto è stato validato, e viene aggiunto alla lista dei contatti
            contatti.add(contatto);

            // Reset di tutti i campi del form dei contatti, per eliminare
            // eventuali errori precedenti
            nomeContattoValidated.reset();
            cognomeContattoValidated.reset();
            telefonoContattoValidated.reset();
            emailContattoValidated.reset();
        }
        catch (InputException inputException) {
            return;
        }
    }

    /**
     * Mateodo callback richiamato da JavaFX al click del pulsante di eliminazione di un contatto urgente.
     * Se è stato selezionato un contatto dalla lista, esso verrà cancellato, altrimenti ignora l'evento.
     *
     * @param actionEvent parametro evento JavaFX
     */
    @FXML
    private void onClickEliminaContatto(ActionEvent actionEvent){
        // Per ogni elemento selezionato dall'utente su listaContattoUrgente (al massimo 1 alla volta)
        for(int key : listaContattoUrgente.getSelectionModel().getSelection().keySet()){
            // Rimuovi contatto urgente dalla lista dei contatti (ObservableList)
            contatti.remove(listaContattoUrgente.getSelectionModel().getSelection().get(key));
        }
        // Rimuovi la precedente selezione
        listaContattoUrgente.getSelectionModel().clearSelection();
    }

    /**
     * Mateodo callback richiamato da JavaFX al click del pulsante di eliminazione di una disponibilità.
     * Se è stato selezionata una disponibilità dalla lista, esso verrà cancellato, altrimenti ignora l'evento.
     *
     * @param actionEvent parametro evento JavaFX
     */
    @FXML
    private void onClickEliminaDisponibilita(ActionEvent actionEvent) {
        // Per ogni elemento selezionato dall'utente su listaDisponibilita (al massimo 1 alla volta)
        for(int key : listaDisponibilita.getSelectionModel().getSelection().keySet()){
            // Rimuovi disponibilità dalla lista dei contatti (ObservableList)
            disponibilita.remove(listaDisponibilita.getSelectionModel().getSelection().get(key));
        }
        // Rimuovi la precedente selezione
        listaDisponibilita.getSelectionModel().clearSelection();
    }

    /**
     * Mateodo callback richiamato da JavaFX al click del pulsante di inserimento disponiblità.
     * Raccoglie i dati contenuti nei campi e ne controlla il contenuto.
     * Se il contenuto non è valido (secondo i parametri impostati alla creazione più altri controlli locali al metodo), viene marcato il campo invalido
     * e non viene proseguita l'inserimento.
     * Se il contenuto è valido, viene inserita la disponibilità nella lista delle disponibilitò.
     *
     * @param actionEvent parametro evento JavaFX
     */
    @FXML
    private void onClickAggiungiDisponibilita(ActionEvent actionEvent) {
        try{
            // Controllo che la data di inizio disponibilità non sia successiva alla data di fine disponibilità
            if(inizioDisponibilitaValidated.getEpochDays()  >= fineDisponibilitaValidated.getEpochDays())
                throw new InvalidPeriodException(fineDisponibilitaValidated, "La data di fine è antecedente alla data di inizio");

            // Controllo che il periodo indicato dall'utente sia di almeno un mese (periodo minimo di disponibilità)
            if(fineDisponibilitaValidated.getEpochDays() <= inizioDisponibilitaValidated.getEpochDays() + DAYS_IN_MONTH)
                throw new InvalidPeriodException(fineDisponibilitaValidated, "La durata deve essere di almeno un mese");

            // Controllo dei conflitti con le disponibilità già presenti
            // Due disponibilità sono in conflitto se riguardano lo stesso comune e si sovrappongono per un certo periodo temporale

            // Per ogni disponibilità già presente
            for(Disponibilita disponibilita : listaDisponibilita.getItems()){
                // Se tale disponibilità riguarda lo stesso comune della disponibilità da inserire
                if(disponibilita.getComune() == comuneDisponibilita.getValue()){
                    // Se hanno un periodo in comune, viene lanciata una InputException, e viene impedito di aggiungerla alla lista delle disponibilità
                    if(inizioDisponibilitaValidated.getEpochDays() <= disponibilita.getFinePeriodo() && fineDisponibilitaValidated.getEpochDays() >= disponibilita.getInizioPeriodo())
                        throw new InvalidPeriodException(listaDisponibilitaValidated, "La disponibilità da inserire è in conflitto con quelle già inserite");
                }
            }

            // Viene creato una nuova disponibilità.
            // I vari metodi get dei decorator Validated richiamano checkValid() e
            // controllano lo stato attuale dell'elemento. Se non è valido viene lanciata
            // una InputException, e non viene terminato l'inserimento della disponibilità.
            Disponibilita nuovaDisponibilita = new Disponibilita(inizioDisponibilitaValidated.getEpochDays(),
                    fineDisponibilitaValidated.getEpochDays(),
                    comuneDisponibilitaValidated.getSelectedItem());

            disponibilita.add(nuovaDisponibilita);

            // Reset di tutti i campi del form delle disponibilità, per eliminare
            // eventuali errori precedenti
            inizioDisponibilitaValidated.reset();
            fineDisponibilitaValidated.reset();
            comuneDisponibilitaValidated.reset();
        }
        catch (InputException inputException){
            return;
        }
    }

    /**
     * Mateodo callback richiamato da JavaFX al click del pulsante di inserimento esperienza.
     * Raccoglie i dati contenuti nei campi e ne controlla il contenuto.
     * Se il contenuto non è valido (secondo i parametri impostati alla creazione più altri controlli locali al metodo), viene marcato il campo invalido
     * e non viene proseguita l'inserimento.
     * Se il contenuto è valido, viene inserita l'esperienza nella lista delle esperienze.
     *
     * @param actionEvent parametro evento JavaFX
     */
    @FXML
    private void onClickAggiungiEsperienza(ActionEvent actionEvent) {
        try {
            // Controllo che la data di inizio esperienza non sia successiva alla data di fine esperienza
            if(fineEsperienzaValidated.getEpochDays() <= inizioEsperienzaValidated.getEpochDays())
                throw new InvalidPeriodException(fineEsperienzaValidated, "La data di fine è antecedente alla data di inizio");

            // Controllo che il periodo indicato dall'utente sia di almeno un mese (periodo minimo di esperienza)
            if(fineEsperienzaValidated.getEpochDays() <= inizioEsperienzaValidated.getEpochDays() + DAYS_IN_MONTH)
                throw new InvalidPeriodException(fineEsperienzaValidated, "La durata deve essere di almeno un mese");

            // Controllo che l'esperienza abbia durata inferiore a 2 anni (limite massimo per un lavoro stagionale)
            if(fineEsperienzaValidated.getEpochDays() >= inizioEsperienzaValidated.getEpochDays() + 2 * DAYS_IN_YEAR)
                throw new InvalidPeriodException(fineEsperienzaValidated, "La durata deve essere di massimo 2 anni");

            // Viene creato una nuova esperienza.
            // I vari metodi get dei decorator Validated richiamano checkValid() e
            // controllano lo stato attuale dell'elemento. Se non è valido viene lanciata
            // una InputException, e non viene terminato l'inserimento dell'esperienza.
            Esperienza nuovaEsperienza = new Esperienza(-1,
                    aziendaEsperienzaValidated.getText(),
                    Integer.parseInt(retribuzioneEsperienzaValidated.getText()),
                    inizioEsperienzaValidated.getEpochDays(),
                    fineEsperienzaValidated.getEpochDays(),
                    comuneEsperienzaValidated.getSelectedItem(),
                    specializzazioneEsperienzaValidated.getSelectedItem());

            esperienze.add(nuovaEsperienza);

            // Reset di tutti i campi del form dell'esperienza, per eliminare
            // eventuali errori precedenti
            aziendaEsperienzaValidated.reset();
            retribuzioneEsperienzaValidated.reset();
            inizioEsperienzaValidated.reset();
            fineEsperienzaValidated.reset();
            comuneEsperienzaValidated.reset();
        }
        catch(InputException inputException) {
            return;
        }
    }

    /**
     * Mateodo callback richiamato da JavaFX al click del pulsante di eliminazione di un'esperienza.
     * Se è stato selezionata un'esperienza dalla lista, essa verrà cancellata, altrimenti ignora l'evento.
     *
     * @param actionEvent parametro evento JavaFX
     */
    @FXML
    private void onClickEliminaEsperienza(ActionEvent actionEvent) {
        // Per ogni elemento selezionato dall'utente su listaEsperienza (al massimo 1 alla volta)
        for(int key : listaEsperienze.getSelectionModel().getSelection().keySet()){
            // Rimuovi disponibilità dalla lista dei contatti (ObservableList)
            esperienze.remove(listaEsperienze.getSelectionModel().getSelection().get(key));
        }
        // Rimuovi la precedente selezione
        listaEsperienze.getSelectionModel().clearSelection();
    }
}
