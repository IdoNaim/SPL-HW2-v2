package bgu.spl.mics.application.messages;

import bgu.spl.mics.Broadcast;
import bgu.spl.mics.Event;
import bgu.spl.mics.application.objects.StampedDetectedObjects;
import bgu.spl.mics.application.objects.TrackedObject;

import java.util.ArrayList;
import java.util.List;

public class TrackedObjectsEvent implements Event<Boolean> {
    private String sender;
    private ArrayList<TrackedObject> trackedObjects;

    public TrackedObjectsEvent(ArrayList<TrackedObject> trackedObjects){
        this.sender=sender;
        this.trackedObjects=trackedObjects;
    }
    public ArrayList<TrackedObject> getTrackedObjects(){
        return this.trackedObjects;
    }
    public String getSender(){
        return this.sender;
    }
    public String toString(){
        return this.trackedObjects.toString();
    }
}
