package com.htmlinterfacer.htmlinterfacer.alert;

import com.htmlinterfacer.htmlinterfacer.log.FileLog;
import javafx.application.Platform;
import javafx.scene.control.Alert;

public class ApplicationAlert {
    private static final FileLog fileLog = new FileLog();
    public static final String errorTitle = "Error";
    public static final String errorBody = "There is an error. The application will now close. Please restart the application.";

    public static void createAlert(Alert.AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(content);
        fileLog.writeToLog("Alert shown - Title: " + title + ", Content: " + content);
        alert.show();
        alert.setOnCloseRequest(e -> Platform.exit());
    }
}
