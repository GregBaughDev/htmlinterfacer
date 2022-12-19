package com.htmlinterfacer.htmlinterfacer.api.connection;

public enum GHQueryStrings {
    GH_API_URI ("https://api.github.com/"),
    REPOS ("repos/"),
    CONTENTS ("contents"),
    GIT ("git/"),
    HEADS ("heads/"),
    PULLS ("pulls"),
    REFS ("refs"),
    RECURSIVE_QUERY ("?recursive=true"),
    OWNER (System.getenv("GHOWNER") + "/"),
    REPO (System.getenv("GHREPO") + "/");

    private final String query;

    private GHQueryStrings(String query) {
        this.query = query;
    }

    public String getQuery() {
        return query;
    }
}
