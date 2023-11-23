package appState;

import app.ErrorAlert;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import board.BoardHandler;

import java.io.File;
import java.util.ArrayList;

public class DefaultState extends AppState {

    public DefaultState(ArrayList<Node> nodes, BoardHandler bh) {
        super(nodes, bh);
        this.simulationButtonText = "Simulazione";
    }

    @Override
    protected void showBoard() {
        try {
            boardHandler.createBoardFromFile(new File(System.getProperty("user.dir")+"\\src\\main\\resources\\app\\default.txt"));
        } catch (Exception e) {
            Alert errorAlert = new ErrorAlert(ErrorAlert.TYPE.WRONG_FORMAT);
            errorAlert.showAndWait();
        }
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
