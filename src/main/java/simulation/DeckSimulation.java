package simulation;

import javafx.scene.control.TextArea;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class DeckSimulation extends Simulation {
    private final Deck deck;

    public DeckSimulation(HandleStrategy[] squares, TextArea textArea, Dice dice, Deck deck) {
        super(squares, textArea, dice);
        deck.shuffleDeck();
        this.deck = deck;
    }

    @Override
    public void setPlayer(int n) {
        list = new ArrayList<>(Arrays.asList(color));
        Collections.shuffle(list);

        player = new Player[n];
        currentIndex = 0;
        for (int i=0; i<n; i++) {
            Player p = new CardPlayer(""+i);
            p.circle.setFill(list.remove(list.size()-1));
            boardHandler.movePlayer(p.circle, 1);
            player[i] = p;
        }
    }

    void getDeckCard() {
        deck.getCard();
    }
}
