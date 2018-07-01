import java.util.ArrayList;
import java.util.Comparator;

class DefensiveWeapon {
    protected int ARM_TYPE;
    protected int SCORE;
    protected int[] COST_OF_CONSTRUCTION;
    protected int[] COST_OF_UPGRADE;
    protected int RADIUS_OF_ATTACK;
    protected int level;
    protected int resistence;
    protected int hitPower;
    protected int id;
    protected int[] position = new int[2];
    protected int[] target = new int[4];
    public DefensiveWeapon() {
    }

    public int[] getTarget() {
        return target;
    }

    public void reduceResistence(int hitPower) {
        this.resistence -= hitPower;
    }

    public int getARM_TYPE() {
        return ARM_TYPE;
    }


    public int getSCORE() {
        return SCORE;
    }

    public int[] getCOST_OF_CONSTRUCTION() {
        return COST_OF_CONSTRUCTION;
    }

    public int[] getCOST_OF_UPGRADE() {
        return COST_OF_UPGRADE;
    }

    public int getRADIUS_OF_ATTACK() {
        return RADIUS_OF_ATTACK;
    }

    public int getLevel() {
        return level;
    }

    public int[] getPosition() {
        return position;
    }

    public int getId() {
        return id;
    }

    public int getResistence() {
        return resistence;
    }

    public int getHitPower() {
        return hitPower;
    }

    void upgrade() {
    }

    int[] attack(ArrayList<Person> soldiers) {
        return null;
    }

    public int isHit(int soldierType) {
        return 10;
    }
}

class ArcherTower extends DefensiveWeapon {
    static int[] r1 = {300, 0};
    static int[] r2 = {100, 0};
    static int[] r3 = {1, 2, 3, 4};

    public ArcherTower(int x, int y, int id) {
        super();
        this.ARM_TYPE = 8;
        this.SCORE = 3;
        this.COST_OF_CONSTRUCTION = r1;
        this.COST_OF_UPGRADE = r2;
        this.RADIUS_OF_ATTACK = 10;
        this.level = 0;
        this.hitPower = INITIAL_HIT_POWER;
        this.resistence = INITIAL_RESISTENCE;
        this.id = id;
        this.position[0] = x;
        this.position[1] = y;
        this.target = r3;
    }

    private static final int INITIAL_RESISTENCE = 300;
    private static final int INITIAL_HIT_POWER = 20;

    @Override
    public int getARM_TYPE() {
        return ARM_TYPE;
    }

    @Override

    public int getSCORE() {
        return SCORE;
    }

    @Override
    public int[] getCOST_OF_CONSTRUCTION() {
        return COST_OF_CONSTRUCTION;
    }

    @Override
    public int[] getCOST_OF_UPGRADE() {
        return COST_OF_UPGRADE;
    }

    @Override
    public int getRADIUS_OF_ATTACK() {
        return RADIUS_OF_ATTACK;
    }

    @Override
    public int getLevel() {
        return level;
    }

    @Override
    public int[] getPosition() {
        return position;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public int getResistence() {
        return resistence;
    }

    @Override
    public int getHitPower() {
        return hitPower;
    }

    public void upgrade() {
        level += 1;
        hitPower += 1;
        resistence += 10;
    }

    public static Comparator<int[]> ascendingDistance = new Comparator<int[]>() {
        @Override
        public int compare(int[] o1, int[] o2) {
            return o1[2] - o2[2];
        }
    };

    public int[] attack(ArrayList<Person> soldiers) {

        ArrayList<int[]> potentialPositions = new ArrayList<>();
        for (Person soldier : soldiers) {
            if (soldier.getType() == 1 || soldier.getType() == 2 || soldier.getType() == 4) {
                if (soldier.getInEnemyMap()) {
//                it is Guardian or Giant or Archer
                    int[] positionAndDistance = {soldier.getCurrentPosition()[0], soldier.getCurrentPosition()[1],
                            (int) (Math.pow((position[0] - soldier.getCurrentPosition()[0]), 2) +
                                    Math.pow((position[1] - soldier.getCurrentPosition()[1]), 2))};
                    potentialPositions.add(positionAndDistance);
                }
            }
        }
        potentialPositions.sort(ascendingDistance);
        if (potentialPositions.size() > 0) {
            int[] target = new int[3];
            target[0] = potentialPositions.get(0)[0];
            target[1] = potentialPositions.get(0)[1];
            if ((Math.pow(target[0], 2) + Math.pow(target[1], 2)) <= Math.pow(RADIUS_OF_ATTACK, 2)) {
                target[2] = this.hitPower;
                return target;
            }
        }
        return new int[]{-1, -1, -1};
    }

    @Override
    public int isHit(int soldierHitPower) {
        resistence -= soldierHitPower;
        if (resistence <= 0) return -1;
        return 0;
    }
}

class Cannon extends DefensiveWeapon {
    static int[] r1 = {400, 0};
    static int[] r2 = {100, 0};
    static int[] r3 = {1, 2, 4, 0};

