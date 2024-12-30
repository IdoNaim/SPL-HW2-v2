package bgu.spl.mics.application.objects;

import bgu.spl.mics.application.messages.PoseEvent;

import java.util.ArrayList;

/**
 * Represents the robot's GPS and IMU system.
 * Provides information about the robot's position and movement.
 */
public class GPSIMU {

    int currentTick;
    STATUS status;
    ArrayList<Pose> PoseList;

    public  GPSIMU(int currentTick){
        this.currentTick = currentTick;
        status = STATUS.DOWN;
        PoseList = new ArrayList<>();
    }
    public void Up(){
        this.status = STATUS.UP;
    }
    public void Down(){
        status = STATUS.DOWN;
    }
    public void Error(){
        status = STATUS.ERROR;
    }

    public PoseEvent handleTick(int time){
        currentTick = time;
        Pose currPose = getPose(time);
        PoseList.add(currPose);
        return new PoseEvent("GPSIMU", currPose);
    }
    public Pose getPose(int time){
        //TODO: implement with Gson
    }


}
