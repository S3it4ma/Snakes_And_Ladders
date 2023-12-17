package simulation;


public class JumpStrategy extends HandleStrategy {
    private final int squareToJumpTo;
    public JumpStrategy(int squareToJumpTo) {
        this.squareToJumpTo = squareToJumpTo;
    }

    @Override
    public void handle() {
        simulation.getCurrentPlayer().setSquare(squareToJumpTo);
        simulation.show("Giocatore "+simulation.getCurrentPlayer().getName()+this);
    }

    @Override
    public String toString() {
        return " deve spostarsi sulla casella "+squareToJumpTo+"!";
    }
}
