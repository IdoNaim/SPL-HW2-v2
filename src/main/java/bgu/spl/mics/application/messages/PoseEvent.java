package bgu.spl.mics.application.messages;

import bgu.spl.mics.Event;

public class PoseEvent implements Event {
    private double X;
    private double Y;
    private double yaw;
    public PoseEvent(double x, double y, double yaw){
        this.X =x;
        this.Y =y;
        this.yaw=yaw;
    }

}
