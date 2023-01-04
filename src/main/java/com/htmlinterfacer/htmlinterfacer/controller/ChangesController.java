package com.htmlinterfacer.htmlinterfacer.controller;

import com.htmlinterfacer.htmlinterfacer.HtmlInterfacer;
import com.htmlinterfacer.htmlinterfacer.alert.ApplicationAlert;
import com.htmlinterfacer.htmlinterfacer.log.FileLog;
import com.htmlinterfacer.htmlinterfacer.task.Committer;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.web.WebView;

import java.io.IOException;

public class ChangesController {
    private final FileLog fileLog = new FileLog();
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

    public void initialize() {
        try {
            for (int i = 0; i < ParentController.getParentHtmlFileList().size(); i++) {
                if (ParentController.getParentHtmlFileList().get(i).isAltered()) {
                    Integer currValue = i;
                    Button fileButton = new Button(ParentController.getParentHtmlFileList().get(i).getPath());
                    fileButton.setOnAction(e -> handleFileChange(currValue));
                    fileButton.setFont(Font.font("Inter Regular"));
                    fileButton.setMaxWidth(150);
                    changedBox.getChildren().add(fileButton);
                    commitBtn.setDisable(false);
                }
            }
        } catch (Exception e) {
            fileLog.writeToLog("Changes Controller - Initialise exception: " + e);
        }
    }

    public void handleFileChange(Integer integer) {
        currentFile = integer;
        changeView.getEngine().loadContent(ParentController.getParentHtmlFileList().get(currentFile).getUpdatedHtml());
    }

    @FXML
    protected void switchView() {
        currentFile = 0;
        HtmlInterfacer.sceneChange("home.fxml");
    }

    @FXML
    protected void handleCommit() {
        committer.createBackgroundThread(commitBtn, switchView, progressIndicator, progressBar);
        committer.getBackgroundThread().setOnSucceeded(evt -> {
            try {
                ApplicationAlert.createAlert(Alert.AlertType.INFORMATION, "Commit complete", "Commit complete - application will now close");
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        committer.getBackgroundThread().setOnFailed(evt -> {
            try {
                fileLog.writeToLog("Thread failed on commit");
                ApplicationAlert.createAlert(Alert.AlertType.ERROR, "Commit failed", "Commit failed - Application will now quit. Please reload and try again.");
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
