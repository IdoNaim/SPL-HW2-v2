package bgu.spl.mics.application.objects;

import bgu.spl.mics.application.messages.DetectedObjectsEvent;
import bgu.spl.mics.application.messages.TrackedObjectsEvent;

import java.util.ArrayList;

/**
 * LiDarWorkerTracker is responsible for managing a LiDAR worker.
 * It processes DetectObjectsEvents and generates TrackedObjectsEvents by using data from the LiDarDataBase.
 * Each worker tracks objects and sends observations to the FusionSlam service.
 */
public class LiDarWorkerTracker {




    private int id;
    private int frequency;
    private STATUS status;
    private ArrayList<TrackedObject> lastTrackedObjects;

    public LiDarWorkerTracker(int id, int frequency){
        this.id = id;
        this.frequency = frequency;
        status = STATUS.DOWN;
        lastTrackedObjects = new ArrayList<>();
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
       return null if there is error
     */
    public TrackedObjectsEvent handleDetectedObjects(DetectedObjectsEvent e){
        lastTrackedObjects = getTrackedObjects(e.getDetectedObjects());
        if(lastTrackedObjects == null){
            return null;
        }
        for (TrackedObject obj: lastTrackedObjects){
            if (obj.getId() == "Error"){
                return null;
            }
        }
        return new TrackedObjectsEvent("LidarWorkerTracker", lastTrackedObjects);
    }
    /*
    returns empty list if no detection was made
    return null if Detected objects dont match Tracked objects
    */
    public ArrayList<TrackedObject> getTrackedObjects(StampedDetectedObjects objects){
        return null; //TODO: implement
    }

    public int getId() {return id;}
    public void setId(int id) {this.id = id;}
    public int getFrequency() {return frequency;}
    public void setFrequency(int frequency) {this.frequency = frequency;}
    public STATUS getStatus() {return status;}
    public void setStatus(STATUS status) {this.status = status;}
    public ArrayList<TrackedObject> getLastTrackedObjects() {return lastTrackedObjects;}
    public void setLastTrackedObjects(ArrayList<TrackedObject> lastTrackedObjects) {this.lastTrackedObjects = lastTrackedObjects;}


}
