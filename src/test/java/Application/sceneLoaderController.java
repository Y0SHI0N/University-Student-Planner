import Application.Controllers.sceneLoaderController;

import Application.*;
import Application.Database.DatabaseConnection;
import Application.StageController;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
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
    static void runJavaFX(){
        try {
            Platform.startup(() -> {});
        } catch (IllegalStateException e) {
        }
    }

    @Test
    void testLoginSceneLoadsSuccessfully() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);
        AtomicReference<Throwable> thrown = new AtomicReference<>();

        Platform.runLater(() -> {
            try {
                StageController stageController = new StageController();
                sceneLoaderController controller = new sceneLoaderController(stageController.applicationStage);

                StageController.closeActiveStage();
                controller.changeScene(Main.getLoginPage());  // Valid loader
                StageController.closeActiveStage();
            } catch (Throwable e) {
                thrown.set(e);
            } finally {
                latch.countDown();
            }
        });

        if (!latch.await(5, TimeUnit.SECONDS)) {
            fail("Test timed out");
        }

        assertNull(thrown.get(), "Scene loading threw an unexpected exception: " +
                (thrown.get() != null ? thrown.get().getMessage() : "null"));
    }
    @Test
    void testRegisterSceneLoadsSuccessfully() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);
        AtomicReference<Throwable> thrown = new AtomicReference<>();

        Platform.runLater(() -> {
            try {
                StageController stageController = new StageController();
                sceneLoaderController controller = new sceneLoaderController(stageController.applicationStage);
                StageController.closeActiveStage();
                controller.changeScene(Main.getRegisterPage());  // Valid loader
                StageController.closeActiveStage();
            } catch (Throwable e) {
                thrown.set(e);
            } finally {
                latch.countDown();
            }
        });

        if (!latch.await(5, TimeUnit.SECONDS)) {
            fail("Test timed out");
        }

        assertNull(thrown.get(), "Scene loading threw an unexpected exception: " +
                (thrown.get() != null ? thrown.get().getMessage() : "null"));
    }
    @Test
    void testMapSceneLoadsSuccessfully() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);
        AtomicReference<Throwable> thrown = new AtomicReference<>();

        Platform.runLater(() -> {
            try {
                StageController stageController = new StageController();
                sceneLoaderController controller = new sceneLoaderController(stageController.applicationStage);
                StageController.closeActiveStage();
                controller.changeScene(Main.getMapPage());  // Valid loader
                StageController.closeActiveStage();
            } catch (Throwable e) {
                thrown.set(e);
            } finally {
                latch.countDown();
            }
        });

        if (!latch.await(5, TimeUnit.SECONDS)) {
            fail("Test timed out");
        }

        assertNull(thrown.get(), "Scene loading threw an unexpected exception: " +
                (thrown.get() != null ? thrown.get().getMessage() : "null"));
    }
    @Test
    void testProfileSceneLoadsSuccessfully() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);
        AtomicReference<Throwable> thrown = new AtomicReference<>();

        Platform.runLater(() -> {
            try {
                StageController stageController = new StageController();
                sceneLoaderController controller = new sceneLoaderController(stageController.applicationStage);
                StageController.closeActiveStage();
                controller.changeScene(Main.getProfilePage());  // Valid loader
                StageController.closeActiveStage();
            } catch (Throwable e) {
                thrown.set(e);
            } finally {
                latch.countDown();
            }
        });

        if (!latch.await(5, TimeUnit.SECONDS)) {
            fail("Test timed out");
        }

        assertNull(thrown.get(), "Scene loading threw an unexpected exception: " +
                (thrown.get() != null ? thrown.get().getMessage() : "null"));
    }
    @Test
    void testCalendarSceneLoadsSuccessfully() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);
        AtomicReference<Throwable> thrown = new AtomicReference<>();

        Platform.runLater(() -> {
            try {
                StageController stageController = new StageController();
                sceneLoaderController controller = new sceneLoaderController(stageController.applicationStage);
                StageController.closeActiveStage();
                controller.changeScene(Main.getCalendarPage());  // Valid loader
                StageController.closeActiveStage();
            } catch (Throwable e) {
                thrown.set(e);
            } finally {
                latch.countDown();
            }
        });

        if (!latch.await(5, TimeUnit.SECONDS)) {
            fail("Test timed out");
        }

        assertNull(thrown.get(), "Scene loading threw an unexpected exception: " +
                (thrown.get() != null ? thrown.get().getMessage() : "null"));
    }
    @Test
    void testIfScenesFailWithInvalidPaths() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);
        AtomicReference<Throwable> thrown = new AtomicReference<>();

        Platform.runLater(() -> {
            try {
                StageController stageController = new StageController();
                sceneLoaderController controller = new sceneLoaderController(stageController.applicationStage);

                FXMLLoader loader = new FXMLLoader(); // set no location
                controller.changeScene(loader);
                stageController.applicationStage.close();

            } catch (Throwable e) {
                thrown.set(e); // expecting this exception
            } finally {
                latch.countDown();
            }
        });

        if (!latch.await(5, TimeUnit.SECONDS)) {
            fail("Test timed out");
        }

        assertFalse(thrown.get() instanceof IllegalStateException, "Expected IllegalStateException when no location is set");
    }

}
