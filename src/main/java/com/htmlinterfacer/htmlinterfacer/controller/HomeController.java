package com.htmlinterfacer.htmlinterfacer.controller;

import com.htmlinterfacer.htmlinterfacer.HtmlInterfacer;
import com.htmlinterfacer.htmlinterfacer.Initialiser;
import com.htmlinterfacer.htmlinterfacer.dao.HtmlFile;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.scene.web.HTMLEditor;
import javafx.scene.web.WebView;

import java.io.IOException;

public class HomeController {
    @FXML
    private HTMLEditor htmlEditor;

    @FXML
    private TextArea textArea;

    @FXML
    private WebView webView;

    @FXML
    private Button toggleView;

    @FXML
    private Button treeBtn;

    @FXML
    private Button saveBtn;

    @FXML
    private Button viewChanges;

    @FXML
    private VBox editorBox;

    @FXML
    private VBox viewBox;

    @FXML
    private VBox fileBox;

    private Integer currentFile = 0;

    private ObservableList<HtmlFile> htmlFileList = FXCollections.observableArrayList();

    private Initialiser initialiser = new Initialiser();

    public HomeController() throws IOException {
    }

    public void initialize() throws InterruptedException {
        Boolean backgroundThreadSuccess = initialiser.createBackgroundThread(htmlFileList);
        if (backgroundThreadSuccess) {
            initialiser.getBackgroundThread().setOnSucceeded(evt -> {
                for (int i = 0; i < htmlFileList.size(); i++) {
                    System.out.println("The inside of the loop");
                    Integer currValue = i;
                    Button stringButton = new Button(currValue.toString());
                    stringButton.setId(currValue.toString());
                    stringButton.setOnAction(e -> handleFileChange(currValue));
                    fileBox.getChildren().add(stringButton);
                }
            });
        }
    }
    // Make a new variable for currently updated string and edit it
    // Check what it's like when pushing back to git
    // Create an area to look at the current changes
    // Push them to GH for PR review

    public EventHandler<ActionEvent> handleFileChange(Integer index) {
        currentFile = index;
        textArea.setText(htmlFileList.get(currentFile).getUpdatedHtml());
        webView.getEngine().loadContent(htmlFileList.get(currentFile).getUpdatedHtml());
        return null;
    }

    @FXML
    protected void onToggleViewClick() {
        if (toggleView.getText().equals("Edit file")) {
            toggleView.setText("View file");
            viewBox.setVisible(false);
            editorBox.setVisible(true);
            textArea.setText(htmlFileList.get(currentFile).getUpdatedHtml());
        } else {
            toggleView.setText("Edit file");
            viewBox.setVisible(true);
            editorBox.setVisible(false);
            webView.getEngine().loadContent(htmlFileList.get(currentFile).getUpdatedHtml());
        }
    }

//    @FXML
//    protected void tree() {
//        test4.getTree().getTree().stream().forEach(tree -> {
//            System.out.println(tree.getSha());
//        });
//    }

    @FXML
    protected void save() {
        htmlFileList.get(currentFile).setUpdatedHtml(textArea.getText());
        textArea.setText(htmlFileList.get(currentFile).getUpdatedHtml());
        webView.getEngine().loadContent(htmlFileList.get(currentFile).getUpdatedHtml());
    }

    @FXML
    protected void viewChangesList() throws IOException {
        HtmlInterfacer.sceneChange("changes.fxml");
    }
}