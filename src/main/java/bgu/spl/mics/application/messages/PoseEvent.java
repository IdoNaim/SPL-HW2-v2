package bgu.spl.mics.application.messages;

import bgu.spl.mics.Event;
import bgu.spl.mics.application.objects.Pose;

public class PoseEvent implements Event {
    private String sender;
    private Pose pose;
    public PoseEvent(String sender,double x, double y, double yaw){
        this.sender = sender;
        this.pose = new Pose(x,y,yaw); //TODO: maybe change this
    }

}
