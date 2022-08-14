package it.univr.lavoratoristagionali.controller;

public enum View {
    LOGIN("fxml/loginView.fxml"),
    MAIN_MENU("fxml/menuView.fxml"),
    INSERISCI_LAVORATORE("fxml/inserisciLavoratoriView.fxml");

    private final String label;

    private View(String label){
        this.label = label;
    }

    public String getLabel(){
        return this.label;
    }
}
