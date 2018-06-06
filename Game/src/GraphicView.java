import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextInputDialog;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


import java.util.ArrayList;
import java.util.Optional;

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
    Scene villageMenuScene;
    Scene showBuildingMenuScene;
    Scene mineMenuScene;
    Scene storageMenuScene;
    Scene storageInfoMenuScene;
    Scene mineInfoMenuScene;
    Scene defensiveWeaponMenuScene;
    Scene defensiveWeaponInfoMenuScene;
    Scene upgradeMenuScene;
    Scene townHallMenuScene;
    Scene townHallInfoMenuScene;
    Scene availableBuildingMenuScene;
    Scene barracksMenuScene;
    Scene barracksInfoMenuScene;
    Scene campMenuScene;
    Scene campInfoMenuScene;
    Scene attackMenuScene;
    Scene targetMapMenuScene;
    ListView<Button> buildingList = new ListView<>();
    ObservableList<Button> buildingItems = FXCollections.observableArrayList ();

    ListView<Button> availableBuildingList = new ListView<>();
    ObservableList<Button> availableBuildingItems = FXCollections.observableArrayList ();

    ListView<Button> attackMapsList = new ListView<>();
    ObservableList<Button> attackMapItems = FXCollections.observableArrayList ();


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

    private static Button exitButton = new Button(EXIT);


    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
//    initial menu
        Button newGameButton = new Button(NEW_GAME);
        Button loadButton = new Button(LOAD_GAME);
        VBox initialComponents = new VBox(SPACING,newGameButton,loadButton,exitButton);
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
                String address = null;
                TextInputDialog dialog = new TextInputDialog(LOAD_GAME);
                dialog.setHeaderText(LOAD_GAME_COMMAND);
                dialog.setContentText(ADDRESS_EXAMPLE);
                Optional<String> result = dialog.showAndWait();
                if (result.isPresent()){
                   address = result.get();
                }
                if (address != null){
                    world = world.loadGame(address, GraphicView.this);
                    if (world.games.size() > 0) {
                        world.currentGame = world.games.get(0);
                    }
                }

            }
        });

//  village menu
        Button attackB = new Button(ATTACK);
        Button showBuildingsB = new Button(SHOW_BUILDINGS);
        Button resourcesB = new Button(RESOURCES);
        Button backB = new Button(BACK);
        VBox villageMenuComponents = new VBox(SPACING, showBuildingsB,resourcesB,attackB,backB);
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
            }
        });
        backB.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                setUpInitialMenuScene();
                // TODO: 6/7/2018
            }
        });
//        show buildings
        VBox showBuildingsComponent = new VBox();
        Button backB1 = new Button(BACK);
        buildingList.setPadding(new Insets(PADDING,PADDING,PADDING,PADDING));
        showBuildingsComponent.getChildren().addAll(buildingList,backB1);
        backB1.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                setUpVillageMenuScene();
            }
        });
        showBuildingsComponent.setSpacing(SPACING);
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
        Button backB2 = new Button(BACK);
        VBox mineMenuComponents = new VBox(SPACING , mineInfoB , mineB ,backB2);
        mineMenuComponents.setPadding(new Insets(PADDING,PADDING,PADDING,PADDING));
        mineMenuScene  = new Scene(mineMenuComponents);
        backB2.setOnMouseClicked(new EventHandler<MouseEvent>() {
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
                } else {
                    ElixirMine temp = (ElixirMine) currentBuilding;
                    view.printMine(temp.getElixirProduce());
                }
                // TODO: 6/5/2018
            }
        });
//        mine info menu
        Button overAllInfoB = new Button(OVERALL_INFO);
        Button upgradeInfoB = new Button(UPGRADE_INFO);
        Button upgradeB = new Button(UPGRADE);
        Button mineInfoBack = new Button(BACK);
        VBox mineInfoComponents = new VBox(SPACING,overAllInfoB,upgradeInfoB,upgradeB ,mineInfoBack);
        mineInfoComponents.setPadding(new Insets(PADDING,PADDING,PADDING,PADDING));
        mineInfoMenuScene = new Scene(mineInfoComponents);
        overAllInfoB.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                //mine /storage /defensiveWeapon overall info
                if (currentBuilding != null) {
                    view.overAllInfoShower(currentBuilding.getLevel(), currentBuilding.getResistance());
                } else {
                    if (currentDefensiveWeapon != null) {
                        view.overAllInfoShower(currentDefensiveWeapon.getLevel(), currentDefensiveWeapon.getResistence());
                    } else System.err.println("BUG!!!!!!  no building is selected");
                }
                // TODO: 6/5/2018
            }
        });
        upgradeInfoB.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                //                        upgradeInfo for mine storage defensive weapon
                if (currentBuilding != null) {
                    view.upgradeInfoShower(currentBuilding.getCostOfUpgrade());
                } else if (currentDefensiveWeapon != null) {
                    view.upgradeInfoShower(currentDefensiveWeapon.getCOST_OF_UPGRADE());
                } else {
                    System.err.println("BUG!!!!!!");
                }
                // TODO: 6/5/2018
            }
        });
        upgradeB.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
