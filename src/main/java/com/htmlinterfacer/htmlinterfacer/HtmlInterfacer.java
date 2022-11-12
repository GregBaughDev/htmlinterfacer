package com.htmlinterfacer.htmlinterfacer;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HtmlInterfacer extends Application {
    private static Stage primaryStage;

    @Override
    public void start(Stage stage) throws Exception {
        this.primaryStage = stage;
        FXMLLoader fxmlLoader = new FXMLLoader(HtmlInterfacer.class.getResource("parent.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        scene.getStylesheets().add(getClass().getResource("/home.css").toExternalForm());

        stage.setTitle("HTMLinterfacer");
        stage.setScene(scene);
        stage.setMinHeight(800);
        stage.setMinWidth(1000);
        stage.setResizable(false);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

    public static void sceneChange(String fxml) throws IOException {
        // Try to reuse this and return the scene, save to var and pass to stage
        FXMLLoader fxmlLoader = new FXMLLoader(HtmlInterfacer.class.getResource(fxml));
        Scene newScene = new Scene(fxmlLoader.load());
        newScene.getStylesheets().add(HtmlInterfacer.class.getResource("/home.css").toExternalForm());
        primaryStage.setScene(newScene);
    }

    public static Stage getStage() {
        return primaryStage;
    }
}