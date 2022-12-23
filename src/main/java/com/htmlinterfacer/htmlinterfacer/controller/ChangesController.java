package com.htmlinterfacer.htmlinterfacer.controller;

import com.htmlinterfacer.htmlinterfacer.HtmlInterfacer;
import com.htmlinterfacer.htmlinterfacer.api.connection.GHApi;
import com.htmlinterfacer.htmlinterfacer.log.FileLog;
import com.htmlinterfacer.htmlinterfacer.task.Committer;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.web.WebView;

import java.io.IOException;

public class ChangesController {
    private GHApi ghApi = new GHApi();
    private FileLog fileLog = new FileLog();
    @FXML
    private Button commitBtn;

    @FXML
    private Button switchView;

    @FXML
    private VBox changedBox;

    @FXML
    private WebView changeView;

    @FXML
    private HBox progressIndicator;

    @FXML
    private ProgressBar progressBar;

    private Integer currentFile = 0;

    private final Committer committer = new Committer();

    public ChangesController() throws IOException {
    }

    public void initialize() throws IOException {
        try {
            for (int i = 0; i < ParentController.getParentHtmlFileList().size(); i++) {
                if (ParentController.getParentHtmlFileList().get(i).isAltered()) {
                    Integer currValue = i;
                    Button stringButton = new Button(ParentController.getParentHtmlFileList().get(i).getPath());
                    stringButton.setOnAction(e -> handleFileChange(currValue));
                    stringButton.setFont(Font.font("Inter Regular"));
                    changedBox.getChildren().add(stringButton);
                    commitBtn.setDisable(false);
                }
            }
        } catch (Exception e) {
            fileLog.writeToLog("Changes Controller - Initialise exception: " + e);
        }
    }

    public EventHandler<ActionEvent> handleFileChange(Integer integer) {
        currentFile = integer;
        changeView.getEngine().loadContent(ParentController.getParentHtmlFileList().get(currentFile).getUpdatedHtml());
        return null;
    }

    @FXML
    protected void switchView() throws IOException {
        currentFile = 0;
        HtmlInterfacer.sceneChange("home.fxml");
    }

    @FXML
    protected void handleCommit() {
        committer.createBackgroundThread(commitBtn, switchView, progressIndicator, progressBar, currentFile);
        committer.getBackgroundThread().setOnSucceeded(evt -> {
            try {
                HtmlInterfacer.sceneChange("home.fxml");
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        committer.getBackgroundThread().setOnFailed(evt -> {
            try {
                fileLog.writeToLog("Thread failed on commit");
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        // Maybe create a dialog box and then close the application
    }
}
