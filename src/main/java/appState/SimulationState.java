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
import simulation.Simulation;

import java.util.ArrayList;

public class SimulationState extends  AppState {
    private final BoardHandler boardHandler;
    private final Timeline timeline;
    private final Simulation simulation;
    private ChoiceBox<Integer> numOfPlayersCB;
    private enum STATE {NULL, STARTED, STOPPED, MANUAL}
    private Button simulButton;
    private TextArea ta;
    private STATE state = STATE.NULL;

    public SimulationState(ArrayList<Node> nodes, BoardHandler bh, Simulation s) {
        super(nodes);
        for (Node n : nodes) {
            if (n instanceof ChoiceBox<?>) {
                numOfPlayersCB = (ChoiceBox<Integer>) n;
            }
            if (n instanceof TextArea) ta = (TextArea) n;
            if (n instanceof Button) simulButton = (Button) n;
        }
        simulation = s;
        boardHandler = bh;
        simulation.setBoardHandler(boardHandler);
        simulationButtonText = "Start";
        timeline = new Timeline(new KeyFrame(Duration.millis(1000), new EventHandler<>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if (!simulation.hasPlayerWon())
                    simulation.simulationStep();
                else {
                    timeline.stop();
                    simulButton.setText("Start");
                    state = STATE.NULL;
                }
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
        timeline.stop();
        setNodes(false);
        return s;
    }

    @Override
    public ApplicationState nextState(ArrayList<Node> sNodes) {
        switch (state) {
            case NULL -> {
                ta.clear();
                boardHandler.clearPlayers();
                simulation.setPlayerWin(false);
                simulation.setPlayer(numOfPlayersCB.getValue());
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

            case MANUAL -> {
                simulation.simulationStep();
            }
        }
        return this;
    }
}
