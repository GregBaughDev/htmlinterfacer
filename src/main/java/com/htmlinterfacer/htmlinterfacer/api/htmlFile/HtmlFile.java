package com.htmlinterfacer.htmlinterfacer.api.htmlFile;

public class HtmlFile {
    private final String originalHtml;

    private String updatedHtml;

    private Boolean isAltered;

    public HtmlFile (String html) {
        this.originalHtml = html;
        this.updatedHtml = html;
    }

    public Boolean isAltered () {
        return this.originalHtml.length() != this.updatedHtml.length();
    }

    public void setUpdatedHtml (String updatedHtml) {
        this.updatedHtml = updatedHtml;
    }

    public String getUpdatedHtml () {
        return this.updatedHtml;
    }

}
