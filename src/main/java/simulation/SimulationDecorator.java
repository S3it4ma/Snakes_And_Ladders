package simulation;

import board.BoardHandler;

public class SimulationDecorator extends Simulation {
    protected Simulation simulation;

    public SimulationDecorator(Simulation simulation) {
        this.simulation = simulation;
    }

    @Override
    public void setPlayerWin(boolean playerWin) {
        simulation.setPlayerWin(playerWin);
    }
    @Override
    public void setPlayer(int n) {
        simulation.setPlayer(n);
        for (Player p : simulation.getPlayer()) p.setSimulation(this);
    }
    @Override
    protected Player[] getPlayer() {
        return simulation.getPlayer();
    }
    @Override
    public void setBoardHandler(BoardHandler boardHandler) {
        simulation.setBoardHandler(boardHandler);
    }
    @Override
    public void show(String text) {
        simulation.show(text);
    }
    @Override
    public void simulationStep() {
        simulation.simulationStep();
    }
    @Override
    public void manageRoll(int number) {
        simulation.manageRoll(number);
    }
    @Override
    protected void setCurrentDiceValue(int currentDiceValue) {
        simulation.setCurrentDiceValue(currentDiceValue);
    }
    @Override
    protected HandleStrategy[] getSquares() {
        return simulation.getSquares();
    }
    @Override
    public int rollDice() {
        return simulation.rollDice();
    }
    @Override
    public boolean hasPlayerWon() {
        return simulation.hasPlayerWon();
    }
    @Override
    public Player getCurrentPlayer() {
        return simulation.getCurrentPlayer();
    }
    @Override
    public int getCurrentDiceValue() {
        return simulation.getCurrentDiceValue();
    }
}
