package com.htmlinterfacer.htmlinterfacer.controller;

import com.htmlinterfacer.htmlinterfacer.HtmlInterfacer;
import com.htmlinterfacer.htmlinterfacer.task.Initialiser;
import com.htmlinterfacer.htmlinterfacer.dao.HtmlFile;
import com.htmlinterfacer.htmlinterfacer.log.FileLog;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.IOException;

public class ParentController {
    private final static ObservableList<HtmlFile> parentHtmlFileList = FXCollections.observableArrayList();
    private final FileLog fileLog = new FileLog();
    private final Initialiser initialiser = new Initialiser();

    public ParentController() throws IOException {
    }

    public void initialize() {
        initialiser.createBackgroundThread(ParentController.getParentHtmlFileList());
        initialiser.getBackgroundThread().setOnSucceeded(evt -> {
            try {
                HtmlInterfacer.sceneChange("home.fxml");
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        initialiser.getBackgroundThread().setOnFailed(evt -> {
            try {
                fileLog.writeToLog("Thread failed on initialise");
            } catch (Exception e) {
                e.printStackTrace();
            }
            Platform.exit();
        });
    }

    public static ObservableList<HtmlFile> getParentHtmlFileList() {
        return ParentController.parentHtmlFileList;
    }

}
