package Application.Controllers;

import Application.Database.DatabaseConnection;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

import java.awt.*;
import java.io.Console;
import java.sql.Array;
import java.util.*;
import java.sql.Connection;
import java.sql.SQLException;

public class mapPageController extends sceneLoaderController {
    @FXML private Canvas heatMap;
    // stores all vital information regarding a building's code, x/y location and classes (in that order)
    private final Building[] CampusBuildings = {
            new Building('P', 255, 100, new Integer[]{302}),
            new Building('S', 75, 165, new Integer[]{1, 201, 302}),
            new Building('Z', 125, 165, new Integer[]{201, 203, 203})
    };

    static class Building {
        Character BlockLetter;
        Integer xPos;
        Integer yPos;
        Integer[] bookedRooms;

        Building(Character BlockLetter, Integer xPos, Integer yPos, Integer[] bookedRooms) {
            this.BlockLetter = BlockLetter;
            this.xPos = xPos;
            this.yPos = yPos;
            this.bookedRooms = bookedRooms;
        }
    }

    public void renderHeatmap() {
        try {
            GraphicsContext graphics = heatMap.getGraphicsContext2D();
            for (Building building : CampusBuildings) {
                // The more rooms there are, the more layers of circle there are, with each warmer colors.
                for (int room_count = building.bookedRooms.length; room_count > 0; room_count--) {
                    // room stores the current room increment, essentially how many layers deep the for loop is
                    // starts on the highest heat (at the core of the ring, and slowly draws it's way outwards)
                    drawBuilding(building, graphics, room_count);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Color calculateHeat(int room_count) {
        // the starting heat is determined by the maximum amount of rooms
        // The values range from 0 to 255 to encompass the full RGB scale of color.
        // One room is negated to make buildings with one room appear the same as zero rooms - blue with no trace of heat
        // (since buildings with zero rooms are impossible to generate naturally, this is never observed)
        // 51 was used as 255 / 5 = 51, and 5 rooms was the highest number that could be feasibly rendered before the heatmap became too unreadable
        int intial_blue_hue = Math.clamp((room_count - 1) * 51, 0, 255);
        return (Color.rgb(255, 0, intial_blue_hue));
    }

    public void drawBuilding(Building building, GraphicsContext graphics, int room_count) {
        // set draw color to 'calculateHeat's output
        graphics.setFill(calculateHeat(building.bookedRooms.length));
        // 30 is the intial circle size, chosen due to it being roughly the size of a building
        float circle_width = 30 + (20 * room_count);
        // removing half of the circle width from the coords is to compensate for the offset of the circle being in the top right corner (thanks javafx)
        graphics.fillOval(building.xPos - (circle_width / 2), building.yPos - (circle_width / 2), circle_width, circle_width); // first pair edits the x/y coords while the second edits the x/y size
    }

    public void initialize() {
        renderHeatmap();
    }
}
