package bgu.spl.mics.application.objects;

import java.util.ArrayList;

/**
 * Represents a landmark in the environment map.
 * Landmarks are identified and updated by the FusionSlam service.
 */
public class LandMark {

    String id;
    String Description;
    ArrayList<CloudPoint> Coordinates;

    public LandMark(String id, String Description, ArrayList<CloudPoint> Coordinates) {
        this.id = id;
        this.Description = Description;
        this.Coordinates = Coordinates;
    }
    public String getId(){
        return id;
    }
    public ArrayList<CloudPoint> getCoordinates(){
        return this.Coordinates;
    }

}
