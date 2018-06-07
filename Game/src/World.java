import com.google.gson.Gson;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.regex.Pattern;

public class World {
    ArrayList<Game> games = new ArrayList<>();
    ArrayList<Map> maps = new ArrayList<>();
    ArrayList<EnemyMap> enemyMaps = new ArrayList<>();
    Game currentGame = null;
    EnemyMap currentEnemy = null;

    public World() {

    }

    public void addEnemyMap(EnemyMap enemyMap) {
        if (enemyMap != null) {
            if (enemyMap.getName() == null)
                enemyMap.setName("enemy map " + Integer.toString(maps.size() + 1));
        }
        enemyMaps.add(enemyMap);
    }

    public ArrayList<EnemyMap> getEnemyMaps() {
        return enemyMaps;
    }

    public int loadEnemyMap(String fileName) {

        try {
            EnemyMap game = new EnemyMap();

            Gson g = new Gson();
            game = g.fromJson(new FileReader(fileName), EnemyMap.class);
            game.initializeMap();
            addEnemyMap(game);

            return 0;
        } catch (FileNotFoundException e) {
            return -1;
        }
    }

    public int saveGame(String addrress, World world) {
        try {
            File f = new File(addrress);
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(f));
            Gson gson = new Gson();
            String x = gson.toJson(world);
            bufferedWriter.write(x);
            bufferedWriter.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public Game newGameMaker() {
        Game newGame = new Game(games.size());
        games.add(newGame);
        currentGame = newGame;

        return newGame;
    }

    public World loadGame(String address, GraphicView v) {
        try {
            String text = new String(Files.readAllBytes((Paths.get(address))), StandardCharsets.UTF_8);
            Gson gson = new Gson();
            World world1 = gson.fromJson(text, World.class);

            world1.games.get(0).ownMap.buildings.clear();
            world1.games.get(0).ownMap.soldiers.clear();
            world1.games.get(0).ownMap.defensiveWeapons.clear();
            world1.games.get(0).ownMap.buildings.add(world1.games.get(0).ownMap.mainBuilding);
            world1.games.get(0).ownMap.buildings.addAll(world1.games.get(0).ownMap.goldStorages);
            world1.games.get(0).ownMap.buildings.addAll(world1.games.get(0).ownMap.elixirStorages);
            world1.games.get(0).ownMap.buildings.addAll(world1.games.get(0).ownMap.camps);
            world1.games.get(0).ownMap.buildings.addAll(world1.games.get(0).ownMap.elixirMines);
            world1.games.get(0).ownMap.buildings.addAll(world1.games.get(0).ownMap.goldMines);
            world1.games.get(0).ownMap.buildings.addAll(world1.games.get(0).ownMap.barracks);

            world1.games.get(0).ownMap.defensiveWeapons.addAll(world1.games.get(0).ownMap.wizardTowers);
            world1.games.get(0).ownMap.defensiveWeapons.addAll(world1.games.get(0).ownMap.airDefenses);
            world1.games.get(0).ownMap.defensiveWeapons.addAll(world1.games.get(0).ownMap.archerTowers);
            world1.games.get(0).ownMap.defensiveWeapons.addAll(world1.games.get(0).ownMap.cannons);

            world1.games.get(0).ownMap.soldiers.addAll(world1.games.get(0).ownMap.guardians);
            world1.games.get(0).ownMap.soldiers.addAll(world1.games.get(0).ownMap.giants);
            world1.games.get(0).ownMap.soldiers.addAll(world1.games.get(0).ownMap.dragons);
            world1.games.get(0).ownMap.soldiers.addAll(world1.games.get(0).ownMap.archers);


            v.setUpVillageMenuScene();
//            v.setUpVillageMenu();
            return world1;
        } catch (IOException e) {
            System.out.println("NO VALID FILE");
            v.setUpNoValidAddressErr();
        }
        // returns -1 in case there is no valid address
        v.setUpInitialMenuScene();
//        v.setUpInitialMenu();
        return this;
//      currentGame must be initialize
    }

    public void setEnemyMapToCurrentGame(int index) {
        currentEnemy = enemyMaps.get(index);
    }
}
