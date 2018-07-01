import javafx.scene.effect.Reflection;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;


import javafx.scene.image.ImageView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;

import static utils.ConstantNumbers.*;
import static utils.Icon.*;

public class GraphicOwnMapCell extends StackPane{
    private static HashMap<Integer , String> photo= new HashMap<>();

    {
        photo.put(0 , GRASS0);
        photo.put(1 , GOLD_MINE1);
        photo.put(2 , ELIXIR_MINE2);
        photo.put(4, GOLD_STORAGE3);
        photo.put(3, ELIXIR_STORAGE4);
        photo.put(5 , TOWN_HALL5);
        photo.put(6 , BARRACKS6);
        photo.put(7 , CAMP7);
        photo.put(8 , ARCHER_TOWER8);
        photo.put(9 , CANNON9);
        photo.put(10 , AIR_DEFENSE10);
        photo.put(11 , WIZARD_TOWER11);
        photo.put(12 , WALL12);
        photo.put(13 , TRAP13);
        photo.put(14 , GUARDIAN_GIANT14);
    }



    private BouncingIcon img ;
    private int typeOfBuilding;
//    Rectangle rect = new Rectangle(CELL_LENGTH,CELL_LENGTH);

    public GraphicOwnMapCell(int typeOfBuilding)  {
        super();
        super.getChildren().add(new ImageView(new Image(photo.get(0))));
        this.typeOfBuilding = typeOfBuilding;
        concertTypeToPhoto(typeOfBuilding);
        super.getChildren().addAll(img);
    }

    private void concertTypeToPhoto(int i) {
        img = new BouncingIcon(new Image(photo.get(i) , SIZE , SIZE , true , true));
        img.setEffect(new Reflection());
    }

    public GraphicOwnMapCell() {
        super();
//        rect.setFill(Color.GREEN);
//        super.getChildren().add(rect);
        super.getChildren().add(new ImageView(new Image(photo.get(0))));
        concertTypeToPhoto(0);
    }
}
