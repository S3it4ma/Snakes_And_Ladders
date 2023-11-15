package simulation;

public class BlockStrategy implements HandleStrategy {
    @Override
    public void handle(Simulation simulation) {
        ((CardPlayer) simulation.getCurrentPlayer()).addCard();
    }
}
