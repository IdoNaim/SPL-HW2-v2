package bgu.spl.mics.application.messages;

import bgu.spl.mics.Event;
import bgu.spl.mics.application.objects.Pose;

public class PoseEvent implements Event {
    private Pose pose;
    public PoseEvent(double x, double y, double yaw){
        this.pose = new Pose(x,y,yaw); //TODO: maybe change this
    }

}
