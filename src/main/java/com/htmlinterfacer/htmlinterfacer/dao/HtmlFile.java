package com.htmlinterfacer.htmlinterfacer.dao;

public class HtmlFile {
    private final String originalHtml;

    private String updatedHtml;

    private final String sha;

    public HtmlFile (String html, String sha) {
        this.originalHtml = html;
        this.updatedHtml = html;
        this.sha = sha;
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

}
