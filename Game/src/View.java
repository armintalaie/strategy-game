import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

public class View {
    int sizeOfEnemiesMaps = 0;
    Menu currentMenuType = Menu.INITIAL_MENU;
    Scanner scanner = new Scanner(System.in);
    private String command = null;

    public String getCommand() {
        return command;
    }

    public void attackInfo(int[] type, int power, int radius) {
        System.out.print("Target : ");
        for (int i = 0; i < 4; i++) {
            if (type[i] != 0)
                System.out.print(convertSoldierTypeToString(type[i]) + " ");
        }
        System.out.println();
        System.out.println("Damage : " + power);
        System.out.println("Damage Range : " + radius);
    }

    public int updateView() {
        command = scanner.nextLine();
        if (Pattern.matches("end", command)) return -20;
        if (Pattern.matches("turn \\d+", command) && currentMenuType != Menu.StartAttack) return 1;
        if (Pattern.matches("save (.*)", command)) return 2;
        switch (currentMenuType) {
            case INITIAL_MENU:
                return 100 + initialMenuAnalyzer(command);
            case VILLAGE_MENU:
                return 200 + villageMenuAnalyzer(command);
            case MINE_MENU:
                return 300 + mineMenuAnalyzer(command);
            case MINE_INFO_MENU:
                return 400 + mineInfoMenuAnalyzer(command);
            case STORAGE_MENU:
                return 500 + storageMenuAnalyzer(command);
            case STORAGE_INFO_MENU:
                return storageInfoMenuAnalyzer(command);
            case DEFENSIVE_WEAPON_MENU:
                return 700 + defensiveWeaponMenuAnalyzer(command);
            case DEFENSIVE_WEAPON_INFO_MENU:
                return defensiveWeaponInfoMenuAnalyzer(command);
            case UPGRADE_MENU:
                return 900 + upgradeMenuAnalyzer(command);
            case BARRACK_MENU:
                return 1000 + barrackMenuAnalyzer(command);
            case BARRACK_INFO_MENU:
                return 1100 + barrackInfoMenuAnalyzer(command);
            case CAMP_MENU:
                return 1200 + campMenuAnalyzer(command);
            case CAMP_INFO_MENU:
                return 1300 + campInfoMenuAnalyzer(command);
            case ATTACK_MENU:
                return 1400 + attackMenuAnalyzer(command);
            case MAP_MENU:
                return 1500 + mapMenuAnalyzer(command);
            case LOAD_MAP_WINDOW:
                return 1601;
            case ATTACK_MAP_WINDOW:
                return 1700 + attackMapWindowAnalyzer(command);
            case PUT_SOLDIERS_ON_MAP_WINDOW:
                return 1800 + putSoldiersOnMapWindowAnalyzer(command);
            case REAL_GAME_SPACE:
                return 1900 + realGameSpaceAnalyzer(command);
            case TOWN_HALL_MENU:
                return 2000 + townHallMenuAnalyzer(command);
            case TOWN_HALL_INFO_MENU:
                return 2100 + townHallInfoMenuAnalyzer(command);
            case AVAILABLE_BUILDING_MENU:
                return 2200 + availableBuildingMenuAnalyzer(command);
            case BUILDING_SOLDIERS_MENU:
                return 2300 + buildingSoldiersMenuAnalyzer(command);
            case BUILDING_CONSTRUCTION_WINDOW:
                return 2400 + buildingConstructionWindowAnalyzer(command);
            case CONSTRUCTION_SOLDIER_WINDOW:
                return 2500 + constructionSoldierWindowAnalyzer(command);
            case GET_XY_FOR_BUILD_BUILDING:
                return 2600 + getXYanalyzer();
            case WAITING_TO_RECIEVE_TYPE:
                return 2700 + waitingToRecieveTypeAnalyzer(command);
            case WATING_ADDRESS:
                return 2800 + watingAddresssAnalyzer(command);
            case StartAttack:
                return 3000 + realGameSpaceAnalyzer(command);
        }
        return 0;
    }

    public int watingAddresssAnalyzer(String command) {
        if (Pattern.matches("load (.*)", command)) return 1;
        if (Pattern.matches("Back", command)) {
            setUpInitialMenu();
            return 3;
        }
        invalidCommand();
        return 4;
    }

    public void setUpWaitingToRecieveType() {
        currentMenuType = Menu.WAITING_TO_RECIEVE_TYPE;
    }

    public int waitingToRecieveTypeAnalyzer(String command) {
        if (Pattern.matches("\\d", command)) {
            int r = Integer.parseInt(command);
            if (r >= 1 && r <= 6) {
                return 1;
            }
            invalidCommand();
            return 3;
        }
        if (Pattern.matches("Back", command)) {
            setUpBarracksMenu4();
            return 2;
        }
        invalidCommand();
        return 3;
    }

