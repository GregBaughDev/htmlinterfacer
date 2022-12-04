package com.htmlinterfacer.htmlinterfacer.api.connection;

import java.io.IOException;
import java.net.URI;
import java.net.http.*;

public class GHApi {
    private final String ghApiUri = "https://api.github.com/";
    private final String repos = "repos/";
    private final String contents = "contents";
    private final String git = "git/";
    private final String trees = "trees/";
    private final String recursiveQuery = "?recursive=true";

    private String owner;
    private String repo;

    private String getRepoContentString = ghApiUri + repos + this.owner + this.repo + contents + recursiveQuery;

    private final HttpClient client = HttpClient.newHttpClient();

    private final HttpRequest getRepoContentRequest = HttpRequest
            .newBuilder()
            .uri(URI.create(ghApiUri + repos + this.owner + this.repo + contents + recursiveQuery))
            .header("Accept", "application/vnd.github+json")
            .header("Authorization", "Bearer " + System.getenv("OAUTH"))
            .GET()
            .build();

    public GHApi (String owner, String repo) {
        this.owner = owner + "/";
        this.repo = repo + "/";
        System.out.println(owner);
        System.out.println(repo);
        System.out.println("NEW GHAPI HAS BEEN INSTANTIATED");
    }

    public HttpResponse<String> getSendRepoContentRequest () throws IOException, InterruptedException {
        System.out.println(this.repo);
        System.out.println(this.owner);
        return client.send(getRepoContentRequest, HttpResponse.BodyHandlers.ofString());
    }


    // Get repo content
    // -> Get recursive and if type == blob, store the url -> get the blob -> get the content

    // Get files

    // Create branch

    // Create commit
}
