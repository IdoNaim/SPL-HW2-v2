package bgu.spl.mics.application.objects;

import bgu.spl.mics.application.messages.PoseEvent;
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
    // Singleton instance holder
    private static class FusionSlamHolder {
        private static FusionSlam instance = new FusionSlam();
    }
    private FusionSlam(){
        landmarks = new ArrayList<>();
        Poses = new ArrayList<>();
    }
    public static FusionSlam getInstance(){
        return FusionSlamHolder.instance;
    }
    public void handleTick(int time){
       //TODO: implement
    }
    public void handleTrackedObjects(TrackedObjectsEvent e){
        ArrayList<TrackedObject> lst = e.getTrackedObjects();
        for (TrackedObject obj : lst) {
            boolean found = false;
            int index = 0;
            for (int i = 0; i < lst.size() && !found; i++) {
                if (landmarks.get(i).getId() == obj.getId()) {
                    found = true;
                    index = i;
                }
            }
            ArrayList<CloudPoint> realCoords = getGlobalCoords(obj);
            if (found){
                LandMark oldLandmark = landmarks.get(index);
                ArrayList<CloudPoint> coordinates = oldLandmark.getCoordinates();
                int i =0;
                for (CloudPoint point: coordinates) {
                    point.update(realCoords.get(i));
                    i++;
                }

            }
            else{
                landmarks.add(new LandMark(obj.getId(), obj.getDescription(),realCoords));
            }
        }
    }
    public void handlePoseEvent(PoseEvent e){
        Poses.add(e.getCurrentPose());
    }
    /*
    TODO:finish getGlobalCoords, need to also make it so
    TODO: the system only updates and adds landmarks that were detected in a tick that
    TODO: we have its pose
     */
    public ArrayList<CloudPoint> getGlobalCoords(TrackedObject obj){
        ArrayList<CloudPoint> result = new ArrayList<>();
        CloudPoint[] relativeCoords = obj.getCoordinates();
        for(CloudPoint point:relativeCoords){

        }
    }
}
