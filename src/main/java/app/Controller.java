package app;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import appState.*;
import board.BoardHandler;
import sidebarState.HideState;
import sidebarState.TransitionState;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.util.Duration;

import java.util.ArrayList;

public class Controller {
    @FXML
    private Tab tabellaSpeciali;
    @FXML
    private Button about;
    @FXML
    private Button aiuto;
    @FXML
    private Button backArrow;
    @FXML
    private ChoiceBox<Integer> numOfPlayersChoiceBox;
    @FXML
    private HBox numOfPlayersHBox;
    @FXML
    private VBox pannelloVersione;
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
    private Button importa;
    @FXML
    private Button defaultB;
    @FXML
    private CheckBox lancioFinaleCB;
    @FXML
    private ImageView menu;
    @FXML
    private Button personalizza;
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
    private Group gridGroup;
    @FXML
    private Slider rotationSlider;
    @FXML
    private BorderPane borderPane;
    private TransitionState ts;
    private BoardHandler boardHandler;
    private appState.ApplicationState appState;
    private ArrayList<Node> savedSNodes, defaultSNodes, simulationSNodes, personalizationSNodes;


    @FXML
    void nextState(ActionEvent event) {
        appState = appState.nextState(simulationSNodes);
        simulazione.setText(appState.getSimulationButtonText());
        appState.show();
    }
    @FXML
    void salva(ActionEvent event) {
        boardHandler.saveBoard("saved.txt");
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
    void creaSerpente1(MouseEvent event) {
        boardHandler.createSnake((ImageView) event.getSource(),
                new Point2D(145,77),
                new Point2D(30, 548),
                event);
    }

    @FXML
    void creaSerpente2(MouseEvent event) {
        boardHandler.createSnake((ImageView) event.getSource(),
                new Point2D(224,22),
                new Point2D(0, 1348),
                event);
    }

    @FXML
    void creaSerpente3(MouseEvent event) {
        boardHandler.createSnake((ImageView) event.getSource(),
                new Point2D(22,108),
                new Point2D(673, 1122),
                event);
    }

    @FXML
    void creaSerpente4(MouseEvent event) {
        boardHandler.createSnake((ImageView) event.getSource(),
                new Point2D(31,90),
                new Point2D(357, 856),
                event);
    }

    @FXML
    void creaSerpente5(MouseEvent event) {
        boardHandler.createSnake((ImageView) event.getSource(),
                new Point2D(522,106),
                new Point2D(26, 1234),
                event);
    }

    @FXML
    void creaSerpente6(MouseEvent event) {
        boardHandler.createSnake((ImageView) event.getSource(),
                new Point2D(34,89),
                new Point2D(211, 710),
                event);
    }

    @FXML
    void cambiaIconaMenu(MouseEvent event) {
        if (event.getEventType() == MouseEvent.MOUSE_ENTERED)
            menu.setImage(new Image(System.getProperty("user.dir")+"\\src\\main\\resources\\icons\\Hamburger_icon.png"));
        else menu.setImage(new Image(System.getProperty("user.dir")+"\\src\\main\\resources\\icons\\Hamburger_iconB.png"));
    }

    @FXML
    void inserisciFile(ActionEvent event) {
        ApplicationState newState = new SavedState(savedSNodes, boardHandler);
        appState = appState.nextState(newState);
        simulazione.setText(appState.getSimulationButtonText());
        appState.show();
    }

    @FXML
    void pannelloAbout(ActionEvent event) {

    }

    @FXML
    void pannelloAiuto(ActionEvent event) {

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
        simulationSNodes.add(textAreaSimul);

        simulazione.setId("simulazione");

        rowChoiceBox.setValue(10);
        colChoiceBox.setValue(10);

        ChangeListener<Integer> changeListener=new ChangeListener<Integer>() {
            @Override
            public void changed(ObservableValue<? extends Integer> observableValue, Integer oldVal, Integer newVal) {
                boardHandler.createNewGrid(rowChoiceBox.getValue(), colChoiceBox.getValue());
            }
        };

        rowChoiceBox.getSelectionModel().selectedItemProperty().addListener(changeListener);
        colChoiceBox.getSelectionModel().selectedItemProperty().addListener(changeListener);

        rotationSlider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number oldVal, Number newVal) {
                boardHandler.rotate((double) newVal);
            }
        });

        numOfPlayersChoiceBox.setValue(5);
        textAreaSimul.setWrapText(true);

        boardHandler = new BoardHandler(rotationSlider, borderPane);
        appState = new DefaultState(defaultSNodes, boardHandler);
        appState.show();
    }
}