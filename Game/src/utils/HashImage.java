package utils;

import java.util.HashMap;

import static utils.Icon.*;

public class HashImage {
    public static HashMap<Integer , String> SoldierPhoto = new HashMap<>();

    static {
        SoldierPhoto.put(0 , GRASS0);
        SoldierPhoto.put(1 ,GAURDIAN1 );
        SoldierPhoto.put(2 , GIANT2);
        SoldierPhoto.put(3, DRAGON3);
        SoldierPhoto.put(4, ARCHER4);
        SoldierPhoto.put(5 , WALL_BREAKER5);
        SoldierPhoto.put(6 , HEALER6);
    }
    public static HashMap<Integer , String> buildingPhoto = new HashMap<>();

    static {
        buildingPhoto.put(0 , GRASS0);
        buildingPhoto.put(1 , GOLD_MINE1);
        buildingPhoto.put(2 , ELIXIR_MINE2);
        buildingPhoto.put(4, GOLD_STORAGE3);
        buildingPhoto.put(3, ELIXIR_STORAGE4);
        buildingPhoto.put(5 , TOWN_HALL5);
        buildingPhoto.put(6 , BARRACKS6);
        buildingPhoto.put(7 , CAMP7);
        buildingPhoto.put(8 , ARCHER_TOWER8);
        buildingPhoto.put(9 , CANNON9);
        buildingPhoto.put(10 , AIR_DEFENSE10);
        buildingPhoto.put(11 , WIZARD_TOWER11);
        buildingPhoto.put(12 , WALL12);
        buildingPhoto.put(13 , TRAP13);
        buildingPhoto.put(14 , GUARDIAN_GIANT14);
    }


}
