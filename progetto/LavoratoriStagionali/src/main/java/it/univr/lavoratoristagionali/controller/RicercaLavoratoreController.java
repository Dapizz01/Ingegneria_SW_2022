package it.univr.lavoratoristagionali.controller;

import io.github.palexdev.materialfx.controls.*;
import io.github.palexdev.materialfx.utils.others.FunctionalStringConverter;
import it.univr.lavoratoristagionali.controller.enums.View;
import it.univr.lavoratoristagionali.controller.exception.InputException;
import it.univr.lavoratoristagionali.filters.*;
import it.univr.lavoratoristagionali.model.Dao.*;
import it.univr.lavoratoristagionali.types.*;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleGroup;

import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

/**
 * E' il controller che si occupa della ricerca dei lavoratori.
 */
public class RicercaLavoratoreController extends Controller implements Initializable {

    // Ritorno menu
    @FXML
    private MFXButton ritornaMenu;

    // ------ PARAMETRI RICERCA ------ //
    // Ricerca per comune
    @FXML
    private MFXCheckListView<Comune> comuneLavoratore;

    // Ricerca per lingua
    @FXML
    private MFXCheckListView<Lingua> lingueLavoratore;
    @FXML
    private MFXRadioButton lingueLavoratoreAND, lingueLavoratoreOR;
    private ToggleGroup lingueLavoratoreGroup;

    // Ricerca per disponibilità
    @FXML
    private MFXDatePicker inizioPeriodo, finePeriodo;
    @FXML
    private MFXFilterComboBox<Comune> comuneDisponibilita;
    @FXML
    private Label inizioPeriodoError, finePeriodoError, comuneDisponibilitaError;

    // Ricerca per patente
    @FXML
    private MFXCheckListView<Patente> patentiLavoratore;
    @FXML
    private MFXRadioButton patentiLavoratoreAND, patentiLavoratoreOR;
    private ToggleGroup patentiLavoratoreGroup;

    // Ricerca per specializzazione
    @FXML
    private MFXCheckListView<Specializzazione> specializzazioniLavoratore;
    @FXML
    private MFXRadioButton specializzazioniLavoratoreAND, specializzazioniLavoratoreOR;
    private ToggleGroup specializzazioniLavoratoreGroup;

    // Ricerca per automunito
    @FXML
    private MFXCheckbox automunito;

    // Ricerca per data di nascita
    @FXML
    private MFXDatePicker dataNascitaLavoratore;
    @FXML
    private MFXRadioButton dataNascitaLavoratoreDa, dataNascitaLavoratoreA;
    private ToggleGroup dataNascitaGroup;

    // Pulsanti ricerca
    @FXML
    private MFXButton ricercaAND, ricercaOR;
    
    // ------ RISULTATO RICERCA ------ //
    // Lista risultati
    @FXML
    private MFXListView<Lavoratore> listaLavoratori;
    @FXML
    private MFXButton visualizzaDettagli;

    // Parametri risultato
    @FXML
    private MFXTextField nomeRisultato, cognomeRisultato, telefonoRisultato, emailRisultato, dataNascitaRisultato, comuneNascitaRisultato, nazionalitaRisultato, comuneAbitazioneRisultato;
    @FXML
    private MFXCheckbox automunitoCheckRisultato;
    @FXML
    private MFXListView<Lingua> lingueRisultato;
    @FXML
    private MFXListView<Patente> patentiRisultato;
    @FXML
    private MFXListView<Esperienza> esperienzeRisultato;
    @FXML
    private MFXListView<Disponibilita> disponibilitaRisultato;
    @FXML
    private MFXListView<Contatto> contattiUrgentiRisultato;

    // ------ VARIABILI LOCALI CONTROLLER ------ //
    private AutomunitoFilter automunitoFilter;
    private ComuniFilter comuniFilter;
    private DataNascitaFilter dataNascitaFilter;
    private DisponibilitaFilter disponibilitaFilter;
    private LingueFilter lingueFilter;
    private PatentiFilter patentiFilter;
    private SpecializzazioniFilter specializzazioniFilter;
    private LavoratoriDao lavoratoriDao;