    public void youCantBuildInThisPosition() {
        System.out.println("you can't build any building in this position,Please choose another position");
    }

    public void setUpGetXY(int type) {
        currentMenuType = Menu.GET_XY_FOR_BUILD_BUILDING;
        whereDoYouWantBuildBuildigType(type);
    }

    public void whereDoYouWantBuildBuildigType(int type) {
        System.out.println("Where do you want to build " + singleBuildingShowerByType(type) + " ?");
    }

    private int getXYanalyzer() {
        if (Pattern.matches("\\((\\d+) , (\\d+)\\)", command)) {
            return 1;
        }
        invalidCommand();
        return 2;
    }

    private int buildingConstructionWindowAnalyzer(String command) {
        return yesNoRecognizer(command);
    }

    private int availableBuildingMenuAnalyzer(String command) {
        if (matchesToWhereAmI(command)) {
            System.out.println();
            return 50;
        }
        if (matchesToShowMenu(command)) {
            return 0;
        }
        if (Pattern.matches("Back", command)) {
            setUpMainHallMenu3();
            return 20;
        }
        return buildingRecognizer(command);
    }

    private int upgradeMenuAnalyzer(String command) {
        return yesNoRecognizer(command);
    }

    private int initialMenuAnalyzer(String command) {
        if (matchesToWhereAmI(command)) {
            System.out.println("Initial menu! ");
            return 50;
        }
        if (matchesToShowMenu(command)) {
            //setup
            setUpInitialMenu();
            return 0;
        }
        if (Pattern.matches("newGame", command))
            return 1;
        if (Pattern.matches("load (.*)", command)) return 2;
        invalidCommand();
        return 3;
    }

    private int villageMenuAnalyzer(String command) {
        if (matchesToWhereAmI(command)) {
            System.out.println("village menu!");
            return 50;
        }
        if (matchesToShowMenu(command)) {
            //setup
            setUpVillageMenu();
            return 0;
        }
        if (Pattern.matches("attack", command)) {
            return 1;
        }
        if (Pattern.matches("Back", command)) {
            youAreInVillage();
            setUpVillageMenu();
        }
        if (Pattern.matches("showBuildings", command)) return 2;
        if (Pattern.matches("resources", command)) return 3;
        return 3 + buildingRecognizer(command);
    }

    private int mineMenuAnalyzer(String command) {
        if (matchesToWhereAmI(command)) {
            System.out.println("mine menu!");
            return 50;
        }
        if (matchesToShowMenu(command)) {
            //setup
            setUpMineMenu();
            return 0;
        }
        //Mine menu
        if (Pattern.matches("Info", command) || Pattern.matches("1", command)) {
            setUpMineInfoMenu(); //current menu type =2
            return 1;
        }
        if (Pattern.matches("Mine", command) || Pattern.matches("2", command)) {
            return 2;
        }
        if (Pattern.matches("Back", command)) {
            youHaveEnteredVillage();
            setUpVillageMenu();
            return 3;
        }
        invalidCommand();
        return 4;

    }

    private int storageMenuAnalyzer(String command) {
        if (matchesToWhereAmI(command)) {
            System.out.println("storage menu!");
            return 50;
        }
        if (matchesToShowMenu(command)) {
            //setup
            setUpStorageMenu();
            return 0;
        }
        if (Pattern.matches("Info", command) || Pattern.matches("1", command)) {
            setUpStorageInfoMenu();
            return 1;
        }
        if (Pattern.matches("Back", command) || Pattern.matches("2", command)) {
            youHaveEnteredVillage();
            setUpVillageMenu();
            return 2;
        }
        invalidCommand();
        return 3;
    }

    private int storageInfoMenuAnalyzer(String command) {
        if (matchesToWhereAmI(command)) {
            System.out.println("storage info menu!");
            return 50;
        }
        if (matchesToShowMenu(command)) {
            //setup
            setUpStorageInfoMenu();
            return 0;
        }
        if (Pattern.matches("Overall info", command) || Pattern.matches("1", command)) return 401;
        if (Pattern.matches("Upgrade info", command) || Pattern.matches("2", command)) return 402;
        if (Pattern.matches("Sources info", command) || Pattern.matches("3", command)) return 603;
        if (Pattern.matches("Upgrade", command) || Pattern.matches("4", command)) {
            setUpUpgradeBuildingMenu5();
            return 404;
        }
        if (Pattern.matches("Back", command) || Pattern.matches("5", command)) {
            setUpStorageMenu();
            return 605;
        }
        invalidCommand();
        return 606;
    }

