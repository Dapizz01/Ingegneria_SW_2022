package it.univr.lavoratoristagionali.controller;

import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXListView;
import io.github.palexdev.materialfx.controls.MFXTextField;
import io.github.palexdev.materialfx.utils.others.FunctionalStringConverter;
import it.univr.lavoratoristagionali.controller.enums.View;
import it.univr.lavoratoristagionali.model.Dao.LavoratoriDao;
import it.univr.lavoratoristagionali.model.Dao.LavoratoriDaoImpl;
import it.univr.lavoratoristagionali.types.Lavoratore;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class MenuModificaLavoratoreController extends Controller implements Initializable {
    // Inserimento ricerca
    @FXML
    private MFXTextField nome, cognome;
    @FXML
    private MFXButton cercaLavoratore;

    // Risultati ricerca + operazioni
    @FXML
    private MFXListView<Lavoratore> listaLavoratori;
    @FXML
    private MFXButton modificaLavoratore, eliminaLavoratore;

    // ... il resto
    @FXML
    private MFXButton tornaAlMenu;

    // Variabili del controller
    private LavoratoriDao lavoratoriDao;
    private ObservableList<Lavoratore> lavoratori;

    public MenuModificaLavoratoreController(){
        // Non mettere niente qui, altrimenti FXMLoader non fa caricare questo controller
    }

    /**
     * Funzione chiamata da JavaFX non appena la scena è caricata. Setta come visualizzare i lavoratori su listaLavoratori e istanzia LavoratoriDAO
     *
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Impostazione del formato della stringa da mostrare per ogni cella di listaLavoratori
        listaLavoratori.setConverter(FunctionalStringConverter.to(lavoratore -> {
            if(lavoratore != null)
                return lavoratore.getNomeLavoratore() +
                        " " + lavoratore.getCognomeLavoratore() +
                        ", nato il " + LocalDate.ofEpochDay(lavoratore.getDataNascita()) +
                        " a " + lavoratore.getComuneNascita();
            return "";
        }));

        // Creazione di un ObservableArrayList
        // listaLavoratori prende gli elementi direttamente da questo ObservableArrayList, quindi ogni modifica fatta
        // a questo oggetto si ripercuote su listaLavoratori (ogni elemento di questo oggetto è una cella su listaLavoratori)
        lavoratori = FXCollections.observableArrayList(new ArrayList<Lavoratore>());
        listaLavoratori.setItems(lavoratori);

        lavoratoriDao = new LavoratoriDaoImpl();
    }

    /**
     * Mateodo callback richiamato da JavaFX al click del pulsante di ritorno al menu.
     * Carica la scena del menu principale.
     *
     * @param actionEvent parametro evento JavaFX
     */
    @FXML
    private void onClickTornaAlMenu(ActionEvent actionEvent) {
        switchScene(getStageFromEvent(actionEvent), View.MAIN_MENU);
    }

    /**
     * Mateodo callback richiamato da JavaFX al click del pulsante di ricerca lavoratore.
     * Ricerca i lavoratori tramite LavoratoriDAO con i valori dei textField e li mostra sulla lista di lavoratori.
     *
     * @param actionEvent parametro evento JavaFX
     */
    @FXML
    private void onClickCercaLavoratore(ActionEvent actionEvent) {
        lavoratori.setAll(lavoratoriDao.getLavoratori(nome.getText(), cognome.getText()));
    }

    /**
     * Mateodo callback richiamato da JavaFX al click del pulsante di modifica lavoratore.
     * Carica la scena modificaLavoratore con i dati del lavoratore selezionato sulla lista.
     * Se sulla lista non è selezionato alcun lavoratore, non fa nulla.
     *
     * @param actionEvent parametro evento JavaFX
     */
    @FXML
    private void onClickModificaLavoratore(ActionEvent actionEvent) {
        // Se è stato selezionato un lavoratore su listaLavoratori
        if(!listaLavoratori.getSelectionModel().getSelectedValues().isEmpty())
            // Cambia scena in modificaLavoratore, passando come parametro il lavoratore scelto dall'utente
            switchScene(getStageFromEvent(actionEvent), View.MODIFICA_LAVORATORE, listaLavoratori.getSelectionModel().getSelectedValues().get(0));
    }

    /**
     * Mateodo callback richiamato da JavaFX al click del pulsante di elimina lavoratore.
     * Elimina il lavoratore selezionato sulla lista.
     * Se sulla lista non è selezionato alcun lavoratore, non fa nulla.
     *
     * @param actionEvent
     */
    @FXML
    private void onClickEliminaLavoratore(ActionEvent actionEvent) {
        LavoratoriDao lavoratoriDao = new LavoratoriDaoImpl();
        // Se è stato selezionato un lavoratore su listaLavoratori
        if(!listaLavoratori.getSelectionModel().getSelectedValues().isEmpty()){
            // Elimina dal database il lavoratore
            lavoratoriDao.deleteLavoratore(listaLavoratori.getSelectionModel().getSelectedValues().get(0).getID());
            // Pulisci i risultati di listaLavoratori
            lavoratori.clear();
            // Togli la selezione precedente (rimasta come residuo)
            listaLavoratori.getSelectionModel().clearSelection();
        }
    }
}
