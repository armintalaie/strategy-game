class Person {
    protected int type;
    protected int costOfProduction;
    protected int timeOfProduction;
    protected int health;
    protected int radiusOfEffect;
    protected int hitPower;
    protected int level;
    protected int[] currentPosition = new int[2];
    protected int maxDistance;
    protected boolean canFly;
    protected boolean inEnemyMap = false;

    public Person() {
    }

    public void setInEnemyMap(boolean inEnemyMap) {
        this.inEnemyMap = inEnemyMap;
    }

    public boolean getInEnemyMap() {
        return this.inEnemyMap;
    }

    int[] findPath(EnemyMap map) {
        return null;
    }

    int[] attack(int[] target, EnemyMap map) {
        return null;
    }

    int[] move(int[] target, EnemyMap map) {
        direction dir = null;
        double distance = Math.sqrt(Math.pow(Math.abs(currentPosition[0] - target[0]), 2) + Math.pow(Math.abs(currentPosition[1] - target[1]), 2));
        if (currentPosition[0] > 0 && map.getMap()[currentPosition[0] - 1][currentPosition[1]].isEmpty()) {
            double newDistance = Math.sqrt(Math.pow(Math.abs(currentPosition[0] - 1 - target[0]), 2) + Math.pow(Math.abs(currentPosition[1] - target[1]), 2));
            if (newDistance < distance)
                dir = direction.UP;
        }
        if (currentPosition[1] > 0 && map.getMap()[currentPosition[0]][currentPosition[1] - 1].isEmpty()) {
            double newDistance = Math.sqrt(Math.pow(Math.abs(currentPosition[0] - target[0]), 2) + Math.pow(Math.abs(currentPosition[1] - 1 - target[1]), 2));
            if (newDistance < distance)
                dir = direction.LEFT;
        }
        if (currentPosition[0] < 29 && map.getMap()[currentPosition[0] + 1][currentPosition[1]].isEmpty()) {
            double newDistance = Math.sqrt(Math.pow(Math.abs(currentPosition[0] + 1 - target[0]), 2) + Math.pow(Math.abs(currentPosition[1] - target[1]), 2));
            if (newDistance < distance)
                dir = direction.DOWN;
        }
        if (currentPosition[1] < 29 && map.getMap()[currentPosition[0]][currentPosition[1] + 1].isEmpty()) {
            double newDistance = Math.sqrt(Math.pow(Math.abs(currentPosition[0] - target[0]), 2) + Math.pow(Math.abs(currentPosition[1] + 1 - target[1]), 2));
            if (newDistance < distance)
                dir = direction.RIGHT;
        }
        if (dir == direction.LEFT)
            currentPosition[1]--;
        if (dir == direction.RIGHT)
            currentPosition[1]++;
        if (dir == direction.UP)
            currentPosition[0]--;
        if (dir == direction.DOWN)
            currentPosition[0]++;
        return null;
    }

    public int getCostOfProduction() {
        return costOfProduction;
    }

    public int getTimeOfProduction() {
        return timeOfProduction;
    }

    public int getHealth() {
        return health;
    }

    public void loseHealth(int health) {
        this.health -= health;
    }

    public void getHealth(int health) {
        this.health += health;
    }

    public int getRadiusOfEffect() {
        return radiusOfEffect;
    }

    public int getHitPower() {
        return hitPower;
    }

    public int getLevel() {
        return level;
    }

    public int[] getCurrentPosition() {
        return currentPosition;
    }

    public void setCurrentPosition(int[] currentPosition) {
        this.currentPosition = currentPosition;
    }

    public int getMaxDistance() {
        return maxDistance;
    }

    public int getType() {
        return type;
    }

    int[] operate(EnemyMap map) {
        int deltaL = maxDistance;
        int[] target = findPath(map);
        double distance = Math.sqrt(Math.pow(Math.abs(currentPosition[0] - target[0]), 2) + Math.pow(Math.abs(currentPosition[1] - target[1]), 2));
        if (distance > radiusOfEffect) {
            move(target, map);
            deltaL--;
            operate(map, deltaL);
            return null;
        } else {
            return attack(target, map);
        }
    }

    private int[] operate(EnemyMap map, int deltaL) {
        int[] target = findPath(map);
        double distance = Math.sqrt(Math.pow(Math.abs(currentPosition[0] - target[0]), 2) + Math.pow(Math.abs(currentPosition[1] - target[1]), 2));
        if (distance > radiusOfEffect) {
            if (deltaL > 0) {
                move(target, map);
                deltaL--;
                operate(map, deltaL);
                return null;
            }
        }
        return null;
    }

    enum direction {
        UP, RIGHT, DOWN, LEFT
    }

}

