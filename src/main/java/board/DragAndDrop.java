package board;

import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.input.*;

public class DragAndDrop implements EventHandler<MouseEvent>{
    private final AnchorableImage img;
    public DragAndDrop(AnchorableImage img) {
        this.img=img;
    }

    @Override
    public void handle(MouseEvent mouseEvent) {
        if (!img.isAnchored()) {
            Dragboard db = img.startDragAndDrop(TransferMode.COPY);
            ClipboardContent content=new ClipboardContent();

            Image image=new Image(img.getImage().getUrl(), img.getFitWidth(), img.getFitHeight(), true, true);
            content.putImage(image);
            db.setContent(content);
            mouseEvent.consume();
        }
    }
}
