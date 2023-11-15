package simulation;

public class LastRollDecorator extends SimulationDecorator {

    public LastRollDecorator(SingleDiceSimulation simulation) {
        super(simulation);
    }

    @Override
    public int rollDice() {
        if (player[currentIndex].getSquare() >= squares.length - 6) {
            currentDiceValue = random.nextInt(1, 6+1);
            return currentDiceValue;
        }
        return super.rollDice();
    }
}