class Archer extends Person {
    Archer() {
        type = 4;
        this.costOfProduction = 60;
        this.hitPower = 10 + level;
        this.level = level;
        this.maxDistance = 2;
        this.timeOfProduction = 10;
        this.radiusOfEffect = 10;
        this.health = 100 + level * 5;
        this.canFly = false;
    }

    @Override
    public int[] getCurrentPosition() {
        return currentPosition;
    }

    @Override
    public int getType() {
        return type;
    }

    @Override
    int[] findPath(EnemyMap map) {
        double minDistance = Math.sqrt(2) * 30;
        int[] target = new int[2];
        target[0] = -1;
        target[1] = -1;
        double distance;
        for (int i = 0; i < map.getDefensiveWeapons().size(); i++) {
            if (8 <= map.getDefensiveWeapons().get(i).getARM_TYPE() && map.getDefensiveWeapons().get(i).getARM_TYPE() <= 14) {
                distance = Math.sqrt(Math.pow(Math.abs(currentPosition[0] - map.getDefensiveWeapons().get(i).getPosition()[0]), 2) + Math.pow(Math.abs(currentPosition[1] - map.getDefensiveWeapons().get(i).getPosition()[1]), 2));
                if (minDistance > distance) {
                    target[0] = map.getDefensiveWeapons().get(i).getPosition()[0];
                    target[1] = map.getDefensiveWeapons().get(i).getPosition()[1];
                    minDistance = distance;
                }
            }
        }
        if (Math.sqrt(2) * 30 != minDistance)
            return target;
        for (int i = 0; i < map.getMapBuildings().size(); i++) {
            {
                distance = Math.sqrt(Math.pow(Math.abs(currentPosition[0] - map.getMapBuildings().get(i).getPosition()[0]), 2) + Math.pow(Math.abs(currentPosition[1] - map.getMapBuildings().get(i).getPosition()[1]), 2));
                if (minDistance > distance) {
                    target[0] = map.getMapBuildings().get(i).getPosition()[0];
                    target[1] = map.getMapBuildings().get(i).getPosition()[1];
                    minDistance = distance;
                }
            }
        }
        return target;
    }

    @Override
    int[] attack(int[] target, EnemyMap map) {
        return new int[]{target[0], target[1], hitPower};
    }

}

class Guardian extends Person {
    Guardian() {
        type = 1;
        this.costOfProduction = 50;
        this.hitPower = 10;
        this.level = 0;
        this.maxDistance = 2;
        this.timeOfProduction = 10;
        this.radiusOfEffect = 1;
        this.health = 100 + level * 5;
        this.canFly = false;
        currentPosition[0] = 10;
        currentPosition[1] = 10;
    }

    @Override
    public int[] getCurrentPosition() {
        return currentPosition;
    }

    @Override
    public int getType() {
        return type;
    }

