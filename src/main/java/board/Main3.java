package board;


import appState.SimulationState;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Slider;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.ArrayList;


public class Main3 extends javafx.application.Application {

    @Override
    public void start(Stage stage) {
        BorderPane bp=new BorderPane();
        Scene scene=new Scene(bp, 1000, 700, Color.BEIGE);
        stage.setScene(scene);

        BoardHandler bh = new BoardHandler(new Slider(), bp);
        bh.createBoardFromFile("default.txt");

        TextArea ta = new TextArea();
        ta.setWrapText(true);

        ChoiceBox<Integer> cb = new ChoiceBox<>();
        for (int base=2; base<=12; base++) {
            cb.getItems().add(base);
        }

        Button start = new Button("wow");
        start.setId("simulation");

        VBox hb = new VBox();

        hb.getChildren().add(ta);
        hb.getChildren().add(start);
        hb.getChildren().add(cb);

        bp.setRight(hb);

        ArrayList<Node> list = new ArrayList<>();
        list.add(ta); list.add(start); list.add(cb);

        SimulationState simulationState = new SimulationState(list, bh, bh.prepareSimulation(null, ta));
        start.setOnAction(e->{
            simulationState.nextState(list);
        });
        simulationState.show();
        stage.show();
    }
}
