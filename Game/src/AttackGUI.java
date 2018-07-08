import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.effect.Effect;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import utils.ConstantNumbers;


import static utils.Icon.*;
import static utils.ConstantNumbers.*;

import java.io.File;
import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;

//import static utils.Icon.GRASS0;

public class AttackGUI {

    ScrollPane scrollPane = new ScrollPane();
    ScrollPane scrollPaneS = new ScrollPane();
    VBox mapResult = new VBox();
    FlowPane flowPane = new FlowPane() ;
    AttackThread attackThread ;
    final double cellSize = ConstantNumbers.SIZE_OF_ENEMY_MAP_CELL;
    int currentSoldierType = -1 ;

    private static HashMap<Integer , String> SoldierPhoto = new HashMap<>();

    {
        SoldierPhoto.put(0 , GRASS0);
        SoldierPhoto.put(1 ,GAURDIAN1 );
        SoldierPhoto.put(2 , GIANT2);
        SoldierPhoto.put(3, DRAGON3);
        SoldierPhoto.put(4, ARCHER4);
        SoldierPhoto.put(5 , WALL_BREAKER5);
        SoldierPhoto.put(6 , HEALER6);
    }
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
    Group map = new Group();
    Group root = new Group();
    Scene scene = new Scene(root , 1100 , 600);


    //-------------------------------------
    ArrayList<SoldierGUI>soldierGUIS = new ArrayList<>();
    ArrayList<BuildingGUI>buildingGUIS = new ArrayList<>();
    ArrayList<DefensiveWeaponGUI>defensiveWeaponGUIS = new ArrayList<>();
    //-------------------------------------
    Label gold = new Label() ;
    Label elixir = new Label();
    //.....................................

    public boolean checkIfItFinished(){
        return attackThread.getEnd();
    }

    public  AttackGUI( AttackThread attackThread ) {
        scene.setFill(Paint.valueOf("#1D272A"));
        this.attackThread = attackThread ;
        attackThread.setAttackGUI(this);
        Thread thread = new Thread(this.attackThread);

        thread.start();
//        this.attackThread.setAttackGUI(this);


        map.relocate(ENEMY_MAP_X , ENEMY_MAP_Y);
        root.getChildren().add(map);
        addLine();
        setSoldiersOnDashBoard();
        setMap();
        setScore();
        root.getChildren().add(gold);
        gold.relocate(map.getScaleX() - gold.getWidth() , 0);
        root.getChildren().add(elixir);
        elixir.relocate(map.getScaleX() - elixir.getWidth() , 20);

        ImageView elixirImage = new ImageView(new Image("clashImages\\elixir_big_icon.png" ,40 , 40 , true, true));
        ImageView goldImage = new ImageView(new Image("clashImages\\Gold.png" ,40 , 40 , true, true));

        elixirImage.relocate(1100 - 40 , 50);
        goldImage.relocate(1100 - 40 , 4);

        root.getChildren().add(elixirImage);
        root.getChildren().add(goldImage);

        scrollPane.setContent(flowPane);
        scrollPane.setPrefViewportWidth(300);
        scrollPane.setPrefViewportHeight(250);
        flowPane.setOrientation(Orientation.VERTICAL);
        root.getChildren().add(scrollPane);
        scrollPane.relocate(2 , 80);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);

        scrollPaneS.setPrefViewportWidth(300);
        scrollPaneS.setPrefViewportHeight(250);
        root.getChildren().add(scrollPaneS);
        scrollPaneS.relocate(2 , 330);
        scrollPaneS.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        setSoldierStatus();
        scrollPaneS.setStyle("-fx-background: tranparent ");
        scrollPane.setStyle("-fx-background:tranparent  ");
        Media media = new Media(new File("src\\music\\combat_music.mp3").toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        mediaPlayer.play();

    }

