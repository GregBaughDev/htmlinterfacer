package com.htmlinterfacer.htmlinterfacer.controller;

import com.htmlinterfacer.htmlinterfacer.HtmlInterfacer;
import com.htmlinterfacer.htmlinterfacer.alert.ApplicationAlert;
import com.htmlinterfacer.htmlinterfacer.log.FileLog;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
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

    private final String EDIT_FILE = "Edit file";

    private final String VIEW_FILE = "View file";

    public HomeController() {
    }

    public void initialize() {
        try {
            for (int i = 0; i < ParentController.getParentHtmlFileList().size(); i++) {
                Integer currValue = i;
                Button fileButton = new Button(ParentController.getParentHtmlFileList().get(i).getPath());
                fileButton.setOnAction(e -> handleFileChange(currValue));
                fileButton.setMaxWidth(150);
                fileBox.getChildren().add(fileButton);
            }
            webView.getEngine().loadContent(ParentController.getParentHtmlFileList().get(currentFile).getUpdatedHtml());
        } catch (Exception e) {
            fileLog.writeToLog("Home Controller - Initialise exception: " + e);
            ApplicationAlert.createAlert(Alert.AlertType.ERROR, ApplicationAlert.errorTitle, ApplicationAlert.errorBody);
        }
    }

    public void handleFileChange(Integer index) {
        currentFile = index;
        textArea.setText(ParentController.getParentHtmlFileList().get(currentFile).getUpdatedHtml());
        webView.getEngine().loadContent(ParentController.getParentHtmlFileList().get(currentFile).getUpdatedHtml());
    }

    @FXML
    protected void onToggleViewClick() {
        if (toggleView.getText().equals(EDIT_FILE)) {
            toggleView.setText(VIEW_FILE);
            viewBox.setVisible(false);
            editorBox.setVisible(true);
            setTextAreaContent();
        } else {
            toggleView.setText(EDIT_FILE);
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
        toggleView.setText(EDIT_FILE);
    }

    @FXML
    protected void viewChangesList() {
        try {
            currentFile = 0;
            HtmlInterfacer.sceneChange(HtmlInterfacer.CHANGES_VIEW);
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