import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.util.ArrayList;

public class AttackThread implements Runnable {
    private Scene scene;
    private Stage stage ;
    private boolean end = false;
    private AttackGUI attackGUI ;
    private EnemyMap enemyMap ;
    private Game currentGame ;

    @Override
    public void run() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        while(!end) {
                attack();
                if(attackGUI != null) {
                    attackGUI.updateSoldiers();
                     attackGUI.updateBuildings();
                     attackGUI.setGameStatus();
                     attackGUI.setScore();
                    attackGUI.setSoldierStatus();
            }
            try {
                Thread.sleep(1000);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }




    }

    private void attack() {
        soldiersAttack(currentGame, enemyMap);
        defensiveBuildingsAttack(currentGame, enemyMap);
        if (gameFinished(enemyMap, currentGame))
            endAttack(currentGame);
    }

    AttackThread (Game currentGame , EnemyMap enemyMap , Stage stage , Scene scene ){
        this.scene = scene;
        this.stage = stage;
        this.enemyMap = enemyMap ;
        this.currentGame = currentGame ;
    }

    public boolean getEnd (){
        return end;
    }

    public void setAttackGUI(AttackGUI attackGUI) {
        this.attackGUI = attackGUI;
    }

    public EnemyMap getEnemyMap() {
        return enemyMap;
    }


    public Game getCurrentGame() {
        return currentGame;
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    private void soldiersAttack(Game currentGame, EnemyMap enemyMap) {
        for (Person person : currentGame.getOwnMap().valuableSoldiers){
            if (person.getType() != 6 && person.getInEnemyMap()) {
                int[] target = person.operate(enemyMap);
                if (target != null)
                    hitEnemyBuilding(target, enemyMap);
                updateAttackMap(currentGame.getOwnMap().valuableSoldiers, enemyMap, currentGame.getOwnMap(), currentGame);
            }
            if(person.getType() == 6 && person.getInEnemyMap()){
                int [] target =  person.operate(currentGame.getOwnMap() , enemyMap);
                if(target != null){
                    Healer healer =(Healer) person ;
                    healSoldiers ( target ,  healer.getHitPower() ,  currentGame.getOwnMap());
                    ((Healer) person).loseHealth();
                }
                updateAttackMap(currentGame.getOwnMap().valuableSoldiers, enemyMap, currentGame.getOwnMap(), currentGame);
            }
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
            if (soldiers.get(index).getHealth() <= 0) {
                for (int indexPrime = 0; indexPrime < game.getOwnMap().soldiers.size(); indexPrime++)
                    if (game.getOwnMap().soldiers.get(indexPrime).getType() == soldiers.get(index).getType()) {
                        removeFromCamp(game, game.getOwnMap().soldiers.get(indexPrime).getType());
                        game.getOwnMap().soldiers.remove(indexPrime);
                        break;
                    }
                soldiers.remove(index);
            }
        for (int index = 0; index < enemyMap.getMapBuildings().size(); index++)
            if (enemyMap.getMapBuildings().get(index).getResistance() <= 0) {
                if (enemyMap.getMapBuildings().get(index).getJasonType() == 3) {
                    GoldStorage goldStorage = (GoldStorage) enemyMap.getMapBuildings().get(index);
                    game.addWonGold(goldStorage.getGoldStored());
                    System.out.println(game.wonGold+"gggg");
                    goldStorage.addGold(-goldStorage.getGoldStored());
                }
                if (enemyMap.getMapBuildings().get(index).getJasonType() == 4) {
                    ElixirStorage elixirStorage = (ElixirStorage) enemyMap.getMapBuildings().get(index);
                    game.addWonElixir(elixirStorage.getElixirStored());
                    elixirStorage.addElixir(-elixirStorage.getElixirStored());
                }
                game.addWonScore(enemyMap.getMapBuildings().get(index).getDestructionScore());
                empty(enemyMap, enemyMap.getMapBuildings().get(index).getPosition()[0], enemyMap.getMapBuildings().get(index).getPosition()[1]);
                attackGUI.removeBuilding(enemyMap.getMapBuildings().get(index));
                enemyMap.getMapBuildings().remove(index);
            }
        for (int index = 0; index < enemyMap.getDefensiveWeapons().size(); index++)
            if (enemyMap.getDefensiveWeapons().get(index).getResistence() <= 0) {
                game.addWonScore(enemyMap.getDefensiveWeapons().get(index).getSCORE());
                empty(enemyMap, enemyMap.getDefensiveWeapons().get(index).getPosition()[0], enemyMap.getDefensiveWeapons().get(index).getPosition()[1]);
                attackGUI.removeBuilding(enemyMap.getDefensiveWeapons().get(index));
                enemyMap.getDefensiveWeapons().remove(index);
            }
    }

    private void empty(EnemyMap enemyMap, int x, int y) {
        enemyMap.getMap()[x][y].setEmpty(true);
    }

    private void removeFromCamp(Game game, int type) {
        for (int index = 0, i = 0; index < game.ownMap.camps.size(); index++) {
            for (int indexprime = 0; index < game.ownMap.camps.get(index).getCampUnits().size(); indexprime++)
                if (game.ownMap.camps.get(index).getCampUnits().get(indexprime).getType() == type) {
                    game.ownMap.camps.get(index).getCampUnits().remove(indexprime);
                    return;
                }
        }
    }
    private void healSoldiers(int [] target , int healPower  , Map map) {
        int X = target[0];
        int Y = target[1];
        for (Person person : map.valuableSoldiers) {
            if (person.getInEnemyMap()) {
                if (person.getCurrentPosition() == new int[]{X, Y})
                    person.loseHealth(-1 * healPower);

            }

        }
    }
    private void defensiveBuildingsAttack(Game currentGame, EnemyMap enemyMap) {
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
    }
    void updateAttackThread(int currentSoldierType, int x, int y) {
        for(int i = 0 ; i < this.currentGame.getOwnMap().valuableSoldiers.size() ; i++)
            if(this.currentGame.getOwnMap().valuableSoldiers.get(i).getType() == currentSoldierType)
                if(!this.currentGame.getOwnMap().valuableSoldiers.get(i).getInEnemyMap()){
                    this.currentGame.getOwnMap().valuableSoldiers.get(i).setInEnemyMap(true);
                    this.currentGame.getOwnMap().valuableSoldiers.get(i).setCurrentPosition(new int[] {x,y});
                    break;
                }
    }
    private boolean gameFinished(EnemyMap enemyMap, Game game) {
        if (game.ownMap.valuableSoldiers.size() == 0)
            return true;
        int num = 0;
       /* for (Person person : game.ownMap.valuableSoldiers)
            if (person.getInEnemyMap())
                num++;
        if (num == 0) {
            return true;
        }*/
        if (enemyMap.getMapBuildings().size() == 0 && enemyMap.getDefensiveWeapons().size() == 0)
            return true;
        return false;
    }
    private void endAttack(Game currentGame) {
        currentGame.finishGame();
        end = true;
    }
}
