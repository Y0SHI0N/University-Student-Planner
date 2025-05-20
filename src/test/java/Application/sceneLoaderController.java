import Application.Main;
import javafx.fxml.FXMLLoader;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

import static org.junit.jupiter.api.Assertions.*;

class sceneLoaderControllerTest {

    @BeforeAll
    static void runJavaFX() throws InterruptedException{
        Thread thread = new Thread();
    }

    @Test
    void testLoginSceneLoadsSuccessfully() throws InterruptedException {
        try {
            CountDownLatch latch = new CountDownLatch(1);
            AtomicReference<Throwable> thrown = new AtomicReference<>();

            try {
                Main.getLoginPage();
            } catch (Throwable e) {
                thrown.set(e);
            } finally {
                latch.countDown();
            }

            if (!latch.await(5, TimeUnit.SECONDS)) {
                fail("Test timed out");
            }

            assertNull(thrown.get(), "Scene loading threw an unexpected exception: " +
                    (thrown.get() != null ? thrown.get().getMessage() : "null"));
        }catch(UnsupportedOperationException e){
        }
    }
    @Test
    void testRegisterSceneLoadsSuccessfully() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);
        AtomicReference<Throwable> thrown = new AtomicReference<>();

        try {
            Main.getRegisterPage();
        } catch (Throwable e) {
            thrown.set(e);
        } finally {
            latch.countDown();
        }

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

        try {
            Main.getMapPage();
        } catch (Throwable e) {
            thrown.set(e);
        } finally {
            latch.countDown();
        }

        if (!latch.await(5, TimeUnit.SECONDS)) {
            fail("Test timed out");
        }

        assertNull(thrown.get(), "Scene loading threw an unexpected exception: " +
                (thrown.get() != null ? thrown.get().getMessage() : "null"));
    }
    @Test
    void testHomeSceneLoadsSuccessfully() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);
        AtomicReference<Throwable> thrown = new AtomicReference<>();

        try {
            Main.getHomePage();
        } catch (Throwable e) {
            thrown.set(e);
        } finally {
            latch.countDown();
        }

        if (!latch.await(5, TimeUnit.SECONDS)) {
            fail("Test timed out");
        }

        assertNull(thrown.get(), "Scene loading threw an unexpected exception: " +
                (thrown.get() != null ? thrown.get().getMessage() : "null"));
    }
    @Test
    void testGoalSceneLoadsSuccessfully() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);
        AtomicReference<Throwable> thrown = new AtomicReference<>();

        try {
            Main.getGoalsPage();
        } catch (Throwable e) {
            thrown.set(e);
        } finally {
            latch.countDown();
        }

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

        try {
            Main.getProfilePage();
        } catch (Throwable e) {
            thrown.set(e);
        } finally {
            latch.countDown();
        }

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

        try {
            Main.getCalendarPage();
        } catch (Throwable e) {
            thrown.set(e);
        } finally {
            latch.countDown();
        }

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

        try {
            FXMLLoader loader = new FXMLLoader(); // set no location
            loader.load();

        } catch (Throwable e) {
            thrown.set(e); // expecting this exception
        } finally {
            latch.countDown();
        }

        if (!latch.await(5, TimeUnit.SECONDS)) {
            fail("Test timed out");
        }

        assertTrue(thrown.get() instanceof IllegalStateException, "Expected IllegalStateException when no location is set");
    }

}