    public Cannon(int x, int y, int id) {
        super();
        this.ARM_TYPE = 9;
        this.SCORE = 4;
        this.COST_OF_CONSTRUCTION = r1;
        this.COST_OF_UPGRADE = r2;
        this.RADIUS_OF_ATTACK = 13;
        this.level = 0;
        this.hitPower = INITIAL_HIT_POWER;
        this.resistence = INITIAL_RESISTENCE;
        this.id = id;
        this.position[0] = x;
        this.position[1] = y;
        this.target = r3;
    }

    private static final int INITIAL_RESISTENCE = 400;

    private static final int INITIAL_HIT_POWER = 20;

    @Override
    public int getARM_TYPE() {
        return ARM_TYPE;
    }

    @Override

    public int getSCORE() {
        return SCORE;
    }

    @Override
    public int[] getCOST_OF_CONSTRUCTION() {
        return COST_OF_CONSTRUCTION;
    }

    @Override
    public int[] getCOST_OF_UPGRADE() {
        return COST_OF_UPGRADE;
    }

    @Override
    public int getRADIUS_OF_ATTACK() {
        return RADIUS_OF_ATTACK;
    }

    @Override
    public int getLevel() {
        return level;
    }

    @Override
    public int[] getPosition() {
        return position;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public int getResistence() {
        return resistence;
    }

    @Override
    public int getHitPower() {
        return hitPower;
    }

    public void upgrade() {
        level += 1;
        hitPower += 1;
        resistence += 10;
    }

    public static Comparator<int[]> ascendingDistance = new Comparator<int[]>() {
        @Override
        public int compare(int[] o1, int[] o2) {
            return o1[2] - o2[2];
        }
    };

    public int[] attack(ArrayList<Person> soldiers) {
        ArrayList<int[]> potentialPositions = new ArrayList<>();
        for (Person soldier : soldiers) {
            if (soldier.getType() == 1 || soldier.getType() == 2 || soldier.getType() == 4) {
                if (soldier.getInEnemyMap()) {
//                it is Guardian or Giant or Archer
                    int[] positionAndDistance = {soldier.getCurrentPosition()[0], soldier.getCurrentPosition()[1],
                            (int) (Math.pow((position[0] - soldier.getCurrentPosition()[0]), 2) +
                                    Math.pow((position[1] - soldier.getCurrentPosition()[1]), 2))};
                    potentialPositions.add(positionAndDistance);
                }
            }
        }
        potentialPositions.sort(ascendingDistance);

        if (potentialPositions.size() > 0) {
            int[] target = new int[3];
            target[0] = potentialPositions.get(0)[0];
            target[1] = potentialPositions.get(0)[1];
            if ((Math.pow(target[0], 2) + Math.pow(target[1], 2)) <= Math.pow(RADIUS_OF_ATTACK, 2)) {
                target[2] = this.hitPower;
                return target;
            }
        }
        return new int[]{-1, -1, -1};
    }

    @Override
    public int isHit(int soldierHitPower) {
        resistence -= soldierHitPower;
        if (resistence <= 0) return -1;
        return 0;
    }
}


class WizardTower extends DefensiveWeapon {
    static int[] r1 = {300, 0};
    static int[] r2 = {100, 0};
    static int[] r3 = {1, 2, 3, 4};

