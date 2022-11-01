package com.htmlinterfacer.htmlinterfacer;

import com.htmlinterfacer.htmlinterfacer.dao.HtmlFile;
import javafx.collections.ObservableList;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

public class Initialiser extends Task {
    private Service<Void> backgroundThread;
    private HtmlFile test = new HtmlFile("<h2>Again to check formatting</h2>");
    private HtmlFile test2 = new HtmlFile("<h3>A second for file switching</h3>");
    private HtmlFile test3 = new HtmlFile("<h4>And again to check this could work</h4>");

    // Create the new GHConnection here
    // Add all files to an array
    // Loop through them in the below

    public void createBackgroundThread(ObservableList<HtmlFile> htmlFileList) {
        backgroundThread = new Service<Void>() {
            @Override
            protected Task<Void> createTask() {
                return new Task<Void>() {
                    @Override
                    protected Void call() throws Exception {
                        htmlFileList.add(test);
                        htmlFileList.add(test2);
                        htmlFileList.add(test3);
                        return null;
                    }
                };
            }
        };
        backgroundThread.restart();
    }

    public Service<Void> getBackgroundThread() {
        return this.backgroundThread;
    }

    @Override
    protected Object call() throws Exception {
        return null;
    }
}
