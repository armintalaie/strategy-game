import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

import static utils.ConstantStrings.*;
import static utils.ConstantNumbers.*;

public class GraphicView extends Application {
    View view = new View();
    private World world = new World();
    String currentCommand = null;
    Building currentBuilding = null;
    DefensiveWeapon currentDefensiveWeapon = null;
    Barracks currentBarrack = null;
    Camp currentCamp = null;
    int currentSoldierTypeToBeBuilt = 0;
    int currentBuildingTypeToBeBuilt = 0;
    Integer input = 0;
    int timeAttack = 0;

//    graphics
    Stage stage = new Stage();
    Scene initialMenuScene;
    VBox initialComponents;
    Scene villageMenuScene;
    VBox villageMenuComponents;
    Scene showBuildingMenuScene;
    VBox showBuildingsComponent;
    Scene mineMenuScene;
    VBox mineMenuComponents;
    Scene storageMenuScene;
    VBox storageMenuComponents;
    Scene storageInfoMenuScene;
    VBox storageInfoComponents;
    Scene mineInfoMenuScene;
    VBox mineInfoComponents;
    Scene defensiveWeaponMenuScene;
    VBox defensiveComponents;
    Scene defensiveWeaponInfoMenuScene;
    VBox defensiveInfoComponents;
    Scene upgradeMenuScene;
    Scene townHallMenuScene;
    VBox townHallComponents;
    Scene townHallInfoMenuScene;
    VBox townHallInfoComponents;
    Scene availableBuildingMenuScene;
    VBox availableBuildingComponents;
    Scene barracksMenuScene;
    VBox barracksComponents;
    Scene barracksInfoMenuScene;
    VBox barracksInfoComponents;
    Scene campMenuScene;
    VBox campComponents;
    Scene campInfoMenuScene;
    VBox campInfoComponents;
    Scene attackMenuScene;
    VBox attackMenuComponents;
    Scene targetMapMenuScene;
    VBox targetMapMenuComponents;
    Scene availableSoldiersMenuScene;
    VBox availableSoldiersMenuComponents;
    Scene constructionOnOwnMapScene;
    VBox constructionOnOwnMapComponents;
    Scene selectSoldiersScene;
    VBox selectSoldiersComponents;
//    maps
//    Label test = new Label("test");
    VBox ownMap = new VBox();
//    lists
    ListView<Button> buildingList = new ListView<>();
    ObservableList<Button> buildingItems = FXCollections.observableArrayList ();

    ListView<Button> availableBuildingList = new ListView<>();
    ObservableList<Button> availableBuildingItems = FXCollections.observableArrayList ();

    ListView<Button> attackMapsList = new ListView<>();
    ObservableList<Button> attackMapItems = FXCollections.observableArrayList ();

    ListView<HBox> availableSoldiersList = new ListView<>();
    ObservableList<HBox> availableSoldiersItems = FXCollections.observableArrayList ();


    public void setUpInitialMenuScene() {
        stage.setScene(initialMenuScene);
        stage.setTitle(INITIAL_MENU);
    }

    public void setUpVillageMenuScene() {
        stage.setScene( villageMenuScene );
        stage.setTitle(VILLAGE_MENU);
    }
    public void setUpshowBuildingMenuScene(){
        updateBuildingList();
        stage.setScene(showBuildingMenuScene);
        stage.setTitle(SHOW_BUILDINGS);
}
    public void setUpMineMenuScene() {
        stage.setScene(mineMenuScene);
        stage.setTitle(MINE_MENU);
    }

    public void setUpStorageMenuScene() {
        stage.setScene(storageMenuScene);
        stage.setTitle(STORAGE_MENU);
    }

    public void setUpStorageInfoMenuScene() {
        stage.setScene(storageInfoMenuScene);
        stage.setTitle(STORAGE_INFO_MENU);
    }

    public void setUpMineInfoMenuScene() {
        stage.setScene(mineInfoMenuScene);
        stage.setTitle(MINE_INFO_MENU);
    }

    public void setUpDefensiveWeaponMenuScene() {
        stage.setScene(defensiveWeaponMenuScene);
        stage.setTitle(DEFENSIVE_WEAPON_MENU);
    }

    public void setUpDefensiveWeaponInfoMenuScene() {
        stage.setScene(defensiveWeaponInfoMenuScene);
        stage.setTitle(DEFENSIVE_WEAPON_INFO_MENU);
    }

    public void setUpUpgradeMenuScene() {
        stage.setScene(upgradeMenuScene);
        stage.setTitle(UPGRADE_MENU);
    }

    public void setUpTownHallMenuScene() {
        stage.setScene(townHallMenuScene);
        stage.setTitle(TOWN_HALL_MENU);
    }

    public void setUpTownHallInfoMenuScene() {
        stage.setScene(townHallInfoMenuScene);
        stage.setTitle(TOWN_HALL_INFO_MENU);
    }

    public void setUpAvailableBuildingMenuScene() {
        updateAvailableBuildingList();
        stage.setScene(availableBuildingMenuScene);
        stage.setTitle(AVAILABLE_BUILDINGS);
    }

    public void setUpBarracksMenuScene() {
        stage.setScene( barracksMenuScene);
        stage.setTitle(BARRACKS_MENU);
    }

    public void setUpBarracksInfoMenuScene() {
        stage.setScene(barracksInfoMenuScene);
        stage.setTitle(BARRACKS_INFO_MENU);
    }

    public void setUpCampMenuScene() {
        stage.setScene(campMenuScene);
        stage.setTitle(CAMP_MENU);
    }

    public void setUpCampInfoMenuScene() {
        stage.setScene(campInfoMenuScene);
        stage.setTitle(CAMP_INFO_MENU);
    }

    public void setUpAttackMenuScene() {
        updateAttackMapsList();
        stage.setScene(attackMenuScene);
        stage.setTitle(ATTACK_MENU);
    }

    public void setUpTargetMapMenuScene() {
        stage.setScene(targetMapMenuScene);
        stage.setTitle(TARGET_MAP_MENU);
    }

    public void setUpAvailableSoldiersMenuScene() {
        updateAvailableSoldiersList();
        stage.setScene(availableSoldiersMenuScene);
        stage.setTitle(SOLDIERS);
    }

