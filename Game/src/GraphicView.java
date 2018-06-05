import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextInputDialog;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


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
    Stage stage;
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

    public void setUpInitialMenuScene() {
        stage.setScene(initialMenuScene);
        stage.setTitle(INITIAL_MENU);
    }

    public void setUpVillageMenuScene() {
        stage.setScene( villageMenuScene );
        stage.setTitle(VILLAGE_MENU);
    }
    public void setUpshowBuildingMenuScene(){
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
        stage.setScene(availableBuildingMenuScene);
        stage.setTitle(AVAILABLE_BUILDING_MENU);
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

    private static Button exitButton = new Button(EXIT);


    public static void main(String[] args) {
        launch(args);
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
                stage.setScene(villageMenuScene);
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
        attackB.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                // TODO: 6/5/2018
            }
        });
        VBox showBuildingsComponent = new VBox();
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
                for (Building b : world.currentGame.getOwnMap().getBuildings()) {
                    Button button = new Button(b.getJasonType() + " " +b.getId() );
                    showBuildingsComponent.getChildren().add(button);
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
                        Button button = new Button(d.getARM_TYPE() + " " + d.getId());
                        showBuildingsComponent.getChildren().add(button);
                        button.setOnMouseClicked(new EventHandler<MouseEvent>() {
                            @Override
                            public void handle(MouseEvent event) {
                                currentDefensiveWeapon = world.currentGame.findDefensiveWeaponTypeInOwnMap(d.getARM_TYPE() , d.getId());
                                setUpDefensiveWeaponMenuScene();
                            }
                        });
                    }
                }
                Button backB1 = new Button(BACK);
                showBuildingsComponent.getChildren().add(backB1);
                backB1.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        setUpVillageMenuScene();
                    }
                });
                showBuildingsComponent.setSpacing(SPACING);
                showBuildingsComponent.setPadding(new Insets(PADDING,PADDING,PADDING,PADDING));
                showBuildingMenuScene = new Scene(showBuildingsComponent);
                setUpshowBuildingMenuScene();
            }
        });
        setUpInitialMenuScene();
        stage.show();
    }//end of start
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
