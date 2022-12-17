package com.htmlinterfacer.htmlinterfacer.api.connection;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.htmlinterfacer.htmlinterfacer.api.response.File;
import com.htmlinterfacer.htmlinterfacer.api.response.Ref;
import com.htmlinterfacer.htmlinterfacer.api.response.Repo;
import com.htmlinterfacer.htmlinterfacer.log.FileLog;

import java.io.IOException;
import java.net.URI;
import java.net.http.*;
import java.util.List;

public class GHApi {
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final FileLog fileLog = new FileLog();

    private final String ghApiUri = "https://api.github.com/";
    private final String repos = "repos/";
    private final String contents = "contents";
    private final String git = "git/";
    private final String heads = "heads/";
    private final String pulls = "pulls";
    private final String refs = "refs";
    private final String recursiveQuery = "?recursive=true";

    private String owner = System.getenv("GHOWNER") + "/";
    private String repo = System.getenv("GHREPO") + "/";

    private String getRepoContentString = ghApiUri + repos + owner + repo + contents + recursiveQuery;

    private String getFileContentString = ghApiUri + repos + owner + repo + contents + "/";

    private String getRefsString = ghApiUri + repos + owner + repo + git + refs + "/" + heads;

    private String postRefsString = ghApiUri + repos + owner + repo + git + refs;

    private String putUpdateFileString = ghApiUri + repos + owner + repo + contents + "/";

    private String postCreatePRString = ghApiUri + repos + owner + repo + pulls;

    private final HttpClient client = HttpClient.newHttpClient();

    public GHApi() throws IOException {
    }

    private HttpRequest getRepoContentRequest() {
        return HttpRequest
                .newBuilder()
                .uri(URI.create(getRepoContentString))
                .header("Accept", "application/vnd.github+json")
                .header("Authorization", "Bearer " + System.getenv("OAUTH"))
                .GET()
                .build();
    }

    private HttpRequest getFileContentRequest(String file) {
        return HttpRequest
                .newBuilder()
                .uri(URI.create(getFileContentString + file))
                .header("Accept", "application/vnd.github+json")
                .header("Authorization", "Bearer " + System.getenv("OAUTH"))
                .GET()
                .build();
    }

    private HttpRequest getRefsRequest() {
        return HttpRequest
                .newBuilder()
                .uri(URI.create(getRefsString))
                .header("Accept", "application/vnd.github+json")
                .header("Authorization", "Bearer " + System.getenv("OAUTH"))
                .GET()
                .build();
    }

    private HttpRequest postRefsRequest(String branchName, String sha) {
        return HttpRequest
                .newBuilder()
                .uri(URI.create(postRefsString))
                .header("Accept", "application/vnd.github+json")
                .header("Authorization", "Bearer " + System.getenv("OAUTH"))
                .POST(
                        HttpRequest.BodyPublishers.ofString("{\"ref\" : \"refs/heads/" + branchName + "\", \"sha\" : \"" + sha + "\" }")
                )
                .build();
    }

    private HttpRequest putUpdateFileRequest(String path, String contents, String branch, String sha, String commitMsg) {
        HttpRequest.BodyPublisher request = HttpRequest.BodyPublishers.ofString(
                "{\"message\": \"" + commitMsg + "\", " +
                        "\"content\": \"" + contents + "\", " +
                        "\"branch\": \"" + branch + "\", " +
                        "\"sha\": \"" + sha + "\" }");
        return HttpRequest
                .newBuilder()
                .uri(URI.create(putUpdateFileString + path))
                .header("Accept", "application/vnd.github+json")
                .header("Authorization", "Bearer " + System.getenv("OAUTH"))
                .PUT(request)
                .build();
    }

    private HttpRequest postCreatePRRequest(String title, String head, String body) {
        HttpRequest.BodyPublisher request = HttpRequest.BodyPublishers.ofString(
            "{\"title\": \"" + title + "\", " +
                    "\"head\": \"" + head + "\", " +
                    "\"body\": \"" + body + "\", " +
                    "\"base\" : \"main\"}");
        return HttpRequest
                .newBuilder()
                .uri(URI.create(postCreatePRString))
                .header("Accept", "application/vnd.github+json")
                .header("Authorization", "Bearer " + System.getenv("OAUTH"))
                .POST(request)
                .build();
    }

    public List<Repo> getSendRepoContentRequest() throws IOException, InterruptedException {
        HttpResponse<String> response = client.send(getRepoContentRequest(), HttpResponse.BodyHandlers.ofString());
        fileLog.writeToLog("getSendRepoContentRequest: " + response.headers());
        return objectMapper.readValue(response.body(), new TypeReference<List<Repo>>(){});
    }

    public File getSendFileContentRequest(String file) throws IOException, InterruptedException {
        HttpResponse<String> response = client.send(getFileContentRequest(file), HttpResponse.BodyHandlers.ofString());
        fileLog.writeToLog("getSendFileContentRequest: " + response.headers());
        return objectMapper.readValue(response.body(), new TypeReference<File>(){});
    }

    public List<Ref> getSendRefsRequest() throws IOException, InterruptedException {
        HttpResponse<String> response = client.send(getRefsRequest(), HttpResponse.BodyHandlers.ofString());
        fileLog.writeToLog("getSendRefsRequest: " + response.headers());
        return objectMapper.readValue(response.body(), new TypeReference<List<Ref>>() {});
    }

    public String postSendRefsRequest(String branchName, String sha) throws IOException, InterruptedException {
        HttpResponse<String> response = client.send(postRefsRequest(branchName, sha), HttpResponse.BodyHandlers.ofString());
        fileLog.writeToLog("postSendRefsRequest: " + response.headers());
        return response.body();
    }

    public String putSendUpdateFileRequest(String path, String contents, String branch, String sha, String commitMsg) throws IOException, InterruptedException {
        HttpResponse<String> response = client.send(putUpdateFileRequest(path, contents, branch, sha, commitMsg), HttpResponse.BodyHandlers.ofString());
        fileLog.writeToLog("putSendUpdateFileRequest: " + response.headers());
        return response.body();
    }

    public String getPostCreatePRRequest(String title, String head, String body) throws IOException, InterruptedException {
        HttpResponse<String> response = client.send(postCreatePRRequest(title, head, body), HttpResponse.BodyHandlers.ofString());
        fileLog.writeToLog("getPostCreatePRRequest: " + response.headers());
        return response.body();
    }
    // Get the main branch name from sysenv
    // search through the refs and if it matches grab the sha to create the main branch off of

    // Get repo content
    // -> Get recursive and if type == blob, store the url -> get the blob -> get the content
}
