module com.htmlinterfacer.htmlinterfacer {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;
    requires com.fasterxml.jackson.databind;
    requires java.net.http;
    requires io.github.cdimascio.dotenv.java;
    requires jdk.crypto.ec;

    opens com.htmlinterfacer.htmlinterfacer to javafx.fxml;
    exports com.htmlinterfacer.htmlinterfacer;
    opens com.htmlinterfacer.htmlinterfacer.controller to javafx.fxml;
    exports com.htmlinterfacer.htmlinterfacer.controller;
    opens com.htmlinterfacer.htmlinterfacer.api.record to com.fasterxml.jackson.databind;
    exports com.htmlinterfacer.htmlinterfacer.api.record;
    exports com.htmlinterfacer.htmlinterfacer.service;
    opens com.htmlinterfacer.htmlinterfacer.service to javafx.fxml;
    exports com.htmlinterfacer.htmlinterfacer.task;
    opens com.htmlinterfacer.htmlinterfacer.task to javafx.fxml;
    exports com.htmlinterfacer.htmlinterfacer.dao;
}