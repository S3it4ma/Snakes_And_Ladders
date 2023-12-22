package simulation;

import java.util.Random;

public class LastRollDecorator extends SimulationDecorator {
    private static final int SINGLE_MAX_VALUE = 6;
    public LastRollDecorator(Simulation simulation) {
        super(simulation);
    }

    @Override
    public int rollDice() {
        if (simulation.getCurrentPlayer().getSquare() >= simulation.getSquares().length - SINGLE_MAX_VALUE) {
            int value = new Random().nextInt(1, SINGLE_MAX_VALUE+1);
            simulation.setCurrentDiceValue(value);
            return value;
        }
        return super.rollDice();
    }
}