    public WizardTower(int x, int y, int id) {
        super();
        this.ARM_TYPE = 11;
        this.SCORE = 3;
        this.COST_OF_CONSTRUCTION = r1;
        this.COST_OF_UPGRADE = r2;
        this.RADIUS_OF_ATTACK = 10;
        this.level = 0;
        this.hitPower = INITIAL_HIT_POWER;
        this.resistence = INITIAL_RESISTENCE;
        this.id = id;
        this.position[0] = x;
        this.position[1] = y;
        this.target = r3;
    }

    private static final int INITIAL_RESISTENCE = 300;

    private static final int INITIAL_HIT_POWER = 20;

    @Override
    public int getARM_TYPE() {
        return ARM_TYPE;
    }

    public int getRadiusOfHit() {
        return this.RADIUS_OF_ATTACK;
    }

    @Override

    public int getSCORE() {
        return SCORE;
    }

    @Override
    public int[] getCOST_OF_CONSTRUCTION() {
        return COST_OF_CONSTRUCTION;
    }

    @Override
    public int[] getCOST_OF_UPGRADE() {
        return COST_OF_UPGRADE;
    }

    @Override
    public int getRADIUS_OF_ATTACK() {
        return RADIUS_OF_ATTACK;
    }

    @Override
    public int getLevel() {
        return level;
    }

    @Override
    public int[] getPosition() {
        return position;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public int getResistence() {
        return resistence;
    }

    @Override
    public int getHitPower() {
        return hitPower;
    }

    public void upgrade() {
        level += 1;
        hitPower += 1;
        resistence += 10;
    }

    public static Comparator<int[]> ascendingDistance = new Comparator<int[]>() {
        @Override
        public int compare(int[] o1, int[] o2) {
            return o1[2] - o2[2];
        }
    };

    public int[] attack(ArrayList<Person> soldiers) {
        ArrayList<int[]> potentialPositions = new ArrayList<>();
        for (Person soldier : soldiers) {
            if (soldier.getInEnemyMap()) {
                int[] positionAndDistance = {soldier.getCurrentPosition()[0], soldier.getCurrentPosition()[1],
                        (int) (Math.pow((position[0] - soldier.getCurrentPosition()[0]), 2) +
                                Math.pow((position[1] - soldier.getCurrentPosition()[1]), 2))};
                potentialPositions.add(positionAndDistance);

            }
        }
        potentialPositions.sort(ascendingDistance);
        if (potentialPositions.size() > 0) {
            int[] target = new int[3];
            target[0] = potentialPositions.get(0)[0];
            target[1] = potentialPositions.get(0)[1];
            if ((Math.pow(target[0], 2) + Math.pow(target[1], 2)) <= Math.pow(RADIUS_OF_ATTACK, 2)) {
                target[2] = this.hitPower;
                return target;
            }
        }
        return new int[]{-1, -1, -1};
    }

    @Override
    public int isHit(int soldierHitPower) {
        resistence -= soldierHitPower;
        if (resistence <= 0) return -1;
        return 0;
    }
}

class AirDefense extends DefensiveWeapon {
    static int[] r1 = {300, 0};
    static int[] r2 = {100, 0};
    static int[] r3 = {3, 0, 0, 0};

    public AirDefense(int x, int y, int id) {
        super();
        this.ARM_TYPE = 10;
        this.SCORE = 3;
        this.COST_OF_CONSTRUCTION = r1;
        this.COST_OF_UPGRADE = r2;
        this.RADIUS_OF_ATTACK = 10;
        this.level = 0;
        this.hitPower = INITIAL_HIT_POWER;
        this.resistence = INITIAL_RESISTENCE;
        this.id = id;
        this.position[0] = x;
        this.position[1] = y;
        this.target = r3;
    }

    private static final int INITIAL_RESISTENCE = 300;
    private static final int INITIAL_HIT_POWER = 20;

    @Override
    public int getARM_TYPE() {
        return ARM_TYPE;
    }

    @Override

    public int getSCORE() {
        return SCORE;
    }

    @Override
    public int[] getCOST_OF_CONSTRUCTION() {
        return COST_OF_CONSTRUCTION;
    }

    @Override
    public int[] getCOST_OF_UPGRADE() {
        return COST_OF_UPGRADE;
    }

    @Override
    public int getRADIUS_OF_ATTACK() {
        return RADIUS_OF_ATTACK;
    }

