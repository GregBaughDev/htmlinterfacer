package com.htmlinterfacer.htmlinterfacer;

import com.htmlinterfacer.htmlinterfacer.log.FileLog;
import io.github.cdimascio.dotenv.Dotenv;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class HtmlInterfacer extends Application {
    private static final Dotenv dotEnv = Dotenv.load();
    private static final FileLog fileLog = new FileLog();
    private static Stage primaryStage;

    public HtmlInterfacer() {
    }

    @Override
    public void start(Stage stage) {
        try {
            primaryStage = stage;
            FXMLLoader fxmlLoader = new FXMLLoader(HtmlInterfacer.class.getResource("parent.fxml"));
            Scene scene = new Scene(fxmlLoader.load());

            stage.setTitle("HTMLinterfacer");
            stage.setScene(scene);
            stage.setMinHeight(800);
            stage.setMinWidth(1000);
            stage.setResizable(false);
            stage.show();
        } catch (Exception e) {
            fileLog.writeToLog("Start exception: " + e);
            Platform.exit();
        }
    }

    public static void main(String[] args) {
        if (isSystemEnvsPopulated()) {
            launch();
        } else {
            fileLog.writeToLog("Main Exception - Env vars not populated");
            Platform.exit();
        }
        // https://medium.com/information-and-technology/test-driven-development-in-javafx-with-testfx-66a84cd561e0
        // Write script for compiling
    }

    public static void sceneChange(String fxml) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(HtmlInterfacer.class.getResource(fxml));
            Scene newScene = new Scene(fxmlLoader.load());
            primaryStage.setScene(newScene);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean isSystemEnvsPopulated() {
        return dotEnv.get("GHREPO") != null &&
                dotEnv.get("GHOWNER") != null &&
                dotEnv.get("OAUTH") != null &&
                dotEnv.get("FILES") != null;
    }
}