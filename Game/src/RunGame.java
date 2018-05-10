import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

public class RunGame {
    public static void main(String[] args) {
        Controller controller = new Controller();
        controller.runGame();
    }
}
