package board;

import javafx.geometry.Point2D;
import javafx.scene.layout.GridPane;
import javafx.scene.transform.Rotate;

import java.util.Arrays;
import java.util.HashMap;


public class Validator {
    private final AnchorableImage[][] squares;
    private final GridPane gridPane;
    private final HashMap<Connector, int[]> connectorHashMap =new HashMap<>();
    private boolean isValid;

    public Validator(Board board) {
        this.squares = new AnchorableImage[board.getSquares().length][board.getSquares()[0].length];
        this.gridPane = board.getGrid();
        for (int i=0; i<squares.length; i++) {
            squares[i] = Arrays.copyOf(board.getSquares()[i], squares[0].length);
        }
        isValid = validate();
    }

    public boolean isValid() {
        return isValid;
    }

    private boolean validate() {
        int firstSquareColumn = (squares.length % 2 == 0) ? 0 : squares[0].length-1;
        boolean isLastSquareOccupied = squares[0][0] != null;
        if (isLastSquareOccupied) return false;


        for (int i=0; i<squares.length; i++) {
            for (int j=0; j<squares[0].length; j++) {

                if (!(squares[i][j] instanceof Connector connector)) continue;

                Point2D anchor = connector.getAnchor();
                Point2D secondAnchor = connector.getSecondAnchor();

                double width = gridPane.getCellBounds(0, 0).getWidth() - 0.3;
                double angle = ((Rotate) connector.getTransforms().get(0)).getAngle();

                Point2D anchorR = calcRotation(anchor.getX(), anchor.getY(), angle);
                Point2D sAnchorR = calcRotation(secondAnchor.getX(), secondAnchor.getY(), angle);

                double offsetX = sAnchorR.getX() - anchorR.getX() + anchor.getX() ;
                double offsetY = sAnchorR.getY() - anchorR.getY() + anchor.getY() ;

                System.out.println("x: "+connector.getLayoutX()+" y: "+connector.getLayoutY());
                double imgStartX = connector.getLayoutX() + offsetX;
                double imgStartY = connector.getLayoutY()  + offsetY;

                int rowIndex = (int) (imgStartY / width);
                int colIndex = (int) (imgStartX / width);

                if (imgStartX < 0 || imgStartY < 0 || rowIndex >= squares.length || colIndex >= squares[0].length)
                    return false;

                boolean secondAnchorIsBeforeFirst = (i == rowIndex) &&
                                ((rowIndex % 2 == 0 && colIndex < j) ||
                                 (rowIndex % 2 == 1 && colIndex > j));

                boolean overlappingConnector = (squares[rowIndex][colIndex] != null && squares[rowIndex][colIndex] != connector);
                if (overlappingConnector) return false;

                //System.out.println("is a snake? "+isSnake(connector)+", isSecondBeforeFirst: "+secondAnchorIsBeforeFirst+"; rowIndex, colIndex: ("+rowIndex + ", "+ colIndex+"); i, j: ("+i+", "+j+")");

                if (isSnake(connector)) {
                    if (rowIndex < i || secondAnchorIsBeforeFirst) return false;
                    connectorHashMap.put(connector, new int[] {rowIndex, colIndex});
                }
                else {
                    if (connectorHashMap.containsKey(connector)) continue;
                    if (rowIndex > i || !secondAnchorIsBeforeFirst) {
                        connectorHashMap.put(connector, new int[] {i, j});
                        squares[i][j] = null;
                        squares[rowIndex][colIndex] = connector;
                    }
                    else connectorHashMap.put(connector, new int[] {rowIndex, colIndex});
                }
            }
            boolean isFirstSquareOccupied = squares[squares.length-1][firstSquareColumn] != null;
            if (isFirstSquareOccupied) return false;
        }
        return true;
    }

    public HashMap<Connector, int[]> getConnectorHashMap() {
        return connectorHashMap;
    }
    public AnchorableImage[][] getSquares() {
        return squares;
    }

    private Point2D calcRotation(double x, double y, double oAngle) {
        double angle = Math.toRadians(oAngle);
        double xRotated = x * Math.cos(angle) - y * Math.sin(angle);
        double yRotated = x * Math.sin(angle) + y * Math.cos(angle);
        return new Point2D(xRotated, yRotated);
    }
    private boolean isSnake(Connector connector) {
        return connector.toString().matches("serpe[1-6]");
    }
}
