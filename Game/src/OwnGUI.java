import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

import static utils.ConstantNumbers.MAP_SPACING;
import static utils.ConstantNumbers.*;
import static utils.HashImage.*;
import static utils.ConstantNumbers.SPACING;
import static utils.ConstantStrings.*;

import static utils.Icon.*;

public class OwnGUI extends Application {
    int speed = 50;
    private World world = new World();
    Building currentBuilding = null;
    DefensiveWeapon currentDefensiveWeapon = null;
    Barracks currentBarrack = null;
    Camp currentCamp = null;
    int currentSoldierTypeToBeBuilt = 0;
    int currentBuildingTypeToBeBuilt = 0;

//graphic
    Stage primaryStage = new Stage();
    GraphicOwnMapCell[][] mm = new GraphicOwnMapCell[30][30];
    Group root = new Group();
    VBox dashBoard = new VBox();
    VBox dashboaredContainer = new VBox();
    VBox map = new VBox();
    Scene scene = new Scene(root , 1100 , 720);
    //main
    ScrollPane mapScroll = new ScrollPane(map);
    BorderPane borderPane = new BorderPane(mapScroll);

    HBox root1 = new HBox(dashboaredContainer,borderPane);
    Scene scene1 = new Scene(root1,1100,720);
    VBox attackMenuComponents = new VBox();
    Group root2 = new Group();
    VBox preview = new VBox();
    Scene scene2 = new Scene(root2,1100,720);
    VBox selectSoldiersComponents = new VBox();
    Group root3 = new Group();
    Scene scene3 = new Scene(root3,1100,720);
    KeyFrame kf= new KeyFrame(new Duration(1000), new EventHandler<ActionEvent>() {

        @Override
        public void handle(ActionEvent event) {
            updateMapVbox();
            if (currentDefensiveWeapon != null){
                dashBoard.getChildren().clear();
                updateDashBoared(currentDefensiveWeapon.getARM_TYPE());
            }else if (currentBuilding!=null){
                dashBoard.getChildren().clear();
                updateDashBoared(currentBuilding.getJasonType());
            }
            world.currentGame.turnTimeOwnMap(speed);
            }

    });
    Timeline clashAnimation = new Timeline(Timeline.INDEFINITE,kf);
    public static void main(String[] args) {
        launch(args);
    }
    ListView<VBox> buildingListQueue = new ListView<>();
    ObservableList<VBox> buildingItems = FXCollections.observableArrayList ();
    ListView<VBox> availableBuildingList = new ListView<>();
    ObservableList<VBox> availableBuildingItems = FXCollections.observableArrayList ();
    ListView<VBox> soldierListQueue = new ListView<>();
    ObservableList<VBox> soldierItems = FXCollections.observableArrayList ();
    ListView<VBox> availableSoldierList = new ListView<>();
    ObservableList<VBox> availableSoldierItems = FXCollections.observableArrayList ();
    ListView<VBox> campSoldierList = new ListView<>();
    ObservableList<VBox> campSoldierItems = FXCollections.observableArrayList ();
    ListView<ImageView> targetList = new ListView<>();
    ObservableList<ImageView> targetItems = FXCollections.observableArrayList ();
    ListView<Button> attackMapsList = new ListView<>();
    ObservableList<Button> attackMapItems = FXCollections.observableArrayList ();