    public void setUpConstructionOnOwnMapScene() {
        ownMap.getChildren().clear();

        ownMap.setSpacing(MAP_SPACING);
        ownMap.setPadding(new Insets(PADDING,PADDING,PADDING,PADDING));
        for (int i = 0; i < 30; i++) {
            HBox hBox = new HBox();
            hBox.setSpacing(MAP_SPACING);
            for (int j = 0; j < 30; j++) {
                int y = j ;
                int x = i;
                Rectangle cell = new Rectangle(CELL_LENGTH,CELL_LENGTH);
                if (world.currentGame.getOwnMap().getMap()[i][j].isEmpty() && world.currentGame.getOwnMap().getMap()[i][j].isConstructable()) {
                    cell.setFill(Color.HOTPINK);
                    cell.setOnMouseClicked(new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent event) {
                            int r = world.currentGame.newBuildingMaker(currentBuildingTypeToBeBuilt, x, y);
                            if (r==-1){
                                view.youCantBuildInThisPosition();
                                // TODO: 6/7/2018
                            }else {
                                currentBuildingTypeToBeBuilt = 0;
                                setUpAvailableBuildingMenuScene();
                            }
                        }
                    });
                }else {
                    cell.setOnMouseClicked(new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent event) {
                            setUpCantChooseErr();
                        }
                    });
                }
                hBox.getChildren().add(cell);
            }
            ownMap.getChildren().add(hBox);
        }
        stage.setScene(constructionOnOwnMapScene);
        stage.setTitle(CONSTRUCTION_BUILDING_WINDOW);
    }

    public void setUpSelectSoldiersScene() {
        stage.setScene(selectSoldiersScene);
        stage.setTitle("Select Soldiers");
    }

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
                }
                context.append("\n").append(soldierType).append(" (").append(type).append(") : ").append(number);
            }
        }
        TextArea textArea = new TextArea(context.toString());
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
                    view.shoeResources(world.currentGame.getOwnResources(), world.currentGame.getOwnScore()); //to test
                    SetUpupgradedSuccessfullyInfo(world.currentGame.getOwnResources(), world.currentGame.getOwnScore());
                }
                if (r == -2) {
                    setUpHaveToUpgradeTownHallFirst();
                }
            }else if (option.get() == ButtonType.CANCEL){
                // TODO: 6/7/2018
            }
        }

    }
    public void setUpConstructionBuildingConfirmation(int type, int cost){
        String context = "Build "+convertTypeToBuilding(type)+" For Cost :"+cost;
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(UPGRADE);
        alert.setHeaderText(UPGRADE);
        alert.setContentText(context);
        // option != null.
        Optional<ButtonType> option = alert.showAndWait();
        if (option.isPresent()){
            if (option.get() == ButtonType.OK){
                setUpConstructionOnOwnMapScene();
            }else if (option.get()== ButtonType.CANCEL){
                currentBuildingTypeToBeBuilt = 0;
            }
        }
    }

//    dialogs
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
            int r = world.loadEnemyMap(address);
            if (r == -1) setUpNoValidAddressErr();
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
            setUpAvailableSoldiersMenuScene();
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
            setUpAttackMenuScene();
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
                    view.notEnoughUnits(r[0]);
                    setUpNotEnoughUnitsErr(r[0]);
                }
            }else setUpInvalidNumberFormat();
        }


    }

    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
//    initial menu
        Button newGameButton = new Button(NEW_GAME);
        Button loadButton = new Button(LOAD_GAME);
        initialComponents = new VBox(SPACING,newGameButton,loadButton);
        initialComponents.setPadding(new Insets(PADDING,PADDING,PADDING,PADDING));
        initialMenuScene = new Scene(initialComponents);
        newGameButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                world.newGameMaker();
                setUpVillageMenuScene();
            }
        });
        loadButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                setUpLoadGameDialog();
            }
        });

//  village menu
        Button attackB = new Button(ATTACK);
        Button showBuildingsB = new Button(SHOW_BUILDINGS);
        Button resourcesB = new Button(RESOURCES);
        Button villageBackB = new Button(BACK);
        villageMenuComponents = new VBox(SPACING, showBuildingsB,resourcesB,attackB,villageBackB);
        villageMenuComponents.setPadding(new Insets(PADDING,PADDING,PADDING,PADDING));
        villageMenuScene = new Scene(villageMenuComponents);
        attackB.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                setUpAttackMenuScene();
            }
        });
        resourcesB.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                view.shoeResources(world.currentGame.getOwnResources(), world.currentGame.getOwnScore());
                setUpResourceInfo(world.currentGame.getOwnResources(), world.currentGame.getOwnScore());
            }
        });
        villageBackB.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                setUpSaveGameDialog();
                world.currentGame = null;
                setUpInitialMenuScene();
                // TODO: 6/7/2018
            }
        });
//        show buildings
        Label buildings = new Label(SHOW_BUILDINGS);
        Button showBuildingsBack = new Button(BACK);
        buildingList.setPadding(new Insets(PADDING,PADDING,PADDING,PADDING));
        showBuildingsBack.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                setUpVillageMenuScene();
            }
        });
        showBuildingsComponent = new VBox(SPACING ,buildingList,showBuildingsBack);
        showBuildingsComponent.setPadding(new Insets(PADDING,PADDING,PADDING,PADDING));
        showBuildingMenuScene = new Scene(showBuildingsComponent);
        showBuildingsB.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {

                for (Building b : world.currentGame.getOwnMap().getBuildings()) {
                    view.buildingShowerTypeID(b.getJasonType(), b.getId());
                }
                if (world.currentGame.getOwnDefensiveWeapon() != null) {
                    for (DefensiveWeapon d : world.currentGame.getOwnDefensiveWeapon()) {
                        view.buildingShowerTypeID(d.getARM_TYPE(), d.getId());
                    }
                }

                setUpshowBuildingMenuScene();
            }
        });

