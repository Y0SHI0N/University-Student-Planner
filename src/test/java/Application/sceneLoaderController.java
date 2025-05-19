import Application.Controllers.sceneLoaderController;

import Application.*;
import Application.Database.DatabaseConnection;
import Application.StageController;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.ConnectException;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

class sceneLoaderControllerTest {
    @BeforeAll
    static void runJavaFX() throws InterruptedException{
        Platform.startup(()->{});
    }

    @Test
    void testLoginSceneLoadsSuccessfully() throws InterruptedException {
    }
    @Test
    void testRegisterSceneLoadsSuccessfully() throws InterruptedException {
    }
    @Test
    void testMapSceneLoadsSuccessfully() throws InterruptedException {
    }
    @Test
    void testHomeSceneLoadsSuccessfully() throws InterruptedException {
    }
    @Test
    void testGoalSceneLoadsSuccessfully() throws InterruptedException {
    }
    @Test
    void testProfileSceneLoadsSuccessfully() throws InterruptedException {
    }
    @Test
    void testCalendarSceneLoadsSuccessfully() throws InterruptedException {
    }
    @Test
    void testIfScenesFailWithInvalidPaths() throws InterruptedException {
    }

}
