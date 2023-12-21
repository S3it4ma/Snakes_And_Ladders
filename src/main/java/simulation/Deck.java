package simulation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Random;

public class Deck {
    private final LinkedList<HandleStrategy> deck;

    public Deck(LinkedList<HandleStrategy> deck) {
        this.deck = deck;
    }

    public LinkedList<HandleStrategy> getDeck() {
        return deck;
    }

    public void shuffleDeck() {
        ArrayList<HandleStrategy> strategies = new ArrayList<>(deck);
        deck.clear();
        Random random = new Random();
        while (!strategies.isEmpty()) {
            int index = random.nextInt(strategies.size());
            deck.add(strategies.remove(index));
        }
    }

    public void getCard() {
        HandleStrategy handleStrategy = deck.removeFirst();
        handleStrategy.handle();
        deck.addLast(handleStrategy);
    }

    public void addCard(HandleStrategy handleStrategy) {
        deck.add(handleStrategy);
    }
}