    private int defensiveWeaponMenuAnalyzer(String command) {
        if (matchesToWhereAmI(command)) {
            System.out.println("defensive weapon menu!");
            return 50;
        }
        if (matchesToShowMenu(command)) {
            //setup
            setUpDefensiveWeaponMenu12();
            return 0;
        }
        if (Pattern.matches("info", command) || Pattern.matches("1", command)) {
            setUpDefensiveWeaponInfoMenu121();
            return 1;
        }
        if (Pattern.matches("Target", command) || Pattern.matches("2", command)) return 2;
        if (Pattern.matches("Back", command) || Pattern.matches("4", command)) {
            youHaveEnteredVillage();
            setUpVillageMenu();
            return 3;
        }
        invalidCommand();
        return 5;
    }

    public void showTarget(int[] target) {
        for (int i = 0; i < 4; i++) {
            if (target[i] != 0) {
                System.out.print(convertSoldierTypeToString(i + 1) + " ");
            }
        }
    }

    private int defensiveWeaponInfoMenuAnalyzer(String command) {
        if (matchesToWhereAmI(command)) {
            System.out.println("defensive weapon info menu!");
            return 50;
        }
        if (matchesToShowMenu(command)) {
            //setup
            setUpDefensiveWeaponInfoMenu121();
            return 0;
        }

        if (Pattern.matches("Overall info", command) || Pattern.matches("1", command))
            return 401;
        if (Pattern.matches("Upgrade info", command) || Pattern.matches("2", command))
            return 402;
        if (Pattern.matches("upgrade", command)) {
            setUpUpgradeBuildingMenu5();
            return 404;
        }
        if (Pattern.matches("Attack info", command) || Pattern.matches("3", command))
            return 804;
        if (Pattern.matches("Back", command)) {
            setUpDefensiveWeaponMenu12();
            return 805;
        }
        invalidCommand();
        return 806;
    }

    private int mineInfoMenuAnalyzer(String command) {
        if (matchesToWhereAmI(command)) {
            System.out.println("mine info menu!");
            return 50;
        }
        if (matchesToShowMenu(command)) {
            //setup
            setUpMineInfoMenu();
            return 0;
        }
        if (Pattern.matches("Overall info", command) || Pattern.matches("1", command)) {
            return 1;
        }
        if (Pattern.matches("Upgrade info", command) || Pattern.matches("2", command)) {
            return 2;
        }
        if (Pattern.matches("Back", command) || Pattern.matches("3", command)) {
            setUpMineMenu();
            return 3;
        }
        if (Pattern.matches("upgrade", command)) {
            setUpUpgradeBuildingMenu5();
            return 4;
        }
        invalidCommand();
        return 5;
    }

    private int barrackMenuAnalyzer(String command) {
        if (matchesToWhereAmI(command)) {
            System.out.println("barracks menu!");
            return 50;
        }
        if (matchesToShowMenu(command)) {
            //setup
            setUpBarracksMenu4();
            return 0;
        }
        //barracks menu
        if (Pattern.matches("Info", command) || Pattern.matches("1", command)) {
            setUpInfoOfBarracks41();
            return 1;
        }
        if (Pattern.matches("Building soldiers", command) || Pattern.matches("2", command)) {
            setUpBuildingSoldierMenu42();
            return 2;
        }
        if (Pattern.matches("Status", command) || Pattern.matches("3", command))
            return 3;
        if (Pattern.matches("Back", command) || Pattern.matches("4", command)) {
            youHaveEnteredVillage();
            setUpVillageMenu();
            return 4;
        }
        invalidCommand();
        return 6;
    }

    private int barrackInfoMenuAnalyzer(String command) {
        if (matchesToWhereAmI(command)) {
            System.out.println("barracks info menu!");
            return 50;
        }
        if (matchesToShowMenu(command)) {
            setUpInfoOfBarracks41();
            return 0;
        }

        if (Pattern.matches("upgrade", command)) {
            setUpUpgradeBuildingMenu5();
            return 1;
        }
        int r = infoRecognizer(command);
        if (r == 3) setUpBarracksMenu4();
        return r + 1;
    }

    private int buildingSoldiersMenuAnalyzer(String command) {
        return soldierTypeRecognizer(command);
    }

    private int campMenuAnalyzer(String command) {
        if (matchesToWhereAmI(command)) {
            System.out.println("camp menu!");
            return 50;
        }
        if (matchesToShowMenu(command)) {
            setUpCampMenu8();
            return 0;
        }
        if (Pattern.matches("Info", command) || Pattern.matches("1", command)) {
            setUpInfoOfCamMenu81();
            return 1;
        }
        if (Pattern.matches("Soldiers", command) || Pattern.matches("2", command)) return 2;
        if (Pattern.matches("Back", command) || Pattern.matches("3", command)) {
            youHaveEnteredVillage();
            setUpVillageMenu();
            return 3;
        }
        invalidCommand();
        return 4;
    }

