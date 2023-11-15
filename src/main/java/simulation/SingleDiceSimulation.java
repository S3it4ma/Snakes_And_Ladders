package simulation;

import javafx.scene.control.TextArea;

public class SingleDiceSimulation extends Simulation {
    public SingleDiceSimulation(HandleStrategy[] squares, TextArea textArea) {
        super(squares, textArea);
    }

    @Override
    public int rollDice() {
        return random.nextInt(1, 6+1);
    }
}
