package board;

import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.scene.transform.Rotate;

import java.io.*;
import java.util.HashMap;
import java.util.StringTokenizer;

public class FileBoard {
    private final String RESOURCE_PATH = System.getProperty("user.dir")+"\\src\\main\\resources\\";
    //private final String TYPE_REGEX = "serpe|scala|puntoInterrogativo|locanda|molla|dadi|panchina";
    private final HashMap<String, int[]> connectorPoints = new HashMap<>();
    //private String path;

    public FileBoard() {
        connectorPoints.put("serpe1", new int[] {147, 77, 30, 548});
        connectorPoints.put("serpe2", new int[] {224, 22, 0, 1348});
        connectorPoints.put("serpe3", new int[] {22, 108, 673, 1122});
        connectorPoints.put("serpe4", new int[] {31, 90, 357, 856});
        connectorPoints.put("serpe5", new int[] {522, 106, 26, 1234});
        connectorPoints.put("serpe6", new int[] {34, 89, 211, 710});
    }

    public void write(Board board, String path) {
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
    }

    public Board read(String path) {
        Board board = new Board();
        String filePath = RESOURCE_PATH + path;

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath))) {
            String line = bufferedReader.readLine();
            //System.out.println(line);
            StringTokenizer stringTokenizer = new StringTokenizer(line, " ");
            int rows = Integer.parseInt(stringTokenizer.nextToken());
            int columns = Integer.parseInt(stringTokenizer.nextToken());
            //board.setCreationStrategy(new EmptyBoardStrategy(board, rows, columns, image));
            board.createBoard(rows, columns);

            while ((line = bufferedReader.readLine()) != null) {
                    StringTokenizer tokenizer = new StringTokenizer(line ," ");

                    String type = tokenizer.nextToken();

                    int rowIndex = Integer.parseInt(tokenizer.nextToken());
                    int colIndex = Integer.parseInt(tokenizer.nextToken());

                    double size = Double.parseDouble(tokenizer.nextToken());
                    double angle = 0;
                    if (tokenizer.hasMoreTokens()) {
                        angle = Double.parseDouble(tokenizer.nextToken());
                    }

                    //System.out.println(rowIndex+" "+colIndex+" angles: "+size+" "+angle);

                    Image image = new Image(RESOURCE_PATH + "icons\\"+ type + ".png");

                    AnchorableImage img;
                    if (type.contains("serpe")) {
                        int[] anchorPoints = connectorPoints.get(type);

                        Point2D anchor = new Point2D(anchorPoints[0], anchorPoints[1]);
                        img = new Connector(anchor,
                                            new Point2D(anchorPoints[2], anchorPoints[3]),
                                            image);
                    }
                    else if (type.contains("scala")) {
                        Point2D anchor = new Point2D(75, 0);
                        img = new Connector(anchor,
                                new Point2D(75, image.getHeight()),
                                image);
                    }
                    else {
                        img = new AnchorableImage(new Point2D(70, 70), image);
                    }

                    img.setWidth(size);
                    board.getSquares()[rowIndex][colIndex] = img;
                    board.getChildren().add(img);

                    double gridPaneWidth = 730;
                    double gridCellSize = (rows > columns) ? gridPaneWidth/rows : gridPaneWidth/columns;

                    img.setLayoutX(gridCellSize*colIndex + gridCellSize/2 - img.getAnchor().getX());
                    img.setLayoutY(gridCellSize*rowIndex + gridCellSize/2 - img.getAnchor().getY());

                    if (img instanceof Connector) img.getTransforms().add(new Rotate(angle, img.getAnchor().getX(), img.getAnchor().getY()));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return board;
    }
}
