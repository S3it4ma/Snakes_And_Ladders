package simulation;

public class DiceStrategy implements HandleStrategy {

    @Override
    public void handle(Simulation simulation) {
        String text = "Il giocatore "+simulation.getCurrentPlayer().getName()+" ritira i dadi";
        simulation.show(text);
        simulation.manageRoll(simulation.rollDice());
    }
}