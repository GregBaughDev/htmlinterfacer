package com.htmlinterfacer.htmlinterfacer.api.htmlFile;

public class HtmlFile {
    private final String originalHtml;

    private String updatedHtml;

    private Boolean isAltered;

    public HtmlFile (String html) {
        this.originalHtml = html;
        this.updatedHtml = html;
    }

    public Boolean isAltered (String originalHtml) {
        return this.isAltered = originalHtml.length() == updatedHtml.length();
    }

    public void setUpdatedHtml (String updatedHtml) {
        this.updatedHtml = updatedHtml;
    }

    public String getUpdatedHtml () {
        return this.updatedHtml;
    }

}
