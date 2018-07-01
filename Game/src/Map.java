import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

class Map {
    //map layout
    private Cell[][] map = new Cell[30][30];

    ArrayList<Person> soldiers = new ArrayList<>();
    ArrayList<Building> buildings = new ArrayList<>();
    ArrayList<DefensiveWeapon> defensiveWeapons = new ArrayList<>();
    MainBuilding mainBuilding = new MainBuilding();
    ArrayList<Camp> camps = new ArrayList<>();
    ArrayList<Barracks> barracks = new ArrayList<>();
    ArrayList<GoldStorage> goldStorages = new ArrayList<>();
    ArrayList<ElixirMine> elixirMines = new ArrayList<>();
    ArrayList<ElixirStorage> elixirStorages = new ArrayList<>();
    ArrayList<GoldMine> goldMines = new ArrayList<>();


    ArrayList<Cannon> cannons = new ArrayList<>();
    ArrayList<WizardTower> wizardTowers = new ArrayList<>();
    ArrayList<ArcherTower> archerTowers = new ArrayList<>();
    ArrayList<AirDefense> airDefenses = new ArrayList<>();

    ArrayList<Person> valuableSoldiers = new ArrayList<>();
    ArrayList<Archer> archers = new ArrayList<>();
    ArrayList<Guardian> guardians = new ArrayList<>();
    ArrayList<Giant> giants = new ArrayList<>();
    ArrayList<Dragon> dragons = new ArrayList<>();
    ArrayList<Healer> healers = new ArrayList<>();
    ArrayList<int[]> queueOfBuildingsToBeBuilt = new ArrayList<>();
    ArrayList<int[]> getQueueOfBuildingsToBeUpgraded = new ArrayList<>();
    HashMap<Integer, Integer> timeForConstruction = new HashMap<>();

    public void addToQueueOfUpgrade(int type, int id) {
        int[] newWaiter = new int[3];
        if (queueOfBuildingsToBeBuilt.size() == 0) {
            newWaiter[0] = type;
            newWaiter[1] = id;
            newWaiter[2] = timeForConstruction.get(type);
            getQueueOfBuildingsToBeUpgraded.add(newWaiter);
        } else {
            int[] lastWaiter = queueOfBuildingsToBeBuilt.get(queueOfBuildingsToBeBuilt.size() - 1);
            int t = lastWaiter[2];

            newWaiter[0] = type;
            newWaiter[1] = id;
            newWaiter[2] = t + timeForConstruction.get(type);
            getQueueOfBuildingsToBeUpgraded.add(newWaiter);
        }
    }

    public void updateQueueOfUpgrade() {
        ArrayList<Integer> toBeRemoved = new ArrayList<>();
        for (int[] b : getQueueOfBuildingsToBeUpgraded) {
            b[2] -= 1;
            if (b[2] == 0) {
//                time to update
                upgrade(b[0], b[1]);
                toBeRemoved.add(getQueueOfBuildingsToBeUpgraded.indexOf(b));
                mainBuilding.addToFreeWorker();

            }
        }
        for (int i : toBeRemoved) {
            getQueueOfBuildingsToBeUpgraded.remove(i);
        }
    }

    public void upgrade(int type, int id) {

        if (type >= 1 && type <= 7)
            for (int i = 0; i < buildings.size(); i++) {
                if (buildings.get(i).getJasonType() == type && buildings.get(i).getId() == id)
                    buildings.get(i).upgrade();
                mainBuilding.workerIsFree();
            }
        else
            for (int i = 0; i < defensiveWeapons.size(); i++) {
                if (defensiveWeapons.get(i).getARM_TYPE() == type && defensiveWeapons.get(i).getId() == id)
                    defensiveWeapons.get(i).upgrade();
            }
    }

    public void addToQueue(int type, int x, int y) {
        int[] newWaiter = new int[4];
        if (queueOfBuildingsToBeBuilt.size() > 0) {
            int[] lastWaiter = queueOfBuildingsToBeBuilt.get(queueOfBuildingsToBeBuilt.size() - 1);
            int t = lastWaiter[1];
            newWaiter[1] = t + timeForConstruction.get(type);
        } else {
            newWaiter[1] = timeForConstruction.get(type);
        }
        newWaiter[0] = type;
        newWaiter[2] = x;
        newWaiter[3] = y;
        queueOfBuildingsToBeBuilt.add(newWaiter);

    }

    public void updateQueue() {
        ArrayList<Integer> toBeRemoved = new ArrayList<>();
        for (int[] b : queueOfBuildingsToBeBuilt) {
            b[1] -= 1;
            if (b[1] == 0) {
//                time to build
                mainBuilding.addToFreeWorker();
                buildBuildingByType(b[0], b[2], b[3]);
                toBeRemoved.add(queueOfBuildingsToBeBuilt.indexOf(b));

            }
        }
        for (int i : toBeRemoved) {
            queueOfBuildingsToBeBuilt.remove(i);
        }
    }

