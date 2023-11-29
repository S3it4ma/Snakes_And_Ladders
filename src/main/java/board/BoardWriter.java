package board;

import javafx.scene.transform.Rotate;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class BoardWriter {
    public void write(Board board, File file) throws IOException {
        try (PrintWriter printWriter = new PrintWriter(new FileWriter(file))) {

            AnchorableImage[][] square = board.getSquares();
            printWriter.println(square.length+" "+square[0].length);

            for (int i=0; i < square.length; i++) {
                for (int j=0; j < square[0].length; j++) {
                    if (square[i][j] == null) continue;

                    AnchorableImage image = square[i][j];
                    printWriter.print(image+" "+ i +" "+ j + " "+ image.getCurrentImageWidth());

                    if (image instanceof Connector c) {
                        printWriter.print(" "+((Rotate) c.getTransforms().get(0)).getAngle());
                    }
                    printWriter.println();
                }
            }
        }
    }
}
