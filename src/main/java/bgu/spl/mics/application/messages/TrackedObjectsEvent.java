package bgu.spl.mics.application.messages;

import bgu.spl.mics.Broadcast;
import bgu.spl.mics.Event;
import bgu.spl.mics.application.objects.StampedDetectedObjects;

public class TrackedObjectsEvent implements Event {
    private StampedDetectedObjects objects;
    private int detectionTime;

    public TrackedObjectsEvent(StampedDetectedObjects objectsList, int time){
        this.objects=objectsList;
        this.detectionTime=time;
    }
}
