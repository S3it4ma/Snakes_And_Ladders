package board;

import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;

public class ImageContextMenu extends ContextMenu {
    private final AnchorableImage img;
    public ImageContextMenu(AnchorableImage img) {
        this.img=img;
        createContextMenu();
    }

    private void deleteImage() {
        Board board = (Board) img.getParent();
        AnchorableImage[][] squares = board.getSquares();
        for (int i = 0; i < squares.length; i++) {
            for (int j = 0; j < squares[0].length; j++) {
                if (squares[i][j] == img)
                    squares[i][j] = null;
            }
        }
    }

    private void createContextMenu() {

        MenuItem move = new MenuItem("Sposta");
        move.setOnAction(e -> {
            img.setAnchored(false);
            deleteImage();
        });
        MenuItem delete = new MenuItem("Rimuovi");

        delete.setOnAction(e -> {
            Board board = (Board) img.getParent();
            deleteImage();
            board.getChildren().remove(img);
        });
        this.getItems().addAll(move, delete);
    }
}
