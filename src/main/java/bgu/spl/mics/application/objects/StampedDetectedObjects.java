package bgu.spl.mics.application.objects;

import java.util.ArrayList;

/**
 * Represents objects detected by the camera at a specific timestamp.
 * Includes the time of detection and a list of detected objects.
 */
public class StampedDetectedObjects {

    int time;
    ArrayList<DetectedObject> DetectedObjects;

    public StampedDetectedObjects(int time, ArrayList<DetectedObject> objects){
        this.time = time;
        DetectedObjects = objects;
        // TODO: DetectedObjects = ?
    }
}
