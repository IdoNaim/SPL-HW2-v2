package bgu.spl.mics.application.messages;

import bgu.spl.mics.Event;
import bgu.spl.mics.application.objects.StampedDetectedObjects;

public class DetectObjectsEvent implements Event {
    private String sender;
    private StampedDetectedObjects objects;
    private int detectionTime;

    public DetectObjectsEvent(String sender,StampedDetectedObjects objectsList, int time){
        this.sender = sender;
        this.objects=objectsList;
        this.detectionTime=time;
    }
}
