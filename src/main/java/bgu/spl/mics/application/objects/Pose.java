package bgu.spl.mics.application.objects;

/**
 * Represents the robot's pose (position and orientation) in the environment.
 * Includes x, y coordinates and the yaw angle relative to a global coordinate system.
 */
public class Pose {

    float x;
    float y;
    float yaw;
    int Time;

    public Pose(float x, float y, float yaw, int Time) {
        this.x = x;
        this.y = y;
        this.yaw = yaw;
        this.Time = Time;
    }

}
