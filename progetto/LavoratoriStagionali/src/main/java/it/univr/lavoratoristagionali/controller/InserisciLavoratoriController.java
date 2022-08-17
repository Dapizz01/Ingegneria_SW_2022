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
    private MFXDatePicker dataNascita;

    @FXML
    private MFXTextField nomeTextField, cognomeTextField, telefonoTextField, indirizzoTextField, emailTextField, nomeContattoUrgente, cognomeContattoUrgente, telefonoContattoUrgente, emailContattoUrgente;

    @FXML
    private MFXFilterComboBox<Comune> comuneNascita;

    @FXML
    private Text anagraficaTitle, contattiTitle, informazioniGeneraliTitle, automunitoTitle;

    @FXML
    private MFXButton indietroButton, inviaLavoratoreButton, contattoUrgenteButton;

    @FXML
    private MFXCheckbox automunitoCheckBox;

    private List<Contatto> contatti;

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

        contatti = new ArrayList<>();
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
        // System.out.println(new Contatto(0, nomeContattoUrgente.getText(), cognomeContattoUrgente.getText(), telefonoContattoUrgente.getText(), emailContattoUrgente.getText()));
        Contatto nuovoContatto = new Contatto(-1, nomeContattoUrgente.getText(), cognomeContattoUrgente.getText(), telefonoContattoUrgente.getText(), emailContattoUrgente.getText());
        contatti.add(nuovoContatto);
        listaContattoUrgente.setItems(FXCollections.observableArrayList(contatti));

        // Funzionamento:
            // 1. Utente inserisce dati
            // 2. Se utente seleziona un contatto dalla lista dei contatti
                // 2.1. Entra in modalità di modifica, cambia il label del tasto in "aggiorna contatto" e i campi vengono riempiti con i dati del contatto
                // 2.2. Una volta che l'utente clicca "aggiorna contatto", si torna alla modalità di modifica, rimuovendo la selezione dalla lista dei contatti e svuotando i campi.
        // Osservazioni: è necessario dare un ID temporaneo ai contatti (NON USATO NEL MODEL)
    }

    @FXML
    private void testEvent(MouseEvent mouseEvent) {
        System.out.println(mouseEvent);
    }
}
