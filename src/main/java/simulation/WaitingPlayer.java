package simulation;

public class WaitingPlayer extends Player {

    public WaitingPlayer(String name) {
        super(name);
    }

    private boolean stopped;
    private int turnsToWait;

    public void setTurnsToWait(int turnsToWait) {
        this.turnsToWait = turnsToWait;
        stopped = true;
    }

    @Override
    public void play(Simulation simulation) {
        if (turnsToWait>0) {
            turnsToWait--;
            return;
        }
        stopped = false;
        super.play(simulation);
    }

    @Override
    public String toString() {
        if (stopped) {
            return "Il giocatore "+name+" salta il turno, "+turnsToWait+" rimanenti."; 
        }
        return super.toString();
    }
}
