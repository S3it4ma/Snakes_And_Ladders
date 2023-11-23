package appState;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.util.Duration;
import board.BoardHandler;
import simulation.DeckSimulation;
import simulation.Simulation;

import java.util.ArrayList;

public class SimulationState extends  AppState {
    private final Timeline timeline;
    private final Simulation simulation;
    private ChoiceBox<Integer> numOfPlayersCB;
    private enum STATE {NULL, STARTED, STOPPED}
    private Button simulButton;
    private TextArea ta;
    private STATE state = STATE.NULL;

    public SimulationState(ArrayList<Node> nodes, BoardHandler bh, Simulation s) {
        super(nodes, bh);
        for (Node n : nodes) {
            if (n instanceof ChoiceBox<?>) {
                numOfPlayersCB = (ChoiceBox<Integer>) n;
            }
            if (n instanceof TextArea) ta = (TextArea) n;
            if (n instanceof Button b && b.getId().equals("simulation")) simulButton = (Button) n;
        }
        simulation = s;
        simulation.setBoardHandler(boardHandler);
        simulationButtonText = "Start";
        timeline = new Timeline(new KeyFrame(Duration.millis(1000), new EventHandler<>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                handleSimulation();
            }
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
    }

    @Override
    protected void showBoard() {
        boardHandler.makeBoardUnresponsive(true);
    }

    @Override
    public ApplicationState nextState(ApplicationState s) {
        boardHandler.clearPlayers();
        timeline.stop();
        setNodes(false);
        return s;
    }


    public void setSimulationSpeed(double newVal) {
        timeline.setRate(newVal);
    }

    public void doManualStep() {
        switch (state) {
            case NULL -> {
                initializeSimulation();
                state = STATE.STOPPED;
                simulation.simulationStep();
            }
            case STARTED -> {

            }
            default -> {
                handleSimulation();
            }
        }

    }

    private void handleSimulation() {
        if (!simulation.hasPlayerWon())
            simulation.simulationStep();
        else {
            timeline.stop();
            simulButton.setText("Start");
            state = STATE.NULL;
        }
    }
    private void initializeSimulation() {
        ta.clear();
        boardHandler.clearPlayers();
        simulation.setPlayerWin(false);
        simulation.setPlayer(numOfPlayersCB.getValue());
    }

    @Override
    public ApplicationState nextState(ArrayList<Node> sNodes) {
        switch (state) {
            case NULL -> {
                initializeSimulation();
                //System.out.println("instance "+(simulation instanceof DeckSimulation));
                timeline.play();
                state = STATE.STARTED;
                simulationButtonText = "Stop";
            }

            case STARTED -> {
                timeline.stop();
                state = STATE.STOPPED;
                simulationButtonText = "Start";
            }

            case STOPPED -> {
                timeline.play();
                state = STATE.STARTED;
                simulationButtonText = "Stop";
            }
        }
        return this;
    }
}
