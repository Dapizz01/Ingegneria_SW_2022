module it.univr.lavoratoristagionali.lavoratoristagionali {
    requires javafx.controls;
    requires javafx.fxml;


    opens it.univr.lavoratoristagionali.lavoratoristagionali to javafx.fxml;
    exports it.univr.lavoratoristagionali.lavoratoristagionali;
}