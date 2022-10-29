package com.htmlinterfacer.htmlinterfacer.controller;

import com.htmlinterfacer.htmlinterfacer.HtmlInterfacer;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.io.IOException;

public class ChangesController {
    // Need to get the list of HTML files here
    @FXML
    private Button switchBtn;

    @FXML
    protected void switchView() throws IOException {
        HtmlInterfacer.sceneChange("home.fxml");
    }
}
