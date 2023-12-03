package board;

import javafx.embed.swing.JFXPanel;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.scene.transform.Rotate;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.File;
import java.util.HashMap;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class ValidatorTest {

    private static final HashMap<String, Point2D[]> connectorPoints = new HashMap<>();
    private static final String defaultConfigPath = System.getProperty("user.dir")+"\\src\\main\\resources\\app\\default.txt";

    @ParameterizedTest(name = "[{index}]: isValid {0} => {1}")
    @MethodSource("localParameters")
    void isValid(Board board, boolean result) {
        JFXPanel panel = new JFXPanel();

        assertEquals(new Validator(board).isValid(), result);
    }


    static Stream<Arguments> localParameters() {
        JFXPanel panel = new JFXPanel();

        String iconPath = System.getProperty("user.dir")+"\\src\\main\\resources\\icons\\";

        connectorPoints.put("serpe1", new Point2D[] {new Point2D(145,77), new Point2D(30, 548)});
        connectorPoints.put("serpe2", new Point2D[] {new Point2D(224,22), new Point2D(0, 1348)});
        connectorPoints.put("serpe3", new Point2D[] {new Point2D(22,108), new Point2D(673, 1122)});
        connectorPoints.put("serpe4", new Point2D[] {new Point2D(31,90), new Point2D(357, 856)});
        connectorPoints.put("serpe5", new Point2D[] {new Point2D(522,106), new Point2D(26, 1234)});
        connectorPoints.put("serpe6", new Point2D[] {new Point2D(34,89), new Point2D(211, 710)});

        Board board1 = null;
        try {
            board1 = new BoardReader(connectorPoints).read(new File(defaultConfigPath));
        } catch (Exception e) {
            e.printStackTrace();
        }

        Connector firstSnake = new Connector(connectorPoints.get("serpe1")[0], connectorPoints.get("serpe1")[1], new Image(iconPath+"serpe1.png"));
        firstSnake.getTransforms().add(new Rotate());

        Image firstLadderImg = new Image(iconPath+"scala1.png");
        Connector firstLadder = new Connector(new Point2D(0, 75), new Point2D(0, firstLadderImg.getHeight()), firstLadderImg);
        firstLadder.getTransforms().add(new Rotate());

        Board board2 = new Board();
        board2.createBoard(9, 9);
        board2.getSquares()[0][0] = firstSnake;

        Board board3 = new Board();
        board3.createBoard(3, 3);
        board3.getSquares()[2][2] = firstSnake;

        Board board4 = new Board();
        board4.createBoard(10, 10);
        board4.getSquares()[0][6] = firstLadder;


        return Stream.of(
                Arguments.of(board1, true),
                Arguments.of(board2, false),
                Arguments.of(board3, false),
                Arguments.of(board4, false)
        );
    }
}