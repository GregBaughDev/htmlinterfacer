package com.htmlinterfacer.htmlinterfacer;

import com.htmlinterfacer.htmlinterfacer.api.connection.GHConnection;
import com.htmlinterfacer.htmlinterfacer.dao.HtmlFile;
import javafx.collections.ObservableList;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

import java.io.IOException;
import java.util.List;

public class Initialiser extends Task {
    private Service<Void> backgroundThread;
    private HtmlFile test = new HtmlFile("<h2>Again to check formatting</h2>");
    private HtmlFile test2 = new HtmlFile("<h3>A second for file switching</h3>");
    private HtmlFile test3 = new HtmlFile("<h4>And again to check this could work</h4>");
    List<HtmlFile> htmlFiles = List.of(test, test2, test3);
    private GHConnection ghConnection = new GHConnection();

    public Initialiser() throws IOException {
    }

    public void createBackgroundThread(ObservableList<HtmlFile> htmlFileList) {
        backgroundThread = new Service<Void>() {
            @Override
            protected Task<Void> createTask() {
                return new Task<Void>() {
                    @Override
                    protected Void call() throws Exception {
                        for (HtmlFile htmlFile : ghConnection.getHtmlFiles()) {
                            htmlFileList.add(htmlFile);
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
