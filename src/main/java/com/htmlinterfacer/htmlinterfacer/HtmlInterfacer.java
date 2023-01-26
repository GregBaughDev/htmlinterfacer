package com.htmlinterfacer.htmlinterfacer;

import com.htmlinterfacer.htmlinterfacer.alert.ApplicationAlert;
import com.htmlinterfacer.htmlinterfacer.log.FileLog;
import io.github.cdimascio.dotenv.Dotenv;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

public class HtmlInterfacer extends Application {
    private static final Dotenv dotEnv = Dotenv.load();
    private static final FileLog fileLog = new FileLog();
    private static Stage primaryStage;
    public static final String CHANGES_VIEW = "changes.fxml";
    public static final String HOME_VIEW = "home.fxml";
    static final String PARENT_VIEW = "parent.fxml";

    public HtmlInterfacer() {
    }

    @Override
    public void start(Stage stage) {
        try {
            primaryStage = stage;
            FXMLLoader fxmlLoader = new FXMLLoader(HtmlInterfacer.class.getResource(PARENT_VIEW));
            Scene scene = new Scene(fxmlLoader.load());

            stage.setTitle("HTMLinterfacer");
            stage.setScene(scene);
            stage.setMinHeight(800);
            stage.setMinWidth(1000);
            stage.setResizable(false);
            stage.show();
        } catch (Exception e) {
            fileLog.writeToLog("Start exception: " + e);
            ApplicationAlert.createAlert(Alert.AlertType.ERROR, ApplicationAlert.errorTitle, ApplicationAlert.errorBody);
        }
    }

    public static void main(String[] args) {
        if (isSystemEnvsPopulated()) {
            launch();
        } else {
            fileLog.writeToLog("Main Exception - Env vars not populated");
            ApplicationAlert.createAlert(Alert.AlertType.ERROR, ApplicationAlert.errorTitle, "The environment variables have not been populated. Please check the .env file and restart the application.");
        }
        // https://medium.com/information-and-technology/test-driven-development-in-javafx-with-testfx-66a84cd561e0
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
                dotEnv.get("FILES") != null &&
                dotEnv.get("BASE_BRANCH") != null;
    }
}