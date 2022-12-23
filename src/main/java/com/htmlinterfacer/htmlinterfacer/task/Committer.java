package com.htmlinterfacer.htmlinterfacer.task;

import com.htmlinterfacer.htmlinterfacer.api.connection.GHApi;
import com.htmlinterfacer.htmlinterfacer.api.record.Ref;
import com.htmlinterfacer.htmlinterfacer.controller.ParentController;
import com.htmlinterfacer.htmlinterfacer.log.FileLog;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.HBox;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.List;

public class Committer extends Task {
    private Service<Void> backgroundThread;
    private FileLog fileLog = new FileLog();
    private GHApi ghApi = new GHApi();

    public Committer() throws IOException {
    }

    public void createBackgroundThread(
            Button commitBtn,
            Button switchView,
            HBox progressIndicator,
            ProgressBar progressBar,
            Integer currentFile
    ) {
        backgroundThread = new Service<>() {
            @Override
            protected Task<Void> createTask() {
                return new Task<>() {
                    @Override
                    protected Void call() throws Exception {
                        try {
                            commitBtn.setDisable(true);
                            switchView.setDisable(true);
                            progressIndicator.setVisible(true);
                            progressBar.setProgress(0.1);

                            String branchName = DateTimeFormatter.ofPattern("dd/MM/yyyy").format(LocalDate.now());
                            List<Ref> refs = ghApi.getSendRefsRequest();
                            ghApi.postSendRefsRequest(branchName, refs.get(currentFile).refObject().sha());
                            progressBar.setProgress(0.5);

                            for (int i = 0; i < ParentController.getParentHtmlFileList().size(); i++) {
                                if (ParentController.getParentHtmlFileList().get(i).isAltered()) {
                                    commitChangedFile(branchName, i);
                                }
                            }
                            progressBar.setProgress(0.8);

                            createPr(branchName);
                            progressBar.setProgress(1);
                            return null;
                        } catch (Exception e) {
                            fileLog.writeToLog("Committer - Create background thread exception: " + e);
                        }
                        return null;
                    }
                };
            }
        };
        backgroundThread.start();
    }

    public Service<Void> getBackgroundThread() {
        return this.backgroundThread;
    }

    @Override
    protected Object call() {
        return null;
    }

    public void commitChangedFile(String branchName, int i) throws IOException, InterruptedException {
        String changedFile = ParentController.getParentHtmlFileList().get(i).getUpdatedHtml();
        String response = ghApi.putSendUpdateFileRequest(
                ParentController.getParentHtmlFileList().get(i).getPath(),
                Base64.getEncoder().encodeToString(changedFile.getBytes(StandardCharsets.UTF_8)),
                branchName,
                ParentController.getParentHtmlFileList().get(i).getSha(),
                "Commit to file: " + ParentController.getParentHtmlFileList().get(i).getPath()
        );
        fileLog.writeToLog("File Commit: " + response);
    }

    public void createPr(String branchName) throws IOException, InterruptedException {
        String response = ghApi.getPostCreatePRRequest("feat/update docs: " + branchName, branchName, "Changes to static files");
        fileLog.writeToLog("Create PR: " + response);
    }
}
