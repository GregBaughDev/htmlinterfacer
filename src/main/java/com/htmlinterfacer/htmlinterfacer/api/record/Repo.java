package com.htmlinterfacer.htmlinterfacer.api.record;

public record Repo(
        String name,
        String path,
        String sha,
        String size,
        String url,
        String html_url,
        String git_url,
        String download_url,
        String type,
        Links _links
) {}
