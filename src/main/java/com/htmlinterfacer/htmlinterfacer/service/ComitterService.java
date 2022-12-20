package com.htmlinterfacer.htmlinterfacer.service;

import com.htmlinterfacer.htmlinterfacer.task.Comitter;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

import java.io.IOException;

public class ComitterService extends Service<Void> {
    @Override
    protected Task<Void> createTask() {
        try {
            return new Comitter();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
