import java.util.ArrayList;

public class Game {
    int gameID;
    Map ownMap = new Map();
    Map enemyMap;
    int wonElixir = 0;
    int wonGold = 0;
    int wonScore = 0;

    public int getWonScore() {
        return wonScore;
    }

    public void addWonScore(int wonScore) {
        this.wonScore += wonScore;
    }

    public Game(int gameID) {
        this.gameID = gameID;
    }

    public Map getOwnMap() {
        return ownMap;
    }

    public Building findBuildingTypeInOwnMap(int type, int id) {
        for (int i = 0; i < ownMap.getBuildings().size(); i++) {
            if (ownMap.getBuildings().get(i).getJasonType() == type) {
                if (ownMap.getBuildings().get(i).getId() == id)
                    return ownMap.getBuildings().get(i);
            }
        }
        return null;
    }

    public DefensiveWeapon findDefensiveWeaponTypeInOwnMap(int type, int id) {
        for (int i = 0; i < ownMap.getDefensiveWeapons().size(); i++) {
            if (ownMap.getDefensiveWeapons().get(i).getARM_TYPE() == type && id != 0) {
                if (ownMap.getDefensiveWeapons().get(i).getId() == id)
                    return ownMap.getDefensiveWeapons().get(i);
            }
        }
        return null;
    }

    public int[] getOwnResources() {
        int[] resources = {0, 0};
        for (int i = 0; i < ownMap.getGoldStorages().size(); i++) {
            resources[0] += ownMap.getGoldStorages().get(i).getGoldStored();
        }
        for (int i = 0; i < ownMap.getElixirStorages().size(); i++) {
            resources[1] += ownMap.getElixirStorages().get(i).getElixirStored();
        }
        return resources;
    }

    public int[] getStoragesCapacity() {
        int capacity[] = new int[2];
        for (int i = 0; i < ownMap.getGoldStorages().size(); i++) {
            capacity[0] += ownMap.getGoldStorages().get(i).getGoldStored();
        }
        for (int i = 0; i < ownMap.getElixirStorages().size(); i++) {
            capacity[1] += ownMap.getElixirStorages().get(i).getElixirStored();
        }
        return capacity;
    }

    public int getOwnScore() {
        return wonScore;
    }

    public int upgradeOwnBuilding(Building building) {
        if (getOwnResources()[0] >= building.getCostOfUpgrade()[0]) {
            if (building.getLevel() + 1 > ownMap.getMainBuilding().getLevel()) {
                return -2;
            }
            if (building.getLevel() + 1 <= ownMap.getMainBuilding().getLevel()) {
                ownMap.addToQueueOfUpgrade(building.getJasonType(), building.getId());
                ownMap.mainBuilding.reduceFreeWorker();
                int availableGold = getOwnResources()[0] - building.getCostOfUpgrade()[0];
                for (int i = 0; i < ownMap.getGoldStorages().size(); i++) {
                    ownMap.getGoldStorages().get(i).setGoldStored(0);
                }
                for (int i = 0; i < ownMap.getGoldStorages().size(); i++) {
                    while (ownMap.getGoldStorages().get(i).getFreeSpace() != 0 && availableGold != 0) {
                        ownMap.getGoldStorages().get(i).addGold(1);
                        availableGold--;
                    }
                }
                return 0;
            }
        }
        return -1;
    }

    public int upgradeOwnDefensiveBuilding(DefensiveWeapon defensiveWeapon) {
        if (getOwnResources()[0] >= defensiveWeapon.getCOST_OF_UPGRADE()[0]) {
            if (defensiveWeapon.getLevel() + 1 > ownMap.getMainBuilding().getLevel()) {
                return -2;
            }
            if (defensiveWeapon.getLevel() + 1 <= ownMap.getMainBuilding().getLevel()) {
                ownMap.addToQueueOfUpgrade(defensiveWeapon.getARM_TYPE(), defensiveWeapon.getId());
                int availableGold = getOwnResources()[0] - defensiveWeapon.getCOST_OF_UPGRADE()[0];
                for (int i = 0; i < ownMap.getGoldStorages().size(); i++) {
                    ownMap.getGoldStorages().get(i).setGoldStored(0);
                }
                for (int i = 0; i < ownMap.getGoldStorages().size(); i++) {
                    while (ownMap.getGoldStorages().get(i).getFreeSpace() != 0 && availableGold != 0) {
                        ownMap.getGoldStorages().get(i).addGold(1);
                        availableGold--;
                    }
                }
                return 0;
            }
        }
        return -1;
    }