//        mine menu
        Button mineInfoB =  new Button(INFO);
        Button mineB = new Button(MINE);
        Button mineBackB = new Button(BACK);
        mineMenuComponents = new VBox(SPACING , mineInfoB , mineB ,mineBackB);
        mineMenuComponents.setPadding(new Insets(PADDING,PADDING,PADDING,PADDING));
        mineMenuScene  = new Scene(mineMenuComponents);
        mineBackB.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                currentBuilding = null;
                setUpshowBuildingMenuScene();
            }
        });
        mineInfoB.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                setUpMineInfoMenuScene();
            }
        });
        mineB.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (currentBuilding.getJasonType() == 1) {
                    GoldMine temp = (GoldMine) currentBuilding;
                    view.printMine(temp.getGoldProduce());
                    setUpMineInfo(temp.getGoldProduce());
                } else {
                    ElixirMine temp = (ElixirMine) currentBuilding;
                    view.printMine(temp.getElixirProduce());
                    setUpMineInfo(temp.getElixirProduce());
                }
                // TODO: 6/5/2018 ??? har 2 --> GOLD ??
            }
        });
//        mine info menu
        Button mineOverAllInfoB = new Button(OVERALL_INFO);
        Button mineUpgradeInfoB = new Button(UPGRADE_INFO);
        Button mineUpgradeB = new Button(UPGRADE);
        Button mineInfoBack = new Button(BACK);
        mineInfoComponents = new VBox(SPACING,mineOverAllInfoB,mineUpgradeInfoB,mineUpgradeB ,mineInfoBack);
        mineInfoComponents.setPadding(new Insets(PADDING,PADDING,PADDING,PADDING));
        mineInfoMenuScene = new Scene(mineInfoComponents);
        mineOverAllInfoB.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                //mine /storage /defensiveWeapon overall info
                if (currentBuilding != null) {
                    view.overAllInfoShower(currentBuilding.getLevel(), currentBuilding.getResistance());
                    setUpOverallInfo(currentBuilding.getLevel(), currentBuilding.getResistance());
                } else {
                    if (currentDefensiveWeapon != null) {
                        view.overAllInfoShower(currentDefensiveWeapon.getLevel(), currentDefensiveWeapon.getResistence());
                        setUpOverallInfo(currentDefensiveWeapon.getLevel(), currentDefensiveWeapon.getResistence());
                    } else System.err.println("BUG!!!!!!  no building is selected");
                }
            }
        });
        mineUpgradeInfoB.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                //                        upgradeInfo for mine storage defensive weapon
                if (currentBuilding != null) {
                    view.upgradeInfoShower(currentBuilding.getCostOfUpgrade());
                    setUpUpgradeInfo(currentBuilding.getCostOfUpgrade());
                } else if (currentDefensiveWeapon != null) {
                    view.upgradeInfoShower(currentDefensiveWeapon.getCOST_OF_UPGRADE());
                    setUpUpgradeInfo(currentDefensiveWeapon.getCOST_OF_UPGRADE());
                } else {
                    System.err.println("BUG!!!!!!");
                }
            }
        });
        mineUpgradeB.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
//                upgrade for mine building defensive weapon
                if (currentBuilding != null) {
                    view.wantUpgradeNameForCostGolds(currentBuilding.getJasonType(), currentBuilding.getCostOfUpgrade()[0]);
                    setUpUpgradeConfirmation(currentBuilding.getJasonType(), currentBuilding.getCostOfUpgrade()[0]);
                } else if (currentDefensiveWeapon != null) {
                    view.wantUpgradeNameForCostGolds(currentDefensiveWeapon.getARM_TYPE(), currentDefensiveWeapon.getCOST_OF_UPGRADE()[0]);
                    setUpUpgradeConfirmation(currentDefensiveWeapon.getARM_TYPE(), currentDefensiveWeapon.getCOST_OF_UPGRADE()[0]);
                } else {
                    System.err.println("BUG!!!!!!");
                }
            }
        });
        mineInfoBack.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                setUpMineMenuScene();
            }
        });
//        storage menu
        Button storageInfoB = new Button(INFO);
        Button storageBackB = new Button(BACK);
        storageMenuComponents = new VBox(SPACING,storageInfoB ,storageBackB);
        storageMenuComponents.setPadding(new Insets(PADDING,PADDING,PADDING,PADDING));
        storageMenuScene = new Scene(storageMenuComponents);
        storageInfoB.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                setUpStorageInfoMenuScene();
            }
        });
        storageBackB.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                currentBuilding = null;
                setUpshowBuildingMenuScene();
            }
        });
//        storage info menu
        Button storageOverallInfoB = new Button(OVERALL_INFO);
        Button sourceInfoB = new Button(SOURCES_INFO);
        Button storageUpgradeInfoB = new Button(UPGRADE_INFO);
        Button storageUpgradeB = new Button(UPGRADE);
        Button storageInfoBackB = new Button(BACK);
        storageInfoComponents = new VBox(SPACING,storageOverallInfoB,sourceInfoB,storageUpgradeInfoB,storageUpgradeB,storageInfoBackB);
        storageInfoComponents.setPadding(new Insets(PADDING,PADDING,PADDING,PADDING));
        storageInfoMenuScene = new Scene(storageInfoComponents);
        storageOverallInfoB.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                //mine /storage /defensiveWeapon overall info
                if (currentBuilding != null) {
                    view.overAllInfoShower(currentBuilding.getLevel(), currentBuilding.getResistance());
                    setUpOverallInfo(currentBuilding.getLevel(), currentBuilding.getResistance());
                } else {
                    if (currentDefensiveWeapon != null) {
                        view.overAllInfoShower(currentDefensiveWeapon.getLevel(), currentDefensiveWeapon.getResistence());
                        setUpOverallInfo(currentDefensiveWeapon.getLevel(), currentDefensiveWeapon.getResistence());
                    } else System.err.println("BUG!!!!!!  no building is selected");
                }
            }
        });
        sourceInfoB.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                int r = currentBuilding.getJasonType();
                if (r == 3) {
//                            gold
                    view.yourSourceStorageGoldIsSourceCapacity(world.currentGame.getGoldAndElixirStorageAndCapacity());
                } else {
                    view.yourSourceStorageforElixirIsSourceCapacity(world.currentGame.getGoldAndElixirStorageAndCapacity());
                }
                // TODO: 6/6/2018
            }
        });
        storageUpgradeInfoB.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                //                        upgradeInfo for mine storage defensive weapon
                if (currentBuilding != null) {
                    view.upgradeInfoShower(currentBuilding.getCostOfUpgrade());
                    setUpUpgradeInfo(currentBuilding.getCostOfUpgrade());
                } else if (currentDefensiveWeapon != null) {
                    view.upgradeInfoShower(currentDefensiveWeapon.getCOST_OF_UPGRADE());
                    setUpUpgradeInfo(currentDefensiveWeapon.getCOST_OF_UPGRADE());
                } else {
                    System.err.println("BUG!!!!!!");
                }
            }
        });
        storageInfoBackB.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                setUpStorageMenuScene();
            }
        });
        storageUpgradeB.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                //                upgrade for mine building defensive weapon
                if (currentBuilding != null) {
                    view.wantUpgradeNameForCostGolds(currentBuilding.getJasonType(), currentBuilding.getCostOfUpgrade()[0]);
                    setUpUpgradeConfirmation(currentBuilding.getJasonType(), currentBuilding.getCostOfUpgrade()[0]);
                } else if (currentDefensiveWeapon != null) {
                    view.wantUpgradeNameForCostGolds(currentDefensiveWeapon.getARM_TYPE(), currentDefensiveWeapon.getCOST_OF_UPGRADE()[0]);
                    setUpUpgradeConfirmation(currentDefensiveWeapon.getARM_TYPE(), currentDefensiveWeapon.getCOST_OF_UPGRADE()[0]);
                } else {
                    System.err.println("BUG!!!!!!");
                }
            }
        });
