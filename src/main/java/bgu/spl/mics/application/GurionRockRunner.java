package bgu.spl.mics.application;

import bgu.spl.mics.application.objects.Camera;
import bgu.spl.mics.application.objects.LiDarWorkerTracker;
import bgu.spl.mics.application.objects.StampedCloudPoints;
import bgu.spl.mics.application.objects.StampedDetectedObjects;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.io.FileReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * The main entry point for the GurionRock Pro Max Ultra Over 9000 simulation.
 * <p>
 * This class initializes the system and starts the simulation by setting up
 * services, objects, and configurations.
 * </p>
 */
public class GurionRockRunner {

    /**
     * The main method of the simulation.
     * This method sets up the necessary components, parses configuration files,
     * initializes services, and starts the simulation.
     *
     * @param args Command-line arguments. The first argument is expected to be the path to the configuration file.
     */
    public static void main(String[] args) {
        System.out.println("Hello World!");
        // TODO: Parse configuration file.
        // TODO: Initialize system components and services.
        // TODO: Start the simulation.

        Gson gson = new Gson();
        try (FileReader configReader = new FileReader("configuration_file.json");
             FileReader cameraReader = new FileReader("camera_data.json");
             FileReader lidarReader = new FileReader("lidar_data.json"); ) {
             JsonObject config = gson.fromJson(configReader, JsonObject.class);
             JsonArray camerasArray = config.getAsJsonObject("Cameras").getAsJsonArray("CamerasConfigurations");
             Type cameraListType = new TypeToken<List<Camera>>() {}.getType();
             List<Camera> cameraList = gson.fromJson(camerasArray, cameraListType);
             for(Camera camera : cameraList) {
                 Type StampedDetectedObjectsType = new TypeToken<ArrayList<StampedDetectedObjects>>() {}.getType();
                 camera.setDetectedObjectsList(gson.fromJson(cameraReader,StampedDetectedObjectsType));
             }

            JsonArray lidarsArray = config.getAsJsonObject("LidarWorkers").getAsJsonArray("LidarConfigurations");
            Type lidarListType = new TypeToken<List<LiDarWorkerTracker>>() {}.getType();
            List<LiDarWorkerTracker> lidarList = gson.fromJson(lidarsArray, lidarListType);
            for(LiDarWorkerTracker lidar : lidarList) {
                Type StampedCloudPointsType = new TypeToken<ArrayList<StampedCloudPoints>>() {}.getType();
                lidar.setLastTrackedObjects(gson.fromJson(lidarReader,StampedCloudPointsType));
            }

        }
        catch (Exception e) {

        }



    }

}
