package bgu.spl.mics.application.objects;

import java.util.ArrayList;

/**
 * Represents a camera sensor on the robot.
 * Responsible for detecting objects in the environment.
 */
public class Camera {

    int id;
    int frequency;
    STATUS status;
    ArrayList<DetectedObject> detectedObjectList;

    public Camera(int id, int frequency){
        this.id = id;
        this.frequency = frequency;
        status = STATUS.DOWN;
        detectedObjectList = new ArrayList<>();
    }


}
