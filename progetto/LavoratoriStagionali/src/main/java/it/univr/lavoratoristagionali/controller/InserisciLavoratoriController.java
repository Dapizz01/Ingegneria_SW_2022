package it.univr.lavoratoristagionali.controller;

import io.github.palexdev.materialfx.controls.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

public class InserisciLavoratoriController extends Controller implements Initializable {
    @FXML
    private MFXCheckListView<String> nazionalita, lingueParlate, patenti;

    @FXML
    private MFXDatePicker dataNascita;

    @FXML
    private MFXTextField nomeTextField, cognomeTextField, telefonoTextField, indirizzoTextField, emailTextField;

    @FXML
    private MFXCheckListView<String> lingua; // TODO: rinominare e spostare questo elemento su "informazioni generali"

    @FXML
    private MFXFilterComboBox<String> comuneNascita;

    @FXML
    private Text anagraficaTitle, contattiTitle, informazioniGeneraliTitle, automunitoTitle;

    @FXML
    private MFXButton indietroButton;

    @FXML
    private MFXCheckbox automunitoCheckbox;

    public InserisciLavoratoriController(){

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<String> strings = FXCollections.observableArrayList("test1", "test2", "test3", "test4", "test5", "test6", "test7", "test8", "test9", "test10");
        comuneNascita.setItems(strings);
        lingua.setItems(strings);
    }

    @FXML
    private void ritornaMenu(ActionEvent actionEvent){
        System.out.println("ritornaMenu fired");
        switchScene(getStageFromEvent(actionEvent), View.MAIN_MENU);
    }
}
