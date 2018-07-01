import javafx.scene.effect.Reflection;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.HashMap;

import static utils.ConstantNumbers.CELL_LENGTH;
import static utils.ConstantNumbers.SIZE;
import static utils.Icon.*;

public class GraphicEnemyMapDetailedCell extends StackPane {
    private static HashMap<Integer , String> photo= new HashMap<>();

    {
        photo.put(0 , GRASS0);
        photo.put(1 ,GAURDIAN1 );
        photo.put(2 , GIANT2);
        photo.put(3, GOLD_STORAGE3);
        photo.put(4, ARCHER4);
        photo.put(5 , WALL_BREAKER5);
        photo.put(6 , HEALER6);
    }



    private BouncingIcon img ;
    private int typeOfSoldier;
    Rectangle rect = new Rectangle(CELL_LENGTH,CELL_LENGTH);
    public GraphicEnemyMapDetailedCell(int typeOfSoldier) {
        super();
        this.typeOfSoldier = typeOfSoldier;

        super.getChildren().add(new ImageView(new Image(photo.get(0))));
        this.typeOfSoldier = typeOfSoldier;
        concertTypeToPhoto(typeOfSoldier);
        super.getChildren().addAll(rect, img);
    }


    private void concertTypeToPhoto(int i) {
        img = new BouncingIcon(new Image(photo.get(i) , SIZE , SIZE , true , true));
        img.setEffect(new Reflection());
    }

    public GraphicEnemyMapDetailedCell() {
        super();
        rect.setFill(Color.GREEN);
        super.getChildren().add(rect);
        super.getChildren().add(new ImageView(new Image(photo.get(0))));
        concertTypeToPhoto(0);
    }

}
