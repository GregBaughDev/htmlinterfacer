package com.htmlinterfacer.htmlinterfacer.api.connection;

import com.htmlinterfacer.htmlinterfacer.dao.HtmlFile;
import org.kohsuke.github.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GHConnection {
    GitHub gitHub = new GitHubBuilder().withPassword(System.getenv("GHUSER"), System.getenv("GHPASS")).build();

    List<String> files = Arrays.asList(System.getenv("FILES").split(","));

    List<GHTreeEntry> tree = gitHub.getRepository(System.getenv("GHREPO"))
            .getTree(System.getenv("TREE")).getTree().stream().toList();

    GHTreeEntry repoTree = tree.get(1);
    GHTree innerTree = gitHub.getRepository(System.getenv(("GHREPO"))).getTree(repoTree.getSha());

    List<HtmlFile> htmlFiles = new ArrayList<>();

    public GHRepository getRepo() throws IOException {
        return gitHub.getRepository(System.getenv("GHREPO"));
    }

    public GHConnection() throws IOException {
    }

    public List<HtmlFile> getHtmlFiles() throws IOException {
        for(String file : files) {
            GHContent temp = gitHub.getRepository(System.getenv("GHREPO")).getFileContent(file);
            htmlFiles.add(
                    new HtmlFile(new String(temp.read().readAllBytes()))
            );
        }
        return htmlFiles;
    }

    public List<HtmlFile> getTree () throws IOException {
        System.out.println(repoTree);
        // RETHINK THIS -> JUST GRAB A LIST OF FILES
        // Can we somehow grab the urls and do a similar thing to above?
        innerTree.getTree().forEach(ref -> {
//            try {
                System.out.println(ref.getUrl().getPath());
//                htmlFiles.add(new HtmlFile(new String(gitHub.getRepository(System.getenv("GHREPO")).getFileContent(ref.getUrl().getPath()).read().readAllBytes())));
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
        });
//        for (int i = 0; i < tree.size(); i++) {
//            try {
//                GHContent temp = gitHub.getRepository(System.getenv("GHREPO")).getFileContent(tree.get(i).getPath());
//                System.out.println(tree.get(i).getPath());
//                System.out.println(tree.get(i).getUrl());
//                System.out.println("----------------");
//                htmlFiles.add(new HtmlFile(new String(temp.read().readAllBytes())));
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
        return htmlFiles;
    }
}
