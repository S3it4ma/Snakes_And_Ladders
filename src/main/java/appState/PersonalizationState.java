package appState;


import app.ErrorAlert;
import javafx.scene.Node;
import javafx.scene.control.*;
import board.BoardHandler;
import java.util.ArrayList;
import java.util.HashMap;


public class PersonalizationState extends AppState {

    public PersonalizationState(ArrayList<Node> nodes, BoardHandler bh) {
        super(nodes, bh);
        this.simulationButtonText = "Simulazione";
    }

    @Override
    protected void showBoard() {
        boardHandler.disableSlider(true);
        boardHandler.restoreConfigBoard();
    }

    @Override
    public ApplicationState nextState(ArrayList<Node> sNodes) {
        if (boardHandler.validateBoard()) {
            HashMap<String, Boolean> choiceBoxHashMap = new HashMap<>();
            for (Node n : nodes) {
                if (n instanceof CheckBox cb) {
                    choiceBoxHashMap.put(cb.getText(), cb.isSelected());
                }
            }
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
                    boardHandler.prepareSimulation(choiceBoxHashMap, ta));
        }
        else {
            Alert alert = new ErrorAlert(ErrorAlert.TYPE.WRONG_CONFIG);
            alert.showAndWait();
            return this;
        }
    }
}