    public void buildBuildingByType(int type, int x, int y) {
        switch (type) {
            case 1: {
                GoldMine goldMine = new GoldMine(x, y, goldMines.size() + 1);
                map[x][y].setEmpty(false);
                buildings.add(goldMine);
                goldMines.add(goldMine);
            }
            break;
            case 2: {
                ElixirMine elixirMine = new ElixirMine(x, y, elixirMines.size() + 1);
                map[x][y].setEmpty(false);
                buildings.add(elixirMine);
                elixirMines.add(elixirMine);
            }
            break;
            case 3: {
                GoldStorage goldStorage = new GoldStorage(x, y, goldStorages.size() + 1);
                map[x][y].setEmpty(false);
                buildings.add(goldStorage);
                goldStorages.add(goldStorage);
            }
            break;
            case 4: {
                ElixirStorage elixirStorage = new ElixirStorage(x, y, elixirStorages.size() + 1);
                map[x][y].setEmpty(false);
                buildings.add(elixirStorage);
                elixirStorages.add(elixirStorage);
            }
            break;
            case 6: {
                Barracks b = new Barracks(x, y, barracks.size() + 1);
                barracks.add(b);
                map[x][y].setEmpty(false);
                buildings.add(b);
            }
            break;
            case 7: {
                Camp camp = new Camp(x, y, camps.size() + 1);
                camps.add(camp);
                map[x][y].setEmpty(false);
                buildings.add(camp);
            }
            break;
            case 8: {
                ArcherTower archerTower = new ArcherTower(x, y, archerTowers.size() + 1);
                archerTowers.add(archerTower);
                map[x][y].setEmpty(false);
                defensiveWeapons.add(archerTower);
            }
            break;
            case 9: {
                Cannon cannon = new Cannon(x, y, cannons.size() + 1);
                cannons.add(cannon);
                map[x][y].setEmpty(false);
                defensiveWeapons.add(cannon);
            }
            break;
            case 10: {
                AirDefense airDefense = new AirDefense(x, y, airDefenses.size() + 1);
                airDefenses.add(airDefense);
                map[x][y].setEmpty(false);
                defensiveWeapons.add(airDefense);
            }
            break;
            case 11: {
                WizardTower wizardTower = new WizardTower(x, y, wizardTowers.size() + 1);
                wizardTowers.add(wizardTower);
                map[x][y].setEmpty(false);
                defensiveWeapons.add(wizardTower);
            }
            break;
        }
    }

    Map() {
        buildings.add(mainBuilding);
        timeForConstruction.put(1, 300);
        timeForConstruction.put(2, 100);
        timeForConstruction.put(3, 100);
        timeForConstruction.put(4, 100);
        timeForConstruction.put(5, 100);
        timeForConstruction.put(6, 100);
        timeForConstruction.put(7, 100);
        timeForConstruction.put(8, 60);
        timeForConstruction.put(9, 100);
        timeForConstruction.put(10, 60);
        timeForConstruction.put(11, 120);
        timeForConstruction.put(12, 20);
        timeForConstruction.put(13, 40);
        timeForConstruction.put(14, 4000);

        for (int i = 0; i < 30; i++)
            for (int j = 0; j < 30; j++)
                map[i][j] = new Cell();

        map[0][0].setConstructable(false);
        map[29][29].setConstructable(false);
        map[29][0].setConstructable(false);
        map[0][29].setConstructable(false);

        map[14][14].setConstructable(false);
        map[14][15].setConstructable(false);
        map[15][14].setConstructable(false);
        map[15][15].setConstructable(false);

        map[14][14].setEmpty(false);
        map[14][15].setEmpty(false);
        map[15][14].setEmpty(false);
        map[15][15].setEmpty(false);

        for (int i = 0; i < 10; i++)
            CreateRandomStorage(0);
        for (int i = 0; i < 50; i++)
            CreateRandomStorage(1);
    }

    private void CreateRandomStorage(int type) {
        Random rand = new Random();
        int x = rand.nextInt(30);
        int y = rand.nextInt(30);


        if (!map[x][y].isEmpty())
            CreateRandomStorage(type);
        else {
            if (type == 0) {
                GoldStorage goldStorage = new GoldStorage(x, y, goldStorages.size() + 1);
                goldStorage.addGold(500);
                goldStorages.add(goldStorage);
                buildings.add(goldStorage);
                map[x][y].setEmpty(false);
            }
            if (type == 1) {
                ElixirStorage elixirStorage = new ElixirStorage(x, y, elixirStorages.size() + 1);
                elixirStorage.addElixir(20);
                elixirStorages.add(elixirStorage);
                buildings.add(elixirStorage);
                map[x][y].setEmpty(false);
            }
        }
    }

