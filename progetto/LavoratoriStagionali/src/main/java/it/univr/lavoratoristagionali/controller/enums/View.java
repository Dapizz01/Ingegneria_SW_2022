package it.univr.lavoratoristagionali.controller.enums;

/**
 * Enum delle view, con il corrispettivo percorso dei file FXML nel progetto
 */
public enum View {
    /**
     * Percorso della vista del login
     */
    LOGIN("fxml/loginView.fxml"),
    /**
     * Percorso della vista del menù principale
     */
    MAIN_MENU("fxml/menuView.fxml"),
    /**
     * Percorso della vista del form di inserimento di un nuovo lavoratore
     */
    INSERISCI_LAVORATORE("fxml/inserisciLavoratoriView.fxml"),
    /**
     * Percorso della vista del menù di modifica di un lavoratore
     */
    MENU_MODIFICA_LAVORATORE("fxml/menuModificaLavoratoreView.fxml"),
    /**
     * Percorso della vista del form di modifica di un lavoratore già esistente
     */
    MODIFICA_LAVORATORE("fxml/modificaLavoratoreView.fxml"),
    /**
     * Percorso della vista del form di ricerca di un lavoratore
     */
    RICERCA_LAVORATORE("fxml/ricercaLavoratoreView.fxml");

    private final String label;

    private View(String label){
        this.label = label;
    }

    public String getLabel(){
        return this.label;
    }
}