    private int campInfoMenuAnalyzer(String command) {
        if (matchesToWhereAmI(command)) {
            System.out.println("camp info menu!");
            return 50;
        }
        if (matchesToShowMenu(command)) {
            setUpInfoOfCamMenu81();
            return 0;
        }
        if (Pattern.matches("Overall info", command) || Pattern.matches("1", command))
            return 1;
        if (Pattern.matches("Upgrade info", command) || Pattern.matches("2", command)) {
            setUpUpgradeBuildingMenu5();
            return 2;
        }
        if (Pattern.matches("Capacity info", command) || Pattern.matches("3", command))
            return 3;
        if (Pattern.matches("Back", command) || Pattern.matches("4", command)) {
            setUpCampMenu8();
            return 4;
        }
        invalidCommand();
        return 5;
    }

    private int attackMenuAnalyzer(String command) {
        if (matchesToWhereAmI(command)) {
            System.out.println("attack menu");
            return 50;
        }
        if (matchesToShowMenu(command)) {
            return 0;
        }
        //attack menu
        if (Pattern.matches("\\d+", command)) {
            int r = Integer.parseInt(command);
            if (r == 1) {
//                    load map
                setUpLoadMapWindow2001();
                return 1;
            }
            if (r >= 2 && r <= sizeOfEnemiesMaps + 1) {
                setUpMapMenu2002();
                return 2;
            }
            if (r == sizeOfEnemiesMaps + 2) {
                //back
                youHaveEnteredVillage();
                setUpVillageMenu();
                return 3;
            }
            invalidCommand();
            return 4;
        }
        invalidCommand();
        return 4;
    }

    public void printMap(EnemyMap enemyMap) {
        for (int index = 0; index < enemyMap.getSize()[0]; index++) {
            for (int indexPrime = 0; indexPrime < enemyMap.getSize()[1]; indexPrime++)
                if (enemyMap.getMap()[index][indexPrime].isEmpty())
                    System.out.print("0");
                else
                    System.out.print("1");
            System.out.println();
        }
    }

    private int mapMenuAnalyzer(String command) {
        if (matchesToWhereAmI(command)) {
            System.out.println("map menu!");
            return 50;
        }
        if (matchesToShowMenu(command)) {
            setUpMapMenu2002();
            return 0;
        }
        //map menu
        if (Pattern.matches("Map info", command) || Pattern.matches("1", command))
            return 1;
        if (Pattern.matches("Attack map", command) || Pattern.matches("2", command)) {
            setUpAttackMapMenu20022();
            return 2;
        }
        if (Pattern.matches("Back", command) || Pattern.matches("3", command))
            return 3;
        invalidCommand();
        return 4;

    }

    private int townHallMenuAnalyzer(String command) {
        if (matchesToWhereAmI(command)) {
            System.out.println("town hall menu!");
            return 50;
        }
        if (matchesToShowMenu(command)) {
            //setup
            setUpMainHallMenu3();
            return 0;
        }
        if (Pattern.matches("Info", command) || Pattern.matches("1", command)) {
            setUpInfoHallMenu31();
            return 1;
        }
        if (Pattern.matches("Available buildings", command) || Pattern.matches("2", command))
            return 2;
        if (Pattern.matches("Status", command) || Pattern.matches("3", command))
            return 3;
        if (Pattern.matches("Back", command) || Pattern.matches("4", command)) {
            youHaveEnteredVillage();
            setUpVillageMenu();
            return 4;
        }
        invalidCommand();
        return 5;
    }

    private int attackMapWindowAnalyzer(String command) {
        if (Pattern.matches("Select [A-Z][a-z]+\\s*\\d*", command))
            return 1;
        if (Pattern.matches("End select", command)) {
            setUpPuttingSoldiersOnEnemyMapWindow200222();
            return 2;
        }
        invalidCommand();
        return 3;
    }

    private int realGameSpaceAnalyzer(String command) {
//        real game space
        if (Pattern.matches("status resources", command))
            return 1;
        if (Pattern.matches("status unit (.*)[a-z]+", command))
            return 2;
        if (Pattern.matches("status units", command))
            return 3;
        if (Pattern.matches("status tower (.*)[a-z]+", command))
            return 4;
        if (Pattern.matches("status towers", command))
            return 5;
        if (Pattern.matches("status all", command))
            return 6;
        if (Pattern.matches("Quit attack", command)) {
            setUpVillageMenu();
            return 7;
        }
        if(Pattern.matches("", command))
            return 10;
        if (Pattern.matches("turn\\d+", command))
            return 8;
        if (Pattern.matches("Put [A-z][a-z]+\\s*\\d+ in \\d+,\\d+", command))
            return 11;

        invalidCommand();
        return 9;
    }

