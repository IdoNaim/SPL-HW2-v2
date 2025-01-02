package bgu.spl.mics.application.objects;

import bgu.spl.mics.application.messages.PoseEvent;
import bgu.spl.mics.application.messages.TerminatedBroadcast;
import bgu.spl.mics.application.messages.TrackedObjectsEvent;

import javax.sound.midi.Track;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Manages the fusion of sensor data for simultaneous localization and mapping (SLAM).
 * Combines data from multiple sensors (e.g., LiDAR, camera) to build and update a global map.
 * Implements the Singleton pattern to ensure a single instance of FusionSlam exists.
 */
public class FusionSlam {

    ArrayList<LandMark> landmarks;
    ArrayList<Pose> Poses;
    ArrayList<TrackedObject> pendingList;
    private int lastPoseTick;
    private int sensors;
    // Singleton instance holder
    private static class FusionSlamHolder {
        private static FusionSlam instance = new FusionSlam();
    }
    private FusionSlam(){
        landmarks = new ArrayList<>();
        Poses = new ArrayList<>();
        lastPoseTick = 0;
        pendingList = new ArrayList<>();
    }
    public static FusionSlam getInstance(){
        return FusionSlamHolder.instance;
    }

    public void handleTick(int time){
        for(TrackedObject obj : pendingList){
            if(obj.getTime() <= getLastPoseTick() ){
                updateLandmarks(obj);
                pendingList.remove(obj);
            }
        }

    }
    public void handleTrackedObjects(TrackedObjectsEvent e){
        ArrayList<TrackedObject> lst = e.getTrackedObjects();
        for (TrackedObject obj : lst) {
            if(obj.getTime() <= getLastPoseTick()){
                updateLandmarks(obj);
            }
            else{
                pendingList.add(obj);
            }
        }
    }
    public void handlePoseEvent(PoseEvent e){
        Poses.add(e.getCurrentPose());
        if(e.getCurrentPose().getTime()>= lastPoseTick)
            this.lastPoseTick = e.getCurrentPose().getTime();
    }
    /**
        argument obj must do: obj.getTime() <= lastPoseTick
     */
    public ArrayList<CloudPoint> getGlobalCoords(TrackedObject obj){
        ArrayList<CloudPoint> result = new ArrayList<>();
        ArrayList<CloudPoint> relativeCoords = obj.getCoordinates();
        for(CloudPoint point:relativeCoords){
            Pose robotPose = getRobotPose(obj.getTime());
            double thetaRad = robotPose.getYaw()*(Math.PI/180);
            double cosTheta = Math.cos(thetaRad);
            double sinTheta = Math.sin(thetaRad);
            double xglobal = cosTheta * point.getX() - sinTheta * point.getY() + robotPose.getX();
            double yglobal = sinTheta * point.getX() +cosTheta * point.getY() + robotPose.getY();
            CloudPoint globalPoint = new CloudPoint(xglobal,yglobal);
            result.add(globalPoint);
        }
        return result;
    }
    /**
     argument obj must do: obj.getTime() <= lastPoseTick
     */
    public void updateLandmarks(TrackedObject obj){
        boolean found = false;
        int index = 0;
        for (int i = 0; i < landmarks.size() && !found; i++) {
            if (landmarks.get(i).getId().equals(obj.getId())) {
                found = true;
                index = i;
            }
        }
        ArrayList<CloudPoint> realCoords = getGlobalCoords(obj);
        if (found){
            LandMark oldLandmark = landmarks.get(index);
            ArrayList<CloudPoint> coordinates = oldLandmark.getCoordinates();
            for (int i = 0 ; i< coordinates.size(); i++) {
                coordinates.get(i).update(realCoords.get(i));
            }
        }
        else{
            landmarks.add(new LandMark(obj.getId(), obj.getDescription(),realCoords));
        }
    }
    public int getLastPoseTick(){
        return lastPoseTick;
    }
    public Pose getRobotPose(int time){
        for(Pose pose: Poses){
            if(pose.getTime() == time){
                return pose;
            }
        }
        return null;
    }
    public boolean handleTerminated(TerminatedBroadcast c){
        setSensors(getSensors()-1);
        if (sensors == 0){
            return true;
        }
        return false;
    }
    public void setSensors(int sensors){
        this.sensors = sensors;
    }
    public int getSensors(){
        return sensors;
    }
}
