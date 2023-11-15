package simulation;


public class JumpStrategy implements HandleStrategy {
    private final int squareToJumpTo;
    public JumpStrategy(int squareToJumpTo) {
        this.squareToJumpTo = squareToJumpTo;
    }

    @Override
    public void handle(Simulation simulation) {
        String text = "Il giocatore "+simulation.getCurrentPlayer().getName()+" deve spostarsi sulla casella "+squareToJumpTo+"!";
        simulation.getCurrentPlayer().setSquare(squareToJumpTo);
        simulation.show(text);
    }
}