    private int putSoldiersOnMapWindowAnalyzer(String command) {
//        puttingSoldiers on enemy map menu
        if (Pattern.matches("Put [A-z][a-z]+\\s*\\d+ in \\d+,\\d+", command))
            return 1;
        if (Pattern.matches("Go next turn", command)) {
            setUpRealGameSpace3000();
            return 2;
        }
        invalidCommand();
        return 3;
    }

    private int townHallInfoMenuAnalyzer(String command) {
        if (matchesToWhereAmI(command)) {
            System.out.println("tow hall info menu!");
            return 50;
        }
        if (matchesToShowMenu(command)) {
            //setup
            setUpInfoHallMenu31();
            return 0;
        }

        if (Pattern.matches("upgrade", command)) {
            return 1;
        }
        int r = infoRecognizer(command);

        if (r == 3) setUpMainHallMenu3();
        return r + 1;
    }

    private int constructionSoldierWindowAnalyzer(String command) {
        if (Pattern.matches("\\d+", command)) return 1;
        invalidCommand();
        return 2;
    }

    //////////////////////////////////////////////////////////attack
    public void setUpAttackMenu2000(ArrayList<EnemyMap> enemyMaps) {

        System.out.println("1.Load map");
        if (enemyMaps != null) {
            sizeOfEnemiesMaps = enemyMaps.size();
            for (int i = 0; i < enemyMaps.size(); i++) {
                System.out.println(i + 2 + "." + enemyMaps.get(i).getName());
            }
            System.out.println((sizeOfEnemiesMaps + 2) + ".Back");
        }
    }

    public void setUpLoadMapWindow2001() {
        currentMenuType = Menu.LOAD_MAP_WINDOW;
        System.out.println("Enter map path:");
    }

    public void setUpMapMenu2002() {
        System.out.println("1.Map info");
        System.out.println("2.Attack map");
        System.out.println("3.Back");
        currentMenuType = Menu.MAP_MENU;
    }

    public void setUpAttackMapMenu20022() {
        currentMenuType = Menu.ATTACK_MAP_WINDOW;
        System.out.println("Start select");
    }

    public void setUpPuttingSoldiersOnEnemyMapWindow200222() {
        currentMenuType = Menu.PUT_SOLDIERS_ON_MAP_WINDOW;
    }

    public void setUpRealGameSpace3000() {
        currentMenuType = Menu.StartAttack;
    }

    ///////////////////////////////////////////////////////////own
    public void setUpInitialMenu() {
        currentMenuType = Menu.INITIAL_MENU;
        showInitialMenue();
    }

    public void setUpVillageMenu() {
        currentMenuType = Menu.VILLAGE_MENU;
        showVillageMenu();
    }

    public void setUpMineMenu() {
        currentMenuType = Menu.MINE_MENU;
        printMineMenu10();
    }

    public void setUpMineInfoMenu() {
        currentMenuType = Menu.MINE_INFO_MENU;
        printMineInfoMenu101();
        //                    BuildingMenu
    }

    public void setUpStorageMenu() {
        currentMenuType = Menu.STORAGE_MENU;
        System.out.println("1.Info");
        System.out.println("2.Back");
    }

    public void setUpStorageInfoMenu() {
        currentMenuType = Menu.STORAGE_INFO_MENU;
        System.out.println("1.Overall info");
        System.out.println("2.Upgrade info");
        System.out.println("3.Sources info");
        System.out.println("4.Upgrade");
        System.out.println("5.Back");
    }

    public void setUpDefensiveWeaponMenu12() {
        currentMenuType = Menu.DEFENSIVE_WEAPON_MENU;
        System.out.println("1.info");
        System.out.println("2.Target");
        System.out.println("3.Back");
    }

    public void setUpDefensiveWeaponInfoMenu121() {
        currentMenuType = Menu.DEFENSIVE_WEAPON_INFO_MENU;
        System.out.println("1.Overall info");
        System.out.println("2.Upgrade info");
        System.out.println("3.Attack info");
        System.out.println("4.Back");
    }

    public void setUpMainHallMenu3() {
        currentMenuType = Menu.TOWN_HALL_MENU;
        printMainHallMenue3();
    }

    public void setUpInfoHallMenu31() {
        currentMenuType = Menu.TOWN_HALL_INFO_MENU;
        printInfoHall31();
    }

