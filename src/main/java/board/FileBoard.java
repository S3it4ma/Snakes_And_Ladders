package board;

import app.ErrorAlert;
import javafx.geometry.Point2D;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.transform.Rotate;

import java.io.*;
import java.util.HashMap;
import java.util.StringTokenizer;

public class FileBoard {
    private final int CENTER_OF_LADDER = 75, CENTER_OF_SPECIAL_SQUARE = 70;
    //private final String RESOURCE_PATH = System.getProperty("user.dir")+"\\src\\main\\resources\\";
    private final HashMap<String, Point2D[]> connectorPoints;
    private String TYPE = "(dadi|puntoInterrogativo|panchina|locanda|molla|serpe[1-6]|scala[1-4])";
    private String SIMPLE_DOUBLE = "[\\-]?\\d+(.\\d+)?";
    private String ANCHORABLE_IMAGE = TYPE+"\\s+\\d{1,2}\\s+\\d{1,2}\\s+"+SIMPLE_DOUBLE+"(\\s+"+SIMPLE_DOUBLE+")?";

    public FileBoard(HashMap<String, Point2D[]> connectorPoints) {
        this.connectorPoints = connectorPoints;
    }

    /*public void write(Board board, String path) {
        String filePath = RESOURCE_PATH + path;
        try (PrintWriter printWriter = new PrintWriter(new FileWriter(filePath))) {

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
        } catch (IOException e) {
            e.printStackTrace();
        }
    }*/

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

    /*
    public Board read(String path) {
        Board board = new Board();
        String filePath = RESOURCE_PATH + path;

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath))) {
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
        } catch (Exception e) {
            e.printStackTrace();
        }
        return board;
    }*/

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
            Point2D anchor = new Point2D(CENTER_OF_LADDER, 0);
            img = new Connector(anchor, new Point2D(CENTER_OF_LADDER, image.getHeight()), image);
        }
        else {
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
