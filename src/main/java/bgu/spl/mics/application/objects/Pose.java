package bgu.spl.mics.application.objects;

import com.google.gson.annotations.SerializedName;

/**
 * Represents the robot's pose (position and orientation) in the environment.
 * Includes x, y coordinates and the yaw angle relative to a global coordinate system.
 */
public class Pose {

    float x;
    float y;
    float yaw;
    int time;

    public Pose(float x, float y, float yaw, int Time) {
        this.x = x;
        this.y = y;
        this.yaw = yaw;
        this.time = Time;
    }
    public int getTime(){
        return this.time;
    }

    public float  getX(){
        return x;
    }
    public float getY(){
        return y;
    }
    public float getYaw(){
        return yaw;
    }

}
