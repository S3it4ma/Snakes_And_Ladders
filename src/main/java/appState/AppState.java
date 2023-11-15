package appState;

import javafx.scene.Node;
import java.util.ArrayList;

public abstract class AppState implements ApplicationState {
    protected String simulationButtonText;
    protected ArrayList<Node> nodes;

    public AppState(ArrayList<Node> nodes) {
        this.nodes = nodes;
    }

    public String getSimulationButtonText() {
        return simulationButtonText;
    }
    public void setNodes(boolean flag) {
        for (Node n : nodes) {
            n.setDisable(!flag);
            n.setVisible(flag);
            n.setMouseTransparent(!flag);
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