    public void setSoldierStatus() {
        FlowPane flowPane1 = new FlowPane();
        flowPane1.setOrientation(Orientation.VERTICAL);
        ArrayList<Person> people = attackThread.getCurrentGame().getOwnMap().valuableSoldiers;

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                Label label1 = new Label("--------SOLDIERS IN ENEMY MAP--------");
                label1.setStyle(" -fx-pref-height: 35 ;-fx-pref-width: 350; -fx-arc-height: 0; -fx-arc-width: 0; -fx-alignment: center;-fx-background-color: \n" +
                        "        #0d0d0d,\n" +
                        "        linear-gradient(#172b3b 50%, #172B3B 100%),\n" +
                        "        radial-gradient(center 50% -40%, radius 200%, #172B3B 45%, #172B3B);\n" +
                        "    -fx-background-radius: 2;\n" +
                        "    -fx-background-insets: 0,1,1;\n" +
                        "    -fx-text-fill: #3e877b;\n" +
                        "    -fx-effect: dropShadow( three-pass-box , rgba(0,0,0,0.6) , 3, 0.0 , 0 , 1 );\n");
                flowPane1.getChildren().add(label1);
                for (int j = 0; j < people.size(); j++) {
                    if (people.get(j).getInEnemyMap()) {
                        int i = j;
                        Label label = new Label(people.get(i).getClass().getName() + "  in (" + people.get(i).getCurrentPosition()[0] + "," + people.get(i).getCurrentPosition()[1]
                                + ")   health : " + people.get(i).getHealth());
                        label.setStyle(" -fx-pref-height: 35 ;-fx-pref-width: 350; -fx-arc-height: 0; -fx-arc-width: 0; -fx-alignment: center;-fx-background-color: \n" +
                                "        #0D0D0D,\n" +
                                "        linear-gradient(#172b3b 50%, #172B3B 100%),\n" +
                                "        radial-gradient(center 50% -40%, radius 200%, #172B3B 45%, #172B3B);\n" +
                                "    -fx-background-radius: 2;\n" +
                                "    -fx-background-insets: 0,1,1;\n" +
                                "    -fx-text-fill: #d9d9d9;\n" +
                                "    -fx-effect: dropShadow( three-pass-box , rgba(0,0,0,0.6) , 3, 0.0 , 0 , 1 );\n");
                        flowPane1.getChildren().add(label);
                    }
                }
                Label label2 = new Label("--------SOLDIERS NOT IN ENEMY MAP--------");
                label2.setStyle(" -fx-pref-height: 35 ;-fx-pref-width: 350; -fx-arc-height: 0; -fx-arc-width: 0; -fx-alignment: center;-fx-background-color: \n" +
                        "        #0D0D0D,\n" +
                        "        linear-gradient(#172b3b 50%, #172B3B 100%),\n" +
                        "        radial-gradient(center 50% -40%, radius 200%, #172B3B 45%, #172B3B);\n" +
                        "    -fx-background-radius: 2;\n" +
                        "    -fx-background-insets: 0,1,1;\n" +
                        "    -fx-text-fill: #88112a;\n" +
                        "    -fx-effect: dropShadow( three-pass-box , rgba(0,0,0,0.6) , 3, 0.0 , 0 , 1 );\n");
                flowPane1.getChildren().add(label2);
                for (int j = 0; j < people.size(); j++) {
                    if (!people.get(j).getInEnemyMap()) {
                        int i = j;
                        Label label = new Label(people.get(i).getClass().getName() + " health : " + people.get(i).getHealth());

                        label.setStyle(" -fx-pref-height: 35 ;-fx-pref-width: 350; -fx-arc-height: 0; -fx-arc-width: 0; -fx-alignment: center;-fx-background-color: \n" +
                                "        #0D0D0D,\n" +
                                "        linear-gradient(#172b3b 50%, #172B3B 100%),\n" +
                                "        radial-gradient(center 50% -40%, radius 200%, #172B3B 45%, #172B3B);\n" +
                                "    -fx-background-radius: 2;\n" +
                                "    -fx-background-insets: 0,1,1;\n" +
                                "    -fx-text-fill: #d9d9d9;\n" +
                                "    -fx-effect: dropShadow( three-pass-box , rgba(0,0,0,0.6) , 3, 0.0 , 0 , 1 );\n");
                        flowPane1.getChildren().add(label);
                    }
                }

                scrollPaneS.setContent(flowPane1);
            }
        });
    }

    public void setScore (){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
            gold.setText(Integer.toString(attackThread.getCurrentGame().wonGold));
                elixir.setText(Integer.toString(attackThread.getCurrentGame().wonElixir));
                elixir.setStyle(" -fx-pref-height: 40 ;-fx-pref-width: 100; -fx-arc-height: 40; -fx-arc-width: 40; -fx-alignment: center;-fx-background-color: \n" +
                        "        #0D0D0D,\n" +
                        "        linear-gradient(#8B008B 50%, #cb73ff 100%),\n" +
                        "        radial-gradient(center 50% -40%, radius 200%, #8B008B 45%, rgba(230,230,230,0) 50%);\n" +
                        "    -fx-background-radius: 15;\n" +
                        "    -fx-background-insets: 0,1,1;\n" +
                        "    -fx-text-fill: #d9d9d9;\n" +
                        "    -fx-effect: dropShadow( three-pass-box , rgba(0,0,0,0.6) , 3, 0.0 , 0 , 1 );\n");
                gold.setStyle(" -fx-pref-height: 40 ;-fx-pref-width: 100; -fx-arc-height: 10; -fx-arc-width: 10; -fx-alignment: center;-fx-background-color: \n" +
                        "        #0D0D0D,\n" +
                        "        linear-gradient(#DAA520 50%, rgba(255,254,232,0.48) 100%),\n" +
                        "        radial-gradient(center 50% -40%, radius 200%, #DAA520 45%, rgba(230,230,230,0) 50%);\n" +
                        "    -fx-background-radius: 15;\n" +
                        "    -fx-background-insets: 0,1,1;\n" +
                        "    -fx-text-fill: #d9d9d9;\n" +
                        "    -fx-effect: dropShadow( three-pass-box , rgba(0,0,0,0.6) , 3, 0.0 , 0 , 1 );\n");
                gold.relocate(1100  - 30 - gold.getWidth() , 4);
                elixir.relocate(1100  - 30 - elixir.getWidth() , 50);


            }
        });


    }

    public void setGameStatus() {
        FlowPane flowPane1 = new FlowPane();
        flowPane1.setOrientation(Orientation.VERTICAL);
        ArrayList<Building> buildings = attackThread.getEnemyMap().getMapBuildings();

           Platform.runLater(new Runnable() {
               @Override
               public void run() {
                   for(int j = 0 ; j<buildings.size() ; j++){
                       int i = j ;
                   Label label = new Label(buildings.get(i).getClass().getName() + "  in (" +buildings.get(i).getPosition()[0]+","+buildings.get(i).getPosition()[1]
                           + ")   health : "+buildings.get(i).getResistance());
                   label.setStyle(" -fx-pref-height: 35 ;-fx-pref-width: 350; -fx-arc-height: 0; -fx-arc-width: 0; -fx-alignment: center;-fx-background-color: \n" +
                           "        #0D0D0D,\n" +
                           "        linear-gradient(#172b3b 50%, #172B3B 100%),\n" +
                           "        radial-gradient(center 50% -40%, radius 200%, #172B3B 45%, #172B3B);\n" +
                           "    -fx-background-radius: 2;\n" +
                           "    -fx-background-insets: 0,1,1;\n" +
                           "    -fx-text-fill: #d9d9d9;\n" +
                           "    -fx-effect: dropShadow( three-pass-box , rgba(0,0,0,0.6) , 3, 0.0 , 0 , 1 );\n");
                       flowPane1.getChildren().add(label);}
        ArrayList<DefensiveWeapon> defensiveWeapons = attackThread.getEnemyMap().getDefensiveWeapons();
        for(int j = 0 ; j<defensiveWeapons.size() ; j++){
            int i = j ;
            Label label = new Label(defensiveWeapons.get(i).getClass().getName() + "  in (" +defensiveWeapons.get(i).getPosition()[0]+","+defensiveWeapons.get(i).getPosition()[1]
                    + ")   health : "+defensiveWeapons.get(i).getResistence());

            label.setStyle(" -fx-pref-height: 35 ;-fx-pref-width: 350; -fx-arc-height: 0; -fx-arc-width: 0; -fx-alignment: center;-fx-background-color: \n" +
                    "        #0D0D0D,\n" +
                    "        linear-gradient(#172b3b 50%, #172B3B 100%),\n" +
                    "        radial-gradient(center 50% -40%, radius 200%, #172B3B 45%, #172B3B);\n" +
                    "    -fx-background-radius: 2;\n" +
                    "    -fx-background-insets: 0,1,1;\n" +
                    "    -fx-text-fill: #d9d9d9;\n" +
                    "    -fx-effect: dropShadow( three-pass-box , rgba(0,0,0,0.6) , 3, 0.0 , 0 , 1 );\n");
            flowPane1.getChildren().add(label);}
        scrollPane.setContent(flowPane1);
    }
});

    }


    public void removeBuilding(Building building){
        int x = building.getPosition()[0];
        int y = building.getPosition()[1];
        HBox hBox =(HBox) mapResult.getChildren().get(x);
        StackPane stackPane = (StackPane) hBox.getChildren().get(y);
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                stackPane.getChildren().remove(1);
            }
        });

    }
    public void removeBuilding(DefensiveWeapon defensiveWeapon){
        int x = defensiveWeapon.getPosition()[0];
        int y = defensiveWeapon.getPosition()[1];
        HBox hBox =(HBox) mapResult.getChildren().get(x);
        StackPane stackPane = (StackPane) hBox.getChildren().get(y);
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                stackPane.getChildren().remove(1);
//                mapResult.getChildren().remove(hBox);
            }
        });
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

        for (int i = 0; i < 30 ; i++) {
            HBox hBox = new HBox();
            for(int j = 0 ; j< 30 ; j++) {
                int x = i ;
                int y = j;
                StackPane stackPane = new StackPane();
                javafx.scene.image.ImageView imageView = new javafx.scene.image.ImageView
                        (new Image(GRASS0,SIZE_OF_ENEMY_MAP_CELL,SIZE_OF_ENEMY_MAP_CELL,true,true));
                stackPane.getChildren().add(imageView);
                stackPane.setOnMouseClicked(event -> {
                    if(currentSoldierType != -1){
                        {
                            putSoldiersOnMap(x , y , currentSoldierType);
                        }
                        // TODO put soldiers in position
                        currentSoldierType = -1;
                    }


                });
                hBox.getChildren().add(stackPane);
            }
            mapResult.getChildren().add(hBox);

            //  mapResult.setPadding(new Insets(0,0,0,0));
        }
        map.getChildren().add(mapResult);
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setPrefViewportHeight(600);
        scrollPane.setLayoutX(ENEMY_MAP_X);
        scrollPane.setPrefViewportWidth(1100 - ENEMY_MAP_X - 10);
        scrollPane.setContent(map);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        root.getChildren().addAll( scrollPane);
        addEnemyBuildings(attackThread.getEnemyMap());

    }

    private void addEnemyBuildings(EnemyMap enemyMap) {


        for(int i = 0 ; i< enemyMap.getMapBuildings().size() ; i++){
            BuildingGUI buildingGUI = new BuildingGUI(enemyMap.getMapBuildings().get(i));
            buildingGUIS.add(buildingGUI);
            HBox hBox = (HBox) mapResult.getChildren().get(enemyMap.getMapBuildings().get(i).getPosition()[0]);
            StackPane stackPane = (StackPane) hBox.getChildren().get(enemyMap.getMapBuildings().get(i).getPosition()[1]);
            //hBox.getChildren().add(buildingGUIS.get(buildingGUIS.size()-1).getImageView());
            stackPane.getChildren().add(buildingGUIS.get(buildingGUIS.size()-1).getImageView());

        }
        for(int i = 0 ; i< enemyMap.getDefensiveWeapons().size() ; i++){
            DefensiveWeaponGUI defensiveWeaponGUI = new DefensiveWeaponGUI(enemyMap.getDefensiveWeapons().get(i));
            defensiveWeaponGUIS.add(defensiveWeaponGUI);
//            map.getChildren().add(defensiveWeaponGUIS.get(defensiveWeaponGUIS.size()-1).getImageView());
            HBox hBox = (HBox) mapResult.getChildren().get(enemyMap.getDefensiveWeapons().get(i).getPosition()[0]);
            StackPane stackPane = (StackPane) hBox.getChildren().get(enemyMap.getDefensiveWeapons().get(i).getPosition()[1]);
            //hBox.getChildren().add(buildingGUIS.get(buildingGUIS.size()-1).getImageView());
            stackPane.getChildren().add(defensiveWeaponGUIS.get(defensiveWeaponGUIS.size()-1).getImageView());

        }

    }
    private void putSoldiersOnMap(int x, int y, int currentSoldierType) {
        ArrayList<Person> valuableSoldiers = new ArrayList<>();
        valuableSoldiers = attackThread.getCurrentGame().ownMap.valuableSoldiers;
        int numOfSoldier =  0 ;
        for(int i = 0 ; i < valuableSoldiers.size() ; i++)
            if(valuableSoldiers.get(i).getType() == currentSoldierType)
                if(!valuableSoldiers.get(i).getInEnemyMap()){
            System.out.println("putting soldier on map");
                    valuableSoldiers.get(i).setCurrentPosition(new int[] {x , y});
                    valuableSoldiers.get(i).setInEnemyMap(true);
                    soldierGUIS.add(new SoldierGUI(valuableSoldiers.get(i)));
                    map.getChildren().add(soldierGUIS.get(soldierGUIS.size()-1).progressBar);
                    map.getChildren().add(soldierGUIS.get(soldierGUIS.size()-1).getImageView());

                   // attackThread.updateAttackThread(currentSoldierType , x , y);
                    currentSoldierType = -1;
                    break;
                }
        //  if(numOfSoldier == 0 )
        //    this.currentSoldierType = -1 ;
    }




    private void setSoldiersOnDashBoard(){
        HBox soldiers = new HBox();
        for (int i = 1; i < 7; i++) {
            System.out.println(i +" ; soldier type");
            BouncingIcon bouncingIcon = new BouncingIcon(new Image(SoldierPhoto.get(i),50,50,true,true));
            soldiers.getChildren().add(bouncingIcon);
            int j = i;
            bouncingIcon.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    currentSoldierType = j ;
                }
            });
        }
        soldiers.setSpacing(8);
        soldiers.relocate(2 , 20);
        root.getChildren().add(soldiers);
    }



    public Scene getScene() {
        return scene;
    }

    public void updateGUI (){

    }
    public void updateSoldiers (){
        for(int i = 0 ; i < soldierGUIS.size() ; i++) {
            soldierGUIS.get(i).progressBar.relocate(SIZE_OF_ENEMY_MAP_CELL * soldierGUIS.get(i).getPerson().getCurrentPosition()[1],
                    SIZE_OF_ENEMY_MAP_CELL * soldierGUIS.get(i).getPerson().getCurrentPosition()[0] - 10);

            //System.out.println("hilo"+soldierGUIS.get(i).getPerson().getCurrentPosition()[1]);
            soldierGUIS.get(i).imageView.relocate
                    (SIZE_OF_ENEMY_MAP_CELL * soldierGUIS.get(i).getPerson().getCurrentPosition()[1],
                            SIZE_OF_ENEMY_MAP_CELL * soldierGUIS.get(i).getPerson().getCurrentPosition()[0]);
        }
    }

    public void updateBuildings (){
        try{
            if(buildingGUIS != null)
                for(int i = 0 ; i < buildingGUIS.size() ; i++) {
                    if(buildingGUIS.get(i).getBuilding() == null){
                        System.out.println("here in if in update building");
                        map.getChildren().remove(buildingGUIS.get(i).getImageView()) ;
                    }
//                    else{
//                        if(buildingGUIS.get(i).getBuilding().getHealth() <= 0 || buildingGUIS.get(i).getBuilding().getResistance() <= 0 ){
//                            map.getChildren().remove(buildingGUIS.get(i).getImageView()) ;
//                            System.out.println("building  is removed in updateBuildings\n\n");
//                        }
//                    }

                }
            if(defensiveWeaponGUIS != null)
                for(int i = 0 ; i < defensiveWeaponGUIS.size() ; i++) {
                    if(defensiveWeaponGUIS.get(i).getDefensiveWeapon() == null){
                        System.out.println("here in if in for defensive...");
                        map.getChildren().remove(defensiveWeaponGUIS.get(i).getImageView()) ;
                    }
//                    else{
//                        if(defensiveWeaponGUIS.get(i).getDefensiveWeapon().getResistence() <= 0){
//                            System.out.println("defensive weapon is removed in updateBuildings\n\n");
//                            map.getChildren().remove(defensiveWeaponGUIS.get(i).getImageView()) ;
//                        }
//                    }

                }}
        catch (Exception e){}
    }
}
//----------------------------------------------------------------------------------------------------------------------------------------------------------
class SoldierGUI {
    private static HashMap<Integer , String> SoldierPhoto = new HashMap<>();

