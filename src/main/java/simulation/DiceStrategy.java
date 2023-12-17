package simulation;

public class DiceStrategy extends HandleStrategy {

    @Override
    public void handle() {
        simulation.show("Giocatore "+simulation.getCurrentPlayer().getName()+this);
        simulation.manageRoll(simulation.rollDice());
    }

    @Override
    public String toString() {
        return " ritira i dadi!";
    }
}