package com.htmlinterfacer.htmlinterfacer.api.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Repo {
    @JsonProperty(value = "name")
    String name;

    @JsonProperty(value = "path")
    String path;

    @JsonProperty(value = "sha")
    String sha;

    @JsonProperty(value = "size")
    String size;

    @JsonProperty(value = "url")
    String url;

    @JsonProperty(value = "html_url")
    String html_url;

    @JsonProperty(value = "git_url")
    String git_url;

    @JsonProperty(value = "download_url")
    String download_url;

    @JsonProperty(value = "type")
    String type;

    @JsonProperty(value = "_links")
    Links _links;

    class Links {
        String self;
        String git;
        String html;
    }

    public Repo() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getSha() {
        return sha;
    }

    public void setSha(String sha) {
        this.sha = sha;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getHtml_url() {
        return html_url;
    }

    public void setHtml_url(String html_url) {
        this.html_url = html_url;
    }

    public String getGit_url() {
        return git_url;
    }

    public void setGit_url(String git_url) {
        this.git_url = git_url;
    }

    public String getDownload_url() {
        return download_url;
    }

    public void setDownload_url(String download_url) {
        this.download_url = download_url;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Links get_links() {
        return _links;
    }

    public void set_links(Links _links) {
        this._links = _links;
    }
}