    //start
    @Override
    public void start(Stage stage) throws Exception {
        clashAnimation.setCycleCount(Timeline.INDEFINITE);
        initialOwnMap();
        setInitialMenu();
        primaryStage.setScene(scene);
        primaryStage.show();
        dashBoard.setPadding(new Insets(5,20,5,20));
        dashBoard.setSpacing(8);
    }
    private void setSelectionScene(){
        Label selectSoldiersL = new Label(SELECT_SOLDIERS);
        Button guardianB = new Button(convertSoldierTypeToString(1));
        Button giantB = new Button(convertSoldierTypeToString(2));
        Button dragonB = new Button(convertSoldierTypeToString(3));
        Button archerB = new Button(convertSoldierTypeToString(4));
        Button wallBreakerB = new Button(convertSoldierTypeToString(5));
        Button healerB = new Button(convertSoldierTypeToString(6));
        Button endSelectionB = new Button(END_SELECTION);
        Button selectSoldiersBackB = new Button(BACK);
        HBox buttonHbox = new HBox(SPACING,endSelectionB,selectSoldiersBackB);
        buttonHbox.setPadding(new Insets(PADDING,PADDING,PADDING,PADDING));
        VBox soldiers = new VBox(SPACING,selectSoldiersL,guardianB,giantB,dragonB,archerB,wallBreakerB,healerB);
        soldiers.setPadding(new Insets(PADDING,PADDING,PADDING,PADDING));
        selectSoldiersComponents = new VBox(SPACING,soldiers,buttonHbox);
        selectSoldiersComponents.setPadding(new Insets(PADDING,PADDING,PADDING,PADDING));
        root3.getChildren().add(selectSoldiersComponents);
        primaryStage.setScene(scene3);
        endSelectionB.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                AttackThread attackThread =new AttackThread(world.currentGame,world.currentEnemy , primaryStage , scene);
                Thread thread = new Thread(attackThread);
                thread.start();
                AttackGUI attackGUI = new AttackGUI(attackThread);
                primaryStage.setScene(attackGUI.getScene());

                for(;;)
                    if(attackThread.getEnd()){


                        primaryStage.setScene(scene1);
                        break;

                    }


            }
        });
        selectSoldiersBackB.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                setAttackScene();
            }
        });
        guardianB.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                setUPNumOfSoldiersToBeSelected(1);
            }
        });
        giantB.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                setUPNumOfSoldiersToBeSelected(2);
            }
        });
        dragonB.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                setUPNumOfSoldiersToBeSelected(3);
            }
        });
        archerB.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                setUPNumOfSoldiersToBeSelected(4);
            }
        });
        wallBreakerB.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                setUPNumOfSoldiersToBeSelected(5);
            }
        });
        healerB.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                setUPNumOfSoldiersToBeSelected(6);
            }
        });
    }
    //............................................................
    private void setAttackScene(){
        updateAttackMapsList();
        root2.getChildren().clear();
        attackMapsList.setPadding(new Insets(PADDING,PADDING,PADDING,PADDING));
        Button loadMapB = new Button(LOAD_MAP);
        Label availableMaps = new Label(AVAILABLE_MAPS);
        Button attackBackB = new Button(BACK);
        attackMenuComponents = new VBox(SPACING,loadMapB,availableMaps,attackMapsList,attackBackB);
        attackMenuComponents.setPadding(new Insets(PADDING,PADDING,PADDING,PADDING));
        loadMapB.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                setUpLoadEnemyMapDialog();
            }
        });
        attackBackB.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                setOwnMap();
            }
        });
        preview.relocate(500,150);
        root2.getChildren().addAll(attackMenuComponents,preview);
        primaryStage.setScene(scene2);
    }
    //.......................................................................
    public void updatePreview(){
        preview.getChildren().clear();
        int gold = world.currentEnemy.getResources().get("gold");
        int elixir = world.currentEnemy.getResources().get("elixir");
//        setUpEnemyMapInfo(gold, elixir, world.currentEnemy.getBuildings());
        StringBuilder context = new StringBuilder();
        context.append("Gold : ").append(gold);
        context.append("\nElixir : ").append(elixir);
        for (int type = 1; type <= 14; type++) {
            int number = 0;
            String soldierType = null;
            for (EnemyBuilding enemyBuilding : world.currentEnemy.getBuildings())
                if (enemyBuilding.getType() == type)
                    number++;
            if (number > 0) {
                switch (type) {
                    case 1: {
                        soldierType = "Gold mine";
                        break;
                    }
                    case 2: {
                        soldierType = "Elixir mine";
                        break;
                    }
                    case 3: {
                        soldierType = "Gold Storage";
                        break;
                    }
                    case 4: {
                        soldierType = "Elixir Storage";
                        break;
                    }
                    case 5: {
                        soldierType = "Main Building";
                        break;
                    }
                    case 6: {
                        soldierType = "Barracks";
                        break;
                    }
                    case 7: {
                        soldierType = "Camp";
                        break;
                    }
                    case 8: {
                        soldierType = "Archer tower";
                        break;
                    }
                    case 9: {
                        soldierType = "Cannon";
                        break;
                    }
                    case 10: {
                        soldierType = "Air defense";
                        break;
                    }
                    case 11: {
                        soldierType = "Wizard tower";
                        break;
                    }
                    case 12: {
                        soldierType = "Wall";
                    }
                    break;
                    case 13: {
                        soldierType = "Trap";
                    }
                    break;
                    case 14: {
                        soldierType = "Guardian Giant";
                    }
                    break;
                }
                context.append("\n").append(soldierType).append(" (").append(type).append(") : ").append(number);
            }
        }
        Label label1 = new Label("Preview");
        Label label2 = new Label(context.toString());
        Button attackMap = new Button("Attack");
        attackMap.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
            setSelectionScene();
            }
        });
        preview.getChildren().addAll(label1,label2,attackMap);
    }
    public void updateAttackMapsList(){
        attackMapItems.clear();
        if (world.getEnemyMaps() != null) {
            for (int i = 0; i < world.getEnemyMaps().size(); i++) {
                Button button = new Button(world.getEnemyMaps().get(i).getName());
                int index = i;
                attackMapItems.add(button);
                button.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        world.setEnemyMapToCurrentGame(index);
                        updatePreview();
                    }
                });
            }
        }
        attackMapsList.setItems(attackMapItems);
    }
    //.........................................................................
    private void initialOwnMap(){
        //        initialiation of map
        for (int i = 0; i <30 ; i++) {
            for (int j = 0; j <30 ; j++) {
                mm[i][j] = new GraphicOwnMapCell();
            }
        }
    }
    private void setInitialMenu(){
        root.getChildren().clear();
        ImageView dragon = new ImageView(new Image(DRAGON_WHITE,300,300,true,true));
        dragon.relocate(30,30);
        MenuButton newGameButton = new MenuButton(NEW_GAME);
        newGameButton.relocate(500,250);
        MenuButton loadButton = new MenuButton(LOAD_GAME);
        loadButton.relocate(490,350);
        ImageView angel1 = new ImageView(new Image(ANGEL1));
        angel1.relocate(700,10);
        ImageView angel2 = new ImageView(new Image(ANGEL2));
        angel2.relocate(300,550);
        ImageView angel3 = new ImageView(new Image(ANGEL3));
        angel3.relocate(850,390);
        root.getChildren().addAll(dragon,newGameButton,loadButton,angel1,angel2,angel3);
        newGameButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                world.newGameMaker();
                setUpSpeedGameDialog();
                setOwnMap();
            }
        });
        loadButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                setUpSpeedGameDialog();
                setUpLoadGameDialog();
            }
        });
        primaryStage.setScene(scene);
    }
    private void setOwnMap(){
        root.getChildren().clear();
        dashBoard.getChildren().clear();
        dashboaredContainer.getChildren().clear();
        BouncingIcon villageBackB = new BouncingIcon(new Image(WHITE_BACK,SMALL_ICONS_SIZE,SMALL_ICONS_SIZE,true,true));
        BouncingIcon attackB = new BouncingIcon(new Image(ZERE_SIAH_SEFID,SMALL_ICONS_SIZE,SMALL_ICONS_SIZE,true,true));
        BouncingIcon resourcesB = new BouncingIcon(new Image(COINS,SMALL_ICONS_SIZE,SMALL_ICONS_SIZE,true,true));
        attackB.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
//               setUpAttackMenuScene();
                setAttackScene();
               clashAnimation.stop();
            }
        });
        resourcesB.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                setUpResourceInfo(world.currentGame.getOwnResources(), world.currentGame.getOwnScore());
            }
        });
        villageBackB.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                clashAnimation.stop();
                setUpSaveGameDialog();
                world.currentGame = null;
                setInitialMenu();
                            }
        });
        HBox hBox = new HBox(SPACING,villageBackB,resourcesB,attackB);
        hBox.setPadding(new Insets(20,100,0,20));
        hBox.setStyle("-fx-background-color: #FFFFFF;");
        dashBoard.setStyle("-fx-background-color: #FFFFFF;");
        dashboaredContainer.setStyle("-fx-background-color: #FFFFFF;");
        dashboaredContainer.getChildren().addAll(hBox,dashBoard);
        borderPane.relocate(1100 - 725,0);
        addLine();
        updateMapVbox();
        primaryStage.setScene(scene1);
        clashAnimation.playFromStart();
        /////////////////////////////////////////////////////////////
        root1.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode()== KeyCode.S){
                    setUpSaveGameDialog();
                }
            }
        });

    }
    private void updateDashBoared(int type){
        dashBoard.getChildren().clear();
        if(type==1) updateGoldMineDashBoared1();
        if(type==2)updateElixirMineDashBoared2();
        if(type==3 || type==4) updateStoragesDashBoared3_4(type);
        if (type==5) updateTownHallDashBoared5();
        if (type==6) updateBarracksDashBoared6();
        if (type == 7) updateCampDashBoared7();
        if (type>=8&&type<=14) updateDefensiveWeapons8_14(type);
    }
    //...........................................................
    private void updateGoldMineDashBoared1(){
        GoldMine temp = (GoldMine) currentBuilding;
        Label label = new Label("Gold Produce : " + temp.getGoldProduce()+ " Per DeltaT");
        Label label2 = new Label("Level : " + currentBuilding.getLevel() +"\nHealth : " + currentBuilding.getResistance());
        StringBuilder context = new StringBuilder();
        if (currentBuilding.getCostOfUpgrade()[0] > 0)
            context.append("Upgrade Cost : " + currentBuilding.getCostOfUpgrade()[0] + " gold");
        if (currentBuilding.getCostOfUpgrade()[1] > 0)
            context.append("\nUpgrade Cost : " +currentBuilding.getCostOfUpgrade()[0] + " elixir");
        Label label3 = new Label(context.toString());

        Button upgradeB = new Button("Upgrade");
        upgradeB.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                setUpUpgradeConfirmation(currentBuilding.getJasonType(), currentBuilding.getCostOfUpgrade()[0]);
            }
        });
        dashBoard.getChildren().addAll(new ImageView(new Image(buildingPhoto.get(1))),label,label2,label3,upgradeB);
    }
    private void updateElixirMineDashBoared2(){
        ElixirMine temp = (ElixirMine) currentBuilding;
        Label label = new Label("Elixir Produce : " + temp.getElixirProduce()+ " Per DeltaT");
        Label label2 = new Label("Level : " + currentBuilding.getLevel() +"\nHealth : " + currentBuilding.getResistance());
        StringBuilder context = new StringBuilder();
        if (currentBuilding.getCostOfUpgrade()[0] > 0)
            context.append("Upgrade Cost : " + currentBuilding.getCostOfUpgrade()[0] + " gold");
        if (currentBuilding.getCostOfUpgrade()[1] > 0)
            context.append("\nUpgrade Cost : " +currentBuilding.getCostOfUpgrade()[0] + " elixir");
        Label label3 = new Label(context.toString());
        Button upgradeB = new Button("Upgrade");
        upgradeB.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                setUpUpgradeConfirmation(currentBuilding.getJasonType(), currentBuilding.getCostOfUpgrade()[0]);
            }
        });
        dashBoard.getChildren().addAll(new ImageView(new Image(buildingPhoto.get(2))),label,label2,label3,upgradeB);
    }
    private void updateStoragesDashBoared3_4(int t){
        Label label1 = new Label("Level : " + currentBuilding.getLevel() +"\nHealth : " + currentBuilding.getResistance());
        StringBuilder context = new StringBuilder();
        if (currentBuilding.getCostOfUpgrade()[0] > 0)
            context.append("Upgrade Cost : " + currentBuilding.getCostOfUpgrade()[0] + " gold");
        if (currentBuilding.getCostOfUpgrade()[1] > 0)
            context.append("\nUpgrade Cost : " +currentBuilding.getCostOfUpgrade()[0] + " elixir");
        Label label2 = new Label(context.toString());
        Label label3 = new Label("Your gold storage is " +
                world.currentGame.getGoldAndElixirStorageAndCapacity()[0][0] +
                "/Your elixir storage is " + world.currentGame.getGoldAndElixirStorageAndCapacity()[0][1]);
        Button upgradeB = new Button();
        upgradeB.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                setUpUpgradeConfirmation(currentBuilding.getJasonType(), currentBuilding.getCostOfUpgrade()[0]);
            }
        });
        System.out.println("image address"+buildingPhoto.get(3));
        dashBoard.getChildren().addAll(new ImageView(new Image(buildingPhoto.get(t))),label1,label2,label3,upgradeB);

    }
    private void updateTownHallDashBoared5(){
        updateAvailableBuildingList();

        updateBuildingsInQueueList();
        availableBuildingList.setOrientation(Orientation.HORIZONTAL);
        buildingListQueue.setOrientation(Orientation.HORIZONTAL);
        Label label1 = new Label("Level : " + world.currentGame.getOwnTownHall().getLevel() +
                "\nHealth : " + world.currentGame.getOwnTownHall().getResistance());
        StringBuilder context = new StringBuilder();
        if (world.currentGame.getOwnTownHall().getCostOfUpgrade()[0] > 0)
            context.append("Upgrade Cost : " + world.currentGame.getOwnTownHall().getCostOfUpgrade()[0] + " gold");
        if (world.currentGame.getOwnTownHall().getCostOfUpgrade()[1] > 0)
            context.append("\nUpgrade Cost : " + world.currentGame.getOwnTownHall().getCostOfUpgrade()[0] + " elixir");
        Label label2 = new Label(context.toString());
        Button townHallUpgradeB = new Button("Upgrade");
        townHallUpgradeB.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                int r = world.currentGame.upgradeTownHall();
                if (r == -1) {
                    setUpDontHaveEnoughResourceErr();
                }else SetUpupgradedSuccessfullyInfo(world.currentGame.getOwnResources(), world.currentGame.getOwnScore());
            }
        });
        dashBoard.getChildren().addAll(new ImageView(new Image(buildingPhoto.get(5))),label1,label2,townHallUpgradeB,availableBuildingList,buildingListQueue);
    }
    private void updateBarracksDashBoared6(){
        updateAvailableSoldiersList();
        updateSoldiersQueueList();
        soldierListQueue.setOrientation(Orientation.HORIZONTAL);
        availableSoldierList.setOrientation(Orientation.HORIZONTAL);
        Label label1 = new Label("Level : " +currentBarrack.getLevel() +"\nHealth : " + currentBarrack.getResistance());
        StringBuilder context = new StringBuilder();
        if (currentBarrack.getCostOfUpgrade()[0] > 0)
            context.append("Upgrade Cost : " + currentBarrack.getCostOfUpgrade()[0] + " gold");
        if (currentBarrack.getCostOfUpgrade()[1] > 0)
            context.append("\nUpgrade Cost : " + currentBarrack.getCostOfUpgrade()[0] + " elixir");
        Label label2 = new Label(context.toString());
//        setUpUpgradeInfo(currentBarrack.getCostOfUpgrade());
        Button barracksUpgradeB = new Button("Upgrade");
        barracksUpgradeB.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
//                upgrade barrack
                if (currentBarrack != null) {
                    currentBuilding = currentBarrack;
                    setUpUpgradeConfirmation(currentBarrack.getJasonType(), currentBarrack.getCostOfUpgrade()[0]);
                } else {
                    System.err.println("BUG!!!!!!");
                }
            }
        });
        dashBoard.getChildren().addAll(new ImageView(new Image(buildingPhoto.get(6))),label1
                ,label2,barracksUpgradeB,availableSoldierList,soldierListQueue);
    }
    private void updateCampDashBoared7(){
        updateCampSoldiersList();
        campSoldierList.setOrientation(Orientation.HORIZONTAL);
        Label label1 = new Label("Level : " + currentCamp.getLevel() +"\nHealth : " + currentCamp.getResistance());
        Label label2 = new Label("Your camps capacity is " + world.currentGame.getSoldiersAndCapacityOfCamps()[0] +
                "/" +world.currentGame.getSoldiersAndCapacityOfCamps()[1]);
        dashBoard.getChildren().addAll(new ImageView(new Image(buildingPhoto.get(7))),label1,label2,campSoldierList);
    }
    private void updateDefensiveWeapons8_14(int type){
        updateTargetList();
        targetList.setOrientation(Orientation.HORIZONTAL);
        Label label1 = new Label("Level : " + currentDefensiveWeapon.getLevel()
                +"\nHealth : " + currentDefensiveWeapon.getResistence());
        StringBuilder context = new StringBuilder();
        if (currentDefensiveWeapon.getCOST_OF_UPGRADE()[0] > 0)
            context.append("Upgrade Cost : " + currentDefensiveWeapon.getCOST_OF_UPGRADE()[0] + " gold");
        if (currentDefensiveWeapon.getCOST_OF_UPGRADE()[1] > 0)
            context.append("\nUpgrade Cost : " + currentDefensiveWeapon.getCOST_OF_UPGRADE()[0] + " elixir");
        Label label2 = new Label(context.toString());
        Label label3 = new Label("Damage : "+currentDefensiveWeapon.getHitPower());
        Label label4 = new Label("Damage Radius : " + currentDefensiveWeapon.getRADIUS_OF_ATTACK());
        Button upgradeB = new Button("Upgrade");
        upgradeB.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                setUpUpgradeConfirmation(currentDefensiveWeapon.getARM_TYPE(), currentDefensiveWeapon.getCOST_OF_UPGRADE()[0]);
            }
        });
        dashBoard.getChildren().addAll(new ImageView(new Image(buildingPhoto.get(type))),targetList,label1,label2,label3,label4,upgradeB);

    }
    private void addLine (){
        Line line = new Line();
        line.setStartX(1100 - 725);
        line.setEndX(1100 - 725);
        line.setStartY(0);
        line.setEndY(719);
        root.getChildren().add(line);
    }
