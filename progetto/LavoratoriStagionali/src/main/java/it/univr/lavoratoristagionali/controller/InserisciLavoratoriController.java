package it.univr.lavoratoristagionali.controller;

import io.github.palexdev.materialfx.controls.*;
import io.github.palexdev.materialfx.controls.cell.MFXListCell;
import it.univr.lavoratoristagionali.types.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class InserisciLavoratoriController extends Controller implements Initializable {
    @FXML
    private MFXCheckListView<Patente> patentiPossedute;

    @FXML
    private MFXCheckListView<Lingua> lingueParlate, nazionalitaLavoratore;

    @FXML
    private MFXListView<Contatto> listaContattoUrgente;

    @FXML
    private MFXDatePicker dataNascita, inizioPeriodoDisponibilita, finePeriodoDisponibilita, inizioPeriodoEsperienza, finePeriodoEsperienza;

    @FXML
    private MFXTextField nomeTextField, cognomeTextField, telefonoTextField, indirizzoTextField, emailTextField, nomeContattoUrgente, cognomeContattoUrgente, telefonoContattoUrgente, emailContattoUrgente, retribuzioneEsperienza, aziendaEsperienza;

    @FXML
    private MFXFilterComboBox<Comune> comuneNascita, comuneDisponibilita, comuneEsperienza;

    @FXML
    private Text anagraficaTitle, contattiTitle, informazioniGeneraliTitle, automunitoTitle;

    @FXML
    private MFXButton indietroButton, inviaLavoratoreButton, contattoUrgenteButton, eliminaContattoButton, eliminaDisponibilitaButton, aggiungiDisponibilitaButton, aggiungiEsperienzaButton, eliminaEsperienzaButton;

    @FXML
    private MFXCheckbox automunitoCheckBox;

    @FXML
    private MFXListView<Disponibilita> listaDisponibilita;

    @FXML
    private MFXListView<Esperienza> listaEsperienze;

    private ObservableList<Contatto> contatti;

    public InserisciLavoratoriController(){

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        List<Lingua> lingue = List.of(new Lingua(1, "lingua1"), new Lingua(2, "lingua2"), new Lingua(3, "lingua3"));
        List<Comune> comuni = List.of(new Comune(1, "comune1"), new Comune(2, "comune2"), new Comune(3, "comune3"));
        List<Lingua> nazionalita = List.of(new Lingua(1, "nazionalità1"), new Lingua(2, "nazionalità2"), new Lingua(3, "nazionalità3"));
        List<Patente> patenti = List.of(new Patente(1, "patente1"), new Patente(2, "patente2"), new Patente(3, "patente3"));
        // List<String> comuni = List.of("comune1", "comune2", "comune3");
        comuneNascita.setItems(FXCollections.observableArrayList(comuni));
        lingueParlate.setItems(FXCollections.observableArrayList(lingue));
        nazionalitaLavoratore.setItems(FXCollections.observableArrayList(nazionalita));
        patentiPossedute.setItems(FXCollections.observableArrayList(patenti));
        // lingua.setItems(strings);

        /*listaContattoUrgente.setCellFactory(param -> new MFXListCell<Contatto>(){

        });*/

        contatti = FXCollections.observableArrayList(new ArrayList<>());
        listaContattoUrgente.setItems(contatti);
    }

    @FXML
    private void ritornaMenu(ActionEvent actionEvent){
        System.out.println("ritornaMenu fired");
        switchScene(getStageFromEvent(actionEvent), View.MAIN_MENU);
    }

    @FXML
    private void inviaLavoratore(ActionEvent actionEvent){
        System.out.println(lingueParlate.getSelectionModel().getSelectedValues());
        System.out.println(nazionalitaLavoratore.getSelectionModel().getSelectedValues());
        System.out.println(patentiPossedute.getSelectionModel().getSelectedValues());
        System.out.println(comuneNascita.getSelectionModel().getSelectedItem());
    }

    @FXML
    private void onClickUrgenteButton(ActionEvent actionEvent) {
        Contatto nuovoContatto = new Contatto(-1, nomeContattoUrgente.getText(), cognomeContattoUrgente.getText(), telefonoContattoUrgente.getText(), emailContattoUrgente.getText());
        contatti.add(nuovoContatto);

        nomeContattoUrgente.clear();
        cognomeContattoUrgente.clear();
        telefonoContattoUrgente.clear();
        emailContattoUrgente.clear();
    }

    @FXML
    private void testEvent(MouseEvent mouseEvent) {
        // Se è stato cliccato un label del ListView
        if(mouseEvent.getTarget() instanceof Text) {
            /*Contatto contattoSelezionato = listaContattoUrgente.getSelectionModel().getSelectedValues().get(0);
            // System.out.println(contattoSelezionato);
            nomeContattoUrgente.setText(contattoSelezionato.getNome());
            cognomeContattoUrgente.setText(contattoSelezionato.getCognome());
            telefonoContattoUrgente.setText(contattoSelezionato.getTelefono());
            emailContattoUrgente.setText(contattoSelezionato.getEmail());
            ObservableMap<Integer, Contatto> map = listaContattoUrgente.getSelectionModel().getSelection();
            System.out.println(map);*/
        }
    }

    @FXML
    private void eliminaContatto(ActionEvent actionEvent){
        for(int key : listaContattoUrgente.getSelectionModel().getSelection().keySet()){
            contatti.remove(listaContattoUrgente.getSelectionModel().getSelection().get(key));
        }
        listaContattoUrgente.getSelectionModel().clearSelection();
        System.out.println(contatti);
    }

    @FXML
    private void eliminaDisponibilita(ActionEvent actionEvent) {
    }

    @FXML
    private void aggiungiDisponibilita(ActionEvent actionEvent) {
    }

    @FXML
    private void aggiungiEsperienza(ActionEvent actionEvent) {
    }

    @FXML
    private void eliminaEsperienza(ActionEvent actionEvent) {
    }
}
