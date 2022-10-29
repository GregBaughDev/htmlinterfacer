package com.htmlinterfacer.htmlinterfacer.api.htmlFile;

import java.util.List;

public class HtmlFileList {
    private final List<HtmlFile> htmlFiles;

    public HtmlFileList(List<HtmlFile> htmlFiles) {
        this.htmlFiles = htmlFiles;
    }

    List<HtmlFile> getHtmlFiles() {
        return this.htmlFiles;
    }
}
