package board;

import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import board.BoardHandler;
import board.Connector;
import javafx.scene.transform.Rotate;

import java.util.HashMap;


public class DragAndResize implements EventHandler<MouseEvent> {
    private double startY;
    private static final double MAX_INCREASE_RATE = 1.6;
    private final HashMap<Connector, Double> initialHeight = new HashMap<>();
    private final Circle circle = new Circle(6);
    private final BoardHandler boardHandler;
    private Rectangle rect;
    public DragAndResize(BoardHandler boardHandler) {
        this.boardHandler = boardHandler;
        initializeBorder();
    }

    private void initializeBorder() {
        //Connector image = boardHandler.getCurrentSelected();

        rect = new Rectangle(0, 0,
                20, 20);

        rect.setFill(Color.TRANSPARENT);
        rect.setStroke(Color.WHITE);
        rect.setMouseTransparent(true);

        circle.setFill(Color.WHITE);

        activateBorder(false);

        boardHandler.addToBoard(circle);
        boardHandler.addToBoard(rect);

        circle.setOnMousePressed(e-> startY=e.getY());

        circle.setOnMouseDragged(e->{
            double difference=e.getY()-startY;

            Connector imgV = boardHandler.getCurrentSelected();

            boolean isResizable = (imgV.getCurrentImageHeight() + difference > initialHeight.get(imgV)) &&
                    (imgV.getCurrentImageHeight() + difference < initialHeight.get(imgV) * MAX_INCREASE_RATE);

            if (isResizable) {
                Point2D oldAnchor = imgV.getAnchor();
                imgV.setHeight(imgV.getCurrentImageHeight()+difference);
                Point2D newAnchor = imgV.getAnchor();

                double xOffset = newAnchor.getX()-oldAnchor.getX();
                double yOffset = newAnchor.getY()-oldAnchor.getY();

                imgV.setLayoutX(imgV.getLayoutX()-xOffset);
                imgV.setLayoutY(imgV.getLayoutY()-yOffset);

                adjustBorder(imgV);

                startY=e.getY();
            }
            e.consume();
        });
    }

    void activateBorder(boolean flag) {
        circle.setVisible(flag);
        circle.setDisable(!flag);

        rect.setDisable(!flag);
        rect.setVisible(flag);
    }

    void adjustBorder(Connector imgV) {
        circle.setCenterX(imgV.getBoundsInParent().getMaxX());
        circle.setCenterY(imgV.getBoundsInParent().getMaxY());

        rect.setX(imgV.getBoundsInParent().getMinX());
        rect.setY(imgV.getBoundsInParent().getMinY());
        rect.setWidth(imgV.getBoundsInParent().getWidth());
        rect.setHeight(imgV.getBoundsInParent().getHeight());
    }


    @Override
    public void handle(MouseEvent mouseEvent) {
        Node receiver = mouseEvent.getPickResult().getIntersectedNode();
        if (!mouseEvent.getButton().equals(MouseButton.PRIMARY)) return;

        if (!(receiver instanceof Connector connector) || !((Connector) receiver).isAnchored()) return;

        if (connector != boardHandler.getCurrentSelected()) {
            boardHandler.setCurrentSelected(connector);
            if (!initialHeight.containsKey(connector))
                initialHeight.put(connector, connector.getCurrentImageHeight());

            boardHandler.slider.setValue(((Rotate) connector.getTransforms().get(0)).getAngle());
            boardHandler.disableSlider(false);
            activateBorder(true);
        }

        else {
            boardHandler.setCurrentSelected(null);
            boardHandler.disableSlider(true);
            activateBorder(false);
        }

        adjustBorder(connector);
        mouseEvent.consume();
    }
}
