package scheduler.main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import scheduler.helper.DataAccess;
import scheduler.helper.SceneManager;

import java.util.Locale;
import java.util.ResourceBundle;

public class Main extends Application {

    private static String user = "brian";
    private static Stage stage;
    private ResourceBundle rb;

    public static String getUser() {
        return user;
    }

    public static void setUser(String user) {
        Main.user = user;
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        DataAccess.connect();
        stage = primaryStage;
        stage.setTitle("Schedule App");
        SceneManager.setStage(stage);

        Parent loginRoot = FXMLLoader.load(getClass().getResource("scheduler/login/LoginView.fxml"), init_Resources());
        SceneManager.addScene("login", new Scene(loginRoot));

        Parent mainRoot = FXMLLoader.load(getClass().getResource("scheduler/main/MainView.fxml"));
        SceneManager.addScene("main", new Scene(mainRoot));

        SceneManager.activate("login");
    }

    @Override
    public void stop() throws Exception {
        super.stop();
        DataAccess.close();
    }

    public static void main(String[] args) {
        launch(args);
    }

    public ResourceBundle init_Resources() {
        Locale us = new Locale("en","US");
        Locale es = new Locale("es");

        //Locale.setDefault(es);
        if (Locale.getDefault() == us)
            rb = ResourceBundle.getBundle("scheduler.resources.login", us);
        else if (Locale.getDefault() == es)
            rb = ResourceBundle.getBundle("scheduler.resources.login", es);
        else
            rb = ResourceBundle.getBundle("scheduler.resources.login");
        return rb;
    }
}