//        defensive weapon menu
        Button defensiveInfoB = new Button(INFO);
        Button targetB = new Button(TARGET);
        Button defensiveBackB = new Button(BACK);
        defensiveComponents = new VBox(SPACING,defensiveInfoB,targetB,defensiveBackB);
        defensiveComponents.setPadding(new Insets(PADDING,PADDING,PADDING,PADDING));
        defensiveWeaponMenuScene = new Scene(defensiveComponents);
        defensiveInfoB.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                setUpDefensiveWeaponInfoMenuScene();
            }
        });
        targetB.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                view.showTarget(currentDefensiveWeapon.getTarget());
                setUpTargetInfo(currentDefensiveWeapon.getTarget());
            }
        });
        defensiveBackB.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                currentDefensiveWeapon = null;
                setUpshowBuildingMenuScene();
            }
        });
//        defensive weapon info menu
        Button defensiveOverallInfoB = new Button(OVERALL_INFO);
        Button attackInfoB = new Button(ATTACK_INFO);
        Button defensiveUpgradeInfoB = new Button(UPGRADE_INFO);
        Button defensiveUpgradeB = new Button(UPGRADE);
        Button defensiveInfoBackB = new Button(BACK);
        defensiveInfoComponents = new VBox(SPACING,defensiveOverallInfoB,attackInfoB,defensiveUpgradeInfoB,defensiveUpgradeB,defensiveInfoBackB);
        defensiveInfoComponents.setPadding(new Insets(PADDING,PADDING,PADDING,PADDING));
        defensiveWeaponInfoMenuScene = new Scene(defensiveInfoComponents);
        defensiveOverallInfoB.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                //mine /storage /defensiveWeapon overall info
                if (currentBuilding != null) {
                    view.overAllInfoShower(currentBuilding.getLevel(), currentBuilding.getResistance());
                    setUpOverallInfo(currentBuilding.getLevel(), currentBuilding.getResistance());
                } else {
                    if (currentDefensiveWeapon != null) {
                        view.overAllInfoShower(currentDefensiveWeapon.getLevel(), currentDefensiveWeapon.getResistence());
                        setUpOverallInfo(currentDefensiveWeapon.getLevel(), currentDefensiveWeapon.getResistence());
                    } else System.err.println("BUG!!!!!!  no building is selected");
                }
            }
        });
        attackInfoB.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                view.attackInfo(currentDefensiveWeapon.getTarget(), currentDefensiveWeapon.getHitPower(), currentDefensiveWeapon.getRADIUS_OF_ATTACK());
            }
        });
        defensiveUpgradeInfoB.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                //                        upgradeInfo for mine storage defensive weapon
                if (currentBuilding != null) {
                    view.upgradeInfoShower(currentBuilding.getCostOfUpgrade());
                    setUpUpgradeInfo(currentBuilding.getCostOfUpgrade());
                } else if (currentDefensiveWeapon != null) {
                    view.upgradeInfoShower(currentDefensiveWeapon.getCOST_OF_UPGRADE());
                    setUpUpgradeInfo(currentDefensiveWeapon.getCOST_OF_UPGRADE());
                } else {
                    System.err.println("BUG!!!!!!");
                }
            }
        });
        defensiveUpgradeB.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                //                upgrade for mine building defensive weapon
                if (currentBuilding != null) {
                    view.wantUpgradeNameForCostGolds(currentBuilding.getJasonType(), currentBuilding.getCostOfUpgrade()[0]);
                    setUpUpgradeConfirmation(currentBuilding.getJasonType(), currentBuilding.getCostOfUpgrade()[0]);
                } else if (currentDefensiveWeapon != null) {
                    view.wantUpgradeNameForCostGolds(currentDefensiveWeapon.getARM_TYPE(), currentDefensiveWeapon.getCOST_OF_UPGRADE()[0]);
                    setUpUpgradeConfirmation(currentDefensiveWeapon.getARM_TYPE(), currentDefensiveWeapon.getCOST_OF_UPGRADE()[0]);
                } else {
                    System.err.println("BUG!!!!!!");
                }
            }
        });
        defensiveInfoBackB.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                setUpDefensiveWeaponMenuScene();
            }
        });
//        barracks menu
        Button barracksInfoB = new Button(INFO);
        Button buildingSoldierB = new Button(BUILDING_SOLDIERS);
        Button barracksStatus = new Button(STATUS);
        Button barracksBackB = new Button(BACK);
        barracksComponents = new VBox(SPACING,barracksInfoB,buildingSoldierB,barracksStatus,barracksBackB);
        barracksComponents.setPadding(new Insets(PADDING,PADDING,PADDING,PADDING));
        barracksMenuScene = new Scene(barracksComponents);
        barracksInfoB.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                setUpBarracksInfoMenuScene();
            }
        });
        buildingSoldierB.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                view.buildingSoldierBarrack(world.currentGame.getPotentialSoldiers(currentBarrack));
                setUpAvailableSoldiersMenuScene();
            }
        });
        barracksStatus.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                view.barrackStatusShower(world.currentGame.getQueueOfSoldiers(currentBarrack));
                setUpBarracksStatusInfo(world.currentGame.getQueueOfSoldiers(currentBarrack));
            }
        });
        barracksBackB.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                setUpshowBuildingMenuScene();
                currentBarrack = null;
            }
        });
