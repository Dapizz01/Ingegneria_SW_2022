package it.univr.lavoratoristagionali.controller;

import io.github.palexdev.materialfx.controls.*;
import it.univr.lavoratoristagionali.controller.enums.Errore;
import it.univr.lavoratoristagionali.controller.enums.View;
import it.univr.lavoratoristagionali.controller.exception.InputException;
import it.univr.lavoratoristagionali.controller.exception.InvalidPeriodException;
import it.univr.lavoratoristagionali.controller.validated.*;
import it.univr.lavoratoristagionali.model.Dao.*;
import it.univr.lavoratoristagionali.types.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ModificaLavoratoreController extends Controller implements Initializable {
    // ANAGRAFICA
    @FXML
    private MFXTextField nomeLavoratore, cognomeLavoratore;
    private MFXTextFieldValidated nomeLavoratoreValidated, cognomeLavoratoreValidated;
    @FXML
    private MFXDatePicker dataNascitaLavoratore;
    private MFXDatePickerValidated dataNascitaLavoratoreValidated;
    @FXML
    private MFXFilterComboBox<Comune> comuneNascitaLavoratore, comuneAbitazioneLavoratore;
    private MFXFilterComboBoxValidated<Comune> comuneNascitaLavoratoreValidated, comuneAbitazioneLavoratoreValidated;
    @FXML
    private MFXFilterComboBox<Lingua> nazionalitaLavoratore;
    private MFXFilterComboBoxValidated<Lingua> nazionalitaLavoratoreValidated;
    @FXML
    private Label nomeLavoratoreError, cognomeLavoratoreError, dataNascitaLavoratoreError, comuneNascitaLavoratoreError, comuneAbitazioneLavoratoreError;

    // CONTATTO LAVORATORE
    @FXML
    private MFXTextField telefonoLavoratore, emailLavoratore;
    private MFXTextFieldValidated telefonoLavoratoreValidated, emailLavoratoreValidated;
    @FXML
    private Label telefonoLavoratoreError, emailLavoratoreError;

    // INFORMAZIONI GENERALI
    @FXML
    private MFXCheckListView<Lingua> lingueLavoratore;
    private MFXCheckListViewValidated<Lingua> lingueLavoratoreValidated;
    // TODO: MFXCheckListViewValidated<T>
    @FXML
    private MFXCheckListView<Patente> patentiLavoratore;
    private MFXCheckListViewValidated<Patente> patentiLavoratoreValidated;
    @FXML
    private MFXCheckbox automunito;
    @FXML
    private Label nazionalitaLavoratoreError, patentiLavoratoreError, lingueLavoratoreError;

    // CONTATTI URGENTI
    @FXML
    private MFXTextField nomeContatto, cognomeContatto, telefonoContatto, emailContatto;
    private MFXTextFieldValidated nomeContattoValidated, cognomeContattoValidated, telefonoContattoValidated, emailContattoValidated;
    @FXML
    private MFXButton aggiungiContatto, eliminaContatto;
    @FXML
    private MFXListView<Contatto> listaContattoUrgente;
    private MFXListViewValidated<Contatto> listaContattoUrgenteValidated;
    @FXML
    private Label nomeContattoError, cognomeContattoError, telefonoContattoError, emailContattoError, listaContattoUrgenteError;

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
    private MFXListViewValidated<Disponibilita> listaDisponibilitaValidated;
    @FXML
    private Label inizioDisponibilitaError, fineDisponibilitaError, comuneDisponibilitaError, listaDisponibilitaError;

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
    private MFXFilterComboBox<Specializzazione> specializzazioneEsperienza;
    private MFXFilterComboBoxValidated<Specializzazione> specializzazioneEsperienzaValidated;
    @FXML
    private MFXButton aggiungiEsperienza, eliminaEsperienza;
    @FXML
    private MFXListView<Esperienza> listaEsperienze;
    private MFXListViewValidated<Esperienza> listaEsperienzeValidated;
    @FXML
    private Label aziendaEsperienzaError, retribuzioneEsperienzaError, inizioEsperienzaError, fineEsperienzaError, comuneEsperienzaError, specializzazioneEsperienzaError, listaEsperienzeError;

    // ... il resto
    @FXML
    private MFXButton ritornaMenu, modificaLavoratore;
    @FXML
    private MFXScrollPane scrollPane;

    // Variabili del controller non legate ad FXML
    private ObservableList<Contatto> contatti;
    private ObservableList<Esperienza> esperienze;
    private ObservableList<Disponibilita> disponibilita;

    // TODO: aggiungere label di fine inserimento con successo / errore

    private static final int DAYS_IN_MONTH = 30;
    private static final int DAYS_IN_YEAR = 365;
    private int ID_lavoratore = -1;

    public ModificaLavoratoreController(){

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        ComuniDao comuniDao = new ComuniDaoImpl();
        LingueDao lingueDao = new LingueDaoImpl();
        PatentiDao patentiDao = new PatentiDaoImpl();
        SpecializzazioniDao specializzazioniDao = new SpecializzazioniDaoImpl();

        List<Comune> comuni = comuniDao.getComuni(); // Ritorna la lista dei comuni nel DB da 0=Bonavigo a 5=Casaleone
        List<Lingua> lingue = lingueDao.getLingue();
        List<Patente> patenti = patentiDao.getPatenti();
        List<Specializzazione> specializzazioni = specializzazioniDao.getSpecializzazioni();


        nomeLavoratoreValidated = new MFXTextFieldValidated(nomeLavoratore, nomeLavoratoreError, Errore.LETTERS_ONLY, Errore.NON_EMPTY);
        cognomeLavoratoreValidated = new MFXTextFieldValidated(cognomeLavoratore, cognomeLavoratoreError, Errore.LETTERS_ONLY, Errore.NON_EMPTY);
        dataNascitaLavoratoreValidated = new MFXDatePickerValidated(dataNascitaLavoratore, dataNascitaLavoratoreError, Errore.MUST_BE_ADULT, Errore.NON_EMPTY);
        comuneNascitaLavoratoreValidated = new MFXFilterComboBoxValidated<Comune>(comuneNascitaLavoratore, comuneNascitaLavoratoreError, Errore.NON_EMPTY);
        comuneAbitazioneLavoratoreValidated = new MFXFilterComboBoxValidated<Comune>(comuneAbitazioneLavoratore, comuneAbitazioneLavoratoreError, Errore.NON_EMPTY);
        nazionalitaLavoratoreValidated = new MFXFilterComboBoxValidated<Lingua>(nazionalitaLavoratore, nazionalitaLavoratoreError, Errore.NON_EMPTY);
        telefonoLavoratoreValidated = new MFXTextFieldValidated(telefonoLavoratore, telefonoLavoratoreError, Errore.TELEPHONE_FORMAT, Errore.NON_EMPTY);
        emailLavoratoreValidated = new MFXTextFieldValidated(emailLavoratore, emailLavoratoreError, Errore.EMAIL_FORMAT, Errore.NON_EMPTY);

        lingueLavoratoreValidated = new MFXCheckListViewValidated<Lingua>(lingueLavoratore, lingueLavoratoreError, Errore.NON_EMPTY);
        patentiLavoratoreValidated = new MFXCheckListViewValidated<Patente>(patentiLavoratore, patentiLavoratoreError, (Errore) null);

        nomeContattoValidated = new MFXTextFieldValidated(nomeContatto, nomeContattoError, Errore.LETTERS_ONLY, Errore.NON_EMPTY);
        cognomeContattoValidated = new MFXTextFieldValidated(cognomeContatto, cognomeContattoError, Errore.LETTERS_ONLY, Errore.NON_EMPTY);
        emailContattoValidated = new MFXTextFieldValidated(emailContatto, emailContattoError, Errore.EMAIL_FORMAT, Errore.NON_EMPTY);
        telefonoContattoValidated = new MFXTextFieldValidated(telefonoContatto, telefonoContattoError, Errore.TELEPHONE_FORMAT, Errore.NON_EMPTY);
        listaContattoUrgenteValidated = new MFXListViewValidated<Contatto>(listaContattoUrgente, listaContattoUrgenteError, Errore.NON_EMPTY);

        inizioDisponibilitaValidated = new MFXDatePickerValidated(inizioDisponibilita, inizioDisponibilitaError, Errore.FROM_NOW, Errore.NON_EMPTY);
        fineDisponibilitaValidated = new MFXDatePickerValidated(fineDisponibilita, fineDisponibilitaError, Errore.FROM_NOW, Errore.NON_EMPTY);
        comuneDisponibilitaValidated = new MFXFilterComboBoxValidated<Comune>(comuneDisponibilita, comuneDisponibilitaError, Errore.NON_EMPTY);
        listaDisponibilitaValidated = new MFXListViewValidated<Disponibilita>(listaDisponibilita, listaDisponibilitaError, (Errore) null);

        aziendaEsperienzaValidated = new MFXTextFieldValidated(aziendaEsperienza, aziendaEsperienzaError, Errore.LETTERS_ONLY, Errore.NON_EMPTY);
        retribuzioneEsperienzaValidated = new MFXTextFieldValidated(retribuzioneEsperienza, retribuzioneEsperienzaError, Errore.NUMBERS_ONLY, Errore.NON_EMPTY);
        inizioEsperienzaValidated = new MFXDatePickerValidated(inizioEsperienza, inizioEsperienzaError, Errore.UP_TO_NOW, Errore.NON_EMPTY);
        fineEsperienzaValidated = new MFXDatePickerValidated(fineEsperienza, fineEsperienzaError, Errore.UP_TO_NOW, Errore.NON_EMPTY);
        comuneEsperienzaValidated = new MFXFilterComboBoxValidated<Comune>(comuneEsperienza, comuneEsperienzaError, Errore.NON_EMPTY);
        specializzazioneEsperienzaValidated = new MFXFilterComboBoxValidated<Specializzazione>(specializzazioneEsperienza, specializzazioneEsperienzaError, Errore.NON_EMPTY);
        listaEsperienzeValidated = new MFXListViewValidated<Esperienza>(listaEsperienze, listaEsperienzeError, (Errore) null);

        comuneNascitaLavoratore.setItems(FXCollections.observableArrayList(comuni));
        comuneAbitazioneLavoratore.setItems(FXCollections.observableArrayList(comuni));
        lingueLavoratore.setItems(FXCollections.observableArrayList(lingue));
        nazionalitaLavoratore.setItems(FXCollections.observableArrayList(lingue));
        patentiLavoratore.setItems(FXCollections.observableArrayList(patenti));
        comuneEsperienza.setItems(FXCollections.observableArrayList(comuni));
        comuneDisponibilita.setItems(FXCollections.observableArrayList(comuni));
        specializzazioneEsperienza.setItems(FXCollections.observableArrayList(specializzazioni));

        contatti = FXCollections.observableArrayList(new ArrayList<>());
        listaContattoUrgente.setItems(contatti);

        esperienze = FXCollections.observableArrayList(new ArrayList<>());
        listaEsperienze.setItems(esperienze);

        disponibilita = FXCollections.observableArrayList(new ArrayList<>());
        listaDisponibilita.setItems(disponibilita);

        listaDisponibilita.features().enableSmoothScrolling(1.2);
        listaEsperienze.features().enableSmoothScrolling(1.2);
        listaContattoUrgente.features().enableSmoothScrolling(1.2);

        // TODO: aggiungere stili css errori con label di errore associata
        // Link utili: https://github.com/palexdev/MaterialFX/blob/main/demo/src/main/java/io/github/palexdev/materialfx/demo/controllers/TextFieldsController.java
        // https://github.com/palexdev/MaterialFX/blob/main/demo/src/main/resources/io/github/palexdev/materialfx/demo/css/TextFields.css
    }

    public void setLavoratoreBase(Lavoratore lavoratore){
        ID_lavoratore = lavoratore.getID();
        nomeLavoratore.setText(lavoratore.getNomeLavoratore());
        cognomeLavoratore.setText(lavoratore.getCognomeLavoratore());
        dataNascitaLavoratore.setValue(LocalDate.ofEpochDay(lavoratore.getDataNascita()));
        comuneNascitaLavoratore.selectItem(lavoratore.getComuneNascita());
        comuneAbitazioneLavoratore.selectItem(lavoratore.getComuneAbitazione());
        nazionalitaLavoratore.selectItem(lavoratore.getNazionalita());
        emailLavoratore.setText(lavoratore.getEmail());
        telefonoLavoratore.setText(lavoratore.getTelefono());
        automunito.setSelected(lavoratore.isAutomunito());
        esperienze.addAll(lavoratore.getEsperienze());
        for(Lingua lingua : lavoratore.getLingue()){
            lingueLavoratore.getSelectionModel().selectItem(lingua);
        }
        /*if(lavoratore.getLingue().size() >= 2)
            lingueLavoratore.getSelectionModel().selectItems(lavoratore.getLingue());
        else if(lavoratore.getLingue().size() == 1)
            lingueLavoratore.getSelectionModel().selectItem(lavoratore.getLingue().get(0));*/
        contatti.addAll(lavoratore.getContatti());
        for(Patente patente : lavoratore.getPatenti()){
            patentiLavoratore.getSelectionModel().selectItem(patente);
        }
        /*if(lavoratore.getPatenti().size() >= 2)
            patentiLavoratore.getSelectionModel().selectItems(lavoratore.getPatenti());
        else if(lavoratore.getPatenti().size() == 1)
            patentiLavoratore.getSelectionModel().selectItem(lavoratore.getPatenti().get(0));*/
        disponibilita.addAll(lavoratore.getDisponibilita());
    }

    @FXML
    private void onClickRitornaMenu(ActionEvent actionEvent){
        System.out.println("ritornaMenu fired");
        switchScene(getStageFromEvent(actionEvent), View.MENU_MODIFICA_LAVORATORE);
    }

    @FXML
    private void onClickModificaLavoratore(ActionEvent actionEvent){
        Lavoratore lavoratore;

        try{
            lavoratore = new Lavoratore(ID_lavoratore,
                    nomeLavoratoreValidated.getText(), // TODO: non considerare come errore gli spazi
                    cognomeLavoratoreValidated.getText(),
                    comuneNascitaLavoratoreValidated.getSelectedItem(),
                    comuneAbitazioneLavoratoreValidated.getSelectedItem(),
                    dataNascitaLavoratoreValidated.getEpochDays(),
                    nazionalitaLavoratoreValidated.getSelectedItem(),
                    emailLavoratoreValidated.getText(),
                    telefonoLavoratoreValidated.getText(),
                    automunito.isSelected(),
                    listaEsperienzeValidated.getSelectedItems(),
                    lingueLavoratoreValidated.getSelectedItems(),
                    listaContattoUrgenteValidated.getSelectedItems(),
                    patentiLavoratoreValidated.getSelectedItems(),
                    listaDisponibilitaValidated.getSelectedItems());
            LavoratoriDao lavoratoriDao = new LavoratoriDaoImpl();
            lavoratoriDao.updateLavoratore(lavoratore);
            switchScene(getStageFromEvent(actionEvent), View.MENU_MODIFICA_LAVORATORE);
        }
        catch (InputException inputException){
            return; // TODO: mostrare errore
        }
    }

    @FXML
    private void onClickAggiungiContatto(ActionEvent actionEvent) {
        Contatto contatto;
        try {
            contatto = new Contatto(-1, nomeContattoValidated.getText(), cognomeContattoValidated.getText(), telefonoContattoValidated.getText(), emailContattoValidated.getText());
            contatti.add(contatto);

            nomeContattoValidated.reset();
            cognomeContattoValidated.reset();
            telefonoContattoValidated.reset();
            emailContattoValidated.reset();
        }
        catch (InputException inputException) {
            return;
        }
    }

    @FXML
    private void testEvent(MouseEvent mouseEvent) {
        // Se è stato cliccato un label del ListView
        if(mouseEvent.getTarget() instanceof Text) {
        }
    }

    @FXML
    private void onClickEliminaContatto(ActionEvent actionEvent){
        for(int key : listaContattoUrgente.getSelectionModel().getSelection().keySet()){
            contatti.remove(listaContattoUrgente.getSelectionModel().getSelection().get(key));
        }
        listaContattoUrgente.getSelectionModel().clearSelection();
    }

    @FXML
    private void onClickEliminaDisponibilita(ActionEvent actionEvent) {
        for(int key : listaDisponibilita.getSelectionModel().getSelection().keySet()){
            disponibilita.remove(listaDisponibilita.getSelectionModel().getSelection().get(key));
        }
        listaDisponibilita.getSelectionModel().clearSelection();
    }

    @FXML
    private void onClickAggiungiDisponibilita(ActionEvent actionEvent) {
        boolean periodInvalid = false;
        try{
            if(inizioDisponibilitaValidated.getEpochDays()  >= fineDisponibilitaValidated.getEpochDays())
                throw new InvalidPeriodException(fineDisponibilitaValidated, "La data di fine deve essere successiva alla data di inizio");

            if(fineDisponibilitaValidated.getEpochDays() <= inizioDisponibilitaValidated.getEpochDays() + DAYS_IN_MONTH)
                throw new InvalidPeriodException(fineDisponibilitaValidated, "La durata della disponibilita deve essere almeno di 1 mese");

            for(Disponibilita disponibilita : listaDisponibilita.getItems()){
                if(disponibilita.getComune() == comuneDisponibilita.getValue()){
                    if((inizioDisponibilitaValidated.getEpochDays() <= disponibilita.getFinePeriodo() && inizioDisponibilitaValidated.getEpochDays() >= disponibilita.getInizioPeriodo()) ||
                            (fineDisponibilitaValidated.getEpochDays() <= disponibilita.getFinePeriodo() && fineDisponibilitaValidated.getEpochDays() >= disponibilita.getInizioPeriodo())){
                        throw new InvalidPeriodException(listaDisponibilitaValidated, "Non è possibile inserire questa disponibilità, va in conflitto con altre disponibilità");
                    }
                }
            }

            Disponibilita nuovaDisponibilita = new Disponibilita(inizioDisponibilitaValidated.getEpochDays(), fineDisponibilitaValidated.getEpochDays(), comuneDisponibilitaValidated.getSelectedItem());
            disponibilita.add(nuovaDisponibilita);

            inizioDisponibilitaValidated.reset();
            fineDisponibilitaValidated.reset();
            comuneDisponibilitaValidated.reset();
        }
        catch (InputException inputException){
            return;
        }
    }

    @FXML
    private void onClickAggiungiEsperienza(ActionEvent actionEvent) {
        try {

            if(fineEsperienzaValidated.getEpochDays() <= inizioEsperienzaValidated.getEpochDays()){
                throw new InvalidPeriodException(fineEsperienzaValidated, "La data di fine deve essere successiva alla data di inizio");
            }

            if(fineEsperienzaValidated.getEpochDays() <= inizioEsperienzaValidated.getEpochDays() + DAYS_IN_MONTH){
                System.out.println("test1");
                throw new InvalidPeriodException(fineEsperienzaValidated, "La esperienza passata deve avere durata di almeno un mese");
            }
            if(fineEsperienzaValidated.getEpochDays() >= inizioEsperienzaValidated.getEpochDays() + 2 * DAYS_IN_YEAR){
                System.out.println("test2");
                throw new InvalidPeriodException(fineEsperienzaValidated, "La esperienza passata deve avere durata di massimo 2 anni");
            }


            Esperienza nuovaEsperienza = new Esperienza(-1,
                    aziendaEsperienzaValidated.getText(),
                    Integer.parseInt(retribuzioneEsperienzaValidated.getText()),
                    inizioEsperienzaValidated.getEpochDays(),
                    fineEsperienzaValidated.getEpochDays(),
                    comuneEsperienzaValidated.getSelectedItem(),
                    specializzazioneEsperienzaValidated.getSelectedItem());

            esperienze.add(nuovaEsperienza);

            aziendaEsperienzaValidated.reset();
            retribuzioneEsperienzaValidated.reset();
            inizioEsperienzaValidated.reset();
            fineEsperienzaValidated.reset();
            comuneEsperienzaValidated.reset();
        }
        catch(InputException inputException) {
            return;
        }
    }

    @FXML
    private void onClickEliminaEsperienza(ActionEvent actionEvent) {
        for(int key : listaEsperienze.getSelectionModel().getSelection().keySet()){
            esperienze.remove(listaEsperienze.getSelectionModel().getSelection().get(key));
        }
        listaEsperienze.getSelectionModel().clearSelection();
    }
}