    /**
     * Metodo chiamato dopo la creazione della scena (e di tutti i suoi elementi) da parte di JavaFX.
     * Istanzia i DAO, riempe le liste e i campi a scelta multipla con i dati ottenuti dai DAO.
     *
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Creazione DAO
        ComuniDao comuniDao = new ComuniDaoImpl();
        LingueDao lingueDao = new LingueDaoImpl();
        PatentiDao patentiDao = new PatentiDaoImpl();
        SpecializzazioniDao specializzazioniDao = new SpecializzazioniDaoImpl();
        lavoratoriDao = new LavoratoriDaoImpl();

        // Salvataggio di tutti i dati dei DAO in delle liste
        List<Comune> comuni = comuniDao.getComuni();
        List<Lingua> lingue = lingueDao.getLingue();
        List<Patente> patenti = patentiDao.getPatenti();
        List<Specializzazione> specializzazioni = specializzazioniDao.getSpecializzazioni();

        // Popolazione dei campi a scelta multipla (o delle liste) con i dati salvati
        comuneLavoratore.setItems(FXCollections.observableArrayList(comuni));
        lingueLavoratore.setItems(FXCollections.observableArrayList(lingue));
        patentiLavoratore.setItems(FXCollections.observableArrayList(patenti));
        specializzazioniLavoratore.setItems(FXCollections.observableArrayList(specializzazioni));
        comuneDisponibilita.setItems(FXCollections.observableArrayList(comuni));

        // Gestione dei radio button in gruppi (in ogni gruppo solo un radio button può essere selezionato)
        lingueLavoratoreGroup = new ToggleGroup();
        patentiLavoratoreGroup = new ToggleGroup();
        specializzazioniLavoratoreGroup = new ToggleGroup();
        dataNascitaGroup = new ToggleGroup();

        lingueLavoratoreAND.setToggleGroup(lingueLavoratoreGroup);
        lingueLavoratoreOR.setToggleGroup(lingueLavoratoreGroup);
        patentiLavoratoreAND.setToggleGroup(patentiLavoratoreGroup);
        patentiLavoratoreOR.setToggleGroup(patentiLavoratoreGroup);
        specializzazioniLavoratoreAND.setToggleGroup(specializzazioniLavoratoreGroup);
        specializzazioniLavoratoreOR.setToggleGroup(specializzazioniLavoratoreGroup);
        dataNascitaLavoratoreDa.setToggleGroup(dataNascitaGroup);
        dataNascitaLavoratoreA.setToggleGroup(dataNascitaGroup);

        // Ogni radio button "AND" viene selezionato di default
        lingueLavoratoreGroup.selectToggle(lingueLavoratoreAND);
        patentiLavoratoreGroup.selectToggle(patentiLavoratoreAND);
        specializzazioniLavoratoreGroup.selectToggle(specializzazioniLavoratoreAND);
        dataNascitaGroup.selectToggle(dataNascitaLavoratoreDa);

        // Impostazione del format di visualizzazione di ogni lavoratore nella lista dei risultati della ricerca
        listaLavoratori.setConverter(FunctionalStringConverter.to(lavoratore -> {
            if(lavoratore != null)
                return lavoratore.getNomeLavoratore() +
                        " " + lavoratore.getCognomeLavoratore() +
                        ", nato il " + LocalDate.ofEpochDay(lavoratore.getDataNascita()) +
                        " a " + lavoratore.getComuneNascita();
            return "";
        }));
    }

    /**
     * Evento generato da JavaFX, al click del pulsante di ritorno al menu.
     * Sostituisce la scena corrente con la scena del menu principale.
     *
     * @param actionEvent parametro evento JavaFX
     */
    public void onClickRitornaMenu(ActionEvent actionEvent) {
        switchScene(getStageFromEvent(actionEvent), View.MAIN_MENU);
    }

