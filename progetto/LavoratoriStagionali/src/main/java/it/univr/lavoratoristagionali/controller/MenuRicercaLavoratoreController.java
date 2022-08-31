package it.univr.lavoratoristagionali.controller;

import io.github.palexdev.materialfx.controls.*;
import it.univr.lavoratoristagionali.types.Lavoratore;
import it.univr.lavoratoristagionali.types.Lingua;
import it.univr.lavoratoristagionali.types.Patente;
import it.univr.lavoratoristagionali.types.Specializzazione;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class MenuRicercaLavoratoreController extends Controller implements Initializable {
    
    // Ricerca per comune
    @FXML
    private MFXListView comuneLavoratore;

    // Ricerca per lingua
    @FXML
    private MFXCheckListView<Lingua> lingueLavoratore;
    @FXML
    private MFXRadioButton lingueLavoratoreAND, lingueLavoratoreOR;

    // Ricerca per periodo
    @FXML
    private MFXDatePicker inizioPeriodo, finePeriodo;

    // Ricerca per patente
    @FXML
    private MFXCheckListView<Patente> patentiLavoratore;
    @FXML
    private MFXRadioButton patentiLavoratoreAND, patentiLavoratoreOR;

    // Ricerca per specializzazione
    @FXML
    private MFXCheckListView<Specializzazione> specializzazioniLavoratore;
    @FXML
    private MFXRadioButton specializzazioniLavoratoreAND, specializzazioniLavoratoreOR;

    // Ricerca per automunito
    @FXML
    private MFXCheckbox automunito;

    // Ricerca per data di nascita
    @FXML
    private MFXDatePicker dataNascitaLavoratore;
    @FXML
    private MFXRadioButton dataNascitaLavoratoreDa, dataNascitaLavoratoreA;

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

    }

    public void onClickRitornaMenu(ActionEvent actionEvent) {
    }

    public void onClickRicercaAND(ActionEvent actionEvent) {
    }

    public void onClickRicercaOR(ActionEvent actionEvent) {
    }

    public void onClickVisualizzaDettagli(ActionEvent actionEvent) {
    }
}
