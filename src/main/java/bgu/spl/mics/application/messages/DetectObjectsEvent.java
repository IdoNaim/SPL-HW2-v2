package bgu.spl.mics.application.messages;

import bgu.spl.mics.Event;
import bgu.spl.mics.application.objects.StampedDetectedObjects;

public class DetectObjectsEvent implements Event {
    private StampedDetectedObjects objects;
    private int detectionTime;

    public DetectObjectsEvent(StampedDetectedObjects objectsList, int time){
        this.objects=objectsList;
        this.detectionTime=time;
    }
}
