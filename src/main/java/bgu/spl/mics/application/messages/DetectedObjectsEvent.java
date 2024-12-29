package bgu.spl.mics.application.messages;

import bgu.spl.mics.Event;
import bgu.spl.mics.application.objects.StampedDetectedObjects;

public class DetectedObjectsEvent implements Event<Boolean> {
    private String sender;
    private StampedDetectedObjects objects;
    private int detectionTime;

    public DetectedObjectsEvent(String sender,StampedDetectedObjects objectsList, int time){
        this.sender = sender;
        this.objects=objectsList;
        this.detectionTime=time;
    }
    public int getDetectionTime(){
        return detectionTime;
    }
    public StampedDetectedObjects getDetectedObjects(){
        return objects;
    }
    public String getSender(){
        return this.sender;
    }
    public String toString(){
        return "time: "+getDetectedObjects()+ "objects: "+ getDetectedObjects().toString();
    }
}
