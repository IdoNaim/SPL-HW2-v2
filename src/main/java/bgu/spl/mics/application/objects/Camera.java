package bgu.spl.mics.application.objects;

import bgu.spl.mics.application.messages.DetectedObjectsEvent;

import java.util.ArrayList;

/**
 * Represents a camera sensor on the robot.
 * Responsible for detecting objects in the environment.
 */
public class Camera {

    private int id;
    private int frequency;
    private STATUS status;
    private ArrayList<DetectedObject> detectedObjectList;

    public Camera(int id, int frequency){
        this.id = id;
        this.frequency = frequency;
        status = STATUS.DOWN;
        detectedObjectList = new ArrayList<>();
    }
    public void Up(){
        this.status = STATUS.UP;
    }
    public void Down(){
        status = STATUS.DOWN;
    }
    public void Error(){
        status = STATUS.ERROR;
    }
    /*
    return Null if there is error
     */
    public DetectedObjectsEvent handleTick(int time){
        detectedObjectList = getDetectedObjects(time);
        for(DetectedObject obj : detectedObjectList){
            if(obj.getId() == "ERROR"){
                return null;
            }
        }
        StampedDetectedObjects result = new StampedDetectedObjects(time, detectedObjectList);
        return new DetectedObjectsEvent("camera",result, time +frequency);


    }
    /*
    returns empty list if no objects detected
     */
    private ArrayList<DetectedObject> getDetectedObjects(int time){
        //TODO: implement with Gson
        return null;
    }

}
