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
    private ArrayList<TrackedObject> pendingList;
    //private ArrayList<DetectedObjectsEvent> pendingList;
    private  int currentTick;
    private ArrayList<TrackedObject> allTrackedObjects;

    public LiDarWorkerTracker(int id, int frequency){
        this.id = id;
        this.frequency = frequency;
        status = STATUS.DOWN;
        lastTrackedObjects = new ArrayList<>();
        pendingList = new ArrayList<>();
        currentTick =0;
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
        if(getAllTrackedObjects().size() == 0){
            return null;
        }
        ArrayList<DetectedObject> list = e.getDetectedObjects().getObjectsArray();
        if(e.getDetectionTime()+ this.frequency <= currentTick){
            ArrayList<TrackedObject> trackedObjects = new ArrayList<>();
            ArrayList<TrackedObject> allObjects = getAllTrackedObjects();
            for(DetectedObject obj: list){
                boolean found = false;
                for(int i= 0; i< allObjects.size() && !found; i++){
                    TrackedObject obj2 = allObjects.get(i);
                    if(obj2.getId().equals("ERROR")){
                        setStatus(STATUS.ERROR);
                        return null;
                    }
                    if(obj.getId().equals(obj2.getId()) && e.getDetectionTime() == obj2.getTime()){
                        trackedObjects.add(obj2);
                        allObjects.remove(obj2);
                    }
                }
            }
            return new TrackedObjectsEvent("LiDarWorkerTracker"+id,trackedObjects);
        }
        else{
            ArrayList<TrackedObject> allObjects = getAllTrackedObjects();
            for(DetectedObject obj : list){
                for(TrackedObject obj2: allObjects){
                    if(obj.getId().equals(obj2.getId()) && e.getDetectionTime() == obj2.getTime()){
                        pendingList.add(obj2);
                        allObjects.remove(obj2);
                    }
                }
            }
            return new TrackedObjectsEvent("LiDarWorkerTracker"+id, new ArrayList<>());
        }
    }
    /*
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
    }*/
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

    /*public TrackedObjectsEvent handleTick(int time){
        currentTick = time;
        if(getAllTrackedObjects().isEmpty()){
            return null;
        }
        ArrayList<TrackedObject> result = new ArrayList<>();
        for(DetectedObjectsEvent e: pendingList){
            if(e.getDetectionTime()+frequency <= time){
                ArrayList<DetectedObject> list =e.getDetectedObjects().getObjectsArray();
                ArrayList<TrackedObject> allTracked = getAllTrackedObjects();
                for(DetectedObject obj : list){
                    for()
                }
            }
        }
    }*/
    public TrackedObjectsEvent handleTick(int time){
        currentTick = time;
        if(getAllTrackedObjects().size() == 0){
            return null;
        }
        ArrayList<TrackedObject> result = new ArrayList<>();
        for(TrackedObject obj :pendingList){
            if(obj.getTime() +this.frequency <= time){
                if(obj.isError()){
                    setStatus(STATUS.ERROR);
                    return null;
                }
                result.add(obj);
                pendingList.remove(obj);
            }
        }
        lastTrackedObjects = result;
        return new TrackedObjectsEvent("LidarWorkerTracker"+id,result);
    }
    public ArrayList<TrackedObject> getAllTrackedObjects(){
        return allTrackedObjects;
    }
}