    /**
     * Evento generato da JavaFX, al click del pulsante di ricerca con operatore globale AND.
     * Prende i valori di tutti i filtri, li manda al DAO opportuno con operatore globale AND e mostra il risultato nella lista sottostante.
     *
     * @param actionEvent parametro evento JavaFX
     */
    public void onClickRicercaAND(ActionEvent actionEvent) {
        try{
            refreshFilters();
            clearResultFields();
            /* System.out.println(patentiFilter);
            System.out.println(specializzazioniFilter); */
            listaLavoratori.setItems(FXCollections.observableArrayList(lavoratoriDao.searchLavoratori(lingueFilter, comuniFilter, patentiFilter, specializzazioniFilter, automunitoFilter, disponibilitaFilter, dataNascitaFilter, Flag.AND)));
        }
        catch (InputException inputException){
            return;
        }
    }

    /**
     * Evento generato da JavaFX, al click del pulsante di ricerca con operatore globale OR.
     * Prende i valori di tutti i filtri, li manda al DAO opportuno con operatore globale AND e mostra il risultato nella lista sottostante.
     *
     * @param actionEvent parametro evento JavaFX
     */
    public void onClickRicercaOR(ActionEvent actionEvent) {
        try{
            refreshFilters();
            clearResultFields();
            /* System.out.println(patentiFilter);
            System.out.println(specializzazioniFilter); */
            listaLavoratori.setItems(FXCollections.observableArrayList(lavoratoriDao.searchLavoratori(lingueFilter, comuniFilter, patentiFilter, specializzazioniFilter, automunitoFilter, disponibilitaFilter, dataNascitaFilter, Flag.OR)));
        }
        catch (InputException inputException){
            return;
        }
    }

    /**
     * Evento generato da JavaFX, al click del pulsante di visualizzazione nel dettaglio di un lavoratore nella lista dei risultati.
     * Popola i campi a destra della lista dei risultati con i dati del lavoratore selezionato nella lista.
     *
     * @param actionEvent parametro evento JavaFX
     */
    public void onClickVisualizzaDettagli(ActionEvent actionEvent) {
        /* if(listaLavoratori.getSelectionModel().getSelectedValues() != null)
            switchScene(getStageFromEvent(actionEvent), View.DETTAGLI_RICERCA_LAVORATORE, listaLavoratori.getSelectionModel().getSelectedValues().get(0)); */
        // Non è necessario un ciclo, ci sarà sempre al massimo un lavoratore selezionato, ma
        // JavaFX restituisce il risultato come una Map, quindi è necessario scorrere le chiavi
        for(int lavoratoreIndex : listaLavoratori.getSelectionModel().getSelection().keySet()){
            Lavoratore lavoratore = listaLavoratori.getSelectionModel().getSelection().get(lavoratoreIndex);
            nomeRisultato.setText(lavoratore.getNomeLavoratore());
            cognomeRisultato.setText(lavoratore.getCognomeLavoratore());
            dataNascitaRisultato.setText(LocalDate.ofEpochDay(Long.parseLong(Integer.toString(lavoratore.getDataNascita()))).toString());
            comuneAbitazioneRisultato.setText(lavoratore.getComuneAbitazione().toString());
            comuneNascitaRisultato.setText(lavoratore.getComuneNascita().toString());
            nazionalitaRisultato.setText(lavoratore.getNazionalita().toString());
            telefonoRisultato.setText(lavoratore.getTelefono());
            emailRisultato.setText(lavoratore.getEmail());
            automunitoCheckRisultato.setSelected(lavoratore.isAutomunito());

            lingueRisultato.setItems(FXCollections.observableArrayList(lavoratore.getLingue()));
            patentiRisultato.setItems(FXCollections.observableArrayList(lavoratore.getPatenti()));
            contattiUrgentiRisultato.setItems(FXCollections.observableArrayList(lavoratore.getContatti()));
            disponibilitaRisultato.setItems(FXCollections.observableArrayList(lavoratore.getDisponibilita()));
            esperienzeRisultato.setItems(FXCollections.observableArrayList(lavoratore.getEsperienze()));
        }
    }

