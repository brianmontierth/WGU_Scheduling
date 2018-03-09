package scheduler.helper;

import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.util.HashMap;

public class SceneManager {
    private static HashMap<String, Scene> sceneMap = new HashMap<>();
    private static Stage main;

    public static void setStage(Stage stage) {
        main = stage;
    }

    protected static void addScene(String name, Scene scene) {
        sceneMap.put(name, scene);
    }

    protected static void removeScene(String name) {
        sceneMap.remove(name);
    }

    public static void activate(String name) {
        main.setScene(sceneMap.get(name));
        main.show();
    }
}
