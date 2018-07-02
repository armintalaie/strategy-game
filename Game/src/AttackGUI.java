import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Line;
import utils.ConstantNumbers;

import static utils.Icon.*;
import static utils.ConstantNumbers.*;

import java.util.ArrayList;
import java.util.HashMap;

//import static utils.Icon.GRASS0;

public class AttackGUI {
    AttackThread attackThread ;
   final double cellSize = ConstantNumbers.SIZE_OF_ENEMY_MAP_CELL;
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


    public  AttackGUI( AttackThread attackThread ) {
        this.attackThread = attackThread ;
        this.attackThread.setAttackGUI(this);


        map.relocate(ENEMY_MAP_X , ENEMY_MAP_Y);
        root.getChildren().add(map);
        addLine();
        setSoldiersOnDashBoard();
        setMap();
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
                int x = i ;
                int y = j;
                javafx.scene.image.ImageView imageView = new javafx.scene.image.ImageView(new Image(GRASS0,SIZE_OF_ENEMY_MAP_CELL,SIZE_OF_ENEMY_MAP_CELL,true,true));
               imageView.setOnMouseClicked(event -> {
                   if(currentSoldierType != -1){
                       {
                           putSoldiersOnMap(x , y , currentSoldierType);
                       }
                       // TODO put soldiers in position
                       currentSoldierType = -1;
                   }

               });
                hBox.getChildren().add(imageView);
            }
            mapResult.getChildren().add(hBox);

          //  mapResult.setPadding(new Insets(0,0,0,0));
        }
        map.getChildren().add(mapResult);
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setPrefViewportHeight(550);
        scrollPane.setLayoutX(ENEMY_MAP_X);
        scrollPane.setPrefViewportWidth(800);
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
           map.getChildren().add(buildingGUIS.get(buildingGUIS.size()-1).getImageView());

       }
        for(int i = 0 ; i< enemyMap.getDefensiveWeapons().size() ; i++){
            DefensiveWeaponGUI defensiveWeaponGUI = new DefensiveWeaponGUI(enemyMap.getDefensiveWeapons().get(i));
            defensiveWeaponGUIS.add(defensiveWeaponGUI);
            map.getChildren().add(defensiveWeaponGUIS.get(defensiveWeaponGUIS.size()-1).getImageView());
        }
    }
    private void putSoldiersOnMap(int x, int y, int currentSoldierType) {
        ArrayList<Person> valuableSoldiers = new ArrayList<>();
        valuableSoldiers = attackThread.getCurrentGame().ownMap.valuableSoldiers;
        int numOfSoldier =  0 ;
        for(int i = 0 ; i < valuableSoldiers.size() ; i++)
            if(valuableSoldiers.get(i).getType() == currentSoldierType)
                if(!valuableSoldiers.get(i).getInEnemyMap()){
                   // System.out.println(x+" "+y);
                    valuableSoldiers.get(i).setCurrentPosition(new int[] {x , y});
                   // System.out.println(valuableSoldiers.get(i).getCurrentPosition()[0]);
                    valuableSoldiers.get(i).setInEnemyMap(true);
                    soldierGUIS.add(new SoldierGUI(valuableSoldiers.get(i)));
                    map.getChildren().add(soldierGUIS.get(soldierGUIS.size()-1).getImageView());
                    attackThread.updateAttackThread(currentSoldierType , x , y);
                    break;
                }
        for(int i = 0 ; i < valuableSoldiers.size() ; i++)
            if(valuableSoldiers.get(i).getType() == currentSoldierType)
                if(!valuableSoldiers.get(i).getInEnemyMap()){
                   numOfSoldier ++;
                }
                System.out.println(numOfSoldier);
      //  if(numOfSoldier == 0 )
        //    this.currentSoldierType = -1 ;

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
            //System.out.println("hilo"+soldierGUIS.get(i).getPerson().getCurrentPosition()[1]);
            soldierGUIS.get(i).imageView.relocate(SIZE_OF_ENEMY_MAP_CELL * soldierGUIS.get(i).getPerson().getCurrentPosition()[1], SIZE_OF_ENEMY_MAP_CELL * soldierGUIS.get(i).getPerson().getCurrentPosition()[0]);

        }
    }

    public void updateBuildings (){
        try{
        if(buildingGUIS != null)
        for(int i = 0 ; i < buildingGUIS.size() ; i++) {
            if(buildingGUIS.get(i).getBuilding() == null){
                map.getChildren().remove(buildingGUIS.get(i).getImageView()) ;
            }
            else{
                if(buildingGUIS.get(i).getBuilding().getResistance() <= 0){
                    map.getChildren().remove(buildingGUIS.get(i).getImageView()) ;
                }
            }

        }
        if(defensiveWeaponGUIS != null)
        for(int i = 0 ; i < defensiveWeaponGUIS.size() ; i++) {
            if(defensiveWeaponGUIS.get(i).getDefensiveWeapon() == null){
                map.getChildren().remove(defensiveWeaponGUIS.get(i).getImageView()) ;
            }
            else{
                if(defensiveWeaponGUIS.get(i).getDefensiveWeapon().getResistence() <= 0){
                    map.getChildren().remove(defensiveWeaponGUIS.get(i).getImageView()) ;
                }
            }

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
        SoldierPhoto.put(3, GOLD_STORAGE3);
        SoldierPhoto.put(4, ARCHER4);
        SoldierPhoto.put(5 , WALL_BREAKER5);
        SoldierPhoto.put(6 , HEALER6);
    }
    ImageView imageView;
    Person person;
    SoldierGUI(Person person){
        double ratio = new Image(SoldierPhoto.get(person.type)).getHeight() / new Image(SoldierPhoto.get(person.type)).getWidth();
        ImageView imageView = new ImageView(new Image(SoldierPhoto.get(person.type) , 20 , 20*ratio , true , true ) );
       // System.out.println(person.getCurrentPosition()[0]+" "+person.getCurrentPosition()[1]);
        imageView.relocate(  SIZE_OF_ENEMY_MAP_CELL*person.getCurrentPosition()[1] , SIZE_OF_ENEMY_MAP_CELL*person.getCurrentPosition()[0]);
        this.imageView = imageView;
        this.person = person;
    }

    public ImageView getImageView() {
        return imageView;
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
        ImageView imageView = new ImageView(new Image(photo.get(building.getJasonType()),20 , 20*ratio,true,true));
        imageView.relocate(SIZE_OF_ENEMY_MAP_CELL *building.getPosition()[1],
                SIZE_OF_ENEMY_MAP_CELL * building.getPosition()[0]);
        this.imageView = imageView;
        this.building = building;
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
        ImageView imageView = new ImageView(new Image(photo.get(defensiveWeapon.getARM_TYPE()),20 , 20*ratio,true,true));
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