    public ArrayList<Building> getBuildings() {
        return buildings;
    }

    public ArrayList<DefensiveWeapon> getDefensiveWeapons() {
        return defensiveWeapons;
    }

    public MainBuilding getMainBuilding() {
        return mainBuilding;
    }

    public Cell[][] getMap() {
        return map;
    }

    public ArrayList<ElixirMine> getElixirMines() {
        return elixirMines;
    }

    public ArrayList<ElixirStorage> getElixirStorages() {
        return elixirStorages;
    }

    public ArrayList<GoldMine> getGoldMines() {
        return goldMines;
    }

    public ArrayList<GoldStorage> getGoldStorages() {
        return goldStorages;
    }

    public ArrayList<Camp> getCamps() {
        return camps;
    }


    public void updateBuldings(int timePassed) {
        int elixirToStorage = 0;
        int goldToStorage = 0;

        for (int i = 0; i < barracks.size(); i++) {
            barracks.get(i).timePass(timePassed);
            barracks.get(i).timePassPrime(timePassed);
        }

        for (int i = 0; i < barracks.size(); i++) {
            for (int j = 0; j < camps.size(); j++) {
                while (camps.get(j).getFreeSpace() != 0 && barracks.get(i).isAnySoldiersInBarracks()) {
                    for (Integer x : barracks.get(i).getSoldiersTrained().keySet())
                        if (barracks.get(i).getSoldiersTrained().get(x) != 0) {
                            switch (x) {
                                case 1: {
                                    Guardian guardian = new Guardian();
                                    camps.get(j).addUnit(guardian);
                                    soldiers.add(guardian);
                                    guardians.add(guardian);
                                    barracks.get(i).getSoldiersTrained().put(1, barracks.get(i).getSoldiersTrained().get(1) - 1);
                                }
                                break;
                                case 2: {
                                    Giant giant = new Giant();
                                    camps.get(j).addUnit(giant);
                                    soldiers.add(giant);
                                    giants.add(giant);
                                    barracks.get(i).getSoldiersTrained().put(2, barracks.get(i).getSoldiersTrained().get(2) - 1);

                                }
                                break;
                                case 3: {
                                    Dragon dragon = new Dragon();
                                    camps.get(j).addUnit(dragon);
                                    soldiers.add(dragon);
                                    dragons.add(dragon);
                                    barracks.get(i).getSoldiersTrained().put(3, barracks.get(i).getSoldiersTrained().get(3) - 1);
                                }
                                break;
                                case 4: {
                                    Archer archer = new Archer();
                                    camps.get(j).addUnit(archer);
                                    soldiers.add(archer);
                                    archers.add(archer);
                                    barracks.get(i).getSoldiersTrained().put(4, barracks.get(i).getSoldiersTrained().get(4) - 1);
                                }
                                break;
                                case 5:{
//                                    camps.get(j).addUnit(new WallBraker);
//                                    barracks.get(i).getSoldiersTrained().put(5, barracks.get(i).getSoldiersTrained().get(5) - 1);
                                }
                                break;
                                case 6: {
                                    camps.get(j).addUnit(new Healer());
                                    barracks.get(i).getSoldiersTrained().put(6, barracks.get(i).getSoldiersTrained().get(5) - 1);
                                }
                                break;
                            }
                        }
                }
            }
        }

        for (int i = 0; i < goldMines.size(); i++) {
            goldToStorage += goldMines.get(i).produceGold(timePassed);
        }

        for (int i = 0; i < elixirMines.size(); i++) {
            elixirToStorage += elixirMines.get(i).produceElixir(timePassed);
        }

        for (int i = 0; i < goldStorages.size(); i++) {
            while (goldStorages.get(i).getFreeSpace() != 0 && goldToStorage != 0) {
                goldStorages.get(i).addGold(1);
                goldToStorage--;
            }
        }

        for (int i = 0; i < elixirStorages.size(); i++) {
            while (elixirStorages.get(i).getFreeSpace() != 0 && elixirToStorage != 0) {
                elixirStorages.get(i).addElixir(1);
                elixirToStorage--;
            }
        }
    }
}

class Cell {
    private boolean isEmpty = true;
    private boolean isConstructable = true;

    public boolean isEmpty() {
        return isEmpty;
    }

    public void setEmpty(boolean empty) {
        isEmpty = empty;
    }

    public boolean isConstructable() {
        return isConstructable;
    }

