package simulation;

public class CardPlayer extends WaitingPlayer {
    private int stopCard = 0;

    public CardPlayer(String name) {
        super(name);
    }
    
    public void addCard() {
        stopCard++;
    }

    @Override
    public void setTurnsToWait(int number) {
        if (stopCard > 0) {
            stopCard--;
            return;
        }
        super.setTurnsToWait(number);
    }
}
