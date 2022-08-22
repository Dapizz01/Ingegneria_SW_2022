package it.univr.lavoratoristagionali.controller.validated;

import io.github.palexdev.materialfx.controls.MFXFilterComboBox;
import io.github.palexdev.materialfx.controls.MFXListView;
import io.github.palexdev.materialfx.validation.Constraint;
import io.github.palexdev.materialfx.validation.Severity;
import it.univr.lavoratoristagionali.controller.Errore;
import javafx.beans.binding.Bindings;
import javafx.scene.control.Label;

import java.util.ArrayList;
import java.util.List;

public class MFXListViewValidated<T> extends MFXListView<T> {
    private final MFXListView<T> listView;
    private final Label errorLabel;
    private final Errore[] flags;

    public MFXListViewValidated(MFXListView<T> listView, Label errorLabel, Errore ...flags){
        this.listView = listView;
        this.errorLabel = errorLabel;
        this.flags = flags;
    }

    // Non ci sono constraint e validate sulle MFXListView

    public boolean checkValid(){
        if(listView.getItems().size() == 0){
            showError("E' necessario inserire almeno un elemento");
            return false;
        }
        else{
            showDefault();
            return true;
        }
    }

    private void showError(String message){
        errorLabel.setText(message);
        errorLabel.setVisible(true);
        listView.setStyle("-fx-border-color: -mfx-red");
    }

    private void showCorrect(){
        errorLabel.setVisible(false);
        listView.setStyle("-fx-border-color: -mfx-green");
    }

    public void showDefault(){
        errorLabel.setVisible(false);
        listView.setStyle("-fx-border-color: -common-gradient");
    }
}
