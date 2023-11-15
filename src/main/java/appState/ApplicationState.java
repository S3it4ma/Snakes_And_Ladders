package appState;

import javafx.scene.Node;
import java.util.ArrayList;


public interface ApplicationState {

    String getSimulationButtonText();
    void show();
    ApplicationState nextState(ApplicationState s);
    ApplicationState nextState(ArrayList<Node> nodes);
}
