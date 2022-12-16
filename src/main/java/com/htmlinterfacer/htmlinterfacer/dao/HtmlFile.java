package com.htmlinterfacer.htmlinterfacer.dao;

public class HtmlFile {
    private final String originalHtml;

    private String updatedHtml;

    private final String sha;

    private final String path;

    public HtmlFile (String html, String sha, String path) {
        this.originalHtml = html;
        this.updatedHtml = html;
        this.sha = sha;
        this.path = path;
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

    public String getSha () {
        return this.sha;
    }

    public String getPath () {
        return this.path;
    }

}
