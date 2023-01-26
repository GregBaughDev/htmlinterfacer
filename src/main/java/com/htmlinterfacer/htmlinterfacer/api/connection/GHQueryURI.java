package com.htmlinterfacer.htmlinterfacer.api.connection;

public enum GHQueryURI {
    GET_REPO_CONTENT_STRING (
            GHQueryStrings.GH_API_URI.getQuery() +
            GHQueryStrings.REPOS.getQuery() +
            GHQueryStrings.OWNER.getQuery() +
            GHQueryStrings.REPO.getQuery() +
            GHQueryStrings.CONTENTS.getQuery() +
            GHQueryStrings.RECURSIVE_QUERY.getQuery()),
    GET_FILE_CONTENT_STRING (
            GHQueryStrings.GH_API_URI.getQuery() +
            GHQueryStrings.REPOS.getQuery() +
            GHQueryStrings.OWNER.getQuery() +
            GHQueryStrings.REPO.getQuery() +
            GHQueryStrings.CONTENTS.getQuery() + "/"),
    GET_REFS_STRING (
            GHQueryStrings.GH_API_URI.getQuery() +
            GHQueryStrings.REPOS.getQuery() +
            GHQueryStrings.OWNER.getQuery() +
            GHQueryStrings.REPO.getQuery() +
            GHQueryStrings.GIT.getQuery() +
            GHQueryStrings.REFS.getQuery() + "/" +
            GHQueryStrings.HEADS.getQuery()),
    POST_REFS_STRING (
            GHQueryStrings.GH_API_URI.getQuery() +
            GHQueryStrings.REPOS.getQuery() +
            GHQueryStrings.OWNER.getQuery() +
            GHQueryStrings.REPO.getQuery() +
            GHQueryStrings.GIT.getQuery() +
            GHQueryStrings.REFS.getQuery()),
    PUT_UPDATE_FILE_STRINGS (
            GHQueryStrings.GH_API_URI.getQuery() +
            GHQueryStrings.REPOS.getQuery() +
            GHQueryStrings.OWNER.getQuery() +
            GHQueryStrings.REPO.getQuery() +
            GHQueryStrings.CONTENTS.getQuery() + "/"),
    POST_CREATE_PR_STRING(
            GHQueryStrings.GH_API_URI.getQuery() +
            GHQueryStrings.REPOS.getQuery() +
            GHQueryStrings.OWNER.getQuery() +
            GHQueryStrings.REPO.getQuery() +
            GHQueryStrings.PULLS.getQuery()),
    HEADER_ACCEPT_KEY ("Accept"),
    HEADER_ACCEPT_VALUE ("application/vnd.github+json"),
    HEADER_AUTH_KEY ("Authorization"),
    HEADER_AUTH_VALUE ("Bearer " + GHQueryStrings.OAUTH.getQuery());

    private final String queryUri;

    GHQueryURI(String queryUri) {
        this.queryUri = queryUri;
    }

    public String getQuery() {
        return queryUri;
    }
}
