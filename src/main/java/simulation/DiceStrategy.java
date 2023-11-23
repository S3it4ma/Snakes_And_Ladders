package simulation;

public class DiceStrategy implements HandleStrategy {

    @Override
    public void handle(Simulation simulation) {
        simulation.show("Giocatore "+simulation.getCurrentPlayer().getName()+this);
        simulation.manageRoll(simulation.rollDice());
    }

    @Override
    public String toString() {
        return " ritira i dadi!";
    }
}