import static org.junit.jupiter.api.Assertions.*;

import bgu.spl.mics.application.objects.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

public class TestFusionSlam {
    FusionSlam fusionSlam;
    @BeforeEach
    void setUp() {
        fusionSlam = FusionSlam.getInstance();
    }
    @Test
    void testTrackedToLandmark() {
        fusionSlam.getPoses().add(new Pose(1, 1, 2, 3));
        ArrayList<CloudPoint> coordinates = new ArrayList<>();
        coordinates.add(new CloudPoint(1, 1));
        coordinates.add(new CloudPoint(2, 0));
        TrackedObject obj = new TrackedObject("test", 1, "testDesc", coordinates);
        fusionSlam.updateLandmarks(obj);
        LandMark landmark = fusionSlam.getLandmarks().get(0);
        assertEquals(Math.cos(Math.PI / 60) - Math.sin(Math.PI / 60) + 1, landmark.getCoordinates().get(0).getX());
        assertEquals(Math.sin(Math.PI / 60) + Math.cos(Math.PI / 60) + 2, landmark.getCoordinates().get(0).getY());
        assertEquals(Math.cos(Math.PI / 60) * 2 + 1, landmark.getCoordinates().get(1).getX());
        assertEquals(Math.sin(Math.PI / 60) * 2 + 2, landmark.getCoordinates().get(1).getY());
    }
}
