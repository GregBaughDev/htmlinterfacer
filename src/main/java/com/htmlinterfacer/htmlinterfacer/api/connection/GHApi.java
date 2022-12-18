package com.htmlinterfacer.htmlinterfacer.api.connection;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.htmlinterfacer.htmlinterfacer.api.record.File;
import com.htmlinterfacer.htmlinterfacer.api.record.Ref;
import com.htmlinterfacer.htmlinterfacer.api.record.Repo;
import com.htmlinterfacer.htmlinterfacer.log.FileLog;

import java.io.IOException;
import java.net.URI;
import java.net.http.*;
import java.util.List;

public class GHApi {
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final FileLog fileLog = new FileLog();
    private final HttpClient client = HttpClient.newHttpClient();

    public GHApi() throws IOException {
    }

    private HttpRequest getRepoContentRequest() {
        return HttpRequest
                .newBuilder()
                .uri(URI.create(GHQueryURI.GET_REPO_CONTENT_STRING.getQuery()))
                .header("Accept", "application/vnd.github+json")
                .header("Authorization", "Bearer " + System.getenv("OAUTH"))
                .GET()
                .build();
    }

    private HttpRequest getFileContentRequest(String file) {
        return HttpRequest
                .newBuilder()
                .uri(URI.create(GHQueryURI.GET_FILE_CONTENT_STRING.getQuery() + file))
                .header("Accept", "application/vnd.github+json")
                .header("Authorization", "Bearer " + System.getenv("OAUTH"))
                .GET()
                .build();
    }

    private HttpRequest getRefsRequest() {
        return HttpRequest
                .newBuilder()
                .uri(URI.create(GHQueryURI.GET_REFS_STRING.getQuery()))
                .header("Accept", "application/vnd.github+json")
                .header("Authorization", "Bearer " + System.getenv("OAUTH"))
                .GET()
                .build();
    }

    private HttpRequest postRefsRequest(String branchName, String sha) {
        HttpRequest.BodyPublisher request = HttpRequest.BodyPublishers.ofString(
                "{\"ref\" : \"refs/heads/" + branchName + "\", " +
                        "\"sha\" : \"" + sha + "\" }");
        return HttpRequest
                .newBuilder()
                .uri(URI.create(GHQueryURI.POST_REFS_STRING.getQuery()))
                .header("Accept", "application/vnd.github+json")
                .header("Authorization", "Bearer " + System.getenv("OAUTH"))
                .POST(request)
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
                .uri(URI.create(GHQueryURI.PUT_UPDATE_FILE_STRINGS.getQuery() + path))
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
                .uri(URI.create(GHQueryURI.POST_CREATE_PR_STRING.getQuery()))
                .header("Accept", "application/vnd.github+json")
                .header("Authorization", "Bearer " + System.getenv("OAUTH"))
                .POST(request)
                .build();
    }

    public List<Repo> getSendRepoContentRequest() throws IOException, InterruptedException {
        try {
            HttpResponse<String> response = client.send(getRepoContentRequest(), HttpResponse.BodyHandlers.ofString());
            fileLog.writeToLog("getSendRepoContentRequest: " + response.headers());
            return objectMapper.readValue(response.body(), new TypeReference<>() {});
        } catch (Exception e) {
            fileLog.writeToLog("getSendRepoContentRequest exception: " + e);
            return null;
        }
    }

    public File getSendFileContentRequest(String file) throws IOException, InterruptedException {
        try {
            HttpResponse<String> response = client.send(getFileContentRequest(file), HttpResponse.BodyHandlers.ofString());
            fileLog.writeToLog("getSendFileContentRequest: " + response.headers());
            return objectMapper.readValue(response.body(), new TypeReference<>() {});
        } catch (Exception e) {
            fileLog.writeToLog("getSendFileContentRequest exception: " + e);
            return null;
        }
    }

    public List<Ref> getSendRefsRequest() throws IOException, InterruptedException {
        try {
            HttpResponse<String> response = client.send(getRefsRequest(), HttpResponse.BodyHandlers.ofString());
            fileLog.writeToLog("getSendRefsRequest: " + response.headers());
            return objectMapper.readValue(response.body(), new TypeReference<>() {});
        } catch (Exception e) {
            fileLog.writeToLog("getSendRefsRequest exception: " + e);
            return null;
        }
    }

    public String postSendRefsRequest(String branchName, String sha) throws IOException, InterruptedException {
        try {
            HttpResponse<String> response = client.send(postRefsRequest(branchName, sha), HttpResponse.BodyHandlers.ofString());
            fileLog.writeToLog("postSendRefsRequest: " + response.headers());
            return response.body();
        } catch (Exception e) {
            fileLog.writeToLog("postSendRefsRequest exception: " + e);
            return null;
        }
    }

    public String putSendUpdateFileRequest(String path, String contents, String branch, String sha, String commitMsg) throws IOException, InterruptedException {
        try {
            HttpResponse<String> response = client.send(putUpdateFileRequest(path, contents, branch, sha, commitMsg), HttpResponse.BodyHandlers.ofString());
            fileLog.writeToLog("putSendUpdateFileRequest: " + response.headers());
            return response.body();
        } catch (Exception e) {
            fileLog.writeToLog("putSendUpdateFileRequest exception: " + e);
            return null;
        }
    }

    public String getPostCreatePRRequest(String title, String head, String body) throws IOException, InterruptedException {
        try {
            HttpResponse<String> response = client.send(postCreatePRRequest(title, head, body), HttpResponse.BodyHandlers.ofString());
            fileLog.writeToLog("getPostCreatePRRequest: " + response.headers());
            return response.body();
        } catch (Exception e) {
            fileLog.writeToLog("getPostCreatePRRequest exception: " + e);
            return null;
        }
    }
    // Get the main branch name from sysenv
    // search through the refs and if it matches grab the sha to create the main branch off of

    // Get repo content
    // -> Get recursive and if type == blob, store the url -> get the blob -> get the content
}
