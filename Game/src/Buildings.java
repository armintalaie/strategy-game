import java.util.ArrayList;
import java.util.HashMap;

class Building {
    protected int jasonType;
    protected int costOfConstruction[] = new int[2];
    protected int destructionScore;
    protected int level = 0;
    protected int costOfUpgrade[] = new int[2];
    protected int timeOfConstruction;
    protected int resistance;
    protected int resourcesForAttacker[] = costOfConstruction.clone();
    protected int position[] = new int[2];
    protected int id;
    protected int health;

    public Building(int x, int y, int id) {

    }

    public void upgrade() {
        level += 1;
    }

    public int[] getResourcesForAttacker() {
        return resourcesForAttacker;
    }

    public int getJasonType() {
        return jasonType;
    }

    public int[] getCostOfConstruction() {
        return costOfConstruction;
    }

    public int getDestructionScore() {
        return destructionScore;
    }

    public int getLevel() {
        return level;
    }

    public int[] getCostOfUpgrade() {
        return costOfUpgrade;
    }

    public int[] getPosition() {
        return position;
    }

    public int getId() {
        return id;
    }

    public int getHealth() {
        return health;
    }

    public void reduceHealth(int health) {
        this.health -= health;
        this.resistance -= health;
    }

    public int getResistance() {
        return resistance;
    }
}


class GoldMine extends Building {
    private int goldProduce = 10;

    public GoldMine(int x, int y, int id) {
        super(0, 0, 0);
        this.jasonType = 1;
        this.costOfConstruction[0] = 150;
        this.costOfConstruction[1] = 5;
        this.resourcesForAttacker = costOfConstruction.clone();
        this.costOfUpgrade[0] = 100;
        this.costOfUpgrade[1] = 0;
        this.destructionScore = 2;
        this.resistance = 200;
        this.timeOfConstruction = 0;
        this.position[0] = x;
        this.position[1] = y;
        this.level = 0;
        this.id = id;
    }

    @Override
    public void upgrade() {
        this.level += 1;
        this.goldProduce = (int) (goldProduce + (60 * goldProduce) / 100);
    }

    public int produceGold(int timePass) {
        return timePass * goldProduce;
    }

    public int getGoldProduce() {
        return goldProduce;
    }

    @Override
    public int[] getResourcesForAttacker() {
        return resourcesForAttacker;
    }
}

class ElixirMine extends Building {
    private int elixirProduce = 5;

    public ElixirMine(int x, int y, int id) {
        super(0, 0, 0);
        this.jasonType = 2;
        this.costOfConstruction[0] = 100;
        this.costOfConstruction[1] = 3;
        this.resourcesForAttacker = costOfConstruction.clone();
        this.costOfUpgrade[0] = 100;
        this.costOfUpgrade[1] = 0;
        this.destructionScore = 2;
        this.resistance = 200;
        this.timeOfConstruction = 100;
        this.position[0] = x;
        this.position[1] = y;
        this.level = 0;
        this.id = id;
    }

    @Override
    public void upgrade() {
        this.level += 1;
        this.elixirProduce = (int) (elixirProduce + (60 * elixirProduce) / 100);
    }

    public int produceElixir(int timePass) {
        return timePass * elixirProduce;
    }

    public int getElixirProduce() {
        return elixirProduce;
    }

    @Override
    public int[] getResourcesForAttacker() {
        return resourcesForAttacker;
    }
}

class GoldStorage extends Building {
    private int capacity = 500;
    private int goldStored = 0;
    private int freeSpace;

    public GoldStorage(int x, int y, int id) {
        super(0, 0, 0);
        this.jasonType = 3;
        this.costOfConstruction[0] = 200;
        this.costOfConstruction[1] = 0;
        this.costOfUpgrade[0] = 100;
        this.costOfUpgrade[1] = 0;
        this.destructionScore = 3;
        this.resistance = 300;
        this.timeOfConstruction = 0;
        this.position[0] = x;
        this.position[1] = y;
        this.level = 0;
        this.id = id;
    }

    @Override
    public int[] getCostOfUpgrade() {
        return super.getCostOfUpgrade();
    }

    @Override
    public void upgrade() {
        this.level += 1;
        capacity = (int) (capacity + (60 * capacity) / 100);
    }

    public int getFreeSpace() {
        return capacity - goldStored;
    }

    @Override
    public int[] getResourcesForAttacker() {
        this.resourcesForAttacker[1] = costOfConstruction[1];
        this.resourcesForAttacker[0] = costOfConstruction[0] + goldStored;
        return resourcesForAttacker;
    }

    public int getGoldStored() {
        return goldStored;
    }

    public void addGold(int goldAmount) {
        goldStored += goldAmount;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setGoldStored(int goldStored) {
        this.goldStored = goldStored;
    }
}

class ElixirStorage extends Building {
    private int capacity = 20;
    private int elixirStored = 0;
    private int freeSpace;

