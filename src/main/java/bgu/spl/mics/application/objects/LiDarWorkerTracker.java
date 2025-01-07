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



//@INV: for all TrackedObject o in pendingList, o.getTime()+ frequency > currentTick
    private int id;
    private int frequency;
    private STATUS status;
    private ArrayList<TrackedObject> lastTrackedObjects = new ArrayList<>();
    private ArrayList<TrackedObject> pendingList = new ArrayList<TrackedObject>();
    //private ArrayList<DetectedObjectsEvent> pendingList;
    private  int currentTick;
    private LiDarDataBase ldb = LiDarDataBase.getInstance();


    public void Up(){
        this.status = STATUS.UP;
    }
    public void Down(){
        status = STATUS.DOWN;
    }
    public void Error(){
        status = STATUS.ERROR;
    }

    public TrackedObject getTrackedObject(String id, int time){
        return ldb.getTrackedObject(id, time);
    }

    /*
       return null if there is error
     */
    //@PRE:for each DetectedObject o in e.getDetectedObjects.getObjectsArray() there should be a TrackedObjects in the DataBase matching in id and time
    //and o doesn't have id ="ERROR"
    //@POST: if there is Error in the LiDarDataBase in the stampedCloudPoints mathcing the objects in e.getDetectedObjects().getObjectsArray() by tie and id, return null and setStatus(STATUS.ERROR)
    //       if there is no more data in DB, return null
    // if e.getDetectedObjects().getTime() + this.frequency <= this.currTick, return a TrackedObjectsEvent to which holds TrackedObjects matching the Detected Objects in e.getDetectedObjects().getObjectsArray() in time and id
    // else return TrackedObjectsEvent with empty list
    public TrackedObjectsEvent handleDetectedObjects(DetectedObjectsEvent e) {
        if (ldb.isEmpty()) {
            return null;
        }
        ArrayList<DetectedObject> list = e.getDetectedObjects().getObjectsArray();
        if (e.getDetectionTime() + this.frequency <= currentTick) {
            ArrayList<TrackedObject> result = new ArrayList<>();

            for (DetectedObject obj : list) {
                TrackedObject obj2 = ldb.getTrackedObject(obj.id, e.getDetectionTime());
                if (obj2 != null) {
                    result.add(obj2);
                } else {
                    setStatus(STATUS.ERROR);
                    return null;
                }
            }

            if(!result.isEmpty())
                lastTrackedObjects = result;
            StatisticalFolder.getInstance().setNumTrackedObjects(StatisticalFolder.getInstance().getNumTrackedObjects() + result.size());
            return new TrackedObjectsEvent("LiDarWorkerTracker" + id, result);
        } else {
            for (DetectedObject obj : list) {
                TrackedObject obj2 = ldb.getTrackedObject(obj.id, e.getDetectionTime());
                if (obj2 != null) {
                    pendingList.add(obj2);
                } else {
                    setStatus(STATUS.ERROR);
                    return null;
                }
            }
        }
        return new TrackedObjectsEvent("LiDarWorkerTracker"+id, new ArrayList<>());
    }

//            for(DetectedObject obj: list){
//                boolean found = false;
//                for(int i= 0; i< allObjects.size() && !found; i++){
//                    TrackedObject obj2 = allObjects.get(i);
//                    if(obj2.getId().equals("ERROR")){
//                        setStatus(STATUS.ERROR);
//                        return null;
//                    }
//                    if(obj.getId().equals(obj2.getId()) && e.getDetectionTime() == obj2.getTime()){
//                        trackedObjects.add(obj2);
//                        allObjects.remove(obj2);
//                    }
//                }
//            }
//            return new TrackedObjectsEvent("LiDarWorkerTracker"+id, result);
//        }
//        else{
//            ArrayList<TrackedObject> allObjects = getAllTrackedObjects();
//            for(DetectedObject obj : list){
//                for(TrackedObject obj2: allObjects){
//                    if(obj.getId().equals(obj2.getId()) && e.getDetectionTime() == obj2.getTime()){
//                        pendingList.add(obj2);
//                        allObjects.remove(obj2);
//                    }
//                }
//            }
//            return new TrackedObjectsEvent("LiDarWorkerTracker"+id, new ArrayList<>());
//        }
//    }
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
    //@PRE:
    public TrackedObjectsEvent handleTick(int time){
        currentTick = time;
        if(ldb.isEmpty() && pendingList.isEmpty()){
            return null;
        }
        ArrayList<TrackedObject> result = new ArrayList<>();
        for(TrackedObject obj :pendingList){
            if(obj.getTime() + this.frequency <= time){
                if(obj.isError()){
                    setStatus(STATUS.ERROR);
                    return null;
                }
                result.add(obj);
            }
        }

        if(!result.isEmpty())
            lastTrackedObjects = result;

        for(TrackedObject obj : result){
            pendingList.remove(obj);
        }
        StatisticalFolder.getInstance().setNumTrackedObjects(StatisticalFolder.getInstance().getNumTrackedObjects() + result.size());
        return new TrackedObjectsEvent("LidarWorkerTracker"+id,result);
    }
    public void setCurrentTick(int time){
        currentTick = time;
    }
    public LiDarDataBase getLdb(){
        return ldb;
    }
    public ArrayList<TrackedObject> getPendingList(){
        return this.pendingList;
    }
}
