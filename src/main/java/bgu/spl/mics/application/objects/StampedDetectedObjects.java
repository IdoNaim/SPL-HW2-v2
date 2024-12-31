package bgu.spl.mics.application.objects;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents objects detected by the camera at a specific timestamp.
 * Includes the time of detection and a list of detected objects.
 */
public class StampedDetectedObjects {

    private int time;

    ArrayList<DetectedObject> detectedObjects;


    public StampedDetectedObjects(int time) {
        this.time = time;
        this.detectedObjects = new ArrayList<>();
    }
    public StampedDetectedObjects(int time, ArrayList<DetectedObject> list){
        this.time = time;
        this.detectedObjects = list;
    }
    public boolean isError(){
        for (DetectedObject obj : detectedObjects){
            if(obj.getId().equals("ERROR")){
                return true;
            }
        }
        return false;
    }
    public boolean isEmpty(){
        return detectedObjects.isEmpty();
    }
    public int getTime(){
        return time;
    }
}
