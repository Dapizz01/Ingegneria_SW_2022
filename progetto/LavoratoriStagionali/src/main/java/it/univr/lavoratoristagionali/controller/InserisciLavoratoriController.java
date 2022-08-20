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
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Border;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class InserisciLavoratoriController extends Controller implements Initializable {
    // ANAGRAFICA
    @FXML
    private Text anagraficaTitle;
    @FXML
    private MFXTextField nomeLavoratore, cognomeLavoratore;
    @FXML
    private MFXDatePicker dataNascitaLavoratore;
    @FXML
    private MFXFilterComboBox<Comune> comuneNascitaLavoratore;
    @FXML
    private MFXCheckListView<Lingua> nazionalitaLavoratore;
    @FXML
    private Label nomeLavoratoreError, cognomeLavoratoreError, dataNascitaLavoratoreError, comuneNascitaLavoratoreError;

    // CONTATTO LAVORATORE
    @FXML
    private MFXTextField indirizzoLavoratore, telefonoLavoratore, emailLavoratore;
    @FXML
    private Label indirizzoLavoratoreError, telefonoLavoratoreError, emailLavoratoreError;

    // INFORMAZIONI GENERALI
    @FXML
    private MFXCheckListView<Lingua> lingueParlate;
    @FXML
    private MFXCheckListView<Patente> patentiPossedute;
    @FXML
    private MFXCheckbox automunito;

    // CONTATTI URGENTI
    @FXML
    private MFXTextField nomeContatto, cognomeContatto, telefonoContatto, emailContatto;
    @FXML
    private MFXButton aggiungiContatto, eliminaContatto;
    @FXML
    private MFXListView<Contatto> listaContattoUrgente;
    @FXML
    private Label nomeContattoError, cognomeContattoError, telefonoContattoError, emailContattoError;

    // DISPONIBILITA
    @FXML
    private MFXDatePicker inizioDisponibilita, fineDisponibilita;
    @FXML
    private MFXFilterComboBox<Comune> comuneDisponibilita;
    @FXML
    private MFXButton aggiungiDisponibilita, eliminaDisponibilita;
    @FXML
    private MFXListView<Disponibilita> listaDisponibilita;
    @FXML
    private Label inizioDisponibilitaError, fineDisponibilitaError, comuneDisponibilitaError;

    // ESPERIENZE
    @FXML
    private MFXTextField aziendaEsperienza, retribuzioneEsperienza;
    @FXML
    private MFXDatePicker inizioEsperienza, fineEsperienza;
    @FXML
    private MFXFilterComboBox<Comune> comuneEsperienza;
    @FXML
    private MFXButton aggiungiEsperienza, eliminaEsperienza;
    @FXML
    private MFXListView<Esperienza> listaEsperienze;
    @FXML
    private Label aziendaEsperienzaError, retribuzioneEsperienzaError, inizioEsperienzaError, fineEsperienzaError, comuneEsperienzaError;

    // ... il resto
    @FXML
    private MFXButton ritornaMenu, inviaLavoratore;

    // Variabili del controller non legate ad FXML
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

        comuneNascitaLavoratore.setItems(FXCollections.observableArrayList(comuni));
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

        buildTextFieldValidator(nomeLavoratore, Errore.LETTERS_ONLY, Errore.NON_EMPTY);

        // TEST ERRORE

        nomeLavoratore.delegateFocusedProperty().addListener((observable, oldValue, newValue) -> {
            if(oldValue && !newValue){
                List<Constraint> constraints = nomeLavoratore.validate();
                if(!constraints.isEmpty()){
                    nomeLavoratoreError.setText(constraints.get(0).getMessage());
                    nomeLavoratoreError.setVisible(true);
                    nomeLavoratore.setStyle("-fx-border-color: -mfx-red");
                }
            }
        });

        nomeLavoratore.getValidator().validProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue){
                nomeLavoratore.setStyle("-fx-border-color: -mfx-green");
                nomeLavoratoreError.setVisible(false);
            }
        });
        // TODO: aggiungere stili css errori con label di errore associata
        // Link utili: https://github.com/palexdev/MaterialFX/blob/main/demo/src/main/java/io/github/palexdev/materialfx/demo/controllers/TextFieldsController.java
        // https://github.com/palexdev/MaterialFX/blob/main/demo/src/main/resources/io/github/palexdev/materialfx/demo/css/TextFields.css
    }

    @FXML
    private void onClickRitornaMenu(ActionEvent actionEvent){
        System.out.println("ritornaMenu fired");
        switchScene(getStageFromEvent(actionEvent), View.MAIN_MENU);
    }

    @FXML
    private void onClickInviaLavoratore(ActionEvent actionEvent){
        /*System.out.println(lingueParlate.getSelectionModel().getSelectedValues());
        System.out.println(nazionalitaLavoratore.getSelectionModel().getSelectedValues());
        System.out.println(patentiPossedute.getSelectionModel().getSelectedValues());
        System.out.println(comuneNascita.getSelectionModel().getSelectedItem());*/


        // TODO: aggiungere comune di abitazione
        Lavoratore lavoratore = new Lavoratore(-1, nomeLavoratore.getText(), cognomeLavoratore.getText(), comuneNascitaLavoratore.getValue(), null, (int) dataNascitaLavoratore.getValue().toEpochDay(), nazionalitaLavoratore.getSelectionModel().getSelection().get(0), emailLavoratore.getText(), telefonoLavoratore.getText(), automunito.isSelected(), listaEsperienze.getItems(), lingueParlate.getItems(), listaContattoUrgente.getItems(), patentiPossedute.getItems(), listaDisponibilita.getItems());
        System.out.println(lavoratore);
    }

    @FXML
    private void onClickAggiungiContatto(ActionEvent actionEvent) {
        Contatto nuovoContatto = new Contatto(-1, nomeContatto.getText(), cognomeContatto.getText(), telefonoContatto.getText(), emailContatto.getText());
        contatti.add(nuovoContatto);

        nomeContatto.clear();
        cognomeContatto.clear();
        telefonoContatto.clear();
        emailContatto.clear();

        System.out.println(nomeLavoratore.validate());
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
    private void onClickEliminaContatto(ActionEvent actionEvent){
        for(int key : listaContattoUrgente.getSelectionModel().getSelection().keySet()){
            contatti.remove(listaContattoUrgente.getSelectionModel().getSelection().get(key));
        }
        listaContattoUrgente.getSelectionModel().clearSelection();
        System.out.println(contatti);
    }

    @FXML
    private void onClickEliminaDisponibilita(ActionEvent actionEvent) {
        for(int key : listaDisponibilita.getSelectionModel().getSelection().keySet()){
            disponibilita.remove(listaDisponibilita.getSelectionModel().getSelection().get(key));
        }
        listaDisponibilita.getSelectionModel().clearSelection();
        // System.out.println(contatti);
    }

    @FXML
    private void onClickAggiungiDisponibilita(ActionEvent actionEvent) {
        Disponibilita nuovaDisponibilita = new Disponibilita((int) inizioDisponibilita.getValue().toEpochDay(), (int) fineDisponibilita.getValue().toEpochDay(), comuneDisponibilita.getValue());
        disponibilita.add(nuovaDisponibilita);

        System.out.println(disponibilita);
        System.out.println(disponibilita.get(0).getInizioPeriodo());
        System.out.println(disponibilita.get(0).getFinePeriodo());

        inizioDisponibilita.clear();
        fineDisponibilita.clear();
        comuneDisponibilita.clear();
    }

    @FXML
    private void onClickAggiungiEsperienza(ActionEvent actionEvent) {
        Esperienza nuovaEsperienza = new Esperienza(-1, aziendaEsperienza.getText(), Integer.parseInt(retribuzioneEsperienza.getText()), (int) inizioEsperienza.getValue().toEpochDay(), (int) inizioEsperienza.getValue().toEpochDay(), comuneEsperienza.getValue());
        esperienze.add(nuovaEsperienza);

        aziendaEsperienza.clear();
        retribuzioneEsperienza.clear();
        inizioEsperienza.clear();
        fineEsperienza.clear();
        comuneEsperienza.clear();
    }

    @FXML
    private void onClickEliminaEsperienza(ActionEvent actionEvent) {
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
                                    yield !textField.getText().equals("");
                                case NUMBERS_ONLY:
                                    yield textField.getText().chars().allMatch(Character::isDigit);
                                case LETTERS_ONLY:
                                    yield textField.getText().chars().allMatch(Character::isLetter);
                                default:
                                    yield true;
                            }, textField.textProperty())
                    ).get());
        }

        for(Constraint constraint : constraints){
            textField.getValidator().constraint(constraint);
        }
    }
}
