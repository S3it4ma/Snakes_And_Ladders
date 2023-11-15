package board;

import javafx.geometry.Point2D;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public interface Facade {
    void createBoardFromFile(String path);
    void createSnake(ImageView img, Point2D fAnchor, Point2D sAnchor, MouseEvent e);
    void createLadder(ImageView img, MouseEvent e);
    void createSquare(ImageView img, MouseEvent e);
    void restoreConfigBoard();
    void makeBoardUnresponsive(boolean flag);
    void saveBoard(String path);
    void createNewGrid(int rows, int columns);
}
