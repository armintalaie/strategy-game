import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Controller {
    View view = new View();
    World world = new World();
    String currentCommand = null;
    Building currentBuilding = null;
    DefensiveWeapon currentDefensiveWeapon = null;
    Barracks currentBarrack = null;
    Camp currentCamp = null;
    int currentSoldierTypeToBeBuilt = 0;
    int currentBuildingTypeToBeBuilt = 0;
    int input = 0;
    int timeAttack = 0;

    public void runGame() {
        view.showInitialMenue();
        input = view.updateView();

        while (input != -20) {
            currentCommand = view.getCommand();
            System.err.println("current command is : " + view.getCommand());
            System.err.println("current input value is : " + input);

            switch (input / 100) {
                case 0: {
                    if (input == 1) {
                        String temp[] = view.getCommand().split(" ");
                        world.currentGame.turnTimeOwnMap(Integer.parseInt(temp[1]));
                    }
                    if (input == 2) {
                        Pattern saveAddress = Pattern.compile("save (.*)");
                        Matcher matcher = saveAddress.matcher(view.getCommand());
                        if (matcher.find()) {
                            world.saveGame(matcher.group(1), world);
                        }
                    }
                }
                case 1: {
//                    initial menu
                    if (input == 101) {
                        world.newGameMaker();
                        view.setUpVillageMenu();
                    }
                    if (input == 102) {
                        //loadGame
                        Pattern loadAddress = Pattern.compile("load (.*)");
                        Matcher matcher = loadAddress.matcher(view.getCommand());
//                        load game
                        if (matcher.find()) {
                            this.world = world.loadGame(matcher.group(1), view);
                            if (world.games.size() > 0) {
                                this.world.currentGame = world.games.get(0);
                            }
                        }
                    }
                    break;
                }
                case 2: {
//                    village menu
                    if (input == 201) {
//                        attack
                        view.currentMenuType = Menu.ATTACK_MENU;
                        view.setUpAttackMenu2000(world.getEnemyMaps());
                    }
                    if (input == 202) {
//                        show buildings
                        for (Building b : world.currentGame.getOwnMap().getBuildings()) {
                            view.buildingShowerTypeID(b.getJasonType(), b.getId());
                        }
                        System.err.println(world.currentGame.getOwnMap().getBuildings().size());
                        if (world.currentGame.getOwnDefensiveWeapon() != null) {
                            for (DefensiveWeapon d : world.currentGame.getOwnDefensiveWeapon()) {
                                view.buildingShowerTypeID(d.getARM_TYPE(), d.getId());
                            }
                        }
                    }
                    if (input == 203) {
//                        resources
                        view.shoeResources(world.currentGame.getOwnResources(), world.currentGame.getOwnScore());
                    }
                    if (input >= 204 && input <= 207) {
                        //a type building is selected
                        currentBuilding = world.currentGame.findBuildingTypeInOwnMap(input - 203, IDrecognizer(currentCommand));
                        if (currentBuilding == null) {
                            view.noSuchBuilding();
                            view.setUpVillageMenu();
                        } else {
                            if (input == 204 || input == 205) {
                                //mine
                                view.setUpMineMenu();
                            } else view.setUpStorageMenu();
                        }
                    }
//                    208 -->mainHall
                    if (input == 209) {
//                        barrack selected
                        //  if (currentBuilding.getClass().getName().equals("Barracks")) {
                        currentBarrack = (Barracks) world.currentGame.findBuildingTypeInOwnMap(input - 203, IDrecognizer(currentCommand));
                        if (currentBarrack == null) {
                            view.noSuchBuilding();
                            view.setUpVillageMenu();
                        } else view.setUpBarracksMenu4();
                    }
                    if (input == 210) {
//                        camp selected
                        currentCamp = (Camp) world.currentGame.findBuildingTypeInOwnMap(input - 203, IDrecognizer(currentCommand));
                        if (currentCamp == null) {
                            view.noSuchBuilding();
                            view.setUpVillageMenu();
                        } else view.setUpCampMenu8();
                    }
                    if (input >= 211 && input <= 214) {
//                        defensive Weapon selected
                        currentDefensiveWeapon = world.currentGame.findDefensiveWeaponTypeInOwnMap(input - 203, IDrecognizer(currentCommand));
                        if (currentDefensiveWeapon == null) {
                            view.noSuchBuilding();
                            view.setUpVillageMenu();
                        } else
                            view.setUpDefensiveWeaponMenu12();
                    }
                    break;
                }
                case 3: {
//                    mine menu
//                    301 -->info
                    if (input == 302) {
                        if (currentBuilding.getJasonType() == 1) {
                            GoldMine temp = (GoldMine) currentBuilding;
                            view.printMine(temp.getGoldProduce());
                        } else {
                            ElixirMine temp = (ElixirMine) currentBuilding;
                            view.printMine(temp.getElixirProduce());
                        }
                    }
                    if (input == 303) {
//                        back
                        currentBuilding = null;
                    }
                    break;
                }
                case 4: {
//                    mine info menu
                    if (input == 401) {
                        //mine /storage /defensiveWeapon overall info
                        if (currentBuilding != null) {
                            view.overAllInfoShower(currentBuilding.getLevel(), currentBuilding.getResistance());
                        } else {
                            if (currentDefensiveWeapon != null) {
                                view.overAllInfoShower(currentDefensiveWeapon.getLevel(), currentDefensiveWeapon.getResistence());
                            } else System.err.println("BUG!!!!!!  no building is selected");
                        }
                    }
                    if (input == 402) {
//                        upgradeInfo for mine storage defensive weapon
                        if (currentBuilding != null) {
                            view.upgradeInfoShower(currentBuilding.getCostOfUpgrade());
                        } else if (currentDefensiveWeapon != null) {
                            view.upgradeInfoShower(currentDefensiveWeapon.getCOST_OF_UPGRADE());
                        } else {
                            System.err.println("BUG!!!!!!");
                        }
                    }
                    if (input == 404) {
//                        upgrade for mine building defensive weapon
                        if (currentBuilding != null) {
                            view.wantUpgradeNameForCostGolds(currentBuilding.getJasonType(), currentBuilding.getCostOfUpgrade()[0]);
                        } else if (currentDefensiveWeapon != null) {
                            view.wantUpgradeNameForCostGolds(currentDefensiveWeapon.getARM_TYPE(), currentDefensiveWeapon.getCOST_OF_UPGRADE()[0]);
                        } else {
                            System.err.println("BUG!!!!!!");
                        }
                    }
                    break;
                }
                case 6: {
//                    storage info menu
                    if (input == 603) {
//                        source info in storage
                        int r = currentBuilding.getJasonType();
                        if (r == 3) {
//                            gold
                            view.yourSourceStorageGoldIsSourceCapacity(world.currentGame.getGoldAndElixirStorageAndCapacity());
                        } else {
                            view.yourSourceStorageforElixirIsSourceCapacity(world.currentGame.getGoldAndElixirStorageAndCapacity());
                        }
                    }
                    break;
                }
                case 7: {
                    if (input == 702) {
                        view.showTarget(currentDefensiveWeapon.getTarget());
                    }
                    if (input == 703) {
//                        back
                        currentDefensiveWeapon = null;
                    }
                }
                break;
                case 8: {
                    if (input == 804) {
//                        attack info
                        view.attackInfo(currentDefensiveWeapon.getTarget(), currentDefensiveWeapon.getHitPower(), currentDefensiveWeapon.getRADIUS_OF_ATTACK());
                    }
                }
                break;
                case 9: {
                    if (input == 901) {
//                        yes to upgrade
                        int r = 10;
                        if (currentBuilding != null) {
                            r = world.currentGame.upgradeOwnBuilding(currentBuilding);
                        } else {
                            if (currentDefensiveWeapon != null) {
                                r = world.currentGame.upgradeOwnDefensiveBuilding(currentDefensiveWeapon);
                            } else System.err.println("BUG!!!!");
                        }
                        if (r == -1) {
                            view.dontHaveEnoughResource();
                        }
                        if (r == 0) {
                            System.err.println("upgraded successfully and now your resource is :");
                            view.shoeResources(world.currentGame.getOwnResources(), world.currentGame.getOwnScore()); //to test
                        }
                        if (r == -2) {
                            System.err.println("you have to upgrade town hall first!");
                        }
                        endToUprating();
                    }
                    if (input == 902) {
//                        no to upgrade
                        endToUprating();
                    }
                    break;
                }
                case 10: {
//                    barrack menu
                    if (input == 1002) {
//                        building soldiers
                        view.buildingSoldierBarrack(world.currentGame.getPotentialSoldiers(currentBarrack));
                        view.setUpWaitingToRecieveType();
                    }
                    if (input == 1003) {
//                      status of barrack
                        view.barrackStatusShower(world.currentGame.getQueueOfSoldiers(currentBarrack));
                    }
                    if (input == 1004) {
//                        back
                        currentBarrack = null;
                    }
                    break;
                }
                case 11: {
                    if (input == 1101) {
//                       upgrade barrack
                        if (currentBarrack != null) {
                            currentBuilding = currentBarrack;
                            view.wantUpgradeNameForCostGolds(currentBarrack.getJasonType(), currentBarrack.getCostOfUpgrade()[0]);
                        } else {
                            System.err.println("BUG!!!!!!");
                        }
                    }
                    if (input == 1102) {
//                        overall info of barrack
                        if (currentBarrack != null) {
                            view.overAllInfoShower(currentBarrack.getLevel(), currentBarrack.getResistance());
                        } else System.err.println("no barrack is set : BUG");
                    }
                    if (input == 1103) {
//                        upgrade info
                        if (currentBarrack != null) {
                            view.upgradeInfoShower(currentBarrack.getCostOfUpgrade());
                        }
                    }
                    break;
                }
                case 12: {
//                    camp menu
                    if (input == 1202) {
//                        soldiers
                        view.showCampSoldiers(world.currentGame.getSoldiersOfCamps());
                    }
                    if (input == 1203) {
//                        back
                        currentCamp = null;
                    }
                    break;
                }
                case 13: {
                    if (input == 1301) {
//                        overall info of camp
                        view.overAllInfoShower(currentCamp.getLevel(), currentCamp.getResistance());
                    }
                    if (input == 1302) {
//                        upgrade info
                        if (currentCamp != null) {
                            view.upgradeInfoShower(currentCamp.getCostOfUpgrade());
                        } else
                            System.err.println("Bug");
                    }
                    if (input == 1303) {
//                        capacity info
                        int[] r = world.currentGame.getSoldiersAndCapacityOfCamps();
                        view.printCampsCapacity(r[0], r[1]);
                    }
                    break;
                }
                case 14: {
//                    attack menu
                    if (input==1400){
                        view.setUpAttackMenu2000(world.getEnemyMaps());
                    }
                    if (input == 1402) {
//                        index of map
                        //map select shode
                        world.setEnemyMapToCurrentGame(Integer.parseInt(currentCommand) - 2);
                    }
                    break;
                }
                case 15: {
//                    map menu
                    if (input == 1501) {
//                        map info
                        int gold = world.currentEnemy.getResources().get("gold");
                        int elixir = world.currentEnemy.getResources().get("elixir");
                        view.showEnemyMapInfo(gold, elixir, world.currentEnemy.getBuildings());
                    }
                    break;
                }
                case 16: {
                    if (input == 1601) {
//                        load map
                        String mapAddress = view.getCommand();
                        int r = world.loadEnemyMap(mapAddress);
                        if (r == -1)
                            view.thereIsNoValidFile();
                        if (r != -1) {
                            view.currentMenuType = Menu.ATTACK_MENU;
                        }
                        view.setUpAttackMenu2000(world.getEnemyMaps());
                    }
                    break;
                }
                case 17: {
//                    attack map window
                    if (input == 1701) {
                        Pattern selectUnitTypeNum = Pattern.compile("Select ([A-Z][a-z]+)\\s*(\\d*)");
                        String selection = view.getCommand();
                        Matcher matcher = selectUnitTypeNum.matcher(selection);
                        if (matcher.find()) {
                            int[] r = new int[2];
                            r[0] = view.soldierTypeRecognizer(matcher.group(1));
                            r[1] = Integer.parseInt(matcher.group(2));
                            if (world.currentGame.selectUnit(r) == 1)
                                view.notEnoughUnits(r[0]);
                        }
                    }
                    if (input == 1702) {
                        view.printMap(world.currentEnemy);
                    }
                    break;
                }
                case 18: {
//                    put soldiers on map window
                    if (input == 1801) {
                        Pattern putUnit = Pattern.compile("Put ([A-Z][a-z]+)\\s*(\\d+) in (\\d+),(\\d+)");
                        Matcher matcher = putUnit.matcher(view.getCommand());
                        if (matcher.find()) {
                            if (world.currentEnemy.getMap()[Integer.parseInt(matcher.group(3))][Integer.parseInt(matcher.group(4))].isEmpty()) {
                                int r = world.currentGame.putSoldier(view.soldierTypeRecognizer(matcher.group(1)),
                                        Integer.parseInt(matcher.group(2)),
                                        Integer.parseInt(matcher.group(3)),
                                        Integer.parseInt(matcher.group(4)), world.currentEnemy);
                                if (r == 1)
                                    view.invalidPut();
                                if (r == -1)
                                    System.err.println("invalid put");
                            } else
                                view.invalidPut();
                        }
                    }
                }

                case 20: {
                    if (input == 2002) {
//                      available buildings
                        view.setUpAvailableBuildingsMenue32();
                        ArrayList<Integer> r = world.currentGame.availableBuildingsAndDefensiveWeapons();
                        if (r != null) {
                            view.buildingShowByType(r);
                        }
                    }
                    if (input == 2003) {
//                        status
                        view.townHallStatusShower(world.currentGame.getQueueOfBuildingsAndDefensiveWeaponsToBEBuilt());
                    }
                    break;
                }
                case 21: {
//                    townHall info menu
                    if (input == 2101) {
//                        upgrade townHall
                        int r = world.currentGame.upgradeTownHall();
                        if (r == -1) {
                            view.dontHaveEnoughResource();
                        }
                    }
                    if (input == 2102) {
//                  overall info
                        view.overAllInfoShower(world.currentGame.getOwnTownHall().getLevel(), world.currentGame.getOwnTownHall().getResistance());
                    }
                    if (input == 2103) {
//                        upgrade info
                        view.upgradeInfoShower(world.currentGame.getOwnTownHall().getCostOfUpgrade());
                    }
                }
                case 22: {
                    if (input >= 2201 && input <= 2211) {
//                        building or defensive weapon
                        int r = world.currentGame.constructionRequest(input - 2200);//check if it is true please!!!

                        if (r == -2)
                            view.dontHaveWorker();
                        if (r == 0) {
                            view.setUpConstructionBuildingMenue6(input - 2200, world.currentGame.getCostOfConstruction(input - 2200)[0]);
                            currentBuildingTypeToBeBuilt = input - 2200;
                        }
                    }
                    break;
                }
                case 23: {
                    if (input >= 2301 && input <= 2306) {
                        currentSoldierTypeToBeBuilt = input - 2300;
                        int r = world.currentGame.checkePossibilityOfBuildingSoldierType(currentSoldierTypeToBeBuilt);
                        if (r == 0) {
                            view.setUpBuildSoldierWaitingToReceieveNum7();
                        } else
                            view.youCantBuildSoldier();
                    }
                    break;
                }
                case 24: {
                    if (input == 2402) {
//                        no to construct
                        currentBuildingTypeToBeBuilt = 0;
                        view.setUpAvailableBuildingsMenue32();
                        ArrayList<Integer> r = world.currentGame.availableBuildingsAndDefensiveWeapons();
                        if (r != null) {
                            view.buildingShowByType(r);
                        }
                    }
                    if (input == 2401) {
//                        yes to constructtod
                        view.showOwnMap(world.currentGame.ownMap);
                        view.setUpGetXY(currentBuildingTypeToBeBuilt);
                    }
                    break;
                }
                case 25: {
                    if (input == 2501) {
                        world.currentGame.soldierMaker(currentSoldierTypeToBeBuilt, Integer.parseInt(view.getCommand()), currentBarrack);
                        currentSoldierTypeToBeBuilt = 0;
                        view.setUpBarracksMenu4();
                    }
                    break;
                }
                case 26: {
                    if (input == 2601) {
                        Pattern xy = Pattern.compile("\\((\\d+) , (\\d+)\\)");
                        Matcher matcher = xy.matcher(view.getCommand());
                        if (matcher.find()) {
                            int x = Integer.parseInt(matcher.group(1));
                            int y = Integer.parseInt(matcher.group(2));
                            int r = world.currentGame.newBuildingMaker(currentBuildingTypeToBeBuilt, x, y);

                            if (r == -1) {
                                view.youCantBuildInThisPosition();
                            } else {
                                currentBuildingTypeToBeBuilt = 0;
                                view.setUpAvailableBuildingsMenue32();
                                ArrayList<Integer> rr = world.currentGame.availableBuildingsAndDefensiveWeapons();
                                if (rr != null) {
                                    view.buildingShowByType(rr);
                                }
                            }
                        }
                    }
                }
                break;
                case 27: {
                    if (input == 2701) {
                        currentSoldierTypeToBeBuilt = Integer.parseInt(view.getCommand());
                        view.setUpBuildSoldierWaitingToReceieveNum7();
                    }
                }
                break;
                case 30: {
                    EnemyMap enemyMap = world.currentEnemy;
                    Game currentGame = world.currentGame;
                    for (Person person : currentGame.getOwnMap().valuableSoldiers)
                        if (person.getType() != 6) {
                            int[] target = person.operate(enemyMap);
                            if (target != null)
                                hitEnemyBuilding(target, enemyMap);
                            updateAttackMap(currentGame.getOwnMap().valuableSoldiers, enemyMap, currentGame.getOwnMap(), currentGame);
                        }
                    for (DefensiveWeapon defensiveWeapon : enemyMap.getDefensiveWeapons()) {
                        int target[] = defensiveWeapon.attack(currentGame.getOwnMap().valuableSoldiers);
                        if (target[0] != -1) {
                            if (defensiveWeapon.getARM_TYPE() == 9 || defensiveWeapon.getARM_TYPE() == 11)
                                groupHit(target, currentGame.getOwnMap().valuableSoldiers, enemyMap.getSize()[0]);
                            else
                                singleHit(target, currentGame.getOwnMap().valuableSoldiers);
                        }
                        updateAttackMap(currentGame.getOwnMap().valuableSoldiers, enemyMap, currentGame.getOwnMap(), currentGame);
                    }
                    if (gameFinished(enemyMap, currentGame)) {
                        input = 3007;
                    }

                    if (input == 3001) {
                        view.statusResourcesOfEnemy(world.currentGame.statusResourcesOfEnemy(enemyMap));
                    }
                    if (input == 3002) {
                        //done
                        Pattern statusUnitUnitType = Pattern.compile("status unit ([A-Z][a-z]+)");
                        Matcher matcher = statusUnitUnitType.matcher(view.getCommand());
                        int r;
                        if (matcher.find()) {
                            r = view.soldierTypeRecognizer(matcher.group(1));
                            view.statusUnitPrint(world.currentGame.statusUnitAttackMode(r));
                        }
                    }
                    if (input == 3003) {
//                       done
                        view.statusUnitPrint(world.currentGame.statusUnitsAttackMode());
                    }
                    if (input == 3004) {
                        Pattern statusTowerType = Pattern.compile("status tower (.*[a-z]+)");
                        Matcher matcher = statusTowerType.matcher(view.getCommand());
                        int r;
                        if (matcher.find()) {
                            r = view.buildingRecognizer(matcher.group(1));
                            if (r >= 8) {
//                            defensiveWeapon
                                view.statusDefensiveWeapon(world.currentGame.statusDefensiveWeaponAttackMode(r, enemyMap));
                            } else view.statusBuilding(world.currentGame.statusBuildingAttackMode(r, enemyMap));
                        }
                    }
                    if (input == 3005) {
//                        status towers
                        view.statusBuilding(world.currentGame.statusBuildingsAttackMode(enemyMap));
                        view.statusDefensiveWeapon(world.currentGame.statusDefensiveWeaponsAttackMode(enemyMap));
                    }
                    if (input == 3006) {
//                        status all
//                        statusUnits
                        view.statusUnitPrint(world.currentGame.statusUnitsAttackMode());
//                        status towers
                        view.statusBuilding(world.currentGame.statusBuildingsAttackMode(enemyMap));
                        view.statusDefensiveWeapon(world.currentGame.statusDefensiveWeaponsAttackMode(enemyMap));
                    }
                    if (input == 3007) {
                        view.currentMenuType = Menu.VILLAGE_MENU;
                        view.endGame(currentGame);
                        world.currentGame.finishGame();
                    }
                    if (input == 3008) {
                        int time = Integer.parseInt(view.getCommand().split(" ")[1]);

                        if (timeAttack == time) {
                            input = 3009;
                            timeAttack = 0;
                        }
                        if (timeAttack < time) {
                            timeAttack++;
                            continue;
                        }
                    }
                    break;
                }
            }
            input = view.updateView();
        }
    }

    public int IDrecognizer(String nameID) {
        Pattern nameIdPattern = Pattern.compile("(.*)[a-z]\\s*(\\d+)");
        Matcher matcher = nameIdPattern.matcher(nameID);
        if (matcher.find()) {

            return Integer.parseInt(matcher.group(2));
        }
        return -1000000000;
    }

    public void endToUprating() {
        if (currentDefensiveWeapon != null) {
            view.setUpDefensiveWeaponInfoMenu121();
        } else {
            if (currentBuilding != null) {
                if (currentBuilding.getJasonType() == 1 || currentBuilding.getJasonType() == 2) {
                    //mine
                    view.setUpMineInfoMenu();
                } else
                    view.setUpStorageInfoMenu();
            } else
                System.err.println("BUG!!!! at end to upgrading");
        }
    }

    private void hitEnemyBuilding(int[] attack, EnemyMap enemyMap) {
        int X = attack[0];
        int Y = attack[1];
        int hitPower = (attack[2]);
        for (Building building : enemyMap.getMapBuildings())
            if (building.getPosition()[1] == Y && building.getPosition()[0] == X) {
                building.reduceHealth(hitPower);
            }

        for (DefensiveWeapon defensiveWeapon : enemyMap.getDefensiveWeapons()) {
            if (defensiveWeapon.getPosition()[1] == Y && defensiveWeapon.getPosition()[0] == X) {
                defensiveWeapon.reduceResistence(hitPower);
            }
        }
    }

    private void singleHit(int[] target, ArrayList<Person> soldiers) {
        int x = target[0];
        int y = target[1];
        for (Person person : soldiers)
            if (person.getCurrentPosition()[0] == x && person.getCurrentPosition()[1] == y && person.getInEnemyMap()) {
                person.loseHealth(target[2]);
                break;
            }
    }

    private void groupHit(int[] target, ArrayList<Person> soldiers, int size) {
        singleHit(target, soldiers);
        if (target[0] > 0) {
            int[] targets = new int[3];
            targets[0] = target[0] - 1;
            targets[1] = target[1];
            singleHit(targets, soldiers);
        }
        if (target[0] > 0 && target[1] < size - 1) {
            int[] targets = new int[3];
            targets[0] = target[0] - 1;
            targets[1] = target[1] + 1;
            singleHit(targets, soldiers);
        }
        if (target[0] > 0 && target[1] > 0) {
            int[] targets = new int[3];
            targets[0] = target[0] - 1;
            targets[1] = target[1] - 1;
            singleHit(targets, soldiers);
        }
        if (target[1] > 0) {
            int[] targets = new int[3];
            targets[0] = target[0];
            targets[1] = target[1] - 1;
            singleHit(targets, soldiers);
        }
        if (target[1] > 0 && target[0] < size - 1) {
            int[] targets = new int[3];
            targets[0] = target[0] + 1;
            targets[1] = target[1] - 1;
            singleHit(targets, soldiers);
        }
        if (target[0] > 0) {
            int[] targets = new int[3];
            targets[0] = target[0] - 1;
            targets[1] = target[1];
            singleHit(targets, soldiers);
        }
        if (target[0] < size - 1) {
            int[] targets = new int[3];
            targets[0] = target[0] + 1;
            targets[1] = target[1];
            singleHit(targets, soldiers);
        }
        if (target[1] < size - 1 && target[0] < size - 1) {
            int[] targets = new int[3];
            targets[0] = target[0] + 1;
            targets[1] = target[1] + 1;
            singleHit(targets, soldiers);
        }
    }

    private void updateAttackMap(ArrayList<Person> soldiers, EnemyMap enemyMap, Map map, Game game) {
        for (int index = 0; index < soldiers.size(); index++)
            if (soldiers.get(index).getHealth() <= 0)
                soldiers.remove(index);
        for (int index = 0; index < enemyMap.getMapBuildings().size(); index++)
            if (enemyMap.getMapBuildings().get(index).getResistance() <= 0) {
                if (enemyMap.getMapBuildings().get(index).getJasonType() == 3) {
                    GoldStorage goldStorage = (GoldStorage) enemyMap.getMapBuildings().get(index);
                    game.addWonGold(goldStorage.getGoldStored());
                    goldStorage.addGold(-goldStorage.getGoldStored());
                }
                if (enemyMap.getMapBuildings().get(index).getJasonType() == 4) {
                    ElixirStorage elixirStorage = (ElixirStorage) enemyMap.getMapBuildings().get(index);
                    game.addWonElixir(elixirStorage.getElixirStored());
                    elixirStorage.addElixir(-elixirStorage.getElixirStored());
                }
                game.addWonScore(enemyMap.getMapBuildings().get(index).getDestructionScore());
                empty(enemyMap, enemyMap.getMapBuildings().get(index).getPosition()[0], enemyMap.getMapBuildings().get(index).getPosition()[1]);
                enemyMap.getMapBuildings().remove(index);
            }
        for (int index = 0; index < enemyMap.getDefensiveWeapons().size(); index++)
            if (enemyMap.getDefensiveWeapons().get(index).getResistence() <= 0) {
                game.addWonScore(enemyMap.getDefensiveWeapons().get(index).getSCORE());
                empty(enemyMap, enemyMap.getDefensiveWeapons().get(index).getPosition()[0], enemyMap.getDefensiveWeapons().get(index).getPosition()[1]);
                enemyMap.getDefensiveWeapons().remove(index);
            }
    }

    private boolean gameFinished(EnemyMap enemyMap, Game game) {
        if (game.ownMap.valuableSoldiers.size() == 0)
            return true;
        for (Person person : game.ownMap.valuableSoldiers) {
            if (!person.getInEnemyMap()) {
                view.warn();
                return true;
            }
        }
        if (enemyMap.getMapBuildings().size() == 0 && enemyMap.getDefensiveWeapons().size() == 0)
            return true;
        return false;
    }

    public void empty(EnemyMap enemyMap, int x, int y) {
        enemyMap.getMap()[x][y].setEmpty(true);
    }
}
