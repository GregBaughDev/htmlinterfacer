module com.htmlinterfacer.htmlinterfacer {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;
    requires org.kohsuke.github.api;
    requires com.fasterxml.jackson.databind;
    requires java.net.http;


    opens com.htmlinterfacer.htmlinterfacer to javafx.fxml;
    exports com.htmlinterfacer.htmlinterfacer;
    opens com.htmlinterfacer.htmlinterfacer.controller to javafx.fxml;
    exports com.htmlinterfacer.htmlinterfacer.controller;
    opens com.htmlinterfacer.htmlinterfacer.api.response to com.fasterxml.jackson.databind;
    exports com.htmlinterfacer.htmlinterfacer.api.response;
}