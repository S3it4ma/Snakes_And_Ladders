package board;


import javafx.geometry.Point2D;
import javafx.scene.image.Image;

public class Connector extends AnchorableImage {

    /**
    * <p><code>ImageView</code> con rapporto immagine fissato che possiede due punti di ancoraggio appartenenti ad essa creati appositamente per fissare
    * l'immagine in base a due punti differenti.</p>
    * <strong>Attenzione</strong> usare i metodi <code> final void setFitWidth(double v)</code> e <code> final void setFitHeight(double v)</code>
     * ereditati da <code>ImageView</code> comprometterebbe funzioni collegate alla classe <code>Connector</code>.
     * A tal proposito usare invece:
     * @see #setWidth(double)
     * @see #setHeight(double)
    * */

    private Point2D secondAnchor;
    //private boolean secondPointAnchored=false;

    public Connector(Point2D firstAnchor, Point2D secondAnchor, Image img) {
        super(firstAnchor, img);
        this.secondAnchor = secondAnchor;
    }



    public Point2D getSecondAnchor() {
        return secondAnchor;
    }

    //public boolean isSecondAnchored() {return secondPointAnchored;}

    @Override
    protected void updateAnchor(double scaleFactor) {
        super.updateAnchor(scaleFactor);
        secondAnchor=new Point2D(secondAnchor.getX() * scaleFactor,
                secondAnchor.getY() * scaleFactor);
    }

    @Override
    public String toString() {
        String path = this.getImage().getUrl();
        return path.substring(path.lastIndexOf("\\")+1, path.indexOf("."));
    }
}