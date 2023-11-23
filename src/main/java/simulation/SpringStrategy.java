package simulation;

public class SpringStrategy implements HandleStrategy {

    @Override
    public void handle(Simulation simulation) {
        simulation.show("Giocatore "+simulation.getCurrentPlayer().getName()+this);
        simulation.manageRoll(simulation.getCurrentDiceValue());
    }

    @Override
    public String toString() {
        return " si sposta di nuovo dello stesso numero di caselle!!";
    }
}