//    ...........................

public void updateAvailableBuildingList(){
    availableBuildingItems.clear();
    ArrayList<Integer> type = world.currentGame.availableBuildingsAndDefensiveWeapons();
    if (type != null) {
        for (int i = 0; i < type.size(); i++) {
            int t = type.get(i);
           ImageView button1 = new ImageView(new Image(buildingPhoto.get(t)));
//            Button button2 = new Button(convertTypeToBuilding(type.get(i)));
            VBox button = new VBox(button1);
            button.setPadding(new Insets(60,0,0,0));
            button.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {

                   int r = world.currentGame.constructionRequest(t);//check if it is true please!!!
                    System.out.println("clicked"+r);
                   if (r == -2){
                        setUpNotEnoughWorkerErr();
                   }
                   if (r==-1) {
                       setUpDontHaveEnoughResourceErr();
                   }
                   if (r == 0) {
                        currentBuildingTypeToBeBuilt = t;
                        setUpConstructionBuildingConfirmation(t, world.currentGame.getCostOfConstruction(t)[0]);
                   }
                }
            });
            availableBuildingItems.add(button);
        }//end of for
    }//end of if
    availableBuildingList.setItems(availableBuildingItems);
}
public void updateBuildingsInQueueList(){
    buildingItems.clear();
    for (int[] b : world.currentGame.getQueueOfBuildingsAndDefensiveWeaponsToBEBuilt()) {
        ImageView imageView = new ImageView(new Image(buildingPhoto.get(b[0])));
        Label label = new Label("  "+ b[1]);
        VBox vBox = new VBox(imageView,label);
        buildingItems.add(vBox);
    }
    buildingListQueue.setItems(buildingItems);
}
public void updateAvailableSoldiersList(){
    availableSoldierItems.clear();
    ArrayList<int[]> potentialSoldiers=world.currentGame.getPotentialSoldiers(currentBarrack);
    for (int i = 0; i < potentialSoldiers.size(); i++) {
        BouncingIcon button = new BouncingIcon(new Image(SoldierPhoto.get(potentialSoldiers.get(i)[0]),SOLDIER_SIZE,SOLDIER_SIZE,true,true));
        int type =potentialSoldiers.get(i)[0];
        Label label = new Label();
        if (potentialSoldiers.get(i)[1] > 0) {
            label.setText(" X " + potentialSoldiers.get(i)[1]);
            button.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    currentSoldierTypeToBeBuilt = type;
                    setUpNumOfSoldiersDialog();
                }
            });
        } else{
            label.setText(" U ");
            button.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    setUpCantChooseErr();
                }
            });
        }
        VBox vBox = new VBox(SOLDIERS_SPACING,button,label);
        availableSoldierItems.add(vBox);
    }
    availableSoldierList.setItems(availableSoldierItems);
}
public void updateSoldiersQueueList(){
        soldierItems.clear();
    for (int[] s : world.currentGame.getQueueOfSoldiers(currentBarrack)) {
        ImageView imageView = new ImageView(new Image(SoldierPhoto.get(s[0]),SOLDIER_SIZE,SOLDIER_SIZE,true,true));
        Label label = new Label(""+s[1]);
        VBox vBox = new VBox(imageView,label);
    }
        soldierListQueue.setItems(soldierItems);
}
public void updateCampSoldiersList(){
        campSoldierItems.clear();
    for (int i = 0; i < world.currentGame.getSoldiersOfCamps().length; i++) {
        if (world.currentGame.getSoldiersOfCamps()[i] > 0) {
            ImageView imageView = new ImageView(new Image(SoldierPhoto.get(i+1)));
            Label label = new Label(""+world.currentGame.getSoldiersOfCamps()[i]);
            VBox vBox = new VBox(imageView,label);
            campSoldierItems.add(vBox);
        }
    }
     campSoldierList.setItems(campSoldierItems);
}
public void updateTargetList(){
        targetItems.clear();
    for (int i = 0; i < 4; i++) {
        if (currentDefensiveWeapon.getTarget()[i] != 0) {
            ImageView imageView = new ImageView(new Image(SoldierPhoto.get(i+1)));
            targetItems.add(imageView);
        }
    }
    targetList.setItems(targetItems);
}
    //...................................................................................................
    public void ownBuildingsOnMap(){
        mm[15][14]=new GraphicOwnMapCell(5);
        mm[14][15]=new GraphicOwnMapCell(5);
        mm[15][15]=new GraphicOwnMapCell(5);
        mm[15][14].setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
            currentBuilding = world.currentGame.getOwnTownHall();
            }
        });
        mm[14][15].setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                currentBuilding = world.currentGame.getOwnTownHall();
            }
        });
        mm[15][15].setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                currentBuilding = world.currentGame.getOwnTownHall();
            }
        });
        for (Building b : world.currentGame.getOwnMap().getBuildings()){
            mm[b.getPosition()[0]][b.getPosition()[1]] = new GraphicOwnMapCell(b.getJasonType());
            mm[b.getPosition()[0]][b.getPosition()[1]].setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    if (b.getJasonType()>=1 &&b.getJasonType()<=5)
                        currentBuilding = world.currentGame.findBuildingTypeInOwnMap(b.getJasonType() ,b.getId());
                    else if (b.getJasonType()==6){
                        currentBuilding = world.currentGame.findBuildingTypeInOwnMap(b.getJasonType() ,b.getId());
                        currentBarrack = (Barracks) world.currentGame.findBuildingTypeInOwnMap(b.getJasonType() ,b.getId());
                    }
                    else if (b.getJasonType()==7) {
                        currentBuilding = world.currentGame.findBuildingTypeInOwnMap(b.getJasonType() ,b.getId());
                        currentCamp = (Camp) world.currentGame.findBuildingTypeInOwnMap(b.getJasonType() ,b.getId());
                    }
                    currentDefensiveWeapon = null;
                    System.out.println("type" + b.getJasonType());
                    updateDashBoared(b.getJasonType());
                }
            });
        }
        if (world.currentGame.getOwnDefensiveWeapon() != null) {
            for (DefensiveWeapon d : world.currentGame.getOwnDefensiveWeapon()) {
                mm[d.getPosition()[0]][d.getPosition()[1]]=new GraphicOwnMapCell(d.getARM_TYPE());
                mm[d.getPosition()[0]][d.getPosition()[1]].setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        currentDefensiveWeapon = world.currentGame.findDefensiveWeaponTypeInOwnMap(d.getARM_TYPE() , d.getId());
                        updateDashBoared(d.getARM_TYPE());
                        currentBuilding = null;
                    }
                });
            }
        }
    }
    public void updateMapVbox(){
        ownBuildingsOnMap();
        map.getChildren().clear();
        for (int i = 0; i <30 ; i++) {
            HBox hb = new HBox();
//            hb.setSpacing(MAP_SPACING);
            for (int j = 0; j < 30; j++) {
                int y = j ;
                int x = i;
                hb.getChildren().add(mm[i][j]);
                if (currentBuildingTypeToBeBuilt!=0){
                    mm[x][y].setOnMouseClicked(new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent event) {
                            if (world.currentGame.getOwnMap().getMap()[x][y].isEmpty() &&
                                    world.currentGame.getOwnMap().getMap()[x][y].isConstructable()){
                                if (currentBuildingTypeToBeBuilt!=0){
                                    int r = world.currentGame.newBuildingMaker(currentBuildingTypeToBeBuilt, x, y);
                                    if (r==-1){
                                        setUpCantChooseErr();
                                        System.out.println("-1");
                                    }else {
                                        currentBuildingTypeToBeBuilt = 0;
                                    }
                                }
                            }
                            else {
                                if (currentBuildingTypeToBeBuilt!=0) setUpCantChooseErr();
                            }


                        }
                    });
                }


            }//end of for
            map.getChildren().add(hb);
        }
    }
    //...................................................................................................
    //    errors
    public void setUpNoValidAddressErr(){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(ERROR);
        alert.setHeaderText(ERROR);
        alert.setContentText(WRONG_ADDRESS);
        alert.showAndWait();
    }
    public void setUpDontHaveEnoughResourceErr(){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(ERROR);
        alert.setHeaderText(ERROR);
        alert.setContentText("Oops,You Don't Have Enough Resources!");
        alert.showAndWait();
    }
    public void setUpHaveToUpgradeTownHallFirst(){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(ERROR);
        alert.setHeaderText(ERROR);
        alert.setContentText("Oops,You Have to Upgrade Town Hall First!");
        alert.showAndWait();
    }
    public void setUpCantBuildMoreThanOnCastle(){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(ERROR);
        alert.setHeaderText(ERROR);
        alert.setContentText("Oops,You Can't Have More Than One Giant Castle!");
        alert.showAndWait();
    }
    public void setUpCantChooseErr(){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(ERROR);
        alert.setHeaderText(ERROR);
        alert.setContentText("Sorry,You Can't Choose This One!");
        alert.showAndWait();
    }
    public void setUpNotEnoughWorkerErr(){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(ERROR);
        alert.setHeaderText(ERROR);
        alert.setContentText("Sorry,You Don't Have Enough Workers!");
        alert.showAndWait();
    }
    public void setUpNotEnoughUnitsErr(int type){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(ERROR);
        alert.setHeaderText(ERROR);
        alert.setContentText("Not Enough "+convertSoldierTypeToString(type)+"s !!!");
        alert.showAndWait();
    }
    public void setUpInvalidNumberFormat(){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(ERROR);
        alert.setHeaderText(ERROR);
        alert.setContentText("Invalid Number Format!");
        alert.showAndWait();
    }
    //    information
    public void setUpResourceInfo(int[] resources, int score){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(RESOURCES);
        alert.setHeaderText(RESOURCES);
        alert.setContentText("Gold : " + resources[0]+"\nElixir : " + resources[1]+"\nScore : " + score);
        alert.showAndWait();
    }
    public void setUpMineInfo(int goldProduced){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(MINE);
        alert.setHeaderText(MINE);
        alert.setContentText("Gold Produce : " + goldProduced + " Per DeltaT");
        alert.showAndWait();
    }
    public void setUpOverallInfo(int level, int resistance){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(OVERALL_INFO);
        alert.setHeaderText(OVERALL_INFO);
        alert.setContentText("Level : " + level +"\nHealth : " + resistance);
        alert.showAndWait();
    }
    public void setUpUpgradeInfo(int[] costOfUpgrade){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(UPGRADE_INFO);
        alert.setHeaderText(UPGRADE_INFO);
        StringBuilder context = new StringBuilder();
        if (costOfUpgrade[0] > 0)
            context.append("Upgrade Cost : " + costOfUpgrade[0] + " gold");
        if (costOfUpgrade[1] > 0)
            context.append("\nUpgrade Cost : " + costOfUpgrade[0] + " elixir");
        alert.setContentText(context.toString());
        alert.showAndWait();
    }
    public void SetUpupgradedSuccessfullyInfo(int[] resources, int score){
        String context = "Will be Upgraded Successfully And Now Your Resources And Score Are : "+"\nGold : " + resources[0] +"\nElixir : " + resources[1] +"\nScore :" + score;
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(UPGRADED);
        alert.setHeaderText(UPGRADED);
        alert.setContentText(context);
        alert.showAndWait();
    }
    public void setUpTargetInfo(int[] target){
        StringBuilder context = new StringBuilder();
        for (int i = 0; i < 4; i++) {
            if (target[i] != 0) {
                context.append(convertSoldierTypeToString(i + 1)).append(" ");
            }
        }
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(TARGET);
        alert.setHeaderText(TARGET);
        alert.setContentText(context.toString());
        alert.showAndWait();
    }
    public void setUpBarracksStatusInfo(ArrayList<int[]> queue){
        StringBuilder context = new StringBuilder();
        for (int[] s : queue) {
            context.append(convertSoldierTypeToString(s[0])).append(" ").append(s[1]).append("\n");
        }
        TextArea textArea = new TextArea(context.toString());
        VBox dialogPaneContent = new VBox(SPACING,textArea);
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Soldiers in construction");
        alert.setHeaderText("Soldiers in queue ");
        alert.getDialogPane().setContent(dialogPaneContent);
        alert.showAndWait();
    }
    public void setUpCampSoldiersInfo(int[] soldierNums){
        StringBuilder context = new StringBuilder();
        int m = 0;
        for (int i = 0; i < soldierNums.length; i++) {
            if (soldierNums[i] > 0) {
                m+=1;
                context.append(convertSoldierTypeToString(i + 1)).append(" X ").append(soldierNums[i]).append("\n");
            }
        }
        if (m==0){
            context.append("No Soldiers In Camps!");
        }
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Camp Soldiers");
        alert.setHeaderText("Soldiers In Camp ");
        alert.setContentText(context.toString());
        alert.showAndWait();
    }
    public void setUpCampCapacityInfo(int numOfSoldiers, int totalCapacity){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Capacity");
        alert.setHeaderText("Capacity ");
        alert.setContentText("Your camps capacity is " + numOfSoldiers + "/" + totalCapacity);
        alert.showAndWait();
    }
    public void setUpTownHallStatusInfo(ArrayList<int[]> queue){
        StringBuilder context = new StringBuilder();
        for (int[] b : queue) {
            context.append(convertTypeToBuilding(b[0])).append(" ").append(b[1]).append("\n");
        }
        TextArea textArea = new TextArea(context.toString());
        VBox dialogPaneContent = new VBox(SPACING,textArea);
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Buildings in construction");
        alert.setHeaderText("Buildings in queue ");
        alert.getDialogPane().setContent(dialogPaneContent);
        alert.showAndWait();
    }
    public void setUpWelcomeInfo(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Welcome");
        alert.setHeaderText("Pay Attention!");
        alert.setContentText("You can easily SAVE game any where by pressing S on keyboard!\nTURN TIME for ONE deltaT by pressing" +
                "SPACE!\nTURN TIME for MORE THAN ONE deltaT by pressing T! ;) ");
        alert.showAndWait();
    }
    public void setUpEnemyMapInfo(int gold, int elixir, List<EnemyBuilding> enemyBuildings){
        StringBuilder context = new StringBuilder();
        context.append("Gold : ").append(gold);
        context.append("\nElixir : ").append(elixir);
        for (int type = 1; type <= 14; type++) {
            int number = 0;
            String soldierType = null ;
            for (EnemyBuilding enemyBuilding : enemyBuildings)
                if (enemyBuilding.getType() == type)
                    number++;
            if (number > 0){
                switch (type){
                    case 1:{
                        soldierType = "Gold mine";
                        break;}
                    case 2:{
                        soldierType = "Elixir mine";
                        break;}
                    case 3:{
                        soldierType = "Gold Storage";
                        break;}
                    case 4:{
                        soldierType = "Elixir Storage";
                        break;}
                    case 5:{
                        soldierType = "Main Building";
                        break;}
                    case 6:{
                        soldierType = "Barracks";
                        break;}
                    case 7:{
                        soldierType = "Camp";
                        break;}
                    case 8:{
                        soldierType = "Archer tower";
                        break;}
                    case 9:{
                        soldierType = "Cannon";
                        break;}
                    case 10:{
                        soldierType = "Air defense";
                        break;}
                    case 11:{
                        soldierType = "Wizard tower";
                        break;}
                    case 12:{
                        soldierType = "Wall";
                    }break;
                    case 13:{
                        soldierType = "Trap";
                    }break;
                    case 14:{
                        soldierType = "Guardian Giant";
                    }break;
                }
                context.append("\n").append(soldierType).append(" (").append(type).append(") : ").append(number);
            }
        }
        TextArea textArea = new TextArea
                (context.toString());
        VBox dialogPaneContent = new VBox(SPACING,textArea);
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Enemy Map Info");
        alert.setHeaderText("Enemy Map Info ");
        alert.getDialogPane().setContent(dialogPaneContent);
        alert.showAndWait();

    }

    //    confirmations
    public void setUpUpgradeConfirmation(int buildingType, int goldCost){
        String context = "Do you want to upgrade "+ convertTypeToBuilding(buildingType)+" for " + goldCost + " golds?";
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(UPGRADE);
        alert.setHeaderText(UPGRADE);
        alert.setContentText(context);
        // option != null.
        Optional<ButtonType> option = alert.showAndWait();
        if (option.isPresent()){
            if (option.get() == ButtonType.OK){
                // TODO: 6/7/2018
                int r = 10;
                if (currentBuilding != null) {
                    r = world.currentGame.upgradeOwnBuilding(currentBuilding);
                } else {
                    if (currentDefensiveWeapon != null) {
                        r = world.currentGame.upgradeOwnDefensiveBuilding(currentDefensiveWeapon);
                    } else System.err.println("BUG!!!!");
                }
                if (r == -1) {
                    setUpDontHaveEnoughResourceErr();
                }
                if (r == 0) {
                    System.err.println("upgraded successfully and now your resource is :");

                    SetUpupgradedSuccessfullyInfo(world.currentGame.getOwnResources(), world.currentGame.getOwnScore());
                }
                if (r == -2) {
                    setUpHaveToUpgradeTownHallFirst();
                }
                if (r==-3){
                    System.out.println("cant build more than one Giant Castle!");
                    setUpCantBuildMoreThanOnCastle();
                }
            }else if (option.get() == ButtonType.CANCEL){
                // TODO: 6/7/2018
            }
        }

    }
    public void setUpConstructionBuildingConfirmation(int type, int cost){
        String context = "Build "+convertTypeToBuilding(type)+" For Cost :"+cost;
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(BUILD);
        alert.setHeaderText(BUILD);
        alert.setContentText(context);
        // option != null.
        Optional<ButtonType> option = alert.showAndWait();
        if (option.isPresent()){
            if (option.get() == ButtonType.OK){
//                setUpConstructionOnOwnMapScene();
            }else if (option.get()== ButtonType.CANCEL){
                currentBuildingTypeToBeBuilt = 0;
            }
        }
    }

    //    dialogs
    public void setUpSpeedGameDialog(){
        String address = null;
        TextInputDialog dialog = new TextInputDialog();
        dialog.setHeaderText("Rate Of Game");
        dialog.setContentText("");
        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()){
            speed = Integer.parseInt(result.get());
        }
    }
    public void setUpLoadGameDialog(){
        String address = null;
        TextInputDialog dialog = new TextInputDialog();
        dialog.setHeaderText(LOAD_MAP);
        dialog.setContentText(ADDRESS_EXAMPLE);
        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()){
            address = result.get();
        }
        if (address != null){
            world.loadGame(address);
            if (world.situationOfLoading == -1){
                setUpNoValidAddressErr();
                setInitialMenu();
            }else {
                System.out.println("assress accepted");
                this.world = world.loadGame(address);
                setOwnMap();
            }
        }
    }
    public void setUpNumOfSoldiersDialog(){
        String number = null;
        TextInputDialog dialog = new TextInputDialog();
        dialog.setHeaderText(NUM_OF_SOLDIERS);
        dialog.setContentText(NUM_EXAMPLE);
        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()){
            number = result.get();
        }
        if (number != null){
            if (Pattern.matches("\\d+",number)){
                int r = world.currentGame.soldierMaker(currentSoldierTypeToBeBuilt, Integer.parseInt(number), currentBarrack);
                currentSoldierTypeToBeBuilt = 0;
                if (r==-1) setUpDontHaveEnoughResourceErr();
                else System.err.println("Built soldiers successfully");
            }else setUpInvalidNumberFormat();
//            setUpAvailableSoldiersMenuScene();
        }
    }
    public void setUpLoadEnemyMapDialog(){
        String address = null;
        TextInputDialog dialog = new TextInputDialog();
        dialog.setHeaderText("Enter Address To Load Enemy Map");
        dialog.setContentText(ADDRESS_EXAMPLE);
        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()){
            address = result.get();
        }
        if (address != null){
            int r = world.loadEnemyMap(address);
            if (r == -1) setUpNoValidAddressErr();
            setAttackScene();

        }

    }
    public void setUpSaveGameDialog(){
        String address = null;
        TextInputDialog dialog = new TextInputDialog();
        dialog.setHeaderText("Enter Address To Save");
        dialog.setContentText(ADDRESS_EXAMPLE);
        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()){
            address = result.get();
        }
        if (address != null){
            int r = world.saveGame(address, world);
            if (r == -1) setUpNoValidAddressErr();
        }
    }
    public void setUpTurnTimeDialog(){
        String number = null;
        TextInputDialog dialog = new TextInputDialog();
        dialog.setHeaderText("Num Of Turns");
        dialog.setContentText("Num Of Turns");
        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()){
            number = result.get();
        }
        if (number != null){
            if (Pattern.matches("\\d+",number)){
                world.currentGame.turnTimeOwnMap(Integer.parseInt(number));
            }else setUpInvalidNumberFormat();

        }
    }
    public void setUPNumOfSoldiersToBeSelected(int soldierType){
        int[] r = new int[2];
        r[0] = soldierType;
        String number = null;
        TextInputDialog dialog = new TextInputDialog();
        dialog.setHeaderText("Num Of Soldiers");
        dialog.setContentText("Num Of "+convertSoldierTypeToString(soldierType)+"s");
        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()){
            number = result.get();
        }
        if (number != null){
            if (Pattern.matches("\\d+",number)){
                r[1] = Integer.parseInt(number);
                if (world.currentGame.selectUnit(r) == 1){
                   setUpNotEnoughUnitsErr(r[0]);
                }
            }else setUpInvalidNumberFormat();
        }

    }
    //.....................................................................................
    public String convertTypeToBuilding(int type) {
        switch (type) {
            case 1:
                return "Gold mine";
            case 2:
                return ("Elixir mine");
            case 3:
                return ("Gold storage");
            case 4:
                return ("Elixir storage");
            case 5:
                return ("Main building");
            case 6:
                return ("Barracks");
            case 7:
                return ("Camp");
            case 8:
                return "Archer tower";
            case 9:
                return "Cannon";
            case 10:
                return "Air defense";
            case 11:
                return "Wizard tower";
            case 12:
                return "Wall";
            case 13:
                return "Trap";
            case 14:
                return "Guardian Giant";
            default:
                return ("invalid jsonType");
        }
    }
    public String convertSoldierTypeToString(int type) {

        switch (type) {
            case 1:
                return ("Guardian");
            case 2:
                return ("Giant");
            case 3:
                return ("Dragon");
            case 4:
                return ("Archer");
            case 5:
                return ("Wall breaker");
            case 6:
                return ("Healer");
        }
        return "invalid soldier type";
    }
    //....................................................................

}//end of class
