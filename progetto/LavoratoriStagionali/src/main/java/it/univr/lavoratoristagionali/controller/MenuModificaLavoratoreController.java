package it.univr.lavoratoristagionali.controller;

import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXListView;
import io.github.palexdev.materialfx.controls.MFXTextField;
import io.github.palexdev.materialfx.controls.cell.MFXListCell;
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
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;

import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayDeque;
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        listaLavoratori.setConverter(FunctionalStringConverter.to(lavoratore -> {
            if(lavoratore != null)
                return lavoratore.getNomeLavoratore() +
                        " " + lavoratore.getCognomeLavoratore() +
                        ", nato il " + LocalDate.ofEpochDay(lavoratore.getDataNascita()) +
                        " a " + lavoratore.getComuneNascita();
            return "";
        }));

        lavoratori = FXCollections.observableArrayList(new ArrayList<Lavoratore>());
        listaLavoratori.setItems(lavoratori);

        lavoratoriDao = new LavoratoriDaoImpl();
    }

    @FXML
    private void onClickTornaAlMenu(ActionEvent actionEvent) {
        switchScene(getStageFromEvent(actionEvent), View.MAIN_MENU);
    }

    @FXML
    public void onClickCercaLavoratore(ActionEvent actionEvent) {
        lavoratori.setAll(lavoratoriDao.getLavoratori(nome.getText(), cognome.getText()));
        /*for(Lavoratore lavoratore : lavoratoriDao.getLavoratori(nome.getText(), cognome.getText())){
            lavoratori.add(lavoratore);
        }
        System.out.println(lavoratoriDao.getLavoratori(nome.getText(), cognome.getText()));
        System.out.println(lavoratori.isEmpty());*/
    }

    @FXML
    public void onClickModificaLavoratore(ActionEvent actionEvent) {
    }

    @FXML
    public void onClickEliminaLavoratore(ActionEvent actionEvent) {
    }
}
