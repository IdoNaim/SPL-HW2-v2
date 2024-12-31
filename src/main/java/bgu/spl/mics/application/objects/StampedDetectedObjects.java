package bgu.spl.mics.application.objects;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents objects detected by the camera at a specific timestamp.
 * Includes the time of detection and a list of detected objects.
 */
public class StampedDetectedObjects {

    int time;
    ArrayList<DetectedObject> detectedObjects;

    public StampedDetectedObjects(int time) {
        this.time = time;
        this.detectedObjects = new ArrayList<>();
    }


}
