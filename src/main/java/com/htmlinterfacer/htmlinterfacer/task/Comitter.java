package com.htmlinterfacer.htmlinterfacer.task;

import com.htmlinterfacer.htmlinterfacer.log.FileLog;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

import java.io.IOException;

public class Comitter extends Task {
    private Service<Void> backgroundThread;
    private FileLog fileLog = new FileLog();

    public Comitter() throws IOException {
    }

    public void createBackgroundThread() {
        backgroundThread = new Service<Void>() {
            @Override
            protected Task<Void> createTask() {
                return new Task<Void>() {
                    @Override
                    protected Void call() throws Exception {
                        System.out.println("new bg thread");
                        return null;
                    }
                };
            }
        };
    }

    public Service<Void> getBackgroundThread() {
        return this.backgroundThread;
    }

    @Override
    protected Object call() {
        return null;
    }
}