    public ArrayList<Integer> availableBuildingsAndDefensiveWeapons() {
        ArrayList<Integer> availableTypes = new ArrayList<>();
        if (getOwnResources()[0] >= 150 && getOwnResources()[1] >= 5)
            availableTypes.add(1);

        if (getOwnResources()[0] >= 100 && getOwnResources()[1] >= 3)
            availableTypes.add(2);

        if (getOwnResources()[0] >= 200) {
            availableTypes.add(3);
            availableTypes.add(4);
            availableTypes.add(6);
            availableTypes.add(7);
        }
        if (getOwnResources()[0] >= 300) {
            availableTypes.add(8);
            availableTypes.add(10);
        }
        if (getOwnResources()[0] >= 400)
            availableTypes.add(9);

        if (getOwnResources()[0] >= 500)
            availableTypes.add(11);
        return availableTypes;
    }

    public ArrayList<DefensiveWeapon> getOwnDefensiveWeapon() {
        return ownMap.getDefensiveWeapons();
    }

    public int constructionRequest(int buildingType) {
        for (int i = 0; i < availableBuildingsAndDefensiveWeapons().size(); i++) {
            if (availableBuildingsAndDefensiveWeapons().get(i) == buildingType)
                if (ownMap.getMainBuilding().getFreeWorker() >= 1)
                    return 0;
                else
                    return -2;
        }
        return -1;
    }

    public int[] getCostOfConstruction(int type) {
        int costOfConstruction[] = new int[2];

        switch (type) {
            case 1: {
                costOfConstruction[0] = 150;
                costOfConstruction[1] = 5;
            }
            break;

            case 2: {
                costOfConstruction[0] = 100;
                costOfConstruction[1] = 3;
            }
            break;

            case 3: {
                costOfConstruction[0] = 200;
                costOfConstruction[1] = 0;
            }
            break;
            case 4: {
                costOfConstruction[0] = 200;
                costOfConstruction[1] = 0;
            }
            break;
            case 5: {
                costOfConstruction[0] = 200;
                costOfConstruction[1] = 0;
            }
            break;
            case 6: {
                costOfConstruction[0] = 200;
                costOfConstruction[1] = 0;
            }
            break;
            case 7: {
                costOfConstruction[0] = 200;
                costOfConstruction[1] = 0;
            }
            break;
            case 8: {
                costOfConstruction[0] = 300;
                costOfConstruction[1] = 0;
            }
            break;
            case 9: {
                costOfConstruction[0] = 400;
                costOfConstruction[1] = 0;
            }
            break;
            case 10: {
                costOfConstruction[0] = 300;
                costOfConstruction[1] = 0;
            }
            break;
            case 11: {
                costOfConstruction[0] = 500;
                costOfConstruction[1] = 0;
            }
            break;
            case 12: {
                costOfConstruction[0] = 100;
                costOfConstruction[1] = 0;
            }
            break;
            case 13: {
                costOfConstruction[0] = 100;
                costOfConstruction[1] = 0;
            }
            break;
            case 14: {
                costOfConstruction[0] = 10000;
                costOfConstruction[1] = 0;
            }
            break;
        }
        return costOfConstruction;
    }

    public int[] getCostOfUpgrade(int type) {
        int costOfUpgrade[] = new int[2];

        switch (type) {
            case 1: {
                costOfUpgrade[0] = 150;
                costOfUpgrade[1] = 5;
            }
            break;

            case 2: {
                costOfUpgrade[0] = 100;
                costOfUpgrade[1] = 3;
            }
            break;

            case 3: {
                costOfUpgrade[0] = 200;
                costOfUpgrade[1] = 0;
            }
            break;
            case 4: {
                costOfUpgrade[0] = 200;
                costOfUpgrade[1] = 0;
            }
            break;
            case 5: {
                costOfUpgrade[0] = 200;
                costOfUpgrade[1] = 0;
            }
            break;
            case 6: {
                costOfUpgrade[0] = 200;
                costOfUpgrade[1] = 0;
            }
            break;
            case 7: {
                costOfUpgrade[0] = 200;
                costOfUpgrade[1] = 0;
            }
            break;
            case 8: {
                costOfUpgrade[0] = 300;
                costOfUpgrade[1] = 0;
            }
            break;
            case 9: {
                costOfUpgrade[0] = 400;
                costOfUpgrade[1] = 0;
            }
            break;
            case 10: {
                costOfUpgrade[0] = 300;
                costOfUpgrade[1] = 0;
            }
            break;
            case 11: {
                costOfUpgrade[0] = 500;
                costOfUpgrade[1] = 0;
            }
            break;
            case 12: {
                costOfUpgrade[0] = 100;
                costOfUpgrade[1] = 0;
            }
            break;
            case 13: {
                costOfUpgrade[0] = 100;
                costOfUpgrade[1] = 0;
            }
            break;
            case 14: {
                costOfUpgrade[0] = 10000;
                costOfUpgrade[1] = 0;
            }
            break;
        }
        return costOfUpgrade;
    }