    {
        SoldierPhoto.put(0 , GRASS0);
        SoldierPhoto.put(1 ,GAURDIAN1 );
        SoldierPhoto.put(2 , GIANT2);
        SoldierPhoto.put(3, DRAGON3);
        SoldierPhoto.put(4, ARCHER4);
        SoldierPhoto.put(5 , WALL_BREAKER5);
        SoldierPhoto.put(6 , HEALER6);
    }
    ProgressBar progressBar = new ProgressBar();
    ImageView imageView;
    Person person;
    SoldierGUI(Person person){
        double ratio = new Image(SoldierPhoto.get(person.type)).getHeight() / new Image(SoldierPhoto.get(person.type)).getWidth();
        ImageView imageView = new ImageView(new Image(SoldierPhoto.get(person.type) , 30 , 30 , true , true ) );
        imageView.relocate(  SIZE_OF_ENEMY_MAP_CELL*person.getCurrentPosition()[1] , SIZE_OF_ENEMY_MAP_CELL*person.getCurrentPosition()[0]);
        this.imageView = imageView;
        progressBar.setStyle("-fx-background-color: orange ; -fx-pref-width: 50 ;-fx-fill: orange");
        progressBar.relocate(SIZE_OF_ENEMY_MAP_CELL*person.getCurrentPosition()[1] , SIZE_OF_ENEMY_MAP_CELL*person.getCurrentPosition()[0] - 10);
        progressBar.setProgress(person.health / person.getFullHealth() );

        this.person = person;
    }

