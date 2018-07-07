import javafx.geometry.Pos;
import javafx.scene.effect.Reflection;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

import java.util.ArrayList;
import java.util.Collections;

import static utils.ConstantNumbers.*;
import static utils.HashImage.*;
public class EnemyCell extends StackPane {
   private boolean isBuilding;
   ImageView img;
   static ArrayList<Pos> positions = new ArrayList<>();
    static {
        positions.add(Pos.CENTER);
        positions.add(Pos.BOTTOM_LEFT);
        positions.add(Pos.BOTTOM_RIGHT);
        positions.add(Pos.TOP_LEFT);
        positions.add(Pos.TOP_RIGHT);
    }

    public EnemyCell(int typeOfBuildingOrSoldier , boolean isBuilding) {
        super();
        super.getChildren().add(new ImageView(new Image
                (SoldierPhoto.get(0),50,50,true,true)));
        this.isBuilding = isBuilding;
        if (isBuilding)convertBuildingToPhoto(typeOfBuildingOrSoldier);
        else convertSoldierToPhoto(typeOfBuildingOrSoldier);

    }

    public EnemyCell() {
        super();
        super.getChildren().add(new ImageView(new Image
                (SoldierPhoto.get(0),50,50,true,true)));
    }

    private void convertBuildingToPhoto(int i){
        img = new ImageView(new Image(buildingPhoto.get(i) ,40 ,40  , true , true));
        img.setEffect(new Reflection());
        super.getChildren().addAll(img);
    }
    private void convertSoldierToPhoto(int i ){
        Collections.shuffle(positions);
            ImageView img1 = new ImageView(new Image(SoldierPhoto.get(i) , 25 ,25  , true , true));
        img1.setEffect(new Reflection());
        super.getChildren().addAll(img1);
        super.setAlignment(img1,positions.get(0));
    }
    public void addSoldirer(int i){
        convertSoldierToPhoto(i);
    }

}
