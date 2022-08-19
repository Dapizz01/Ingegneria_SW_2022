package it.univr.lavoratoristagionali.controller;

import io.github.palexdev.materialfx.controls.*;
import io.github.palexdev.materialfx.controls.cell.MFXListCell;
import io.github.palexdev.materialfx.validation.Constraint;
import io.github.palexdev.materialfx.validation.Severity;
import it.univr.lavoratoristagionali.types.*;
import javafx.beans.binding.Bindings;
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

    private ObservableList<Esperienza> esperienze;

    private ObservableList<Disponibilita> disponibilita;

    public InserisciLavoratoriController(){

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        List<Lingua> lingue = List.of(new Lingua(1, "lingua1"), new Lingua(2, "lingua2"), new Lingua(3, "lingua3"));
        List<Comune> comuni = List.of(new Comune(1, "comune1"), new Comune(2, "comune2"), new Comune(3, "comune3"));
        List<Lingua> nazionalita = List.of(new Lingua(1, "nazionalità1"), new Lingua(2, "nazionalità2"), new Lingua(3, "nazionalità3"));
        List<Patente> patenti = List.of(new Patente(1, "patente1"), new Patente(2, "patente2"), new Patente(3, "patente3"));

        comuneNascita.setItems(FXCollections.observableArrayList(comuni));
        lingueParlate.setItems(FXCollections.observableArrayList(lingue));
        nazionalitaLavoratore.setItems(FXCollections.observableArrayList(nazionalita));
        patentiPossedute.setItems(FXCollections.observableArrayList(patenti));
        comuneEsperienza.setItems(FXCollections.observableArrayList(comuni));
        comuneDisponibilita.setItems(FXCollections.observableArrayList(comuni));
        // lingua.setItems(strings);

        /*listaContattoUrgente.setCellFactory(param -> new MFXListCell<Contatto>(){

        });*/

        contatti = FXCollections.observableArrayList(new ArrayList<>());
        listaContattoUrgente.setItems(contatti);

        esperienze = FXCollections.observableArrayList(new ArrayList<>());
        listaEsperienze.setItems(esperienze);

        disponibilita = FXCollections.observableArrayList(new ArrayList<>());
        listaDisponibilita.setItems(disponibilita);

        buildTextFieldValidator(nomeTextField, Errore.LETTERS_ONLY, Errore.NON_EMPTY);
        // TODO: aggiungere stili css errori con label di errore associata
        // Link utili: https://github.com/palexdev/MaterialFX/blob/main/demo/src/main/java/io/github/palexdev/materialfx/demo/controllers/TextFieldsController.java
        // https://github.com/palexdev/MaterialFX/blob/main/demo/src/main/resources/io/github/palexdev/materialfx/demo/css/TextFields.css
    }

    @FXML
    private void ritornaMenu(ActionEvent actionEvent){
        System.out.println("ritornaMenu fired");
        switchScene(getStageFromEvent(actionEvent), View.MAIN_MENU);
    }

    @FXML
    private void inviaLavoratore(ActionEvent actionEvent){
        /*System.out.println(lingueParlate.getSelectionModel().getSelectedValues());
        System.out.println(nazionalitaLavoratore.getSelectionModel().getSelectedValues());
        System.out.println(patentiPossedute.getSelectionModel().getSelectedValues());
        System.out.println(comuneNascita.getSelectionModel().getSelectedItem());*/


        // TODO: aggiungere comune di abitazione
        Lavoratore lavoratore = new Lavoratore(-1, nomeTextField.getText(), cognomeTextField.getText(), comuneNascita.getValue(), null, (int) dataNascita.getValue().toEpochDay(), nazionalitaLavoratore.getSelectionModel().getSelection().get(0), emailTextField.getText(), telefonoTextField.getText(), automunitoCheckBox.isSelected(), listaEsperienze.getItems(), lingueParlate.getItems(), listaContattoUrgente.getItems(), patentiPossedute.getItems(), listaDisponibilita.getItems());
        System.out.println(lavoratore);
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
        for(int key : listaDisponibilita.getSelectionModel().getSelection().keySet()){
            disponibilita.remove(listaDisponibilita.getSelectionModel().getSelection().get(key));
        }
        listaDisponibilita.getSelectionModel().clearSelection();
        // System.out.println(contatti);
    }

    @FXML
    private void aggiungiDisponibilita(ActionEvent actionEvent) {
        Disponibilita nuovaDisponibilita = new Disponibilita((int) inizioPeriodoDisponibilita.getValue().toEpochDay(), (int) finePeriodoDisponibilita.getValue().toEpochDay(), comuneDisponibilita.getValue());
        disponibilita.add(nuovaDisponibilita);

        System.out.println(disponibilita);
        System.out.println(disponibilita.get(0).getInizioPeriodo());
        System.out.println(disponibilita.get(0).getFinePeriodo());

        inizioPeriodoDisponibilita.clear();
        finePeriodoDisponibilita.clear();
        comuneDisponibilita.clear();
    }

    @FXML
    private void aggiungiEsperienza(ActionEvent actionEvent) {
        Esperienza nuovaEsperienza = new Esperienza(-1, aziendaEsperienza.getText(), Integer.parseInt(retribuzioneEsperienza.getText()), (int) inizioPeriodoEsperienza.getValue().toEpochDay(), (int) inizioPeriodoEsperienza.getValue().toEpochDay(), comuneEsperienza.getValue());
        esperienze.add(nuovaEsperienza);

        aziendaEsperienza.clear();
        retribuzioneEsperienza.clear();
        inizioPeriodoEsperienza.clear();
        finePeriodoEsperienza.clear();
        comuneEsperienza.clear();
    }

    @FXML
    private void eliminaEsperienza(ActionEvent actionEvent) {
        for(int key : listaEsperienze.getSelectionModel().getSelection().keySet()){
            esperienze.remove(listaEsperienze.getSelectionModel().getSelection().get(key));
        }
        listaEsperienze.getSelectionModel().clearSelection();
    }

    private void buildTextFieldValidator(MFXTextField textField, Errore ...flags){
        List<Constraint> constraints = new ArrayList<Constraint>();
        for(Errore flag : flags){
            constraints.add(Constraint.Builder.build()
                    .setSeverity(Severity.ERROR)
                    .setMessage(flag.getLabel())
                    .setCondition(Bindings.createBooleanBinding(() -> switch(flag){
                                case NON_EMPTY:
                                    yield textField.getText().equals("");
                                case NUMBERS_ONLY:
                                    yield textField.getText().chars().allMatch(Character::isDigit);
                                default:
                                    yield textField.getText().chars().allMatch(Character::isLetter);
                            }, textField.textProperty())
                    ).get());
        }

        for(Constraint constraint : constraints){
            textField.getValidator().constraint(constraint);
        }
    }
}