    @Override
    int[] findPath(EnemyMap map) {
        double minDistance = Math.sqrt(2) * 30;
        int[] target = new int[2];
        target[0] = -1;
        target[1] = -1;
        double distance;
        for (int i = 0; i < map.getDefensiveWeapons().size(); i++) {
            if (8 <= map.getDefensiveWeapons().get(i).getARM_TYPE() && map.getDefensiveWeapons().get(i).getARM_TYPE() <= 14) {
                distance = Math.sqrt(Math.pow(Math.abs(currentPosition[0] - map.getDefensiveWeapons().get(i).getPosition()[0]), 2) + Math.pow(Math.abs(currentPosition[1] - map.getDefensiveWeapons().get(i).getPosition()[1]), 2));
                if (minDistance > distance) {
                    target[0] = map.getDefensiveWeapons().get(i).getPosition()[0];
                    target[1] = map.getDefensiveWeapons().get(i).getPosition()[1];
                    minDistance = distance;
                }
            }
        }
        for (int i = 0; i < map.getMapBuildings().size(); i++) {
            {
                distance = Math.sqrt(Math.pow(Math.abs(currentPosition[0] - map.getMapBuildings().get(i).getPosition()[0]), 2) + Math.pow(Math.abs(currentPosition[1] - map.getMapBuildings().get(i).getPosition()[1]), 2));
                if (minDistance > distance) {
                    target[0] = map.getMapBuildings().get(i).getPosition()[0];
                    target[1] = map.getMapBuildings().get(i).getPosition()[1];
                    minDistance = distance;
                }
            }
        }
        return target;
    }

    @Override
    int[] attack(int[] target, EnemyMap map) {
        return new int[]{target[0], target[1], hitPower};
    }


}

class Giant extends Person {
    Giant() {
        type = 2;
        this.costOfProduction = 125;
        this.hitPower = 30 + level;
        this.level = level;
        this.maxDistance = 3;
        this.timeOfProduction = 30;
        this.radiusOfEffect = 1;
        this.health = 500 + level * 5;
        this.canFly = false;
    }

    @Override
    public int[] getCurrentPosition() {
        return currentPosition;
    }

    @Override
    public int getType() {
        return type;
    }

    @Override
    int[] findPath(EnemyMap map) {
        double minDistance = Math.sqrt(2) * 30;
        int[] target = new int[2];
        target[0] = -1;
        target[1] = -1;
        double distance;
        for (int i = 0; i < map.getBuildings().size(); i++) {
            if (map.getMapBuildings().get(i).getJasonType() <= 4 && map.getMapBuildings().get(i).getJasonType() >= 1) {

                distance = Math.sqrt(Math.pow(Math.abs(currentPosition[0] - map.getMapBuildings().get(i).getPosition()[0]), 2) + Math.pow(Math.abs(currentPosition[1] - map.getMapBuildings().get(i).getPosition()[1]), 2));
                if (minDistance > distance) {
                    target[0] = map.getBuildings().get(i).getX();
                    target[1] = map.getBuildings().get(i).getY();
                    minDistance = distance;
                }
            }
        }
        if (minDistance != Math.sqrt(2) * 30)
            return target;
        for (int i = 0; i < map.getDefensiveWeapons().size(); i++) {
            if (8 <= map.getDefensiveWeapons().get(i).getARM_TYPE() && map.getDefensiveWeapons().get(i).getARM_TYPE() <= 14) {
                distance = Math.sqrt(Math.pow(Math.abs(currentPosition[0] - map.getDefensiveWeapons().get(i).getPosition()[0]), 2) + Math.pow(Math.abs(currentPosition[1] - map.getDefensiveWeapons().get(i).getPosition()[1]), 2));
                if (minDistance > distance) {
                    target[0] = map.getDefensiveWeapons().get(i).getPosition()[0];
                    target[1] = map.getDefensiveWeapons().get(i).getPosition()[1];
                    minDistance = distance;
                }
            }
        }
        for (int i = 0; i < map.getMapBuildings().size(); i++) {
            {
                distance = Math.sqrt(Math.pow(Math.abs(currentPosition[0] - map.getMapBuildings().get(i).getPosition()[0]), 2) + Math.pow(Math.abs(currentPosition[1] - map.getMapBuildings().get(i).getPosition()[1]), 2));
                if (minDistance > distance) {
                    target[0] = map.getMapBuildings().get(i).getPosition()[0];
                    target[1] = map.getMapBuildings().get(i).getPosition()[1];
                    minDistance = distance;
                }
            }
        }
        return target;
    }

