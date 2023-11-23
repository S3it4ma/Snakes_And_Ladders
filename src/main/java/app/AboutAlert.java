package app;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;

public class AboutAlert extends Alert{

    public AboutAlert() {
        super(Alert.AlertType.NONE,
                "Progetto di Ingegneria del software progettato da:\n\n" +
                   "   - Costa Paolo\n     Università della Calabria: matricola 234558\n",
                ButtonType.CLOSE);
        setTitle("About");
        DialogPane dialogPane = getDialogPane();
        dialogPane.getStylesheets().add(getClass().getResource("dialog.css").toExternalForm());
        dialogPane.setMinSize(300, 500);
    }
}
