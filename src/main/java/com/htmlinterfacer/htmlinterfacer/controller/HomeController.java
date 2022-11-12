package com.htmlinterfacer.htmlinterfacer.controller;

import com.htmlinterfacer.htmlinterfacer.HtmlInterfacer;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebView;

import java.io.IOException;

public class HomeController {
    @FXML
    private TextArea textArea;

    @FXML
    private WebView webView;

    @FXML
    private Button toggleView;

    @FXML
    private Button treeBtn;

    @FXML
    private VBox editorBox;

    @FXML
    private VBox viewBox;

    @FXML
    private VBox fileBox;

    private Integer currentFile = 0;

    public HomeController() throws IOException {
    }

    public void initialize() throws InterruptedException {
        for (int i = 0; i < ParentController.getParentHtmlFileList().size(); i++) {
            Integer currValue = i;
            Button stringButton = new Button(currValue.toString());
//            stringButton.setId(currValue.toString());
            stringButton.setOnAction(e -> handleFileChange(currValue));
            fileBox.getChildren().add(stringButton);
        }
    }

    public EventHandler<ActionEvent> handleFileChange(Integer index) {
        currentFile = index;
        textArea.setText(ParentController.getParentHtmlFileList().get(currentFile).getUpdatedHtml());
        webView.getEngine().loadContent(ParentController.getParentHtmlFileList().get(currentFile).getUpdatedHtml());
        return null;
    }

    @FXML
    protected void onToggleViewClick() {
        if (toggleView.getText().equals("Edit file")) {
            toggleView.setText("View file");
            viewBox.setVisible(false);
            editorBox.setVisible(true);
            textArea.setText(ParentController.getParentHtmlFileList().get(currentFile).getUpdatedHtml());
        } else {
            toggleView.setText("Edit file");
            viewBox.setVisible(true);
            editorBox.setVisible(false);
            webView.getEngine().loadContent(ParentController.getParentHtmlFileList().get(currentFile).getUpdatedHtml());
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
        ParentController.getParentHtmlFileList().get(currentFile).setUpdatedHtml(textArea.getText());
        textArea.setText(ParentController.getParentHtmlFileList().get(currentFile).getUpdatedHtml());
        webView.getEngine().loadContent(ParentController.getParentHtmlFileList().get(currentFile).getUpdatedHtml());
    }

    @FXML
    protected void viewChangesList() throws IOException {
        currentFile = 0;
        HtmlInterfacer.sceneChange("changes.fxml");
    }
}