package com.htmlinterfacer.htmlinterfacer.controller;

import com.htmlinterfacer.htmlinterfacer.HtmlInterfacer;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.io.IOException;

public class TestController {
    @FXML
    private Label testText;

    @FXML
    protected void onTestSwitch() throws IOException {
        HtmlInterfacer.sceneChange("home.fxml");
    }
}
