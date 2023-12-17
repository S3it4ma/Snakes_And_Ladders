package simulation;

public class SpringStrategy extends HandleStrategy {

    @Override
    public void handle() {
        simulation.show("Giocatore "+simulation.getCurrentPlayer().getName()+this);
        simulation.manageRoll(simulation.getCurrentDiceValue());
    }

    @Override
    public String toString() {
        return " si sposta di nuovo dello stesso numero di caselle!!";
    }
}