    public ElixirStorage(int x, int y, int id) {
        super(0, 0, 0);
        this.jasonType = 4;
        this.costOfConstruction[0] = 200;
        this.costOfConstruction[1] = 0;
        this.costOfUpgrade[0] = 100;
        this.costOfUpgrade[1] = 0;
        this.destructionScore = 3;
        this.resistance = 300;
        this.timeOfConstruction = 0;
        this.position[0] = x;
        this.position[1] = y;
        this.level = 0;
        this.id = id;
    }

    @Override
    public int[] getCostOfUpgrade() {
        return super.getCostOfUpgrade();
    }

    public int getElixirStored() {
        return elixirStored;
    }

    public int getCapacity() {
        return capacity;
    }

    @Override
    public void upgrade() {
        level += 1;
        capacity = (int) (capacity + (60 * capacity) / 100);
    }

    public int getFreeSpace() {
        return capacity - elixirStored;
    }

    @Override
    public int[] getResourcesForAttacker() {
        resourcesForAttacker[0] = costOfConstruction[0];
        resourcesForAttacker[1] = costOfConstruction[1] + elixirStored;
        return resourcesForAttacker;
    }

    public void addElixir(int elixirAmount) {
        elixirStored += elixirAmount;
    }

    public void setElixirStored(int elixirStored) {
        this.elixirStored = elixirStored;
    }
}

class MainBuilding extends Building {
    private int score = 0;
    private int worker = 1;
    private int freeWorker = worker;
    private int workerOnJob = worker - freeWorker;


    public MainBuilding() {
        super(0, 0, 0);
        this.jasonType = 5;
        this.costOfUpgrade[0] = 500;
        this.costOfUpgrade[1] = 0;
        this.destructionScore = 8;
        this.resistance = 1000;
        this.timeOfConstruction = 100;
        this.position[0] = 14;
        this.position[1] = 14;
        this.level = 0;
    }

    public void reduceFreeWorker() {
        freeWorker -= 1;
    }

    public int getScore() {
        return score;
    }

    public void addScore(int score) {
        this.score += score;
    }

    @Override
    public void upgrade() {
        this.level += 1;
        this.resistance += 500;
        if (level % 5 == 0 && this.level != 0)
            this.worker += 1;
    }

    public void addToFreeWorker() {
        freeWorker += 1;
    }

    public void workerIsFree() {
        workerOnJob -= 1;
        freeWorker += 1;
    }

    public int getFreeWorker() {
        return freeWorker;
    }
}

class Barracks extends Building {
    private ArrayList<Integer> soldiersQueue = new ArrayList<>();
    private ArrayList<Integer> soldiersNumber = new ArrayList<>();
    private ArrayList<Integer> timeNeed = new ArrayList<>();
    private int time = 0;
    private HashMap<Integer, Integer> soldiersTimeForTrain = new HashMap<>();
    private HashMap<Integer, Integer> soldiersTrained = new HashMap<>();


    public Barracks(int x, int y, int id) {
        super(0, 0, 0);
        this.jasonType = 6;
        this.costOfConstruction[0] = 200;
        this.costOfConstruction[1] = 0;
        this.resourcesForAttacker = costOfConstruction.clone();
        this.costOfUpgrade[0] = 100;
        this.costOfUpgrade[1] = 0;
        this.destructionScore = 1;
        this.resistance = 300;
        this.timeOfConstruction = 100;
        this.position[0] = x;
        this.position[1] = y;
        this.level = 0;
        this.id = id;

        soldiersTimeForTrain.put(1, 10);
        soldiersTimeForTrain.put(2, 30);
        soldiersTimeForTrain.put(3, 45);
        soldiersTimeForTrain.put(4, 10);
        soldiersTimeForTrain.put(5, 10);
        soldiersTimeForTrain.put(6, 30);
    }

    @Override
    public void upgrade() {
        level += 1;
        for (Integer temp : soldiersTimeForTrain.keySet()) {
            soldiersTimeForTrain.put(temp, soldiersTimeForTrain.get(temp) - 1);
        }
    }

    ArrayList<int[]> queueOfSoldiersToBeBuilt = new ArrayList<>();

    public void addToQueueOfSoldiers(int typ) {
        int[] newWaiter = new int[2];
        newWaiter[0] = typ;
        if (queueOfSoldiersToBeBuilt.size() == 0) {
            newWaiter[1] = soldiersTimeForTrain.get(typ);
        } else {
            int[] lastInQueue = queueOfSoldiersToBeBuilt.get(queueOfSoldiersToBeBuilt.size() - 1);
            newWaiter[1] = lastInQueue[1] + soldiersTimeForTrain.get(typ);
        }
        queueOfSoldiersToBeBuilt.add(newWaiter);
    }

    public void timePassPrime(int n) {
        for (int i = 0; i < n; i++) {
            ArrayList<Integer> toBeRemoved = new ArrayList<>();
            for (int[] s : queueOfSoldiersToBeBuilt) {
                s[1] -= 1;
                if (s[1] == 0) {
                    toBeRemoved.add(queueOfSoldiersToBeBuilt.indexOf(s));
                }
            }
            for (int j : toBeRemoved) {
                queueOfSoldiersToBeBuilt.remove(j);
            }
        }
    }

