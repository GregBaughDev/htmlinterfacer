package com.htmlinterfacer.htmlinterfacer.api.connection;

import org.kohsuke.github.GHContent;
import org.kohsuke.github.GHTree;
import org.kohsuke.github.GitHub;
import org.kohsuke.github.GitHubBuilder;

import java.io.IOException;

public class GHConnection {
    GitHub gitHub = new GitHubBuilder().withPassword(System.getenv("GHUSER"), System.getenv("GHPASS")).build();

    GHContent repo = gitHub.getRepository(System.getenv("GHREPO")).getFileContent(System.getenv("REPO1"));
    GHContent repo2 = gitHub.getRepository(System.getenv("GHREPO")).getFileContent(System.getenv("REPO2"));

    GHTree tree = gitHub.getRepository(System.getenv("GHREPO")).getTree(System.getenv("TREE"));

    public GHContent getRepo () {
        return repo;
    }

    public GHContent getRepo2 () {
        return repo2;
    }

    public GHConnection() throws IOException {
    }

    public GHTree getTree () {
        return tree;
    }
}
