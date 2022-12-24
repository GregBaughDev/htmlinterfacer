package com.htmlinterfacer.htmlinterfacer.service;

import com.htmlinterfacer.htmlinterfacer.task.Committer;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

import java.io.IOException;

public class CommitterService extends Service<Void> {
    @Override
    protected Task<Void> createTask() {
        try {
            return new Committer();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
