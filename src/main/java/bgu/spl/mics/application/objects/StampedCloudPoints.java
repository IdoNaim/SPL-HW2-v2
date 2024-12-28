package bgu.spl.mics.application.objects;

import java.util.ArrayList;
import java.util.List;
/**
 * Represents a group of cloud points corresponding to a specific timestamp.
 * Used by the LiDAR system to store and process point cloud data for tracked objects.
 */
public class StampedCloudPoints {

    String id;
    int time;
    ArrayList<List<Double>> cloudPoints;



}
