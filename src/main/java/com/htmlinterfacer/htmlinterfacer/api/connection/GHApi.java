package com.htmlinterfacer.htmlinterfacer.api.connection;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.htmlinterfacer.htmlinterfacer.api.response.File;
import com.htmlinterfacer.htmlinterfacer.api.response.Links;
import com.htmlinterfacer.htmlinterfacer.api.response.Ref;
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
    private static final String git = "git/";
    private static final String heads = "heads/";
    private final String trees = "trees/";
    private static final String refs = "refs/";
    private static final String recursiveQuery = "?recursive=true";

    private static String owner = System.getenv("GHOWNER") + "/";
    private static String repo = System.getenv("GHREPO") + "/";

    private static String getRepoContentString = ghApiUri + repos + owner + repo + contents + recursiveQuery;

    private static String getFileContentString = ghApiUri + repos + owner + repo + contents + "/";

    private static String getRefsString = ghApiUri + repos + owner + repo + git + refs + heads;

    private static String postRefsString = ghApiUri + repos + owner + repo + git + refs;

    private static final HttpClient client = HttpClient.newHttpClient();

    private static HttpRequest getRepoContentRequest = HttpRequest
            .newBuilder()
            .uri(URI.create(getRepoContentString))
            .header("Accept", "application/vnd.github+json")
            .header("Authorization", "Bearer " + System.getenv("OAUTH"))
            .GET()
            .build();

    private static HttpRequest getFileContentRequest(String file) {
        return HttpRequest
                .newBuilder()
                .uri(URI.create(getFileContentString + file))
                .header("Accept", "application/vnd.github+json")
                .header("Authorization", "Bearer " + System.getenv("OAUTH"))
                .GET()
                .build();
    }

    private static HttpRequest getRefsRequest() {
        return HttpRequest
                .newBuilder()
                .uri(URI.create(getRefsString))
                .header("Accept", "application/vnd.github+json")
                .header("Authorization", "Bearer " + System.getenv("OAUTH"))
                .GET()
                .build();
    }

    private static HttpRequest postRefsRequest(String branchName, String sha) {
        return HttpRequest
                .newBuilder()
                .uri(URI.create(postRefsString))
                .header("Accept", "application/vnd.github+json")
                .header("Authorization", "Bearer " + System.getenv("OAUTH"))
                .POST(
                        HttpRequest.BodyPublishers.ofString("{\"ref\" : \"refs/heads/" + branchName + ", \"sha\" : " + sha + "\" } ")
                )
                .build();


    }

    static public List<Repo> getSendRepoContentRequest() throws IOException, InterruptedException {
        HttpResponse<String> response = client.send(getRepoContentRequest, HttpResponse.BodyHandlers.ofString());
        return objectMapper.readValue(response.body(), new TypeReference<List<Repo>>(){});
    }

    static public File getSendFileContentRequest(String file) throws IOException, InterruptedException {
        HttpResponse<String> response = client.send(getFileContentRequest(file), HttpResponse.BodyHandlers.ofString());
        return objectMapper.readValue(response.body(), new TypeReference<File>(){});
    }

    static public List<Ref> getSendRefsRequest() throws IOException, InterruptedException {
        HttpResponse<String> response = client.send(getRefsRequest(), HttpResponse.BodyHandlers.ofString());
        return objectMapper.readValue(response.body(), new TypeReference<List<Ref>>() {});
    }

    static public String postSendRefsRequest(String branchName, String sha) throws IOException, InterruptedException {
        HttpResponse<String> response = client.send(postRefsRequest(branchName, sha), HttpResponse.BodyHandlers.ofString());
        // TO DO NEXT TIME -> Issue with the JSON
        return response.body();
    }

    // Get the main branch name from sysenv
    // search through the refs and if it matches grab the sha to create the main branch off of


    // Get repo content
    // -> Get recursive and if type == blob, store the url -> get the blob -> get the content


    // Create branch

    // Create commit
}
