package com.htmlinterfacer.htmlinterfacer.dao;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

class HtmlFileTest {
    private HtmlFile htmlFile;

    @BeforeEach
    void setUp() {
        htmlFile = new HtmlFile("Test html", "1234", "test/file.html");
    }

    @Test
    @DisplayName("Should return false when html has not been altered")
    void testIsNotAltered() {
        assertFalse(htmlFile.isAltered());
    }

    @Test
    @DisplayName("Should return true when html has been altered")
    void testIsAltered() {
        htmlFile.setUpdatedHtml("Updated html");

        assertTrue(htmlFile.isAltered());
    }

    @Test
    @DisplayName("Should return original and updated html when altered")
    void testGetHtmlContentAltered() {
        htmlFile.setUpdatedHtml("Updated html");

        assertEquals("Test html", htmlFile.getOriginalHtml());
        assertEquals("Updated html", htmlFile.getUpdatedHtml());
    }

    @Test
    @DisplayName("Should return original html when not altered")
    void testGetHtmlContent() {
        assertEquals("Test html", htmlFile.getUpdatedHtml());
        assertEquals("Test html", htmlFile.getOriginalHtml());
    }
}