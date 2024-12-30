package bgu.spl.mics.application.objects;

import bgu.spl.mics.application.messages.DetectObjectsEvent;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.io.FileReader;
import java.util.ArrayList;

/**
 * Represents a camera sensor on the robot.
 * Responsible for detecting objects in the environment.
 */
public class Camera {

    private int id;
    private int frequency;
    private STATUS status;
    private ArrayList<DetectedObject> detectedObjectList;

    public Camera(int id, int frequency){
        this.id = id;
        this.frequency = frequency;
        status = STATUS.DOWN;
        detectedObjectList = new ArrayList<>();
    }
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
        detectedObjectList = getDetectedObjects(time);
        for(DetectedObject obj : detectedObjectList){
            if(obj.getId().equals("ERROR")){
                return null;
            }
        }
        StampedDetectedObjects result = new StampedDetectedObjects(time, detectedObjectList);
        return new DetectObjectsEvent("camera",result, time +frequency);


    }
    /*
    returns empty list if no objects detected
     */
    private ArrayList<DetectedObject> getDetectedObjects(int time){
        Gson gson = new Gson();
        String fileName = "camera_data.json";
        ArrayList<DetectedObject> result = new ArrayList<>();
        String cameraName = String.format("camera%d", this.id);
        try (FileReader fileReader = new FileReader(fileName)){
            JsonObject jsonObject = gson.fromJson(fileReader, JsonObject.class);
            JsonArray camera = jsonObject.getAsJsonArray(cameraName);
            for(JsonElement element : camera){
                JsonObject detectedObject = element.getAsJsonObject();
                if(detectedObject.get("time").getAsInt() == time){
                    JsonArray objectsArray = detectedObject.getAsJsonArray("detectedObjects");
                    for (JsonElement objectElement : objectsArray) {
                        JsonObject object = objectElement.getAsJsonObject();
                        String id = object.get("id").getAsString();
                        String description = object.get("description").getAsString();
                        result.add(new DetectedObject(id, description));
                    }
                }
                else if(detectedObject.get("time").getAsInt() > time)
                    break;
            }

        }
        catch (Exception e){

        }
        return result;
    }

}
