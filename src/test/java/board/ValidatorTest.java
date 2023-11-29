package board;

import javafx.embed.swing.JFXPanel;
import javafx.geometry.Point2D;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class ValidatorTest {

    private final HashMap<String, Point2D[]> connectorPoints = new HashMap<>();
    private final String defaultConfigPath = System.getProperty("user.dir")+"\\src\\main\\resources\\app\\default.txt";

    @Test
    void isValid() {
        JFXPanel panel = new JFXPanel();

        connectorPoints.put("serpe1", new Point2D[] {new Point2D(145,77), new Point2D(30, 548)});
        connectorPoints.put("serpe2", new Point2D[] {new Point2D(224,22), new Point2D(0, 1348)});
        connectorPoints.put("serpe3", new Point2D[] {new Point2D(22,108), new Point2D(673, 1122)});
        connectorPoints.put("serpe4", new Point2D[] {new Point2D(31,90), new Point2D(357, 856)});
        connectorPoints.put("serpe5", new Point2D[] {new Point2D(522,106), new Point2D(26, 1234)});
        connectorPoints.put("serpe6", new Point2D[] {new Point2D(34,89), new Point2D(211, 710)});

        Validator validator = null;
        try {
            Board board = new BoardReader(connectorPoints).read(new File(defaultConfigPath));
            validator = new Validator(board);
        } catch (Exception e) {
            e.printStackTrace();
        }

        assertTrue(validator.isValid());
    }
}