//        barracks info menu
        Button barracksOverallInfoB = new Button(OVERALL_INFO);
        Button barracksUpgradeInfoB = new Button(UPGRADE_INFO);
        Button barracksUpgradeB = new Button(UPGRADE);
        Button barracksInfoBackB = new Button(BACK);
        barracksInfoComponents = new VBox(SPACING,barracksOverallInfoB,barracksUpgradeInfoB,barracksUpgradeB,barracksInfoBackB);
        barracksInfoComponents.setPadding(new Insets(PADDING,PADDING,PADDING,PADDING));
        barracksInfoMenuScene = new Scene(barracksInfoComponents);
        barracksOverallInfoB.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                //                        overall info of barrack
                if (currentBarrack != null) {
                    view.overAllInfoShower(currentBarrack.getLevel(), currentBarrack.getResistance());
                    setUpOverallInfo(currentBarrack.getLevel(), currentBarrack.getResistance());
                } else System.err.println("no barrack is set : BUG");
            }
        });
        barracksUpgradeInfoB.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                //                        upgrade info
                if (currentBarrack != null) {
                    view.upgradeInfoShower(currentBarrack.getCostOfUpgrade());
                    setUpUpgradeInfo(currentBarrack.getCostOfUpgrade());
                }
            }
        });
        barracksUpgradeB.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
//                upgrade barrack
                if (currentBarrack != null) {
                    currentBuilding = currentBarrack;
                    view.wantUpgradeNameForCostGolds(currentBarrack.getJasonType(), currentBarrack.getCostOfUpgrade()[0]);
                    setUpUpgradeConfirmation(currentBarrack.getJasonType(), currentBarrack.getCostOfUpgrade()[0]);
                } else {
                    System.err.println("BUG!!!!!!");
                }
            }
        });
        barracksInfoBackB.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                setUpBarracksMenuScene();
            }
        });
//        camp menu
        Button campInfoB = new Button(INFO);
        Button campSoldiersB = new Button(SOLDIERS);
        Button campBackB = new Button(BACK);
        campComponents = new VBox(SPACING,campInfoB,campSoldiersB,campBackB);
        campComponents.setPadding(new Insets(PADDING,PADDING,PADDING,PADDING));
        campMenuScene = new Scene(campComponents);
        campInfoB.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                setUpCampInfoMenuScene();
            }
        });
        campSoldiersB.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                view.showCampSoldiers(world.currentGame.getSoldiersOfCamps());
                setUpCampSoldiersInfo(world.currentGame.getSoldiersOfCamps());
            }
        });
        campBackB.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                currentCamp = null;
                setUpshowBuildingMenuScene();
            }
        });
//        camp info menu
        Button campOverallInfoB = new Button(OVERALL_INFO);
        Button campCapacityB = new Button(CAPACITY_INFO);
        Button campUpgradeInfoB = new Button(UPGRADE_INFO);
        Button campUpgradeB = new Button(UPGRADE);
        Button campInfoBackB = new Button(BACK);
        campInfoComponents = new VBox(SPACING,campOverallInfoB,campCapacityB,campUpgradeInfoB,campUpgradeB,campInfoBackB);
        campInfoComponents.setPadding(new Insets(PADDING,PADDING,PADDING,PADDING));
        campInfoMenuScene = new Scene(campInfoComponents);
        campOverallInfoB.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                view.overAllInfoShower(currentCamp.getLevel(), currentCamp.getResistance());
                setUpOverallInfo(currentCamp.getLevel(), currentCamp.getResistance());
            }
        });
        campCapacityB.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                int[] r = world.currentGame.getSoldiersAndCapacityOfCamps();
                view.printCampsCapacity(r[0], r[1]);
                setUpCampCapacityInfo(r[0], r[1]);
            }
        });
        campUpgradeInfoB.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (currentCamp != null) {
                    view.upgradeInfoShower(currentCamp.getCostOfUpgrade());
                    setUpUpgradeInfo(currentCamp.getCostOfUpgrade());
                } else
                    System.err.println("Bug");
            }
        });
        campUpgradeB.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                // TODO: 6/6/2018  
            }
        });
        campInfoBackB.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                setUpCampMenuScene();
            }
        });
//        town hall menu
        Button townHallInfo = new Button(INFO);
        Button availableBuilding = new Button(AVAILABLE_BUILDINGS);
        Button townHallStatus = new Button(STATUS);
        Button townHallBackB = new Button(BACK);
        townHallComponents = new VBox(SPACING,townHallInfo,availableBuilding,townHallStatus,townHallBackB);
        townHallComponents.setPadding(new Insets(PADDING,PADDING,PADDING,PADDING));
        townHallMenuScene = new Scene(townHallComponents);
        townHallInfo.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                setUpTownHallInfoMenuScene();
            }
        });
        availableBuilding.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                view.setUpAvailableBuildingsMenue32();
                setUpAvailableBuildingMenuScene();
            }
        });
        townHallStatus.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                view.townHallStatusShower(world.currentGame.getQueueOfBuildingsAndDefensiveWeaponsToBEBuilt());
                setUpTownHallStatusInfo(world.currentGame.getQueueOfBuildingsAndDefensiveWeaponsToBEBuilt());
            }
        });
        townHallBackB.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                setUpshowBuildingMenuScene();
            }
        });