    @Override
    public int getLevel() {
        return level;
    }

    @Override
    public int[] getPosition() {
        return position;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public int getResistence() {
        return resistence;
    }

    @Override
    public int getHitPower() {
        return hitPower;
    }

    public void upgrade() {
        level += 1;
        hitPower += 1;
        resistence += 10;
    }

    public static Comparator<int[]> ascendingDistance = new Comparator<int[]>() {
        @Override
        public int compare(int[] o1, int[] o2) {
            return o1[2] - o2[2];
        }
    };

    public int[] attack(ArrayList<Person> soldiers) {
        ArrayList<int[]> potentialPositions = new ArrayList<>();
        for (Person soldier : soldiers) {
            if (soldier.getType() == 3 && soldier.getInEnemyMap()) {
//                it is Dragon or ]

                int[] positionAndDistance = {soldier.getCurrentPosition()[0], soldier.getCurrentPosition()[1],
                        (int) (Math.pow((position[0] - soldier.getCurrentPosition()[0]), 2) +
                                Math.pow((position[1] - soldier.getCurrentPosition()[1]), 2))};
                potentialPositions.add(positionAndDistance);
            }
        }
        potentialPositions.sort(ascendingDistance);
        if (potentialPositions.size() > 0) {
            int[] target = new int[3];
            target[0] = potentialPositions.get(0)[0];
            target[1] = potentialPositions.get(0)[1];
            if ((Math.pow(target[0], 2) + Math.pow(target[1], 2)) <= Math.pow(RADIUS_OF_ATTACK, 2)) {
                target[2] = this.hitPower;
                return target;
            }
        }
        return new int[]{-1, -1, -1};
    }

    @Override
    public int isHit(int soldierHitPower) {
        resistence -= soldierHitPower;
        if (resistence <= 0) return -1;
        return 0;
    }
}
class Trap extends DefensiveWeapon {
    static int[] r1 = {100, 0};
    static int[] r2 = {100, 0};
    static int[] r3 = {1, 0, 0, 0}; // TODO: 6/29/2018

    public Trap(int x, int y, int id) {
        super();
        this.ARM_TYPE = 13;
        this.SCORE = 1;
        this.COST_OF_CONSTRUCTION = r1;
        this.COST_OF_UPGRADE = r2;
        this.RADIUS_OF_ATTACK = 1;
        this.level = 0;
        this.hitPower = INITIAL_HIT_POWER;
        this.resistence = INITIAL_RESISTENCE;
        this.id = id;
        this.position[0] = x;
        this.position[1] = y;
        this.target = r3;
    }

    private static final int INITIAL_RESISTENCE = 100;
    private static final int INITIAL_HIT_POWER = 100;

    @Override
    public int getARM_TYPE() {
        return ARM_TYPE;
    }

    @Override

    public int getSCORE() {
        return SCORE;
    }

    @Override
    public int[] getCOST_OF_CONSTRUCTION() {
        return COST_OF_CONSTRUCTION;
    }

    @Override
    public int[] getCOST_OF_UPGRADE() {
        return COST_OF_UPGRADE;
    }

    @Override
    public int getRADIUS_OF_ATTACK() {
        return RADIUS_OF_ATTACK;
    }

    @Override
    public int getLevel() {
        return level;
    }

