package app;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;

public class HelpAlert extends Alert {
    public HelpAlert() {
        super(Alert.AlertType.INFORMATION,
                "Quest'applicazione consente di effettuare una simulazione del popolare gioco scale e serpenti. " +
                        "Lo scopo principale è quello di raggiungere l'ultima casella del percorso prima degli altri giocatori. " +
                        "Nella versione tradizionale sono presenti scale, che consentono di raggiungere una casella maggiore e avvicinarsi al traguardo, " +
                        "e serpenti, che a partire dalla testa portano il giocatore indietro. Una griglia di default è pronta per l'utilizzo attraverso " +
                        "la sezione default del menù, ma si può anche creare una versione di gioco personalizzata che è possibile salvare per simulazioni " +
                        "future. Adesso un elenco delle caselle speciali e delle varie versioni possibili:\n\nCaselle speciali:\n" +
                        "   Panchina: il giocatore aspetta un turno;\n" +
                        "   Locanda: il giocatore aspetta tre turni;\n" +
                        "   Dadi: il giocatore ritira i dadi;\n" +
                        "   Molla: il giocatore si sposta dello stesso numero che ha appena fatto;\n" +
                        "   Punto interrogativo: il giocatore pesca una carta 'speciale' da un mazzo che ha l'effetto di una delle caselle precedenti.\n\n" +
                        "Varianti di gioco:\n" +
                        "   Dado singolo: nella versione tradizionale si utilizzano due dadi, mentre con questa opzione sarà possibile utilizzarne uno;\n" +
                        "   Doppio sei: se il giocatore ottiene dodici può ritirare;\n" +
                        "   Ultimo lancio: quando il giocatore si trova ad una differenza dall'ultima casella minore o uguale a sei può tirerà un solo dado;\n" +
                        "   Carta divieto di sosta: aggiunge al mazzo l'omonima carta, che potrà essere usata per non aspettare su una casella di sosta*.\n\n" +
                        "* Ciò vale solo se sono state aggiunte caselle speciali nella griglia.\n\n" +
                        "Regole per la configurazione personalizzata:\n" +
                        "   - non si possono inserire scale o serpenti ch iniziano o terminano nella stessa casella;\n" +
                        "   - non si possono inserire scale, serpenti o caselle speciali che tocchino l'ultima casella;\n" +
                        "   - la prima casella può contenere solo la coda di un serpente;\n" +
                        "   - daranno errore serpenti o scale i cui estremi si trovano fuori dalla griglia;\n" +
                        "   - non si possono inserire serpenti la cui coda porta ad una casella maggiore di quella in cui si trova la testa.\n",
                ButtonType.CLOSE);
        setTitle("Aiuto");
        setHeaderText("Scale e Serpenti");
        DialogPane dialogPane = getDialogPane();
        dialogPane.getStylesheets().add(getClass().getResource("dialog.css").toExternalForm());
        dialogPane.setMinSize(1000, 700);
    }
}
