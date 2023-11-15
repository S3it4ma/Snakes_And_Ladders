package simulation;

public class DoubleSixDecorator extends SimulationDecorator {
    private int maxRoll = 3;

    public DoubleSixDecorator(SingleDiceSimulation simulation) {
        super(simulation);
    }

    @Override
    public void manageRoll(int n) {
        super.manageRoll(n);
        if (currentDiceValue == 12 && maxRoll > 0) {
            String text = "Il giocatore "+getCurrentPlayer().getName()+" ha fatto 12! Ritira i dadi";
            show(text);
            int value = rollDice();
            manageRoll(value);
            maxRoll--;
        }
        else if (maxRoll < 3) maxRoll = 3;
    }
}
