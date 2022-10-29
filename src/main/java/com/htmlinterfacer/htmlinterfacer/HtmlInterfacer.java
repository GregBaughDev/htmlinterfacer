package com.htmlinterfacer.htmlinterfacer;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HtmlInterfacer extends Application {
    private static Stage primaryStage;

    @Override
    public void start(Stage stage) throws IOException {
        this.primaryStage = stage;
        FXMLLoader fxmlLoader = new FXMLLoader(HtmlInterfacer.class.getResource("home.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        scene.getStylesheets().add(getClass().getResource("/home.css").toExternalForm());

        stage.setTitle("HTMLinterfacer");
        stage.setScene(scene);
        stage.setMaximized(true);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

    public static void sceneChange(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HtmlInterfacer.class.getResource(fxml));
        Scene newScene = new Scene(fxmlLoader.load());
        primaryStage.setScene(newScene);
    }
}