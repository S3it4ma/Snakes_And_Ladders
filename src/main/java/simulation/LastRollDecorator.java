package simulation;

public class LastRollDecorator extends SimulationDecorator {

    public LastRollDecorator(Simulation simulation) {
        super(simulation);
    }

    @Override
    public int rollDice() {
        if (player[currentIndex].getSquare() >= squares.length - 6) {
            currentDiceValue = dice.rollDice();
            return currentDiceValue;
        }
        return super.rollDice();
    }
}
