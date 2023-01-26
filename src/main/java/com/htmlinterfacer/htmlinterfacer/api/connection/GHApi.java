package com.htmlinterfacer.htmlinterfacer.api.connection;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.htmlinterfacer.htmlinterfacer.api.record.File;
import com.htmlinterfacer.htmlinterfacer.api.record.Ref;
import com.htmlinterfacer.htmlinterfacer.api.record.Repo;
import com.htmlinterfacer.htmlinterfacer.log.FileLog;
import io.github.cdimascio.dotenv.Dotenv;

import java.net.URI;
import java.net.http.*;
import java.util.List;

public class GHApi {
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final FileLog fileLog = new FileLog();
    private final HttpClient client = HttpClient.newHttpClient();
    private final Dotenv dotenv = Dotenv.load();

    private HttpRequest getRepoContentRequest() {
        return HttpRequest
                .newBuilder()
                .uri(URI.create(GHQueryURI.GET_REPO_CONTENT_STRING.getQuery()))
                .header(GHQueryURI.HEADER_ACCEPT_KEY.getQuery(), GHQueryURI.HEADER_ACCEPT_VALUE.getQuery())
                .header(GHQueryURI.HEADER_AUTH_KEY.getQuery(), GHQueryURI.HEADER_AUTH_VALUE.getQuery())
                .GET()
                .build();
    }

    private HttpRequest getFileContentRequest(String file) {
        return HttpRequest
                .newBuilder()
                .uri(URI.create(GHQueryURI.GET_FILE_CONTENT_STRING.getQuery() + file))
                .header(GHQueryURI.HEADER_ACCEPT_KEY.getQuery(), GHQueryURI.HEADER_ACCEPT_VALUE.getQuery())
                .header(GHQueryURI.HEADER_AUTH_KEY.getQuery(), GHQueryURI.HEADER_AUTH_VALUE.getQuery())
                .GET()
                .build();
    }

    private HttpRequest getRefsRequest() {
        return HttpRequest
                .newBuilder()
                .uri(URI.create(GHQueryURI.GET_REFS_STRING.getQuery()))
                .header(GHQueryURI.HEADER_ACCEPT_KEY.getQuery(), GHQueryURI.HEADER_ACCEPT_VALUE.getQuery())
                .header(GHQueryURI.HEADER_AUTH_KEY.getQuery(), GHQueryURI.HEADER_AUTH_VALUE.getQuery())
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
                .header(GHQueryURI.HEADER_ACCEPT_KEY.getQuery(), GHQueryURI.HEADER_ACCEPT_VALUE.getQuery())
                .header(GHQueryURI.HEADER_AUTH_KEY.getQuery(), GHQueryURI.HEADER_AUTH_VALUE.getQuery())
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
                .header(GHQueryURI.HEADER_ACCEPT_KEY.getQuery(), GHQueryURI.HEADER_ACCEPT_VALUE.getQuery())
                .header(GHQueryURI.HEADER_AUTH_KEY.getQuery(), GHQueryURI.HEADER_AUTH_VALUE.getQuery())
                .PUT(request)
                .build();
    }

    private HttpRequest postCreatePRRequest(String title, String head, String body) {
        HttpRequest.BodyPublisher request = HttpRequest.BodyPublishers.ofString(
            "{\"title\": \"" + title + "\", " +
                    "\"head\": \"" + head + "\", " +
                    "\"body\": \"" + body + "\", " +
                    "\"base\" : \"" + dotenv.get("BASE_BRANCH") + "\" }");
        return HttpRequest
                .newBuilder()
                .uri(URI.create(GHQueryURI.POST_CREATE_PR_STRING.getQuery()))
                .header(GHQueryURI.HEADER_ACCEPT_KEY.getQuery(), GHQueryURI.HEADER_ACCEPT_VALUE.getQuery())
                .header(GHQueryURI.HEADER_AUTH_KEY.getQuery(), GHQueryURI.HEADER_AUTH_VALUE.getQuery())
                .POST(request)
                .build();
    }

    public List<Repo> getSendRepoContentRequest() {
        try {
            HttpResponse<String> response = client.send(getRepoContentRequest(), HttpResponse.BodyHandlers.ofString());
            fileLog.writeToLog("getSendRepoContentRequest: " + response.headers());
            return objectMapper.readValue(response.body(), new TypeReference<>() {});
        } catch (Exception e) {
            fileLog.writeToLog("getSendRepoContentRequest exception: " + e);
            return null;
        }
    }

    public File getSendFileContentRequest(String file) {
        try {
            HttpResponse<String> response = client.send(getFileContentRequest(file), HttpResponse.BodyHandlers.ofString());
            fileLog.writeToLog("getSendFileContentRequest: " + response.headers());
            return objectMapper.readValue(response.body(), new TypeReference<>() {});
        } catch (Exception e) {
            fileLog.writeToLog("getSendFileContentRequest exception: " + e);
            return null;
        }
    }

    public List<Ref> getSendRefsRequest() {
        try {
            HttpResponse<String> response = client.send(getRefsRequest(), HttpResponse.BodyHandlers.ofString());
            fileLog.writeToLog("getSendRefsRequest: " + response.headers());
            return objectMapper.readValue(response.body(), new TypeReference<>() {});
        } catch (Exception e) {
            fileLog.writeToLog("getSendRefsRequest exception: " + e);
            return null;
        }
    }

    public String postSendRefsRequest(String branchName, String sha) {
        try {
            HttpResponse<String> response = client.send(postRefsRequest(branchName, sha), HttpResponse.BodyHandlers.ofString());
            fileLog.writeToLog("postSendRefsRequest: " + response.headers());
            return response.body();
        } catch (Exception e) {
            fileLog.writeToLog("postSendRefsRequest exception: " + e);
            return null;
        }
    }

    public String putSendUpdateFileRequest(String path, String contents, String branch, String sha, String commitMsg) {
        try {
            HttpResponse<String> response = client.send(putUpdateFileRequest(path, contents, branch, sha, commitMsg), HttpResponse.BodyHandlers.ofString());
            fileLog.writeToLog("putSendUpdateFileRequest: " + response.headers());
            return response.body();
        } catch (Exception e) {
            fileLog.writeToLog("putSendUpdateFileRequest exception: " + e);
            return null;
        }
    }

    public String getPostCreatePRRequest(String title, String head, String body) {
        try {
            HttpResponse<String> response = client.send(postCreatePRRequest(title, head, body), HttpResponse.BodyHandlers.ofString());
            fileLog.writeToLog("getPostCreatePRRequest: " + response.headers());
            return response.body();
        } catch (Exception e) {
            fileLog.writeToLog("getPostCreatePRRequest exception: " + e);
            return null;
        }
    }
}
