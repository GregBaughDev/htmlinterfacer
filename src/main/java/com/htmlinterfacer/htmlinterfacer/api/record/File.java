package com.htmlinterfacer.htmlinterfacer.api.record;

public record File(
        String name,
        String path,
        String sha,
        Integer size,
        String url,
        String html_url,
        String git_url,
        String download_url,
        String type,
        String content,
        String encoding,
        Links _links
) {}
