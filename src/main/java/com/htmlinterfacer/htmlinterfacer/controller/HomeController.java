package com.htmlinterfacer.htmlinterfacer.controller;

import com.htmlinterfacer.htmlinterfacer.HtmlInterfacer;
import com.htmlinterfacer.htmlinterfacer.dao.HtmlFile;
import com.htmlinterfacer.htmlinterfacer.log.FileLog;
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
            Button stringButton = new Button(ParentController.getParentHtmlFileList().get(i).getPath());
            stringButton.setOnAction(e -> handleFileChange(currValue));
            fileBox.getChildren().add(stringButton);
        }
        webView.getEngine().loadContent(ParentController.getParentHtmlFileList().get(currentFile).getUpdatedHtml());
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
    }

    @FXML
    protected void viewChangesList() throws IOException {
        currentFile = 0;
        HtmlInterfacer.sceneChange("changes.fxml");
    }

    @FXML
    protected void handleReset() throws IOException {
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
}