package simulation;

public class WaitingStrategy implements HandleStrategy {
    private final int turnsToWait;

    public WaitingStrategy(int t) {
        turnsToWait = t;
    }

    @Override
    public void handle(Simulation simulation) {
        simulation.show("Giocatore "+simulation.getCurrentPlayer().getName()+this);
        ((WaitingPlayer) simulation.getCurrentPlayer()).setTurnsToWait(turnsToWait);
    }

    @Override
    public String toString() {
        return " deve aspettare "+turnsToWait+" turni!";
    }
}
