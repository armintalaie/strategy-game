
import com.google.gson.Gson;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;

public class AttackGUICheck extends Application
{
    public static void main(String[] args) {
        System.out.println("before launch");
        Application.launch(args);
        System.out.println("after launch");
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Scanner scanner = new Scanner(System.in);

        Game game = new Game(2);
        EnemyMap enemyMap = new EnemyMap();
       try {
            EnemyMap enemyMap1 = new EnemyMap();

            Gson g = new Gson();
            enemyMap1 = g.fromJson(new FileReader(scanner.nextLine()), EnemyMap.class);
            enemyMap1.initializeMap();
            enemyMap = enemyMap1;
        }catch (Exception e){
            System.out.println("excecption");
            System.out.println(e.getStackTrace());
        }



        ArrayList<Person> valuableSoldiers = new ArrayList<>();

        Guardian guardian = new Guardian();
        Archer archer = new Archer();
        Giant giant= new Giant();
        Dragon dragon = new Dragon();
        game.getOwnMap().valuableSoldiers.add(giant);
        game.getOwnMap().valuableSoldiers.add(guardian);
        game.getOwnMap().valuableSoldiers.add(new Archer());
        game.getOwnMap().valuableSoldiers.add(new Archer());
        game.getOwnMap().valuableSoldiers.add(new Archer());

        game.getOwnMap().valuableSoldiers.add(new Healer());

        game.getOwnMap().valuableSoldiers.add(dragon);
        AttackThread attackThread =new AttackThread(game,enemyMap , primaryStage , new Scene(new Group()));
        Thread thread = new Thread(attackThread);
        thread.start();
        AttackGUI attackGUI = new AttackGUI(attackThread);
        primaryStage.setScene(attackGUI.getScene());
        primaryStage.show();
    }
}
