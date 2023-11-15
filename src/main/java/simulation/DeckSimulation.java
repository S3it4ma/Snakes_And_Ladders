package simulation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class DeckSimulation extends SimulationDecorator {
    private final Deck deck;

    public DeckSimulation(Simulation simulation, Deck deck) {
        super(simulation);
        deck.shuffleDeck();
        this.deck = deck;
    }

    @Override
    public void setPlayer(int n) {
        list = new ArrayList<>(Arrays.asList(color));
        Collections.shuffle(list);

        simulation.player = new Player[n];
        currentIndex = 0;
        for (int i=0; i<n; i++) {
            Player p = new CardPlayer(""+i);
            p.setFill(list.remove(list.size()-1));
            boardHandler.movePlayer(p.getCircle(), 1);
            simulation.player[i] = p;
        }
    }

    void getDeckCard() {
        deck.getCard(this);
    }
}
