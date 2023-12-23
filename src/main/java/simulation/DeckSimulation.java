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
    protected Player factory(String name) {
        return new CardPlayer(name);
    }

    void getDeckCard() {
        deck.getCard();
    }
}
