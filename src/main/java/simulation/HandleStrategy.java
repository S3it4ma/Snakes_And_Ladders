package simulation;

public abstract class HandleStrategy {
    protected Simulation simulation;

    public void setSimulation(Simulation simulation) {
        this.simulation = simulation;
    }
    abstract void handle();
}
