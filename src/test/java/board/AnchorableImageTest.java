package board;

import javafx.embed.swing.JFXPanel;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class AnchorableImageTest {

    @ParameterizedTest(name = "[{index}]: URL of {0} => {1}")
    @MethodSource("localParameters")
    void testToString(AnchorableImage image, String string) {
        assertEquals(image.toString(), string);
    }
    static Stream<Arguments> localParameters() {
        JFXPanel panel = new JFXPanel();
        String iconPath = System.getProperty("user.dir")+"\\src\\main\\resources\\icons\\";
        Point2D point = new Point2D(0, 0);
        return Stream.of(
                Arguments.of(new AnchorableImage(point, new Image(iconPath+"scala1.png")), "scala1"),
                Arguments.of(new AnchorableImage(point, new Image(iconPath+"serpe1.png")), "serpe1"),
                Arguments.of(new AnchorableImage(point, new Image(iconPath+"dadi.png")), "dadi"),
                Arguments.of(new AnchorableImage(point, new Image(iconPath+"molla.png")), "molla"),
                Arguments.of(new AnchorableImage(point, new Image(iconPath+"puntoInterrogativo.png")), "puntoInterrogativo")
        );
    }
}