//                upgrade for mine building defensive weapon
                if (currentBuilding != null) {
                    view.wantUpgradeNameForCostGolds(currentBuilding.getJasonType(), currentBuilding.getCostOfUpgrade()[0]);
                } else if (currentDefensiveWeapon != null) {
                    view.wantUpgradeNameForCostGolds(currentDefensiveWeapon.getARM_TYPE(), currentDefensiveWeapon.getCOST_OF_UPGRADE()[0]);
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
        VBox storageMenuComponents = new VBox(SPACING,storageInfoB ,backB2);
        storageMenuComponents.setPadding(new Insets(PADDING,PADDING,PADDING,PADDING));
        storageMenuScene = new Scene(storageMenuComponents);
        storageInfoB.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                setUpStorageInfoMenuScene();
            }
        });
//        storage info menu
        Button sourceInfoB = new Button(SOURCES_INFO);
        Button storageInfoBackB = new Button(BACK);
        VBox storageInfoComponents = new VBox(SPACING,overAllInfoB,sourceInfoB,upgradeInfoB,upgradeB,storageInfoBackB);
        storageInfoComponents.setPadding(new Insets(PADDING,PADDING,PADDING,PADDING));
        storageInfoMenuScene = new Scene(storageInfoComponents);
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
        storageInfoBackB.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                setUpStorageMenuScene();
            }
        });
//        defensive weapon menu
        Button defensiveInfoB = new Button(INFO);
        Button targetB = new Button(TARGET);
        Button back3 = new Button(BACK);
        VBox defensiveComponents = new VBox(SPACING,defensiveInfoB,targetB,back3);
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
                // TODO: 6/6/2018
            }
        });
        back3.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                currentDefensiveWeapon = null;
                setUpshowBuildingMenuScene();
            }
        });
//        defensive weapon info menu
        Button attackInfoB = new Button(ATTACK_INFO);
        Button defensiveInfoBackB = new Button(BACK);
        VBox defensiveInfoComponents = new VBox(SPACING,overAllInfoB,attackInfoB,upgradeInfoB,upgradeB,defensiveInfoBackB);
        defensiveInfoComponents.setPadding(new Insets(PADDING,PADDING,PADDING,PADDING));
        defensiveWeaponInfoMenuScene = new Scene(defensiveInfoComponents);
        attackInfoB.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                view.attackInfo(currentDefensiveWeapon.getTarget(), currentDefensiveWeapon.getHitPower(), currentDefensiveWeapon.getRADIUS_OF_ATTACK());
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
        VBox barracksComponents = new VBox(SPACING,barracksInfoB,buildingSoldierB,barracksStatus,barracksBackB);
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
                // TODO: 6/6/2018   1002
            }
        });
        barracksStatus.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                view.barrackStatusShower(world.currentGame.getQueueOfSoldiers(currentBarrack));
                // TODO: 6/6/2018
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
        VBox barracksInfoComponents = new VBox(SPACING,barracksOverallInfoB,barracksUpgradeInfoB,barracksUpgradeB,barracksInfoBackB);
        barracksInfoComponents.setPadding(new Insets(PADDING,PADDING,PADDING,PADDING));
        barracksInfoMenuScene = new Scene(barracksInfoComponents);
        barracksOverallInfoB.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                //                        overall info of barrack
                if (currentBarrack != null) {
                    view.overAllInfoShower(currentBarrack.getLevel(), currentBarrack.getResistance());
                } else System.err.println("no barrack is set : BUG");
            }
            // TODO: 6/6/2018
        });
        barracksUpgradeInfoB.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                //                        upgrade info
                if (currentBarrack != null) {
                    view.upgradeInfoShower(currentBarrack.getCostOfUpgrade());
                }
                // TODO: 6/6/2018
            }
        });
        barracksUpgradeB.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
