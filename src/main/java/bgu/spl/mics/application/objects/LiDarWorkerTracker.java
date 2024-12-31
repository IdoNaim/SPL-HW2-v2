package bgu.spl.mics.application.objects;

import java.util.ArrayList;

/**
 * LiDarWorkerTracker is responsible for managing a LiDAR worker.
 * It processes DetectObjectsEvents and generates TrackedObjectsEvents by using data from the LiDarDataBase.
 * Each worker tracks objects and sends observations to the FusionSlam service.
 */
public class LiDarWorkerTracker {


    int id;
    int frequency;
    STATUS status = STATUS.UP;
    ArrayList<TrackedObject> lastTrackedObjects;

    public int getId() {return id;}
    public void setId(int id) {this.id = id;}
    public int getFrequency() {return frequency;}
    public void setFrequency(int frequency) {this.frequency = frequency;}
    public STATUS getStatus() {return status;}
    public void setStatus(STATUS status) {this.status = status;}
    public ArrayList<TrackedObject> getLastTrackedObjects() {return lastTrackedObjects;}
    public void setLastTrackedObjects(ArrayList<TrackedObject> lastTrackedObjects) {this.lastTrackedObjects = lastTrackedObjects;}



}
