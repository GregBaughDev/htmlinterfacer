package com.htmlinterfacer.htmlinterfacer.controller;

import com.htmlinterfacer.htmlinterfacer.HtmlInterfacer;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.web.HTMLEditor;
import javafx.scene.web.WebView;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class HomeController {
    @FXML
    private HTMLEditor htmlEditor;

    @FXML
    private WebView webView;

    @FXML
    private Button toggleView;

    @FXML
    private Button testBtn;

    @FXML
    private VBox editorBox;

    @FXML
    private VBox viewBox;

    @FXML
    private VBox fileBox;

    // Make a new variable for currently updated string and edit it
    // Grab all files and create a button for each one
    // Check what it's like when pushing back to git
    // Create an area to look at the current changes
    // Push them to GH for PR review

    private String test = "<h2>Again to check formatting</h2>";
    private String test2 = "<h3>A second for file switching</h3>";
    private String test3 = "<h4>And again to check this could work</h4>";

    List<String> htmlStrings = Arrays.asList(test, test2, test3);

    EventHandler<ActionEvent> handleFileChange(Integer index) {
        // Currently this is being ran setting up the buttons -> fix
        System.out.println(index + " index is");
        htmlEditor.setHtmlText(htmlStrings.get(index));
        webView.getEngine().loadContent(htmlStrings.get(index));
        return null;
    };

    @FXML
    protected void onHelloButtonClick() throws IOException {
        HtmlInterfacer.sceneChange("test.fxml");
    }

    @FXML
    protected void onToggleViewClick() {
        if (toggleView.getText().equals("Edit file")) {
            toggleView.setText("View file");
            viewBox.setVisible(false);
            editorBox.setVisible(true);
        } else {
            toggleView.setText("Edit file");
            viewBox.setVisible(true);
            editorBox.setVisible(false);
        }
    }

    @FXML
    protected void test() {
        // Find a way to label the buttons
        // Find a way to do this on load -> move to another class when the API info returns
        for (int i = 0; i < htmlStrings.size(); i++) {
            Integer currValue = i;
            Button stringButton = new Button(currValue.toString());
            stringButton.setId(currValue.toString());
            stringButton.setOnAction(handleFileChange(currValue));
            fileBox.getChildren().add(stringButton);
        }
    }
}