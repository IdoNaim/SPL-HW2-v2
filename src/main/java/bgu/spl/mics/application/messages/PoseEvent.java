package bgu.spl.mics.application.messages;

import bgu.spl.mics.Event;
import bgu.spl.mics.application.objects.Pose;

public class PoseEvent implements Event<Boolean> {
    private String sender;
    private Pose pose;
    public PoseEvent(String sender,Pose currPose){
        this.sender = sender;
        this.pose = currPose; //TODO: maybe change this
    }
    public Pose getCurrentPose(){
        return pose;
    }
    public String toString(){
        return pose.toString();
    }
    public String getSender(){
        return this.sender;
    }
}
