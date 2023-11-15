package appState;


import javafx.scene.Node;
import javafx.scene.control.*;
import board.BoardHandler;

import java.util.ArrayList;
import java.util.HashMap;


public class PersonalizationState extends AppState {
    private final BoardHandler boardHandler;

    public PersonalizationState(ArrayList<Node> nodes, BoardHandler bh) {
        super(nodes);
        this.boardHandler = bh;
        this.simulationButtonText = "Simulazione";
    }

    @Override
    protected void showBoard() {
        //parent.getChildren().remove(parent.getCenter());
        boardHandler.disableSlider(true);
        boardHandler.restoreConfigBoard();
    }

    @Override
    public ApplicationState nextState(ArrayList<Node> sNodes) {
        if (boardHandler.validateBoard()) {
            setNodes(false);
            HashMap<String, CheckBox> choiceBoxHashMap = new HashMap<>();
            for (Node n : nodes) {
                if (n instanceof CheckBox cb) {
                    choiceBoxHashMap.put(cb.getText(), cb);
                }
            }
            TextArea ta = null;
            for (Node n : sNodes) {
                if (n instanceof TextArea) {
                    ta = (TextArea) n;
                    ta.clear();
                }
            }
            return new SimulationState(sNodes,
                    boardHandler,
                    boardHandler.prepareSimulation(choiceBoxHashMap, ta));
        }
        else return this;
    }
}
