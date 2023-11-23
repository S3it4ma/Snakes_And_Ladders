package simulation;

public class RandomStrategy implements HandleStrategy {
    @Override
    public void handle(Simulation simulation) {
        simulation.show("Giocatore "+simulation.getCurrentPlayer().getName()+this);
        ((DeckSimulation) simulation).getDeckCard();
    }

    @Override
    public String toString() {
        return " ha raggiunto una casella incognita! Pesca una carta...";
    }
}
