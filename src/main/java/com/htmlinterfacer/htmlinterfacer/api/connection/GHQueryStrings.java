package com.htmlinterfacer.htmlinterfacer.api.connection;

import io.github.cdimascio.dotenv.Dotenv;

public enum GHQueryStrings {
    GH_API_URI ("https://api.github.com/"),
    REPOS ("repos/"),
    CONTENTS ("contents"),
    GIT ("git/"),
    HEADS ("heads/"),
    PULLS ("pulls"),
    REFS ("refs"),
    RECURSIVE_QUERY ("?recursive=true"),
    OWNER (EnvironmentVars.owner + "/"),
    REPO (EnvironmentVars.repo + "/");

    private final String query;

    GHQueryStrings(String query) {
        this.query = query;
    }

    public String getQuery() {
        return query;
    }

    private static class EnvironmentVars {
        private static final Dotenv dotenv = Dotenv.load();

        private final static String owner = dotenv.get("GHOWNER");
        private final static String repo = dotenv.get("GHREPO");
    }
}