    public int checkePossibilityOfBuildingSoldierType(int soldierType) {
        switch (soldierType) {
            case 1: {
                if (getOwnResources()[1] >= 50)
                    return 0;
                else
                    return -1;
            }
            case 2: {
                if (getOwnResources()[1] >= 125)
                    return 0;
                else
                    return -1;
            }
            case 3: {
                if (getOwnResources()[1] >= 175)
                    return 0;
                else
                    return -1;
            }
            case 4: {
                if (getOwnResources()[1] >= 60)
                    return 0;
                else
                    return -1;
            }
            case 5: {
                if (getOwnResources()[1] >= 60)
                    return 0;
                else
                    return -1;
            }
            case 6: {
                if (getOwnResources()[1] >= 175)
                    return 0;
                else
                    return -1;
            }
        }
        return -2;
    }


    public ArrayList<int[]> getPotentialSoldiers(Barracks barracks) {
        int temp[] = new int[2];
        ArrayList<int[]> potentialSoldiers = new ArrayList<>();

        //forType1
        int[] temp1 = new int[2];
        temp1[0] = 1;
        temp1[1] = getOwnResources()[1] / 50;
        potentialSoldiers.add(temp1);

        //forType2
        int[] temp2 = new int[2];
        temp2[0] = 2;
        temp2[1] = getOwnResources()[1] / 125;
        potentialSoldiers.add(temp2);

        //forType3
        int[] temp3 = new int[2];
        if (barracks.getLevel() >= 2) {
            temp3[0] = 3;
            temp3[1] = getOwnResources()[1] / 175;
        } else {
            temp3[0] = 3;
            temp3[1] = 0;
        }
        potentialSoldiers.add(temp3);

        //forType4
        int[] temp4 = new int[2];
        temp4[0] = 4;
        temp4[1] = getOwnResources()[1] / 60;
        potentialSoldiers.add(temp4);
        return potentialSoldiers;
    }

