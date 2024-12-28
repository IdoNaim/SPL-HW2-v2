package bgu.spl.mics.application.objects;

import java.util.ArrayList;

/**
 * Manages the fusion of sensor data for simultaneous localization and mapping (SLAM).
 * Combines data from multiple sensors (e.g., LiDAR, camera) to build and update a global map.
 * Implements the Singleton pattern to ensure a single instance of FusionSlam exists.
 */
public class FusionSlam {

    ArrayList<LandMark> landmarks;
    ArrayList<Pose> Poses;

    // TODO: Constructor? im not sure needed because of singleton

    // Singleton instance holder
    private static class FusionSlamHolder {
        // TODO: Implement singleton instance logic.
    }
}
