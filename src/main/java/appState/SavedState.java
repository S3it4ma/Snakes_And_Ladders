package appState;

import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import board.BoardHandler;
import javafx.scene.control.TextArea;

import java.util.ArrayList;
import java.util.HashMap;

public class SavedState extends AppState {
    private final BoardHandler boardHandler;

    public SavedState(ArrayList<Node> nodes, BoardHandler bh) {
        super(nodes);
        this.boardHandler = bh;
        this.simulationButtonText = "Simulazione";
    }

    @Override
    protected void showBoard() {
        //parent.getChildren().remove(parent.getCenter());
        boardHandler.createBoardFromFile("saved.txt");
    }

    @Override
    public ApplicationState nextState(ArrayList<Node> sNodes) {
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
}