    @Override
    int[] attack(int[] target, EnemyMap map) {
        return new int[]{target[0], target[1], hitPower};
    }

}

class Dragon extends Person {
    Dragon() {
        type = 4;
        this.costOfProduction = 175;
        this.hitPower = 30 + level;
        this.level = level;
        this.maxDistance = 6;
        this.timeOfProduction = 40;
        this.radiusOfEffect = 3;
        this.health = 700 + level * 5;
        this.canFly = true;
    }

    @Override
    public int[] getCurrentPosition() {
        return currentPosition;
    }

    @Override
    public int getType() {
        return type;
    }

    @Override
    int[] findPath(EnemyMap map) {
        double minDistance = Math.sqrt(2) * 30;
        int[] target = {-1, -1};
        double distance;
        for (int i = 0; i < map.getDefensiveWeapons().size(); i++) {
            if (8 <= map.getDefensiveWeapons().get(i).getARM_TYPE() && map.getDefensiveWeapons().get(i).getARM_TYPE() <= 14) {
                distance = Math.sqrt(Math.pow(Math.abs(currentPosition[0] - map.getDefensiveWeapons().get(i).getPosition()[0]), 2) + Math.pow(Math.abs(currentPosition[1] - map.getDefensiveWeapons().get(i).getPosition()[1]), 2));
                if (minDistance > distance) {
                    target[0] = map.getDefensiveWeapons().get(i).getPosition()[0];
                    target[1] = map.getDefensiveWeapons().get(i).getPosition()[1];
                    minDistance = distance;
                }
            }
        }
        for (int i = 0; i < map.getMapBuildings().size(); i++) {
            {
                distance = Math.sqrt(Math.pow(Math.abs(currentPosition[0] - map.getMapBuildings().get(i).getPosition()[0]), 2) + Math.pow(Math.abs(currentPosition[1] - map.getMapBuildings().get(i).getPosition()[1]), 2));
                if (minDistance > distance) {
                    target[0] = map.getMapBuildings().get(i).getPosition()[0];
                    target[1] = map.getMapBuildings().get(i).getPosition()[1];
                    minDistance = distance;
                }
            }
        }
        return target;
    }

    @Override
    int[] attack(int[] target, EnemyMap map) {
        return new int[]{target[0], target[1], hitPower};
    }

    @Override
    int[] move(int[] target, EnemyMap map) {
        direction dir = null;
        double distance = Math.sqrt(Math.pow(Math.abs(currentPosition[0] - target[0]), 2) + Math.pow(Math.abs(currentPosition[1] - target[1]), 2));
        if (currentPosition[0] > 0) {
            double newDistance = Math.sqrt(Math.pow(Math.abs(currentPosition[0] - 1 - target[0]), 2) + Math.pow(Math.abs(currentPosition[1] - target[1]), 2));
            if (newDistance < distance)
                dir = direction.UP;
        }
        if (currentPosition[1] > 0) {
            double newDistance = Math.sqrt(Math.pow(Math.abs(currentPosition[0] - target[0]), 2) + Math.pow(Math.abs(currentPosition[1] - 1 - target[1]), 2));
            if (newDistance < distance)
                dir = direction.LEFT;
        }
        if (currentPosition[0] < 30) {
            double newDistance = Math.sqrt(Math.pow(Math.abs(currentPosition[0] + 1 - target[0]), 2) + Math.pow(Math.abs(currentPosition[1] - target[1]), 2));
            if (newDistance < distance)
                dir = direction.DOWN;
        }
        if (currentPosition[1] < 30) {
            double newDistance = Math.sqrt(Math.pow(Math.abs(currentPosition[0] - target[0]), 2) + Math.pow(Math.abs(currentPosition[1] + 1 - target[1]), 2));
            if (newDistance < distance)
                dir = direction.RIGHT;
        }
        if (dir == direction.LEFT)
            currentPosition[1]--;
        if (dir == direction.RIGHT)
            currentPosition[1]++;
        if (dir == direction.UP)
            currentPosition[0]--;
        if (dir == direction.DOWN)
            currentPosition[0]++;
        return null;
    }
}