    public void newSoldier(int type, int number) {
        soldiersQueue.add(type);
        soldiersNumber.add(number);
        timeNeed.add(soldiersTimeForTrain.get(type) * number);
        for (int i = 0; i < number; i++) {
            addToQueueOfSoldiers(type);
        }
    }


    public void timePass(int n) {
        time += n;
        n = 0;
        for (int i = 0; i < timeNeed.size(); i++) {
            if (timeNeed.get(i) > 0) {
                if (timeNeed.get(i) > time)
                    timeNeed.set(i, timeNeed.get(i) - time);
                else
                    timeNeed.set(i, 0);

                if (time / soldiersTimeForTrain.get(soldiersQueue.get(i)) >= 1) {
                    if (soldiersNumber.get(i) >= time / soldiersTimeForTrain.get(soldiersQueue.get(i))) {
                        if (soldiersTrained.containsKey(soldiersQueue.get(i)))
                            soldiersTrained.put(soldiersQueue.get(i), soldiersTrained.get(soldiersQueue.get(i)) +
                                    time / soldiersTimeForTrain.get(soldiersQueue.get(i)));
                        else
                            soldiersTrained.put(soldiersQueue.get(i), time / soldiersTimeForTrain.get(soldiersQueue.get(i)));
                    } else {
                        if (soldiersTrained.containsKey(soldiersQueue.get(i)))
                            soldiersTrained.put(soldiersQueue.get(i), soldiersTrained.get(soldiersQueue.get(i)) + soldiersNumber.get(i));
                        else
                            soldiersTrained.put(soldiersQueue.get(i), soldiersNumber.get(i));
                    }
                    if (soldiersNumber.get(i) >= time / soldiersTimeForTrain.get(soldiersQueue.get(i)))
                        soldiersNumber.set(i, soldiersNumber.get(i) - time / soldiersTimeForTrain.get(soldiersQueue.get(i)));
                    else
                        soldiersNumber.set(i, 0);

                    time = time - soldiersTrained.get(soldiersQueue.get(i)) * soldiersTimeForTrain.get(soldiersQueue.get(i));
                }
            }
        }
    }

    public boolean isAnySoldiersInBarracks() {
        for (Integer x : soldiersTrained.keySet())
            if (soldiersTrained.get(x) != 0)
                return true;
        return false;
    }

    public HashMap<Integer, Integer> getSoldiersTrained() {
        return soldiersTrained;
    }

    @Override
    public int[] getResourcesForAttacker() {
        return resourcesForAttacker;
    }
}

class Camp extends Building {
    private int capacity = 50;
    private int freeSpace = capacity;
    private ArrayList<Person> campUnits = new ArrayList<>();

    public Camp(int x, int y, int id) {
        super(0, 0, 0);
        this.jasonType = 7;
        this.costOfConstruction[0] = 200;
        this.costOfConstruction[1] = 0;
        this.resourcesForAttacker = costOfConstruction.clone();
        this.costOfUpgrade[0] = 200;
        this.costOfUpgrade[1] = 0;
        this.destructionScore = 1;
        this.resistance = 900;
        this.timeOfConstruction = 100;
        this.position[0] = x;
        this.position[1] = y;
        this.level = 0;
        this.id = id;
    }

    public void addUnit(Person person) {
        campUnits.add(person);
        freeSpace--;
    }

    public int getFreeSpace() {
        return freeSpace;
    }

    public int getCapacity() {
        return capacity;
    }

    @Override
    public int[] getResourcesForAttacker() {
        return resourcesForAttacker;
    }

    public ArrayList<Person> getCampUnits() {
        return campUnits;
    }
}

class Wall {
    private int X;
    private int Y;
    private Integer level;

    private Integer y2;

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Wall withLevel(Integer level) {
        this.level = level;
        return this;
    }

    public Wall withX(Integer x) {
//        this.x = x;
        return this;
    }

    public Integer getY2() {
        return y2;
    }

    public void setY2(Integer y2) {
        this.y2 = y2;
    }

    public Wall withY(Integer y) {
        this.y2 = y;
        return this;
    }
}

class EnemyBuilding {

    private Integer type;
    private Integer level;
    private int x;
    private int y;
    private Integer amount;

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public EnemyBuilding withType(Integer type) {
        this.type = type;
        return this;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public EnemyBuilding withLevel(Integer level) {
        this.level = level;
        return this;
    }

    public int getX() {
        return x;
    }

    public void setX(Integer x) {
        this.x = x;
    }

    public EnemyBuilding withX(Integer x) {
        this.x = x;
        return this;
    }

    public int getY() {
        return y;
    }

    public void setY(Integer y) {
        this.y = y;
    }

    public EnemyBuilding withY(Integer y) {
        this.y = y;
        return this;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public EnemyBuilding withAmount(Integer amount) {
        this.amount = amount;
        return this;
    }
}



