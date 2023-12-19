package appState;

import board.BoardHandler;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;

import java.util.ArrayList;

public abstract class AppState implements ApplicationState {
    protected String simulationButtonText;
    protected final ArrayList<Node> nodes;
    protected final BoardHandler boardHandler;

    public AppState(ArrayList<Node> nodes, BoardHandler boardHandler) {
        this.boardHandler = boardHandler;
        this.nodes = nodes;
    }
    public String getSimulationButtonText() {
        return simulationButtonText;
    }
    protected void setNodes(boolean flag) {
        for (Node n : nodes) {
            n.setDisable(!flag);
            n.setVisible(flag);
            if (n instanceof CheckBox cb) cb.setSelected(false);
        }
    }

    protected abstract void showBoard();

    @Override
    public ApplicationState nextState(ApplicationState applicationState) {
        setNodes(false);
        return applicationState;
    }

    @Override
    public void show(){
        setNodes(true);
        showBoard();
    }
}
