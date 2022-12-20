package com.htmlinterfacer.htmlinterfacer.task;

import com.htmlinterfacer.htmlinterfacer.api.connection.GHApi;
import com.htmlinterfacer.htmlinterfacer.api.record.File;
import com.htmlinterfacer.htmlinterfacer.dao.HtmlFile;
import com.htmlinterfacer.htmlinterfacer.log.FileLog;
import javafx.collections.ObservableList;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.regex.Pattern;

public class Initialiser extends Task {
    private GHApi ghApi = new GHApi();
    private Service<Void> backgroundThread;
    private FileLog fileLog = new FileLog();
    private Pattern pattern = Pattern.compile("html");

    List<String> envFiles = List.of(System.getenv("FILES").split(","));

    public Initialiser() throws IOException {
    }

    public void createBackgroundThread(ObservableList<HtmlFile> htmlFileList) {
        backgroundThread = new Service<Void>() {
            @Override
            protected Task<Void> createTask() {
                return new Task<Void>() {
                    @Override
                    protected Void call() throws Exception {
                        for (String envFile : envFiles) {
                            if (!pattern.matcher(envFile.substring(envFile.length() - 4)).matches()) {
                                fileLog.writeToLog("createBackgroundThread - File is not HTML: " + envFile);
                                return null;
                            }
                            try {
                                File fileResponse = ghApi.getSendFileContentRequest(envFile);
                                byte[] fileContents = Base64.getMimeDecoder().decode(fileResponse.content());
                                htmlFileList.add(new HtmlFile(new String(fileContents), fileResponse.sha(), fileResponse.path()));
                            } catch (Exception e) {
                                fileLog.writeToLog("createBackgroundThread exception: " + e);
                            }
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
}