    public void setUpAvailableBuildingsMenue32() {
        currentMenuType = Menu.AVAILABLE_BUILDING_MENU;
    }

    public void setUpUpgradeBuildingMenu5() {
        currentMenuType = Menu.UPGRADE_MENU;
    }

    public void setUpConstructionBuildingMenue6(int type, int cost) {
        currentMenuType = Menu.BUILDING_CONSTRUCTION_WINDOW;
        wantBuildNameForCost(singleBuildingShowerByType(type), cost);
    }

    public void setUpBarracksMenu4() {
        currentMenuType = Menu.BARRACK_MENU;
        System.out.println("1.Info");
        System.out.println("2.Building soldiers");
        System.out.println("3.Status");
        System.out.println("4.Back");

    }

    public void setUpInfoOfBarracks41() {
        currentMenuType = Menu.BARRACK_INFO_MENU;
        printInfoHall31();
    }

    public void setUpBuildingSoldierMenu42() {
        currentMenuType = Menu.BUILDING_SOLDIERS_MENU;
    }

    public void setUpBuildSoldierWaitingToReceieveNum7() {
        System.out.println("How many of this soldier do you want to build?");
        currentMenuType = Menu.CONSTRUCTION_SOLDIER_WINDOW;
    }

    public void setUpCampMenu8() {
        currentMenuType = Menu.CAMP_MENU;
        System.out.println("1.Info");
        System.out.println("2.Soldiers");
        System.out.println("3.Back");

    }

    public void setUpInfoOfCamMenu81() {
        currentMenuType = Menu.CAMP_INFO_MENU;
        System.out.println("1.Overall info");
        System.out.println("2.Upgrade info");
        System.out.println("3.Capacity info");
        System.out.println("4.Back");
    }

    public void showVillageMenu() {
        System.out.println("attack");
        System.out.println("showBuildings");
        System.out.println("resources");

    }

    public void showInitialMenue() {
        System.out.println("newGame");
        System.out.println("loadPreviousGame");
    }

    public void buildingShowerTypeID(int gsonType, int id) {
        switch (gsonType) {
            case 1:
                System.out.println("Gold mine " + id);
                break;
            case 2:
                System.out.println("Elixir mine " + id);
                break;
            case 3:
                System.out.println("Gold storage " + id);
                break;
            case 4:
                System.out.println("Elixir storage " + id);
                break;
            case 5:
                System.out.println("Main building");
                break;
            case 6:
                System.out.println("Barracks " + id);
                break;
            case 7:
                System.out.println("Camp " + id);
                break;
            case 8:
                System.out.println("Archer tower " + id);
                break;
            case 9:
                System.out.println("Cannon " + id);
                break;
            case 10:
                System.out.println("Air defense " + id);
                break;
            case 11:
                System.out.println("Wizard tower " + id);
                break;
            default:
                System.out.println("invalid jsonType");
        }
    }

    public void invalidCommand() {
        System.out.println("invalid command");
    }

    public void printMineMenu10() {
        System.out.println("1.Info");
        System.out.println("2.Mine");
        System.out.println("3.Back");
    }

    public void printStorageMenu11() {
        System.out.println("1.Info");
    }

    public void printMineInfoMenu101() {
        System.out.println("1.Overall info");
        System.out.println("2.Upgrade info");
        System.out.println("3.Back");
    }

    public void printMainHallMenue3() {
        System.out.println("1.Info");
        System.out.println("2.Available buildings");
        System.out.println("3.Status");
        System.out.println("4.Back");
    }

    public void printInfoHall31() {
        System.out.println("1.Overall info");
        System.out.println("2.Upgrade info");
        System.out.println("3.Back");
    }

    public void buildingShowByType(ArrayList<Integer> types) {
        for (int i = 0; i < types.size(); i++) {
            System.out.println(singleBuildingShowerByType(types.get(i)));
        }
    }

    public void dontHaveWorker() {
        System.out.println("You don't have any worker to build this building");
    }

    public void wantBuildNameForCost(String name, int cost) {
        System.out.println("Do you want to build " + name + " for cost " + cost + " ? [Y/N]");
    }

    public void dontHaveEnoughResource() {
        System.out.println("You don't have enough recources.");
    }

    public void buildingSoldierBarrack(ArrayList<int[]> potentialSoldiers) {
        for (int i = 0; i < potentialSoldiers.size(); i++) {
            System.out.print(potentialSoldiers.get(i)[0] + "." + convertSoldierTypeToString(potentialSoldiers.get(i)[0]));
            if (potentialSoldiers.get(i)[1] > 0) {
                System.out.println("A x " + potentialSoldiers.get(i)[1]);
            } else System.out.println("U");
        }
    }

