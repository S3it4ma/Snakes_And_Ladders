package simulation;

public class RandomStrategy extends HandleStrategy {
    @Override
    public void handle() {
        simulation.show("Giocatore "+simulation.getCurrentPlayer().getName()+this);
        ((DeckSimulation) simulation).getDeckCard();
    }

    @Override
    public String toString() {
        return " ha raggiunto una casella incognita! Pesca una carta...";
    }
}
