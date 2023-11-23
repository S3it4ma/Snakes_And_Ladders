package simulation;

public class BlockStrategy implements HandleStrategy {
    @Override
    public void handle(Simulation simulation) {
        simulation.show("Giocatore "+simulation.getCurrentPlayer().getName()+this);
        ((CardPlayer) simulation.getCurrentPlayer()).addCard();
    }

    @Override
    public String toString() {
        return " ha pescato una carta divieto di sosta!";
    }
}
