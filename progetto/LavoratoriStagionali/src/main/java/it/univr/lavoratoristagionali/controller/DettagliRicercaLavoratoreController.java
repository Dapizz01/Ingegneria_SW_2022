package it.univr.lavoratoristagionali.controller;

import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXCheckbox;
import io.github.palexdev.materialfx.controls.MFXListView;
import io.github.palexdev.materialfx.controls.MFXTextField;
import it.univr.lavoratoristagionali.controller.enums.View;
import it.univr.lavoratoristagionali.types.*;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class DettagliRicercaLavoratoreController extends Controller implements Initializable {
    @FXML
    private MFXTextField nome, cognome, dataNascita, nazionalita, comuneNascita, comuneAbitazione, telefono, email;
    @FXML
    private MFXCheckbox automunitoCheck;
    @FXML
    private MFXListView<Lingua> lingue;
    @FXML
    private MFXListView<Patente> patenti;
    @FXML
    private MFXListView<Contatto> contattiUrgenti;
    @FXML
    private MFXListView<Disponibilita> disponibilita;
    @FXML
    private MFXListView<Esperienza> esperienze;
    @FXML
    private MFXButton ritornaMenu;

    public DettagliRicercaLavoratoreController(){

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void setLavoratoreBase(Lavoratore lavoratore){
        nome.setText(lavoratore.getNomeLavoratore());
        cognome.setText(lavoratore.getCognomeLavoratore());
        dataNascita.setText(Integer.toString(lavoratore.getDataNascita()));
        comuneAbitazione.setText(lavoratore.getComuneAbitazione().toString());
        comuneNascita.setText(lavoratore.getComuneNascita().toString());
        nazionalita.setText(lavoratore.getNazionalita().toString());
        telefono.setText(lavoratore.getTelefono());
        email.setText(lavoratore.getEmail());
        automunitoCheck.setSelected(lavoratore.isAutomunito());

        lingue.setItems(FXCollections.observableArrayList(lavoratore.getLingue()));
        patenti.setItems(FXCollections.observableArrayList(lavoratore.getPatenti()));
        contattiUrgenti.setItems(FXCollections.observableArrayList(lavoratore.getContatti()));
        disponibilita.setItems(FXCollections.observableArrayList(lavoratore.getDisponibilita()));
        esperienze.setItems(FXCollections.observableArrayList(lavoratore.getEsperienze()));
    }

    @FXML
    private void onClickRitornaMenu(ActionEvent actionEvent){
        switchScene(getStageFromEvent(actionEvent), View.MENU_RICERCA_LAVORATORE);
    }
}
