package com.htmlinterfacer.htmlinterfacer.controller;

import com.htmlinterfacer.htmlinterfacer.HtmlInterfacer;
import com.htmlinterfacer.htmlinterfacer.api.connection.GHApi;
import com.htmlinterfacer.htmlinterfacer.api.response.Links;
import com.htmlinterfacer.htmlinterfacer.api.response.Ref;
import com.htmlinterfacer.htmlinterfacer.api.response.Repo;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebView;

import java.io.IOException;
import java.net.http.HttpResponse;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;

public class ChangesController {
    @FXML
    private Button switchBtn;

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
//        gh.getRepo().createCommit();
//        byte[] fileContents = Base64.getMimeDecoder().decode(GHApi.getSendFileContentRequest(System.getenv("REPO1")).getContent());
//        System.out.println(new String(fileContents));

        String response = GHApi.postSendRefsRequest("fromUI", "e85991183321aaa2b258b2604812b4d1196e9ff1");
        System.out.println(response);
        // System.out.println(GHApi.getSendRepoContentRequest());
//        for (Repo repo : test) {
//            System.out.println(repo.toString());
//        }
    }
    // https://api.github.com/repos/GregBaughDev/htmlInterfacerTestRepo/git/refs
//    {
//        "ref": "refs/heads/test-branch",
//            "sha": "e85991183321aaa2b258b2604812b4d1196e9ff1"
//    }
    // TO DO:
    // CREATE A POPUP -> To confirm the save
    // Check what it's like when pushing back to git
    // Push them to GH for PR review
}
