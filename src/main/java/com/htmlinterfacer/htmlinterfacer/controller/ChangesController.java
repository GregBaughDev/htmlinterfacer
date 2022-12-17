package com.htmlinterfacer.htmlinterfacer.controller;

import com.htmlinterfacer.htmlinterfacer.HtmlInterfacer;
import com.htmlinterfacer.htmlinterfacer.api.connection.GHApi;
import com.htmlinterfacer.htmlinterfacer.api.response.Ref;
import com.htmlinterfacer.htmlinterfacer.dao.HtmlFile;
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

    private List<String> files = Arrays.asList(System.getenv("FILES").split(","));

    public ChangesController() throws IOException {
    }

    public void initialize() {
        for (int i = 0; i < ParentController.getParentHtmlFileList().size(); i++) {
            if (ParentController.getParentHtmlFileList().get(i).isAltered()) {
                Integer currValue = i;
                Button stringButton = new Button(currValue.toString());
                stringButton.setId(currValue.toString());
                stringButton.setOnAction(e -> handleFileChange(currValue));
                changedBox.getChildren().add(stringButton);
                commitBtn.setDisable(false);
            }
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
        String branchName = DateTimeFormatter.ofPattern("dd/MM/yyyy").format(LocalDate.now());
        List<Ref> refs = ghApi.getSendRefsRequest();
        // Check the below -> may change depending on the repo
        ghApi.postSendRefsRequest(branchName, refs.get(currentFile).getRefObject().getSha());

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
        // Lock the ui or add reset button

        // Also need to find a way to do bulk file commits
        // So once the commit has been made we need to grab the new sha or disable it being edited again
        // Also set 0 as always displayed when UI opens
        // Each file saved push into an array which is looped through and commited
        // Array is cleared and user goes back to main screen
    }
    // TO DO:
    // CREATE A POPUP -> To confirm the save
    // Logging

    // OPTIMISATIONS
    // Refactor response classes to records
}