    public int soldierMaker(int soldierType, int num, Barracks barracks) {

        int availableElixir = 0;

        switch (soldierType) {
            case 1: {
                if (num <= getPotentialSoldiers(barracks).get(0)[1]) {
                    barracks.newSoldier(1, num);
                    availableElixir = getOwnResources()[1] - num * 50;
                    for (int i = 0; i < ownMap.getElixirStorages().size(); i++) {
                        ownMap.getElixirStorages().get(i).setElixirStored(0);
                    }
                    for (int i = 0; i < ownMap.getElixirStorages().size(); i++) {
                        while (ownMap.getElixirStorages().get(i).getFreeSpace() != 0 && availableElixir != 0) {
                            ownMap.getElixirStorages().get(i).addElixir(1);
                            availableElixir--;
                        }
                    }
                    return 0;
                } else
                    return -1;
            }
            case 2: {
                if (num <= getPotentialSoldiers(barracks).get(1)[1]) {
                    barracks.newSoldier(2, num);
                    availableElixir = getOwnResources()[1] - num * 125;
                    for (int i = 0; i < ownMap.getElixirStorages().size(); i++) {
                        ownMap.getElixirStorages().get(i).setElixirStored(0);
                    }
                    for (int i = 0; i < ownMap.getElixirStorages().size(); i++) {
                        while (ownMap.getElixirStorages().get(i).getFreeSpace() != 0 && availableElixir != 0) {
                            ownMap.getElixirStorages().get(i).addElixir(1);
                            availableElixir--;
                        }
                    }
                    return 0;
                }
                return -1;
            }

            case 3: {
                if (num <= getPotentialSoldiers(barracks).get(2)[1]) {
                    barracks.newSoldier(3, num);
                    availableElixir = getOwnResources()[1] - num * 175;
                    for (int i = 0; i < ownMap.getElixirStorages().size(); i++) {
                        ownMap.getElixirStorages().get(i).setElixirStored(0);
                    }
                    for (int i = 0; i < ownMap.getElixirStorages().size(); i++) {
                        while (ownMap.getElixirStorages().get(i).getFreeSpace() != 0 && availableElixir != 0) {
                            ownMap.getElixirStorages().get(i).addElixir(1);
                            availableElixir--;
                        }
                    }
                    return 0;
                }
                return -1;
            }
            case 4: {

                if (num <= getPotentialSoldiers(barracks).get(3)[1]) {
                    barracks.newSoldier(4, num);
                    availableElixir = getOwnResources()[1] - num * 60;
                    for (int i = 0; i < ownMap.getElixirStorages().size(); i++) {
                        ownMap.getElixirStorages().get(i).setElixirStored(0);
                    }
                    for (int i = 0; i < ownMap.getElixirStorages().size(); i++) {
                        while (ownMap.getElixirStorages().get(i).getFreeSpace() != 0 && availableElixir != 0) {
                            ownMap.getElixirStorages().get(i).addElixir(1);
                            availableElixir--;
                        }
                    }
                    return 0;
                }
                return -1;
            }
        }
        return -2;
    }

    public int[] getSoldiersAndCapacityOfCamps() {
        int soldiersNumberAndCampsCapacity[] = new int[2];

        for (int i = 0; i < ownMap.getCamps().size(); i++) {
            soldiersNumberAndCampsCapacity[0] += ownMap.getCamps().get(i).getCampUnits().size();
        }
        soldiersNumberAndCampsCapacity[1] = ownMap.getCamps().size() * 50;
        return soldiersNumberAndCampsCapacity;
    }

    public int[] getSoldiersOfCamps() {
        int allSoldiers[] = new int[6];

        for (int i = 0; i < ownMap.getCamps().size(); i++) {
            for (int j = 0; j < ownMap.getCamps().get(i).getCampUnits().size(); j++) {
                switch (ownMap.getCamps().get(i).getCampUnits().get(j).getType()) {
                    case 1: {
                        allSoldiers[0] += 1;
                    }
                    break;
                    case 2: {
                        allSoldiers[1] += 1;
                    }
                    break;
                    case 3: {
                        allSoldiers[2] += 1;
                    }
                    break;
                    case 4: {
                        allSoldiers[3] += 1;
                    }
                    break;
                }
            }
        }

        return allSoldiers;
    }

    public int[][] getGoldAndElixirStorageAndCapacity() {
        int[][] GoldElixirStorageCapacity = new int[2][2];
        GoldElixirStorageCapacity[0][0] = getOwnResources()[0];
        GoldElixirStorageCapacity[0][1] = getStoragesCapacity()[0];
        GoldElixirStorageCapacity[1][0] = getOwnResources()[1];
        GoldElixirStorageCapacity[1][1] = getStoragesCapacity()[1];
        return GoldElixirStorageCapacity;
    }

    public int[][] statusResourcesOfEnemy(EnemyMap enemyMap) {
        int remainingGold = 0;
        int remainingElixir = 0;
        int[][] r = new int[2][2];
        for (int index = 0; index < enemyMap.getMapBuildings().size(); index++) {
            if (enemyMap.getMapBuildings().get(index).getJasonType() == 3) {
                GoldStorage goldStorage = (GoldStorage) enemyMap.getMapBuildings().get(index);
                remainingGold += goldStorage.getGoldStored();
            }
            if (enemyMap.getMapBuildings().get(index).getJasonType() == 4) {
                ElixirStorage elixirStorage = (ElixirStorage) enemyMap.getMapBuildings().get(index);
                remainingElixir += elixirStorage.getElixirStored();
            }
        }

        r[0][0] = wonGold;
        r[0][1] = remainingGold;
        r[1][0] = wonElixir;
        r[1][1] = remainingElixir;

        return r;
    }

