package com.htmlinterfacer.htmlinterfacer.controller;

import com.htmlinterfacer.htmlinterfacer.HtmlInterfacer;
import com.htmlinterfacer.htmlinterfacer.api.connection.GHApi;
import com.htmlinterfacer.htmlinterfacer.api.record.File;
import com.htmlinterfacer.htmlinterfacer.api.record.Links;
import com.htmlinterfacer.htmlinterfacer.dao.HtmlFile;
import javafx.collections.FXCollections;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.testfx.api.FxToolkit;

import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.ApplicationTest;
import org.testfx.framework.junit5.Start;
import org.testfx.util.WaitForAsyncUtils;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.testfx.api.FxAssert.verifyThat;

@ExtendWith(ApplicationExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class HomeControllerTest extends ApplicationExtension {
    GHApi ghApi = mock();
    ParentController parentController = mock();

    @BeforeAll
    public void init() throws Exception {
        ApplicationTest.launch(HtmlInterfacer.class);
    }

    @Start
    public void start (Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HtmlInterfacer.class.getResource("home.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        stage.show();
    }

    @AfterAll
    public void afterEachTest() throws TimeoutException {
        FxToolkit.hideStage();
        release(new KeyCode[]{});
        release(new MouseButton[]{});
    }

    @Test
    @DisplayName("handle file change test")
    void handleFileChangeTest() {
        when(ghApi).thenReturn((GHApi) FXCollections.observableArrayList(
                new HtmlFile("<h1>Test content</h1>", "1234", "test/test.html"),
                new HtmlFile("<h1>Second test</h2>", "5678", "test/test2.html")
        ));
        MockedStatic<ParentController> parentControllerMockedStatic = Mockito.mockStatic(ParentController.class);
        parentControllerMockedStatic.when(ParentController::getParentHtmlFileList).thenReturn(FXCollections.observableArrayList(
                new HtmlFile("<h1>Test content</h1>", "1234", "test/test.html"),
                new HtmlFile("<h1>Second test</h2>", "5678", "test/test2.html")
        ));

        WaitForAsyncUtils.waitForFxEvents();
        clickOn("test/test2.html");
        clickOn("#toggleView");

        verifyThat("#textArea", (TextArea textArea) -> textArea.getText().contains("<h1>Second test</h2>"));
    }
}