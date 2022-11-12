package com.htmlinterfacer.htmlinterfacer.controller;

import com.htmlinterfacer.htmlinterfacer.HtmlInterfacer;
import com.htmlinterfacer.htmlinterfacer.Initialiser;
import com.htmlinterfacer.htmlinterfacer.dao.HtmlFile;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.IOException;

public class ParentController {
    private static ObservableList<HtmlFile> parentHtmlFileList = FXCollections.observableArrayList();

    private Initialiser initialiser = new Initialiser();

    public ParentController() throws IOException {
    }

    public void initialize() throws InterruptedException, IOException {
        initialiser.createBackgroundThread(ParentController.getParentHtmlFileList());
        initialiser.getBackgroundThread().setOnSucceeded(evt -> {
            try {
                HtmlInterfacer.sceneChange("home.fxml");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        initialiser.getBackgroundThread().setOnFailed(evt -> System.out.println("Thread failed"));
    }

    public static ObservableList<HtmlFile> getParentHtmlFileList() {
        return ParentController.parentHtmlFileList;
    }

}
