package com.htmlinterfacer.htmlinterfacer.api.connection;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.htmlinterfacer.htmlinterfacer.api.response.Repo;

import java.io.IOException;
import java.net.URI;
import java.net.http.*;
import java.util.List;

public class GHApi {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    private static final String ghApiUri = "https://api.github.com/";
    private static final String repos = "repos/";
    private static final String contents = "contents";
    private final String git = "git/";
    private final String trees = "trees/";
    private static final String recursiveQuery = "?recursive=true";

    private static String owner = System.getenv("GHOWNER") + "/";
    private static String repo = System.getenv("GHREPO") + "/";

    private static String getRepoContentString = ghApiUri + repos + owner + repo + contents + recursiveQuery;

    private static final HttpClient client = HttpClient.newHttpClient();

    private static HttpRequest getRepoContentRequest = HttpRequest
            .newBuilder()
            .uri(URI.create(getRepoContentString))
            .header("Accept", "application/vnd.github+json")
            .header("Authorization", "Bearer " + System.getenv("OAUTH"))
            .GET()
            .build();

    static public List<Repo> getSendRepoContentRequest () throws IOException, InterruptedException {
        HttpResponse<String> response = client.send(getRepoContentRequest, HttpResponse.BodyHandlers.ofString());
        System.out.println(response.body());
        return objectMapper.readValue(response.body(), new TypeReference<List<Repo>>(){});
    }


    // Get repo content
    // -> Get recursive and if type == blob, store the url -> get the blob -> get the content

    // Get files

    // Create branch

    // Create commit
}
