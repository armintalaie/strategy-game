import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

import static utils.HashImage.*;
import java.util.ArrayList;

public class AtiyeAttackGUI  {
    AttackThread attackThread ;
    int currentSoldierType = -1 ;
    ArrayList<Person> soldiersOnMap = new ArrayList<>();
//    graphic
    EnemyCell[][] enemyCells = new EnemyCell[30][30];
    VBox dashBoard = new VBox();
    VBox dashboaredContainer = new VBox();
    VBox map = new VBox();
    ScrollPane mapScroll = new ScrollPane(map);
    BorderPane borderPane = new BorderPane(mapScroll);
    HBox root1 = new HBox(dashboaredContainer,borderPane);
    Scene scene1 = new Scene(root1,1100,720);
    KeyFrame keyFrame = new KeyFrame(new Duration(1000), new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            System.out.println("here in key frame");
            updateDashBoared();
            printMap();

        }
    });
    Timeline attackAnimatuin = new Timeline(Timeline.INDEFINITE,keyFrame);
    //.................
    Game currentGame;
    EnemyMap enemyMap;

    public Scene getScene() {
        return scene1;
    }

    public  AtiyeAttackGUI(Game currentGame,EnemyMap currentEnemy) {
        attackThread =new AttackThread(currentGame,currentEnemy , new Stage(), new Scene(new Group()));
        Thread thread = new Thread(attackThread);
//        Platform.runLater(attackThread);
//        attackThread.setAttackGUI(this);

//        this.attackThread = attackThread ;
//       this.attackThread.setAttackGUI(this);
        initializeMap();
        printMap();
        setSoldiersOnDashBoard();
        thread.start();
        System.out.println("new thread started");
        attackAnimatuin.setCycleCount(Animation.INDEFINITE);
        attackAnimatuin.playFromStart();
        this.currentGame = currentGame;
        this.enemyMap = currentEnemy;
    }
    private void updateDashBoared(){
        dashBoard.getChildren().clear();
        StringBuilder context1 = new StringBuilder();
        for (Building building : currentGame.statusBuildingsAttackMode(enemyMap)) {
            context1.append(singleBuildingShowerByType(building.getJasonType())).append(" level = ").append(building.getLevel()).append(" in ");
            context1.append("(").append(building.getPosition()[0]).append(",").append(building.getPosition()[1]).append(") ");
            context1.append("with health = ").append(building.getResistance()).append("\n");
        }
        ////////////

        for (DefensiveWeapon defensiveWeapon : currentGame.statusDefensiveWeaponsAttackMode(enemyMap)) {
            context1.append(singleBuildingShowerByType(defensiveWeapon.getARM_TYPE())).append(" level = ").append(defensiveWeapon.getLevel()).append(" in ");
            context1.append("(").append(defensiveWeapon.getPosition()[0]).append(",").append(defensiveWeapon.getPosition()[1]).append(") ");
            context1.append("with health = ").append(defensiveWeapon.getResistence()).append("\n");
        }
        Label label1 = new Label(context1.toString());
        ///////////////
        StringBuilder context2 = new StringBuilder();
        context2.append("soldiers currently in enemy map\n");
        for (Person p : currentGame.statusUnitsAttackMode()) {
            if(p.inEnemyMap)
                context2.append(convertSoldierTypeToString(p.getType())).append(" level = ").append(p.getLevel()).append(" in(").append(p.getCurrentPosition()[0]).append(",").append(p.getCurrentPosition()[1]).append(") with health = ").append(p.getHealth()).append("\n");
        }
        context2.append("soldiers currently NOT in enemy map\n");
        for (Person p : currentGame.statusUnitsAttackMode()) {
            if(!p.inEnemyMap)
                context2.append(convertSoldierTypeToString(p.getType())).append(" level = ").append(p.getLevel()).append(" in(").append(p.getCurrentPosition()[0]).append(",").append(p.getCurrentPosition()[1]).append(") with health = ").append(p.getHealth()).append("\n");
        }
        Label label2 = new Label(context2.toString());
        dashBoard.getChildren().addAll(label1,label2);
    }
    private void initializeMap(){
        for (int i = 0; i <30 ; i++) {
            for (int j = 0; j <30 ; j++) {
                enemyCells[i][j] = new EnemyCell();
            }
        }
    }
    public void printMap(){
        updateEnemyMapCells();
       map.getChildren().clear();
        for (int i = 0; i <30; i++) {
            HBox hBox = new HBox();
            for (int j = 0; j <30 ; j++) {
                int x = i ;
                int y = j;
                if(currentSoldierType != -1){
                    enemyCells[i][j].setOnMouseClicked(new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent event) {
                            putSoldiersOnMap(x , y , currentSoldierType);
                        }
                    });
                }
                hBox.getChildren().add(enemyCells[i][j]);
            }
            map.getChildren().add(hBox);
        }
    }
    public void updateEnemyMapCells(){
        initializeMap();
        ArrayList<Integer> toBeRemoved = new ArrayList<>();
        for(int i = 0 ; i< attackThread.getEnemyMap().getMapBuildings().size() ; i++){
            if (attackThread.getEnemyMap().getMapBuildings().get(i).getResistance()>0) {
                enemyCells[attackThread.getEnemyMap().getMapBuildings().get(i).getPosition()[0]]
                        [attackThread.getEnemyMap().getMapBuildings().get(i).getPosition()[1]] =
                        new EnemyCell(attackThread.getEnemyMap().getMapBuildings().get(i).getJasonType(), true);
            }
        }
        for (int i = 0; i <attackThread.getEnemyMap().getDefensiveWeapons().size() ; i++) {
            System.out.println("defensive" +i);
            if (attackThread.getEnemyMap().getDefensiveWeapons().get(i).getResistence()>0)
            enemyCells[attackThread.getEnemyMap().getDefensiveWeapons().get(i).getPosition()[0]]
                    [attackThread.getEnemyMap().getDefensiveWeapons().get(i).getPosition()[1]]=
                    new EnemyCell(attackThread.getEnemyMap().getDefensiveWeapons().get(i).getARM_TYPE(),true);
        }
        for (Person p:soldiersOnMap) {
            if (p.getHealth()>0)
            enemyCells[p.getCurrentPosition()[0]][p.getCurrentPosition()[1]].addSoldirer(p.getType());
            else toBeRemoved.add(soldiersOnMap.indexOf(p));
        }
        for(int i : toBeRemoved) soldiersOnMap.remove(i);
    }
    private void setSoldiersOnDashBoard(){
        HBox soldiers = new HBox();
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
        dashboaredContainer.getChildren().addAll(soldiers,dashBoard);
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
                    soldiersOnMap.add(valuableSoldiers.get(i));
//                    map.getChildren().add(soldierGUIS.get(soldierGUIS.size()-1).getImageView());
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
    //.................................................
    public String singleBuildingShowerByType(int type) {
        switch (type) {
            case 1:
                return "Gold mine ";
            case 2:
                return ("Elixir mine ");
            case 3:
                return ("Gold storage ");
            case 4:
                return ("Elixir storage ");
            case 5:
                return ("Main building ");
            case 6:
                return ("Barracks ");
            case 7:
                return ("Camp ");
            case 8:
                return "Archer tower ";
            case 9:
                return "Cannon ";
            case 10:
                return "Air defense ";
            case 11:
                return "Wizard tower ";
            case 12:
                return "Wall";
            case 13:
            return "Trap";
            case 14:
            return "Guardian Giant";
            default:
                return ("invalid jsonType ");
        }
    }
    private String convertSoldierTypeToString(int type) {

        switch (type) {
            case 1:
                return ("Guardian ");
            case 2:
                return ("Giant ");
            case 3:
                return ("Dragon ");
            case 4:
                return ("Archer ");
            case 5:
                return ("WallBreaker ");
            case 6:
                return ("Healer ");
        }
        return "invalid soldier type";
    }
}