//                upgrade barrack
                if (currentBarrack != null) {
                    currentBuilding = currentBarrack;
                    view.wantUpgradeNameForCostGolds(currentBarrack.getJasonType(), currentBarrack.getCostOfUpgrade()[0]);
                } else {
                    System.err.println("BUG!!!!!!");
                }
                // TODO: 6/6/2018
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
        VBox campComponents = new VBox(SPACING,campInfoB,campSoldiersB,campBackB);
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
                // TODO: 6/6/2018
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
        VBox campInfoComponents = new VBox(SPACING,campOverallInfoB,campCapacityB,campUpgradeInfoB,campUpgradeB,campInfoBackB);
        campInfoComponents.setPadding(new Insets(PADDING,PADDING,PADDING,PADDING));
        campInfoMenuScene = new Scene(campInfoComponents);
        campOverallInfoB.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                view.overAllInfoShower(currentCamp.getLevel(), currentCamp.getResistance());
                // TODO: 6/6/2018  
            }
        });
        campCapacityB.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                int[] r = world.currentGame.getSoldiersAndCapacityOfCamps();
                view.printCampsCapacity(r[0], r[1]);
                // TODO: 6/6/2018  
            }
        });
        campUpgradeInfoB.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (currentCamp != null) {
                    view.upgradeInfoShower(currentCamp.getCostOfUpgrade());
                } else
                    System.err.println("Bug");
                // TODO: 6/6/2018  
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
        VBox townHallComponents = new VBox(SPACING,townHallInfo,availableBuilding,townHallStatus,townHallBackB);
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
//                ArrayList<Integer> r = world.currentGame.availableBuildingsAndDefensiveWeapons();
//                if (r != null) {
//                    view.buildingShowByType(r);
//                }
                // TODO: 6/6/2018
                setUpAvailableBuildingMenuScene();
            }
        });
        townHallStatus.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                view.townHallStatusShower(world.currentGame.getQueueOfBuildingsAndDefensiveWeaponsToBEBuilt());
                // TODO: 6/6/2018
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
        VBox townHallInfoComponents = new VBox(SPACING,townHallOverallInfoB,townHallUpgradeInfoB,townHallUpgradeB,townHallInfoBackB);
        townHallInfoComponents.setPadding(new Insets(PADDING,PADDING,PADDING,PADDING));
        townHallInfoMenuScene = new Scene(townHallInfoComponents);
        townHallOverallInfoB.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                view.overAllInfoShower(world.currentGame.getOwnTownHall().getLevel(), world.currentGame.getOwnTownHall().getResistance());
                // TODO: 6/6/2018
            }
        });
        townHallUpgradeInfoB.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                view.upgradeInfoShower(world.currentGame.getOwnTownHall().getCostOfUpgrade());
                // TODO: 6/6/2018
            }
        });
        townHallUpgradeB.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                int r = world.currentGame.upgradeTownHall();
                if (r == -1) {
                    view.dontHaveEnoughResource();
                }
                // TODO: 6/6/2018
            }
        });
        townHallInfoBackB.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                setUpTownHallMenuScene();
            }
        });
//        available buildings
        availableBuildingList.setPadding(new Insets(PADDING,PADDING,PADDING,PADDING));
        VBox availableBuildingComponents = new VBox(SPACING,availableBuildingList,townHallInfoBackB);
        availableBuildingComponents.setPadding(new Insets(PADDING,PADDING,PADDING,PADDING));
        availableBuildingMenuScene = new Scene(availableBuildingComponents);
//        attack menu
        attackMapsList.setPadding(new Insets(PADDING,PADDING,PADDING,PADDING));
        Button loadMapB = new Button(LOAD_MAP);
        Label availableMaps = new Label(AVAILABLE_MAPS);
        Button attackBackB = new Button(BACK);
        VBox attackMenuComponents = new VBox(SPACING,loadMapB,availableMaps,attackMapsList,attackBackB);
        attackMenuComponents.setPadding(new Insets(PADDING,PADDING,PADDING,PADDING));
        attackMenuScene = new Scene(attackMenuComponents);
        loadMapB.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                // TODO: 6/7/2018  
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
        VBox targetMapMenuComponents = new VBox(SPACING,targetMapInfo,attackMap,targetMapBackB);
        targetMapMenuComponents.setPadding(new Insets(PADDING,PADDING,PADDING,PADDING));
        targetMapMenuScene = new Scene(targetMapMenuComponents);
        targetMapInfo.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                int gold = world.currentEnemy.getResources().get("gold");
                int elixir = world.currentEnemy.getResources().get("elixir");
                view.showEnemyMapInfo(gold, elixir, world.currentEnemy.getBuildings());
                // TODO: 6/7/2018  
            }
        });
        attackMap.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                // TODO: 6/7/2018  
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


        setUpInitialMenuScene();
        stage.show();
    }//end of start

    public void updateBuildingList(){
        buildingItems.clear();
        for (Building b : world.currentGame.getOwnMap().getBuildings()) {
            Button button = new Button(convertTypeToBuilding(b.getJasonType()) + " " +b.getId() );
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

                        if (r == -2)
                            view.dontHaveWorker();
                        if (r == 0) {
                            view.setUpConstructionBuildingMenue6(t, world.currentGame.getCostOfConstruction(t)[0]);
                            currentBuildingTypeToBeBuilt = t;
                        }
                        // TODO: 6/7/2018
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
                        // TODO: 6/7/2018
                    }
                });
            }
        }
        attackMapsList.setItems(attackMapItems);
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

}//end of class
