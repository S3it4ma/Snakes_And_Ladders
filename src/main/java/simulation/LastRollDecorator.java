package simulation;

import java.util.Random;

public class LastRollDecorator extends SimulationDecorator {

    public LastRollDecorator(Simulation simulation) {
        super(simulation);
    }

    @Override
    public int rollDice() {
        if (simulation.getCurrentPlayer().getSquare() >= simulation.getSquares().length - 6) {
            simulation.setCurrentDiceValue(new Random().nextInt(1, 6+1));
            return simulation.getCurrentDiceValue();
        }
        return super.rollDice();
    }
}
