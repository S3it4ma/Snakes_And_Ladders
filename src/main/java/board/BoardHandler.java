package board;


import app.ErrorAlert;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Slider;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Circle;
import javafx.scene.transform.Rotate;
import simulation.LogicalBoard;
import simulation.Simulation;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;


public class BoardHandler {
    private Board configBoard;
    private Connector currentSelected;
    final Slider slider;
    private final BorderPane parent;
    private final HashSet<Circle> players = new HashSet<>();
    private final HashMap<String, Point2D[]> connectorPoints = new HashMap<>();
    public Connector getCurrentSelected() {
        return currentSelected;
    }

    public void setCurrentSelected(Connector currentSelected) {
        this.currentSelected = currentSelected;
    }

    public BoardHandler(Slider slider, BorderPane parent) {
        this.slider = slider;
        this.parent = parent;
        connectorPoints.put("serpe1", new Point2D[] {new Point2D(145,77), new Point2D(30, 548)});
        connectorPoints.put("serpe2", new Point2D[] {new Point2D(224,22), new Point2D(0, 1348)});
        connectorPoints.put("serpe3", new Point2D[] {new Point2D(22,108), new Point2D(673, 1122)});
        connectorPoints.put("serpe4", new Point2D[] {new Point2D(31,90), new Point2D(357, 856)});
        connectorPoints.put("serpe5", new Point2D[] {new Point2D(522,106), new Point2D(26, 1234)});
        connectorPoints.put("serpe6", new Point2D[] {new Point2D(34,89), new Point2D(211, 710)});
    }

    public void restoreConfigBoard() {
        if (configBoard==null) {
            configBoard = new Board();
            configBoard.createBoard(10, 10);
            configBoard.setOnMouseClicked(new DragAndResize(this));
        }
        parent.setCenter(configBoard);
    }

    public void makeBoardUnresponsive(boolean flag) {
        parent.getCenter().setMouseTransparent(flag);
    }

    public void saveBoard(File file) throws IOException {
        new BoardWriter().write(configBoard, file);
    }

    public void createNewGrid(int rows, int columns) {
        configBoard.getChildren().clear();
        configBoard.createBoard(rows, columns);
        configBoard.setOnMouseClicked(new DragAndResize(this));
    }

    public void createBoardFromFile(File file) throws Exception {
        Board newBoard = new BoardReader(connectorPoints).read(file);
        parent.setCenter(newBoard);
    }

    public void createSnake(ImageView img, MouseEvent event) {
        String url = img.getImage().getUrl();
        String name = url.substring(url.lastIndexOf("/")+1, url.indexOf("."));

        Point2D[] anchor = connectorPoints.get(name);
        Connector connector = new Connector(anchor[0], anchor[1], img.getImage());
        connector.setWidth(img.getImage().getWidth()/5.6);

        double x = connector.getAnchor().getX();
        double y = connector.getAnchor().getY();
        connector.getTransforms().add(new Rotate(0, x, y));

        initializeImage(connector, event);
    }

    public void createLadder(ImageView img, MouseEvent event) {
        Point2D firstAnchor = new Point2D(75, 0);
        Point2D secondAnchor = new Point2D(75, img.getImage().getHeight());

        Connector connector = new Connector(firstAnchor, secondAnchor, img.getImage());
        connector.setHeight(img.getImage().getHeight()/3.4);

        double x = connector.getAnchor().getX();
        double y = connector.getAnchor().getY();
        connector.getTransforms().add(new Rotate(0, x, y));


        initializeImage(connector, event);
    }

    public void createSquare(ImageView img, MouseEvent event) {
        Point2D anchor = new Point2D(70, 70);
        AnchorableImage image = new AnchorableImage(anchor, img.getImage());
        image.setWidth(configBoard.getGrid().getCellBounds(0, 0).getWidth()-18);

        initializeImage(image, event);
    }

    private void initializeImage(AnchorableImage connector, MouseEvent event) {
        connector.setVisible(false);
        configBoard.getChildren().add(connector);

        connector.setOnDragDetected(new DragAndDrop(connector));
        connector.setOnDragDone(e->{
            if (e.getTransferMode()!= TransferMode.COPY) {
                configBoard.getChildren().remove(connector);
            }
            e.consume();
        });

        ImageContextMenu contextMenu = new ImageContextMenu(connector);

        connector.setOnContextMenuRequested(e->{
            contextMenu.show(connector, e.getScreenX(), e.getScreenY());
        });
        connector.fireEvent(event);
    }


    public void rotate(double newVal) {
        ((Rotate) currentSelected.getTransforms().get(0)).setAngle(newVal);
        ((DragAndResize) configBoard.getOnMouseClicked()).adjustBorder(currentSelected);
    }

    public void addToBoard(Node node) {
        configBoard.getChildren().add(node);
    }

    public void disableSlider(boolean flag) {
        slider.setDisable(flag);
    }

    public boolean validateBoard() {
        Board currentVisibleBoard = (Board) parent.getCenter();
        return new Validator(currentVisibleBoard).isValid();
    }

    public Simulation prepareSimulation(HashMap<String, Boolean> list, TextArea textArea) {
        Board currentVisibleBoard = (Board) parent.getCenter();

        if (currentVisibleBoard.getOnMouseClicked() != null)
            ((DragAndResize) currentVisibleBoard.getOnMouseClicked()).activateBorder(false);

        Validator validator = new Validator(currentVisibleBoard);

        LogicalBoard logicalBoard = new LogicalBoard(validator, list, textArea);
        return logicalBoard.translate();
    }

    public void movePlayer(Circle circle, int square) {

        Board currentVisibleBoard = (Board) parent.getCenter();
        GridPane gridPane = currentVisibleBoard.getGrid();
        int rows = gridPane.getRowCount(),
            columns = gridPane.getColumnCount();

        if (!players.contains(circle)) {
            double cellSize = (rows > columns) ? Board.MAX_SIZE/rows : Board.MAX_SIZE/columns;
            circle.setRadius(cellSize / 4);
            circle.setStrokeWidth(cellSize / 2);
            players.add(circle);
        }

        gridPane.getChildren().remove(circle);

        int rowIndex = square / columns;
        int reversedRowIndex = rows - rowIndex - 1;

        int colIndex = square - (rowIndex * columns);
        if (reversedRowIndex % 2 == 0) colIndex = columns - (colIndex + 1);

        gridPane.add(circle, colIndex, reversedRowIndex);
    }

    public void clearPlayers() {
        Board currentVisibleBoard = (Board) parent.getCenter();
        GridPane gridPane = currentVisibleBoard.getGrid();
        for (Circle p : players) {
            gridPane.getChildren().remove(p);
        }
        players.clear();
    }
}