    public void setConstructable(boolean constructable) {
        isConstructable = constructable;
    }
}

class EnemyMap {
    private String name = null;
    private int[] size = new int[2];
    private List<Wall> walls = null;
    private HashMap<String, Integer> resources;
    private List<EnemyBuilding> buildings = null;
    private Cell[][] map;
    private ArrayList<Building> MapBuildings = new ArrayList<>();
    private ArrayList<DefensiveWeapon> defensiveWeapons = new ArrayList<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void initializeMap() {
        map = new Cell[this.size[0]][this.size[1]];

        for (int i = 0; i < this.size[0]; i++)
            for (int j = 0; j < this.size[1]; j++)
                map[i][j] = new Cell();

        for (EnemyBuilding enemyBuilding : buildings) {
            map[enemyBuilding.getX()][enemyBuilding.getY()].setEmpty(false);
        }
        createBuildings();
    }

    private void createBuildings() {
        MapBuildings.clear();
        defensiveWeapons.clear();
        for (EnemyBuilding enemyBuilding : buildings) {
            int X = enemyBuilding.getX();
            int Y = enemyBuilding.getY();
            switch (enemyBuilding.getType()) {
                case 1: {
                    GoldMine goldMine = new GoldMine(X, Y, 0);
                    for (; goldMine.getLevel() < enemyBuilding.getLevel(); )
                        goldMine.upgrade();
                    MapBuildings.add(goldMine);

                    break;
                }
                case 2: {
                    ElixirMine elixirMine = new ElixirMine(X, Y, 0);
                    MapBuildings.add(elixirMine);
                    for (; elixirMine.getLevel() < enemyBuilding.getLevel(); )
                        elixirMine.upgrade();
                    break;
                }
                case 3: {
                    GoldStorage goldStorage = new GoldStorage(X, Y, 0);
                    goldStorage.addGold(enemyBuilding.getAmount());
                    for (; goldStorage.getLevel() < enemyBuilding.getLevel(); )
                        goldStorage.upgrade();
                    MapBuildings.add(goldStorage);
                    break;
                }
                case 4: {
                    ElixirStorage elixirStorage = new ElixirStorage(X, Y, 0);
                    elixirStorage.addElixir(enemyBuilding.getAmount());
                    for (; elixirStorage.getLevel() < enemyBuilding.getLevel(); )
                        elixirStorage.upgrade();
                    MapBuildings.add(elixirStorage);
                    break;
                }
                case 5: {
                    MainBuilding mainBuilding = new MainBuilding();
                    for (; mainBuilding.getLevel() < enemyBuilding.getLevel(); )
                        mainBuilding.upgrade();
                    MapBuildings.add(mainBuilding);
                    break;
                }
                case 6: {
                    Barracks barracks = new Barracks(X, Y, 0);
                    for (; barracks.getLevel() < enemyBuilding.getLevel(); )
                        MapBuildings.add(barracks);
                }
                case 7: {
                    Camp camp = new Camp(X, Y, 0);
                    for (; camp.getLevel() < enemyBuilding.getLevel(); )
                        camp.upgrade();
                    MapBuildings.add(camp);
                    break;
                }
                case 8: {
                    ArcherTower archerTower = new ArcherTower(X, Y, 0);
                    for (; archerTower.getLevel() < enemyBuilding.getLevel(); )
                        archerTower.upgrade();
                    defensiveWeapons.add(archerTower);
                    break;
                }
                case 9: {
                    Cannon cannon = new Cannon(X, Y, 0);
                    for (; cannon.getLevel() < enemyBuilding.getLevel(); )
                        cannon.upgrade();
                    defensiveWeapons.add(cannon);
                    break;
                }
                case 10: {
                    AirDefense airDefense = new AirDefense(X, Y, 0);
                    for (; airDefense.getLevel() < enemyBuilding.getLevel(); )
                        airDefense.upgrade();
                    defensiveWeapons.add(airDefense);
                    break;
                }
                case 11: {
                    WizardTower wizardTower = new WizardTower(X, Y, 0);
                    for (; wizardTower.getLevel() < enemyBuilding.getLevel(); )
                        wizardTower.upgrade();
                    defensiveWeapons.add(wizardTower);
                    break;
                }

            }
        }

    }

    public ArrayList<Building> getMapBuildings() {
        return MapBuildings;
    }

    public ArrayList<DefensiveWeapon> getDefensiveWeapons() {
        return defensiveWeapons;
    }

    public Cell[][] getMap() {
        return map;
    }

    public int[] getSize() {
        return size;
    }



    public HashMap<String, Integer> getResources() {
        return resources;
    }


    public List<EnemyBuilding> getBuildings() {
        return buildings;
    }


}