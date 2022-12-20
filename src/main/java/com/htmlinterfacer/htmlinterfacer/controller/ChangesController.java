package com.htmlinterfacer.htmlinterfacer.controller;

import com.htmlinterfacer.htmlinterfacer.HtmlInterfacer;
import com.htmlinterfacer.htmlinterfacer.api.connection.GHApi;
import com.htmlinterfacer.htmlinterfacer.api.record.Ref;
import com.htmlinterfacer.htmlinterfacer.log.FileLog;
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
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.List;

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
    protected void handleCommit() throws IOException, InterruptedException {
        // Need to move this into it's own thread
        commitBtn.setDisable(true);
        switchView.setDisable(true);
        progressIndicator.setVisible(true);
        progressBar.setProgress(0.1);
        // Move to try/catch
        String branchName = DateTimeFormatter.ofPattern("dd/MM/yyyy").format(LocalDate.now());
        List<Ref> refs = ghApi.getSendRefsRequest();
        // Check the below -> may change depending on the repo
        ghApi.postSendRefsRequest(branchName, refs.get(currentFile).refObject().sha());
        progressBar.setProgress(0.5);
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
        progressBar.setProgress(0.8);
        String response = ghApi.getPostCreatePRRequest("feat/update docs: " + branchName, branchName, "Changes to static files");
        fileLog.writeToLog("Create PR: " + response);
        // re-initialise here??
        progressBar.setProgress(1);
        HtmlInterfacer.sceneChange("home.fxml");

        // Reset status and regrab the commit shas etc

        // Array is cleared and user goes back to main screen
    }
    //
}
