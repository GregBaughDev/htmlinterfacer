package com.htmlinterfacer.htmlinterfacer.api.htmlFile;

import java.util.Arrays;
import java.util.List;

public class HtmlFileList {
    private static final List<HtmlFile> htmlFiles = Arrays.asList();

    public static void addToHtmlList(HtmlFile html) {
        htmlFiles.add(html);
    }

    public static List<HtmlFile> getHtmlFiles() {
        return htmlFiles;
    }

    public static HtmlFile getHtmlFile(Integer index) {
        return htmlFiles.get(index);
    }
}
