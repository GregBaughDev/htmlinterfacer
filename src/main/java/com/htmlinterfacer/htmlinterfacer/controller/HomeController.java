package com.htmlinterfacer.htmlinterfacer.controller;

import com.htmlinterfacer.htmlinterfacer.HtmlInterfacer;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.io.IOException;

public class HomeController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() throws IOException {
        HtmlInterfacer.sceneChange("test.fxml");
    }
}