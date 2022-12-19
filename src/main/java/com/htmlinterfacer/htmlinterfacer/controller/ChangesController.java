package com.htmlinterfacer.htmlinterfacer.controller;

import com.htmlinterfacer.htmlinterfacer.HtmlInterfacer;
import com.htmlinterfacer.htmlinterfacer.api.connection.GHApi;
import com.htmlinterfacer.htmlinterfacer.api.record.Ref;
import com.htmlinterfacer.htmlinterfacer.log.FileLog;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebView;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;

public class ChangesController {
    private GHApi ghApi = new GHApi();
    private FileLog fileLog = new FileLog();
    @FXML
    private Button commitBtn;

    @FXML
    private VBox changedBox;

    @FXML
    private WebView changeView;

    private Integer currentFile = 0;

    public ChangesController() throws IOException {
    }

    public void initialize() throws IOException {
        try {
            for (int i = 0; i < ParentController.getParentHtmlFileList().size(); i++) {
                if (ParentController.getParentHtmlFileList().get(i).isAltered()) {
                    Integer currValue = i;
                    Button stringButton = new Button(ParentController.getParentHtmlFileList().get(i).getPath());
                    stringButton.setOnAction(e -> handleFileChange(currValue));
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
    protected void handleCommit() throws IOException, InterruptedException {
        // CREATE A POPUP -> To confirm the save
        // Move to try/catch
        // Do something with the UI - lock the screen or similar
        String branchName = DateTimeFormatter.ofPattern("dd/MM/yyyy").format(LocalDate.now());
        List<Ref> refs = ghApi.getSendRefsRequest();
        // Check the below -> may change depending on the repo
        ghApi.postSendRefsRequest(branchName, refs.get(currentFile).refObject().sha());

        for (int i = 0; i < ParentController.getParentHtmlFileList().size(); i++) {
            if (ParentController.getParentHtmlFileList().get(i).isAltered()){
                String changedFile = ParentController.getParentHtmlFileList().get(i).getUpdatedHtml();
                String response = ghApi.putSendUpdateFileRequest(
                        // Move this to an accessible variable instead of having to recalculate
                        ParentController.getParentHtmlFileList().get(i).getPath(),
                        Base64.getEncoder().encodeToString(changedFile.getBytes(StandardCharsets.UTF_8)),
                        branchName,
                        ParentController.getParentHtmlFileList().get(i).getSha(),
                        "Commit to file: " + ParentController.getParentHtmlFileList().get(i).getPath()
                );
                fileLog.writeToLog("File Commit: " + response);
            }
        }

        String response = ghApi.getPostCreatePRRequest("feat/update docs: " + branchName, branchName, "Changes to static files");
        fileLog.writeToLog("Create PR: " + response);
        // re-initialise here??
        HtmlInterfacer.sceneChange("home.fxml");

        // Reset status and regrab the commit shas etc

        // Array is cleared and user goes back to main screen
    }
}
