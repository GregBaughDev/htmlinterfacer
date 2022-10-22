package com.htmlinterfacer.htmlinterfacer.controller;

import com.htmlinterfacer.htmlinterfacer.HtmlInterfacer;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.web.HTMLEditor;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

import java.io.IOException;

public class HomeController {
    @FXML
    private Label welcomeText;

    @FXML
    private HTMLEditor htmlEditor;

    @FXML
    private WebView webView;

    // Switch between view and editor
    // Grab all files and create a button for each one
    // Check what it's like when pushing back to git
    // Create an area to look at the current changes
    // Push them to GH for PR review

    private String test = "<h2>Again to check formatting</h2>";
    private String test2 = "<h3>A second for file switching</h3>";
    private String test3 = "<h4>And again to check this could work</h4>";

    @FXML
    protected void onHelloButtonClick() throws IOException {
        HtmlInterfacer.sceneChange("test.fxml");
    }

    @FXML
    protected void onHtmlButtonClick() {
        htmlEditor.setHtmlText(test);
        webView.getEngine().loadContent(test);
    }

    @FXML
    protected void onHtmlButtonClick2() {
        htmlEditor.setHtmlText(test2);
        webView.getEngine().loadContent(test2);
    }

    @FXML
    protected void onHtmlButtonClick3() {
        htmlEditor.setHtmlText(test3);
        webView.getEngine().loadContent(test3);
    }
}