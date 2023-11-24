package board;


import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.Color;

import java.util.Random;

public class Board extends Group {
    public static final double MAX_SIZE = 730;
    private final Image[] image = new Image[4];
    private GridPane gridPane;
    private AnchorableImage[][] squares;

    public Board() {
        for (int i=0; i<4; i++) {
            image[i] = new Image(System.getProperty("user.dir")+"\\src\\main\\resources\\colours\\"+(i+1)+".png");
        }
    }
    AnchorableImage[][] getSquares() {
        return squares;
    }
    GridPane getGrid() {
        return gridPane;
    }

    private void createColorGrid(int[][] colours) {
        Random random=new Random();
        int choices = image.length;
        for (int i=0; i < colours.length; i++) {
            for (int j=0; j < colours[0].length; j++) {
                int x;
                do {
                    x=random.nextInt(0, choices);
                } while ((i>0 && x==colours[i-1][j]) ||
                        (j>0 && x==colours[i][j-1]) ||
                        (i>0 && j>0 && x==colours[i-1][j-1]));
                colours[i][j]=x;
            }
        }
    }

    public void createBoard(int rows, int columns) {
        squares = new AnchorableImage[rows][columns];
        int[][] colours = new int[rows][columns];

        createColorGrid(colours);

        double height = MAX_SIZE;
        double gridCellSize = (rows > columns) ? height/rows : height/columns;

        GridPane gridPane = new GridPane();
        RowConstraints rc = new RowConstraints();
        rc.setValignment(VPos.TOP);

        for (int i=0; i<rows; i++) {
            gridPane.getRowConstraints().add(rc);
            for (int j=0; j<columns; j++) {
                ImageView cell = new ImageView(image[colours[i][j]]);
                cell.setPreserveRatio(true);
                cell.setFitHeight(gridCellSize);
                gridPane.add(cell, j, i);
            }
        }

        int n=rows*columns;
        for (int i=0; i<rows; i++) {

            int k = (i%2==0)? 0 : columns-1;

            for (int j=0; j<columns; j++) {
                Label l=new Label(""+n);
                l.setStyle("-fx-font-weight: bold");
                l.setTextFill(Color.WHITE);
                l.setPadding(new Insets(2, 0, 0, 4));

                gridPane.add(l, k, i);

                k = (i%2==0)? k+1 : k-1;
                n--;
            }
        }
        this.getChildren().add(gridPane);
        this.gridPane = gridPane;
        gridPane.setOnDragDropped(new DroppedOnBoard(this));
        gridPane.setOnDragOver(e->{
            if(e.getGestureSource() != gridPane && e.getDragboard().hasImage()){
                e.acceptTransferModes(TransferMode.COPY_OR_MOVE);
            }
            e.consume();
        });
    }
}
