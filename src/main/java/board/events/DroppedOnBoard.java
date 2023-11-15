package board.events;

import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.input.DragEvent;
import javafx.scene.layout.GridPane;
import board.AnchorableImage;
import board.Board;
public class DroppedOnBoard implements EventHandler<DragEvent> {
    private final Board board;

    public DroppedOnBoard(Board board) {
        this.board=board;
    }

    @Override
    public void handle(DragEvent e) {
        GridPane gridPane = board.getGrid();
        boolean success=false;
        Node receiver=e.getPickResult().getIntersectedNode();

        if (receiver != gridPane && e.getDragboard().hasImage()) {
            int colIndex = GridPane.getColumnIndex(receiver);
            int rowIndex = GridPane.getRowIndex(receiver);

            Bounds cellBounds = gridPane.getCellBounds(colIndex, rowIndex);

            AnchorableImage imageV = (AnchorableImage) e.getGestureSource();

            boolean canBeDropped = board.getSquares()[rowIndex][colIndex] == null;

            if (canBeDropped) {
                board.getSquares()[rowIndex][colIndex] = imageV;

                imageV.relocate(cellBounds.getCenterX()-imageV.getAnchor().getX()+gridPane.getLayoutX(),
                        cellBounds.getCenterY()-imageV.getAnchor().getY()+gridPane.getLayoutY());

                imageV.setAnchored(true);
                imageV.setVisible(true);
                success=true;
            }
        }
        e.setDropCompleted(success);
        e.consume();
    }
}
