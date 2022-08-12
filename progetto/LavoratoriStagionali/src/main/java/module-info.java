module it.univr.lavoratoristagionali {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens it.univr.lavoratoristagionali to javafx.fxml;
    exports it.univr.lavoratoristagionali;
}