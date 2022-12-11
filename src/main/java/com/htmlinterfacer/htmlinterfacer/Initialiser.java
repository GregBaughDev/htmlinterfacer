package com.htmlinterfacer.htmlinterfacer;

import com.htmlinterfacer.htmlinterfacer.api.connection.GHApi;
import com.htmlinterfacer.htmlinterfacer.api.response.File;
import com.htmlinterfacer.htmlinterfacer.dao.HtmlFile;
import javafx.collections.ObservableList;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

import java.io.IOException;
import java.util.Base64;
import java.util.List;

public class Initialiser extends Task {
    private Service<Void> backgroundThread;

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
                            // Try catch -> throw exception not correct file type
                            // create a util class which checks file type
                            File fileResponse = GHApi.getSendFileContentRequest(envFile);
                            byte[] fileContents = Base64.getMimeDecoder().decode(fileResponse.getContent());
                            htmlFileList.add(new HtmlFile(new String(fileContents), fileResponse.getSha()));
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
    protected Object call() throws Exception {
        return null;
    }
}
