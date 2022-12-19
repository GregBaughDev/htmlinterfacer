package com.htmlinterfacer.htmlinterfacer.log;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class FileLog {
    private final File fileLog = new File("./fileLog.txt");

    public FileLog() throws IOException {
    }

    public void writeToLog(String log) throws IOException {
        try {
            FileWriter fileWriter = new FileWriter(fileLog, true);
            fileWriter.append(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss").format(LocalDateTime.now()));
            fileWriter.append(" -> " + log + "\n");
            fileWriter.close();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }
}