    /**
     * Aggiorna i filter locali del controller con quelli modificati nella vista dall'utente.
     */
    private void refreshFilters() throws InputException{
        automunitoFilter = new AutomunitoFilter(automunito.isSelected());
        comuniFilter = new ComuniFilter(comuneLavoratore.getSelectionModel().getSelectedValues(), Flag.OR);
        dataNascitaFilter = new DataNascitaFilter(getEpochDays(dataNascitaLavoratore),
                (dataNascitaGroup.getSelectedToggle().equals(dataNascitaLavoratoreDa)) ? Flag.FROM : Flag.TO);

        resetErrorLabels();
        if(getEpochDays(inizioPeriodo) == -1 && getEpochDays(finePeriodo) == -1 && comuneDisponibilita.getSelectedItem() == null)
            disponibilitaFilter = new DisponibilitaFilter(-1, -1, null);
        else if(getEpochDays(inizioPeriodo) != -1 && getEpochDays(finePeriodo) != -1 && comuneDisponibilita.getSelectedItem() != null)
            disponibilitaFilter = new DisponibilitaFilter(getEpochDays(inizioPeriodo), getEpochDays(finePeriodo), comuneDisponibilita.getSelectedItem());
        else{
            if(getEpochDays(inizioPeriodo) == -1)
                throw new InputException(inizioPeriodoError, "Indicare inizio periodo");
            else if (getEpochDays(finePeriodo) == -1)
                throw new InputException(finePeriodoError, "Indicare fine periodo");
            else
                throw new InputException(comuneDisponibilitaError, "Selezionare un comune");
        }

        lingueFilter = new LingueFilter(lingueLavoratore.getSelectionModel().getSelectedValues(),
                (lingueLavoratoreGroup.getSelectedToggle().equals(lingueLavoratoreAND)) ? Flag.AND : Flag.OR);
        patentiFilter = new PatentiFilter(patentiLavoratore.getSelectionModel().getSelectedValues(),
                (patentiLavoratoreGroup.getSelectedToggle().equals(patentiLavoratoreAND)) ? Flag.AND : Flag.OR);
        specializzazioniFilter = new SpecializzazioniFilter(specializzazioniLavoratore.getSelectionModel().getSelectedValues(),
                (specializzazioniLavoratoreGroup.getSelectedToggle().equals(specializzazioniLavoratoreAND)) ? Flag.AND : Flag.OR);
    }

    /**
     * Pulisce i campi che mostrano nel dettaglio un lavoratore
     */
    private void clearResultFields(){
        nomeRisultato.clear();
        cognomeRisultato.clear();
        dataNascitaRisultato.clear();
        comuneNascitaRisultato.clear();
        comuneAbitazioneRisultato.clear();
        nazionalitaRisultato.clear();
        telefonoRisultato.clear();
        emailRisultato.clear();
        automunito.setSelected(false);
        patentiRisultato.setItems(FXCollections.observableArrayList());
        lingueRisultato.setItems(FXCollections.observableArrayList());
        contattiUrgentiRisultato.setItems(FXCollections.observableArrayList());
        disponibilitaRisultato.setItems(FXCollections.observableArrayList());
        esperienzeRisultato.setItems(FXCollections.observableArrayList());
    }

    /**
     * Dato un MFXDatePicker ritorna il numero di giorni dalla Unix epoch (01/01/1970)
     *
     * @param datePicker è un oggetto di tipo MFXDatePicker
     * @return il numero di giorni dalla Unix epoch (oppure -1 se il campo non ha valore)
     */
    private int getEpochDays(MFXDatePicker datePicker){
        return (datePicker.getValue() != null) ? (int) datePicker.getValue().toEpochDay() : -1;
    }

    private void resetErrorLabels(){
        inizioPeriodoError.setVisible(false);
        finePeriodoError.setVisible(false);
        comuneDisponibilitaError.setVisible(false);
    }
}
