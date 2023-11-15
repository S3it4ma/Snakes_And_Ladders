package board;


import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Slider;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Circle;
import javafx.scene.transform.Rotate;
import board.events.DragAndDrop;
import board.events.DragAndResize;
import simulation.LogicalBoard;
import simulation.Simulation;

import java.util.HashMap;
import java.util.HashSet;


public class BoardHandler implements Facade {
    private Board configBoard;
    private Connector currentSelected;
    private final Slider slider;
    private final BorderPane parent;
    private final HashSet<Circle> players = new HashSet<>();

    public Connector getCurrentSelected() {
        return currentSelected;
    }

    public void setCurrentSelected(Connector currentSelected) {
        this.currentSelected = currentSelected;
    }

    public BoardHandler(Slider slider, BorderPane parent) {
        this.slider = slider;
        this.parent = parent;
    }

    @Override
    public void restoreConfigBoard() {
        if (configBoard==null) {
            configBoard = new Board();
            //board.setCreationStrategy(new EmptyBoardStrategy(board, 10, 10, image));
            configBoard.createBoard(10, 10);
            configBoard.setOnMouseClicked(new DragAndResize(this));
        }
        parent.setCenter(configBoard);
    }

    @Override
    public void makeBoardUnresponsive(boolean flag) {
        parent.getCenter().setMouseTransparent(flag);
    }

    @Override
    public void saveBoard(String path) {
        new FileBoard().write(configBoard, path);
    }

    @Override
    public void createNewGrid(int rows, int columns) {
        configBoard.getChildren().clear();
        //board.setCreationStrategy(new EmptyBoardStrategy(board, rows, columns, image));
        configBoard.createBoard(rows, columns);
        configBoard.setOnMouseClicked(new DragAndResize(this));
    }

    @Override
    public void createBoardFromFile(String path) {
        Board newBoard = new FileBoard().read(path);
        parent.setCenter(newBoard);
    }

    @Override
    public void createSnake(ImageView img, Point2D firstAnchor, Point2D secondAnchor, MouseEvent event) {
        Connector connector = new Connector(firstAnchor, secondAnchor, img.getImage());
        connector.setWidth(img.getImage().getWidth()/5.6);

        double x = connector.getAnchor().getX();
        double y = connector.getAnchor().getY();
        connector.getTransforms().add(new Rotate(0, x, y));


        initializeImage(connector, event);
    }

    @Override
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

    @Override
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
        connector.setOnContextMenuRequested(e->{
            new ImageContextMenu(connector).show(connector, e.getScreenX(), e.getScreenY());
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

    public Simulation prepareSimulation(HashMap<String, CheckBox> list, TextArea textArea) {
        Board currentVisibleBoard = (Board) parent.getCenter();
        LogicalBoard logicalBoard = new LogicalBoard(new Validator(currentVisibleBoard), list, textArea);
        return logicalBoard.translate();
    }

    public void movePlayer(Circle circle, int square) {
        players.add(circle);
        Board currentVisibleBoard = (Board) parent.getCenter();
        GridPane gridPane = currentVisibleBoard.getGrid();
        gridPane.getChildren().remove(circle);

        int rowIndex = (square - 1)/gridPane.getRowCount();
        int reversedRowIndex = gridPane.getRowCount() - rowIndex - 1;
        int colIndex = (square-1) - rowIndex * gridPane.getRowCount();
        if (rowIndex % 2 != 0) colIndex = gridPane.getColumnCount() - (colIndex + 1);

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
