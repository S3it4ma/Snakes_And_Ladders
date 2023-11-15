package simulation;

public class RandomStrategy implements HandleStrategy {
    @Override
    public void handle(Simulation simulation) {
        String text = "Il giocatore "+simulation.getCurrentPlayer().getName()+" ha raggiunto una casella incognita! Pesca una carta";
        simulation.show(text);
        ((DeckSimulation) simulation).getDeckCard();
    }
}
