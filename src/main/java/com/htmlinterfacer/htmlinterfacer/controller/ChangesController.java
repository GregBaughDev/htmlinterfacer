package com.htmlinterfacer.htmlinterfacer.controller;

import com.htmlinterfacer.htmlinterfacer.HtmlInterfacer;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebView;

import java.io.IOException;

public class ChangesController {
    @FXML
    private Button switchBtn;

    @FXML
    private VBox changedBox;

    @FXML
    private WebView changeView;

    private Integer currentFile = 0;

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

    // TO DO:
    // CREATE A POPUP -> To confirm the save
    // Check what it's like when pushing back to git
    // Push them to GH for PR review
}
