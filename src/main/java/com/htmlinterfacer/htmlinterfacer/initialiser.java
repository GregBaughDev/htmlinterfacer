package com.htmlinterfacer.htmlinterfacer;

import com.htmlinterfacer.htmlinterfacer.api.htmlFile.HtmlFile;
import com.htmlinterfacer.htmlinterfacer.api.htmlFile.HtmlFileList;

public class initialiser {
    private static HtmlFile test = new HtmlFile("<h2>Again to check formatting</h2>");
    private static HtmlFile test2 = new HtmlFile("<h3>A second for file switching</h3>");
    private static HtmlFile test3 = new HtmlFile("<h4>And again to check this could work</h4>");

    static void initialise() {
        // Call Github from here
        // Add files to HtmlFileList
        HtmlFileList.addToHtmlList(test);
        HtmlFileList.addToHtmlList(test2);
        HtmlFileList.addToHtmlList(test3);
    }
}
