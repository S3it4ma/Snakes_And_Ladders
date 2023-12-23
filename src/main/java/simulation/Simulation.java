package simulation;

import board.BoardHandler;
import javafx.scene.control.TextArea;
import javafx.scene.paint.Color;

import java.util.*;

public class Simulation {
    protected int currentIndex;
    protected Player[] player;
    protected HandleStrategy[] squares;
    protected static Color[] color = {Color.WHITE, Color.BEIGE, Color.DARKGREY, Color.CYAN,
                               Color.PINK, Color.BLUE, Color.DARKMAGENTA, Color.DARKGOLDENROD,
                               Color.RED, Color.GREEN, Color.LIGHTCORAL, Color.DARKORANGE};
    protected ArrayList<Color> list;
    protected boolean playerWin;
    protected int currentDiceValue;
    protected TextArea textArea;
    protected BoardHandler boardHandler;
    protected enum Dice {
        SINGLE (1, 6),
        DOUBLE (2, 12);

        private final Random random = new Random();
        private final int min;
        private final int max;

        Dice(int min, int max) {
            this.min = min;
            this.max = max;
        }
        int rollDice() {
            return random.nextInt(min, max+1);
        }
    }

    protected Dice dice;
    protected Simulation() {}

    public Simulation(HandleStrategy[] squares, TextArea textArea, Dice dice) {
        this.dice = dice;
        this.textArea = textArea;
        this.squares = squares;
    }

    public void setPlayerWin(boolean playerWin) {
        this.playerWin = playerWin;
    }

    public void setPlayer(int n) {
        list = new ArrayList<>(Arrays.asList(color));
        Collections.shuffle(list);

        currentIndex = 0;
        player = new Player[n];
        for (int i=0; i<n; i++) {
            WaitingPlayer p = new WaitingPlayer(""+i);
            p.circle.setFill(list.remove(list.size()-1));
            p.setSimulation(this);
            player[i] = p;
            boardHandler.movePlayer(p.circle, 0);
        }
    }
    public void setBoardHandler(BoardHandler boardHandler) {
        this.boardHandler = boardHandler;
    }
    public boolean hasPlayerWon() {
        return playerWin;
    }
    int getCurrentDiceValue() {
        return currentDiceValue;
    }
    protected void setCurrentDiceValue(int currentDiceValue) {
        this.currentDiceValue = currentDiceValue;
    }
    protected Player[] getPlayer() {
        return player;
    }
    protected HandleStrategy[] getSquares() {
        return squares;
    }
    Player getCurrentPlayer() {
        return player[currentIndex];
    }

    public void simulationStep() {
        player[currentIndex].play();

        if (playerWin) {
            show("Il giocatore "+player[currentIndex].getName()+" ha vinto!!\n");
        }

        currentIndex = (currentIndex+1)%player.length;
    }


    public int rollDice() {
        currentDiceValue = dice.rollDice();
        return currentDiceValue;
    }

    public void show(String text) {
        textArea.appendText(text+"\n");
        Player currentP = player[currentIndex];
        boardHandler.movePlayer(currentP.circle, currentP.getSquare()-1);
    }

    public void manageRoll(int number) {
        Player currentP = player[currentIndex];
        int squareToGo = currentP.getSquare() + number;

        if (squareToGo == squares.length) {
            currentP.setSquare(squareToGo);
            textArea.appendText(player[currentIndex].toString()+'\n');
            playerWin = true;
            return;
        }

        if (squareToGo > squares.length) {
            int offset = squareToGo - squares.length;
            squareToGo = squares.length - offset;
        }

        currentP.setSquare(squareToGo);
        show(currentP.toString());

        squares[squareToGo-1].handle();
    }
}
