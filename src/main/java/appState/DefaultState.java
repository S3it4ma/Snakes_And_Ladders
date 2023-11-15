package appState;

import javafx.scene.Node;
import javafx.scene.control.TextArea;
import board.BoardHandler;

import java.util.ArrayList;

public class DefaultState extends AppState {
    private final BoardHandler boardHandler;

    public DefaultState(ArrayList<Node> nodes, BoardHandler bh) {
        super(nodes);
        this.boardHandler = bh;
        this.simulationButtonText = "Simulazione";
    }

    @Override
    protected void showBoard() {
        boardHandler.createBoardFromFile("default.txt");
    }


    @Override
    public ApplicationState nextState(ArrayList<Node> sNodes) {
        setNodes(false);
        TextArea ta = null;
        for (Node n : sNodes) {
            if (n instanceof TextArea) {
                ta = (TextArea) n;
                ta.clear();
            }
        }
        return new SimulationState(sNodes,
                boardHandler,
                boardHandler.prepareSimulation(null, ta));
    }
}
