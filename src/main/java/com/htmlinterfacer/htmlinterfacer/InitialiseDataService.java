package com.htmlinterfacer.htmlinterfacer;

import com.htmlinterfacer.htmlinterfacer.dao.HtmlFile;
import javafx.collections.ObservableList;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

import java.io.IOException;

public class InitialiseDataService extends Service<ObservableList<HtmlFile>> {
    @Override
    protected Task<ObservableList<HtmlFile>> createTask() {
        try {
            return new Initialiser();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
