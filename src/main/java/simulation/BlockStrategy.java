package simulation;

public class BlockStrategy extends HandleStrategy {
    @Override
    public void handle() {
        simulation.show("Giocatore "+simulation.getCurrentPlayer().getName()+this);
        ((CardPlayer) simulation.getCurrentPlayer()).addCard();
    }

    @Override
    public String toString() {
        return " ha pescato una carta divieto di sosta!";
    }
}
