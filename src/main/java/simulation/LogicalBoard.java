package simulation;

import board.*;
import javafx.scene.control.TextArea;
import java.util.HashMap;
import java.util.LinkedList;

public class LogicalBoard {
    private final Validator validator;
    private final HashMap<String, Boolean> list;
    private final TextArea textArea;
    private Deck deck = null;

    public LogicalBoard(Validator validator, HashMap<String, Boolean> list, TextArea textArea) {
        this.list = list;
        this.validator = validator;
        this.textArea = textArea;
    }

    public Simulation translate() {
        AnchorableImage[][] grid = validator.getSquares();

        int rows = grid.length;
        int columns = grid[0].length;

        HandleStrategy[] squares = new HandleStrategy[rows*columns];

        int n = 0;
        for (int i=rows-1; i>=0; i--) {
            int k = (i % 2 == 0) ? columns-1 : 0;
            for (int j=0; j<columns; j++) {
                squares[n] = findStrategy(grid[i][k]);

                k = (i % 2 == 0) ? k-1 : k+1;
                n++;
            }
        }

        Simulation.Dice dice = Simulation.Dice.DOUBLE;

        boolean isDefaultConfiguration = (list == null);

        if (isDefaultConfiguration) {
            Simulation s = new Simulation(squares, textArea, dice);
            for (HandleStrategy handleStrategy: squares) handleStrategy.setSimulation(s);
            return s;
        }

        Simulation s;

        if (list.get("Dado singolo")) {
            dice = Simulation.Dice.SINGLE;
        }

        if (deck != null) {
            if (list.get("Carta divieto di sosta")) {
                deck.addCard(new BlockStrategy());
            }
            s = new DeckSimulation(squares, textArea, dice, deck);
            for (HandleStrategy handleStrategy : deck.getDeck()) handleStrategy.setSimulation(s);
        }
        else s = new Simulation(squares, textArea, dice);

        if (list.get("Doppio sei")) {
            s = new DoubleSixDecorator(s);
        }
        if (list.get("Lancio finale")) {
            s = new LastRollDecorator(s);
        }

        for (HandleStrategy handleStrategy: squares) handleStrategy.setSimulation(s);
        return s;
    }

    private HandleStrategy findStrategy(AnchorableImage image) {
        if (image == null) return new NoStrategy();

        HandleStrategy result;

        switch (image.toString()) {
            case "dadi" -> {
                result = new DiceStrategy();
            }
            case "locanda" -> {
                result = new WaitingStrategy(3);
            }
            case "panchina" -> {
                result = new WaitingStrategy(1);
            }
            case "molla" -> {
                result = new SpringStrategy();
            }
            case "puntoInterrogativo" -> {
                result = new RandomStrategy();
                if (deck == null) {
                    LinkedList<HandleStrategy> list = new LinkedList<>();
                    list.add(new DiceStrategy());
                    list.add(new WaitingStrategy(1));
                    list.add(new WaitingStrategy(3));
                    list.add(new SpringStrategy());
                    deck = new Deck(list);
                }
            }
            default -> {
                Connector c = (Connector) image;

                int rows = validator.getSquares().length,
                    columns = validator.getSquares()[0].length,
                    rowIndex = validator.getConnectorHashMap().get(c)[0],
                    colIndex = validator.getConnectorHashMap().get(c)[1];

                int rowToGo = (rows-(rowIndex+1)) * columns;
                int squareToGo = (rowIndex%2 == 0) ? rowToGo + columns - colIndex : rowToGo + (colIndex+1);
                result = new JumpStrategy(squareToGo);
            }
        }
        return  result;
    }
}