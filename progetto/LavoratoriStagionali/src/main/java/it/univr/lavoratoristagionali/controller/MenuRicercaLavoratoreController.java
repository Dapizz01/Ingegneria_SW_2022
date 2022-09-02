package it.univr.lavoratoristagionali.controller;

import io.github.palexdev.materialfx.controls.*;
import io.github.palexdev.materialfx.utils.others.FunctionalStringConverter;
import it.univr.lavoratoristagionali.controller.enums.View;
import it.univr.lavoratoristagionali.filters.*;
import it.univr.lavoratoristagionali.model.Dao.*;
import it.univr.lavoratoristagionali.types.*;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.ToggleGroup;

import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

public class MenuRicercaLavoratoreController extends Controller implements Initializable {

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


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ComuniDao comuniDao = new ComuniDaoImpl();
        LingueDao lingueDao = new LingueDaoImpl();
        PatentiDao patentiDao = new PatentiDaoImpl();
        SpecializzazioniDao specializzazioniDao = new SpecializzazioniDaoImpl();
        lavoratoriDao = new LavoratoriDaoImpl();

        List<Comune> comuni = comuniDao.getComuni();
        List<Lingua> lingue = lingueDao.getLingue();
        List<Patente> patenti = patentiDao.getPatenti();
        List<Specializzazione> specializzazioni = specializzazioniDao.getSpecializzazioni();

        comuneLavoratore.setItems(FXCollections.observableArrayList(comuni));
        lingueLavoratore.setItems(FXCollections.observableArrayList(lingue));
        patentiLavoratore.setItems(FXCollections.observableArrayList(patenti));
        specializzazioniLavoratore.setItems(FXCollections.observableArrayList(specializzazioni));
        comuneDisponibilita.setItems(FXCollections.observableArrayList(comuni));

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

        lingueLavoratoreGroup.selectToggle(lingueLavoratoreAND);
        patentiLavoratoreGroup.selectToggle(patentiLavoratoreAND);
        specializzazioniLavoratoreGroup.selectToggle(specializzazioniLavoratoreAND);
        dataNascitaGroup.selectToggle(dataNascitaLavoratoreDa);

        listaLavoratori.setConverter(FunctionalStringConverter.to(lavoratore -> {
            if(lavoratore != null)
                return lavoratore.getNomeLavoratore() +
                        " " + lavoratore.getCognomeLavoratore() +
                        ", nato il " + LocalDate.ofEpochDay(lavoratore.getDataNascita()) +
                        " a " + lavoratore.getComuneNascita();
            return "";
        }));

        // clearRisultato();
    }

    public void onClickRitornaMenu(ActionEvent actionEvent) {
        switchScene(getStageFromEvent(actionEvent), View.MAIN_MENU);
    }

    public void onClickRicercaAND(ActionEvent actionEvent) {
        refreshFilters();
        listaLavoratori.setItems(FXCollections.observableArrayList(lavoratoriDao.getLavoratori("Mirko", "DeMarchi")));
    }

    public void onClickRicercaOR(ActionEvent actionEvent) {
        refreshFilters();
        listaLavoratori.setItems(FXCollections.observableArrayList(lavoratoriDao.getLavoratori("Mirko", "DeMarchi")));
    }

    public void onClickVisualizzaDettagli(ActionEvent actionEvent) {
        /* if(listaLavoratori.getSelectionModel().getSelectedValues() != null)
            switchScene(getStageFromEvent(actionEvent), View.DETTAGLI_RICERCA_LAVORATORE, listaLavoratori.getSelectionModel().getSelectedValues().get(0)); */

        if(!listaLavoratori.getSelectionModel().getSelectedValues().isEmpty()){
            Lavoratore lavoratore = listaLavoratori.getSelectionModel().getSelection().get(0);
            nomeRisultato.setText(lavoratore.getNomeLavoratore());
            cognomeRisultato.setText(lavoratore.getCognomeLavoratore());
            dataNascitaRisultato.setText(Integer.toString(lavoratore.getDataNascita()));
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

    private void refreshFilters(){
        automunitoFilter = new AutomunitoFilter(automunito.isSelected());
        comuniFilter = new ComuniFilter(comuneLavoratore.getSelectionModel().getSelectedValues(), Flag.OR);
        dataNascitaFilter = new DataNascitaFilter(getEpochDays(dataNascitaLavoratore),
                (dataNascitaGroup.getSelectedToggle().equals(dataNascitaLavoratoreDa)) ? Flag.FROM : Flag.TO);
        disponibilitaFilter = new DisponibilitaFilter(getEpochDays(inizioPeriodo), getEpochDays(finePeriodo), comuneDisponibilita.getSelectedItem());
        lingueFilter = new LingueFilter(lingueLavoratore.getSelectionModel().getSelectedValues(),
                (lingueLavoratoreGroup.getSelectedToggle().equals(lingueLavoratoreAND)) ? Flag.AND : Flag.OR);
        patentiFilter = new PatentiFilter(patentiLavoratore.getSelectionModel().getSelectedValues(),
                (patentiLavoratoreGroup.getSelectedToggle().equals(patentiLavoratoreAND)) ? Flag.AND : Flag.OR);
        specializzazioniFilter = new SpecializzazioniFilter(specializzazioniLavoratore.getSelectionModel().getSelectedValues(),
                (specializzazioniLavoratoreGroup.getSelectedToggle().equals(specializzazioniLavoratoreAND)) ? Flag.AND : Flag.OR);
    }

    private int getEpochDays(MFXDatePicker datePicker){
        return (datePicker.getValue() != null) ? (int) datePicker.getValue().toEpochDay() : -1;
    }
}