    public ImageView getImageView() {
        return imageView;
    }

    public ProgressBar getProgressBar() {
        return progressBar;
    }

    public Person getPerson() {
        return person;
    }


}

class BuildingGUI {
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

    ImageView imageView;
    Building building;

    BuildingGUI(Building building) {
        double ratio = new Image(photo.get(building.getJasonType())).getHeight() /  new Image(photo.get(building.getJasonType())).getWidth();
        ImageView imageView = new ImageView(new Image(photo.get(building.getJasonType()),45 , 45,true,true));

        this.imageView = imageView;
        this.building = building;
        System.out.println("building gui");
        System.out.println(building.getPosition()[0]+" "+building.getPosition()[1]);
    }

    public ImageView getImageView() {
        return imageView;
    }

    public Building getBuilding() {
        return building;
    }
}

class DefensiveWeaponGUI {
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

    ImageView imageView;
    DefensiveWeapon defensiveWeapon;

    DefensiveWeaponGUI(DefensiveWeapon defensiveWeapon) {
        double ratio = new Image(photo.get(defensiveWeapon.getARM_TYPE())).getHeight() /  new Image(photo.get(defensiveWeapon.getARM_TYPE())).getWidth();
        ImageView imageView = new ImageView(new Image(photo.get(defensiveWeapon.getARM_TYPE()),40 , 40*ratio,true,true));
        imageView.relocate(SIZE_OF_ENEMY_MAP_CELL *defensiveWeapon.getPosition()[1],
                SIZE_OF_ENEMY_MAP_CELL * defensiveWeapon.getPosition()[0]);
        this.imageView = imageView;
        this.defensiveWeapon = defensiveWeapon;

    }

    public ImageView getImageView() {
        return imageView;
    }

    public DefensiveWeapon getDefensiveWeapon() {
        return defensiveWeapon;
    }
}