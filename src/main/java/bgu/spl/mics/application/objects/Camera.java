package bgu.spl.mics.application.objects;

import bgu.spl.mics.application.messages.DetectObjectsEvent;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a camera sensor on the robot.
 * Responsible for detecting objects in the environment.
 */
public class Camera {

    private int id;
    private int frequency;
    private STATUS status = STATUS.UP;
    private ArrayList<StampedDetectedObjects> detectedObjectsList;
    private String camera_key;

    public int getFrequency() {return frequency;}
    public void setFrequency(int frequency) {this.frequency = frequency;}
    public String getCamera_key() {return camera_key;}
    public void setCamera_key(String camera_key) {this.camera_key = camera_key;}
    public ArrayList<StampedDetectedObjects> getDetectedObjectsList() {return detectedObjectsList;}
    public void setDetectedObjectsList(ArrayList<StampedDetectedObjects> detectedObjectsList) {this.detectedObjectsList = detectedObjectsList;}
    public STATUS getStatus() {return status;}
    public void setStatus(STATUS status) {this.status = status;}
    public int getId() {return id;}
    public void setId(int id) {this.id = id;}

    public void Up(){
        this.status = STATUS.UP;
    }
    public void Down(){
        status = STATUS.DOWN;
    }
    public void Error(){
        status = STATUS.ERROR;
    }
    /*
    return Null if there is error
     */
    public DetectObjectsEvent handleTick(int time){

        for(StampedDetectedObjects sdo : detectedObjectsList){
            if(sdo.time == time){
                return new DetectObjectsEvent(camera_key, sdo, time + frequency);
            }
        }
        return new DetectObjectsEvent(camera_key, new StampedDetectedObjects(time), time + frequency);


//        detectedObjectsList = getDetectedObjects(time);
//        for(DetectedObject obj : detectedObjectsList){
//            if(obj.getId().equals("ERROR")){
//                return null;
//            }
//        }
//        StampedDetectedObjects result = new StampedDetectedObjects(time, detectedObjectList);
//        return new DetectObjectsEvent("camera",result, time +frequency);

    }
    /*
    returns empty list if no objects detected
     */
