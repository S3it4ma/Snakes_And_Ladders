package board;


import app.Controller;
import appState.SimulationState;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Popup;
import javafx.stage.Stage;

import java.io.File;
import java.util.ArrayList;


public class Main3 extends javafx.application.Application {

    @Override
    public void start(Stage stage) {
        BorderPane bp=new BorderPane();
        Scene scene=new Scene(bp, 1000, 700, Color.BEIGE);
        stage.setScene(scene);

        BoardHandler bh = new BoardHandler(new Slider(), bp);


        TextArea ta = new TextArea();
        ta.setWrapText(true);

        ChoiceBox<Integer> cb = new ChoiceBox<>();
        for (int base=2; base<=12; base++) {
            cb.getItems().add(base);
        }

        Button start = new Button("wow");
        start.setId("simulation");

        StackPane pane = new StackPane();
        VBox hb = new VBox();


        hb.getChildren().add(start);
        hb.getChildren().add(cb);
        pane.getChildren().add(hb);
        pane.getChildren().add(ta);

        bp.setRight(pane);

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
