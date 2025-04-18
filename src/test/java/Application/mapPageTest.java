import Application.Controllers.mapPageController;
import org.junit.jupiter.api.Test;

import javafx.scene.canvas.Canvas;
import javafx.scene.control.ListView;

import static org.junit.jupiter.api.Assertions.*;

class MapPageTest {

    @Test
    void heatMapExistsTest() {
        Canvas heatMap = new mapPageController().heatMap;
        assertNotNull(heatMap, "'HeatMap' (.fxml Canvas Object) must exist in current scope.");
    }

    @Test
    void busyListExistsTest() {
        ListView<Integer> busyLocationList = new mapPageController().busyLocationList;
        assertNotNull(busyLocationList, "'BusyLocation' (.fxml ListView Object) must exist in current scope.");
    }

    @Test
    void quietListExistsTest() {
        ListView<Integer> quietLocationList = new mapPageController().quietLocationList;
        assertNotNull(quietLocationList, "QuietLocation ListView must exist in current scope.");
    }

    @Test
    void circleWidthVisibleTest() {
        /// Circle Width must be a non-negative value or circles will not appear.
        int circlePresetWidth = new mapPageController().CirclePreset.getWidth();
        assertTrue(circlePresetWidth > 0);
    }

    @Test
    void circleStepVisibleTest() {
        /// Circle Step must be a non-negative value or heat bubbles will not grow.
        int circlePresetStepValue = new mapPageController().CirclePreset.getStepValue();
        assertTrue(circlePresetStepValue > 0);
    }

    @Test
    void circleHueVisibleTest() {
        /// Circle Hue must be a non-negative value or circle's color will not change.
        int circlePresetHueValue = new mapPageController().CirclePreset.getHueValue();
        assertTrue(circlePresetHueValue > 0);
    }

    @Test
    void campusBuildingsExistsTest() {
        /// Campus Buildings must contain at least one building (as each building contains pixel-marked
        /// coordinates of block locations, and therefore, should not be cleared).
        int campusBuildings_length = new mapPageController().CampusBuildings.length;
        assertTrue(campusBuildings_length > 0);
    }
}
