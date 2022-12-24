package com.htmlinterfacer.htmlinterfacer;

import com.htmlinterfacer.htmlinterfacer.log.FileLog;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HtmlInterfacer extends Application {
    private final FileLog fileLog = new FileLog();
    private static Stage primaryStage;

    public HtmlInterfacer() throws IOException {
    }

    @Override
    public void start(Stage stage) throws Exception {
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
        }
    }

    public static void main(String[] args) {
        launch();
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
}