    @Override
    public int[] getPosition() {
        return position;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public int getResistence() {
        return resistence;
    }

    @Override
    public int getHitPower() {
        return hitPower;
    }

    public void upgrade() {
        level += 1;
        hitPower += 1;
        resistence += 10;
    }

    public static Comparator<int[]> ascendingDistance = new Comparator<int[]>() {
        @Override
        public int compare(int[] o1, int[] o2) {
            return o1[2] - o2[2];
        }
    };

    public int[] attack(ArrayList<Person> soldiers) {
        ArrayList<int[]> potentialPositions = new ArrayList<>();
        for (Person soldier : soldiers) {
            if (soldier.getType() == 1 && soldier.getInEnemyMap()) {
//                it is Dragon or ]
                // TODO: 6/29/2018

                int[] positionAndDistance = {soldier.getCurrentPosition()[0], soldier.getCurrentPosition()[1],
                        (int) (Math.pow((position[0] - soldier.getCurrentPosition()[0]), 2) +
                                Math.pow((position[1] - soldier.getCurrentPosition()[1]), 2))};
                potentialPositions.add(positionAndDistance);
            }
        }
        potentialPositions.sort(ascendingDistance);
        if (potentialPositions.size() > 0) {
            int[] target = new int[3];
            target[0] = potentialPositions.get(0)[0];
            target[1] = potentialPositions.get(0)[1];
            if ((Math.pow(target[0], 2) + Math.pow(target[1], 2)) <= Math.pow(RADIUS_OF_ATTACK, 2)) {
                target[2] = this.hitPower;
                return target;
            }
        }
        return new int[]{-1, -1, -1};
    }

    @Override
    public int isHit(int soldierHitPower) {
        resistence -= soldierHitPower;
        if (resistence <= 0) return -1;
        return 0;
    }
}//end of trap
class GiantCastle extends DefensiveWeapon {
    static int[] r1 = {100000, 0};
    static int[] r2 = {100, 0};
    static int[] r3 = {1, 0, 0, 0};// TODO: 6/29/2018
    public GiantCastle(int x, int y, int id) {
        super();
        this.ARM_TYPE = 14;
        this.SCORE = 6;
        this.COST_OF_CONSTRUCTION = r1;
        this.COST_OF_UPGRADE = r2;
        this.RADIUS_OF_ATTACK = 1;
        this.level = 0;
        this.hitPower = INITIAL_HIT_POWER;
        this.resistence = INITIAL_RESISTENCE;
        this.id = id;
        this.position[0] = x;
        this.position[1] = y;
        this.target = r3;
    }

    private static final int INITIAL_RESISTENCE = 700;
    private static final int INITIAL_HIT_POWER = 60;

    @Override
    public int getARM_TYPE() {
        return ARM_TYPE;
    }

    @Override

    public int getSCORE() {
        return SCORE;
    }

    @Override
    public int[] getCOST_OF_CONSTRUCTION() {
        return COST_OF_CONSTRUCTION;
    }

    @Override
    public int[] getCOST_OF_UPGRADE() {
        return COST_OF_UPGRADE;
    }

    @Override
    public int getRADIUS_OF_ATTACK() {
        return RADIUS_OF_ATTACK;
    }

    @Override
    public int getLevel() {
        return level;
    }

    @Override
    public int[] getPosition() {
        return position;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public int getResistence() {
        return resistence;
    }

    @Override
    public int getHitPower() {
        return hitPower;
    }

    public void upgrade() {
        level += 1;
        hitPower += 1;
        resistence += 10;
    }

    public static Comparator<int[]> ascendingDistance = new Comparator<int[]>() {
        @Override
        public int compare(int[] o1, int[] o2) {
            return o1[2] - o2[2];
        }
    };

    public int[] attack(ArrayList<Person> soldiers) {
        ArrayList<int[]> potentialPositions = new ArrayList<>();
        for (Person soldier : soldiers) {
            if (soldier.getType() == 1 && soldier.getInEnemyMap()) {
//                it is Dragon or ] todo


                int[] positionAndDistance = {soldier.getCurrentPosition()[0], soldier.getCurrentPosition()[1],
                        (int) (Math.pow((position[0] - soldier.getCurrentPosition()[0]), 2) +
                                Math.pow((position[1] - soldier.getCurrentPosition()[1]), 2))};
                potentialPositions.add(positionAndDistance);
            }
        }
        potentialPositions.sort(ascendingDistance);
        if (potentialPositions.size() > 0) {
            int[] target = new int[3];
            target[0] = potentialPositions.get(0)[0];
            target[1] = potentialPositions.get(0)[1];
            if ((Math.pow(target[0], 2) + Math.pow(target[1], 2)) <= Math.pow(RADIUS_OF_ATTACK, 2)) {
                target[2] = this.hitPower;
                return target;
            }
        }
        return new int[]{-1, -1, -1};
    }

    @Override
    public int isHit(int soldierHitPower) {
        resistence -= soldierHitPower;
        if (resistence <= 0) return -1;
        return 0;
    }
    public void move(int verticalDirection,int horizentalDirection){
        position[0]+=horizentalDirection;
        position[1]+=verticalDirection;
    }
}




