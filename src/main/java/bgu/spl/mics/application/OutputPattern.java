package bgu.spl.mics.application;

import bgu.spl.mics.application.objects.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class OutputPattern {
    String error;
    String faultySensor;
    Map<String,StampedDetectedObjects> lastCamerasFrames;
    Map<String,ArrayList<TrackedObject>> lastLiDarWorkerTrackersFrame;
    List<Pose> poses;
    StatisticalFolder statistics = StatisticalFolder.getInstance();
    List<LandMark> landmarks;

    public OutputPattern(String errorString, String faultySensor, Map<String,StampedDetectedObjects> lastCamerasFrames, Map<String,ArrayList<TrackedObject>> lastLiDarWorkerTrackersFrame, List<Pose> poses, List<LandMark> landmarks) {
        this.error = errorString;
        this.faultySensor = faultySensor;
        this.lastCamerasFrames = lastCamerasFrames;
        this.lastLiDarWorkerTrackersFrame = lastLiDarWorkerTrackersFrame;
        this.poses = poses;
        this.landmarks = landmarks;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getFaultySensor() {
        return faultySensor;
    }

    public void setFaultySensor(String faultySensor) {
        this.faultySensor = faultySensor;
    }

    public Map<String, StampedDetectedObjects> getLastCamerasFrames() {
        return lastCamerasFrames;
    }

    public void setLastCamerasFrames(Map<String, StampedDetectedObjects> lastCamerasFrames) {
        this.lastCamerasFrames = lastCamerasFrames;
    }

    public Map<String, ArrayList<TrackedObject>> getLastLiDarWorkerTrackersFrame() {
        return lastLiDarWorkerTrackersFrame;
    }

    public void setLastLiDarWorkerTrackersFrame(Map<String, ArrayList<TrackedObject>> lastLiDarWorkerTrackersFrame) {
        this.lastLiDarWorkerTrackersFrame = lastLiDarWorkerTrackersFrame;
    }

    public List<Pose> getPoses() {
        return poses;
    }

    public void setPoses(List<Pose> poses) {
        this.poses = poses;
    }

    public StatisticalFolder getInstance() {
        return statistics;
    }

    public void setInstance(StatisticalFolder instance) {
        this.statistics = instance;
    }

    public List<LandMark> getLandmarks() {
        return landmarks;
    }

    public void setLandmarks(List<LandMark> landmarks) {
        this.landmarks = landmarks;
    }
}
