package simulation;

public class WaitingPlayer extends Player {

    public WaitingPlayer(String name) {
        super(name);
    }
    protected int turnsToWait;

    public void setTurnsToWait(int turnsToWait) {
        this.turnsToWait = turnsToWait;
    }

    @Override
    void play() {
        if (turnsToWait>0) {
            turnsToWait--;
            simulation.show("Il giocatore "+name+" salta il turno, "+turnsToWait+" rimanenti.");
            return;
        }
        super.play();
    }
}
