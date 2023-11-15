package simulation;

import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;

public class Player {
    protected int square = 1;
    protected Circle circle = new Circle(20);
    protected String name;
    public Player(String name) {
        this.name=name;
        circle.setStrokeWidth(30);
        circle.setStroke(Color.TRANSPARENT);
    }
    public void setFill(Paint paint) {
        circle.setFill(paint);
    }
    public String getName() {
        return name;
    }
    public Circle getCircle() {
        return circle;
    }
    public int getSquare() {
        return square;
    }
    public void setSquare(int square) {
        this.square = square;
    }

    public void play(Simulation simulation) {
        int n = simulation.rollDice();
        simulation.manageRoll(n);
    }

    @Override
    public String toString() {
        return "Giocatore "+ name + " giunge alla casella " + square;
    }
}
