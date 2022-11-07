package com.htmlinterfacer.htmlinterfacer.controller;

import com.htmlinterfacer.htmlinterfacer.HtmlInterfacer;
import com.htmlinterfacer.htmlinterfacer.dao.HtmlFile;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class ChangesController {
    @FXML
    private Button switchBtn;

    @FXML
    private VBox changedBox;

    private ObservableList<HtmlFile> changedHtmlFileList = FXCollections.observableArrayList();

    @FXML
    protected void switchView() throws IOException {
        HtmlInterfacer.sceneChange("home.fxml");
    }
    // Only call once?
    // View box to see changes
    public void initialize() {
        changedHtmlFileList = ParentController.parentHtmlFileList.filtered(file -> file.isAltered());
        for (int i = 0; i < changedHtmlFileList.size(); i++) {
            Integer currValue = i;
            Button stringButton = new Button(currValue.toString());
            stringButton.setId(currValue.toString());
            changedBox.getChildren().add(stringButton);
        }
    }
}
