package app;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;

public class ErrorAlert extends Alert {
    public enum TYPE {
        WRITING_ERROR("Errore nella creazione del file:\nverifica che non esista già un file con lo stesso nome"),
        WRONG_FORMAT("Errore nella creazione della griglia: file o formato danneggiato"),
        WRONG_CONFIG("Configurazione non idonea:\nper maggiori informazioni consultare la sezione aiuto.");
        private String text;
        TYPE(String string) {
            text = string;
        }
    }
    public ErrorAlert(TYPE type) {
        super(AlertType.ERROR, type.text, ButtonType.CLOSE);
        setTitle("Errore");
        DialogPane dialogPane = getDialogPane();
        dialogPane.getStylesheets().add(getClass().getResource("dialog.css").toExternalForm());
        dialogPane.setMinSize(500, 200);
    }
}
