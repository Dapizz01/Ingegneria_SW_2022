module it.univr.lavoratoristagionali {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires MaterialFX;
    requires VirtualizedFX;


    exports it.univr.lavoratoristagionali.controller;
    opens it.univr.lavoratoristagionali.controller to javafx.fxml;
    exports it.univr.lavoratoristagionali.controller.enums;
    opens it.univr.lavoratoristagionali.controller.enums to javafx.fxml;
}