    public ArrayList<Person> statusUnitAttackMode(int soldierType) {
        ArrayList<Person> soldiers = new ArrayList<>();
        for (int i = 0; i < ownMap.valuableSoldiers.size(); i++)
            if (soldierType == ownMap.valuableSoldiers.get(i).type)
                soldiers.add(ownMap.valuableSoldiers.get(i));

        return soldiers;
    }

    public ArrayList<Person> statusUnitsAttackMode() {
        ArrayList<Person> soldiers = new ArrayList<>();
        for (int i = 0; i < ownMap.valuableSoldiers.size(); i++)
            soldiers.add(ownMap.valuableSoldiers.get(i));

        return soldiers;
    }

    public ArrayList<DefensiveWeapon> statusDefensiveWeaponAttackMode(int defensiveWeaponType, EnemyMap enemyMap) {
        ArrayList<DefensiveWeapon> defensiveWeapons = new ArrayList<>();
        for (int index = 0; index < enemyMap.getDefensiveWeapons().size(); index++)
            if (enemyMap.getDefensiveWeapons().get(index).getARM_TYPE() == defensiveWeaponType)
                defensiveWeapons.add(enemyMap.getDefensiveWeapons().get(index));

        return defensiveWeapons;
    }

    public ArrayList<Building> statusBuildingAttackMode(int buildingType, EnemyMap enemyMap) {
        ArrayList<Building> buildings = new ArrayList<>();
        for (int index = 0; index < enemyMap.getDefensiveWeapons().size(); index++)
            if (enemyMap.getMapBuildings().get(index).getJasonType() == buildingType)
                buildings.add(enemyMap.getMapBuildings().get(index));
        return buildings;
    }

    public ArrayList<DefensiveWeapon> statusDefensiveWeaponsAttackMode(EnemyMap enemyMap) {
        ArrayList<DefensiveWeapon> defensiveWeapons = new ArrayList<>();
        for (int index = 0; index < enemyMap.getDefensiveWeapons().size(); index++)
            defensiveWeapons.add(enemyMap.getDefensiveWeapons().get(index));
        return defensiveWeapons;
    }

    public ArrayList<Building> statusBuildingsAttackMode(EnemyMap enemyMap) {
        ArrayList<Building> buildings = new ArrayList<>();
        for (int index = 0; index < enemyMap.getDefensiveWeapons().size(); index++)
            buildings.add(enemyMap.getMapBuildings().get(index));
        return buildings;
    }

    public ArrayList<int[]> getQueueOfSoldiers(Barracks b) {
        return b.queueOfSoldiersToBeBuilt;
    }

    public ArrayList<int[]> getQueueOfBuildingsAndDefensiveWeaponsToBEBuilt() {
        return ownMap.queueOfBuildingsToBeBuilt;
    }


    public int selectUnit(int[] unitTypeNum) {
        int numberOfSoldier = 0;
        for (int index = 0; index < ownMap.soldiers.size(); index++)
            if (ownMap.soldiers.get(index).getType() == unitTypeNum[0]) {
                numberOfSoldier++;
            }
        if (numberOfSoldier < unitTypeNum[1])
            return 1;

        if (numberOfSoldier < unitTypeNum[1])
            return 1;

        for (int index = 0, num = 0; index < ownMap.soldiers.size() && num < unitTypeNum[1]; index++)
            if (ownMap.soldiers.get(index).getType() == unitTypeNum[0]) {
                ownMap.valuableSoldiers.add(ownMap.soldiers.get(index));
                num++;
            }

        return 0;
    }

    public int putSoldier(int soldierType, int index, int x, int y, EnemyMap enemyMap) {
        int num = 0;
        for (int indexPrime = 0; indexPrime < ownMap.valuableSoldiers.size(); indexPrime++) {
            if (ownMap.valuableSoldiers.get(indexPrime).currentPosition == new int[]{x, y})
                num++;
        }
        if (num + index > 5) {
            return 1;
        }
        int[] position = new int[]{x, y};
        int[] size = enemyMap.getSize();
        num = 0;
        if (position[0] == 0 && position[1] == 0)
            num++;
        if (position[0] == 0 && position[1] == size[1] - 1)
            num++;
        if (position[0] == size[0] - 1 && position[1] == 0)
            num++;
        if (position[0] == size[0] - 1 && position[1] == size[1] - 1)
            num++;
        if (num == 0) {
            return 1;
        }
        num = 0;
        for (int indexPrime = 0; num < index && indexPrime < ownMap.valuableSoldiers.size(); indexPrime++)
            if (ownMap.valuableSoldiers.get(indexPrime).getType() == soldierType && !ownMap.valuableSoldiers.get(indexPrime).getInEnemyMap()) {
                ownMap.valuableSoldiers.get(indexPrime).setCurrentPosition(new int[]{x, y});
                ownMap.valuableSoldiers.get(indexPrime).setInEnemyMap(true);
                num++;
            }
        return 0;
    }