    public void showCampSoldiers(int[] soldierNums) {
        for (int i = 0; i < soldierNums.length; i++) {
            if (soldierNums[i] > 0) {
                System.out.print(convertSoldierTypeToString(i + 1));
                System.out.println(" x " + soldierNums[i]);
            }
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
        }
        return "invalid soldier type";
    }

    public void shoeResources(int[] resources, int score) {
        System.out.println("Gold: " + resources[0]);
        System.out.println("Elixir: " + resources[1]);
        System.out.println("Score:" + score);
    }

    public void overAllInfoShower(int level, int resistance) {
        System.out.println("Level: " + level);
        System.out.println("Health: " + resistance);
    }

    public void upgradeInfoShower(int[] costOfUpgrade) {
        if (costOfUpgrade[0] > 0)
            System.out.println("Upgrade Cost: " + costOfUpgrade[0] + " gold");
        if (costOfUpgrade[1] > 0)
            System.out.println("Upgrade Cost: " + costOfUpgrade[0] + " elixir");
    }

    public void wantUpgradeNameForCostGolds(int buildingType, int goldCost) {
        System.out.print("Do you want to upgrade ");
        switch (buildingType) {
            case 1:
                System.out.print("Gold mine ");
                break;
            case 2:
                System.out.print("Elixir mine ");
                break;
            case 3:
                System.out.print("Gold storage ");
                break;
            case 4:
                System.out.print("Elixir storage ");
                break;
            case 5:
                System.out.print("Main building ");
                break;
            case 6:
                System.out.print("Barracks ");
                break;
            case 7:
                System.out.print("Camp ");
                break;
            case 8:
                System.out.print("Archer tower");
                break;
            case 9:
                System.out.print("Cannon");
                break;
            case 10:
                System.out.print("Air defense");
                break;
            case 11:
                System.out.print("Wizard tower");
                break;
            default:
                System.out.print("invalid gsonType ");
        }
        System.out.println("for " + goldCost + " golds?[Y/N]");
    }

    public void printCampsCapacity(int numOfSoldiers, int totalCapacity) {
        System.out.println("Your camps capacity is " + numOfSoldiers + "/" + totalCapacity);
    }

    public void youHaveEnteredVillage() {
        System.out.println("You have entered village.");
    }

    public void youAreInVillage() {
        System.out.println("You are in village.");
    }

    public boolean matchesToShowMenu(String command) {
        return (Pattern.matches("showMenu", command));
    }

    public boolean matchesToWhereAmI(String command) {
        return (Pattern.matches("whereAmI", command));
    }

    public void noSuchBuilding() {
        System.out.println("No such building");
    }

