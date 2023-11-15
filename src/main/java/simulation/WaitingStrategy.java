package simulation;

public class WaitingStrategy implements HandleStrategy {
    private final int turnsToWait;

    public WaitingStrategy(int t) {
        turnsToWait = t;
    }

    @Override
    public void handle(Simulation simulation) {
        ((WaitingPlayer) simulation.getCurrentPlayer()).setTurnsToWait(turnsToWait);
    }
}
