package com.htmlinterfacer.htmlinterfacer.controller;

import com.htmlinterfacer.htmlinterfacer.HtmlInterfacer;
import com.htmlinterfacer.htmlinterfacer.log.FileLog;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.web.WebView;

public class HomeController {
    private final FileLog fileLog = new FileLog();
    @FXML
    private TextArea textArea;

    @FXML
    private WebView webView;

    @FXML
    private Button toggleView;

    @FXML
    private VBox editorBox;

    @FXML
    private VBox viewBox;

    @FXML
    private VBox fileBox;

    private Integer currentFile = 0;

    public HomeController() {
    }

    public void initialize() {
        try {
            for (int i = 0; i < ParentController.getParentHtmlFileList().size(); i++) {
                Integer currValue = i;
                Button stringButton = new Button(ParentController.getParentHtmlFileList().get(i).getPath());
                stringButton.setOnAction(e -> handleFileChange(currValue));
                stringButton.setFont(Font.font("Inter Regular"));
                fileBox.getChildren().add(stringButton);
            }
            webView.getEngine().loadContent(ParentController.getParentHtmlFileList().get(currentFile).getUpdatedHtml());
        } catch (Exception e) {
            fileLog.writeToLog("Home Controller - Initialise exception: " + e);
            Platform.exit();
        }
    }

    public void handleFileChange(Integer index) {
        currentFile = index;
        textArea.setText(ParentController.getParentHtmlFileList().get(currentFile).getUpdatedHtml());
        webView.getEngine().loadContent(ParentController.getParentHtmlFileList().get(currentFile).getUpdatedHtml());
    }

    @FXML
    protected void onToggleViewClick() {
        if (toggleView.getText().equals("Edit file")) {
            toggleView.setText("View file");
            viewBox.setVisible(false);
            editorBox.setVisible(true);
            setTextAreaContent();
        } else {
            toggleView.setText("Edit file");
            viewBox.setVisible(true);
            editorBox.setVisible(false);
            setWebViewContent();
        }
    }

    @FXML
    protected void save() {
        ParentController.getParentHtmlFileList().get(currentFile).setUpdatedHtml(textArea.getText());
        setTextAreaContent();
        setWebViewContent();
        viewBox.setVisible(true);
        editorBox.setVisible(false);
        toggleView.setText("Edit file");
    }

    @FXML
    protected void viewChangesList() {
        try {
            currentFile = 0;
            HtmlInterfacer.sceneChange("changes.fxml");
        } catch (Exception e) {
            fileLog.writeToLog("Home Controller - viewChangesList exception: " + e);
        }
    }

    @FXML
    protected void handleReset() {
        for (int i = 0; i < ParentController.getParentHtmlFileList().size(); i++) {
            ParentController.getParentHtmlFileList().get(i).setUpdatedHtml(
                    ParentController.getParentHtmlFileList().get(i).getOriginalHtml()
            );
        }
        setTextAreaContent();
        setWebViewContent();
    }

    private void setTextAreaContent() {
        textArea.setText(ParentController.getParentHtmlFileList().get(currentFile).getUpdatedHtml());
    }

    private void setWebViewContent() {
        webView.getEngine().loadContent(ParentController.getParentHtmlFileList().get(currentFile).getUpdatedHtml());
    }

    @FXML
    protected void handleQuit() {
        Platform.exit();
    }
}