    public int buildingRecognizer(String command) {
        if (Pattern.matches("Gold mine\\s*\\d*", command))
            return 1;
        if (Pattern.matches("Elixir mine\\s*\\d*", command))
            return 2;
        if (Pattern.matches("Gold storage\\s*\\d*", command))
            return 3;
        if (Pattern.matches("Elixir storage\\s*\\d*", command))
            return 4;
        if (Pattern.matches("Main building", command)) {
            setUpMainHallMenu3();
            return 5;
        }
        if (Pattern.matches("Barracks\\s*\\d*", command)) {
            return 6;
        }
        if (Pattern.matches("Camp\\s*\\d*", command))
            return 7;
        if (Pattern.matches("Archer tower\\s*\\d*", command))
            return 8;
        if (Pattern.matches("Cannon\\s*\\d*", command))
            return 9;
        if (Pattern.matches("Air defense\\s*\\d*", command))
            return 10;
        if (Pattern.matches("Wizard tower\\s*\\d*", command))
            return 11;
        invalidCommand();
        return 12;
    }

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
            default:
                return ("invalid jsonType ");
        }
    }

    public void youCantBuildSoldier() {
        System.out.println("you can't build this soldier");
        setUpBarracksMenu4();
    }

    public void printMine(int goldProduced) {
        System.out.println("Gold Produce : " + goldProduced + " Per DeltaT");
    }

    public void yourSourceStorageGoldIsSourceCapacity(int[][] sourceCapacity) {
        System.out.println("Your gold storage is " + sourceCapacity[0][0] + "/" + sourceCapacity[0][1]);
    }

    public void yourSourceStorageforElixirIsSourceCapacity(int[][] sourceCapacity) {
        System.out.println("Your elixir storage is " + sourceCapacity[1][0] + "/" + sourceCapacity[1][1]);
    }

    public int soldierTypeRecognizer(String command) {
        if (Pattern.matches("Guardian", command))
            return 1;
        if (Pattern.matches("Giant", command))
            return 2;
        if (Pattern.matches("Dragon", command))
            return 3;
        if (Pattern.matches("Archer", command))
            return 4;
        if (Pattern.matches("Wall breaker", command))
            return 5;
        if (Pattern.matches("Healer", command))
            return 6;
        invalidCommand();
        return 7;
    }

    public int yesNoRecognizer(String command) {
        if (Pattern.matches("Y", command) || Pattern.matches("Yes", command))
            return 1;
        if (Pattern.matches("N", command) || Pattern.matches("No", command))
            return 2;
        invalidCommand();
        return 3;

    }

    public int infoRecognizer(String command) {
        if (Pattern.matches("Overall info", command) || Pattern.matches("1", command))
            return 1;
        if (Pattern.matches("Upgrade info", command) || Pattern.matches("2", command))
            return 2;
        if (Pattern.matches("Back", command) || Pattern.matches("3", command)) {
            return 3;
        }
        invalidCommand();
        return 4;
    }

    public void thereIsNoValidFile() {
        System.out.println("There is no valid file in this location");
    }

    public void showEnemyMapInfo(int gold, int elixir, List<EnemyBuilding> enemyBuildings) {
        System.out.println("Gold: " + gold);
        System.out.println("Elixir: " + elixir);
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
                System.out.println(soldierType+" ("+type + ") : " + number);
            }
        }
    }

    public void statusUnitPrint(ArrayList<Person> units) {
        for (Person p : units) {
            System.out.println(convertSoldierTypeToString(p.getType()) + " level = " + p.getLevel() + " in(" + p.getCurrentPosition()[0] + "," + p.getCurrentPosition()[1]
                    + ") with health = " + p.getHealth());
        }
    }

    public void statusBuilding(ArrayList<Building> buildings) {
        for (Building building : buildings) {
            System.out.print(singleBuildingShowerByType(building.getJasonType())+ " level = " + building.getLevel() + " in ");
            System.out.print("(" + building.getPosition()[0] + "," + building.getPosition()[1] + ") ");
            System.out.print("with health = " + building.getHealth() + "\n");
        }
    }

    public void statusDefensiveWeapon(ArrayList<DefensiveWeapon> defensiveWeapons) {
        for (DefensiveWeapon defensiveWeapon : defensiveWeapons) {
            System.out.print(singleBuildingShowerByType(defensiveWeapon.getARM_TYPE())+ " level = " + defensiveWeapon.getLevel() + " in ");
            System.out.print("(" + defensiveWeapon.getPosition()[0] + "," + defensiveWeapon.getPosition()[1] + ") ");
            System.out.print("with health = " + defensiveWeapon.getResistence() + "\n");
        }
    }

    public void statusResourcesOfEnemy(int[][] resources) {
        System.out.println("Gold achieved: " + resources[0][0]);
        System.out.println("Elixir achieved: " + resources[1][0]);
        System.out.println("Gold remained in map: " + resources[0][1]);
        System.out.println("Elixir remained in map: " + resources[1][1]);
    }

    public void invalidPut() {
        System.out.println("You can't put any soldiers here");
    }

    public void showOwnMap(Map map) {
        for (int i = 0; i < 30; i++) {
            for (int j = 0; j < 30; j++) {
                if (map.getMap()[i][j].isEmpty() && map.getMap()[i][j].isConstructable()) {
                    System.out.print("0");
                } else
                    System.out.print("1");
            }
            System.out.println();
        }
    }

    public void townHallStatusShower(ArrayList<int[]> queue) {
        for (int[] b : queue) {
            System.out.println(singleBuildingShowerByType(b[0]) + " " + b[1]);
        }
    }

    public void endGame(Game game) {
        System.out.println("The war ended with " + game.getWonGold() + " golds, " + game.getWonElixir() + " elixirs and " + game.getWonScore() + " scores achieved!");
    }

    public void barrackStatusShower(ArrayList<int[]> queue) {
        for (int[] s : queue) {
            System.out.println(convertSoldierTypeToString(s[0]) + " " + s[1]);
        }
    }

    public void notEnoughUnits(int type) {
        if (type == 1)
            System.out.println("Not enough Guardians in camps");
        if (type == 2)
            System.out.println("Not enough Giants in camps");
        if (type == 3)
            System.out.println("Not enough Dragons in camps");
        if (type == 4)
            System.out.println("Not enough Archers in camps");
        if (type == 5)
            System.out.println("Not enough Wall Breakers in camps");
        if (type == 6)
            System.out.println("Not enough Healers in camps");
    }

    public void warn() {
        System.out.println("there aren't any more soldier in the map ; but you have some selected remaining");
        System.out.println("game will be forfeited");
    }
}


