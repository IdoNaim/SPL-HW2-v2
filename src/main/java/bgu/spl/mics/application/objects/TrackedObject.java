package bgu.spl.mics.application.objects;

import java.util.ArrayList;

/**
 * Represents an object tracked by the LiDAR.
 * This object includes information about the tracked object's ID, description, 
 * time of tracking, and coordinates in the environment.
 */
public class TrackedObject {

    private String id;
    private int time;
    private String description;
    CloudPoint[] coordinates;

    public TrackedObject(String id, int time, String description) {
        this.id = id;
        this.time = time;
        this.description = description;
        // TODO: coordinates = ?
    }
    public String getId(){
        return id;
    }
    public String getDescription(){
        return description;
    }
    public CloudPoint[] getCoordinates(){
        return coordinates;
    }
    public int getTime(){
        return time;
    }



}
