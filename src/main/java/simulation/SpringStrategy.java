package simulation;

public class SpringStrategy implements HandleStrategy {

    @Override
    public void handle(Simulation simulation) {
        simulation.manageRoll(simulation.getCurrentDiceValue());
    }
}