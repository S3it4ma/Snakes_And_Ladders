package board;

import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.net.URL;

public class AnchorableImage extends ImageView {
    protected Point2D anchor;
    private boolean isAnchored;
    private double currentImageWidth;
    private double currentImageHeight;
    private final double imgRatio;
    public AnchorableImage(Point2D anchor, Image img) {
        super(img);
        super.setPreserveRatio(true);
        this.anchor = anchor;
        this.currentImageWidth = img.getWidth();
        this.currentImageHeight = img.getHeight();
        this.imgRatio= currentImageWidth / currentImageHeight;
    }

    public Point2D getAnchor() {
        return anchor;
    }

    public boolean isAnchored() {
        return isAnchored;
    }

    public double getCurrentImageHeight() {
        return currentImageHeight;
    }

    public double getCurrentImageWidth() {
        return currentImageWidth;
    }

    public void setAnchored(boolean anchored) {
        this.isAnchored = anchored;
    }


    protected void updateAnchor(double scaleFactor) {
        anchor=new Point2D(anchor.getX() * scaleFactor,
                anchor.getY() * scaleFactor);
    }
    public void setWidth(double newWidth) {
        double scaleFactor = newWidth / currentImageWidth;
        currentImageWidth = newWidth;
        currentImageHeight = newWidth / imgRatio;

        if (getFitWidth() == 0) super.setFitHeight(currentImageHeight);
        else super.setFitWidth(currentImageWidth);
        updateAnchor(scaleFactor);
    }

    public void setHeight(double newHeight) {
        double scaleFactor = newHeight / currentImageHeight;
        currentImageHeight = newHeight;
        currentImageWidth = imgRatio * newHeight;

        if (getFitWidth() == 0) super.setFitHeight(currentImageHeight);
        else super.setFitWidth(currentImageWidth);
        updateAnchor(scaleFactor);
    }

    @Override
    public String toString() {
        String string = this.getImage().getUrl();

        int index = string.lastIndexOf("\\");
        int beginIndex = (index == -1)? string.lastIndexOf("/") : index;

        return string.substring(beginIndex+1, string.indexOf("."));
    }
}
