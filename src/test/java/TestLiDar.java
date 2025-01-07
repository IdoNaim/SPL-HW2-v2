import static org.junit.jupiter.api.Assertions.*;

import bgu.spl.mics.application.messages.DetectedObjectsEvent;
import bgu.spl.mics.application.messages.TrackedObjectsEvent;
import bgu.spl.mics.application.objects.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class TestLiDar {
    LiDarWorkerTracker liDar = new LiDarWorkerTracker();
    @BeforeEach
    void setUp(){
        liDar.setFrequency(2);
        liDar.setCurrentTick(3);
        List<Double> doubleList = new ArrayList<>();
        doubleList.add(1.2);
        doubleList.add(1.0);
        ArrayList<List<Double>> cloudpoints = new ArrayList<>();
        cloudpoints.add(doubleList);
        liDar.getLdb().getCloudPoints().add(new StampedCloudPoints("test1", 1,cloudpoints));
        liDar.getLdb().getCloudPoints().add(new StampedCloudPoints("test1", 3,cloudpoints));
    }
    @Test
    void lidarTestCreateTrackedObjectEvent(){
        ArrayList<DetectedObject> list = new ArrayList<>();
        list.add(new DetectedObject("test1","test1"));
        StampedDetectedObjects detectedObjects = new StampedDetectedObjects(1,list);
        DetectedObjectsEvent event = new DetectedObjectsEvent("test", detectedObjects, 1);
        TrackedObjectsEvent e = liDar.handleDetectedObjects(event);
        assertEquals("test1",e.getTrackedObjects().get(0).getId());
        assertEquals(1,e.getTrackedObjects().get(0).getTime());
        assertEquals(1.2,e.getTrackedObjects().get(0).getCoordinates().get(0).getX());
        assertEquals(1.0, e.getTrackedObjects().get(0).getCoordinates().get(0).getY());
        
    }
    @Test
    void lidarTestAddToPendingList(){
        ArrayList<DetectedObject> list = new ArrayList<>();
        list.add(new DetectedObject("test1","test1"));
        StampedDetectedObjects detectedObjects = new StampedDetectedObjects(3,list);
        DetectedObjectsEvent event = new DetectedObjectsEvent("test", detectedObjects, 3);
        TrackedObjectsEvent e = liDar.handleDetectedObjects(event);
        assertTrue(e.isEmpty());
        ArrayList<TrackedObject> pendinglist = liDar.getPendingList();
        assertEquals("test1",pendinglist.get(0).getId());
        assertEquals(3,pendinglist.get(0).getTime());
        assertEquals(1.2,pendinglist.get(0).getCoordinates().get(0).getX());
        assertEquals(1.0, pendinglist.get(0).getCoordinates().get(0).getY());
    }

}
