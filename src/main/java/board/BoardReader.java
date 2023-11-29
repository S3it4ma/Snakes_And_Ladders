package board;

import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.scene.transform.Rotate;

import java.io.*;
import java.util.HashMap;
import java.util.StringTokenizer;

public class BoardReader {
    private final HashMap<String, Point2D[]> connectorPoints;
    private final String TYPE = "(dadi|puntoInterrogativo|panchina|locanda|molla|serpe[1-6]|scala[1-4])";
    private final String SIMPLE_DOUBLE = "[\\-]?\\d+(.\\d+)?";
    private final String ANCHORABLE_IMAGE = TYPE+"\\s+\\d{1,2}\\s+\\d{1,2}\\s+"+SIMPLE_DOUBLE+"(\\s+"+SIMPLE_DOUBLE+")?";

    public BoardReader(HashMap<String, Point2D[]> connectorPoints) {
        this.connectorPoints = connectorPoints;
    }


    public Board read(File file) throws Exception {
        Board board = new Board();

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
            String line = bufferedReader.readLine();
            StringTokenizer stringTokenizer = new StringTokenizer(line, " ");

            int rows = Integer.parseInt(stringTokenizer.nextToken());
            int columns = Integer.parseInt(stringTokenizer.nextToken());
            board.createBoard(rows, columns);

            while ((line = bufferedReader.readLine()) != null) {
                StringTokenizer tokenizer = new StringTokenizer(line ," ");

                if (line.matches(ANCHORABLE_IMAGE)) handleAnchorableImage(tokenizer, board, rows, columns);
                else throw new Exception();
            }
        }
        return board;
    }

    private void handleAnchorableImage(StringTokenizer tokenizer, Board board, int rows, int columns) {
        String type = tokenizer.nextToken();

        int rowIndex = Integer.parseInt(tokenizer.nextToken());
        int colIndex = Integer.parseInt(tokenizer.nextToken());

        double size = Double.parseDouble(tokenizer.nextToken());
        double angle = 0;
        if (tokenizer.hasMoreTokens()) {
            angle = Double.parseDouble(tokenizer.nextToken());
        }

        Image image = new Image(System.getProperty("user.dir")+"\\src\\main\\resources\\" + "icons\\"+ type + ".png");

        AnchorableImage img;

        if (type.contains("serpe")) {
            Point2D[] anchor = connectorPoints.get(type);
            img = new Connector(anchor[0], anchor[1], image);
        }
        else if (type.contains("scala")) {
            int CENTER_OF_LADDER = 75;

            Point2D anchor = new Point2D(CENTER_OF_LADDER, 0);
            img = new Connector(anchor, new Point2D(CENTER_OF_LADDER, image.getHeight()), image);
        }
        else {
            int CENTER_OF_SPECIAL_SQUARE = 70;

            img = new AnchorableImage(new Point2D(CENTER_OF_SPECIAL_SQUARE, CENTER_OF_SPECIAL_SQUARE), image);
        }

        img.setWidth(size);
        board.getSquares()[rowIndex][colIndex] = img;
        board.getChildren().add(img);

        double gridPaneWidth = Board.MAX_SIZE;
        double gridCellSize = (rows > columns) ? gridPaneWidth/rows : gridPaneWidth/columns;

        img.setLayoutX(gridCellSize * colIndex + gridCellSize/2 - img.getAnchor().getX());
        img.setLayoutY(gridCellSize * rowIndex + gridCellSize/2 - img.getAnchor().getY());

        if (img instanceof Connector)
            img.getTransforms().add(new Rotate(angle, img.getAnchor().getX(), img.getAnchor().getY()));
    }
}