//        townHall info menu
        Button townHallOverallInfoB = new Button(OVERALL_INFO);
        Button townHallUpgradeInfoB = new Button(UPGRADE_INFO);
        Button townHallUpgradeB = new Button(UPGRADE);
        Button townHallInfoBackB = new Button(BACK);
        townHallInfoComponents = new VBox(SPACING,townHallOverallInfoB,townHallUpgradeInfoB,townHallUpgradeB,townHallInfoBackB);
        townHallInfoComponents.setPadding(new Insets(PADDING,PADDING,PADDING,PADDING));
        townHallInfoMenuScene = new Scene(townHallInfoComponents);
        townHallOverallInfoB.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                view.overAllInfoShower(world.currentGame.getOwnTownHall().getLevel(), world.currentGame.getOwnTownHall().getResistance());
                setUpOverallInfo(world.currentGame.getOwnTownHall().getLevel(), world.currentGame.getOwnTownHall().getResistance());
            }
        });
        townHallUpgradeInfoB.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                view.upgradeInfoShower(world.currentGame.getOwnTownHall().getCostOfUpgrade());
                setUpUpgradeInfo(world.currentGame.getOwnTownHall().getCostOfUpgrade());
            }
        });
        townHallUpgradeB.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                int r = world.currentGame.upgradeTownHall();
                if (r == -1) {
                    setUpDontHaveEnoughResourceErr();
                }else SetUpupgradedSuccessfullyInfo(world.currentGame.getOwnResources(), world.currentGame.getOwnScore());
            }
        });
        townHallInfoBackB.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                setUpTownHallMenuScene();
            }
        });
//        available buildings
        Button availableBuildingsBackB = new Button(BACK);
        availableBuildingsBackB.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                setUpTownHallMenuScene();
            }
        });
        availableBuildingList.setPadding(new Insets(PADDING,PADDING,PADDING,PADDING));
        availableBuildingComponents = new VBox(SPACING,availableBuildingList,availableBuildingsBackB);
        availableBuildingComponents.setPadding(new Insets(PADDING,PADDING,PADDING,PADDING));
        availableBuildingMenuScene = new Scene(availableBuildingComponents);
//        attack menu
        attackMapsList.setPadding(new Insets(PADDING,PADDING,PADDING,PADDING));
        Button loadMapB = new Button(LOAD_MAP);
        Label availableMaps = new Label(AVAILABLE_MAPS);
        Button attackBackB = new Button(BACK);
        attackMenuComponents = new VBox(SPACING,loadMapB,availableMaps,attackMapsList,attackBackB);
        attackMenuComponents.setPadding(new Insets(PADDING,PADDING,PADDING,PADDING));
        attackMenuScene = new Scene(attackMenuComponents);
        loadMapB.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                setUpLoadEnemyMapDialog();
            }
        });
        attackBackB.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                setUpVillageMenuScene();
            }
        });
//    target map menu
        Button targetMapInfo = new Button(MAP_INFO);
        Button attackMap = new Button(ATTACK_MAP);
        Button targetMapBackB = new Button(BACK);
        targetMapMenuComponents = new VBox(SPACING,targetMapInfo,attackMap,targetMapBackB);
        targetMapMenuComponents.setPadding(new Insets(PADDING,PADDING,PADDING,PADDING));
        targetMapMenuScene = new Scene(targetMapMenuComponents);
        targetMapInfo.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                int gold = world.currentEnemy.getResources().get("gold");
                int elixir = world.currentEnemy.getResources().get("elixir");
                view.showEnemyMapInfo(gold, elixir, world.currentEnemy.getBuildings());
                setUpEnemyMapInfo(gold, elixir, world.currentEnemy.getBuildings());
            }
        });
        attackMap.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                setUpSelectSoldiersScene();
            }
        });
        targetMapBackB.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                // TODO: 6/7/2018 ?????? 
                world.currentEnemy = null; 
                setUpAttackMenuScene();
            }
        });
//        available soldiers menu
        Label availableSoldiersL = new Label(AVAILABLE_SOLDIERS);
        availableSoldiersList.setPadding(new Insets(PADDING,PADDING,PADDING,PADDING));
        Button availableSoldiersBackB = new Button(BACK);
        availableSoldiersMenuComponents = new VBox(SPACING,availableSoldiersL,availableSoldiersList,availableSoldiersBackB);
        availableSoldiersMenuComponents.setPadding(new Insets(PADDING,PADDING,PADDING,PADDING));
        availableSoldiersMenuScene = new Scene(availableSoldiersMenuComponents);
        availableSoldiersBackB.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                setUpBarracksMenuScene();
            }
        });
//        construction on own map
        Label chooseL = new Label(CHOOSE_X_Y);
        Button constructionBackB = new Button(BACK);
        constructionOnOwnMapComponents = new VBox(SPACING,chooseL,ownMap,constructionBackB);
        constructionOnOwnMapComponents.setPadding(new Insets(PADDING,PADDING,PADDING,PADDING));
        constructionOnOwnMapScene = new Scene(constructionOnOwnMapComponents);
        constructionBackB.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                currentBuildingTypeToBeBuilt = 0;
                setUpAvailableBuildingMenuScene();
            }
        });
//        select soldiers menu
        Label selectSoldiersL = new Label(SELECT_SOLDIERS);
        Button guardianB = new Button(convertSoldierTypeToString(1));
        Button giantB = new Button(convertSoldierTypeToString(2));
        Button dragonB = new Button(convertSoldierTypeToString(3));
        Button archerB = new Button(convertSoldierTypeToString(4));
        Button endSelectionB = new Button(END_SELECTION);
        Button selectSoldiersBackB = new Button(BACK);
        HBox buttonHbox = new HBox(SPACING,endSelectionB,selectSoldiersBackB);
        buttonHbox.setPadding(new Insets(PADDING,PADDING,PADDING,PADDING));
        VBox soldiers = new VBox(SPACING,selectSoldiersL,guardianB,giantB,dragonB,archerB);
        soldiers.setPadding(new Insets(PADDING,PADDING,PADDING,PADDING));
        selectSoldiersComponents = new VBox(SPACING,soldiers,buttonHbox);
        selectSoldiersComponents.setPadding(new Insets(PADDING,PADDING,PADDING,PADDING));
        selectSoldiersScene = new Scene(selectSoldiersComponents);
        selectSoldiersBackB.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                setUpTargetMapMenuScene();
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
        setUpInitialMenuScene();
        stage.show();
        setUpWelcomeInfo();
//        key listeners
        initialComponents.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode()== KeyCode.S){
                    setUpSaveGameDialog();
                }else if (event.getCode()==KeyCode.SPACE){
                    System.out.println("turned");
                }else if (event.getCode()==KeyCode.T){
                    setUpTurnTimeDialog();
                }
            }
        });//initial
        villageMenuComponents.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
