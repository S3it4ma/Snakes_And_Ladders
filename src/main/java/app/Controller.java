package app;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import appState.*;
import board.BoardHandler;
import appState.sidebarState.HideState;
import appState.sidebarState.TransitionState;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Controller {
    @FXML
    private ChoiceBox<Double> simulationVelocity;
    @FXML
    private HBox simulationVelocityHB;
    @FXML
    private Button stepManuale;
    @FXML
    private ChoiceBox<Integer> numOfPlayersChoiceBox;
    @FXML
    private HBox numOfPlayersHBox;
    @FXML
    private TextArea textAreaSimul;
    @FXML
    private ChoiceBox<Integer> colChoiceBox;
    @FXML
    private CheckBox dadoSingoloCB;
    @FXML
    private CheckBox divietoDiSostaCB;
    @FXML
    private CheckBox doppioSeiCB;
    @FXML
    private CheckBox lancioFinaleCB;
    @FXML
    private ImageView menu;
    @FXML
    private HBox rowHBox;
    @FXML
    private HBox colHBox;
    @FXML
    private ChoiceBox<Integer> rowChoiceBox;
    @FXML
    private Button salva;
    @FXML
    private Button simulazione;
    @FXML
    private AnchorPane slider;
    @FXML
    private TabPane tabPane;
    @FXML
    private Slider rotationSlider;
    @FXML
    private BorderPane borderPane;
    private TransitionState ts;
    private BoardHandler boardHandler;
    private appState.ApplicationState appState;
    private ArrayList<Node> savedSNodes, defaultSNodes, simulationSNodes, personalizationSNodes;
    private List<CheckBox> doubleDiceCB;


    @FXML
    void disattivaDadoSingolo(ActionEvent event) {
        CheckBox source = (CheckBox) event.getSource();

        if (source.isSelected()) {
            dadoSingoloCB.setDisable(true);
        }
        else {
            for (CheckBox checkBox : doubleDiceCB)
                if (checkBox != source && checkBox.isSelected())
                    return;
            dadoSingoloCB.setDisable(false);
        }
    }

    @FXML
    void disattivaDoppioDadoCB(ActionEvent event) {
        lancioFinaleCB.setDisable(!lancioFinaleCB.isDisable());
        doppioSeiCB.setDisable(!doppioSeiCB.isDisable());
    }

    @FXML
    void nextState(ActionEvent event) {
        appState = appState.nextState(simulationSNodes);
        simulazione.setText(appState.getSimulationButtonText());
        appState.show();
    }
    @FXML
    void salva(ActionEvent event) {
        if (!boardHandler.validateBoard()) {
            Alert errorAlert = new ErrorAlert(ErrorAlert.TYPE.WRONG_CONFIG);
            errorAlert.showAndWait();
        }

        FileChooser fc = new FileChooser();
        fc.setTitle("Scegli la configurazione salvata");
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text doc(*.txt)", "*.txt"));
        fc.setInitialDirectory(new File(System.getProperty("user.home")));
        Stage currentStage = (Stage) salva.getScene().getWindow();

        File selectedFile = fc.showSaveDialog(currentStage);

        try {
            boardHandler.saveBoard(selectedFile);
        } catch (IOException e) {
            Alert errorAlert = new ErrorAlert(ErrorAlert.TYPE.WRITING_ERROR);
            errorAlert.showAndWait();
        }
    }

    @FXML
    void creaCasella(MouseEvent event) {
        boardHandler.createSquare((ImageView) event.getSource(), event);
    }
    @FXML
    void creaScala(MouseEvent event) {
        boardHandler.createLadder((ImageView) event.getSource(), event);
    }
    @FXML
    void creaSerpente(MouseEvent event) {
        boardHandler.createSnake((ImageView) event.getSource(), event);
    }

    @FXML
    void cambiaIconaMenu(MouseEvent event) {
        if (event.getEventType() == MouseEvent.MOUSE_ENTERED)
            menu.setImage(new Image(System.getProperty("user.dir")+"\\src\\main\\resources\\icons\\Hamburger_icon.png"));
        else menu.setImage(new Image(System.getProperty("user.dir")+"\\src\\main\\resources\\icons\\Hamburger_iconB.png"));
    }

    @FXML
    void inserisciFile(ActionEvent event) {
        FileChooser fc = new FileChooser();
        fc.setTitle("Scegli la configurazione salvata");
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text doc(*.txt)", "*.txt"));
        fc.setInitialDirectory(new File(System.getProperty("user.home")));
        Stage currentStage = (Stage) textAreaSimul.getScene().getWindow();

        File selectedFile = fc.showOpenDialog(currentStage);
        if (selectedFile == null) return;

        try {
            boardHandler.createBoardFromFile(selectedFile);
        } catch(Exception e) {
            Alert errorAlert = new ErrorAlert(ErrorAlert.TYPE.WRONG_FORMAT);
            errorAlert.showAndWait();
            return;
        }

        ApplicationState newState = new SavedState(savedSNodes, boardHandler);
        appState = appState.nextState(newState);
        simulazione.setText(appState.getSimulationButtonText());
        appState.show();
    }

    @FXML
    void pannelloAbout(ActionEvent event) {
        Alert alert = new AboutAlert();
        alert.showAndWait();
    }

    @FXML
    void pannelloAiuto(ActionEvent event) {
        Alert alert = new HelpAlert();
        alert.showAndWait();
    }

    @FXML
    void pannelloConfigurazione(ActionEvent event) {
        ApplicationState newState = new PersonalizationState(personalizationSNodes, boardHandler);
        appState = appState.nextState(newState);
        simulazione.setText(appState.getSimulationButtonText());
        appState.show();
    }

    @FXML
    void pannelloDefault(ActionEvent event) {
        ApplicationState newState = new DefaultState(defaultSNodes, boardHandler);
        appState = appState.nextState(newState);
        simulazione.setText(appState.getSimulationButtonText());
        appState.show();
    }


    @FXML
    void stepManuale(ActionEvent event) {
        ((SimulationState) appState).doManualStep();
    }

    @FXML
    void spostaSlider(MouseEvent event) {
        ts=(TransitionState) ts.nextState();
        ts.show();
    }


    public void initialize() {
        ts=new HideState();
        ts.setNode(slider);
        ts.setTransition(new TranslateTransition(Duration.seconds(0.35),slider));
        ts.setFrom(0);
        ts.setTo(-234);

        ts.show();

        for (int base=5; base<=15; base++) {
            rowChoiceBox.getItems().add(base);
            colChoiceBox.getItems().add(base);
            numOfPlayersChoiceBox.getItems().add(base-3);
        }
        for (int i=5; i<=100; i+=5) simulationVelocity.getItems().add(((double) i)/10);

        defaultSNodes = new ArrayList<>(1);
        savedSNodes = new ArrayList<>(5);
        personalizationSNodes = new ArrayList<>(10);
        simulationSNodes = new ArrayList<>(2);

        defaultSNodes.add(simulazione);

        savedSNodes.add(dadoSingoloCB);
        savedSNodes.add(doppioSeiCB);
        savedSNodes.add(divietoDiSostaCB);
        savedSNodes.add(lancioFinaleCB);
        savedSNodes.add(simulazione);

        personalizationSNodes.addAll(savedSNodes);
        personalizationSNodes.add(rotationSlider);
        personalizationSNodes.add(tabPane);
        personalizationSNodes.add(slider);
        personalizationSNodes.add(colHBox);
        personalizationSNodes.add(rowHBox);
        personalizationSNodes.add(salva);

        simulationSNodes.add(simulazione);
        simulationSNodes.add(numOfPlayersHBox);
        simulationSNodes.add(numOfPlayersChoiceBox);
        simulationSNodes.add(simulationVelocityHB);
        simulationSNodes.add(simulationVelocity);
        simulationSNodes.add(textAreaSimul);
        simulationSNodes.add(stepManuale);

        rowChoiceBox.setValue(10);
        colChoiceBox.setValue(10);
        simulationVelocity.setValue(1d);

        doubleDiceCB = List.of(lancioFinaleCB, doppioSeiCB);

        ChangeListener<Integer> changeListener=new ChangeListener<Integer>() {
            @Override
            public void changed(ObservableValue<? extends Integer> observableValue, Integer oldVal, Integer newVal) {
                boardHandler.createNewGrid(rowChoiceBox.getValue(), colChoiceBox.getValue());
            }
        };
        ChangeListener<Double> velocityChangeListener=new ChangeListener<Double>() {
            @Override
            public void changed(ObservableValue<? extends Double> observableValue, Double oldVal, Double newVal) {
                ((SimulationState) appState).setSimulationSpeed(newVal);
            }
        };

        rowChoiceBox.getSelectionModel().selectedItemProperty().addListener(changeListener);
        colChoiceBox.getSelectionModel().selectedItemProperty().addListener(changeListener);
        simulationVelocity.getSelectionModel().selectedItemProperty().addListener(velocityChangeListener);

        rotationSlider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number oldVal, Number newVal) {
                boardHandler.rotate((double) newVal);
            }
        });

        numOfPlayersChoiceBox.setValue(5);
        textAreaSimul.setEditable(false);
        textAreaSimul.setWrapText(true);

        boardHandler = new BoardHandler(rotationSlider, borderPane);
        appState = new DefaultState(defaultSNodes, boardHandler);
        appState.show();
    }
}