package com.htmlinterfacer.htmlinterfacer;

import com.htmlinterfacer.htmlinterfacer.dao.HtmlFile;
import javafx.collections.ObservableList;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

public class InitialiseDataService extends Service<ObservableList<HtmlFile>> {
    @Override
    protected Task<ObservableList<HtmlFile>> createTask() {
        return new Initialiser();
    }
}
