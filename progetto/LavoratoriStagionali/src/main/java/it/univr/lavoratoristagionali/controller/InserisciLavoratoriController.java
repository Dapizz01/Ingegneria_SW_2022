package it.univr.lavoratoristagionali.controller;

import io.github.palexdev.materialfx.controls.*;
import io.github.palexdev.materialfx.controls.cell.MFXListCell;
import io.github.palexdev.materialfx.validation.Constraint;
import io.github.palexdev.materialfx.validation.Severity;
import it.univr.lavoratoristagionali.controller.validated.MFXDatePickerValidated;
import it.univr.lavoratoristagionali.controller.validated.MFXFilterComboBoxValidated;
import it.univr.lavoratoristagionali.controller.validated.MFXTextFieldValidated;
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
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class InserisciLavoratoriController extends Controller implements Initializable {
    // ANAGRAFICA
    @FXML
    private Text anagraficaTitle;
    @FXML
    private MFXTextField nomeLavoratore, cognomeLavoratore;
    private MFXTextFieldValidated nomeLavoratoreValidated, cognomeLavoratoreValidated;
    @FXML
    private MFXDatePicker dataNascitaLavoratore;
    private MFXDatePickerValidated dataNascitaLavoratoreValidated;
    @FXML
    private MFXFilterComboBox<Comune> comuneNascitaLavoratore;
    private MFXFilterComboBoxValidated<Comune> comuneNascitaLavoratoreValidated;
    @FXML
    private MFXCheckListView<Lingua> nazionalitaLavoratore;
    @FXML
    private Label nomeLavoratoreError, cognomeLavoratoreError, dataNascitaLavoratoreError, comuneNascitaLavoratoreError;

    // CONTATTO LAVORATORE
    @FXML
    private MFXTextField indirizzoLavoratore, telefonoLavoratore, emailLavoratore;
    private MFXTextFieldValidated indirizzoLavoratoreValidated, telefonoLavoratoreValidated, emailLavoratoreValidated;
    @FXML
    private Label indirizzoLavoratoreError, telefonoLavoratoreError, emailLavoratoreError;

    // INFORMAZIONI GENERALI
    @FXML
    private MFXCheckListView<Lingua> lingueParlate;
    // TODO: MFXCheckListViewValidated<T>
    @FXML
    private MFXCheckListView<Patente> patentiPossedute;
    @FXML
    private MFXCheckbox automunito;

    // CONTATTI URGENTI
    @FXML
    private MFXTextField nomeContatto, cognomeContatto, telefonoContatto, emailContatto;
    private MFXTextFieldValidated nomeContattoValidated, cognomeContattoValidated, telefonoContattoValidated, emailContattoValidated;
    @FXML
    private MFXButton aggiungiContatto, eliminaContatto;
    @FXML
    private MFXListView<Contatto> listaContattoUrgente;
    @FXML
    private Label nomeContattoError, cognomeContattoError, telefonoContattoError, emailContattoError;

    // DISPONIBILITA
    @FXML
    private MFXDatePicker inizioDisponibilita, fineDisponibilita;
    private MFXDatePickerValidated inizioDisponibilitaValidated, fineDisponibilitaValidated;
    @FXML
    private MFXFilterComboBox<Comune> comuneDisponibilita;
    private MFXFilterComboBoxValidated<Comune> comuneDisponibilitaValidated;
    @FXML
    private MFXButton aggiungiDisponibilita, eliminaDisponibilita;
    @FXML
    private MFXListView<Disponibilita> listaDisponibilita;
    @FXML
    private Label inizioDisponibilitaError, fineDisponibilitaError, comuneDisponibilitaError;

    // ESPERIENZE
    @FXML
    private MFXTextField aziendaEsperienza, retribuzioneEsperienza;
    private MFXTextFieldValidated aziendaEsperienzaValidated, retribuzioneEsperienzaValidated;
    @FXML
    private MFXDatePicker inizioEsperienza, fineEsperienza;
    private MFXDatePickerValidated inizioEsperienzaValidated, fineEsperienzaValidated;
    @FXML
    private MFXFilterComboBox<Comune> comuneEsperienza;
    private MFXFilterComboBoxValidated<Comune> comuneEsperienzaValidated;
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

        nomeLavoratoreValidated = new MFXTextFieldValidated(nomeLavoratore, nomeLavoratoreError, Errore.LETTERS_ONLY, Errore.NON_EMPTY);
        cognomeLavoratoreValidated = new MFXTextFieldValidated(cognomeLavoratore, cognomeLavoratoreError, Errore.LETTERS_ONLY, Errore.NON_EMPTY);
        dataNascitaLavoratoreValidated = new MFXDatePickerValidated(dataNascitaLavoratore, dataNascitaLavoratoreError, Errore.MUST_BE_ADULT, Errore.NON_EMPTY);
        comuneNascitaLavoratoreValidated = new MFXFilterComboBoxValidated<Comune>(comuneNascitaLavoratore, comuneNascitaLavoratoreError, Errore.NON_EMPTY);
        indirizzoLavoratoreValidated = new MFXTextFieldValidated(indirizzoLavoratore, indirizzoLavoratoreError, Errore.NON_EMPTY);
        telefonoLavoratoreValidated = new MFXTextFieldValidated(telefonoLavoratore, telefonoLavoratoreError, Errore.TELEPHONE_FORMAT, Errore.NON_EMPTY);
        emailLavoratoreValidated = new MFXTextFieldValidated(emailLavoratore, emailLavoratoreError, Errore.EMAIL_FORMAT, Errore.NON_EMPTY);

        nomeContattoValidated = new MFXTextFieldValidated(nomeContatto, nomeContattoError, Errore.LETTERS_ONLY, Errore.NON_EMPTY);
        cognomeContattoValidated = new MFXTextFieldValidated(cognomeContatto, cognomeContattoError, Errore.LETTERS_ONLY, Errore.NON_EMPTY);
        emailContattoValidated = new MFXTextFieldValidated(emailContatto, emailContattoError, Errore.EMAIL_FORMAT, Errore.NON_EMPTY);
        telefonoContattoValidated = new MFXTextFieldValidated(telefonoContatto, telefonoContattoError, Errore.TELEPHONE_FORMAT, Errore.NON_EMPTY);

        inizioDisponibilitaValidated = new MFXDatePickerValidated(inizioDisponibilita, inizioDisponibilitaError, Errore.FROM_NOW, Errore.NON_EMPTY);
        fineDisponibilitaValidated = new MFXDatePickerValidated(fineDisponibilita, fineDisponibilitaError, Errore.FROM_NOW, Errore.NON_EMPTY);
        comuneDisponibilitaValidated = new MFXFilterComboBoxValidated<Comune>(comuneDisponibilita, comuneDisponibilitaError, Errore.NON_EMPTY);

        aziendaEsperienzaValidated = new MFXTextFieldValidated(aziendaEsperienza, aziendaEsperienzaError, Errore.LETTERS_ONLY, Errore.NON_EMPTY);
        retribuzioneEsperienzaValidated = new MFXTextFieldValidated(retribuzioneEsperienza, retribuzioneEsperienzaError, Errore.NUMBERS_ONLY, Errore.NON_EMPTY);
        inizioEsperienzaValidated = new MFXDatePickerValidated(inizioEsperienza, inizioEsperienzaError, Errore.UP_TO_NOW, Errore.NON_EMPTY);
        fineEsperienzaValidated = new MFXDatePickerValidated(fineEsperienza, fineEsperienzaError, Errore.UP_TO_NOW, Errore.NON_EMPTY);
        comuneEsperienzaValidated = new MFXFilterComboBoxValidated<Comune>(comuneEsperienza, comuneEsperienzaError, Errore.NON_EMPTY);

        comuneNascitaLavoratore.setItems(FXCollections.observableArrayList(comuni));
        lingueParlate.setItems(FXCollections.observableArrayList(lingue));
        nazionalitaLavoratore.setItems(FXCollections.observableArrayList(nazionalita));
        patentiPossedute.setItems(FXCollections.observableArrayList(patenti));
        comuneEsperienza.setItems(FXCollections.observableArrayList(comuni));
        comuneDisponibilita.setItems(FXCollections.observableArrayList(comuni));

        contatti = FXCollections.observableArrayList(new ArrayList<>());
        listaContattoUrgente.setItems(contatti);

        esperienze = FXCollections.observableArrayList(new ArrayList<>());
        listaEsperienze.setItems(esperienze);

        disponibilita = FXCollections.observableArrayList(new ArrayList<>());
        listaDisponibilita.setItems(disponibilita);

        /*buildFilterComboBoxValidator(comuneNascitaLavoratore, comuneNascitaLavoratoreError, Errore.NON_EMPTY);
        buildFilterComboBoxValidator(comuneDisponibilita, comuneDisponibilitaError, Errore.NON_EMPTY);
        buildFilterComboBoxValidator(comuneEsperienza, comuneEsperienzaError, Errore.NON_EMPTY);*/


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

        if(nomeLavoratoreValidated.checkValid() && cognomeLavoratoreValidated.checkValid() && comuneNascitaLavoratoreValidated.checkValid() && dataNascitaLavoratoreValidated.checkValid() && emailLavoratoreValidated.checkValid() && telefonoLavoratoreValidated.checkValid()){
            Lavoratore lavoratore = new Lavoratore(-1, nomeLavoratore.getText(), cognomeLavoratore.getText(), comuneNascitaLavoratore.getValue(), null, (int) dataNascitaLavoratore.getValue().toEpochDay(), nazionalitaLavoratore.getSelectionModel().getSelection().get(0), emailLavoratore.getText(), telefonoLavoratore.getText(), automunito.isSelected(), listaEsperienze.getSelectionModel().getSelectedValues(), lingueParlate.getItems(), listaContattoUrgente.getItems(), patentiPossedute.getSelectionModel().getSelectedValues(), listaDisponibilita.getItems());
            System.out.println(lavoratore);
        }
        // TODO: aggiungere comune di abitazione
    }

    @FXML
    private void onClickAggiungiContatto(ActionEvent actionEvent) {
        boolean valid = true;
        if(nomeContattoValidated.checkValid() && cognomeContattoValidated.checkValid() && telefonoContattoValidated.checkValid() && emailContattoValidated.checkValid()){
            Contatto nuovoContatto = new Contatto(-1, nomeContatto.getText(), cognomeContatto.getText(), telefonoContatto.getText(), emailContatto.getText());
            contatti.add(nuovoContatto);

            nomeContatto.clear();
            cognomeContatto.clear();
            telefonoContatto.clear();
            emailContatto.clear();

            System.out.println(nomeLavoratore.validate());
        }
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
        // System.out.println(contatti);
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
        boolean periodInvalid = false;
        if(inizioDisponibilitaValidated.checkValid() && fineDisponibilitaValidated.checkValid() && comuneDisponibilitaValidated.checkValid()){ // TODO: aggiungere controllo data inizio / fine, dove inizio < fine

            for(Disponibilita disponibilita : listaDisponibilita.getItems()){
                if(disponibilita.getComune() == comuneDisponibilita.getValue()){
                    if((inizioDisponibilita.getValue().toEpochDay() <= disponibilita.getFinePeriodo() && inizioDisponibilita.getValue().toEpochDay() >= disponibilita.getInizioPeriodo()) ||
                            (fineDisponibilita.getValue().toEpochDay() <= disponibilita.getFinePeriodo() && fineDisponibilita.getValue().toEpochDay() >= disponibilita.getInizioPeriodo())){
                        listaDisponibilita.setStyle("-fx-border-color: -mfx-red"); // TODO: not working
                        periodInvalid = true;
                    }
                }
            }

            System.out.println(disponibilita);

            if(!periodInvalid){

                Disponibilita nuovaDisponibilita = new Disponibilita((int) inizioDisponibilita.getValue().toEpochDay(), (int) fineDisponibilita.getValue().toEpochDay(), comuneDisponibilita.getValue());
                disponibilita.add(nuovaDisponibilita);

                inizioDisponibilita.clear();
                fineDisponibilita.clear();
                comuneDisponibilita.clear();
            }

        }
        /*System.out.println(disponibilita);
        System.out.println(disponibilita.get(0).getInizioPeriodo());
        System.out.println(disponibilita.get(0).getFinePeriodo());*/
    }

    @FXML
    private void onClickAggiungiEsperienza(ActionEvent actionEvent) {
        if(aziendaEsperienzaValidated.checkValid() && retribuzioneEsperienzaValidated.checkValid() && inizioEsperienzaValidated.checkValid() && fineEsperienzaValidated.checkValid() && comuneEsperienzaValidated.checkValid()){
            Esperienza nuovaEsperienza = new Esperienza(-1, aziendaEsperienza.getText(), Integer.parseInt(retribuzioneEsperienza.getText()), (int) inizioEsperienza.getValue().toEpochDay(), (int) fineEsperienza.getValue().toEpochDay(), comuneEsperienza.getValue(), null);
            esperienze.add(nuovaEsperienza);

            aziendaEsperienza.clear();
            retribuzioneEsperienza.clear();
            inizioEsperienza.clear();
            fineEsperienza.clear();
            comuneEsperienza.clear();
        }
    }

    @FXML
    private void onClickEliminaEsperienza(ActionEvent actionEvent) {
        for(int key : listaEsperienze.getSelectionModel().getSelection().keySet()){
            esperienze.remove(listaEsperienze.getSelectionModel().getSelection().get(key));
        }
        listaEsperienze.getSelectionModel().clearSelection();
    }

    private void buildTextFieldValidator(MFXTextField textField, Label errorLabel, Errore ...flags){
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

        textField.delegateFocusedProperty().addListener((observable, oldValue, newValue) -> {
            if(oldValue && !newValue){
                List<Constraint> currentConstraints = textField.validate();
                if(!currentConstraints.isEmpty()){
                    errorLabel.setText(currentConstraints.get(0).getMessage());
                    errorLabel.setVisible(true);
                    textField.setStyle("-fx-border-color: -mfx-red");
                }
            }
        });


        textField.getValidator().validProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue){
                textField.setStyle("-fx-border-color: -mfx-green");
                errorLabel.setVisible(false);
            }
        });
    }
}
