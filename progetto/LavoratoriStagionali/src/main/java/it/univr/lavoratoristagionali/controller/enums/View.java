package it.univr.lavoratoristagionali.controller.enums;

/**
 * Enum delle view, con il corrispettivo percorso dei file FXML nel progetto
 */
public enum View {
    LOGIN("fxml/loginView.fxml"),
    MAIN_MENU("fxml/menuView.fxml"),
    INSERISCI_LAVORATORE("fxml/inserisciLavoratoriView.fxml"),
    MENU_MODIFICA_LAVORATORE("fxml/menuModificaLavoratoreView.fxml"),
    MODIFICA_LAVORATORE("fxml/modificaLavoratoreView.fxml"),
    MENU_RICERCA_LAVORATORE("fxml/menuRicercaLavoratoreView.fxml"),
    DETTAGLI_RICERCA_LAVORATORE("fxml/dettagliRicercaLavoratoreView.fxml");

    private final String label;

    private View(String label){
        this.label = label;
    }

    public String getLabel(){
        return this.label;
    }
}
