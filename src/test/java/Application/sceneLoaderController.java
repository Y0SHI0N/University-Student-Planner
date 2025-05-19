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
        CountDownLatch latch = new CountDownLatch(1);
        try{
        Platform.startup(() -> latch.countDown());
        }catch(IllegalStateException e){
            latch.countDown();
        }
        latch.await();
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
        CountDownLatch latch = new CountDownLatch(1);
        AtomicReference<Throwable> thrown = new AtomicReference<>();

        Platform.runLater(() -> {
            try {
                FXMLLoader loader = new FXMLLoader(); // set no location
                loader.load();

            } catch (Exception e) {
                thrown.set(e); // expecting this exception
            } finally {
                latch.countDown();
            }
        });

        if (!latch.await(5, TimeUnit.SECONDS)) {
            fail("Test timed out");
        }

        assertTrue(thrown.get() instanceof IllegalStateException, "Expected IllegalStateException when no location is set");
    }

}
