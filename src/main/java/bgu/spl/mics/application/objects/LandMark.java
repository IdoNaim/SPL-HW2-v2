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


}
