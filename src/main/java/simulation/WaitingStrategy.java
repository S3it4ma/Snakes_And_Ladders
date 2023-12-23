package simulation;

public class WaitingStrategy extends HandleStrategy {
    private final int turnsToWait;

    public WaitingStrategy(int t) {
        turnsToWait = t;
    }

    @Override
    public void handle() {
        Player currentP = simulation.getCurrentPlayer();

        simulation.show("Giocatore "+currentP.getName()+this);
        ((WaitingPlayer) currentP).setTurnsToWait(turnsToWait);

        if (currentP instanceof CardPlayer cardPlayer && cardPlayer.getTurnsToWait()==0)
            simulation.show("Il giocatore "+currentP.getName()+"Ha usato una carta divieto di sosta");
    }

    @Override
    public String toString() {
        return " deve aspettare "+turnsToWait+" turni!";
    }
}