//                if (event.getCode()== KeyCode.S){
//                    setUpSaveGameDialog();
//                }else if (event.getCode()==KeyCode.SPACE){
//                    System.out.println("turned");
//                }else if (event.getCode()==KeyCode.T){
//                    setUpTurnTimeDialog();
//                }
                // TODO: 6/7/2018 ???
            }
        });//village
        showBuildingsComponent.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode()== KeyCode.S){
                    setUpSaveGameDialog();
                }else if (event.getCode()==KeyCode.SPACE){
                    System.out.println("turned");
                }else if (event.getCode()==KeyCode.T){
                    setUpTurnTimeDialog();
                }
            }
        });//show building
        mineMenuComponents.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode()== KeyCode.S){
                    setUpSaveGameDialog();
                }else if (event.getCode()==KeyCode.SPACE){
                    System.out.println("turned");
                }else if (event.getCode()==KeyCode.T){
                    setUpTurnTimeDialog();
                }
            }
        });//min menu
        storageMenuComponents.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode()== KeyCode.S){
                    setUpSaveGameDialog();
                }else if (event.getCode()==KeyCode.SPACE){
                    System.out.println("turned");
                }else if (event.getCode()==KeyCode.T){
                    setUpTurnTimeDialog();
                }
            }
        });//storage menu
        storageInfoComponents.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode()== KeyCode.S){
                    setUpSaveGameDialog();
                }else if (event.getCode()==KeyCode.SPACE){
                    System.out.println("turned");
                }else if (event.getCode()==KeyCode.T){
                    setUpTurnTimeDialog();
                }
            }
        });//storage info
        mineInfoComponents.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode()== KeyCode.S){
                    setUpSaveGameDialog();
                }else if (event.getCode()==KeyCode.SPACE){
                    System.out.println("turned");
                }else if (event.getCode()==KeyCode.T){
                    setUpTurnTimeDialog();
                }
            }
        });//mine info
        defensiveComponents.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode()== KeyCode.S){
                    setUpSaveGameDialog();
                }else if (event.getCode()==KeyCode.SPACE){
                    System.out.println("turned");
                }else if (event.getCode()==KeyCode.T){
                    setUpTurnTimeDialog();
                }
            }
        });//defensive
        defensiveInfoComponents.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode()== KeyCode.S){
                    setUpSaveGameDialog();
                }else if (event.getCode()==KeyCode.SPACE){
                    System.out.println("turned");
                }else if (event.getCode()==KeyCode.T){
                    setUpTurnTimeDialog();
                }
            }
        });//defensive info
        townHallComponents.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode()== KeyCode.S){
                    setUpSaveGameDialog();
                }else if (event.getCode()==KeyCode.SPACE){
                    System.out.println("turned");
                }else if (event.getCode()==KeyCode.T){
                    setUpTurnTimeDialog();
                }
            }
        });//town hall
        townHallInfoComponents.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode()== KeyCode.S){
                    setUpSaveGameDialog();
                }else if (event.getCode()==KeyCode.SPACE){
                    System.out.println("turned");
                }else if (event.getCode()==KeyCode.T){
                    setUpTurnTimeDialog();
                }
            }
        });//town hall info
        availableBuildingComponents.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode()== KeyCode.S){
                    setUpSaveGameDialog();
                }else if (event.getCode()==KeyCode.SPACE){
                    System.out.println("turned");
                }else if (event.getCode()==KeyCode.T){
                    setUpTurnTimeDialog();
                }
            }
        });//available buildings
        barracksComponents.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode()== KeyCode.S){
                    setUpSaveGameDialog();
                }else if (event.getCode()==KeyCode.SPACE){
                    System.out.println("turned");
                }else if (event.getCode()==KeyCode.T){
                    setUpTurnTimeDialog();
                }
            }
        });//barracks
        barracksInfoComponents.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode()== KeyCode.S){
                    setUpSaveGameDialog();
                }else if (event.getCode()==KeyCode.SPACE){
                    System.out.println("turned");
                }else if (event.getCode()==KeyCode.T){
                    setUpTurnTimeDialog();
                }
            }
        });//barracks info
        campComponents.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode()== KeyCode.S){
                    setUpSaveGameDialog();
                }else if (event.getCode()==KeyCode.SPACE){
                    System.out.println("turned");
                }else if (event.getCode()==KeyCode.T){
                    setUpTurnTimeDialog();
                }
            }
        });//camp
        campInfoComponents.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode()== KeyCode.S){
                    setUpSaveGameDialog();
                }else if (event.getCode()==KeyCode.SPACE){
                    System.out.println("turned");
                }else if (event.getCode()==KeyCode.T){
                    setUpTurnTimeDialog();
                }
            }
        });//camp info
        attackMenuComponents.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode()== KeyCode.S){
                    setUpSaveGameDialog();
                }else if (event.getCode()==KeyCode.SPACE){
                    System.out.println("turned");
                }else if (event.getCode()==KeyCode.T){
                    setUpTurnTimeDialog();
                }
            }
        });//attack menu
        targetMapMenuComponents.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode()== KeyCode.S){
                    setUpSaveGameDialog();
                }else if (event.getCode()==KeyCode.SPACE){
                    System.out.println("turned");
                }else if (event.getCode()==KeyCode.T){
                    setUpTurnTimeDialog();
                }
            }
        });//target map menu
        availableSoldiersMenuComponents.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode()== KeyCode.S){
                    setUpSaveGameDialog();
                }else if (event.getCode()==KeyCode.SPACE){
                    System.out.println("turned");
                }else if (event.getCode()==KeyCode.T){
                    setUpTurnTimeDialog();
                }
            }
        });//available soldiers
        constructionOnOwnMapComponents.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode()== KeyCode.S){
                    setUpSaveGameDialog();
                }else if (event.getCode()==KeyCode.SPACE){
                    System.out.println("turned");
                }else if (event.getCode()==KeyCode.T){
                    setUpTurnTimeDialog();
                }
            }
        });//own map


    }//end of start

    public void updateBuildingList(){
        buildingItems.clear();
        for (Building b : world.currentGame.getOwnMap().getBuildings()) {
            Button button = new Button(convertTypeToBuilding(b.getJasonType()) + " " +b.getId() );
            if (b.getId()==0){
                button.setText(convertTypeToBuilding(b.getJasonType()));
            }
            buildingItems.add(button);
            button.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {

                    switch (b.getJasonType()){
                        case 1:{
                            currentBuilding = world.currentGame.findBuildingTypeInOwnMap(b.getJasonType() ,b.getId());
                            setUpMineMenuScene();
                        }break;
                        case 2:{
                            currentBuilding = world.currentGame.findBuildingTypeInOwnMap(b.getJasonType() ,b.getId());
                            setUpMineMenuScene();
                        }break;
                        case 3:{
                            currentBuilding = world.currentGame.findBuildingTypeInOwnMap(b.getJasonType() ,b.getId());
                            setUpStorageMenuScene();
                        }break;
                        case 4:{
                            currentBuilding = world.currentGame.findBuildingTypeInOwnMap(b.getJasonType() ,b.getId());
                            setUpStorageMenuScene();
                        }break;
                        case 5:{
                            setUpTownHallMenuScene();
                        }break;
                        case 6:{
                            currentBarrack = (Barracks) world.currentGame.findBuildingTypeInOwnMap(b.getJasonType() ,b.getId());
                            setUpBarracksMenuScene();
                        }break;
                        case 7:{
                            currentCamp = (Camp) world.currentGame.findBuildingTypeInOwnMap(b.getJasonType() ,b.getId());
                            setUpCampMenuScene();
                        }break;
                    }
                }
            });
        }
        if (world.currentGame.getOwnDefensiveWeapon() != null) {
            for (DefensiveWeapon d : world.currentGame.getOwnDefensiveWeapon()) {
                Button button = new Button(convertTypeToBuilding(d.getARM_TYPE() ) + " " + d.getId());
                buildingItems.add(button);
                button.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        currentDefensiveWeapon = world.currentGame.findDefensiveWeaponTypeInOwnMap(d.getARM_TYPE() , d.getId());
                        setUpDefensiveWeaponMenuScene();
                    }
                });
            }
        }
        buildingList.setItems(buildingItems);
    }
    public void updateAvailableBuildingList(){
        availableBuildingItems.clear();
        ArrayList<Integer> type = world.currentGame.availableBuildingsAndDefensiveWeapons();
        if (type != null) {
            for (int i = 0; i < type.size(); i++) {
                int t = type.get(i);
                System.out.println(convertTypeToBuilding(type.get(i)));
                Button button = new Button(convertTypeToBuilding(type.get(i)));
                availableBuildingItems.add(button);
                button.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        int r = world.currentGame.constructionRequest(t);//check if it is true please!!!

                        if (r == -2){
                            setUpNotEnoughWorkerErr();
                        }
                        if (r==-1){
                            setUpDontHaveEnoughResourceErr();
                        }
                        if (r == 0) {
                            view.setUpConstructionBuildingMenue6(t, world.currentGame.getCostOfConstruction(t)[0]);
                            currentBuildingTypeToBeBuilt = t;
                            setUpConstructionBuildingConfirmation(t, world.currentGame.getCostOfConstruction(t)[0]);
                        }
                    }
                });
            }//end of for
        }//end of if
        availableBuildingList.setItems(availableBuildingItems);
    }
    public void updateAttackMapsList(){
        attackMapItems.clear();
        if (world.getEnemyMaps() != null) {
            for (int i = 0; i < world.getEnemyMaps().size(); i++) {
                System.out.println(i + 2 + "." + world.getEnemyMaps().get(i).getName());
                Button button = new Button(world.getEnemyMaps().get(i).getName());
                int index = i;
                attackMapItems.add(button);
                button.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        world.setEnemyMapToCurrentGame(index);
                        setUpTargetMapMenuScene();
                    }
                });
            }
        }
        attackMapsList.setItems(attackMapItems);
    }
    public void updateAvailableSoldiersList(){
        availableSoldiersItems.clear();
        ArrayList<int[]> potentialSoldiers=world.currentGame.getPotentialSoldiers(currentBarrack);
        for (int i = 0; i < potentialSoldiers.size(); i++) {
            Button button = new Button(convertSoldierTypeToString(potentialSoldiers.get(i)[0]));
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
            HBox hBox = new HBox(SOLDIERS_SPACING,button,label);
            availableSoldiersItems.add(hBox);
        }
        availableSoldiersList.setItems(availableSoldiersItems);
    }
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
        }
        return "invalid soldier type";
    }
    public VBox ownMap(Map map){
        System.out.println("here");
        for (int i = 0; i < 30; i++) {
            for (int j = 0; j < 30; j++) {
                if (map.getMap()[i][j].isEmpty() && map.getMap()[i][j].isConstructable()) {
                    System.out.print("0");
                } else
                    System.out.print("1");
            }
            System.out.println();
        }
        VBox mapVbox = new VBox();
        mapVbox.setSpacing(MAP_SPACING);
        mapVbox.setPadding(new Insets(PADDING,PADDING,PADDING,PADDING));
        for (int i = 0; i < 30; i++) {
            HBox hBox = new HBox();
            hBox.setSpacing(MAP_SPACING);
            for (int j = 0; j < 30; j++) {
                int x = j ;
                int y = i;
                Rectangle cell = new Rectangle(CELL_LENGTH,CELL_LENGTH);
                if (map.getMap()[i][j].isEmpty() && map.getMap()[i][j].isConstructable()) {
                    cell.setFill(Color.HOTPINK);
                    cell.setOnMouseClicked(new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent event) {
                            int r = world.currentGame.newBuildingMaker(currentBuildingTypeToBeBuilt, x, y);
                            if (r==-1){
                                view.youCantBuildInThisPosition();
                                // TODO: 6/7/2018
                            }else {
                                currentBuildingTypeToBeBuilt = 0;
                                setUpAvailableBuildingMenuScene();
                            }
                        }
                    });
                }else {
                    cell.setOnMouseClicked(new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent event) {
                            setUpCantChooseErr();
                        }
                    });
                }
                hBox.getChildren().add(cell);
            }
            mapVbox.getChildren().add(hBox);
        }
        return mapVbox;
    }

}//end of class
