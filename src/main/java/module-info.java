module com.htmlinterfacer.htmlinterfacer {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.htmlinterfacer.htmlinterfacer to javafx.fxml;
    exports com.htmlinterfacer.htmlinterfacer;
    opens com.htmlinterfacer.htmlinterfacer.controller to javafx.fxml;
    exports com.htmlinterfacer.htmlinterfacer.controller;
}