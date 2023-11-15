package simulation;

import board.BoardHandler;
import javafx.scene.control.TextArea;
import javafx.scene.paint.Color;

import java.util.*;

public class Simulation {
    protected int currentIndex;
    protected Player[] player;
    protected Random random = new Random();
    protected HandleStrategy[] squares;
    protected Color[] color = {Color.WHITE, Color.BEIGE, Color.DARKGREY, Color.CYAN,
                               Color.PINK, Color.BLUE, Color.DARKMAGENTA, Color.DARKGOLDENROD,
                               Color.RED, Color.GREEN, Color.LIGHTCORAL, Color.DARKORANGE};
    protected ArrayList<Color> list;
    protected boolean playerWin;
    protected int currentDiceValue;
    protected TextArea textArea;
    protected BoardHandler boardHandler;

    protected Simulation() {}

    public Simulation(HandleStrategy[] squares, TextArea textArea) {
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
            p.setFill(list.remove(list.size()-1));
            player[i] = p;
            boardHandler.movePlayer(p.getCircle(), 1);
        }
    }

    public void setBoardHandler(BoardHandler boardHandler) {
        this.boardHandler = boardHandler;
    }

    public boolean hasPlayerWon() {
        return playerWin;
    }
    public int getCurrentDiceValue() {
        return currentDiceValue;
    }
    public Player getCurrentPlayer() {
        return player[currentIndex];
    }

    public void simulationStep() {
        player[currentIndex].play(this);

        if (playerWin) {
            textArea.appendText("Il giocatore "+player[currentIndex].getName()+" ha vinto!!\n");
            Player currentP = player[currentIndex];
            boardHandler.movePlayer(currentP.getCircle(), currentP.getSquare());
        }

        currentIndex = (currentIndex+1)%player.length;
    }

    public int rollDice() {
        currentDiceValue = random.nextInt(2, 12+1);
        return currentDiceValue;
    }

    public void show(String text) {
        textArea.appendText(text+"\n");
        Player currentP = player[currentIndex];
        boardHandler.movePlayer(currentP.getCircle(), currentP.getSquare());
    }

    public void manageRoll(int number) {

        Player currentP = player[currentIndex];
        int squareToGo = currentP.getSquare() + number;

        System.out.println(squareToGo + ": il gio "+ currentIndex + " ha fatto "+currentDiceValue);

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
        textArea.appendText(player[currentIndex].toString()+'\n');
        boardHandler.movePlayer(currentP.getCircle(), currentP.getSquare());

        squares[squareToGo-1].handle(this);
    }
}
