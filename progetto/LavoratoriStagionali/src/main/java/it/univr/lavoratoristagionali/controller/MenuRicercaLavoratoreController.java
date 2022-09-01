package it.univr.lavoratoristagionali.controller;

import io.github.palexdev.materialfx.controls.*;
import it.univr.lavoratoristagionali.controller.enums.View;
import it.univr.lavoratoristagionali.filters.*;
import it.univr.lavoratoristagionali.model.Dao.*;
import it.univr.lavoratoristagionali.types.*;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ToggleGroup;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class MenuRicercaLavoratoreController extends Controller implements Initializable {
    
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

    // Lista risultati
    @FXML
    private MFXListView<Lavoratore> listaLavoratori;
    @FXML
    private MFXButton visualizzaDettagli;
    
    // Ritorno menu
    @FXML
    private MFXButton ritornaMenu;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ComuniDao comuniDao = new ComuniDaoImpl();
        LingueDao lingueDao = new LingueDaoImpl();
        PatentiDao patentiDao = new PatentiDaoImpl();
        SpecializzazioniDao specializzazioniDao = new SpecializzazioniDaoImpl();

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
    }

    public void onClickRitornaMenu(ActionEvent actionEvent) {
        switchScene(getStageFromEvent(actionEvent), View.MAIN_MENU);
    }

    public void onClickRicercaAND(ActionEvent actionEvent) {
        AutomunitoFilter automunitoFilter = new AutomunitoFilter(automunito.isSelected());
        ComuniFilter comuniFilter = new ComuniFilter(comuneLavoratore.getSelectionModel().getSelectedValues(), Flag.OR);
        DataNascitaFilter dataNascitaFilter = new DataNascitaFilter(getEpochDays(dataNascitaLavoratore),
                (dataNascitaGroup.getSelectedToggle().equals(dataNascitaLavoratoreDa)) ? Flag.FROM : Flag.TO);
        DisponibilitaFilter disponibilitaFilter = new DisponibilitaFilter(getEpochDays(inizioPeriodo), getEpochDays(finePeriodo), comuneDisponibilita.getSelectedItem());
        LingueFilter lingueFilter = new LingueFilter(lingueLavoratore.getSelectionModel().getSelectedValues(),
                (lingueLavoratoreGroup.getSelectedToggle().equals(lingueLavoratoreAND)) ? Flag.AND : Flag.OR);
        PatentiFilter patentiFilter = new PatentiFilter(patentiLavoratore.getSelectionModel().getSelectedValues(),
                (patentiLavoratoreGroup.getSelectedToggle().equals(patentiLavoratoreAND)) ? Flag.AND : Flag.OR);
        SpecializzazioniFilter specializzazioniFilter = new SpecializzazioniFilter(specializzazioniLavoratore.getSelectionModel().getSelectedValues(),
                (specializzazioniLavoratoreGroup.getSelectedToggle().equals(specializzazioniLavoratoreAND)) ? Flag.AND : Flag.OR);


    }

    public void onClickRicercaOR(ActionEvent actionEvent) {
    }

    public void onClickVisualizzaDettagli(ActionEvent actionEvent) {
    }

    private int getEpochDays(MFXDatePicker datePicker){
        return (datePicker.getValue() != null) ? (int) datePicker.getValue().toEpochDay() : -1;
    }
}