class Healer extends Person {
    Healer() {
        type = 6;
        this.costOfProduction = 175;
        this.hitPower = 50 + level;
        this.level = level;
        this.maxDistance = 3;
        this.timeOfProduction = 30;
        this.radiusOfEffect = 10;
        this.health = 10;
        this.canFly = true;
    }

    @Override
    int[] findPath(EnemyMap map) {
        return null;
    }

    @Override
    public int[] getCurrentPosition() {
        return currentPosition;
    }

    @Override
    public int getType() {
        return type;
    }

    int[] findPath(Map map) {
        double minDistance = Math.sqrt(2) * 30;
        int[] target = {-1, -1};
        for (int i = 0; i < map.valuableSoldiers.size(); i++) {
            double distance;
            distance = Math.sqrt(Math.pow(Math.abs(currentPosition[0] - map.valuableSoldiers.get(i).currentPosition[0]), 2) + Math.pow(Math.abs(currentPosition[1] - map.valuableSoldiers.get(i).currentPosition[1]), 2));
            if (minDistance > distance) {
                target[0] = map.valuableSoldiers.get(i).currentPosition[0];
                target[1] = map.valuableSoldiers.get(i).currentPosition[1];
                minDistance = distance;
            }
        }
        return target;
    }

    @Override
    int[] attack(int[] target, EnemyMap map) {
        return new int[]{target[0], target[1], hitPower};
    }

    @Override
    int[] move(int[] target, EnemyMap map) {

        direction dir = null;
        double distance = Math.sqrt(Math.pow(Math.abs(currentPosition[0] - target[0]), 2) + Math.pow(Math.abs(currentPosition[1] - target[1]), 2));
        if (currentPosition[0] > 0) {
            double newDistance = Math.sqrt(Math.pow(Math.abs(currentPosition[0] - 1 - target[0]), 2) + Math.pow(Math.abs(currentPosition[1] - target[1]), 2));
            if (newDistance < distance)
                dir = direction.UP;
        }
        if (currentPosition[1] > 0) {
            double newDistance = Math.sqrt(Math.pow(Math.abs(currentPosition[0] - target[0]), 2) + Math.pow(Math.abs(currentPosition[1] - 1 - target[1]), 2));
            if (newDistance < distance)
                dir = direction.LEFT;
        }
        if (currentPosition[0] < 30) {
            double newDistance = Math.sqrt(Math.pow(Math.abs(currentPosition[0] + 1 - target[0]), 2) + Math.pow(Math.abs(currentPosition[1] - target[1]), 2));
            if (newDistance < distance)
                dir = direction.DOWN;
        }
        if (currentPosition[1] < 30) {
            double newDistance = Math.sqrt(Math.pow(Math.abs(currentPosition[0] - target[0]), 2) + Math.pow(Math.abs(currentPosition[1] + 1 - target[1]), 2));
            if (newDistance < distance)
                dir = direction.RIGHT;
        }
        if (dir == direction.LEFT)
            currentPosition[1]--;
        if (dir == direction.RIGHT)
            currentPosition[1]++;
        if (dir == direction.UP)
            currentPosition[0]--;
        if (dir == direction.DOWN)
            currentPosition[0]++;
        return null;
    }

    public void loseHealth() {
        this.health--;
    }
}