    public int upgradeTownHall() {
        if (getOwnResources()[0] >= ownMap.getMainBuilding().getCostOfUpgrade()[0]) {
            ownMap.addToQueueOfUpgrade(5, 0);
            ownMap.mainBuilding.reduceFreeWorker();
            int availableGold = getOwnResources()[0] - ownMap.mainBuilding.getCostOfUpgrade()[0];
            for (int i = 0; i < ownMap.getGoldStorages().size(); i++) {
                ownMap.getGoldStorages().get(i).setGoldStored(0);
            }
            for (int i = 0; i < ownMap.getGoldStorages().size(); i++) {
                while (ownMap.getGoldStorages().get(i).getFreeSpace() != 0 && availableGold != 0) {
                    ownMap.getGoldStorages().get(i).addGold(1);
                    availableGold--;
                }
            }
        } else
            return -1;
        return 0;
    }

    public void finishGame() {

        for (int index = 0; index < ownMap.valuableSoldiers.size(); index++) {
            ownMap.valuableSoldiers.remove(index);
        }

        for (int index = 0; index < ownMap.soldiers.size(); index++) {
            System.out.println(ownMap.soldiers.size());
            if (ownMap.soldiers.get(index).getHealth() <= 0) {
                ownMap.valuableSoldiers.remove(index);
            }
        }
        addResources(getWonGold(), getWonElixir());
        ownMap.getMainBuilding().addScore(wonScore);
        addWonScore(-getWonScore());
        addWonElixir(-getWonElixir());
        addWonGold(-getWonGold());
    }

    private void addResources(int gold, int elixir) {

        for (int i = 0; i < this.ownMap.goldStorages.size(); i++) {
            while (this.ownMap.goldStorages.get(i).getFreeSpace() != 0 && gold != 0) {
                this.ownMap.goldStorages.get(i).addGold(1);
                gold--;
            }
        }

        for (int i = 0; i < this.ownMap.elixirStorages.size(); i++) {
            while (this.ownMap.elixirStorages.get(i).getFreeSpace() != 0 && elixir != 0) {
                this.ownMap.elixirStorages.get(i).addElixir(1);
                elixir--;
            }
        }

    }

    public Building getOwnTownHall() {
        return ownMap.getMainBuilding();
    }

    public int newBuildingMaker(int type, int x, int y) {
        if (x >= 30 && y >= 30)
            return -1;
        if (ownMap.getMap()[x][y].isEmpty() && ownMap.getMap()[x][y].isConstructable()) {
            ownMap.addToQueue(type, x, y);
            ownMap.getMap()[x][y].setEmpty(false);
            int availableGold = getOwnResources()[0] - getCostOfUpgrade(type)[0];
            for (int i = 0; i < ownMap.getGoldStorages().size(); i++) {
                ownMap.getGoldStorages().get(i).setGoldStored(0);
            }
            for (int i = 0; i < ownMap.getGoldStorages().size(); i++) {
                while (ownMap.getGoldStorages().get(i).getFreeSpace() != 0 && availableGold != 0) {
                    ownMap.getGoldStorages().get(i).addGold(1);
                    availableGold--;
                }
            }
            ownMap.mainBuilding.reduceFreeWorker();
            return 0;
        }
        return -1;
    }

    public int getWonGold() {
        return wonGold;
    }

    public int getWonElixir() {
        return wonElixir;
    }

    public void addWonGold(int wonGold) {
        this.wonGold += wonGold;
    }

    public void addWonElixir(int wonElixir) {
        this.wonElixir += wonElixir;
    }

    public void turnTimeOwnMap(int timePassed) {
        for (int i = 0; i < timePassed; i++) {
            ownMap.updateQueueOfUpgrade();
            ownMap.updateQueue();
        }
        ownMap.updateBuldings(timePassed);
    }
}