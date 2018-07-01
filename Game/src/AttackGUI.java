import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Line;
import javafx.scene.shape.VLineTo;
import javafx.stage.Stage;
import static utils.Icon.*;
import static utils.ConstantNumbers.*;

import javax.swing.*;
import javax.swing.text.html.ImageView;
import java.util.ArrayList;
import java.util.HashMap;

//import static utils.Icon.GRASS0;

public class AttackGUI extends Application{
   int currentSoldierType = -1 ;

    private static HashMap<Integer , String> SoldierPhoto = new HashMap<>();

    {
        SoldierPhoto.put(0 , GRASS0);
        SoldierPhoto.put(1 ,GAURDIAN1 );
        SoldierPhoto.put(2 , GIANT2);
        SoldierPhoto.put(3, GOLD_STORAGE3);
        SoldierPhoto.put(4, ARCHER4);
        SoldierPhoto.put(5 , WALL_BREAKER5);
        SoldierPhoto.put(6 , HEALER6);
    }
    Group root = new Group();
    Scene scene = new Scene(root , 1100 , 720);

    //-------------------------------------
    ArrayList<Person>valuableSoldiers = new ArrayList<>();
    Guardian guardian = new Guardian();
    Archer archer = new Archer();
    Giant giant= new Giant();
    Dragon dragon = new Dragon();


    public static void main(String[] args) {
     launch(args);
    }


    @Override
    public void start(Stage primaryStage) throws Exception {
        valuableSoldiers.add(giant);
        valuableSoldiers.add(guardian);
        valuableSoldiers.add(archer);
        valuableSoldiers.add(dragon);
        addLine();
        setSoldiersOnDashBoard();

        setMap();
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void addLine (){
        Line line = new Line();
        line.setStartX(1100 - 725);
        line.setEndX(1100 - 725);
        line.setStartY(0);
        line.setEndY(719);
        root.getChildren().add(line);
    }

    private void setMap() {
        VBox mapResult = new VBox();
        for (int i = 0; i < 30 ; i++) {
            HBox hBox = new HBox();
            for(int j = 0 ; j< 30 ; j++) {
                javafx.scene.image.ImageView imageView = new javafx.scene.image.ImageView(new Image(GRASS0,SIZE_OF_ENEMY_MAP_CELL,SIZE_OF_ENEMY_MAP_CELL,true,true));
               imageView.setOnMouseClicked(event -> {
                   if(currentSoldierType != -1){
                       // TODO put soldiers in position
                       currentSoldierType = -1;
                   }

               });
                hBox.getChildren().add(imageView);
            }
            mapResult.getChildren().add(hBox);
            mapResult.relocate(1100 - 720 , 0);
            mapResult.setPadding(new Insets(0,0,0,0));
        }

        root.getChildren().add(mapResult);
    }
    HBox soldiers = new HBox();
    private void setSoldiersOnDashBoard(){
        for (int i = 1; i < 7; i++) {
            BouncingIcon bouncingIcon = new BouncingIcon(new Image(SoldierPhoto.get(i),50,50,true,true));
            soldiers.getChildren().add(bouncingIcon);
            int j = i;
            bouncingIcon.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {

                }
            });
        }
        soldiers.setSpacing(8);
        soldiers.relocate(2 , 20);
        root.getChildren().add(soldiers);
    }


}
